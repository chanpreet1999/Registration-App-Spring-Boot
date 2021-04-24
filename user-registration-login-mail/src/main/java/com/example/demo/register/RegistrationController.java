package com.example.demo.register;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/registration")

@AllArgsConstructor
public class RegistrationController {

	private RegistrationService registrationService;

	@PostMapping
	public String register(@RequestBody RegisterRequest request) {
		return registrationService.register(request);
	}

	@GetMapping(path = "confirm")
	public String confirmEmail(@RequestParam String token) {

		return registrationService.confirm(token);
	}

}
