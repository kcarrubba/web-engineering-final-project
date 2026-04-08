package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.example.demo.controllers.EmployeeController;
import com.example.demo.entities.Department;
import com.example.demo.entities.Employee;
import com.example.demo.entities.Project;
import com.example.demo.repositories.EmployeeRepository;

@SpringBootApplication
public class DemoApplication {

    private final EmployeeController employeeController;

    DemoApplication(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
	}
}

					
