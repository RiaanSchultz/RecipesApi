package com.riaan.recipes.recipesapi.repository;

import com.riaan.recipes.recipesapi.dtos.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipesRepository extends MongoRepository<Recipe, String> {

}
