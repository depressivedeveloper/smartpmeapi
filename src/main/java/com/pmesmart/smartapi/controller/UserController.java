package com.pmesmart.smartapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pmesmart.smartapi.exception.ResourceNotFoundException;
import com.pmesmart.smartapi.model.User;
import com.pmesmart.smartapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@ResponseBody
@RequestMapping("/api")

public class UserController{ 

	@Autowired
    private final UserRepository userRepository;
	private final PasswordEncoder encoder;

	public UserController(UserRepository userRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}

    // get all rest api
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}	

    // get by id rest api
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUsertById(@PathVariable Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist with id :" + id));
		return ResponseEntity.ok(user);
	}

    // create in rest api
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user){
		user.setPassword(encoder.encode(user.getPassword()));
		return ResponseEntity.ok(userRepository.save(user));
	}

    // update rest api
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User temp ){
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist with id :" + id));
		
		user.setUsername(temp.getUsername());
		user.setPassword(encoder.encode(temp.getPassword()));
		user.setId(temp.getId());
		
		User updatedUser = userRepository.save(temp);
		return ResponseEntity.ok(updatedUser);
	}

    // delete rest api
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id){
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist with id :" + id));
		
        userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	// validate user login
	@GetMapping("/validate")
	public ResponseEntity<Boolean> validateUser(@RequestParam String username, @RequestParam String password) {
		Optional<User> user = userRepository.findByUsername(username);

		if(user.isEmpty()){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}

		boolean isValid = encoder.matches(password, user.get().getPassword());
		HttpStatus status = (isValid)? HttpStatus.OK: HttpStatus.UNAUTHORIZED;
		return ResponseEntity.status(status).body(isValid);
	}

}