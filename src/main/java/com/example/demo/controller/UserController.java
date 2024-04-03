package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MessageDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import com.example.demo.util.ResponseUtil;
import com.example.demo.util.ValidatorUtil;

@RestController
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Value("${emailMessage}")
	private String emailMessage;

	@Value("${formatEmailMessage}")
	private String formatEmailMessage;

	@Value("${passwordMessage}")
	private String passwordMessage;

	@Value("${emailNotFoundMessage}")
	private String emailNotFoundMessage;

	@Value("${userRetrievedOK}")
	private String userRetrievedOK;

	@RequestMapping(value="/users", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageDTO> retrieveUsers() {
		logger.debug("[UserController][retrieveUsers][INICIO]");

		MessageDTO message = new MessageDTO();
		ResponseEntity<MessageDTO> response = null;
		try {
			List<UserDTO> usersList = userService.getAllUsers();
			if (usersList != null) {
				logger.debug("[UserController][retrieveUsers][users list size: " + usersList.size() + "]");

				message.setUsers(usersList);
				if (usersList.size() == 0) {
					message.setMessage("No hay usuarios disponibles!");
				}
			}
			response = ResponseUtil.ok(message);
		}
		catch (Exception e) {
			logger.error("[UserController][retrieveUsers][Error: " + e.toString() + "]");

			message.setMessage("Error al recuperar lista de usuarios: " + e.toString());
			response = ResponseUtil.error(message);			
		}
		
		logger.debug("[UserController][retrieveUsers][FIN]");
		return response;
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageDTO> retrieveUserById(@PathVariable String id) {
		logger.debug("[UserController][retrieveUserById][INICIO]");
		logger.debug("[UserController][retrieveUserById][id: " + id + "]");
		
		ResponseEntity<MessageDTO> response = null;
		try {
			UserDTO userRetrieved = userService.getUserById(id);
			if (userRetrieved != null) {
				logger.debug("[UserController][retrieveUserById][user retrieved: " + userRetrieved.toString() + "]");

				MessageDTO message = new MessageDTO();
				message.setUser(userRetrieved);
				message.setMessage(userRetrievedOK);

				response = ResponseEntity.ok(message);
			}
			else {
				MessageDTO message = new MessageDTO();
				message.setUser(userRetrieved);
				message.setMessage(userRetrievedOK);

				response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
			}
			

		}
		catch (Exception e) {
			logger.error("[UserController][retrieveUserById][Error: " + e.toString() + "]");
			
			MessageDTO message = new MessageDTO();
			message.setMessage("Error al recuperar usuario: " + e.toString());
			response = ResponseUtil.error(message);			
		}
		
		logger.debug("[UserController][retrieveUserById][FIN]");
		return response;
	}	
	
	@RequestMapping(value="/users", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageDTO> createUser(@RequestBody UserDTO user) {
		logger.debug("[UserController][createUser][INICIO]");
		logger.debug("[UserController][createUser][user: " + user.toString() + "]");
		
		ResponseEntity<MessageDTO> response = null;
		try {
			MessageDTO message = new MessageDTO();
			
			boolean flagEmailExists = userService.exists(user.getEmail());
			logger.debug("[UserController][createUser][flagEmailExists: " + flagEmailExists + "]");
			
			if (flagEmailExists) {
				message.setMessage(emailMessage);
				response = ResponseUtil.ok(message);
			}
			
			boolean flagPasswordFormat = ValidatorUtil.validatePassword(user.getPassword());
			logger.debug("[UserController][createUser][flagPasswordFormat: " + flagPasswordFormat + "]");
			
			if (!flagPasswordFormat) {
				message.setMessage(passwordMessage);
				response = ResponseUtil.ok(message);
			}			
			
			boolean flagEmailFormat = ValidatorUtil.validateEmail(user.getEmail());
			logger.debug("[UserController][createUser][flagEmailFormat: " + flagEmailFormat + "]");
			
			if (!flagEmailFormat) {
				message.setMessage(formatEmailMessage);
				response = ResponseUtil.ok(message);					
			}
			
			if (!flagEmailExists && flagPasswordFormat && flagEmailFormat) {
				UserDTO newUser = userService.addUser(user);
				
				message.setMessage("El usuario fue creado correctamente!!!");
				message.setUser(newUser);
				
				response = ResponseUtil.created(message);			
			}
		}
		catch (Exception e) {
			logger.error("[UserController][createUser][Error: " + e.toString() + "]");
			
			MessageDTO message = new MessageDTO();
			message.setMessage("Error al crear un nuevo usuario: " + e.toString());
			response = ResponseUtil.error(message);
		}
		
		logger.debug("[UserController][createUser][FIN]");
		return response;
	}





}