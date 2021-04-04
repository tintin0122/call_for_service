CREATE TABLE IF NOT EXISTS agency
(
    agency_id    VARCHAR(36) PRIMARY KEY NOT NULL,
    agency_name  VARCHAR(200),
    created_date TIMESTAMP,
    updated_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS customer
(
    customer_id   SERIAL PRIMARY KEY NOT NULL,
    customer_name VARCHAR(200),
    agency_id     VARCHAR(36)        NOT NULL,
    created_date  TIMESTAMP,
    updated_date  TIMESTAMP,
    CONSTRAINT fk_agency
        FOREIGN KEY (agency_id)
            REFERENCES agency (agency_id)
);

CREATE TABLE IF NOT EXISTS responder
(
    responder_id   SERIAL PRIMARY KEY NOT NULL,
    responder_name VARCHAR(200),
    responder_code VARCHAR(11),
    agency_id      VARCHAR(36)        NOT NULL,
    created_date   TIMESTAMP,
    updated_date   TIMESTAMP,
    CONSTRAINT fk_agency
        FOREIGN KEY (agency_id)
            REFERENCES agency (agency_id)
);

CREATE TABLE IF NOT EXISTS event
(
    event_id        VARCHAR(36) PRIMARY KEY NOT NULL,
    event_number    int4,
    event_type_code VARCHAR(3),
    responder_id    int4,
    customer_id     int4,
    event_time      TIMESTAMP,
    dispatch_time   TIMESTAMP,
    created_date    TIMESTAMP,
    updated_date    TIMESTAMP,
    CONSTRAINT fk_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id),
    CONSTRAINT fk_responder
        FOREIGN KEY (responder_id)
            REFERENCES responder (responder_id)
);

CREATE SEQUENCE IF NOT EXISTS customer_id_seq
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1001
    OWNED BY customer.customer_id;

CREATE SEQUENCE IF NOT EXISTS responder_id_seq
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    OWNED BY responder.responder_id;