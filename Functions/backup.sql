
-- Function: exists_topico(character varying)

-- DROP FUNCTION exists_topico(character varying);

CREATE OR REPLACE FUNCTION exists_accao(id_user integer,id_ideia integer)
  RETURNS boolean AS
$BODY$DECLARE passed BOOLEAN;

begin 
        SELECT  (topiconome = $1) INTO passed
        FROM    accao
        WHERE   id_user= $1 and id_idea = $2;

        RETURN passed;

end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION exists_accao(id_user integer,id_ideia integer)
  OWNER TO postgres;

-- Function: "Delete_ideia"(character varying)

-- DROP FUNCTION "Delete_ideia"(character varying);

CREATE OR REPLACE FUNCTION "Delete_ideia"(username character varying)
  RETURNS void AS
$BODY$BEGIN 

if username is not null then
  delete from "ideias" where username = "nome";
  end if;

end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION "Delete_ideia"(character varying)
  OWNER TO postgres;

-- Function: "Delete_topico"(character varying)

-- DROP FUNCTION "Delete_topico"(character varying);

CREATE OR REPLACE FUNCTION "Delete_topico"(username character varying)
  RETURNS void AS
$BODY$BEGIN 

if username is not null then
  delete from "topico" where username = "nome";
  end if;

end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION "Delete_topico"(character varying)
  OWNER TO postgres;


-- Function: "Delete_user"(character varying)

-- DROP FUNCTION "Delete_user"(character varying);

CREATE OR REPLACE FUNCTION "Delete_user"(username character varying)
  RETURNS void AS
$BODY$BEGIN 

if username is not null then
  delete from "utilizador" where username = "login";
  end if;

end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION "Delete_user"(character varying)
  OWNER TO postgres;

-- Function: exists_ideia(character varying)

-- DROP FUNCTION exists_ideia(character varying);

CREATE OR REPLACE FUNCTION exists_ideia(username character varying)
  RETURNS boolean AS
$BODY$DECLARE passed BOOLEAN;

begin 
        SELECT  (ideiasnome = $1) INTO passed
        FROM    ideias
        WHERE   ideias.nome = $1;

        RETURN passed;

end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION exists_ideia(character varying)
  OWNER TO postgres;

-- Function: exists_topico(character varying)

-- DROP FUNCTION exists_topico(character varying);

CREATE OR REPLACE FUNCTION exists_topico(username character varying)
  RETURNS boolean AS
$BODY$DECLARE passed BOOLEAN;

begin 
        SELECT  (topiconome = $1) INTO passed
        FROM    topico
        WHERE   topico.nome = $1;

        RETURN passed;

end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION exists_topico(character varying)
  OWNER TO postgres;


-- Function: exists_user(character varying)

-- DROP FUNCTION exists_user(character varying);

CREATE OR REPLACE FUNCTION exists_user(username character varying)
  RETURNS boolean AS
$BODY$DECLARE passed BOOLEAN;

begin 
        SELECT  (login = $1) INTO passed
        FROM    utilizador
        WHERE   utilizador.login = $1;

        RETURN passed;

end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION exists_user(character varying)
  OWNER TO postgres;
















-- Function: check_password(character varying, character varying)

-- DROP FUNCTION check_password(character varying, character varying);

CREATE OR REPLACE FUNCTION check_password(login character varying, password character varying)
  RETURNS boolean AS
$BODY$DECLARE passed BOOLEAN;

begin 
        SELECT  (pass = $2) INTO passed
        FROM    utilizador
        WHERE   utilizador.login = $1;

        RETURN passed;

end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION check_password(character varying, character varying)
  OWNER TO postgres;


-- Function: delete_accao_owner()

-- DROP FUNCTION delete_accao_owner();

CREATE OR REPLACE FUNCTION delete_accao_owner()
  RETURNS trigger AS
$BODY$
BEGIN
 DELETE FROM accao WHERE accao.id_utilizador = OLD.id_utilizador;
 RETURN OLD;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION delete_accao_owner()
  OWNER TO postgres;


-- Trigger: delete_ideia on ideias

-- DROP TRIGGER delete_ideia ON ideias;

CREATE TRIGGER delete_ideia
  BEFORE DELETE
  ON ideias
  FOR EACH ROW
  EXECUTE PROCEDURE delete_ideia();



-- Trigger: topico_verify_params on topico

-- DROP TRIGGER topico_verify_params ON topico;

CREATE TRIGGER topico_verify_params
  BEFORE INSERT OR UPDATE
  ON topico
  FOR EACH ROW
  EXECUTE PROCEDURE topico_verify_params();

-- Function: delete_ideia()

-- DROP FUNCTION delete_ideia();

CREATE OR REPLACE FUNCTION delete_ideia()
  RETURNS trigger AS
$BODY$
BEGIN
 DELETE FROM topico WHERE topico.id_ideia = OLD.id_ideia;
 RETURN OLD;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION delete_ideia()
  OWNER TO postgres;


-- Function: topico_verify_params()

-- DROP FUNCTION topico_verify_params();

CREATE OR REPLACE FUNCTION topico_verify_params()
  RETURNS trigger AS
$BODY$
    BEGIN
        -- Check that empname and salary are given
        IF NEW.id_utilizador IS NULL THEN
            RAISE EXCEPTION 'id_utilizador cannot be null';
        END IF;
        IF NEW.id_topico is NULL THEN
	    RAISE EXCEPTION 'id_topico cannot be null';
	END IF;
        IF NEW.nome IS NULL THEN
            RAISE EXCEPTION 'nome cannot be null ';
        END IF;
        IF NEW.hashtag IS NULL THEN
	    RAISE EXCEPTION 'hashtag cannot be null';
	END IF;
	IF NEW.data_criacao IS NULL THEN
	    RAISE EXCEPTION ' data_criacao cannot be null';
	END IF;

        RETURN NEW;
    END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION topico_verify_params()
  OWNER TO postgres;

  -- Function: ideia_verify_params()

-- DROP FUNCTION ideia_verify_params();

CREATE OR REPLACE FUNCTION ideia_verify_params()
  RETURNS trigger AS
$BODY$
    BEGIN
        -- Check that empname and salary are given
        IF NEW.nome IS NULL THEN
            RAISE EXCEPTION 'nome cannot be null';
        END IF;
        IF NEW.id_ideia is NULL THEN
	    RAISE EXCEPTION 'id_ideia cannot be null';
	END IF;
        IF NEW.id_utilizador IS NULL THEN
            RAISE EXCEPTION 'id_user cannot be null ';
        END IF;
        IF NEW.venda_automatica IS NULL THEN
	    RAISE EXCEPTION 'venda_automatica cannot be null';
	END IF;

        RETURN NEW;
    END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION ideia_verify_params()
  OWNER TO postgres;


-- Function: user_stamp()

-- DROP FUNCTION user_stamp();

CREATE OR REPLACE FUNCTION user_stamp()
  RETURNS trigger AS
$BODY$
    BEGIN
        -- Check that empname and salary are given
        IF NEW.nome IS NULL THEN
            RAISE EXCEPTION 'nome cannot be null';
        END IF;
        IF NEW.pass is NULL THEN
	    RAISE EXCEPTION 'pass cannot be null';
	END IF;
        IF NEW.login IS NULL THEN
            RAISE EXCEPTION 'login cannot be null ';
        END IF;

        RETURN NEW;
    END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION user_stamp()
  OWNER TO postgres;


-- Function: validadeicoins()

-- DROP FUNCTION validadeicoins();

CREATE OR REPLACE FUNCTION validadeicoins()
  RETURNS trigger AS
$BODY$
BEGIN
	UPDATE utilizador
	SET deicoins = 1000000
	WHERE id_utilizador = NEW.id_utilizador;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION validadeicoins()
  OWNER TO postgres;

-- Function: accao_verify_params()

-- DROP FUNCTION accao_verify_params();

CREATE OR REPLACE FUNCTION accao_verify_params()
  RETURNS trigger AS
$BODY$
    BEGIN
        -- Check that empname and salary are given
        IF NEW.num_shares IS NULL THEN
            RAISE EXCEPTION 'num_shares cannot be null';
        END IF;
        IF NEW.id_ideia is NULL THEN
	    RAISE EXCEPTION 'id_ideia cannot be null';
	END IF;
        IF NEW.id_utilizador IS NULL THEN
            RAISE EXCEPTION 'id_user cannot be null ';
        END IF;
        IF NEW.valor IS NULL THEN
	    RAISE EXCEPTION 'valor cannot be null';
	END IF;
	IF NEW.estado IS NULL THEN
	    RAISE EXCEPTION 'estado cannot be null';
	END IF;

        RETURN NEW;
    END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION accao_verify_params()
  OWNER TO postgres;


-- Sequence: seq_ideia_id

-- DROP SEQUENCE seq_ideia_id;

CREATE SEQUENCE seq_ideia_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_ideia_id
  OWNER TO postgres;


- Sequence: seq_topico_id

-- DROP SEQUENCE seq_topico_id;

CREATE SEQUENCE seq_topico_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_topico_id
  OWNER TO postgres;

- Sequence: seq_transaccao_id

-- DROP SEQUENCE seq_transaccao_id;

CREATE SEQUENCE seq_transaccao_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_transaccao_id
  OWNER TO postgres;


-- Sequence: seq_utilizador_id

-- DROP SEQUENCE seq_utilizador_id;

CREATE SEQUENCE seq_utilizador_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_utilizador_id
  OWNER TO postgres;





-- Trigger: delete_ideia on ideias

-- DROP TRIGGER delete_ideia ON ideias;

CREATE TRIGGER delete_ideia
  BEFORE DELETE
  ON ideias
  FOR EACH ROW
  EXECUTE PROCEDURE delete_ideia();




-- Trigger: delete_accao_owner on utilizador

-- DROP TRIGGER delete_accao_owner ON utilizador;

CREATE TRIGGER delete_accao_owner
  BEFORE DELETE
  ON utilizador
  FOR EACH ROW
  EXECUTE PROCEDURE delete_accao_owner();

-- Trigger: user_stamp on utilizador

-- DROP TRIGGER user_stamp ON utilizador;

CREATE TRIGGER user_stamp
  BEFORE INSERT OR UPDATE
  ON utilizador
  FOR EACH ROW
  EXECUTE PROCEDURE user_stamp();


 -- Function: add_ideia(integer, integer, character varying, integer, character varying, timestamp without time zone, text)

-- DROP FUNCTION add_ideia(integer, integer, character varying, integer, character varying, timestamp without time zone, text);

CREATE OR REPLACE FUNCTION add_ideia(id_ideia integer, id_utilizador integer, nome character varying, venda_atomatica integer, descricao character varying, data_criacao timestamp without time zone, imgname text)
  RETURNS void AS
$BODY$begin
	

	Insert into "ideias" VALUES(nextval('seq_ideia_id'::regclass),id_utilizador,nome,venda_atomatica,descricao,data_criacao,imgname);
	
end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION add_ideia(integer, integer, character varying, integer, character varying, timestamp without time zone, text)
  OWNER TO postgres;

-- Function: add_ideia_imagem(integer, integer, character varying, integer, character varying, timestamp without time zone, text, oid)

-- DROP FUNCTION add_ideia_imagem(integer, integer, character varying, integer, character varying, timestamp without time zone, text, oid);

CREATE OR REPLACE FUNCTION add_ideia_imagem(id_ideia integer, id_utilizador integer, nome character varying, venda_atomatica integer, descricao character varying, data_criacao timestamp without time zone, imgname text, imgoid oid)
  RETURNS void AS
$BODY$begin
	

	Insert into "ideias" VALUES(nextval('seq_ideia_id'::regclass),id_utilizador,nome,venda_atomatica,descricao,data_criacao,imgname,imgoid);
	
end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION add_ideia_imagem(integer, integer, character varying, integer, character varying, timestamp without time zone, text, oid)
  OWNER TO postgres;


-- Function: add_topico(integer, character varying, character varying, timestamp without time zone)

-- DROP FUNCTION add_topico(integer, character varying, character varying, timestamp without time zone);

CREATE OR REPLACE FUNCTION add_topico(id_utilizador integer, nome character varying, hashtag character varying, data_criacao timestamp without time zone)
  RETURNS void AS
$BODY$begin
	

	Insert into "topico" VALUES(nextval('seq_topico_id'::regclass),id_utilizador,nome,hashtag,descricao,data_criacao);
	
end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION add_topico(integer, character varying, character varying, timestamp without time zone)
  OWNER TO postgres;

-- Function: add_transaccao(integer, integer, integer, integer, numeric, timestamp without time zone)

-- DROP FUNCTION add_transaccao(integer, integer, integer, integer, numeric, timestamp without time zone);

CREATE OR REPLACE FUNCTION add_transaccao(id_utilizador integer, id_utilizador2 integer, id_ideia integer, n_shares integer, valor_compras numeric, data_criacao timestamp without time zone)
  RETURNS void AS
$BODY$begin
	

	Insert into "transacao" VALUES(id_utilizador,id_utilizador2,id_ideia,n_shares,valor_compras,nextval('seq_transaccao_id'::regclass),data_criacao);
	
end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION add_transaccao(integer, integer, integer, integer, numeric, timestamp without time zone)
  OWNER TO postgres;

-- Function: add_user(character varying, character varying, character varying, character varying, character varying, integer)

-- DROP FUNCTION add_user(character varying, character varying, character varying, character varying, character varying, integer);

CREATE OR REPLACE FUNCTION add_user(nome character varying, login character varying, pass character varying, pais character varying, email character varying, idade integer)
  RETURNS void AS
$BODY$Declare
	total integer;
	
begin
	

		Insert into "utilizador" VALUES(nextval('seq_utilizador_id'::regclass),nome,login,pass,pais,1000000,email,idade,false);
	
end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION add_user(character varying, character varying, character varying, character varying, character varying, integer)
  OWNER TO postgres;


-- Function: combine_ideia_topico(integer, integer)

-- DROP FUNCTION combine_ideia_topico(integer, integer);

CREATE OR REPLACE FUNCTION combine_ideia_topico(id_topico integer, id_ideia integer)
  RETURNS void AS
$BODY$begin
	

	Insert into "topico_ideia" VALUES(id_topico,id_ideia);
	
end;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION combine_ideia_topico(integer, integer)
  OWNER TO postgres;


-- Function: totalaccoes()

-- DROP FUNCTION totalaccoes();

CREATE OR REPLACE FUNCTION totalaccoes()
  RETURNS integer AS
$BODY$
declare
	total integer;
BEGIN
   SELECT count(*) into total FROM accao;
   RETURN total;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION totalaccoes()
  OWNER TO postgres;


-- Function: totalfavoritos()

-- DROP FUNCTION totalfavoritos();

CREATE OR REPLACE FUNCTION totalfavoritos()
  RETURNS integer AS
$BODY$
declare
	total integer;
BEGIN
   SELECT count(*) into total FROM favoritos;
   RETURN total;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION totalfavoritos()
  OWNER TO postgres;


-- Function: totalideias()

-- DROP FUNCTION totalideias();

CREATE OR REPLACE FUNCTION totalideias()
  RETURNS integer AS
$BODY$
declare
	total integer;
BEGIN
   SELECT count(*) into total FROM ideias;
   RETURN total;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION totalideias()
  OWNER TO postgres;


-- Function: totalseguidores()

-- DROP FUNCTION totalseguidores();

CREATE OR REPLACE FUNCTION totalseguidores()
  RETURNS integer AS
$BODY$
declare
	total integer;
BEGIN
   SELECT count(*) into total FROM seguidores;
   RETURN total;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION totalseguidores()
  OWNER TO postgres;


-- Function: totaltopicos()

-- DROP FUNCTION totaltopicos();

CREATE OR REPLACE FUNCTION totaltopicos()
  RETURNS integer AS
$BODY$
declare
	total integer;
BEGIN
   SELECT count(*) into total FROM topico;
   RETURN total;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION totaltopicos()
  OWNER TO postgres;

- Function: totaltransaccao()

-- DROP FUNCTION totaltransaccao();

CREATE OR REPLACE FUNCTION totaltransaccao()
  RETURNS integer AS
$BODY$
declare
	total integer;
BEGIN
   SELECT count(*) into total FROM transacao;
   RETURN total;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION totaltransaccao()
  OWNER TO postgres;

-- Function: totalusers()

-- DROP FUNCTION totalusers();

CREATE OR REPLACE FUNCTION totalusers()
  RETURNS integer AS
$BODY$
declare
	total integer;
BEGIN
   SELECT count(*) into total FROM utilizador;
   RETURN total;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION totalusers()
  OWNER TO postgres;

