package com.riaan.recipes.recipesapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@Configuration
public class DatabaseConfiguration {

    /**
     * Prepulate database with default recipes
     * @return the populator bean with resources set to contents of /resources/initial_recipes.json.
     */
    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
        Resource sourceData = new ClassPathResource("initial_recipes.json");
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[] { sourceData });
        return factory;
    }
}