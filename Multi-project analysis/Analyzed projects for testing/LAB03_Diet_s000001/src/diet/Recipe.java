package diet1;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a set of ingredients, that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement, Comparable<Recipe> {
	
	private static class Ingredient {	// static: non può accedere ai valori d'istanza dell'outer class
		private NutritionalElement rawMaterial;
		private double quantity;
		
		public Ingredient(NutritionalElement rawMaterial, double quantity) {
			this.rawMaterial = rawMaterial;
			this.quantity = quantity;
		}

		@Override
		public String toString() {
			return rawMaterial.getName() + " : " + String.format("%.1f", quantity) + "\n";
		}
	}
	
	private String name;
	private double totalCalories;
	private double totalProteins;
	private double totalCarbohydrates;
	private double totalFat;
	private double totalQuantity;
	private Food foodContainer;	// classe Food che contiene i NutritionalElement da inserire nella ricetta
    private Queue<Ingredient> ingredients;

	public Recipe(String name, Food foodContainer) {
		this.name = name;
		this.foodContainer = foodContainer;
		this.totalCalories = 0;
		this.totalProteins = 0;
		this.totalCarbohydrates = 0;
		this.totalFat = 0;
		this.totalQuantity = 0;
		ingredients = new ArrayDeque<>();
	}

	/**
	 * Adds a given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		NutritionalElement rm = foodContainer.getRawMaterial(material);
		Ingredient i = new Ingredient(rm, quantity);
		ingredients.add(i);
		
		totalCalories += rm.getCalories()/100*quantity;
		totalProteins += rm.getProteins()/100*quantity;
		totalCarbohydrates += rm.getCarbs()/100*quantity;
		totalFat += rm.getFat()/100*quantity;
		totalQuantity += quantity;
		
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getCalories() {
		return totalCalories/totalQuantity*100;
	}

	@Override
	public double getProteins() {
		return totalProteins/totalQuantity*100;
	}

	@Override
	public double getCarbs() {
		return totalCarbohydrates/totalQuantity*100;
	}

	@Override
	public double getFat() {
		return totalFat/totalQuantity*100;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
	
	/**
	 * Returns the ingredients composing the recipe.
	 * 
	 * A string that contains all the ingredients, one per line, 
	 * using the following format:
	 * {@code "Material : ###.#"} where <i>Material</i> is the name of the 
	 * raw material and <i>###.#</i> is the relative quantity. 
	 * 
	 * Lines are all terminated with character {@code '\n'} and the ingredients 
	 * must appear in the same order they have been added to the recipe.
	 */
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		
		for (Ingredient i:ingredients)
			output.append(i);
		
		return output.toString();
	}

	@Override
	public int compareTo(Recipe r) {
		return this.getName().compareTo(r.getName());
	}
}
