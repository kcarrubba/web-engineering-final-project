use companydb;

delete from department;

/*
CREATE TABLE department (
  id 		INT PRIMARY KEY AUTO_INCREMENT,
  name 		VARCHAR(255)
);
*/

insert into department(name) values ("Marketing");
insert into department(name) values ("Sales");
insert into department(name) values ("Production");

select * from department;

delete from employee;

/*
CREATE TABLE employee (
  id 		INT PRIMARY KEY AUTO_INCREMENT,
  name 		VARCHAR(255),
  salary 	DOUBLE,
  department_id INT REFERENCES department(id)
); 
*/

insert into employee (name, salary, department_id) values ("Peter Wang", 70000, 1);
insert into employee (name, salary, department_id) values ("Mary Ellison", 100000, 1);
insert into employee (name, salary, department_id) values ("Kate Billy", 90000, 1);

select * from employee;

