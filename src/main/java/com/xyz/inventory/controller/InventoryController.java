package com.xyz.inventory.controller;


import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.inventory.entity.Product;
import com.xyz.inventory.model.ProductRequest;
import com.xyz.inventory.model.UserRequest;
import com.xyz.inventory.repository.ProductRepository;

@RestController
public class InventoryController {
	
	@Autowired
	private ProductRepository productRepository;

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Value("${USER-SERVICE-URI:http://127.0.0.1:8000}")
	private String userServiceHost;
	
	@GetMapping("/health")
	public String healthCheck() {
		return "OK";
	}
	
	@CrossOrigin
	@PostMapping("/products")
	public List<Product> getAllProducts(@RequestBody ProductRequest request) throws JsonParseException, JsonMappingException, IOException{
		//Rest Template
		// return products.
		HttpEntity<UserRequest> requestEntity = new HttpEntity<>(request.getUser());
		//UserRequest res = restTemplate().postForObject(userServiceHost+"/user", request.getUser(), UserRequest.class);
		final ResponseEntity<String> responseEntity = restTemplate().exchange(userServiceHost.trim()+"/user", HttpMethod.POST, requestEntity, String.class);
		UserRequest res = new ObjectMapper().readValue(responseEntity.getBody(), UserRequest.class);
		if(res.getId()!=null) 
			return productRepository.findAll();
		else return Collections.emptyList();
	}
		
}
