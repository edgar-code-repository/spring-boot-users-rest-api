package com.example.demo.controller.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.dto.MessageDTO;
import com.example.demo.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
	
	@Autowired
    private MockMvc mockMvc;
	
	private static final String USERS_ENDPOINT = "/users";
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	@Order(1)
	public void testGetAllUsersEndpoint() throws Exception {
		logger.debug("[UserControllerTest][testGetAllUsersEndpoint][INICIO]");
		
		mockMvc.perform(get(USERS_ENDPOINT).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		logger.debug("[UserControllerTest][testGetAllUsersEndpoint][FIN]");
	}
	
	@Test
	@Order(2)
	public void testCreateUserEndpoint() throws Exception {
		logger.debug("[UserControllerTest][testCreateUserEndpoint][INICIO]");
		
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Mr. Robot");
		userDTO.setEmail("mr.robot@domain.com");
		userDTO.setPassword("Secret20");
		
		String userAsJson = mapper.writeValueAsString(userDTO);

		mockMvc.perform(post(USERS_ENDPOINT).accept(MediaType.APPLICATION_JSON).content(userAsJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
		
		logger.debug("[UserControllerTest][testCreateUserEndpoint][FIN]");
	}	
	
	@Test
	@Order(3)
	public void testEmailAlreadyExists() throws Exception {
		logger.debug("[UserControllerTest][testEmailAlreadyExists][INICIO]");
		
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Mr. Robot");
		userDTO.setEmail("mr.robot@domain.com");
		userDTO.setPassword("Secret20");
		
		String userAsJson = mapper.writeValueAsString(userDTO);

		MvcResult responseCreateUser = mockMvc.perform(post(USERS_ENDPOINT).accept(MediaType.APPLICATION_JSON).content(userAsJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		String strResponseCreateUser = responseCreateUser.getResponse().getContentAsString();
        MessageDTO messageDTO = mapper.readValue(strResponseCreateUser, MessageDTO.class);

        assertTrue(messageDTO.getMessage().equals("El email ya se encuentra registrado"));		
		
		logger.debug("[UserControllerTest][testEmailAlreadyExists][FIN]");
	}	
	
	@Test
	@Order(4)
	public void testEmailFormat() throws Exception {
		logger.debug("[UserControllerTest][testEmailFormat][INICIO]");
		
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Mr. Robot");
		userDTO.setEmail("mr.robot@domain");
		userDTO.setPassword("Secret20");
		
		String userAsJson = mapper.writeValueAsString(userDTO);

		MvcResult responseCreateUser = mockMvc.perform(post(USERS_ENDPOINT).accept(MediaType.APPLICATION_JSON).content(userAsJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		String strResponseCreateUser = responseCreateUser.getResponse().getContentAsString();
        MessageDTO messageDTO = mapper.readValue(strResponseCreateUser, MessageDTO.class);

        assertTrue(messageDTO.getMessage().equals("El formato del email es invalido"));		
		
		logger.debug("[UserControllerTest][testEmailFormat][FIN]");
	}	
	
	@Test
	@Order(5)
	public void testPasswordFormat() throws Exception {
		logger.debug("[UserControllerTest][testPasswordFormat][INICIO]");
		
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Mr. Robot");
		userDTO.setEmail("mr.robot@domain.com");
		userDTO.setPassword("secret");
		
		String userAsJson = mapper.writeValueAsString(userDTO);

		MvcResult responseCreateUser = mockMvc.perform(post(USERS_ENDPOINT).accept(MediaType.APPLICATION_JSON).content(userAsJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		String strResponseCreateUser = responseCreateUser.getResponse().getContentAsString();
        MessageDTO messageDTO = mapper.readValue(strResponseCreateUser, MessageDTO.class);

        assertTrue(messageDTO.getMessage().equals("El formato de la password es incorrecto"));		
		
		logger.debug("[UserControllerTest][testPasswordFormat][FIN]");
	}	
	
	
	
	
}
