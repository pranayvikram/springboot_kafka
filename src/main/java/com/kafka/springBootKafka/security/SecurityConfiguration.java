package com.kafka.springBootKafka.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		
		.usersByUsernameQuery("Select username, password, enabled from users where username = ?")
		.authoritiesByUsernameQuery("select username, authority from authorities where username = ?");
		/*.withDefaultSchema()
				.withUser(User.withUsername("admin").password("admin").roles("ADMIN"))
				.withUser(User.withUsername("dba").password("dba").roles("DBA"))
				.withUser(User.withUsername("user").password("pass").roles("USER"));*/
	}
 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/admin").hasRole("ADMIN")
		.antMatchers("/dba").hasRole("DBA")
		.antMatchers("/admin").hasAnyRole("ADMIN", "USER")
		.antMatchers("/")
		.permitAll()
		.and()
		.formLogin();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}