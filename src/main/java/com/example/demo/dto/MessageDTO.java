package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MessageDTO  {
	
	private List<UserDTO> users;

	private UserDTO user;
	
	private String message;
	
}
