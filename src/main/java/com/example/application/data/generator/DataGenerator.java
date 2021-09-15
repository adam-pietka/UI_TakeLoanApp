package com.example.application.data.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;

import com.example.application.data.service.SamplePersonRepository;
import com.example.application.data.entity.Customer;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(SamplePersonRepository samplePersonRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (samplePersonRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 100 Sample Person entities...");
            ExampleDataGenerator<Customer> samplePersonRepositoryGenerator = new ExampleDataGenerator<>(
                    Customer.class, LocalDateTime.of(2021, 9, 15, 0, 0, 0));
            samplePersonRepositoryGenerator.setData(Customer::setId, DataType.ID);
            samplePersonRepositoryGenerator.setData(Customer::setFirstName, DataType.FIRST_NAME);
            samplePersonRepositoryGenerator.setData(Customer::setLastName, DataType.LAST_NAME);
            samplePersonRepositoryGenerator.setData(Customer::setEmail, DataType.EMAIL);
            samplePersonRepositoryGenerator.setData(Customer::setPhone, DataType.PHONE_NUMBER);
            samplePersonRepositoryGenerator.setData(Customer::setDateOfBirth, DataType.DATE_OF_BIRTH);
            samplePersonRepositoryGenerator.setData(Customer::setOccupation, DataType.OCCUPATION);
            samplePersonRepositoryGenerator.setData(Customer::setImportant, DataType.BOOLEAN_10_90);
            samplePersonRepository.saveAll(samplePersonRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}