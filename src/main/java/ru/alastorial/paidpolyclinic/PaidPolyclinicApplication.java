package ru.alastorial.paidpolyclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // для полей created_at
public class PaidPolyclinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaidPolyclinicApplication.class, args);
    }

}
