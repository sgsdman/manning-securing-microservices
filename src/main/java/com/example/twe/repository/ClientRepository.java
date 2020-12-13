package com.example.twe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.twe.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	
	Client findByClient(String client);

}
