package com.example.twe;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@SpringBootTest
@AutoConfigureMockMvc
class SpringOauth2ApplicationTests {

	 @Autowired
	  private MockMvc mvc;
	 
	@Test
	void contextLoads() throws Exception {
		
		 mvc.perform(
		            post("/oauth/token")
		                    .with(httpBasic("client", "secret"))
		                    .queryParam("grant_type", "password")
		                    .queryParam("username", "john")
		                    .queryParam("password", "12345")
		                    .queryParam("scope", "read")
		    )
		            .andExpect(jsonPath("$.access_token").exists())
		            .andExpect(status().isOk());
	}

}
