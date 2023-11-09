--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.25
-- Dumped by pg_dump version 9.5.5

-- Started on 2023-11-09 06:39:58

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE meme_sharing;
--
-- TOC entry 2161 (class 1262 OID 18344)
-- Name: meme_sharing; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE meme_sharing WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


ALTER DATABASE meme_sharing OWNER TO postgres;

\connect meme_sharing

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2164 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 181 (class 1259 OID 18345)
-- Name: accesses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE accesses (
    id bigint NOT NULL,
    is_deleted boolean,
    name character varying(255) NOT NULL
);


ALTER TABLE accesses OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 18379)
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE categories (
    id bigint NOT NULL,
    is_deleted boolean,
    name character varying(255) NOT NULL,
    user_id bigint NOT NULL,
    created_at date NOT NULL
);


ALTER TABLE categories OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 18391)
-- Name: images; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE images (
    id bigint NOT NULL,
    is_deleted boolean,
    original_image text NOT NULL,
    thumbnail_image text NOT NULL,
    meme_id bigint NOT NULL
);


ALTER TABLE images OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 18399)
-- Name: memes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE memes (
    id bigint NOT NULL,
    created_at date NOT NULL,
    description character varying(255) NOT NULL,
    is_deleted boolean,
    name character varying(255) NOT NULL,
    url character varying(255) NOT NULL,
    category_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE memes OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 18365)
-- Name: seq_access; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_access
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_access OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 18384)
-- Name: seq_category; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_category
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_category OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 18407)
-- Name: seq_image; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_image
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_image OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 18409)
-- Name: seq_meme; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_meme
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_meme OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 18367)
-- Name: seq_user; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_user
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_user OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 18350)
-- Name: users_accesses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users_accesses (
    user_id bigint NOT NULL,
    access_id bigint NOT NULL
);


ALTER TABLE users_accesses OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 18353)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users (
    id bigint NOT NULL,
    is_deleted boolean,
    login character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    password_created_at date NOT NULL
);


ALTER TABLE users OWNER TO postgres;

--
-- TOC entry 2146 (class 0 OID 18345)
-- Dependencies: 181
-- Data for Name: accesses; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO accesses (id, is_deleted, name) VALUES (1, false, 'ROLE_USER');


--
-- TOC entry 2151 (class 0 OID 18379)
-- Dependencies: 186
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2153 (class 0 OID 18391)
-- Dependencies: 188
-- Data for Name: images; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2154 (class 0 OID 18399)
-- Dependencies: 189
-- Data for Name: memes; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2165 (class 0 OID 0)
-- Dependencies: 184
-- Name: seq_access; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_access', 1, false);


--
-- TOC entry 2166 (class 0 OID 0)
-- Dependencies: 187
-- Name: seq_category; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_category', 1, false);


--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 190
-- Name: seq_image; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_image', 1, false);


--
-- TOC entry 2168 (class 0 OID 0)
-- Dependencies: 191
-- Name: seq_meme; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_meme', 1, false);


--
-- TOC entry 2169 (class 0 OID 0)
-- Dependencies: 185
-- Name: seq_user; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_user', 1, false);


--
-- TOC entry 2147 (class 0 OID 18350)
-- Dependencies: 182
-- Data for Name: users_accesses; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2148 (class 0 OID 18353)
-- Dependencies: 183
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO users (id, is_deleted, login, password, password_created_at) VALUES (1, false, 'amaral_adm@hotmail.com', '$2a$10$bX6JMn6LUgyUpSrg3/.s2.S9gVeV5fVmrjDYRU6vBJvLVPTcfGt.2', '2023-11-06');


--
-- TOC entry 2013 (class 2606 OID 18349)
-- Name: accesses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY accesses
    ADD CONSTRAINT accesses_pkey PRIMARY KEY (id);


--
-- TOC entry 2021 (class 2606 OID 18383)
-- Name: categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- TOC entry 2023 (class 2606 OID 18398)
-- Name: images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY images
    ADD CONSTRAINT images_pkey PRIMARY KEY (id);


--
-- TOC entry 2025 (class 2606 OID 18406)
-- Name: memes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY memes
    ADD CONSTRAINT memes_pkey PRIMARY KEY (id);


--
-- TOC entry 2015 (class 2606 OID 18362)
-- Name: uk_99eu0gkyijod6fbcmk918c2xd; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users_accesses
    ADD CONSTRAINT uk_99eu0gkyijod6fbcmk918c2xd UNIQUE (access_id);


--
-- TOC entry 2017 (class 2606 OID 18364)
-- Name: unique_access_user; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users_accesses
    ADD CONSTRAINT unique_access_user UNIQUE (user_id, access_id);


--
-- TOC entry 2019 (class 2606 OID 18360)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2026 (class 2606 OID 18369)
-- Name: access_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users_accesses
    ADD CONSTRAINT access_fk FOREIGN KEY (access_id) REFERENCES accesses(id);


--
-- TOC entry 2030 (class 2606 OID 18416)
-- Name: category_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY memes
    ADD CONSTRAINT category_fk FOREIGN KEY (category_id) REFERENCES categories(id);


--
-- TOC entry 2029 (class 2606 OID 18411)
-- Name: meme_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY images
    ADD CONSTRAINT meme_fk FOREIGN KEY (meme_id) REFERENCES memes(id);


--
-- TOC entry 2027 (class 2606 OID 18374)
-- Name: user_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users_accesses
    ADD CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2028 (class 2606 OID 18386)
-- Name: user_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2031 (class 2606 OID 18421)
-- Name: user_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY memes
    ADD CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2163 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2023-11-09 06:39:59

--
-- PostgreSQL database dump complete
--

