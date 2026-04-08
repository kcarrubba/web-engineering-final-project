package com.UniX;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.UniX.controllers.SemestersController;
import com.UniX.repositories.SemesterRepository;

@SpringBootApplication
public class UnixApplication {

    private final SemestersController semestersController;

    public UnixApplication(SemestersController semestersController) {
        this.semestersController = semestersController;
    }
    public static void main(String[] args) {
        SpringApplication.run(UnixApplication.class, args);
    }
}