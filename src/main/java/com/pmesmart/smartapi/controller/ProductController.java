package com.pmesmart.smartapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pmesmart.smartapi.exception.ResourceNotFoundException;
import com.pmesmart.smartapi.model.Product;
import com.pmesmart.smartapi.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@ResponseBody
@RequestMapping("/api")

public class ProductController{ 

	@Autowired
    private ProductRepository productRepository;

    // get all rest api
	@GetMapping("/products")
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}	

    // get by id rest api
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		Product employee = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product does not exist with id :" + id));
		return ResponseEntity.ok(employee);
	}

    // create in rest api
	@PostMapping("/products")
	public Product createProduct(@RequestBody Product product){
		return productRepository.save(product);
	}

    // update rest api
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails){
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product does not exist with id :" + id));
		
		product.setProductDescription(productDetails.getProductDescription());
		product.setProductName(productDetails.getProductName());
		product.setProductId(productDetails.getProductId());
		
		Product updatedProduct = productRepository.save(product);
		return ResponseEntity.ok(updatedProduct);
	}

    // delete rest api
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product does not exist with id :" + id));
		
		productRepository.delete(product);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}