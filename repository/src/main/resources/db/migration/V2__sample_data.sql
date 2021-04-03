INSERT INTO agency(agency_id, agency_name)
VALUES ('9000001', 'Agency 1'),
       ('9000002', 'Agency 2'),
       ('9000003', 'Agency 3');

INSERT INTO customer(agency_id, customer_id, customer_name)
VALUES ('9000001', nextval('customer_id_seq'), 'customer 1'),
       ('9000002', nextval('customer_id_seq'), 'customer 2'),
       ('9000003', nextval('customer_id_seq'), 'customer 3');

INSERT INTO responder(agency_id, responder_id, responder_name, responder_code)
VALUES ('9000001', nextval('responder_id_seq'), 'officer 1', 'OFFICER_001'),
       ('9000002', nextval('responder_id_seq'), 'officer 2', 'OFFICER_002'),
       ('9000003', nextval('responder_id_seq'), 'officer 3', 'OFFICER_003');
