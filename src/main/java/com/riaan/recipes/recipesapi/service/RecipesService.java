package com.riaan.recipes.recipesapi.service;

import com.mongodb.BasicDBList;
import com.riaan.recipes.recipesapi.dtos.Recipe;
import com.riaan.recipes.recipesapi.dtos.RecipeFilters;
import com.riaan.recipes.recipesapi.repository.RecipesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The service handling recipe repository lookups
 */
@Service
@Transactional
public class RecipesService {

    @Autowired
    private RecipesRepository recipesRepository;

    /**
     * find all recipes in the repo
     * @return
     */
    public List<Recipe> getAllRecipes() {
        return recipesRepository.findAll();
    }

    /**
     * Filter all recipes in the repo with provider request parameters
     * @param filters the request parameter the recipes should be filtered on
     * @return a list of filtered recipes
     */
    public List<Recipe> getFilteredRecipes(Map<String, String> filters) {
        Recipe recipe = new Recipe();
        recipe.setServings(filters.get(RecipeFilters.SERVINGS.value) != null ?
                Integer.valueOf(filters.get(RecipeFilters.SERVINGS.value)) : null);
        recipe.setVegetarian(filters.get(RecipeFilters.VEGETARIAN.value) != null ?
                Boolean.valueOf(filters.get(RecipeFilters.VEGETARIAN.value)) : null);
        recipe.setIngredients(filters.get(RecipeFilters.INCLUDE_INGREDIENTS.value) != null ?
                Set.of(filters.get(RecipeFilters.INCLUDE_INGREDIENTS.value).split(",")) : null);
        recipe.setInstructions(filters.get(RecipeFilters.INSTRUCTIONS.value));

        ExampleMatcher filter = ExampleMatcher.matching()
                .withMatcher("instructions", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("ingredients", match ->
                        match.transform(source -> Optional.of(
                                ((List) source.get()).iterator().next())).exact());


        List<Recipe> filteredRecipes = recipesRepository.findAll(Example.of(recipe, filter));
        if ((filters.get(RecipeFilters.EXCLUDE_INGREDIENTS.value) != null) && !filteredRecipes.isEmpty()) {
            return filteredRecipes.stream().filter(filteredRecipe ->
                    !filteredRecipe.getIngredients().contains(filters.get(RecipeFilters.EXCLUDE_INGREDIENTS.value))).toList();
        }
        return filteredRecipes;
    }
}
