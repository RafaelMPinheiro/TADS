--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.2 (Homebrew)

-- Started on 2024-07-08 22:51:00 -03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE trabalho_1;
--
-- TOC entry 3402 (class 1262 OID 18694)
-- Name: trabalho_1; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE trabalho_1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE trabalho_1 OWNER TO postgres;

\connect trabalho_1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3403 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 225 (class 1255 OID 18751)
-- Name: converter_senha(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.converter_senha(character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $_$
BEGIN
    RETURN MD5($1);
END;
$_$;


ALTER FUNCTION public.converter_senha(character varying) OWNER TO postgres;

--
-- TOC entry 223 (class 1255 OID 18717)
-- Name: isautor(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.isautor(pessoa_id integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN (SELECT tipo = 'A' FROM pessoa WHERE id = pessoa_id);
END;
$$;


ALTER FUNCTION public.isautor(pessoa_id integer) OWNER TO postgres;

--
-- TOC entry 224 (class 1255 OID 18718)
-- Name: iscompartilhado(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.iscompartilhado(id_post integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
    compartilhadoAUX BOOLEAN;
    postAUX INTEGER;
BEGIN
    SELECT COUNT(*) INTO postAUX FROM pessoa_post WHERE post_id = id_post;

    IF postAUX = 0 THEN
        RETURN TRUE;
    END IF;

    SELECT compartilhado INTO compartilhadoAUX FROM post WHERE id = id_post;

    IF compartilhadoAUX = FALSE THEN
        RAISE NOTICE 'O post não é compartilhado';
        RETURN FALSE;
    END IF;

    RETURN TRUE;
END;
$$;


ALTER FUNCTION public.iscompartilhado(id_post integer) OWNER TO postgres;

--
-- TOC entry 222 (class 1255 OID 18716)
-- Name: isleitor(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.isleitor(pessoa_id integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN (SELECT tipo = 'L' FROM pessoa WHERE id = pessoa_id);
END;
$$;


ALTER FUNCTION public.isleitor(pessoa_id integer) OWNER TO postgres;

--
-- TOC entry 229 (class 1255 OID 18755)
-- Name: login(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.login(character varying, character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $_$
declare
    md5_senha VARCHAR(32);
begin
    select MD5($2) into md5_senha;
    
    return exists (
        select 1
        from pessoa
        where email = $1
        and senha = md5_senha
    );
end;
$_$;


ALTER FUNCTION public.login(character varying, character varying) OWNER TO postgres;

--
-- TOC entry 226 (class 1255 OID 18752)
-- Name: mostrar_dados_pessoas(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mostrar_dados_pessoas() RETURNS TABLE(nome character varying, email character varying, tipo character, "Endereço completo" text)
    LANGUAGE plpgsql
    AS $$
begin
	return query SELECT pessoa.nome, pessoa.email, pessoa.tipo,
		Coalesce(string_agg(endereco.bairro || ' ' || endereco.rua || ' ' || endereco.numero, '| '), pessoa.tipo || ' - SEM ENDEREÇO CADASTRADO') AS endereco_completo
		FROM pessoa LEFT JOIN endereco ON pessoa.id = endereco.pessoa_id
		GROUP BY pessoa.nome, pessoa.email, pessoa.tipo
		ORDER BY pessoa.nome;
end;
$$;


ALTER FUNCTION public.mostrar_dados_pessoas() OWNER TO postgres;

--
-- TOC entry 228 (class 1255 OID 18754)
-- Name: mostrar_pessoas_post(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mostrar_pessoas_post() RETURNS TABLE("Titulo" character varying, "Autores envolvidos" text)
    LANGUAGE plpgsql
    AS $$
begin
	return query 
        SELECT post.titulo AS "Titulo",
            STRING_AGG(pessoa.nome, ', ') AS "Autores envolvidos"
        FROM post
        LEFT JOIN pessoa_post ON post.id = pessoa_post.post_id
        LEFT JOIN pessoa ON pessoa_post.pessoa_id = pessoa.id
        GROUP BY post.id, post.titulo;
end;
$$;


ALTER FUNCTION public.mostrar_pessoas_post() OWNER TO postgres;

--
-- TOC entry 227 (class 1255 OID 18753)
-- Name: mostrar_qntd_pessoas_post(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.mostrar_qntd_pessoas_post() RETURNS TABLE("Titulo" character varying, "Data de publicação" text, "Autores envolvidos" bigint)
    LANGUAGE plpgsql
    AS $$
begin
	return query 
        select post.titulo, 
        to_char(post.data_hora, 'HH24:MI DD/MM/YYYY'), 
        count(pessoa_post.pessoa_id) from post 
        left join pessoa_post on post.id = pessoa_post.post_id
        group by post.titulo, post.data_hora;
end;
$$;


ALTER FUNCTION public.mostrar_qntd_pessoas_post() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 221 (class 1259 OID 18737)
-- Name: endereco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.endereco (
    id integer NOT NULL,
    bairro character varying(255) NOT NULL,
    rua character varying(255) NOT NULL,
    numero character varying(255) NOT NULL,
    cep character(9) NOT NULL,
    pessoa_id integer,
    CONSTRAINT endereco_pessoa_id_check CHECK (public.isleitor(pessoa_id))
);


ALTER TABLE public.endereco OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 18736)
-- Name: endereco_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.endereco_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.endereco_id_seq OWNER TO postgres;

--
-- TOC entry 3404 (class 0 OID 0)
-- Dependencies: 220
-- Name: endereco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.endereco_id_seq OWNED BY public.endereco.id;


--
-- TOC entry 216 (class 1259 OID 18696)
-- Name: pessoa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pessoa (
    id integer NOT NULL,
    nome character varying(100) NOT NULL,
    email character varying(100),
    senha character varying(100) NOT NULL,
    tipo character(1),
    CONSTRAINT pessoa_tipo_check CHECK ((tipo = ANY (ARRAY['A'::bpchar, 'L'::bpchar])))
);


ALTER TABLE public.pessoa OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 18695)
-- Name: pessoa_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pessoa_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pessoa_id_seq OWNER TO postgres;

--
-- TOC entry 3405 (class 0 OID 0)
-- Dependencies: 215
-- Name: pessoa_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pessoa_id_seq OWNED BY public.pessoa.id;


--
-- TOC entry 219 (class 1259 OID 18719)
-- Name: pessoa_post; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pessoa_post (
    pessoa_id integer NOT NULL,
    post_id integer NOT NULL,
    CONSTRAINT pessoa_post_pessoa_id_check CHECK (public.isautor(pessoa_id)),
    CONSTRAINT pessoa_post_post_id_check CHECK (public.iscompartilhado(post_id))
);


ALTER TABLE public.pessoa_post OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 18706)
-- Name: post; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.post (
    id integer NOT NULL,
    titulo character varying(100) NOT NULL,
    texto text NOT NULL,
    data_hora timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    compartilhado boolean DEFAULT false
);


ALTER TABLE public.post OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 18705)
-- Name: post_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.post_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.post_id_seq OWNER TO postgres;

--
-- TOC entry 3406 (class 0 OID 0)
-- Dependencies: 217
-- Name: post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.post_id_seq OWNED BY public.post.id;


--
-- TOC entry 3229 (class 2604 OID 18740)
-- Name: endereco id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.endereco ALTER COLUMN id SET DEFAULT nextval('public.endereco_id_seq'::regclass);


--
-- TOC entry 3225 (class 2604 OID 18699)
-- Name: pessoa id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa ALTER COLUMN id SET DEFAULT nextval('public.pessoa_id_seq'::regclass);


--
-- TOC entry 3226 (class 2604 OID 18709)
-- Name: post id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post ALTER COLUMN id SET DEFAULT nextval('public.post_id_seq'::regclass);


--
-- TOC entry 3396 (class 0 OID 18737)
-- Dependencies: 221
-- Data for Name: endereco; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.endereco VALUES (1, 'Centro', 'Rua das Flores', '123', '12345-678', 6);
INSERT INTO public.endereco VALUES (2, 'Vila Nova', 'Rua Palmeiras', '456', '23456-789', 7);
INSERT INTO public.endereco VALUES (3, 'Jardim Primavera', 'Rua dos Girassóis', '101', '45678-901', 9);
INSERT INTO public.endereco VALUES (4, 'Bairro do Lago', 'Rua Acácias', '222', '56789-012', 10);
INSERT INTO public.endereco VALUES (5, 'Vila Nova', 'Rua Palmeiras', '456', '23456-789', 10);


--
-- TOC entry 3391 (class 0 OID 18696)
-- Dependencies: 216
-- Data for Name: pessoa; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pessoa VALUES (1, 'Ana Silva', 'ana.silva@example.com', '92f20dafc5e5ac1c66820903c492cc04', 'A');
INSERT INTO public.pessoa VALUES (2, 'Carlos Oliveira', 'carlos.oliveira@example.com', '34ae07655b9a94e90556aff79bfd60b0', 'A');
INSERT INTO public.pessoa VALUES (3, 'Mariana Santos', 'mariana.santos@example.com', 'f940608acd624f8092bc86353052e734', 'A');
INSERT INTO public.pessoa VALUES (4, 'Pedro Almeida', 'pedro.almeida@example.com', 'f98494438eca6d421a8f9b5b3f02ed83', 'A');
INSERT INTO public.pessoa VALUES (5, 'Camila Costa', 'camila.costa@example.com', '4003ad34bf5fe5b88e5f393ff15ad623', 'A');
INSERT INTO public.pessoa VALUES (6, 'João Santos', 'joao.santos@example.com', '92f20dafc5e5ac1c66820903c492cc04', 'L');
INSERT INTO public.pessoa VALUES (7, 'Maria Oliveira', 'maria.oliveira@example.com', '34ae07655b9a94e90556aff79bfd60b0', 'L');
INSERT INTO public.pessoa VALUES (8, 'Rafael Lima', 'rafael.lima@example.com', 'f940608acd624f8092bc86353052e734', 'L');
INSERT INTO public.pessoa VALUES (9, 'Fernanda Costa', 'fernanda.costa@example.com', 'f98494438eca6d421a8f9b5b3f02ed83', 'L');
INSERT INTO public.pessoa VALUES (10, 'Juliana Pereira', 'juliana.pereira@example.com', '4003ad34bf5fe5b88e5f393ff15ad623', 'L');


--
-- TOC entry 3394 (class 0 OID 18719)
-- Dependencies: 219
-- Data for Name: pessoa_post; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pessoa_post VALUES (1, 1);
INSERT INTO public.pessoa_post VALUES (2, 2);
INSERT INTO public.pessoa_post VALUES (3, 3);
INSERT INTO public.pessoa_post VALUES (4, 4);
INSERT INTO public.pessoa_post VALUES (5, 5);
INSERT INTO public.pessoa_post VALUES (5, 2);


--
-- TOC entry 3393 (class 0 OID 18706)
-- Dependencies: 218
-- Data for Name: post; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.post VALUES (1, 'A viagem dos meus sonhos', 'Finalmente fiz aquela viagem dos sonhos para o Caribe...', '2024-07-09 01:46:06.242035', false);
INSERT INTO public.post VALUES (2, 'Review do novo filme "Caminho Estelar"', 'Ontem fui ao cinema assistir ao novo lançamento e aqui está minha opinião...', '2024-07-09 01:46:06.242035', true);
INSERT INTO public.post VALUES (3, 'Dicas para manter a saúde mental em tempos difíceis', 'Compartilhando algumas dicas que têm me ajudado a lidar com o estresse...', '2024-07-09 01:46:06.242035', true);
INSERT INTO public.post VALUES (4, 'Receita especial de família: Bolo de cenoura da vovó', 'Essa é uma receita que passou de geração em geração em nossa família...', '2024-07-09 01:46:06.242035', true);
INSERT INTO public.post VALUES (5, 'Os benefícios do yoga para a mente e o corpo', 'Praticar yoga tem sido uma parte essencial da minha rotina diária. Aqui estão alguns dos benefícios que tenho experimentado...', '2024-07-09 01:46:06.242035', true);


--
-- TOC entry 3407 (class 0 OID 0)
-- Dependencies: 220
-- Name: endereco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.endereco_id_seq', 5, true);


--
-- TOC entry 3408 (class 0 OID 0)
-- Dependencies: 215
-- Name: pessoa_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pessoa_id_seq', 10, true);


--
-- TOC entry 3409 (class 0 OID 0)
-- Dependencies: 217
-- Name: post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.post_id_seq', 5, true);


--
-- TOC entry 3243 (class 2606 OID 18745)
-- Name: endereco endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);


--
-- TOC entry 3235 (class 2606 OID 18704)
-- Name: pessoa pessoa_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa
    ADD CONSTRAINT pessoa_email_key UNIQUE (email);


--
-- TOC entry 3237 (class 2606 OID 18702)
-- Name: pessoa pessoa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa
    ADD CONSTRAINT pessoa_pkey PRIMARY KEY (id);


--
-- TOC entry 3241 (class 2606 OID 18725)
-- Name: pessoa_post pessoa_post_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_post
    ADD CONSTRAINT pessoa_post_pkey PRIMARY KEY (pessoa_id, post_id);


--
-- TOC entry 3239 (class 2606 OID 18715)
-- Name: post post_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_pkey PRIMARY KEY (id);


--
-- TOC entry 3246 (class 2606 OID 18746)
-- Name: endereco endereco_pessoa_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pessoa_id_fkey FOREIGN KEY (pessoa_id) REFERENCES public.pessoa(id);


--
-- TOC entry 3244 (class 2606 OID 18726)
-- Name: pessoa_post pessoa_post_pessoa_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_post
    ADD CONSTRAINT pessoa_post_pessoa_id_fkey FOREIGN KEY (pessoa_id) REFERENCES public.pessoa(id);


--
-- TOC entry 3245 (class 2606 OID 18731)
-- Name: pessoa_post pessoa_post_post_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_post
    ADD CONSTRAINT pessoa_post_post_id_fkey FOREIGN KEY (post_id) REFERENCES public.post(id);


-- Completed on 2024-07-08 22:51:01 -03

--
-- PostgreSQL database dump complete
--

