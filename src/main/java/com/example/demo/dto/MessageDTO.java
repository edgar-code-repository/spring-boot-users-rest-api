package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class MessageDTO  {
	
	private List<UserDTO> users;

	private UserDTO user;
	
	private String message;

	public MessageDTO(String msg) {
		this.message = msg;
	}
	
}
