
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
