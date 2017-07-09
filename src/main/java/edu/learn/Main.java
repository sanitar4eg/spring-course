package edu.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 2/4/2016 Time: 12:06 PM
 */
@SpringBootApplication
@EnableWebMvc
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}