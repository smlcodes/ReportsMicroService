package com.reports;

import org.springframework.context.annotation.Configuration;

//import com.reports.dao.entity.Employee;
//import com.reports.dao.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Application-specific configuration, which may include Beans, Initializations, etc.
 */
@Configuration
@Slf4j
public class ApplicationConfig { 

	/*
    @Bean
    CommandLineRunner bootstrapEntities(EmployeeRepository employeeRepository) {
        return args -> {
        	// Do Something ...
        	List<Employee> employees = Arrays.asList(
        			new Employee("Bob", "Dylan"),
        			new Employee("Omar", "Sherrif"),
        			new Employee("Ali", "Baba")
        	);
        	            
            log.debug("Initializer - Bootstrapping database");
            log.debug("Preloading " + employeeRepository.saveAll(employees));
        };
    }

	 */
}