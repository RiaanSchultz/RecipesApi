package com.riaan.recipes.recipesapi.controllers;

import com.riaan.recipes.recipesapi.dtos.Recipe;
import com.riaan.recipes.recipesapi.repository.RecipesRepository;
import com.riaan.recipes.recipesapi.service.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Controller for retrieving recipes either filtered or unfiltered.
 * Controller for retrieving recipes either filtered or unfiltered.
 */
@RestController
@RequestMapping("/recipes")
public class RecipesController {

    @Autowired
    private RecipesService recipesService;

    /**
     * Return all recipes in the database
     * @return all available recipes
     */
    @GetMapping
    public List<Recipe> getAllRecipes(){
        return recipesService.getAllRecipes();
    }

    /**
     * Filter recipes as per predefined parameters e.g. includeIngredients, vegetarian and servings.
     * @param filters request parameters defining the filtering
     * @return a list of filtered recipes
     */
    @GetMapping("filtered")
    public List<Recipe> getFilteredRecipes(@RequestParam Map<String,String> filters){
        return recipesService.getFilteredRecipes(filters);
    }


}
