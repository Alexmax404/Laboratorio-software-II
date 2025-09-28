
	-- ===========================================
	-- Función para obtener la última versión de un formato
	-- ===========================================
	CREATE OR REPLACE FUNCTION gtg.fn_get_latest_version(p_formato_id UUID)
	RETURNS INTEGER LANGUAGE sql STABLE AS $$
	  SELECT COALESCE(MAX(version), 0) FROM gtg.formato_version WHERE formato_id = p_formato_id;
	$$;
	
	-- ===========================================
	-- Función para "enviar/reenviar" Formato A (gestiona versión + intentos)
	--    - Si p_formato_id IS NULL -> crea formato maestro + version 1
	--    - Si p_formato_id IS NOT NULL -> crea nueva versión (version = last+1)
	--    - Bloquea si intentos >= max_intentos
	-- ===========================================
	CREATE OR REPLACE FUNCTION gtg.submit_formato(
	    p_formato_id UUID,            -- NULL => crear nuevo
	    p_estudiante_id1 UUID,
	    p_estudiante_id2 UUID,
	    p_docente_id UUID,
	    p_enviado_por UUID,
	    p_titulo TEXT,
	    p_modalidad TEXT,
	    p_director TEXT,
	    p_co_director TEXT,
	    p_fecha_presentacion DATE,
	    p_objetivos_generales TEXT,
	    p_objetivos_especificos TEXT,
	    p_archivo_formato_path TEXT,
	    OUT o_formato_id UUID,
	    OUT o_version INTEGER
	) RETURNS RECORD
	LANGUAGE plpgsql AS $$
	DECLARE
	    v_formato gtg.formato%ROWTYPE;
	    v_next_version INTEGER;
	BEGIN
	    IF p_formato_id IS NULL THEN
	        -- ===== CREAR NUEVO FORMATO =====
	        PERFORM 1 FROM gtg.usuario WHERE id = p_estudiante_id1;
	        IF NOT FOUND THEN
	            RAISE EXCEPTION 'Estudiante1 no existe';
	        END IF;
	
	        IF p_estudiante_id2 IS NOT NULL THEN
	            PERFORM 1 FROM gtg.usuario WHERE id = p_estudiante_id2;
	            IF NOT FOUND THEN
	                RAISE EXCEPTION 'Estudiante2 informado no existe';
	            END IF;
	        END IF;
	
	        -- validar que p_enviado_por sea el docente
	        IF p_enviado_por IS NULL OR (p_enviado_por <> p_docente_id) THEN
	            RAISE EXCEPTION 'El formato A debe ser enviado por el docente.';
	        END IF;
	
	        -- crear formato maestro
	        INSERT INTO gtg.formato (estudiante_id1, estudiante_id2, docente_id, titulo, modalidad, director, co_director, fecha_presentacion, estado, intentos, max_intentos, created_at, updated_at)
	        VALUES (p_estudiante_id1, p_estudiante_id2, p_docente_id, p_titulo, p_modalidad, p_director, p_co_director, p_fecha_presentacion, 'EnRevision', 1, 3, now(), now())
	        RETURNING * INTO v_formato;
	
	        v_next_version := 1;
	
	        -- primera versión
	        INSERT INTO gtg.formato_version (formato_id, version, titulo, modalidad, director, co_director, fecha_presentacion,
	            objetivos_generales, objetivos_especificos, archivo_formato_path, estado_local, enviado_por, fecha_subida)
	        VALUES (v_formato.id, v_next_version, p_titulo, p_modalidad, p_director, p_co_director, p_fecha_presentacion,
	            p_objetivos_generales, p_objetivos_especificos, p_archivo_formato_path, 'Enviado', p_enviado_por, now());
	
	        o_formato_id := v_formato.id;
	        o_version := v_next_version;
	        RETURN;
	
	    ELSE
	        -- ===== REENVÍO =====
	        SELECT * INTO v_formato FROM gtg.formato WHERE id = p_formato_id;
	        IF NOT FOUND THEN
	            RAISE EXCEPTION 'Formato % no encontrado', p_formato_id;
	        END IF;
	
	        -- validar que p_enviado_por sea el docente del formato
	        IF p_enviado_por IS NULL OR (p_enviado_por <> v_formato.docente_id) THEN
	            RAISE EXCEPTION 'Solo el docente propietario puede reenviar el formato.';
	        END IF;
	
	        -- bloquear si el estado no es CorreccionesSolicitadas
	        IF v_formato.estado <> 'CorreccionesSolicitadas' THEN
	            RAISE EXCEPTION 'El formato solo puede reenviarse cuando el estado es "CorreccionesSolicitadas". Estado actual: %', v_formato.estado;
	        END IF;
	
	        -- bloquear si ya alcanzó el máximo de intentos
	        IF v_formato.intentos >= v_formato.max_intentos THEN
	            UPDATE gtg.formato
	            SET estado = 'Rechazado',
	                updated_at = now()
	            WHERE id = p_formato_id;
	            RAISE EXCEPTION 'Se alcanzó el máximo de intentos (%). El formato ha sido rechazado.', v_formato.max_intentos;
	        END IF;
	
	        -- calcular siguiente versión
	        v_next_version := (SELECT COALESCE(MAX(version),0) + 1 FROM gtg.formato_version WHERE formato_id = p_formato_id);
	
	        -- insertar nueva versión
	        INSERT INTO gtg.formato_version (formato_id, version, titulo, modalidad, director, co_director, fecha_presentacion,
	            objetivos_generales, objetivos_especificos, archivo_formato_path, estado_local, enviado_por, fecha_subida)
	        VALUES (p_formato_id, v_next_version, p_titulo, p_modalidad, p_director, p_co_director, p_fecha_presentacion,
	            p_objetivos_generales, p_objetivos_especificos, p_archivo_formato_path, 'Corregido', p_enviado_por, now());
	
	        -- incrementar contador y mantener estado en revisión
	        UPDATE gtg.formato
	        SET intentos = intentos + 1,
	            estado = CASE 
	                        WHEN intentos + 1 > max_intentos THEN 'Rechazado'
	                        ELSE 'EnRevision'
	                     END,
	            updated_at = now(),
	            titulo = p_titulo,
	            modalidad = p_modalidad,
	            director = p_director,
	            co_director = p_co_director,
	            fecha_presentacion = p_fecha_presentacion
	        WHERE id = p_formato_id
	        RETURNING * INTO v_formato;
			
	
	        -- si llegó al límite, cerramos el caso
	        IF v_formato.estado = 'Rechazado' THEN
	            RAISE EXCEPTION 'El formato ha sido rechazado definitivamente tras alcanzar el máximo de intentos.';
	        END IF;
	
	        o_formato_id := v_formato.id;
	        o_version := v_next_version;
	        RETURN;
	    END IF;
	END;
	$$;
	
	-- ===================================
	-- Función: add_evaluacion_coordinador (coordinador evalúa)
	-- ===================================
	CREATE OR REPLACE FUNCTION gtg.add_evaluacion_coordinador(
	    p_formato_id UUID,
	    p_formato_version_id UUID,
	    p_coordinador_id UUID,
	    p_decision TEXT,
	    p_comentarios TEXT,
	    OUT o_eval_id UUID
	) RETURNS UUID
	LANGUAGE plpgsql AS $$
	DECLARE
	    v_rol TEXT;
	BEGIN
	    -- validar rol del coordinador
	    SELECT rol INTO v_rol FROM gtg.usuario WHERE id = p_coordinador_id;
	    IF NOT FOUND THEN
	        RAISE EXCEPTION 'Coordinador con id % no encontrado', p_coordinador_id;
	    END IF;
	    IF v_rol <> 'Coordinador' THEN
	        RAISE EXCEPTION 'Usuario % no tiene rol Coordinador', p_coordinador_id;
	    END IF;
	
	    -- validar existencia del formato maestro
	    PERFORM 1 FROM gtg.formato WHERE id = p_formato_id;
	    IF NOT FOUND THEN
	        RAISE EXCEPTION 'Formato % no existe', p_formato_id;
	    END IF;
	
	    -- si se pasó formato_version_id, validar que pertenezca al formato
	    IF p_formato_version_id IS NOT NULL THEN
	        PERFORM 1 FROM gtg.formato_version fv
	        WHERE fv.id = p_formato_version_id AND fv.formato_id = p_formato_id;
	        IF NOT FOUND THEN
	            RAISE EXCEPTION 'La versión % no pertenece al formato %', p_formato_version_id, p_formato_id;
	        END IF;
	    END IF;
	
	    -- validar decisión permitida (seguridad extra)
	    IF p_decision NOT IN ('Aprobado','Correcciones','Rechazado') THEN
	        RAISE EXCEPTION 'Decision inválida: %', p_decision;
	    END IF;
	
	    -- insertar evaluación y devolver id en la variable OUT
	    INSERT INTO gtg.evaluacion_coordinador (formato_id, formato_version_id, coordinador_id, decision, comentarios, fecha_evaluacion)
	    VALUES (p_formato_id, p_formato_version_id, p_coordinador_id, p_decision, p_comentarios, now())
	    RETURNING id INTO o_eval_id;
	
	    -- actualizar estado del maestro según la decisión
	    IF p_decision = 'Aprobado' THEN
	        UPDATE gtg.formato SET estado = 'Aprobado', updated_at = now() WHERE id = p_formato_id;
	    ELSIF p_decision = 'Rechazado' THEN
	        UPDATE gtg.formato SET estado = 'Rechazado', updated_at = now() WHERE id = p_formato_id;
	    ELSIF p_decision = 'Correcciones' THEN
	        UPDATE gtg.formato SET estado = 'CorreccionesSolicitadas', updated_at = now() WHERE id = p_formato_id;
	    END IF;
	
	    RETURN;  -- RECORDAAAR: en funciones con OUT usar RETURN sin valor
	END;
	$$;
	
