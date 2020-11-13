package com.example.demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.MessageDTO;

public class ResponseUtil {

	public static ResponseEntity<MessageDTO> ok(MessageDTO message) {
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	public static ResponseEntity<MessageDTO> created(MessageDTO message) {
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}	
	
	public static ResponseEntity<MessageDTO> error(MessageDTO message) {
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
