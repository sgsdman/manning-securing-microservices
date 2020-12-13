package com.example.twe.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

public class SecurityClient implements ClientDetails {

	private final Client client;
	
	public  SecurityClient (Client client) {
		
		this.client = client;
	}

	@Override
	public String getClientId() {
		
		return client.getClient();
	}

	@Override
	public Set<String> getResourceIds() {
		
		Set<String> resources = new HashSet<>();
		// Assume only one resource
		resources.add(client.getResources());
		return resources;
	}

	@Override
	public boolean isSecretRequired() {
		
		return true;
	}

	@Override
	public String getClientSecret() {
		
		return client.getSecret();
	}

	@Override
	public boolean isScoped() {
		
		return true;
	}

	@Override
	public Set<String> getScope() {
		
		Set<String> scopes = new HashSet<>();
		String[] s = client.getScope().split(",");
		for (String scope: s)
			scopes.add(scope);
		
		return scopes;
        
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		
		Set<String> grantTypes = new HashSet<>();
		String[] grants = client.getGrantTypes().split(",");
		for (String grant: grants)
			grantTypes.add(grant);
		
		return grantTypes;
		
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		
		Set<String> redirectUris = new HashSet<>();
		// Assume only one uri
		redirectUris.add(client.getRedirectUri());
		return redirectUris;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		// Assume only one grantedAuthority
		grantedAuthorities.add(new SimpleGrantedAuthority(client.getRedirectUri()));
		return grantedAuthorities;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		
		// ignore values from Database
		return new Integer(60);
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		
		// ignore values from Database
		return new Integer(60);
	}

	@Override
	public boolean isAutoApprove(String scope) {

		return true;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		Map<String, Object> additionInfoMap = new HashMap<>();
		// Assume only one additional Info
		additionInfoMap.put("Name",client.getAdditionalInfo());
		return additionInfoMap;
	}

}
