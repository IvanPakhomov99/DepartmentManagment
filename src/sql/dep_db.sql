-- SQL Manager Lite for PostgreSQL 5.9.5.52424
-- ---------------------------------------
-- Хост         : localhost
-- База данных  : dep_db
-- Версия       : PostgreSQL 10.6, compiled by Visual C++ build 1800, 64-bit



SET check_function_bodies = false;
--
-- Definition for sequence employee_seq (OID = 25028) : 
--
SET search_path = public, pg_catalog;
CREATE SEQUENCE public.employee_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Definition for sequence department_seq (OID = 25032) : 
--
CREATE SEQUENCE public.department_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Definition for sequence department_id_seq (OID = 25058) : 
--
CREATE SEQUENCE public.department_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table department (OID = 25060) : 
--
CREATE TABLE public.department (
    id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
    name varchar(50) NOT NULL,
    city varchar(50) NOT NULL
)
WITH (oids = false);
--
-- Definition for sequence employee_id_seq (OID = 25133) : 
--
CREATE SEQUENCE public.employee_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
--
-- Structure for table employee (OID = 25135) : 
--
CREATE TABLE public.employee (
    id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    email varchar(100) NOT NULL,
    birth_day date,
    salary integer,
    dep_name varchar(50) NOT NULL
)
WITH (oids = false);
--
-- Data for table public.department (OID = 25060) (LIMIT 0,4)
--
INSERT INTO department (id, name, city)
VALUES (25, 'Education', 'Kharkiv');

INSERT INTO department (id, name, city)
VALUES (21, 'Finance', 'Lviv');

INSERT INTO department (id, name, city)
VALUES (23, 'Town Building', 'Dnipro');

INSERT INTO department (id, name, city)
VALUES (22, 'Medicine', 'Lviv');

--
-- Data for table public.employee (OID = 25135) (LIMIT 0,6)
--
INSERT INTO employee (id, first_name, last_name, email, birth_day, salary, dep_name)
VALUES (112, 'Mikhail', 'Yuminov', 'mikhail.yuminov@nure.ua', '1999-07-07', 1000, 'Medicine');

INSERT INTO employee (id, first_name, last_name, email, birth_day, salary, dep_name)
VALUES (58, 'Test', 'Test', 'test@nure.ua', '1999-07-07', 1111, 'Medicine');

INSERT INTO employee (id, first_name, last_name, email, birth_day, salary, dep_name)
VALUES (113, 'Yurii', 'Pakhomov', 'yurii.pakhomov@nure.ua', '1966-05-15', 777, 'Town Building');

INSERT INTO employee (id, first_name, last_name, email, birth_day, salary, dep_name)
VALUES (47, 'Marina', 'Pakhomov', 'marina.pakhomova@nure.ua', '1999-07-07', 777, 'Finance');

INSERT INTO employee (id, first_name, last_name, email, birth_day, salary, dep_name)
VALUES (42, 'Polina', 'Bilous', 'polina.bilous@nure.ua', '1999-08-18', 777, 'Education');

INSERT INTO employee (id, first_name, last_name, email, birth_day, salary, dep_name)
VALUES (16, 'Ivan', 'Pakhomov', 'ivan.pakhomov@nure.ua', '1999-07-07', 777, 'Education');

--
-- Definition for index idx_department_location (OID = 41383) : 
--
CREATE UNIQUE INDEX idx_department_location ON public.department USING btree (name, city);
--
-- Definition for index department_pkey (OID = 25063) : 
--
ALTER TABLE ONLY department
    ADD CONSTRAINT department_pkey
    PRIMARY KEY (id);
--
-- Definition for index department_name_key (OID = 25065) : 
--
ALTER TABLE ONLY department
    ADD CONSTRAINT department_name_key
    UNIQUE (name);
--
-- Definition for index employee_pkey (OID = 25138) : 
--
ALTER TABLE ONLY employee
    ADD CONSTRAINT employee_pkey
    PRIMARY KEY (id);
--
-- Definition for index employee_email_key (OID = 25140) : 
--
ALTER TABLE ONLY employee
    ADD CONSTRAINT employee_email_key
    UNIQUE (email);
--
-- Definition for index employee_dep_fk (OID = 33205) : 
--
ALTER TABLE ONLY employee
    ADD CONSTRAINT employee_dep_fk
    FOREIGN KEY (dep_name) REFERENCES department(name) ON UPDATE CASCADE ON DELETE CASCADE;
--
-- Data for sequence public.employee_seq (OID = 25028)
--
SELECT pg_catalog.setval('employee_seq', 1, false);
--
-- Data for sequence public.department_seq (OID = 25032)
--
SELECT pg_catalog.setval('department_seq', 1, false);
--
-- Data for sequence public.department_id_seq (OID = 25058)
--
SELECT pg_catalog.setval('department_id_seq', 51, true);
--
-- Data for sequence public.employee_id_seq (OID = 25133)
--
SELECT pg_catalog.setval('employee_id_seq', 120, true);
--
-- Comments
--
COMMENT ON SCHEMA public IS 'standard public schema';
