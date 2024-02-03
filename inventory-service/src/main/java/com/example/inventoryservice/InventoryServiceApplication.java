package com.example.inventoryservice;

import com.example.inventoryservice.entites.Product;
import com.example.inventoryservice.repos.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration repositoryRestConfiguration){
        return args -> {
            repositoryRestConfiguration.exposeIdsFor(Product.class);
            productRepository.saveAll(
                    List.of(
                            Product.builder()
                                    .name("Computer")
                                    .price(1265)
                                    .quantity(10)
                                    .build(),
                            Product.builder()
                                    .name("Printer")
                                    .price(1200)
                                    .quantity(8)
                                    .build(),
                            Product.builder()
                                    .name("Smartphone")
                                    .price(145)
                                    .quantity(23)
                                    .build()
                    )
            );
        };
    }

}
