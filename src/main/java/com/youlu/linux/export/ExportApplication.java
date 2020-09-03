package com.youlu.linux.export;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhushilong
 */
@SpringBootApplication
public class ExportApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExportApplication.class,args);
    }
}
