package com.example.application.data.service;

import com.example.application.data.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SamplePersonRepository extends JpaRepository<Customer, Integer> {

}