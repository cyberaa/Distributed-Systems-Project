-- Table: ideias

-- DROP TABLE ideias;

CREATE TABLE ideias
(
  id_ideia integer NOT NULL,
  id_utilizador integer NOT NULL,
  nome character varying(60) NOT NULL,
  venda_atomatica integer NOT NULL,
  descricao character varying(500),
  data_criacao timestamp without time zone DEFAULT now(),
  imgname text,
  imgoid oid,
  CONSTRAINT pk_ideias PRIMARY KEY (id_ideia),
  CONSTRAINT fk_ideias_relations_utilizad FOREIGN KEY (id_utilizador)
      REFERENCES utilizador (id_utilizador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE ideias
  OWNER TO postgres;

-- Index: relationship_7_fk

-- DROP INDEX relationship_7_fk;

CREATE INDEX relationship_7_fk
  ON ideias
  USING btree
  (id_utilizador);



  -- Table: accao

-- DROP TABLE accao;

CREATE TABLE accao
(
  id_utilizador integer NOT NULL,
  id_ideia integer NOT NULL,
  num_shares numeric NOT NULL,
  valor numeric(9,2) NOT NULL,
  estado character varying(10) NOT NULL DEFAULT 'INACTIVO'::character varying,
  CONSTRAINT pk_share PRIMARY KEY (id_utilizador, id_ideia),
  CONSTRAINT fk_share_pertence_ideias FOREIGN KEY (id_ideia)
      REFERENCES ideias (id_ideia) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_share_pode_comp_utilizad FOREIGN KEY (id_utilizador)
      REFERENCES utilizador (id_utilizador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ck_valor CHECK (valor >= 0::numeric)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE accao
  OWNER TO postgres;

-- Index: pertence_fk

-- DROP INDEX pertence_fk;

CREATE INDEX pertence_fk
  ON accao
  USING btree
  (id_ideia);

-- Index: pode_comprar_fk

-- DROP INDEX pode_comprar_fk;

CREATE INDEX pode_comprar_fk
  ON accao
  USING btree
  (id_utilizador);

-- Table: favoritos

-- DROP TABLE favoritos;

CREATE TABLE favoritos
(
  id_ideia integer NOT NULL,
  id_utilizador integer NOT NULL,
  CONSTRAINT pk_favoritos PRIMARY KEY (id_ideia, id_utilizador),
  CONSTRAINT fk_favorito_inclui_fa_utilizad FOREIGN KEY (id_utilizador)
      REFERENCES utilizador (id_utilizador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_favorito_inclui_id_ideias FOREIGN KEY (id_ideia)
      REFERENCES ideias (id_ideia) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE favoritos
  OWNER TO postgres;

-- Index: inclui_favoritos_fk

-- DROP INDEX inclui_favoritos_fk;

CREATE INDEX inclui_favoritos_fk
  ON favoritos
  USING btree
  (id_utilizador);

-- Index: inclui_ideias_favoritas_fk

-- DROP INDEX inclui_ideias_favoritas_fk;

CREATE INDEX inclui_ideias_favoritas_fk
  ON favoritos
  USING btree
  (id_ideia);

-- Table: seguidores

-- DROP TABLE seguidores;

CREATE TABLE seguidores
(
  id_ideia integer NOT NULL,
  id_utilizador integer NOT NULL,
  CONSTRAINT pk_seguidores PRIMARY KEY (id_ideia, id_utilizador),
  CONSTRAINT fk_seguidor_ideias_se_ideias FOREIGN KEY (id_ideia)
      REFERENCES ideias (id_ideia) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_seguidor_utilizado_utilizad FOREIGN KEY (id_utilizador)
      REFERENCES utilizador (id_utilizador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE seguidores
  OWNER TO postgres;

-- Index: ideias_seguidas_fk

-- DROP INDEX ideias_seguidas_fk;

CREATE INDEX ideias_seguidas_fk
  ON seguidores
  USING btree
  (id_ideia);

-- Index: utilizador_segue_ideias_fk

-- DROP INDEX utilizador_segue_ideias_fk;

CREATE INDEX utilizador_segue_ideias_fk
  ON seguidores
  USING btree
  (id_utilizador);

-- Table: topico

-- DROP TABLE topico;

CREATE TABLE topico
(
  id_topico integer NOT NULL DEFAULT nextval('seq_topico_id'::regclass),
  id_utilizador integer NOT NULL,
  nome character varying(20) NOT NULL,
  hashtag character varying(20) NOT NULL,
  data_criacao timestamp without time zone NOT NULL DEFAULT now(),
  CONSTRAINT pk_topico PRIMARY KEY (id_topico),
  CONSTRAINT fk_topico_cria_utilizad FOREIGN KEY (id_utilizador)
      REFERENCES utilizador (id_utilizador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT nome_unico UNIQUE (nome)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE topico
  OWNER TO postgres;

-- Index: cria_fk

-- DROP INDEX cria_fk;

CREATE INDEX cria_fk
  ON topico
  USING btree
  (id_utilizador);


-- Trigger: topico_verify_params on topico

-- DROP TRIGGER topico_verify_params ON topico;

CREATE TRIGGER topico_verify_params
  BEFORE INSERT OR UPDATE
  ON topico
  FOR EACH ROW
  EXECUTE PROCEDURE topico_verify_params();

-- Table: topico_ideia

-- DROP TABLE topico_ideia;

CREATE TABLE topico_ideia
(
  id_topico integer NOT NULL,
  id_ideia integer NOT NULL,
  CONSTRAINT pk_topico_ideia PRIMARY KEY (id_topico, id_ideia),
  CONSTRAINT fk_topico_i_inclui_id_ideias FOREIGN KEY (id_ideia)
      REFERENCES ideias (id_ideia) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_topico_i_inclui_to_topico FOREIGN KEY (id_topico)
      REFERENCES topico (id_topico) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE topico_ideia
  OWNER TO postgres;

-- Index: inclui_ideias_fk

-- DROP INDEX inclui_ideias_fk;

CREATE INDEX inclui_ideias_fk
  ON topico_ideia
  USING btree
  (id_ideia);

-- Index: inclui_topicos_fk

-- DROP INDEX inclui_topicos_fk;

CREATE INDEX inclui_topicos_fk
  ON topico_ideia
  USING btree
  (id_topico);

-- Table: transacao

-- DROP TABLE transacao;

CREATE TABLE transacao
(
  id_utilizador integer NOT NULL,
  uti_id_utilizador integer NOT NULL,
  id_ideia integer NOT NULL,
  n_shares integer NOT NULL,
  valor_compra numeric NOT NULL,
  id_transaccao integer NOT NULL DEFAULT nextval('seq_transaccao_id'::regclass),
  data_transaccao date NOT NULL DEFAULT now(),
  CONSTRAINT pk_transacao PRIMARY KEY (id_transaccao),
  CONSTRAINT fk_transaca_compra_utilizad FOREIGN KEY (uti_id_utilizador)
      REFERENCES utilizador (id_utilizador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_transaca_pode_ser__ideias FOREIGN KEY (id_ideia)
      REFERENCES ideias (id_ideia) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_transaca_venda_utilizad FOREIGN KEY (id_utilizador)
      REFERENCES utilizador (id_utilizador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE transacao
  OWNER TO postgres;

-- Index: compra_fk

-- DROP INDEX compra_fk;

CREATE INDEX compra_fk
  ON transacao
  USING btree
  (uti_id_utilizador);

-- Index: pode_ser_feita_fk

-- DROP INDEX pode_ser_feita_fk;

CREATE INDEX pode_ser_feita_fk
  ON transacao
  USING btree
  (id_ideia);

-- Index: venda_fk

-- DROP INDEX venda_fk;

CREATE INDEX venda_fk
  ON transacao
  USING btree
  (id_utilizador);

-- Table: utilizador

-- DROP TABLE utilizador;

CREATE TABLE utilizador
(
  id_utilizador integer NOT NULL DEFAULT nextval('seq_utilizador_id'::regclass),
  nome character varying(60) NOT NULL,
  login character varying(45) NOT NULL,
  pass character varying(60) NOT NULL,
  pais character varying(50),
  deicoins numeric NOT NULL DEFAULT 1000000,
  email character varying(60),
  idade integer,
  status character varying(5) NOT NULL DEFAULT false,
  CONSTRAINT pk_utilizador PRIMARY KEY (id_utilizador),
  CONSTRAINT email_unico UNIQUE (email),
  CONSTRAINT login_unico UNIQUE (login)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE utilizador
  OWNER TO postgres;

-- Trigger: delete_accao_owner on utilizador

-- DROP TRIGGER delete_accao_owner ON utilizador;

CREATE TRIGGER delete_accao_owner
  BEFORE DELETE
  ON utilizador
  FOR EACH ROW
  EXECUTE PROCEDURE delete_accao_owner();

-- Trigger: delete_fav_owner on utilizador

-- DROP TRIGGER delete_fav_owner ON utilizador;

CREATE TRIGGER delete_fav_owner
  BEFORE DELETE
  ON utilizador
  FOR EACH ROW
  EXECUTE PROCEDURE delete_fav_owner();

-- Trigger: user_stamp on utilizador

-- DROP TRIGGER user_stamp ON utilizador;

CREATE TRIGGER user_stamp
  BEFORE INSERT OR UPDATE
  ON utilizador
  FOR EACH ROW
  EXECUTE PROCEDURE user_stamp();



