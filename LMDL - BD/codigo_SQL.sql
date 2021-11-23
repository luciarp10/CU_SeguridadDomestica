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


-- object: public.usuario | type: TABLE --
-- DROP TABLE IF EXISTS public.usuario CASCADE;
CREATE TABLE public.usuario (
	dni text NOT NULL,
	id_cliente integer NOT NULL,
	direccion character(50) NOT NULL,
	telefono integer,
	nombre text NOT NULL,
	CONSTRAINT "USUARIO_pk" PRIMARY KEY (id_cliente)

);
-- ddl-end --
ALTER TABLE public.usuario OWNER TO postgres;
-- ddl-end --

-- object: public.identificacion | type: TABLE --
-- DROP TABLE IF EXISTS public.identificacion CASCADE;
CREATE TABLE public.identificacion (
	codigo_qr integer NOT NULL,
	password integer NOT NULL,
	id_cliente_usuario integer
);
-- ddl-end --
ALTER TABLE public.identificacion OWNER TO postgres;
-- ddl-end --

-- object: usuario_fk | type: CONSTRAINT --
-- ALTER TABLE public.identificacion DROP CONSTRAINT IF EXISTS usuario_fk CASCADE;
ALTER TABLE public.identificacion ADD CONSTRAINT usuario_fk FOREIGN KEY (id_cliente_usuario)
REFERENCES public.usuario (id_cliente) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.registro_temperatura | type: TABLE --
-- DROP TABLE IF EXISTS public.registro_temperatura CASCADE;
CREATE TABLE public.registro_temperatura (
	fecha_estadistico date,
	valor_estadistico float,
	hora_estadistico float,
	fecha_user date,
	valor_user float,
	hora_user float,
	id_cliente_usuario integer
);
-- ddl-end --
ALTER TABLE public.registro_temperatura OWNER TO postgres;
-- ddl-end --

-- object: usuario_fk | type: CONSTRAINT --
-- ALTER TABLE public.registro_temperatura DROP CONSTRAINT IF EXISTS usuario_fk CASCADE;
ALTER TABLE public.registro_temperatura ADD CONSTRAINT usuario_fk FOREIGN KEY (id_cliente_usuario)
REFERENCES public.usuario (id_cliente) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: registro_temperatura_uq | type: CONSTRAINT --
-- ALTER TABLE public.registro_temperatura DROP CONSTRAINT IF EXISTS registro_temperatura_uq CASCADE;
ALTER TABLE public.registro_temperatura ADD CONSTRAINT registro_temperatura_uq UNIQUE (id_cliente_usuario);
-- ddl-end --

-- object: public.registro_presion | type: TABLE --
-- DROP TABLE IF EXISTS public.registro_presion CASCADE;
CREATE TABLE public.registro_presion (
	valor_normal float,
	fecha_user date,
	valor_user float,
	hora_user float,
	id_cliente_usuario integer
);
-- ddl-end --
ALTER TABLE public.registro_presion OWNER TO postgres;
-- ddl-end --

-- object: usuario_fk | type: CONSTRAINT --
-- ALTER TABLE public.registro_presion DROP CONSTRAINT IF EXISTS usuario_fk CASCADE;
ALTER TABLE public.registro_presion ADD CONSTRAINT usuario_fk FOREIGN KEY (id_cliente_usuario)
REFERENCES public.usuario (id_cliente) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: registro_presion_uq | type: CONSTRAINT --
-- ALTER TABLE public.registro_presion DROP CONSTRAINT IF EXISTS registro_presion_uq CASCADE;
ALTER TABLE public.registro_presion ADD CONSTRAINT registro_presion_uq UNIQUE (id_cliente_usuario);
-- ddl-end --

-- object: public.registro_camara | type: TABLE --
-- DROP TABLE IF EXISTS public.registro_camara CASCADE;
CREATE TABLE public.registro_camara (
	fecha_user date,
	foto_user bit,
	hora_user float,
	id_cliente_usuario integer
);
-- ddl-end --
ALTER TABLE public.registro_camara OWNER TO postgres;
-- ddl-end --

-- object: usuario_fk | type: CONSTRAINT --
-- ALTER TABLE public.registro_camara DROP CONSTRAINT IF EXISTS usuario_fk CASCADE;
ALTER TABLE public.registro_camara ADD CONSTRAINT usuario_fk FOREIGN KEY (id_cliente_usuario)
REFERENCES public.usuario (id_cliente) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: registro_camara_uq | type: CONSTRAINT --
-- ALTER TABLE public.registro_camara DROP CONSTRAINT IF EXISTS registro_camara_uq CASCADE;
ALTER TABLE public.registro_camara ADD CONSTRAINT registro_camara_uq UNIQUE (id_cliente_usuario);
-- ddl-end --

-- object: public.registro_luminosidad | type: TABLE --
-- DROP TABLE IF EXISTS public.registro_luminosidad CASCADE;
CREATE TABLE public.registro_luminosidad (
	fecha_estadistico date,
	valor_estadistico float,
	hora_estadistico float,
	fecha_user date,
	valor_user float,
	hora_user float,
	id_cliente_usuario integer
);
-- ddl-end --
ALTER TABLE public.registro_luminosidad OWNER TO postgres;
-- ddl-end --

-- object: usuario_fk | type: CONSTRAINT --
-- ALTER TABLE public.registro_luminosidad DROP CONSTRAINT IF EXISTS usuario_fk CASCADE;
ALTER TABLE public.registro_luminosidad ADD CONSTRAINT usuario_fk FOREIGN KEY (id_cliente_usuario)
REFERENCES public.usuario (id_cliente) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: registro_luminosidad_uq | type: CONSTRAINT --
-- ALTER TABLE public.registro_luminosidad DROP CONSTRAINT IF EXISTS registro_luminosidad_uq CASCADE;
ALTER TABLE public.registro_luminosidad ADD CONSTRAINT registro_luminosidad_uq UNIQUE (id_cliente_usuario);
-- ddl-end --

-- object: public.registro_movimiento | type: TABLE --
-- DROP TABLE IF EXISTS public.registro_movimiento CASCADE;
CREATE TABLE public.registro_movimiento (
	valor_normal float,
	fecha_user date,
	valor_user float,
	hora_user float,
	id_cliente_usuario integer
);
-- ddl-end --
ALTER TABLE public.registro_movimiento OWNER TO postgres;
-- ddl-end --

-- object: public.registro_humo | type: TABLE --
-- DROP TABLE IF EXISTS public.registro_humo CASCADE;
CREATE TABLE public.registro_humo (
	valor_normal float,
	fecha_user date,
	valor_user float,
	hora_user float,
	id_cliente_usuario integer
);
-- ddl-end --
ALTER TABLE public.registro_humo OWNER TO postgres;
-- ddl-end --

-- object: usuario_fk | type: CONSTRAINT --
-- ALTER TABLE public.registro_movimiento DROP CONSTRAINT IF EXISTS usuario_fk CASCADE;
ALTER TABLE public.registro_movimiento ADD CONSTRAINT usuario_fk FOREIGN KEY (id_cliente_usuario)
REFERENCES public.usuario (id_cliente) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: registro_movimiento_uq | type: CONSTRAINT --
-- ALTER TABLE public.registro_movimiento DROP CONSTRAINT IF EXISTS registro_movimiento_uq CASCADE;
ALTER TABLE public.registro_movimiento ADD CONSTRAINT registro_movimiento_uq UNIQUE (id_cliente_usuario);
-- ddl-end --

-- object: usuario_fk | type: CONSTRAINT --
-- ALTER TABLE public.registro_humo DROP CONSTRAINT IF EXISTS usuario_fk CASCADE;
ALTER TABLE public.registro_humo ADD CONSTRAINT usuario_fk FOREIGN KEY (id_cliente_usuario)
REFERENCES public.usuario (id_cliente) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: registro_humo_uq | type: CONSTRAINT --
-- ALTER TABLE public.registro_humo DROP CONSTRAINT IF EXISTS registro_humo_uq CASCADE;
ALTER TABLE public.registro_humo ADD CONSTRAINT registro_humo_uq UNIQUE (id_cliente_usuario);
-- ddl-end --


