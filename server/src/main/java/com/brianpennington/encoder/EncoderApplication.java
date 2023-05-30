package com.brianpennington.encoder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.brianpennington.encoder.storage.StorageProperties;
import com.brianpennington.encoder.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class EncoderApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EncoderApplication.class, args);
        System.out.println("EncoderApplication started");
    }

    @Bean
    CommandLineRunner init(final StorageService storageService) {
        return (args) -> {
            // storageService.deleteAll();
            storageService.init();
        };
    }
}
