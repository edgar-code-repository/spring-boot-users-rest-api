package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class PhoneDTO {
	
	private String id;
	
	private String number;
	
	private String cityCode;
	
	private String countryCode;
	
}
