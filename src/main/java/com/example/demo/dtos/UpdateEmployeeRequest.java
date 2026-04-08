package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateEmployeeRequest {

    private Integer id;
    private String name;
    private double salary;

}
