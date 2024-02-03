package com.example.customerservice;

import com.example.customerservice.entities.Customer;
import com.example.customerservice.repos.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(CustomerRepository customerRepository, RepositoryRestConfiguration repositoryRestConfigurer){
        return args -> {
            repositoryRestConfigurer.exposeIdsFor(Customer.class);
            customerRepository.saveAll(
                    List.of(
                            Customer.builder().name("Youssef11").email("youssef11@gmail.com").build(),
                            Customer.builder().name("Youssef22").email("youssef22@gmail.com").build(),
                            Customer.builder().name("Youssef33").email("youssef33@gmail.com").build()
                    )
            );
            customerRepository.findAll().forEach(c->{
                System.out.println(c);
            });
        };
    }

}
