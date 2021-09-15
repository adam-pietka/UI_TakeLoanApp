package com.example.application.data.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
@Service
public class RestClientService implements Serializable {

	@Value("${server.port}")
	private String serverPort;

	public List<JsonNode> getAllCustomers() {

		System.out.println("Fetching all Customers objects through REST..");
		final RequestHeadersSpec<?> spec = WebClient.create().get().uri("localhost:8085/takeLoan/customers/getAllCustomes");

		// do fetch and map result
		final List<JsonNode> customers = spec.retrieve().toEntityList(JsonNode.class).block().getBody();
		System.out.println(String.format("...received %d items.", customers.size()));

		return customers;
	}

	public List<JsonNode> getAllLoansApplications() {

		System.out.println("Fetching all LoansApplications objects through REST..");
		final RequestHeadersSpec<?> spec = WebClient.create().get().uri("localhost:8085/takeLoan/loanApplist/getAllLoansApp");

		// do fetch and map result
		final List<JsonNode> customers = spec.retrieve().toEntityList(JsonNode.class).block().getBody();
		System.out.println(String.format("...received %d items.", customers.size()));

		return customers;
	}
}
