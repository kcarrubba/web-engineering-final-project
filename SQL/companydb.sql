use companydb;
drop table employee;
drop table department;
drop table project;
drop table employeeproject;

CREATE TABLE department (
  id 		INT PRIMARY KEY AUTO_INCREMENT,
  name 		VARCHAR(255)
);

CREATE TABLE employee (
  id 		INT PRIMARY KEY AUTO_INCREMENT,
  name 		VARCHAR(255),
  salary 	DOUBLE,
  department_id INT REFERENCES department(id)
); 

CREATE TABLE project (
  id 		INT PRIMARY KEY AUTO_INCREMENT,
  title 	VARCHAR(255),
  budget 	DOUBLE
);

CREATE TABLE employee_project (
  employee_id INT	REFERENCES employee(id) ON DELETE CASCADE ON UPDATE CASCADE,
  project_id 	INT REFERENCES project(id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (employee_id, project_id)
);



