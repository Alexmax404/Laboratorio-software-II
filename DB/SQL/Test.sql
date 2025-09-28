	
	-- ===============================================
	-- PRUEBAS
	-- ===============================================

	---------------------------------- ESTUDIANTES ----------------------------------------------

	INSERT INTO gtg.usuario (nombres, apellidos, programa, rol, correo, contrasenia)
	VALUES 
	('Juan', 'Pérez', 'IngenieriaDeSistemas', 'Estudiante', 'juan@correo.com', crypt('1234', gen_salt('bf'))),
	('Ana', 'López', 'IngenieriaDeSistemas', 'Estudiante', 'ana@correo.com', crypt('1234', gen_salt('bf')));
	

	---------------------------------------- DOCENTE ----------------------------------------------
	INSERT INTO gtg.usuario (nombres, apellidos, programa, rol, correo, contrasenia)
	VALUES ('Carlos', 'Gómez', 'IngenieriaDeSistemas', 'Docente', 'carlos@correo.com', crypt('1234', gen_salt('bf')));
	
	-------------------------------------- COORDINADOR ----------------------------------------------
	INSERT INTO gtg.usuario (nombres, apellidos, programa, rol, correo, contrasenia)
	VALUES ('Marta', 'Ríos', 'IngenieriaDeSistemas', 'Coordinador', 'marta@correo.com', crypt('1234', gen_salt('bf')));
	
	
	-- Enviar formato A
	SELECT * FROM gtg.submit_formato(
	    NULL,                                   -- NULL = nuevo formato
	    (SELECT id FROM gtg.usuario WHERE correo='juan@correo.com'), -- estudiante1
		NULL,
	    (SELECT id FROM gtg.usuario WHERE correo='carlos@correo.com'),  -- docente
	    (SELECT id FROM gtg.usuario WHERE correo='carlos@correo.com'), -- enviado_por (debe ser uno de los estudiantes)
	    'Sistema de Gestión de TG',             -- título
	    'Investigación',                        -- modalidad
	    'Dr. López',                            -- director
	    NULL,                                   -- co-director
	    '2025-09-20',                           -- fecha_presentacion
	    'Objetivo general del proyecto',        -- objetivos_generales
	    'Objetivo específico 1; Objetivo 2',    -- objetivos_especificos
	    '/uploads/formatoA_v1.pdf'              -- archivo_formato_path
	);
	
	-- Reenviar formato A
	SELECT * FROM gtg.submit_formato(
	    (SELECT id FROM gtg.formato LIMIT 1),   -- formato ya existente
	    (SELECT id FROM gtg.usuario WHERE correo='juan@correo.com'),
	    NULL,
	    (SELECT id FROM gtg.usuario WHERE correo='carlos@correo.com'), 
		(SELECT id FROM gtg.usuario WHERE correo='carlos@correo.com'), -- Lo reenvía docente
	    'Sistema de Gestión de TG - corregido2',
	    'Investigación',
	    'Dr. López',
	    NULL,
	    '2025-09-25',
	    'Objetivo general actualizado',
	    'Objetivo específico 1 actualizado',
	    NULL,
	    '/uploads/formatoA_v2.pdf'
	);
	
	-- Coordinador evalua formato
	SELECT * FROM gtg.add_evaluacion_coordinador(
	    (SELECT id FROM gtg.formato LIMIT 1),
	    (SELECT id FROM gtg.formato_version ORDER BY version DESC LIMIT 1),
	    (SELECT id FROM gtg.usuario WHERE rol='Coordinador'),
	    'Correcciones',
	    'El título no es claro'
	);
	
	
	INSERT INTO gtg.usuario (nombres, apellidos, programa, rol, correo, contrasenia)
	VALUES ('1', '0', 'IngenieriaDeSistemas', 'Estudiante', '1', crypt('1', gen_salt('bf')));
	
	INSERT INTO gtg.usuario (nombres, apellidos, programa, rol, correo, contrasenia)
	VALUES ('2', '0', 'IngenieriaDeSistemas', 'Docente', '2', crypt('2', gen_salt('bf')));
	

	
	SELECT * FROM gtg.usuario;
	SELECT * FROM gtg.formato;
	SELECT * FROM gtg.formato_version;
	SELECT * FROM gtg.evaluacion_coordinador;
	
	-- ===============================================
	-- NOTAS:
	-- - gtg_owner: se usa para crear y modificar la estructura de la BD (DDL).
	-- - gtg_appuser: se usa en la aplicación para ejecutar consultas normales (DML).
	-- - Me faltaría agregar un usuario para reportes, pero después.
	-- ===============================================