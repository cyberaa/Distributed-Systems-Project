/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     29-11-2013 01:58:23                          */
/*==============================================================*/


alter table FAVORITOS
   drop constraint FK_FAVORITO_INCLUI_FA_UTILIZAD;

alter table FAVORITOS
   drop constraint FK_FAVORITO_INCLUI_ID_IDEIAS;

alter table IDEIAS
   drop constraint FK_IDEIAS_RELATIONS_UTILIZAD;

alter table SEGUIDORES
   drop constraint FK_SEGUIDOR_IDEIAS_SE_IDEIAS;

alter table SEGUIDORES
   drop constraint FK_SEGUIDOR_UTILIZADO_UTILIZAD;

alter table "SHARE"
   drop constraint FK_SHARE_PERTENCE_IDEIAS;

alter table "SHARE"
   drop constraint FK_SHARE_PODE_COMP_UTILIZAD;

alter table TOPICO
   drop constraint FK_TOPICO_CRIA_UTILIZAD;

alter table TOPICO_IDEIA
   drop constraint FK_TOPICO_I_INCLUI_ID_IDEIAS;

alter table TOPICO_IDEIA
   drop constraint FK_TOPICO_I_INCLUI_TO_TOPICO;

alter table TRANSACAO
   drop constraint FK_TRANSACA_COMPRA_UTILIZAD;

alter table TRANSACAO
   drop constraint FK_TRANSACA_PODE_SER__IDEIAS;

alter table TRANSACAO
   drop constraint FK_TRANSACA_VENDA_UTILIZAD;

drop index INCLUI_IDEIAS_FAVORITAS_FK;

drop index INCLUI_FAVORITOS_FK;

drop table FAVORITOS cascade constraints;

drop index RELATIONSHIP_7_FK;

drop table IDEIAS cascade constraints;

drop index IDEIAS_SEGUIDAS_FK;

drop index UTILIZADOR_SEGUE_IDEIAS_FK;

drop table SEGUIDORES cascade constraints;

drop index PERTENCE_FK;

drop index PODE_COMPRAR_FK;

drop table "SHARE" cascade constraints;

drop index CRIA_FK;

drop table TOPICO cascade constraints;

drop index INCLUI_IDEIAS_FK;

drop index INCLUI_TOPICOS_FK;

drop table TOPICO_IDEIA cascade constraints;

drop index PODE_SER_FEITA_FK;

drop index COMPRA_FK;

drop index VENDA_FK;

drop table TRANSACAO cascade constraints;

drop table UTILIZADOR cascade constraints;

/*==============================================================*/
/* Table: FAVORITOS                                             */
/*==============================================================*/
create table FAVORITOS 
(
   ID_IDEIA             INTEGER              not null,
   ID_UTILIZADOR        INTEGER              not null,
   constraint PK_FAVORITOS primary key (ID_IDEIA, ID_UTILIZADOR)
);

/*==============================================================*/
/* Index: INCLUI_FAVORITOS_FK                                   */
/*==============================================================*/
create index INCLUI_FAVORITOS_FK on FAVORITOS (
   ID_UTILIZADOR ASC
);

/*==============================================================*/
/* Index: INCLUI_IDEIAS_FAVORITAS_FK                            */
/*==============================================================*/
create index INCLUI_IDEIAS_FAVORITAS_FK on FAVORITOS (
   ID_IDEIA ASC
);

/*==============================================================*/
/* Table: IDEIAS                                                */
/*==============================================================*/
create table IDEIAS 
(
   ID_IDEIA             INTEGER              not null,
   ID_UTILIZADOR        INTEGER              not null,
   NOME                 VARCHAR2(60)         not null,
   DATA__CRIACAO        DATE                 not null,
   VENDA_ATOMATICA      INTEGER              not null,
   constraint PK_IDEIAS primary key (ID_IDEIA)
);

/*==============================================================*/
/* Index: RELATIONSHIP_7_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_7_FK on IDEIAS (
   ID_UTILIZADOR ASC
);

/*==============================================================*/
/* Table: SEGUIDORES                                            */
/*==============================================================*/
create table SEGUIDORES 
(
   ID_IDEIA             INTEGER              not null,
   ID_UTILIZADOR        INTEGER              not null,
   constraint PK_SEGUIDORES primary key (ID_IDEIA, ID_UTILIZADOR)
);

/*==============================================================*/
/* Index: UTILIZADOR_SEGUE_IDEIAS_FK                            */
/*==============================================================*/
create index UTILIZADOR_SEGUE_IDEIAS_FK on SEGUIDORES (
   ID_UTILIZADOR ASC
);

/*==============================================================*/
/* Index: IDEIAS_SEGUIDAS_FK                                    */
/*==============================================================*/
create index IDEIAS_SEGUIDAS_FK on SEGUIDORES (
   ID_IDEIA ASC
);

/*==============================================================*/
/* Table: "SHARE"                                               */
/*==============================================================*/
create table "SHARE" 
(
   ID_UTILIZADOR        INTEGER              not null,
   ID_IDEIA             INTEGER              not null,
   NUM_SHARES           NUMBER               not null,
   VALOR                NUMBER               not null,
   constraint PK_SHARE primary key (ID_UTILIZADOR, ID_IDEIA)
);

/*==============================================================*/
/* Index: PODE_COMPRAR_FK                                       */
/*==============================================================*/
create index PODE_COMPRAR_FK on "SHARE" (
   ID_UTILIZADOR ASC
);

/*==============================================================*/
/* Index: PERTENCE_FK                                           */
/*==============================================================*/
create index PERTENCE_FK on "SHARE" (
   ID_IDEIA ASC
);

/*==============================================================*/
/* Table: TOPICO                                                */
/*==============================================================*/
create table TOPICO 
(
   ID_TOPICO            INTEGER              not null,
   ID_UTILIZADOR        INTEGER              not null,
   NOME                 VARCHAR2(20)         not null,
   HASHTAG              VARCHAR2(20)         not null,
   DATA_CRIACAO         DATE                 not null,
   constraint PK_TOPICO primary key (ID_TOPICO)
);

/*==============================================================*/
/* Index: CRIA_FK                                               */
/*==============================================================*/
create index CRIA_FK on TOPICO (
   ID_UTILIZADOR ASC
);

/*==============================================================*/
/* Table: TOPICO_IDEIA                                          */
/*==============================================================*/
create table TOPICO_IDEIA 
(
   ID_TOPICO            INTEGER              not null,
   ID_IDEIA             INTEGER              not null,
   constraint PK_TOPICO_IDEIA primary key (ID_TOPICO, ID_IDEIA)
);

/*==============================================================*/
/* Index: INCLUI_TOPICOS_FK                                     */
/*==============================================================*/
create index INCLUI_TOPICOS_FK on TOPICO_IDEIA (
   ID_TOPICO ASC
);

/*==============================================================*/
/* Index: INCLUI_IDEIAS_FK                                      */
/*==============================================================*/
create index INCLUI_IDEIAS_FK on TOPICO_IDEIA (
   ID_IDEIA ASC
);

/*==============================================================*/
/* Table: TRANSACAO                                             */
/*==============================================================*/
create table TRANSACAO 
(
   ID_UTILIZADOR        INTEGER              not null,
   UTI_ID_UTILIZADOR    INTEGER              not null,
   ID_IDEIA             INTEGER              not null,
   N_SHARES             INTEGER              not null,
   VALOR_COMPRA         NUMBER               not null,
   ID_TRANSACCAO        INTEGER              not null,
   DATA_TRANSACCAO      DATE                 not null,
   constraint PK_TRANSACAO primary key (ID_TRANSACCAO)
);

/*==============================================================*/
/* Index: VENDA_FK                                              */
/*==============================================================*/
create index VENDA_FK on TRANSACAO (
   ID_UTILIZADOR ASC
);

/*==============================================================*/
/* Index: COMPRA_FK                                             */
/*==============================================================*/
create index COMPRA_FK on TRANSACAO (
   UTI_ID_UTILIZADOR ASC
);

/*==============================================================*/
/* Index: PODE_SER_FEITA_FK                                     */
/*==============================================================*/
create index PODE_SER_FEITA_FK on TRANSACAO (
   ID_IDEIA ASC
);

/*==============================================================*/
/* Table: UTILIZADOR                                            */
/*==============================================================*/
create table UTILIZADOR 
(
   ID_UTILIZADOR        INTEGER              not null,
   NOME                 VARCHAR2(60)         not null,
   LOGIN                VARCHAR2(45)         not null,
   PASS                 VARCHAR2(60)         not null,
   PAIS                 VARCHAR2(50),
   DEICOINS             NUMBER               not null,
   EMAIL                VARCHAR2(60),
   IDADE                INTEGER,
   constraint PK_UTILIZADOR primary key (ID_UTILIZADOR)
);

alter table FAVORITOS
   add constraint FK_FAVORITO_INCLUI_FA_UTILIZAD foreign key (ID_UTILIZADOR)
      references UTILIZADOR (ID_UTILIZADOR);

alter table FAVORITOS
   add constraint FK_FAVORITO_INCLUI_ID_IDEIAS foreign key (ID_IDEIA)
      references IDEIAS (ID_IDEIA);

alter table IDEIAS
   add constraint FK_IDEIAS_RELATIONS_UTILIZAD foreign key (ID_UTILIZADOR)
      references UTILIZADOR (ID_UTILIZADOR);

alter table SEGUIDORES
   add constraint FK_SEGUIDOR_IDEIAS_SE_IDEIAS foreign key (ID_IDEIA)
      references IDEIAS (ID_IDEIA);

alter table SEGUIDORES
   add constraint FK_SEGUIDOR_UTILIZADO_UTILIZAD foreign key (ID_UTILIZADOR)
      references UTILIZADOR (ID_UTILIZADOR);

alter table "SHARE"
   add constraint FK_SHARE_PERTENCE_IDEIAS foreign key (ID_IDEIA)
      references IDEIAS (ID_IDEIA);

alter table "SHARE"
   add constraint FK_SHARE_PODE_COMP_UTILIZAD foreign key (ID_UTILIZADOR)
      references UTILIZADOR (ID_UTILIZADOR);

alter table TOPICO
   add constraint FK_TOPICO_CRIA_UTILIZAD foreign key (ID_UTILIZADOR)
      references UTILIZADOR (ID_UTILIZADOR);

alter table TOPICO_IDEIA
   add constraint FK_TOPICO_I_INCLUI_ID_IDEIAS foreign key (ID_IDEIA)
      references IDEIAS (ID_IDEIA);

alter table TOPICO_IDEIA
   add constraint FK_TOPICO_I_INCLUI_TO_TOPICO foreign key (ID_TOPICO)
      references TOPICO (ID_TOPICO);

alter table TRANSACAO
   add constraint FK_TRANSACA_COMPRA_UTILIZAD foreign key (UTI_ID_UTILIZADOR)
      references UTILIZADOR (ID_UTILIZADOR);

alter table TRANSACAO
   add constraint FK_TRANSACA_PODE_SER__IDEIAS foreign key (ID_IDEIA)
      references IDEIAS (ID_IDEIA);

alter table TRANSACAO
   add constraint FK_TRANSACA_VENDA_UTILIZAD foreign key (ID_UTILIZADOR)
      references UTILIZADOR (ID_UTILIZADOR);

