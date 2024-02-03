package com.example.billingservice;

import com.example.billingservice.entities.Bill;
import com.example.billingservice.entities.ProductItem;
import com.example.billingservice.model.Customer;
import com.example.billingservice.model.Product;
import com.example.billingservice.repo.BillRepository;
import com.example.billingservice.repo.ProductItemRepository;
import com.example.billingservice.services.CustomerRestClient;
import com.example.billingservice.services.ProductRestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepository, ProductItemRepository productItemRepository,
                            CustomerRestClient customerRestClient, ProductRestClient productRestClient){
        return args -> {
            Collection<Product> products=productRestClient.allProducts().getContent();
            Long customerId=1L;
            Customer customer = customerRestClient.findCustomerById(customerId);
            if (customer==null) throw new RuntimeException("Costomer not found");
            Bill bill = new Bill();
            bill.setBillDate(new Date());
            bill.setCustomerId(customerId);
            Bill saveBill = billRepository.save(bill);
            products.forEach(product -> {
                ProductItem productItem = new ProductItem();
                productItem.setBill(saveBill);
                productItem.setProductId(product.getId());
                productItem.setQuantity(1+new Random().nextInt(10));
                productItem.setPrice(product.getPrice());
                productItem.setDiscount(Math.random());
                productItemRepository.save(productItem);
            });
        };
    }

}
