package com.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final UserDetailsService userDetailsService;
	// HTTP 요청에 따른 보안 구성
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth ->
		// 누구나 접근가능
		auth
			.requestMatchers(
					new AntPathRequestMatcher("/"),
					new AntPathRequestMatcher("/login"),
					new AntPathRequestMatcher("/logout"),
					new AntPathRequestMatcher("/join"),
					new AntPathRequestMatcher("/index")
			).permitAll()
			
//			// 관리자 -> 전부 접속가능
//			.requestMatchers(
//						new AntPathRequestMatcher("/**"),
//						new AntPathRequestMatcher("/group/**")
//						).hasRole("LEADER")
				// 로그인된 사용자만 접근 가능
				.requestMatchers("/mypage/**","/group/**").authenticated()
				// 나머지는 인증된 사용자
				.anyRequest().authenticated());
		
		http.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/group").loginProcessingUrl("/login"));
		http.logout(logout -> logout.logoutSuccessUrl("/").invalidateHttpSession(true));
		http.csrf(AbstractHttpConfigurer::disable);
		http.cors(AbstractHttpConfigurer::disable);
		
		return http.getOrBuild();
		
	}
	
	@Bean
	BCryptPasswordEncoder bc() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bc, UserDetailsService uds) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(bc);
		return new ProviderManager(authProvider);
	}
	
}
