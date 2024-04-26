
package com.email.support.config;

/**
 * Security configuration based on the Spring Security setup.
 * <p>
 * Since Micro-Services do not participate in authentication of users, and the authentication is delegated
 * to an external Authentication Server, then this configuration wires the Pre-Authentication settings required 
 * to extract the authenticated user token from the header.
 * <p>
 * By default, all APIs at path /api will be secured against the pre-authenticated header.
 * 
 * @author Satya
 */
/*
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Configure Security Policy
		http			
			.csrf().disable()	// CSRF does not apply in REST app
			.addFilterAfter(this.preAuthenticationFilter(), RequestHeaderAuthenticationFilter.class)
			.authorizeRequests()
				// Authenticate /api/** requests - Will use the RequestHeaderPreAuthenticationFilter
				.antMatchers(ApplicationConstants.API_BASE + "v*//**").hasAuthority(ApplicationConstants.PREAUTH_USER_ROLE) //authenticated()
			.and()
				// REST API is stateless, hence no session. Caching will be used instead
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				// For Pre-Authenticated setup use a 403 Forbidden entry point
				.exceptionHandling()
					.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
			.and()
				.headers().frameOptions().disable();	// FrameOptions do not apply in REST app				
	}

    @Override
    protected AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(this.authenticationProvider()));
    }

	@Bean
	public RequestHeaderAuthenticationFilter preAuthenticationFilter() {
		RequestHeaderAuthenticationFilter preAuthenticationFilter = new RequestHeaderAuthenticationFilter();
		preAuthenticationFilter.setPrincipalRequestHeader(ApplicationConstants.PREAUTH_HEADER_LABEL);
		preAuthenticationFilter.setCredentialsRequestHeader(ApplicationConstants.PREAUTH_HEADER_LABEL);
		preAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
		// Turn off exceptions since we have configured a 403 for the security policy
		preAuthenticationFilter.setExceptionIfHeaderMissing(false);

		return preAuthenticationFilter;
	}


    @Bean
    public AuthenticationProvider authenticationProvider() {
        PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
        authenticationProvider.setPreAuthenticatedUserDetailsService(this.userDetailsServiceWrapper());
        // Turn off exceptions since we have configured a 403 for the security policy
        authenticationProvider.setThrowExceptionWhenTokenRejected(false);

        return authenticationProvider;
    }

    @Bean
    public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper() {
        return null; // new AuthorizationUserDetailsService();
    }
}
 */