/*
 * package com.shopme.admin.security;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.authentication.dao.DaoAuthenticationProvider;
 * import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import
 * org.springframework.security.config.annotation.web.builders.WebSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfiguration; import
 * org.springframework.security.config.annotation.web.configuration.
 * WebSecurityCustomizer; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
 * org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.security.web.SecurityFilterChain;
 * 
 * import com.shopme.admin.ShopmeBackEndApplication;
 * 
 * 
 * @Configuration public class WebSecurityConfig{
 * 
 * @Bean SecurityFilterChain configureHttpSecurity(HttpSecurity http) throws
 * Exception{ http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
 * return http.build(); }
 * 
 * 
 * @Bean public PasswordEncoder passwordEncoder() { return new
 * BCryptPasswordEncoder(); }
 * 
 * @Bean UserDetailsService userDetailsService() { return new
 * ShopmeUserDetailsService(); }
 * 
 * @Bean DaoAuthenticationProvider authenticationProvider() {
 * DaoAuthenticationProvider authProvider =new DaoAuthenticationProvider();
 * authProvider.setUserDetailsService(userDetailsService());
 * authProvider.setPasswordEncoder(passwordEncoder()); return authProvider; }
 * 
 * @Bean SecurityFilterChain configureHttpSecurity(HttpSecurity http) throws
 * Exception{ //http.authenticationProvider(authenticationProvider());
 * 
 * http.authorizeHttpRequests(auth -> auth
 * .requestMatchers("/users/**").hasAuthority("Admin")
 * .requestMatchers("/categories/**").hasAnyAuthority("Admin", "Editor")
 * .anyRequest().authenticated()) http.authorizeRequests()
 * .antMatchers("/users/**").hasRole("Admin")
 * .antMatchers("/category/**").hasAnyAuthority("Admin", "Editor")
 * .anyRequest().permitAll(); ) //.and() .formLogin( form -> form
 * .loginPage("/login") .usernameParameter("email") .permitAll()) .logout(logout
 * -> logout.permitAll()) .rememberMe(rem -> rem .key("abcxyzt")
 * .tokenValiditySeconds(7*24*60*60)); return http.build(); }
 * 
 * @Bean WebSecurityCustomizer configureWebSecurity() throws Exception { return
 * (web) -> web.ignoring().requestMatchers("/images/**", "/js/**",
 * "/webjars/**"); }
 * 
 * 
 * }
 */
package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	UserDetailsService userDetailsService() {
		return new ShopmeUserDetailsService();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	SecurityFilterChain configureHttp(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider());

		http.authorizeHttpRequests(auth -> auth
				//.requestMatchers("/users/**").hasAuthority("Admin")
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
				.loginPage("/login")
				.usernameParameter("email")
				.permitAll())

			.logout(logout -> logout.permitAll())

			.rememberMe(rem -> rem
					.key("AbcDefgHijKlmnOpqrs_1234567890")
					.tokenValiditySeconds(7 * 24 * 60 * 60));

			return http.build();
	}

	@Bean
	WebSecurityCustomizer configureWebSecurity() throws Exception {
		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
	}

}