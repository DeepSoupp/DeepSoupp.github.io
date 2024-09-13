package com.kosta.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final UserDetailsService userDetailsService;

	@Bean
	WebSecurityCustomizer configure() {
		return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/static/**/"));
	}

	@Bean
	SecurityFilterChain scfc(HttpSecurity hs) throws Exception {
		return hs
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(new AntPathRequestMatcher("/login"), new AntPathRequestMatcher("/join"))
						.permitAll().anyRequest().authenticated())
				.formLogin(formlogin -> formlogin.loginPage("/login").defaultSuccessUrl("/blog/list"))
				.logout(logout -> logout.logoutSuccessUrl("/login").invalidateHttpSession(true))
				.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable).build();
	}

	@Bean
	AuthenticationManager atcm(HttpSecurity hs, BCryptPasswordEncoder bc, UserDetailsService us) throws Exception {
		DaoAuthenticationProvider authP = new DaoAuthenticationProvider();
		authP.setUserDetailsService(us);
		authP.setPasswordEncoder(bc);
		return new ProviderManager(authP);
	}

	@Bean
	BCryptPasswordEncoder bc() {
		return new BCryptPasswordEncoder();
	}
}
