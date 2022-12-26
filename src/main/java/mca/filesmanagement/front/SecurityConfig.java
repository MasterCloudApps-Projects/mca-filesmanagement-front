package mca.filesmanagement.front;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http//.authenticationProvider(authenticaionProvider)
			.csrf().disable().authorizeHttpRequests() // To all the requests
				.antMatchers("/login", "/static/*", "/static/*/*", "/403", "/css/*", "/js/*").permitAll()
				.anyRequest() // Every request
				.authenticated() // Must be authenticated
				.and()
				.formLogin()
				.loginPage("/login")
				//.loginProcessingUrl("/perform_login")
				.defaultSuccessUrl("/fileslist", true)
				.failureUrl("/403")
				//.failureHandler(authenticationFailureHandler())
				.and()
				.logout()
				//.logoutUrl("/perform_logout")
				//.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/login.html?logout=true")
				//.logoutSuccessHandler(logoutSuccessHandler())
				; // Through a basic authentication mechanism
	}

	private LogoutSuccessHandler logoutSuccessHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	private AuthenticationFailureHandler authenticationFailureHandler() {
		// TODO Auto-generated method stub
		return null;
	}
}
