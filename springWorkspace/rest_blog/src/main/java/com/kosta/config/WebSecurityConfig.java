package com.kosta.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.kosta.repository.UserRepository;
import com.kosta.security.JwtAuthenticationFilter;
import com.kosta.security.JwtAuthenticationService;
import com.kosta.security.JwtProperties;
import com.kosta.security.JwtProvider;
import com.kosta.security.LoginCustomAuthenticationFilter;
import com.kosta.util.TokenUtils;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final UserDetailsService userDetailsService;
	private final JwtProperties jwtProperties;
	private final UserRepository userRepository;

	// JWT PROVIDER
	@Bean
	JwtProvider jwtProvider() {
		return new JwtProvider(userDetailsService, jwtProperties);
	}

	private TokenUtils tokenUtils() {
		return new TokenUtils(jwtProvider());
	}

	private JwtAuthenticationService jwtAuthenticationService() {
		return new JwtAuthenticationService(tokenUtils(), userRepository);
	}

	// 인증 관리자 (AuthenticationManager) 설정
	@Bean
	AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return new ProviderManager(authProvider);
	}

	// 암호화 빈
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// HRRP 요청에 따른 보안 구성
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// 경로 권한 설정
		http.authorizeHttpRequests(auth ->
		// 특정 URL 경로에 대해서는 인증 없이 접근 가능
		auth.requestMatchers(new AntPathRequestMatcher("/api/auth/signup"), // 회원가입
				new AntPathRequestMatcher("/api/auth/duplicate"), // 이메일 중복체크
				new AntPathRequestMatcher("/img/**"), // 이미지
				new AntPathRequestMatcher("/api/auth/refresh-token"), // 토큰 재발급
				new AntPathRequestMatcher("/api/post/**", "GET")).permitAll()
				// AuthController 중 나머지들은 "ADMIN" 만 가능
				.requestMatchers(new AntPathRequestMatcher("/api/auth")).hasRole("ADMIN")
				// 그 밖의 다른 요청들은 인증을 통과한 (로그인한) 사용자만 모두 접근할 수 있도록 한다
				.anyRequest().authenticated());
		// 무상태성 세션 관리
		http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// 특정 경로데 대한 필터 추가
		http.addFilterBefore(new LoginCustomAuthenticationFilter(authenticationManager(), jwtAuthenticationService()),
				UsernamePasswordAuthenticationFilter.class);

		// (토큰을 통해 검증할 수 있도록) 필터 추가
		http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider()), UsernamePasswordAuthenticationFilter.class);
		// HTTP 기본설정
		http.httpBasic(HttpBasicConfigurer::disable);
		// CSRF 비활성화
		http.csrf(AbstractHttpConfigurer::disable);
		// CORS 설정
		http.cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()));

		return http.getOrBuild();
	}

	// CORS 설정을 정의
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		return request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedHeaders(Collections.singletonList("*"));
			config.setAllowedMethods(Collections.singletonList("*"));
			config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000"));
			config.setAllowCredentials(true);
			return config;
		};
	}
}
