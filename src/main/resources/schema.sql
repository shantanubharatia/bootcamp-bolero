CREATE TABLE employee (
                          id INT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);

CREATE TABLE department (
                            id INT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            mandatory boolean,
                            read_only boolean
);

CREATE TABLE map_employee_department (
                                         id_employee INTEGER,
                                         id_department INTEGER,
                                         PRIMARY KEY (id_employee, id_department),
                                         FOREIGN KEY (id_employee) REFERENCES employee(id),
                                         FOREIGN KEY (id_department) REFERENCES department(id)
);

CREATE SEQUENCE employee_sequence
    MINVALUE 1
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE department_sequence
    MINVALUE 1
    START WITH 1
    INCREMENT BY 1;

//create sequences for the first 2 tables - google it

