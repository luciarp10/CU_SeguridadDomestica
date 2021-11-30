-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.3-beta1
-- PostgreSQL version: 13.0
-- Project Site: pgmodeler.io
-- Model Author: ---

-- Database creation must be performed outside a multi lined SQL file. 
-- These commands were put in this file only as a convenience.
-- 
-- object: new_database | type: DATABASE --
-- DROP DATABASE IF EXISTS new_database;
CREATE DATABASE new_database;
-- ddl-end --


-- object: habitacion | type: TABLE --
-- DROP TABLE IF EXISTS habitacion CASCADE;
CREATE TABLE habitacion (
	id_habitacion integer NOT NULL,
	n_puertas integer NOT NULL,
	n_ventanas integer NOT NULL,
	tamanno integer,
	cod_sistema_sistema_seguridad integer,
	CONSTRAINT habitacion_pk PRIMARY KEY (id_habitacion)

);
-- ddl-end --
ALTER TABLE habitacion OWNER TO postgres;
-- ddl-end --

-- object: identificacion | type: TABLE --
-- DROP TABLE IF EXISTS identificacion CASCADE;
CREATE TABLE identificacion (
	codigo_qr integer NOT NULL,
	password char(20) NOT NULL,
	nombre char(50) NOT NULL,
	cod_sistema_sistema_seguridad integer,
	CONSTRAINT identificacion_pk PRIMARY KEY (nombre)

);
-- ddl-end --
ALTER TABLE identificacion OWNER TO postgres;
-- ddl-end --

-- object: registro_estadistico | type: TABLE --
-- DROP TABLE IF EXISTS registro_estadistico CASCADE;
CREATE TABLE registro_estadistico (
	fecha date NOT NULL,
	valor float NOT NULL,
	hora time NOT NULL,
	id_sensor_sensor integer
);
-- ddl-end --
ALTER TABLE registro_estadistico OWNER TO postgres;
-- ddl-end --

-- object: registro_camara | type: TABLE --
-- DROP TABLE IF EXISTS registro_camara CASCADE;
CREATE TABLE registro_camara (
	fecha date NOT NULL,
	foto character(256) NOT NULL,
	hora time NOT NULL,
	id_sensor_sensor integer
);
-- ddl-end --
ALTER TABLE registro_camara OWNER TO postgres;
-- ddl-end --

-- object: cliente | type: TABLE --
-- DROP TABLE IF EXISTS cliente CASCADE;
CREATE TABLE cliente (
	dni char(9) NOT NULL,
	id_cliente integer NOT NULL,
	direccion char(50) NOT NULL,
	telefono char(9) NOT NULL,
	nombre char(50) NOT NULL,
	CONSTRAINT id_cliente PRIMARY KEY (id_cliente)

);
-- ddl-end --
ALTER TABLE cliente OWNER TO postgres;
-- ddl-end --

-- object: registro | type: TABLE --
-- DROP TABLE IF EXISTS registro CASCADE;
CREATE TABLE registro (
	hora_on time NOT NULL,
	fecha_on date NOT NULL,
	duracion float NOT NULL,
	id_actuador_actuador integer
);
-- ddl-end --
ALTER TABLE registro OWNER TO postgres;
-- ddl-end --

-- object: sistema_seguridad | type: TABLE --
-- DROP TABLE IF EXISTS sistema_seguridad CASCADE;
CREATE TABLE sistema_seguridad (
	cod_sistema integer NOT NULL,
	direccion char(50) NOT NULL,
	nombre char(50) NOT NULL,
	contrasenna_acceso char(4) NOT NULL,
	id_cliente_cliente integer,
	CONSTRAINT sistema_seguridad_pk PRIMARY KEY (cod_sistema)

);
-- ddl-end --
ALTER TABLE sistema_seguridad OWNER TO postgres;
-- ddl-end --

-- object: cliente_fk | type: CONSTRAINT --
-- ALTER TABLE sistema_seguridad DROP CONSTRAINT IF EXISTS cliente_fk CASCADE;
ALTER TABLE sistema_seguridad ADD CONSTRAINT cliente_fk FOREIGN KEY (id_cliente_cliente)
REFERENCES cliente (id_cliente) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: sistema_seguridad_fk | type: CONSTRAINT --
-- ALTER TABLE identificacion DROP CONSTRAINT IF EXISTS sistema_seguridad_fk CASCADE;
ALTER TABLE identificacion ADD CONSTRAINT sistema_seguridad_fk FOREIGN KEY (cod_sistema_sistema_seguridad)
REFERENCES sistema_seguridad (cod_sistema) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: sistema_seguridad_fk | type: CONSTRAINT --
-- ALTER TABLE habitacion DROP CONSTRAINT IF EXISTS sistema_seguridad_fk CASCADE;
ALTER TABLE habitacion ADD CONSTRAINT sistema_seguridad_fk FOREIGN KEY (cod_sistema_sistema_seguridad)
REFERENCES sistema_seguridad (cod_sistema) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: sensor | type: TABLE --
-- DROP TABLE IF EXISTS sensor CASCADE;
CREATE TABLE sensor (
	id_sensor integer NOT NULL,
	tipo char(50) NOT NULL,
	id_habitacion_habitacion integer,
	CONSTRAINT sensor_pk PRIMARY KEY (id_sensor)

);
-- ddl-end --
ALTER TABLE sensor OWNER TO postgres;
-- ddl-end --

-- object: actuador | type: TABLE --
-- DROP TABLE IF EXISTS actuador CASCADE;
CREATE TABLE actuador (
	id_actuador integer NOT NULL,
	tipo char(50) NOT NULL,
	id_habitacion_habitacion integer,
	CONSTRAINT actuador_pk PRIMARY KEY (id_actuador)

);
-- ddl-end --
ALTER TABLE actuador OWNER TO postgres;
-- ddl-end --

-- object: habitacion_fk | type: CONSTRAINT --
-- ALTER TABLE sensor DROP CONSTRAINT IF EXISTS habitacion_fk CASCADE;
ALTER TABLE sensor ADD CONSTRAINT habitacion_fk FOREIGN KEY (id_habitacion_habitacion)
REFERENCES habitacion (id_habitacion) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: habitacion_fk | type: CONSTRAINT --
-- ALTER TABLE actuador DROP CONSTRAINT IF EXISTS habitacion_fk CASCADE;
ALTER TABLE actuador ADD CONSTRAINT habitacion_fk FOREIGN KEY (id_habitacion_habitacion)
REFERENCES habitacion (id_habitacion) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: actuador_fk | type: CONSTRAINT --
-- ALTER TABLE registro DROP CONSTRAINT IF EXISTS actuador_fk CASCADE;
ALTER TABLE registro ADD CONSTRAINT actuador_fk FOREIGN KEY (id_actuador_actuador)
REFERENCES actuador (id_actuador) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: registro_uq | type: CONSTRAINT --
-- ALTER TABLE registro DROP CONSTRAINT IF EXISTS registro_uq CASCADE;
ALTER TABLE registro ADD CONSTRAINT registro_uq UNIQUE (id_actuador_actuador);
-- ddl-end --

-- object: sensor_fk | type: CONSTRAINT --
-- ALTER TABLE registro_estadistico DROP CONSTRAINT IF EXISTS sensor_fk CASCADE;
ALTER TABLE registro_estadistico ADD CONSTRAINT sensor_fk FOREIGN KEY (id_sensor_sensor)
REFERENCES sensor (id_sensor) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: registro_estadistico_uq | type: CONSTRAINT --
-- ALTER TABLE registro_estadistico DROP CONSTRAINT IF EXISTS registro_estadistico_uq CASCADE;
ALTER TABLE registro_estadistico ADD CONSTRAINT registro_estadistico_uq UNIQUE (id_sensor_sensor);
-- ddl-end --

-- object: sensor_fk | type: CONSTRAINT --
-- ALTER TABLE registro_camara DROP CONSTRAINT IF EXISTS sensor_fk CASCADE;
ALTER TABLE registro_camara ADD CONSTRAINT sensor_fk FOREIGN KEY (id_sensor_sensor)
REFERENCES sensor (id_sensor) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: registro_camara_uq | type: CONSTRAINT --
-- ALTER TABLE registro_camara DROP CONSTRAINT IF EXISTS registro_camara_uq CASCADE;
ALTER TABLE registro_camara ADD CONSTRAINT registro_camara_uq UNIQUE (id_sensor_sensor);
-- ddl-end --


