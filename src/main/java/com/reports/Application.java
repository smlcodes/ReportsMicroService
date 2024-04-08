package com.reports;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Application class
 *
 * @author Satya
 */
@SpringBootApplication
@Configuration
@Slf4j
@EnableJpaAuditing
@EnableEnversRepositories
@EnableJpaRepositories(basePackages = {"com.reports"}, repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EnableScheduling
@EnableCaching
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);

        // Start Application
        app.run(args);
    }

    @EventListener(ApplicationReadyEvent.class)
    void onApplicationReady(ApplicationReadyEvent event) {
        Environment env = event.getApplicationContext().getEnvironment();
        log.info("Application Ready!! \n\tListening on address: {} and port: {} \n\tProfiles: {}",
                env.getProperty("server.address"),
                env.getProperty("server.port"),
                env.getActiveProfiles()
        );

        log.info("\n Swagger : http://localhost:" + env.getProperty("server.port") + "/reports-service/swagger-ui/index.html");
        log.info("Basic base64(username:password) :dXNlcjpwYXNzd29yZA==");
    }

/*	@PostConstruct
	void afterStartup(){
		log.info("\n \nAfter StartUp");

	}*/
}
