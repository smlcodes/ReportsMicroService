/*
package com.email.support.security;

import java.util.Collections;

import com.email.ApplicationConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import lombok.extern.slf4j.Slf4j;

*/
/**
 * Spring Security UserDetails Service class for pre-authenticated requests.
 * <p>
 * Pre-authenticated requests inject a header token of the user id that has already been authenticated
 * by an external Authentication Server.
 * <p>
 * This class is responsible for applying verification of the token and loading the associated user and 
 * roles. No authentication is actually performed here
 *
 *//*

@Slf4j
public class AuthorizationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	*/
/**
	 * Load and return a UserDetails instance of the Pre-Autenticated user token.
	 * <p>
	 * Caching is enabled here since the Security Config is Stateless (SessionCreationPolicy.STATELESS),
	 * then there is no session management, which can cause performance issues due to the creation of a
	 * new Security Context for every request. 
	 * <p>
	 * NOTE: Ensure the caching configuration declares the Cache name of <b>PreAuthUsers</b>
	 * 
	 *//*

	@Override
	@Cacheable(value = "PreAuthUsers", key = "#token")
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		String userId = token.getCredentials().toString();
        log.debug("Detected Pre-Authenticated token for user: {}", userId);
        
        // Return a wrapped User object representing the pre-authenticated token.
        // If no token, the Spring Security framework will throw an exception before arriving at this method
		return new User(userId, userId, Collections.singletonList(new SimpleGrantedAuthority(ApplicationConstants.PREAUTH_USER_ROLE)));
	}

}
*/
