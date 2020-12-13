package com.example.twe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.example.twe.model.Client;
import com.example.twe.model.SecurityClient;
import com.example.twe.repository.ClientRepository;

@Service
public class DBClientDetailsService implements ClientDetailsService {

	@Autowired
	ClientRepository clientRepo;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

		Client client = clientRepo.findByClient(clientId);
		if (client == null)
			throw new ClientRegistrationException("Client not found!");

		else {
			ClientDetails clientDetails = new SecurityClient(client);
			return clientDetails;
		}
	}

}
