package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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

	@Autowired
	private ModelMapper modelMapper;

	public UserDTO addUser(UserDTO userDTO) {
		LocalDateTime now = LocalDateTime.now();
		String jwtToken = JwtTokenUtil.createToken(userDTO.getEmail());

		User newUser = modelMapper.map(userDTO, User.class);
		newUser.setCreatedDate(now);
		newUser.setLastModifiedDate(now);
		newUser.setLastLogin(now);
		newUser.setTokenJWT(jwtToken);
		newUser.setActive(true);

		List<Phone> phones = userDTO.getPhones().stream()
				.map(phoneDTO -> modelMapper.map(phoneDTO, Phone.class))
				.collect(Collectors.toList());

		newUser.setPhones(phones);
		newUser = userRepository.save(newUser);

		userDTO = modelMapper.map(newUser, UserDTO.class);
		return userDTO;
	}
	
	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream()
				.map(user -> modelMapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
	}
	
	public UserDTO getUserById(String id) {
		UserDTO userDTO = null;
		
		Optional<User> userOptional = userRepository.findById(UUID.fromString(id));
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			userDTO = modelMapper.map(user, UserDTO.class);
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
		Optional<User> userOptional = userRepository.findById(UUID.fromString(userDTO.getId()));
		if (userOptional.isPresent()) {
			User modifiedUser = userOptional.get();
			modifiedUser.setLastModifiedDate(LocalDateTime.now());
			modifiedUser.setName(userDTO.getName());
			modifiedUser.setPassword(userDTO.getPassword());

			List<Phone> phones = userDTO.getPhones().stream()
					.map(phoneDTO -> modelMapper.map(phoneDTO, Phone.class))
					.collect(Collectors.toList());

			modifiedUser.setPhones(phones);

			modifiedUser = userRepository.save(modifiedUser);
			userDTO = modelMapper.map(modifiedUser, UserDTO.class);
		}
		return userDTO;
	}

	public boolean deleteUserById(String id) {
		userRepository.deleteById(UUID.fromString(id));
		return true;
	}
	
}
