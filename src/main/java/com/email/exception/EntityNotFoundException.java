package com.email.exception;

import lombok.ToString;
import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * General purpose Business Exception to mark that an Entity is not found
 * 
 * @author Ameer Qudsiyeh
 */
@SuppressWarnings("serial")
@ToString
@ExceptionMapping(statusCode = HttpStatus.CONFLICT, errorCode = "NotFound.Entity.message")
public class EntityNotFoundException extends RuntimeException {

	@ExposeAsArg(0) private String entity;
	@ExposeAsArg(1) private String query;
	
	/**
	 * Create the exception with the Entity class and Id value
	 * @param clazz The alerts Class
	 * @param id The alerts id
	 */
	public EntityNotFoundException(Class<?> clazz, Number id) {
		this(clazz, Map.of("id", id));
	}
	
	/**
	 * Create the exception with the Entity class and a map of query parameters
	 * @param clazz The alerts class
	 * @param queryParams A map of query parameters used to find the Entity
	 * @see {@link Map.of}
	 */
	public EntityNotFoundException(Class<?> clazz, Map<String, Object> queryParams) {
		super("Entity: " + clazz.getSimpleName() + " not found for query: " + queryParams);
		this.entity = clazz.getSimpleName();
		this.query = queryParams.toString();
	}
}
