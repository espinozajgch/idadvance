package com.inadvance.prueba.controller;

import com.inadvance.prueba.dto.LoginDto;
import com.inadvance.prueba.security.JWTAuthtenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@Autowired
	JWTAuthtenticationConfig jwtAuthtenticationConfig;

	@PostMapping("login")
	public LoginDto login(
			@RequestParam("user") String username,
			@RequestParam("encryptedPass") String encryptedPass) {

		/**
		 * En el ejemplo no se realiza la correcta validación del usuario
		 */
		String token = jwtAuthtenticationConfig.getJWTToken(username);
		return LoginDto.builder()
				.user(username)
				.pass(encryptedPass)
				.token(token)
				.build();
		
	}

}
