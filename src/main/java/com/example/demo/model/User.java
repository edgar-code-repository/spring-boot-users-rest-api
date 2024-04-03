package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="tbl_user")
@Getter
@Setter
@NoArgsConstructor
public class User {
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")	
	private UUID id;

	private String name;
	
	private String email;
	
	private String password;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime lastModifiedDate;
	
	private LocalDateTime lastLogin;
	
	private String tokenJWT;
	
	private boolean isActive;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Phone> phones = new ArrayList<>();

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
		for(Phone phone: phones) {
			phone.setUser(this);
		}
	}

	
}
