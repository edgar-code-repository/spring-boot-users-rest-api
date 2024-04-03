package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class UserDTO {
	
	private String id;

	private String name;
	
	private String email;
	
	private String password;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime lastModifiedDate;
	
	private LocalDateTime lastLogin;
	
	private String tokenJWT;
	
	private boolean isActive;
	
	private List<PhoneDTO> phones = new ArrayList<>();
	

}
