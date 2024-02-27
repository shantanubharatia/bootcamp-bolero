INSERT INTO DEPARTMENT (id, name, mandatory, read_only) VALUES (nextval('SEQUENCE_DEPARTMENT'), 'Organisation', true, true);
INSERT INTO EMPLOYEE (id, name) VALUES (nextval('SEQUENCE_EMPLOYEE'), 'Shantanu');
INSERT INTO MAP_EMPLOYEE_DEPARTMENT VALUES (SELECT id FROM EMPLOYEE WHERE name = 'Shantanu', SELECT id FROM DEPARTMENT WHERE name = 'Organisation');
