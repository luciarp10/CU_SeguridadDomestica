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
--CREATE DATABASE new_database;
-- ddl-end --


-- object: public."USUARIO" | type: TABLE --
-- DROP TABLE IF EXISTS public."USUARIO" CASCADE;
CREATE TABLE public."USUARIO" (
	"DNI" varchar(9) NOT NULL,
	"ID_Cliente" integer NOT NULL,
	"Direcccion" character(100) NOT NULL,
	"Telefono" smallint,
	CONSTRAINT "USUARIO_pk" PRIMARY KEY ("ID_Cliente")

);
-- ddl-end --
ALTER TABLE public."USUARIO" OWNER TO postgres;
-- ddl-end --

-- object: public."IDENTIFICACION" | type: TABLE --
-- DROP TABLE IF EXISTS public."IDENTIFICACION" CASCADE;
CREATE TABLE public."IDENTIFICACION" (
	"Codigo_QR" integer NOT NULL,
	"Password" smallint NOT NULL,
	"ID_Cliente_USUARIO" integer
);
-- ddl-end --
ALTER TABLE public."IDENTIFICACION" OWNER TO postgres;
-- ddl-end --

-- object: "USUARIO_fk" | type: CONSTRAINT --
-- ALTER TABLE public."IDENTIFICACION" DROP CONSTRAINT IF EXISTS "USUARIO_fk" CASCADE;
ALTER TABLE public."IDENTIFICACION" ADD CONSTRAINT "USUARIO_fk" FOREIGN KEY ("ID_Cliente_USUARIO")
REFERENCES public."USUARIO" ("ID_Cliente") MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public."Registro_Temperatura" | type: TABLE --
-- DROP TABLE IF EXISTS public."Registro_Temperatura" CASCADE;
CREATE TABLE public."Registro_Temperatura" (
	"Fecha_Estadistico" date,
	"Valor_Estadistico" smallint,
	"Hora_Estadistico" float,
	"Fecha_User" date,
	"Valor_User" smallint,
	"Hora_User" float,
	"ID_Cliente_USUARIO" integer
);
-- ddl-end --
ALTER TABLE public."Registro_Temperatura" OWNER TO postgres;
-- ddl-end --

-- object: "USUARIO_fk" | type: CONSTRAINT --
-- ALTER TABLE public."Registro_Temperatura" DROP CONSTRAINT IF EXISTS "USUARIO_fk" CASCADE;
ALTER TABLE public."Registro_Temperatura" ADD CONSTRAINT "USUARIO_fk" FOREIGN KEY ("ID_Cliente_USUARIO")
REFERENCES public."USUARIO" ("ID_Cliente") MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Registro_Temperatura_uq" | type: CONSTRAINT --
-- ALTER TABLE public."Registro_Temperatura" DROP CONSTRAINT IF EXISTS "Registro_Temperatura_uq" CASCADE;
ALTER TABLE public."Registro_Temperatura" ADD CONSTRAINT "Registro_Temperatura_uq" UNIQUE ("ID_Cliente_USUARIO");
-- ddl-end --

-- object: public."Registro_Luminosidad" | type: TABLE --
-- DROP TABLE IF EXISTS public."Registro_Luminosidad" CASCADE;
CREATE TABLE public."Registro_Luminosidad" (
	"Fecha_Estadistico" date,
	"Valor_Estadistico" smallint,
	"Hora_Estadistico" float,
	"Fecha_User" date,
	"Valor_User" smallint,
	"Hora_User" float,
	"ID_Cliente_USUARIO" integer
);
-- ddl-end --
ALTER TABLE public."Registro_Luminosidad" OWNER TO postgres;
-- ddl-end --

-- object: "USUARIO_fk" | type: CONSTRAINT --
-- ALTER TABLE public."Registro_Luminosidad" DROP CONSTRAINT IF EXISTS "USUARIO_fk" CASCADE;
ALTER TABLE public."Registro_Luminosidad" ADD CONSTRAINT "USUARIO_fk" FOREIGN KEY ("ID_Cliente_USUARIO")
REFERENCES public."USUARIO" ("ID_Cliente") MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Registro_Luminosidad_uq" | type: CONSTRAINT --
-- ALTER TABLE public."Registro_Luminosidad" DROP CONSTRAINT IF EXISTS "Registro_Luminosidad_uq" CASCADE;
ALTER TABLE public."Registro_Luminosidad" ADD CONSTRAINT "Registro_Luminosidad_uq" UNIQUE ("ID_Cliente_USUARIO");
-- ddl-end --

-- object: public."Registro_Presion" | type: TABLE --
-- DROP TABLE IF EXISTS public."Registro_Presion" CASCADE;
CREATE TABLE public."Registro_Presion" (
	"Valor_Normal" float,
	"Fecha_User" date,
	"Valor_User" smallint,
	"Hora_User" float,
	"ID_Cliente_USUARIO" integer
);
-- ddl-end --
ALTER TABLE public."Registro_Presion" OWNER TO postgres;
-- ddl-end --

-- object: "USUARIO_fk" | type: CONSTRAINT --
-- ALTER TABLE public."Registro_Presion" DROP CONSTRAINT IF EXISTS "USUARIO_fk" CASCADE;
ALTER TABLE public."Registro_Presion" ADD CONSTRAINT "USUARIO_fk" FOREIGN KEY ("ID_Cliente_USUARIO")
REFERENCES public."USUARIO" ("ID_Cliente") MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Registro_Presion_uq" | type: CONSTRAINT --
-- ALTER TABLE public."Registro_Presion" DROP CONSTRAINT IF EXISTS "Registro_Presion_uq" CASCADE;
ALTER TABLE public."Registro_Presion" ADD CONSTRAINT "Registro_Presion_uq" UNIQUE ("ID_Cliente_USUARIO");
-- ddl-end --

-- object: public."Registro_Humo" | type: TABLE --
-- DROP TABLE IF EXISTS public."Registro_Humo" CASCADE;
CREATE TABLE public."Registro_Humo" (
	"Valor_Normal" float,
	"Fecha_User" date,
	"Valor_User" smallint,
	"Hora_User" float,
	"ID_Cliente_USUARIO" integer
);
-- ddl-end --
ALTER TABLE public."Registro_Humo" OWNER TO postgres;
-- ddl-end --

-- object: public."Registro_Camara" | type: TABLE --
-- DROP TABLE IF EXISTS public."Registro_Camara" CASCADE;
CREATE TABLE public."Registro_Camara" (
	"Fecha_User" date,
	"Foto_User" bit,
	"Hora_User" float,
	"ID_Cliente_USUARIO" integer
);
-- ddl-end --
ALTER TABLE public."Registro_Camara" OWNER TO postgres;
-- ddl-end --

-- object: public."Registro_Movimiento" | type: TABLE --
-- DROP TABLE IF EXISTS public."Registro_Movimiento" CASCADE;
CREATE TABLE public."Registro_Movimiento" (
	"Valor_Normal" float,
	"Fecha_User" date,
	"Valor_User" smallint,
	"Hora_User" float,
	"ID_Cliente_USUARIO" integer
);
-- ddl-end --
ALTER TABLE public."Registro_Movimiento" OWNER TO postgres;
-- ddl-end --

-- object: "USUARIO_fk" | type: CONSTRAINT --
-- ALTER TABLE public."Registro_Humo" DROP CONSTRAINT IF EXISTS "USUARIO_fk" CASCADE;
ALTER TABLE public."Registro_Humo" ADD CONSTRAINT "USUARIO_fk" FOREIGN KEY ("ID_Cliente_USUARIO")
REFERENCES public."USUARIO" ("ID_Cliente") MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Registro_Humo_uq" | type: CONSTRAINT --
-- ALTER TABLE public."Registro_Humo" DROP CONSTRAINT IF EXISTS "Registro_Humo_uq" CASCADE;
ALTER TABLE public."Registro_Humo" ADD CONSTRAINT "Registro_Humo_uq" UNIQUE ("ID_Cliente_USUARIO");
-- ddl-end --

-- object: "USUARIO_fk" | type: CONSTRAINT --
-- ALTER TABLE public."Registro_Movimiento" DROP CONSTRAINT IF EXISTS "USUARIO_fk" CASCADE;
ALTER TABLE public."Registro_Movimiento" ADD CONSTRAINT "USUARIO_fk" FOREIGN KEY ("ID_Cliente_USUARIO")
REFERENCES public."USUARIO" ("ID_Cliente") MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Registro_Movimiento_uq" | type: CONSTRAINT --
-- ALTER TABLE public."Registro_Movimiento" DROP CONSTRAINT IF EXISTS "Registro_Movimiento_uq" CASCADE;
ALTER TABLE public."Registro_Movimiento" ADD CONSTRAINT "Registro_Movimiento_uq" UNIQUE ("ID_Cliente_USUARIO");
-- ddl-end --

-- object: "USUARIO_fk" | type: CONSTRAINT --
-- ALTER TABLE public."Registro_Camara" DROP CONSTRAINT IF EXISTS "USUARIO_fk" CASCADE;
ALTER TABLE public."Registro_Camara" ADD CONSTRAINT "USUARIO_fk" FOREIGN KEY ("ID_Cliente_USUARIO")
REFERENCES public."USUARIO" ("ID_Cliente") MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Registro_Camara_uq" | type: CONSTRAINT --
-- ALTER TABLE public."Registro_Camara" DROP CONSTRAINT IF EXISTS "Registro_Camara_uq" CASCADE;
ALTER TABLE public."Registro_Camara" ADD CONSTRAINT "Registro_Camara_uq" UNIQUE ("ID_Cliente_USUARIO");
-- ddl-end --


