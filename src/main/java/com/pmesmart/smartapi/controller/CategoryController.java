package com.pmesmart.smartapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pmesmart.smartapi.exception.ResourceNotFoundException;
import com.pmesmart.smartapi.model.Category;
import com.pmesmart.smartapi.repository.CategoryRepository;

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
public class CategoryController {
    
	@Autowired
    private CategoryRepository categoryRepository;

    // get all Categories
	@GetMapping("/category")
	public List<Category> getAllCategories(){
		return categoryRepository.findAll();
	}	

    // get by id rest api
	@GetMapping("/category/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category does not exist with id :" + id));
		return ResponseEntity.ok(category);
	}

    // create in rest api
	@PostMapping("/category")
	public Category createCategory(@RequestBody Category temp){
		return categoryRepository.save(temp);
	}

    // update rest api
	@PutMapping("/category/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category temp){
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category does not exist with id :" + id));
		
                category.setDescription(temp.getDescription());
                category.setId(temp.getId());
		
		Category updated = categoryRepository.save(category);
		return ResponseEntity.ok(updated);
	}

    // delete rest api
	@DeleteMapping("/category/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Category temp = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category does not exist with id :" + id));
		
         categoryRepository.delete(temp);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
