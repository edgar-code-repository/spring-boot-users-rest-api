package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PhoneDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Phone;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtTokenUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public UserDTO addUser(UserDTO userDTO) {
				
		LocalDateTime now = LocalDateTime.now();
		String jwtToken = JwtTokenUtil.createToken(userDTO.getEmail());
		
		User newUser = new User();
		newUser.setName(userDTO.getName());
		newUser.setEmail(userDTO.getEmail());
		newUser.setPassword(userDTO.getPassword());
		newUser.setCreatedDate(now);
		newUser.setLastModifiedDate(now);
		newUser.setLastLogin(now);
		newUser.setTokenJWT(jwtToken);
		newUser.setActive(true);
		
		List<Phone> phones = new ArrayList<>();
		for(PhoneDTO phoneDTO: userDTO.getPhones()) {
			Phone phone = new Phone();
			phone.setNumber(phoneDTO.getNumber());
			phone.setCountryCode(phoneDTO.getCountryCode());
			phone.setCityCode(phoneDTO.getCityCode());
			
			phones.add(phone);
		}
		newUser.setPhones(phones);
		
		newUser = userRepository.save(newUser);
		
		userDTO = new UserDTO();
		userDTO.setId(newUser.getId().toString());
		userDTO.setName(newUser.getName());
		userDTO.setCreatedDate(newUser.getCreatedDate());
		userDTO.setLastModifiedDate(newUser.getLastModifiedDate());
		userDTO.setLastLogin(newUser.getLastLogin());
		userDTO.setTokenJWT(newUser.getTokenJWT());
		userDTO.setActive(newUser.isActive());
		
		return userDTO;
	}
	
	public List<UserDTO> getAllUsers() {
		List<UserDTO> list = new ArrayList<>();
		
		List<User> allUsers = userRepository.findAll();

		for(User user: allUsers) {
			UserDTO userDTO = new UserDTO();
			userDTO.setId(user.getId().toString());
			userDTO.setName(user.getName());
			userDTO.setCreatedDate(user.getCreatedDate());
			userDTO.setLastModifiedDate(user.getLastModifiedDate());
			userDTO.setLastLogin(user.getLastLogin());
			userDTO.setTokenJWT(user.getTokenJWT());
			userDTO.setActive(user.isActive());
			
			list.add(userDTO);
		}
		
		return list;
	}
	
	public UserDTO getUserById(String id) {
		UserDTO userDTO = null;
		
		Optional<User> userOptional = userRepository.findById(UUID.fromString(id));
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			
			userDTO = new UserDTO();
			userDTO.setId(user.getId().toString());
			userDTO.setName(user.getName());
			userDTO.setCreatedDate(user.getCreatedDate());
			userDTO.setLastModifiedDate(user.getLastModifiedDate());
			userDTO.setLastLogin(user.getLastLogin());
			userDTO.setTokenJWT(user.getTokenJWT());
			userDTO.setActive(user.isActive());			
		}
		
		return userDTO;
	}	
	
	public boolean exists(String email) {
		boolean flag = false;
		User user = userRepository.findByEmail(email);
		if (user != null) {
			flag = true;
		}
		return flag;
	}

	public UserDTO updateUser(UserDTO userDTO) {

		LocalDateTime now = LocalDateTime.now();
		String jwtToken = JwtTokenUtil.createToken(userDTO.getEmail());

		User newUser = new User();
		newUser.setId(UUID.fromString(userDTO.getId()));
		newUser.setName(userDTO.getName());
		newUser.setEmail(userDTO.getEmail());
		newUser.setPassword(userDTO.getPassword());
		newUser.setCreatedDate(now);
		newUser.setLastModifiedDate(now);
		newUser.setLastLogin(now);
		newUser.setTokenJWT(jwtToken);
		newUser.setActive(true);

		List<Phone> phones = new ArrayList<>();
		for(PhoneDTO phoneDTO: userDTO.getPhones()) {
			Phone phone = new Phone();
			phone.setNumber(phoneDTO.getNumber());
			phone.setCountryCode(phoneDTO.getCountryCode());
			phone.setCityCode(phoneDTO.getCityCode());

			phones.add(phone);
		}
		newUser.setPhones(phones);

		newUser = userRepository.save(newUser);

		userDTO = new UserDTO();
		userDTO.setId(newUser.getId().toString());
		userDTO.setName(newUser.getName());
		userDTO.setCreatedDate(newUser.getCreatedDate());
		userDTO.setLastModifiedDate(newUser.getLastModifiedDate());
		userDTO.setLastLogin(newUser.getLastLogin());
		userDTO.setTokenJWT(newUser.getTokenJWT());
		userDTO.setActive(newUser.isActive());

		return userDTO;
	}
	
}
