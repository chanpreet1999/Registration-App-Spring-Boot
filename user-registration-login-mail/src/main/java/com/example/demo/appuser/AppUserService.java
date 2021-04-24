package com.example.demo.appuser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.register.token.ConfirmationToken;
import com.example.demo.register.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

	private final static String USER_NOT_FOUND = "user with name %s not found";
	private final static String EMAIL_ALREADY_EXISTS = "Email %s already registered";

	private final AppUserRepository appUserRespositorty;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return appUserRespositorty.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)));
	}

	public String signUpUser(AppUser appUser) {
		Boolean emailAlreadyRegistered = appUserRespositorty.findByEmail(appUser.getEmail()).isPresent();

		if (emailAlreadyRegistered) {
			throw new IllegalStateException(String.format(EMAIL_ALREADY_EXISTS, appUser.getEmail()));
		}

		String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
		appUser.setPassword(encodedPassword);
		appUserRespositorty.save(appUser);

		// create token
		String token = UUID.randomUUID().toString();
		confirmationTokenService.saveConfirmationToken(
				new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser));

		// TODO : send verification code for validation

		return token;
	}

	public int enableAppUser(AppUser appUser) {
		return appUserRespositorty.enableAppUser(appUser.getEmail());
	}

}
