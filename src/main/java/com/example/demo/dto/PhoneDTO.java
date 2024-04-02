package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PhoneDTO {
	
	private String id;
	
	private String number;
	
	private String cityCode;
	
	private String countryCode;
	
}
