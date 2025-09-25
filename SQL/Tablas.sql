
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
		docente_id UUID NOT NULL REFERENCES gtg.usuario(id) ON DELETE CASCADE,
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
	CREATE INDEX IF NOT EXISTS idx_formato_doc ON gtg.formato(docente_id);
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
	
	-- EVALUACIÓN COORDINADOR
	CREATE TABLE IF NOT EXISTS gtg.evaluacion_coordinador (
	    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	    formato_id UUID NOT NULL REFERENCES gtg.formato(id) ON DELETE CASCADE,
	    formato_version_id UUID REFERENCES gtg.formato_version(id) ON DELETE SET NULL,
	    coordinador_id UUID NOT NULL REFERENCES gtg.usuario(id),
	    decision TEXT NOT NULL CHECK (decision IN ('Aprobado', 'Correcciones', 'Rechazado')),
	    comentarios TEXT,
	    fecha_evaluacion TIMESTAMPTZ DEFAULT now()
	);
	
	CREATE INDEX IF NOT EXISTS idx_eval_formato ON gtg.evaluacion_coordinador(formato_id);
	CREATE INDEX IF NOT EXISTS idx_eval_coordinador_coordinador ON gtg.evaluacion_coordinador(coordinador_id);
	
