package com.riaan.recipes.recipesapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riaan.recipes.recipesapi.dtos.Recipe;
import com.riaan.recipes.recipesapi.repository.RecipesRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.*;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
class RecipesApiApplicationIT {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test_getAllRecipes() throws Exception {

        File initialState = new ClassPathResource("initial_recipes.json").getFile();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Recipe> expectedResponse = objectMapper.readValue(initialState, new TypeReference<>() {
        });

        mvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()")
                        .value(expectedResponse.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].servings")
                        .value(expectedResponse.get(0).getServings()))
                .andExpect(jsonPath("$[0].ingredients")
                        .value(Matchers.containsInAnyOrder(expectedResponse.get(0).getIngredients().toArray())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vegetarian")
                        .value(expectedResponse.get(0).getVegetarian()));
    }

    @Test
    public void test_getFilteredRecipes() throws Exception {

        File initialState = new ClassPathResource("initial_recipes.json").getFile();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Recipe> expectedResponse = objectMapper.readValue(initialState, new TypeReference<>() {
        });

        final int servings = 2;
        final boolean vegetarian = true;
        final String includeIngredients = "apple";
        final String excludeIngredients = "steak";
        final String instructions = "fruit";

        List<Recipe> expectedFilterResponse = expectedResponse.stream().filter(recipe -> (
                (recipe.getVegetarian() == vegetarian) &&
                (recipe.getServings() == servings) &&
                recipe.getIngredients().contains(includeIngredients) &&
                !recipe.getIngredients().contains(excludeIngredients) &&
                recipe.getInstructions().contains(instructions))
        ).toList();

        mvc.perform(get("/recipes/filtered")
                        .param("servings", String.valueOf(servings))
                        .param("vegetarian", Boolean.toString(vegetarian))
                        .param("includeIngredients", includeIngredients)
                        .param("excludeIngredients", excludeIngredients)
                        .param("instructions", instructions)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()")
                        .value(expectedFilterResponse.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].servings")
                        .value(expectedFilterResponse.get(0).getServings()))
                .andExpect(jsonPath("$[0].ingredients")
                        .value(Matchers.containsInAnyOrder(expectedFilterResponse.get(0).getIngredients().toArray())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vegetarian")
                        .value(expectedFilterResponse.get(0).getVegetarian()));
    }
}
