# Recipes API

### Startup
This spring boot API can be startup up by simply running the default start configuration in your IDE and running a 
MongoDB in the default port. 
If a different URL or port is used, please update the following property in the applications.properties file:
spring.data.mongodb.uri=mongodb://localhost:27017/Recipes

The following optional request parameters are supported for filtering:
- vegetarian : denotes whether this recipe contains no meat
- servings: the amount of servings for given ingredients
- includeIngredients: recipe should contain these ingredients
- excludeIngredients: recipe should not contain these ingredients
- instructions: the recipe instructions

### Data
Please override the recipes in the initial_recipes.json file as required. Currently only placeholders are loaded.

### Endpoints
Two endpoints are provided:
- GET /recipes
- GET /recipes/filtered

The former returns all recipes in the database.
The latter accepts aforementioned request parameters that may be added as such:
http://localhost:8080/recipes/filtered?servings=2&vegetarian=false&includeIngredients=appel2&instructions=ucts&excludeIngredients=ui

### Tests
The Recipe service is unit tested in RecipesServiceTest.
The API is integrated tested in RecipesApiApplicationIT