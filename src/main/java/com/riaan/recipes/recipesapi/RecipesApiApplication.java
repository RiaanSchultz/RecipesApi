package com.riaan.recipes.recipesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class RecipesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipesApiApplication.class, args);
    }

}
