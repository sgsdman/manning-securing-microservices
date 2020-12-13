package com.example.twe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.twe.model.User;
import com.example.twe.repository.UserRepository;

@RestController
@RequestMapping(path = "/user", produces = "application/json")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/{username}")
	public ResponseEntity<User> getUser(@PathVariable("username") String username) {
		
		User user = userRepository.findByUsername(username);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@PostMapping("/newuser")
	public ResponseEntity<String> addUser(@RequestBody User user) {
		
		User existingUser = userRepository.findByUsername(user.getUsername());
		if (existingUser == null) {
			
			// Encode password in Bcrypt
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			userRepository.save(user);
			return new ResponseEntity<>("OK", HttpStatus.CREATED);
		}
		return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}
}
