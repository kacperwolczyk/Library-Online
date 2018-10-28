package library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConig extends WebSecurityConfigurerAdapter{

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
				.antMatchers("/css/**", "/img/**").permitAll()
				.antMatchers("/", "/register", "/booklist").permitAll()
				.antMatchers("/adminpanel", "/adminpanel/searchuser", "/adminpanel/bookform").hasAnyRole("ADMIN")
				.antMatchers("/userpanel").hasAnyRole("USER")
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/loginform")
				.defaultSuccessUrl("/")
				.permitAll()
			.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/loginform")
				.permitAll();
	}

}
