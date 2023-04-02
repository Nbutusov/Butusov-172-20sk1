package edu.nmu.fit.ButusovNYProgect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ButusovApplication {

    public static void main(String[] args) {
        System.setProperty("server.port", "9091");
        SpringApplication.run(ButusovApplication.class, args);
    }

}

