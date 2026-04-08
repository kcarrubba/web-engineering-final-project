package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmployeeDto {

    @JsonProperty("emp_id")
    private Integer id;
    private String name;
}