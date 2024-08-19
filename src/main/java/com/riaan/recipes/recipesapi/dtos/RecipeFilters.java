package com.riaan.recipes.recipesapi.dtos;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RecipeFilters {
    VEGETARIAN("vegetarian"),
    SERVINGS("servings"),
    INCLUDE_INGREDIENTS("includeIngredients"),
    EXCLUDE_INGREDIENTS("excludeIngredients"),
    INSTRUCTIONS("instructions");

    public final String value;
}
