package com.example.demo.util;

import java.util.regex.Pattern;

public class ValidatorUtil {

	private static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

	public static boolean validateEmail(String email) {
		boolean flag = false;
		flag = email.matches(EMAIL_REGEX);
		return flag;
	}

	public static boolean validatePassword(String password) {
		boolean flag = false;
		flag = Pattern.matches("([A-Z]{1})([a-z]+)([0-9]{2})", password);
		return flag;
	}	
	
}
