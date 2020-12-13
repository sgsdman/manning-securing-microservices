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

import com.example.twe.model.Client;
import com.example.twe.repository.ClientRepository;

@RestController
@RequestMapping(path = "/client", produces = "application/json")
public class ClientController {

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/{clientName}")
	public ResponseEntity<Client> getClient(@PathVariable("clientName") String clientName) {
		
		Client client = clientRepository.findByClient(clientName);
		if (client != null) {
			return new ResponseEntity<>(client, HttpStatus.OK);
		} else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@PostMapping("/newclient")
	public ResponseEntity<String> addClient(@RequestBody Client client) {
		
		Client existingClient = clientRepository.findByClient(client.getClient());
		if (existingClient == null) {
			
			// Encode secret in Bcrypt
			client.setSecret(passwordEncoder.encode(client.getSecret()));
			
			clientRepository.save(client);
			return new ResponseEntity<>("OK", HttpStatus.CREATED);
		}
		return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}
}
