--liquibase formatted sql


----------------------------------------------------------------------------------------------------
--changeset rcastro:create_sequences
----------------------------------------------------------------------------------------------------
CREATE SEQUENCE PRODUCT_ID_SEQ                    START WITH 1 MINVALUE 1 NOMAXVALUE NOCYCLE CACHE 10 ORDER;

----------------------------------------------------------------------------------------------------
--changeset rcastro:create_product_table
----------------------------------------------------------------------------------------------------
CREATE TABLE PRODUCTS (
    SKU                 NUMBER(38) PRIMARY KEY,
    NAME                VARCHAR2(255) NOT NULL,
    PRICE               DOUBLE NOT NULL,
    CREATION_DATE       DATE NOT NULL,
    IS_ACTIVE           BOOLEAN
);
--
----------------------------------------------------------------------------------------------------

