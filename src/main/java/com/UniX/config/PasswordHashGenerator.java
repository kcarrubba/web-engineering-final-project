package com.UniX.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHashGenerator implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    public PasswordHashGenerator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        System.out.println("c0002 / myPassword -> " + passwordEncoder.encode("myPassword"));
        System.out.println("c0003 / 12345Pass -> " + passwordEncoder.encode("12345Pass"));
        System.out.println("c0004 / MyPet123 -> " + passwordEncoder.encode("MyPet123"));
        System.out.println("c1234 / password -> " + passwordEncoder.encode("password"));
    }
}