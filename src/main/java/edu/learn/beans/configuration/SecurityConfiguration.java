package edu.learn.beans.configuration;

import static edu.learn.beans.models.Authority.BOOKING_MANAGER;
import static edu.learn.beans.models.Authority.REGISTERED_USER;

import edu.learn.beans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public SecurityConfiguration(UserService userService,
		PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic()
			.and()
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/ws/**").permitAll()
			.antMatchers("/booking/bookTicket").hasAuthority(BOOKING_MANAGER.toString())
			.anyRequest().hasAuthority(REGISTERED_USER.toString())

			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.and()
			.rememberMe().tokenRepository(persistentTokenRepository())
			.tokenValiditySeconds(1209600);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		return new InMemoryTokenRepositoryImpl();
	}
}
