package com.email.support.config;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.email.ApplicationConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring WebMvc configuration, where custom Http controls and I18N are configured.
 * 
 * @author Satya
 */
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

	@Value("${spring.messages.basename:classpath:locale/messages}")
	private String messagesBasename;
	
	@Value("${spring.mvc.locale:en}")
	private String defaultLocale;

	@Value("${spring.resources.static-locations:/,classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/}")
	private String[] staticLocations;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    }

    @Override
	public void addInterceptors(InterceptorRegistry registry) {
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Add resource - Swagger UI
		//registry.addResourceHandler("/swagger*/**")
		//		.addResourceLocations(this.staticLocations);
		
		// Add resource - webjars
		//registry.addResourceHandler("/webjars/**")
		//		.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
    public Validator getValidator() {
       return this.validator();
    }
	
	/**
	 * Custom LocaleResolver that resolves the locale from the request Accept-Language header or the
	 * lang query parameter. In effect, this custom resolver combines the Spring built-in 
	 * LocaleChangeInterceptor and AcceptHeaderLocaleResolver.
	 * <p>
	 * Precedence is given to the lang query parameter, followed by the Accept-Language value. If 
	 * locale cannot be resolved, then the default locale applies.
	 * <p>
	 * Example: http://url?lang=ar or Accept-Language: ar 
	 */
	@Bean
	public LocaleResolver localeResolver() {
		// Implementation of LocaleResolver class
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver() {
			@Override
			public Locale resolveLocale(HttpServletRequest request) {
				String localeString = request.getParameter(ApplicationConstants.LOCALE_QUERYPARAM);
				return StringUtils.hasText(localeString) 
						? StringUtils.parseLocaleString(localeString)
						: super.resolveLocale(request);
			}
		};

		// Set the default locale
		localeResolver.setDefaultLocale( StringUtils.parseLocaleString(this.defaultLocale) );				
				
		log.info("Default Locale set to: {}", localeResolver.getDefaultLocale());
		
		return localeResolver;	 		
	}
	
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(this.messagesBasename);
        messageSource.setDefaultEncoding("UTF-8");
        
        return messageSource;
    }
   
	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(this.messageSource());

		return bean;
	}
}