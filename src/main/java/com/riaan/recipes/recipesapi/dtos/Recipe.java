package com.riaan.recipes.recipesapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("Recipe")
public class Recipe {

    @Id
    private String id;
    private Boolean vegetarian;
    private Integer servings;
    private Set<String> ingredients;
    private String instructions;

    public Recipe(Boolean vegetarian, Integer servings, Set<String> ingredients, String instructions) {
        this.vegetarian = vegetarian;
        this.servings = servings;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }
}
