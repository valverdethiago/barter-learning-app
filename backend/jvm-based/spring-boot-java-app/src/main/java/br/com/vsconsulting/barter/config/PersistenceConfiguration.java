package br.com.vsconsulting.barter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "br.com.vsconsulting.barter.repository")
@Configuration
public class PersistenceConfiguration {

}
