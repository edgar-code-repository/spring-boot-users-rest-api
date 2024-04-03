package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>{

	public User findByEmail(String email);
	
}
