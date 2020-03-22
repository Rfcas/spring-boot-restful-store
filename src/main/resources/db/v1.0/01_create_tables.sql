--liquibase formatted sql


----------------------------------------------------------------------------------------------------
--changeset rcastro:create_sequences
----------------------------------------------------------------------------------------------------
CREATE SEQUENCE PRODUCT_ID_SEQ                    START WITH 1 MINVALUE 1 NOMAXVALUE NOCYCLE CACHE 10 ORDER;
CREATE SEQUENCE ORDER_ID_SEQ                      START WITH 1 MINVALUE 1 NOMAXVALUE NOCYCLE CACHE 10 ORDER;

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

CREATE TABLE ORDERS (
    ID                  NUMBER(38) PRIMARY KEY,
    EMAIL               VARCHAR2(255) NOT NULL,
    CREATION_DATE       TIMESTAMP NOT NULL
);

CREATE TABLE ORDERS_PRODUCTS (
    ORDERS_ID                 NUMBER(38) NOT NULL,
    PRODUCT_SKU               NUMBER(38) NOT NULL
);

ALTER TABLE ORDERS_PRODUCTS ADD PRIMARY KEY (ORDERS_ID, PRODUCT_SKU);
ALTER TABLE ORDERS_PRODUCTS ADD FOREIGN KEY (ORDERS_ID) REFERENCES ORDERS (ID);
ALTER TABLE ORDERS_PRODUCTS ADD FOREIGN KEY (PRODUCT_SKU) REFERENCES PRODUCTS (SKU);
--
----------------------------------------------------------------------------------------------------