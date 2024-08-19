package com.riaan.recipes.recipesapi.service;

import com.riaan.recipes.recipesapi.dtos.Recipe;
import com.riaan.recipes.recipesapi.dtos.RecipeFilters;
import com.riaan.recipes.recipesapi.repository.RecipesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipesServiceTest {

    @Mock
    private RecipesRepository repo;

    @InjectMocks
    private RecipesService recipesService;

    @Captor
    private ArgumentCaptor<Example<Recipe>> recipeCaptor;

    private final static List<Recipe> RECIPES = List.of(
            Recipe.builder()
                    .vegetarian(true)
                    .servings(1)
                    .instructions("N/A")
                    .ingredients(Set.of("apple"))
                    .build(),
            Recipe.builder()
                    .vegetarian(false)
                    .servings(2)
                    .instructions("Cook on pan")
                    .ingredients(Set.of("Steak"))
                    .build()
    );

    @Test
    void getAllRecipes() {
        //given when
        when(repo.findAll()).thenReturn(RECIPES);
        List<Recipe> allRecipes = recipesService.getAllRecipes();
        //then
        assertEquals(allRecipes.size(), RECIPES.size());

    }

    @Test
    void getFilteredRecipes() {
        //given when
        Map<String, String> filters = Map.of(RecipeFilters.VEGETARIAN.value,"true",
                RecipeFilters.SERVINGS.value, "2",
                RecipeFilters.INCLUDE_INGREDIENTS.value,"Steak",
                RecipeFilters.EXCLUDE_INGREDIENTS.value, "apple",
                RecipeFilters.INSTRUCTIONS.value,"pan");

        when(repo.findAll(recipeCaptor.capture())).thenReturn(RECIPES);
        List<Recipe> result= recipesService.getFilteredRecipes(filters);
        verify(repo, times(1)).findAll(recipeCaptor.capture());
        Example<Recipe> recipeExample = recipeCaptor.getValue();
        assertEquals(recipeExample.getProbe().getVegetarian(),
                Boolean.valueOf(filters.get(RecipeFilters.VEGETARIAN.value)));
        assertEquals(recipeExample.getProbe().getServings(),
                Integer.valueOf(filters.get(RecipeFilters.SERVINGS.value)));
        assertEquals(recipeExample.getProbe().getIngredients(),
                Set.of(filters.get(RecipeFilters.INCLUDE_INGREDIENTS.value)));
        assertEquals(recipeExample.getProbe().getInstructions(),
                filters.get(RecipeFilters.INSTRUCTIONS.value));
        assertFalse(result.stream().anyMatch(recipe ->
                recipe.getIngredients().stream().anyMatch(
                        ingredient ->
                                ingredient.equals(filters.get(RecipeFilters.EXCLUDE_INGREDIENTS.value)))
        ));
    }
}