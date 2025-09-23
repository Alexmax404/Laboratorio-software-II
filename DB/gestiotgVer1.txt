-- Eliminar tablas
DROP TABLE IF EXISTS gtg.aval_coordinador CASCADE;
DROP TABLE IF EXISTS gtg.evaluacion_docente CASCADE;
DROP TABLE IF EXISTS gtg.formato_version CASCADE;
DROP TABLE IF EXISTS gtg.formato CASCADE;
DROP TABLE IF EXISTS gtg.usuario CASCADE;

-- Limpiar tablas
TRUNCATE TABLE gtg.aval_coordinador RESTART IDENTITY CASCADE;
TRUNCATE TABLE gtg.evaluacion_docente RESTART IDENTITY CASCADE;
TRUNCATE TABLE gtg.formato_version RESTART IDENTITY CASCADE;
TRUNCATE TABLE gtg.formato RESTART IDENTITY CASCADE;
TRUNCATE TABLE gtg.usuario RESTART IDENTITY CASCADE;

-- ===============================================
-- USUARIOS Y BASE DE DATOS GTG
-- ===============================================

-- Rol dueño de la BD (solo para DDL, no para la app)
CREATE ROLE gtg_owner LOGIN PASSWORD 'AdminPass2025!' CREATEDB;

-- Base de datos principal
CREATE DATABASE gestiontg OWNER gtg_owner;

-- Rol de la aplicación (solo DML)
CREATE ROLE gtg_appuser LOGIN PASSWORD 'ApplicationUser2025!';

CREATE SCHEMA IF NOT EXISTS gtg AUTHORIZATION gtg_owner;

-- Extensiones
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ===============================================
-- ASIGNACIÓN DE PRIVILEGIOS AL USUARIO DE LA APP
-- ===============================================
GRANT CONNECT ON DATABASE gestiontg TO gtg_appuser;   -- acceso a la BD
GRANT USAGE ON SCHEMA public TO gtg_appuser;          -- uso de esquema public
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO gtg_appuser; -- DML
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO gtg_appuser;



-- Conceder acceso al esquema gtg
GRANT USAGE ON SCHEMA gtg TO gtg_appuser;

-- Conceder permisos sobre todas las tablas del esquema gtg
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA gtg TO gtg_appuser;

-- Asegurar permisos para tablas futuras
ALTER DEFAULT PRIVILEGES IN SCHEMA gtg 
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO gtg_appuser;

-- Conceder permisos para usar las secuencias (necesario para inserciones con DEFAULT)
GRANT USAGE ON ALL SEQUENCES IN SCHEMA gtg TO gtg_appuser;
ALTER DEFAULT PRIVILEGES IN SCHEMA gtg 
GRANT USAGE ON SEQUENCES TO gtg_appuser;

-- ===============================================
-- CREACIÓN DE TABLAS
-- ===============================================

-- USUARIO (docentes, estudiantes, coordinadores)
CREATE TABLE IF NOT EXISTS gtg.usuario (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),  -- Estaba usando UUID, si resulta cansón lo podemos cambiar.
    nombres TEXT NOT NULL,
    apellidos TEXT NOT NULL,
    celular TEXT,
    programa TEXT NOT NULL CHECK (programa IN 
        ('IngenieriaDeSistemas', 
         'IngenieriaElectronicaYTelecomunicaciones', 
         'AutomaticaIndustrial', 
         'TecnologiaIndustrial')),
    rol TEXT NOT NULL CHECK (rol IN ('Docente', 'Estudiante', 'Coordinador')),
    correo TEXT UNIQUE NOT NULL,
    contrasenia TEXT NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_usuario_correo ON gtg.usuario(correo);

-- FORMATO A (Tabla maestra de formato A (Id dijo por cada formato))
CREATE TABLE IF NOT EXISTS gtg.formato (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    estudiante_id1 UUID NOT NULL REFERENCES gtg.usuario(id) ON DELETE CASCADE,
	estudiante_id2 UUID	NULL REFERENCES gtg.usuario(id) ON DELETE SET NULL, --- Cambiar esto
    titulo TEXT NOT NULL,
    modalidad TEXT NOT NULL,
    director TEXT NOT NULL,
    co_director TEXT,
    fecha_presentacion DATE NOT NULL,
    estado TEXT NOT NULL CHECK (estado IN (
        'EnRevision', 'Aprobado', 'Rechazado', 'CorreccionesSolicitadas'
    )) DEFAULT 'EnRevision',
    intentos INTEGER NOT NULL DEFAULT 0,    -- número de envíos (se controla con funciones)
    max_intentos INTEGER NOT NULL DEFAULT 3,
    created_at TIMESTAMPTZ DEFAULT now(),
    updated_at TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_formato_est1 ON gtg.formato(estudiante_id1);
CREATE INDEX IF NOT EXISTS idx_formato_est2 ON gtg.formato(estudiante_id2);
CREATE INDEX IF NOT EXISTS idx_formato_estado ON gtg.formato(estado);

-- HISTORIAL DE VERSIONES (Cada reenvío genera una nueva fila)
CREATE TABLE IF NOT EXISTS gtg.formato_version (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    formato_id UUID NOT NULL REFERENCES gtg.formato(id) ON DELETE CASCADE,
    version INTEGER NOT NULL, 
    titulo TEXT NOT NULL,
    modalidad TEXT NOT NULL,
    director TEXT NOT NULL,
    co_director TEXT,
    fecha_presentacion DATE NOT NULL,
    objetivos_generales TEXT NOT NULL,
    objetivos_especificos TEXT NOT NULL,
    carta_aceptacion_path TEXT,
    archivo_formato_path TEXT NOT NULL, -- ruta/URL al PDF
    observaciones_estudiante TEXT,
    estado_local TEXT NOT NULL CHECK (estado_local IN (
        'Enviado', 'Corregido'
    )) DEFAULT 'Enviado',
    enviado_por UUID NOT NULL REFERENCES gtg.usuario(id),
    fecha_subida TIMESTAMPTZ DEFAULT now(),
    UNIQUE (formato_id, version)
);

CREATE INDEX IF NOT EXISTS idx_formato_version_formato ON gtg.formato_version(formato_id);
CREATE INDEX IF NOT EXISTS idx_formato_version_formato_version ON gtg.formato_version(formato_id, version DESC);

-- EVALUACIÓN DOCENTE
CREATE TABLE IF NOT EXISTS gtg.evaluacion_docente (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    formato_id UUID NOT NULL REFERENCES gtg.formato(id) ON DELETE CASCADE,
    formato_version_id UUID REFERENCES gtg.formato_version(id) ON DELETE SET NULL,
    docente_id UUID NOT NULL REFERENCES gtg.usuario(id),
    decision TEXT NOT NULL CHECK (decision IN ('Aprobado', 'Correcciones', 'Rechazado')),
    comentarios TEXT,
    fecha_evaluacion TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_eval_formato ON gtg.evaluacion_docente(formato_id);
CREATE INDEX IF NOT EXISTS idx_eval_docente_docente ON gtg.evaluacion_docente(docente_id);

-- AVAL DEL COORDINADOR
CREATE TABLE IF NOT EXISTS gtg.aval_coordinador (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    evaluacion_id UUID NOT NULL REFERENCES gtg.evaluacion_docente(id) ON DELETE CASCADE,
    coordinador_id UUID NOT NULL REFERENCES gtg.usuario(id),
    aval BOOLEAN NOT NULL,
    observaciones TEXT,
    fecha_aval TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_aval_eval ON gtg.aval_coordinador(evaluacion_id);

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
    p_enviado_por UUID,
    p_titulo TEXT,
    p_modalidad TEXT,
    p_director TEXT,
    p_co_director TEXT,
    p_fecha_presentacion DATE,
    p_objetivos_generales TEXT,
    p_objetivos_especificos TEXT,
    p_carta_aceptacion_path TEXT,
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
        -- Validaciones básicas (estudiante1 debe existir)
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
        -- validar que p_enviado_por sea uno de los estudiantes
        IF p_enviado_por IS NULL OR (p_enviado_por <> p_estudiante_id1 AND (p_estudiante_id2 IS NULL OR p_enviado_por <> p_estudiante_id2)) THEN
            RAISE EXCEPTION 'p_enviado_por debe ser est1 o est2';
        END IF;

        INSERT INTO gtg.formato (estudiante_id1, estudiante_id2, titulo, modalidad, director, co_director, fecha_presentacion, estado, intentos, max_intentos, created_at, updated_at)
        VALUES (p_estudiante_id1, p_estudiante_id2, p_titulo, p_modalidad, p_director, p_co_director, p_fecha_presentacion, 'EnRevision', 1, 3, now(), now())
        RETURNING * INTO v_formato;

        v_next_version := 1;

        INSERT INTO gtg.formato_version (formato_id, version, titulo, modalidad, director, co_director, fecha_presentacion,
            objetivos_generales, objetivos_especificos, carta_aceptacion_path, archivo_formato_path, estado_local, enviado_por, fecha_subida)
        VALUES (v_formato.id, v_next_version, p_titulo, p_modalidad, p_director, p_co_director, p_fecha_presentacion,
            p_objetivos_generales, p_objetivos_especificos, p_carta_aceptacion_path, p_archivo_formato_path, 'Enviado', p_enviado_por, now());

        o_formato_id := v_formato.id;
        o_version := v_next_version;
        RETURN;
    ELSE
        -- Reenvío
        SELECT * INTO v_formato FROM gtg.formato WHERE id = p_formato_id;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Formato % no encontrado', p_formato_id;
        END IF;

        -- validar que p_enviado_por sea uno de los estudiantes registrados en el formato
        IF p_enviado_por IS NULL OR (p_enviado_por <> v_formato.estudiante_id1 AND (v_formato.estudiante_id2 IS NULL OR p_enviado_por <> v_formato.estudiante_id2)) THEN
            RAISE EXCEPTION 'Solo estudiantes propietarios pueden reenviar (p_enviado_por inválido)';
        END IF;

        IF v_formato.intentos >= v_formato.max_intentos THEN
            RAISE EXCEPTION 'Se ha alcanzado el numero maximo de intentos (%). No se permite mas reenvios', v_formato.max_intentos;
        END IF;

        v_next_version := (SELECT COALESCE(MAX(version),0) + 1 FROM gtg.formato_version WHERE formato_id = p_formato_id);

        INSERT INTO gtg.formato_version (formato_id, version, titulo, modalidad, director, co_director, fecha_presentacion,
            objetivos_generales, objetivos_especificos, carta_aceptacion_path, archivo_formato_path, estado_local, enviado_por, fecha_subida)
        VALUES (p_formato_id, v_next_version, p_titulo, p_modalidad, p_director, p_co_director, p_fecha_presentacion,
            p_objetivos_generales, p_objetivos_especificos, p_carta_aceptacion_path, p_archivo_formato_path, 'Corregido', p_enviado_por, now());

        -- incrementar contador e actualizar metadata del maestro
        UPDATE gtg.formato
        SET intentos = intentos + 1,
            estado = 'EnRevision',
            updated_at = now(),
            titulo = p_titulo,
            modalidad = p_modalidad,
            director = p_director,
            co_director = p_co_director,
            fecha_presentacion = p_fecha_presentacion
        WHERE id = p_formato_id
        RETURNING * INTO v_formato;

        o_formato_id := v_formato.id;
        o_version := v_next_version;
        RETURN;
    END IF;
END;
$$;

-- ===================================
-- Función: add_evaluacion_docente (docente evalúa)
-- ===================================
CREATE OR REPLACE FUNCTION gtg.add_evaluacion_docente(
    p_formato_id UUID,
    p_formato_version_id UUID,
    p_docente_id UUID,
    p_decision TEXT,
    p_comentarios TEXT,
    OUT o_eval_id UUID
) RETURNS UUID
LANGUAGE plpgsql AS $$
DECLARE
    v_rol TEXT;
BEGIN
    -- validar rol del docente
    SELECT rol INTO v_rol FROM gtg.usuario WHERE id = p_docente_id;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Docente con id % no encontrado', p_docente_id;
    END IF;
    IF v_rol <> 'Docente' THEN
        RAISE EXCEPTION 'Usuario % no tiene rol Docente', p_docente_id;
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
    INSERT INTO gtg.evaluacion_docente (formato_id, formato_version_id, docente_id, decision, comentarios, fecha_evaluacion)
    VALUES (p_formato_id, p_formato_version_id, p_docente_id, p_decision, p_comentarios, now())
    RETURNING id INTO o_eval_id;

    -- actualizar estado del maestro según la decisión
    IF p_decision = 'Aprobado' THEN
        UPDATE gtg.formato SET estado = 'Aprobado', updated_at = now() WHERE id = p_formato_id;
    ELSIF p_decision = 'Rechazado' THEN
        UPDATE gtg.formato SET estado = 'Rechazado', updated_at = now() WHERE id = p_formato_id;
    ELSIF p_decision = 'Correcciones' THEN
        UPDATE gtg.formato SET estado = 'CorreccionesSolicitadas', updated_at = now() WHERE id = p_formato_id;
    END IF;

    RETURN;  -- IMPORTANTE: en funciones con OUT usar RETURN sin valor
END;
$$;

-- ===================================
-- Función: add_aval_coordinador
-- ===================================
CREATE OR REPLACE FUNCTION gtg.add_aval_coordinador(
    p_evaluacion_id UUID,
    p_coordinador_id UUID,
    p_aval BOOLEAN,
    p_observaciones TEXT,
    OUT o_aval_id UUID
) RETURNS UUID
LANGUAGE plpgsql AS $$
DECLARE
    v_rol TEXT;
    v_decision TEXT;
    v_formato_id UUID;
BEGIN
    -- validar rol coordinador
    SELECT rol INTO v_rol FROM gtg.usuario WHERE id = p_coordinador_id;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Coordinador con id % no encontrado', p_coordinador_id;
    END IF;
    IF v_rol <> 'Coordinador' THEN
        RAISE EXCEPTION 'Usuario % no tiene rol Coordinador', p_coordinador_id;
    END IF;

    -- validar evaluación
    SELECT formato_id, decision 
    INTO v_formato_id, v_decision 
    FROM gtg.evaluacion_docente 
    WHERE id = p_evaluacion_id;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Evaluación % no encontrada', p_evaluacion_id;
    END IF;

    -- insertar aval
    INSERT INTO gtg.aval_coordinador (evaluacion_id, coordinador_id, aval, observaciones, fecha_aval)
    VALUES (p_evaluacion_id, p_coordinador_id, p_aval, p_observaciones, now())
    RETURNING id INTO o_aval_id;

    -- actualizar estado del formato según el aval
    IF p_aval = TRUE THEN
        IF v_decision = 'Aprobado' THEN
            UPDATE gtg.formato 
            SET estado = 'Aprobado', updated_at = now() 
            WHERE id = v_formato_id;
        END IF;
    ELSE
        -- regla: si no avala, se marca como Rechazado
        UPDATE gtg.formato 
        SET estado = 'Rechazado', updated_at = now() 
        WHERE id = v_formato_id;
    END IF;

    RETURN;  -- importante: solo RETURN vacío cuando hay parámetros OUT
END;
$$;

-- ===============================================
-- PRUEBAS
-- ===============================================
-- Estudiantes
INSERT INTO gtg.usuario (nombres, apellidos, programa, rol, correo, contrasenia)
VALUES 
('Juan', 'Pérez', 'IngenieriaDeSistemas', 'Estudiante', 'juan@correo.com', crypt('1234', gen_salt('bf'))),
('Ana', 'López', 'IngenieriaDeSistemas', 'Estudiante', 'ana@correo.com', crypt('1234', gen_salt('bf')));

-- Docente
INSERT INTO gtg.usuario (nombres, apellidos, programa, rol, correo, contrasenia)
VALUES ('Carlos', 'Gómez', 'IngenieriaDeSistemas', 'Docente', 'carlos@correo.com', crypt('1234', gen_salt('bf')));

-- Coordinador
INSERT INTO gtg.usuario (nombres, apellidos, programa, rol, correo, contrasenia)
VALUES ('Marta', 'Ríos', 'IngenieriaDeSistemas', 'Coordinador', 'marta@correo.com', crypt('1234', gen_salt('bf')));

-- Enviar formato A
SELECT * FROM gtg.submit_formato(
    NULL,                                   -- NULL = nuevo formato
    (SELECT id FROM gtg.usuario WHERE correo='juan@correo.com'), -- estudiante1
    (SELECT id FROM gtg.usuario WHERE correo='ana@correo.com'),  -- estudiante2
    (SELECT id FROM gtg.usuario WHERE correo='juan@correo.com'), -- enviado_por (debe ser uno de los estudiantes)
    'Sistema de Gestión de TG',             -- título
    'Investigación',                        -- modalidad
    'Dr. López',                            -- director
    NULL,                                   -- co-director
    '2025-09-20',                           -- fecha_presentacion
    'Objetivo general del proyecto',        -- objetivos_generales
    'Objetivo específico 1; Objetivo 2',    -- objetivos_especificos
    NULL,                                   -- carta_aceptacion_path
    '/uploads/formatoA_v1.pdf'              -- archivo_formato_path
);

-- Reenviar formato A
SELECT * FROM gtg.submit_formato(
    (SELECT id FROM gtg.formato LIMIT 1),   -- formato ya existente
    (SELECT id FROM gtg.usuario WHERE correo='juan@correo.com'),
    (SELECT id FROM gtg.usuario WHERE correo='ana@correo.com'),
    (SELECT id FROM gtg.usuario WHERE correo='ana@correo.com'), -- ahora lo envía Ana
    'Sistema de Gestión de TG - corregido',
    'Investigación',
    'Dr. López',
    NULL,
    '2025-09-25',
    'Objetivo general actualizado',
    'Objetivo específico 1 actualizado',
    NULL,
    '/uploads/formatoA_v2.pdf'
);

-- Docente evalua formato
SELECT * FROM gtg.add_evaluacion_docente(
    (SELECT id FROM gtg.formato LIMIT 1),
    (SELECT id FROM gtg.formato_version ORDER BY version DESC LIMIT 1),
    (SELECT id FROM gtg.usuario WHERE rol='Docente'),
    'Correcciones',
    'El título no es claro'
);

-- Aval coordinador
SELECT * FROM gtg.add_aval_coordinador(
    (SELECT id FROM gtg.evaluacion_docente LIMIT 1),
    (SELECT id FROM gtg.usuario WHERE rol='Coordinador'),
    TRUE,   -- aval positivo
    'De acuerdo con el docente'
);

SELECT * FROM gtg.usuario;
SELECT * FROM gtg.formato;
SELECT * FROM gtg.formato_version;
SELECT * FROM gtg.evaluacion_docente;
SELECT * FROM gtg.aval_coordinador;

-- ===============================================
-- NOTAS:
-- - gtg_owner: se usa para crear y modificar la estructura de la BD (DDL).
-- - gtg_appuser: se usa en la aplicación para ejecutar consultas normales (DML).
-- - Me faltaría agregar un usuario para reportes, pero después.
-- ===============================================