--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.1
-- Dumped by pg_dump version 9.2.1
-- Started on 2013-11-29 15:48:54

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 180 (class 3079 OID 11727)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2020 (class 0 OID 0)
-- Dependencies: 180
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 193 (class 1255 OID 74341)
-- Name: procurauser(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION procurauser() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    x numeric;
    y numeric;
BEGIN
         select count(*) into x
         from utilizador
         where email = new.email;
         --Verifica se ja existe aquele mail
         select count(*) into y
         from utilizador
         where login=new.login;
-- Check that empname and salary are given
        IF y =0 THEN
            RAISE EXCEPTION '% ja esta a ser utilizado ',NEW.login;
        END IF;
        IF x =0 THEN
            RAISE EXCEPTION '% ja esta a ser utilizado ',NEW.email;
        END IF;

        IF NEW.nome IS NULL THEN
            RAISE EXCEPTION 'nome nao pode ser null';
        END IF;
        IF NEW.login IS NULL THEN
            RAISE EXCEPTION 'Login nao pode ser null';
        END IF;
        IF NEW.pass IS NULL THEN
            RAISE EXCEPTION 'Deve introduzir uma pass';
        END IF;
        IF NEW.login IS NULL THEN
            RAISE EXCEPTION 'Login nao pode ser null';
        END IF;
        RETURN NEW;
END;
$$;


ALTER FUNCTION public.procurauser() OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 169 (class 1259 OID 74206)
-- Name: accao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE accao (
    id_utilizador integer NOT NULL,
    id_ideia integer NOT NULL,
    num_shares numeric NOT NULL,
    valor numeric(9,2) NOT NULL,
    estado character varying(10) DEFAULT 'INACTIVO'::character varying NOT NULL,
    CONSTRAINT ck_valor CHECK ((valor >= (0)::numeric))
);


ALTER TABLE public.accao OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 74366)
-- Name: favoritos; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE favoritos (
    id_ideia integer NOT NULL,
    id_utilizador integer NOT NULL
);


ALTER TABLE public.favoritos OWNER TO postgres;

--
-- TOC entry 168 (class 1259 OID 74200)
-- Name: ideias; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ideias (
    id_ideia integer NOT NULL,
    id_utilizador integer NOT NULL,
    nome character varying(60) NOT NULL,
    venda_atomatica integer NOT NULL,
    descricao character varying(500),
    data_criacao timestamp without time zone DEFAULT now()
);


ALTER TABLE public.ideias OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 74373)
-- Name: seguidores; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE seguidores (
    id_ideia integer NOT NULL,
    id_utilizador integer NOT NULL
);


ALTER TABLE public.seguidores OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 74295)
-- Name: seq_ideia_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_ideia_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_ideia_id OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 74297)
-- Name: seq_topico_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_topico_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_topico_id OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 74299)
-- Name: seq_transaccao_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_transaccao_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_transaccao_id OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 74293)
-- Name: seq_utilizador_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_utilizador_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_utilizador_id OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 74216)
-- Name: topico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE topico (
    id_topico integer DEFAULT nextval('seq_topico_id'::regclass) NOT NULL,
    id_utilizador integer NOT NULL,
    nome character varying(20) NOT NULL,
    hashtag character varying(20) NOT NULL,
    data_criacao timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.topico OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 74222)
-- Name: topico_ideia; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE topico_ideia (
    id_topico integer NOT NULL,
    id_ideia integer NOT NULL
);


ALTER TABLE public.topico_ideia OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 74229)
-- Name: transacao; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE transacao (
    id_utilizador integer NOT NULL,
    uti_id_utilizador integer NOT NULL,
    id_ideia integer NOT NULL,
    n_shares integer NOT NULL,
    valor_compra numeric NOT NULL,
    id_transaccao integer DEFAULT nextval('seq_transaccao_id'::regclass) NOT NULL,
    data_transaccao date DEFAULT now() NOT NULL
);


ALTER TABLE public.transacao OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 74240)
-- Name: utilizador; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE utilizador (
    id_utilizador integer DEFAULT nextval('seq_utilizador_id'::regclass) NOT NULL,
    nome character varying(60) NOT NULL,
    login character varying(45) NOT NULL,
    pass character varying(60) NOT NULL,
    pais character varying(50),
    deicoins numeric DEFAULT 1000000 NOT NULL,
    email character varying(60),
    idade integer,
    status character varying(5) DEFAULT false NOT NULL
);


ALTER TABLE public.utilizador OWNER TO postgres;

--
-- TOC entry 1987 (class 2606 OID 74308)
-- Name: email_unico; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY utilizador
    ADD CONSTRAINT email_unico UNIQUE (email);


--
-- TOC entry 1989 (class 2606 OID 74306)
-- Name: login_unico; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY utilizador
    ADD CONSTRAINT login_unico UNIQUE (login);


--
-- TOC entry 1974 (class 2606 OID 74346)
-- Name: nome_unico; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY topico
    ADD CONSTRAINT nome_unico UNIQUE (nome);


--
-- TOC entry 1995 (class 2606 OID 74370)
-- Name: pk_favoritos; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY favoritos
    ADD CONSTRAINT pk_favoritos PRIMARY KEY (id_ideia, id_utilizador);


--
-- TOC entry 1966 (class 2606 OID 74204)
-- Name: pk_ideias; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ideias
    ADD CONSTRAINT pk_ideias PRIMARY KEY (id_ideia);


--
-- TOC entry 1998 (class 2606 OID 74377)
-- Name: pk_seguidores; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY seguidores
    ADD CONSTRAINT pk_seguidores PRIMARY KEY (id_ideia, id_utilizador);


--
-- TOC entry 1970 (class 2606 OID 74213)
-- Name: pk_share; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY accao
    ADD CONSTRAINT pk_share PRIMARY KEY (id_utilizador, id_ideia);


--
-- TOC entry 1976 (class 2606 OID 74220)
-- Name: pk_topico; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY topico
    ADD CONSTRAINT pk_topico PRIMARY KEY (id_topico);


--
-- TOC entry 1980 (class 2606 OID 74226)
-- Name: pk_topico_ideia; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY topico_ideia
    ADD CONSTRAINT pk_topico_ideia PRIMARY KEY (id_topico, id_ideia);


--
-- TOC entry 1983 (class 2606 OID 74236)
-- Name: pk_transacao; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY transacao
    ADD CONSTRAINT pk_transacao PRIMARY KEY (id_transaccao);


--
-- TOC entry 1991 (class 2606 OID 74247)
-- Name: pk_utilizador; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY utilizador
    ADD CONSTRAINT pk_utilizador PRIMARY KEY (id_utilizador);


--
-- TOC entry 1981 (class 1259 OID 74238)
-- Name: compra_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX compra_fk ON transacao USING btree (uti_id_utilizador);


--
-- TOC entry 1972 (class 1259 OID 74221)
-- Name: cria_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX cria_fk ON topico USING btree (id_utilizador);


--
-- TOC entry 1996 (class 1259 OID 74379)
-- Name: ideias_seguidas_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX ideias_seguidas_fk ON seguidores USING btree (id_ideia);


--
-- TOC entry 1992 (class 1259 OID 74371)
-- Name: inclui_favoritos_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX inclui_favoritos_fk ON favoritos USING btree (id_utilizador);


--
-- TOC entry 1993 (class 1259 OID 74372)
-- Name: inclui_ideias_favoritas_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX inclui_ideias_favoritas_fk ON favoritos USING btree (id_ideia);


--
-- TOC entry 1977 (class 1259 OID 74228)
-- Name: inclui_ideias_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX inclui_ideias_fk ON topico_ideia USING btree (id_ideia);


--
-- TOC entry 1978 (class 1259 OID 74227)
-- Name: inclui_topicos_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX inclui_topicos_fk ON topico_ideia USING btree (id_topico);


--
-- TOC entry 1968 (class 1259 OID 74215)
-- Name: pertence_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX pertence_fk ON accao USING btree (id_ideia);


--
-- TOC entry 1971 (class 1259 OID 74214)
-- Name: pode_comprar_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX pode_comprar_fk ON accao USING btree (id_utilizador);


--
-- TOC entry 1984 (class 1259 OID 74239)
-- Name: pode_ser_feita_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX pode_ser_feita_fk ON transacao USING btree (id_ideia);


--
-- TOC entry 1967 (class 1259 OID 74205)
-- Name: relationship_7_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX relationship_7_fk ON ideias USING btree (id_utilizador);


--
-- TOC entry 1999 (class 1259 OID 74378)
-- Name: utilizador_segue_ideias_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX utilizador_segue_ideias_fk ON seguidores USING btree (id_utilizador);


--
-- TOC entry 1985 (class 1259 OID 74237)
-- Name: venda_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX venda_fk ON transacao USING btree (id_utilizador);


--
-- TOC entry 2009 (class 2606 OID 74380)
-- Name: fk_favorito_inclui_fa_utilizad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY favoritos
    ADD CONSTRAINT fk_favorito_inclui_fa_utilizad FOREIGN KEY (id_utilizador) REFERENCES utilizador(id_utilizador);


--
-- TOC entry 2010 (class 2606 OID 74385)
-- Name: fk_favorito_inclui_id_ideias; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY favoritos
    ADD CONSTRAINT fk_favorito_inclui_id_ideias FOREIGN KEY (id_ideia) REFERENCES ideias(id_ideia);


--
-- TOC entry 2000 (class 2606 OID 74248)
-- Name: fk_ideias_relations_utilizad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ideias
    ADD CONSTRAINT fk_ideias_relations_utilizad FOREIGN KEY (id_utilizador) REFERENCES utilizador(id_utilizador);


--
-- TOC entry 2011 (class 2606 OID 74390)
-- Name: fk_seguidor_ideias_se_ideias; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY seguidores
    ADD CONSTRAINT fk_seguidor_ideias_se_ideias FOREIGN KEY (id_ideia) REFERENCES ideias(id_ideia);


--
-- TOC entry 2012 (class 2606 OID 74395)
-- Name: fk_seguidor_utilizado_utilizad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY seguidores
    ADD CONSTRAINT fk_seguidor_utilizado_utilizad FOREIGN KEY (id_utilizador) REFERENCES utilizador(id_utilizador);


--
-- TOC entry 2001 (class 2606 OID 74253)
-- Name: fk_share_pertence_ideias; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY accao
    ADD CONSTRAINT fk_share_pertence_ideias FOREIGN KEY (id_ideia) REFERENCES ideias(id_ideia);


--
-- TOC entry 2002 (class 2606 OID 74258)
-- Name: fk_share_pode_comp_utilizad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY accao
    ADD CONSTRAINT fk_share_pode_comp_utilizad FOREIGN KEY (id_utilizador) REFERENCES utilizador(id_utilizador);


--
-- TOC entry 2003 (class 2606 OID 74263)
-- Name: fk_topico_cria_utilizad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY topico
    ADD CONSTRAINT fk_topico_cria_utilizad FOREIGN KEY (id_utilizador) REFERENCES utilizador(id_utilizador);


--
-- TOC entry 2004 (class 2606 OID 74268)
-- Name: fk_topico_i_inclui_id_ideias; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY topico_ideia
    ADD CONSTRAINT fk_topico_i_inclui_id_ideias FOREIGN KEY (id_ideia) REFERENCES ideias(id_ideia);


--
-- TOC entry 2005 (class 2606 OID 74273)
-- Name: fk_topico_i_inclui_to_topico; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY topico_ideia
    ADD CONSTRAINT fk_topico_i_inclui_to_topico FOREIGN KEY (id_topico) REFERENCES topico(id_topico);


--
-- TOC entry 2006 (class 2606 OID 74278)
-- Name: fk_transaca_compra_utilizad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transacao
    ADD CONSTRAINT fk_transaca_compra_utilizad FOREIGN KEY (uti_id_utilizador) REFERENCES utilizador(id_utilizador);


--
-- TOC entry 2007 (class 2606 OID 74283)
-- Name: fk_transaca_pode_ser__ideias; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transacao
    ADD CONSTRAINT fk_transaca_pode_ser__ideias FOREIGN KEY (id_ideia) REFERENCES ideias(id_ideia);


--
-- TOC entry 2008 (class 2606 OID 74288)
-- Name: fk_transaca_venda_utilizad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transacao
    ADD CONSTRAINT fk_transaca_venda_utilizad FOREIGN KEY (id_utilizador) REFERENCES utilizador(id_utilizador);


--
-- TOC entry 2019 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2013-11-29 15:48:55

--
-- PostgreSQL database dump complete
--

