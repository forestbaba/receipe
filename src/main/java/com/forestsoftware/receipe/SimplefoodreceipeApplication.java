package com.forestsoftware.receipe;

import com.forestsoftware.receipe.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class SimplefoodreceipeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimplefoodreceipeApplication.class, args);
    }

}
