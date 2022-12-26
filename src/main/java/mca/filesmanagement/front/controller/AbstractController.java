package mca.filesmanagement.front.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractController {
	
	protected final String HAS_ADMIN = "hasAuthority('ADMIN')";

	protected final String getToken() {
		String token = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		token = authentication.getPrincipal().toString();
		//		if (authentication instanceof OAuth2Authentication) {
//			OAuth2Authentication authToken = (OAuth2Authentication) authentication;
//		    OAuth2AuthenticationDetails detail = (OAuth2AuthenticationDetails) authToken.getDetails(); 
//		    token = detail.getTokenValue();
//		}
		return token;
	}
}
