package org.hollingdale.vestasim;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class VestasimApplication {

    public static void main(String[] args) throws IOException {
        new SpringApplicationBuilder(VestasimApplication.class)
                .headless(false)
                .build()
                .run(args);
    }
}
