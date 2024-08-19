# Recipes API

### Startup
This spring boot API can be startup up by simply running the default start configuration in your IDE and running a
MongoDB in the default port. Please add a database named 'Recipes', and a collection named 'Recipe'.
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

### Architectural choices
My choices on what stack to use relied on what I consider relevant in the current development world.
I used springboot 3 and Java 17 which are relatively new, along with MongoDB which I chose because I have experience with it.

I've decided to pre-load a couple of recipes since I prefer keeping the service independent of database setup and
loading. This way one can merely start a mongoDb instance in default mode, add a Recipes Database and Recipe collection
and start using this API. It's easy to add new json recipes based on the default response.