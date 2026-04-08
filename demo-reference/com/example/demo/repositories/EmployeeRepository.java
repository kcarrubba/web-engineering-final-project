package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.entities.Employee;
/*
public interface EmployeeRepository extends CrudRepository <Employee, Integer> {
}

 */
public interface EmployeeRepository extends JpaRepository <Employee, Integer> {
}