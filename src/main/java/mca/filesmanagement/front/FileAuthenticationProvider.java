package mca.filesmanagement.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

@Service
public class FileAuthenticationProvider implements AuthenticationProvider {
	
	private static Logger LOGGER = LoggerFactory.getLogger(FileAuthenticationProvider.class);
	
	@Value("${mca.filesmanagement.front.oauth2.clientid}")
	private String oauthClientId;
	
	@Value("${mca.filesmanagement.front.oauth2.clientsecret}")
	private String oauthSecret;
	
	@Value("${mca.filesmanagement.front.oauth2.tokenendpointurl}")
	private String tokenendpointurl;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public FileAuthenticationProvider() {
		super();
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		LOGGER.info(String.format("Autenticando a [%s]", authentication.getName()));
		try {
			if (authentication.getPrincipal() != null
					&& authentication.getCredentials() != null) {
				final String password = authentication.getCredentials().toString();
				LOGGER.info(String.format("password: [%s]", password));	
				
				
				ResponseEntity<Oauth2Response> response = this.getOauthResponse(authentication.getName(), password);
				LOGGER.info(response.toString());
				
				if (HttpStatus.OK.equals(response.getStatusCode())){
					Oauth2Response oauth2Response = response.getBody();
					return new UsernamePasswordAuthenticationToken(oauth2Response.getAccessToken(), authentication.getCredentials(), authentication.getAuthorities());
				}
			}			
		} 
		catch (Exception e) {
			LOGGER.error(String.format("Error al llamar al servicio [%s]", this.tokenendpointurl));
			LOGGER.error("Error en la autenticación", e);
		}

		throw new BadCredentialsException("Autenticación inválida");
	}
	
	private ResponseEntity<Oauth2Response> getOauthResponse(String user, String password) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(this.oauthClientId, this.oauthSecret);
        
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("username", user);
        body.add("password", password);
        body.add("grant_type", "password");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        
        return this.restTemplate.postForEntity(tokenendpointurl, requestEntity, Oauth2Response.class);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
	public static class Oauth2Response {
		
		@JsonProperty("access_token")
		private String accessToken;
		
		@JsonProperty("token_type")
		private String tokenType;
		
		@JsonProperty("refresh_token")
		private String refreshToken;
		
		@JsonProperty("expires_in")
		private Long expiresIn;
		
		@JsonProperty("scope")
		private String scope;
		
		public Oauth2Response() {
			super();
		}

		/**
		 * @return the accessToken
		 */
		public String getAccessToken() {
			return accessToken;
		}

		/**
		 * @param accessToken the accessToken to set
		 */
		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}

		/**
		 * @return the tokenType
		 */
		public String getTokenType() {
			return tokenType;
		}

		/**
		 * @param tokenType the tokenType to set
		 */
		public void setTokenType(String tokenType) {
			this.tokenType = tokenType;
		}

		/**
		 * @return the refreshToken
		 */
		public String getRefreshToken() {
			return refreshToken;
		}

		/**
		 * @param refreshToken the refreshToken to set
		 */
		public void setRefreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
		}

		/**
		 * @return the expiresIn
		 */
		public Long getExpiresIn() {
			return expiresIn;
		}

		/**
		 * @param expiresIn the expiresIn to set
		 */
		public void setExpiresIn(Long expiresIn) {
			this.expiresIn = expiresIn;
		}

		/**
		 * @return the scope
		 */
		public String getScope() {
			return scope;
		}

		/**
		 * @param scope the scope to set
		 */
		public void setScope(String scope) {
			this.scope = scope;
		}

		@Override
		public String toString() {
			return String.format(
					"Oauth2Response [accessToken=%s, tokenType=%s, refreshToken=%s, expiresIn=%s, scope=%s]",
					accessToken, tokenType, refreshToken, expiresIn, scope);
		}
	}
}
