package edu.learn.beans.configuration;

import static edu.learn.beans.models.Authority.BOOKING_MANAGER;
import static edu.learn.beans.models.Authority.REGISTERED_USER;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic()
			.and()
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/booking/bookTicket").hasAuthority(BOOKING_MANAGER.toString())
			.anyRequest().hasAuthority(REGISTERED_USER.toString())

			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll();
	}
}
