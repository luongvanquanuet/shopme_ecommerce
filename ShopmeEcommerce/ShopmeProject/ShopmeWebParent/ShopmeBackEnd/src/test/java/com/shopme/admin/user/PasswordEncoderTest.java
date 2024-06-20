package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	@Test
	public void testEncoderPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "quan1997";
		String encodedPassword = passwordEncoder.encode(rawPassword);

		System.out.println(encodedPassword);
		boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
		//assertThat(null)
		assertThat(matches).isTrue();
	}
}
