DROP SEQUENCE if exists seq_utilizador_id;
DROP SEQUENCE if exists seq_ideia_id;
DROP SEQUENCE if exists seq_topico_id;
DROP SEQUENCE if exists seq_transaccao_id;


CREATE SEQUENCE seq_utilizador_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_ideia_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_topico_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_transaccao_id START WITH 1 INCREMENT BY 1;
