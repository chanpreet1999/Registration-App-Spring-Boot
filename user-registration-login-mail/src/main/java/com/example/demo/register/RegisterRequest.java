package com.example.demo.register;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class RegisterRequest {

	private String firstname;
	private String lastname;
	private String password;
	private String email;
	
}
