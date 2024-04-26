package com.email.support.config;

import java.util.Arrays;
import java.util.List;

import com.email.ApplicationConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 configuration. SpringBoot will auto-bootstrap and generate fully working Swagger API specs for this Module.
 * Access the UI at http://host:port/swagger-ui.html
 * 
 * @author Satya
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(this.apiInfo())
				.ignoredParameterTypes(Pageable.class)	// Use @ApiPageable instead
				.globalOperationParameters(this.globalParams())
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.email.api"))
					.paths(PathSelectors.any())
				.build();				
    }

    /*
     * Build the Swagger API information
     */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("email-services-2023 Rest API")
				.description("Internal REST API for email-services-2023")
				.version("1.0")
				.build();
    }
	
	/*
	 * Build the Swagger Global params list. This is the list of various params that 
	 * will apply globally to all documented APIs
	 */
	private List<Parameter> globalParams() {
		return Arrays.asList(
				// The Pre-Auth Header
				new ParameterBuilder()	
					.name(ApplicationConstants.PREAUTH_HEADER_LABEL)
					.description("Pre-Auth User Identifier")
					.parameterType("header")
					.modelRef(new ModelRef("string"))
					.required(false)
					.build()
		);
	}
}