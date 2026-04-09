package com.UniX.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SemesterDto {
    
    @JsonProperty("semesterId")
    private Integer semesterId;

    @JsonProperty("semester")
    private Integer semester;

    @JsonProperty("year")
    private Integer year;
}
