package diet;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
	private String name;
	private Food food;

	private List<Ingredient> ingredients = new LinkedList<Ingredient>();
	private static class Ingredient {
		final NutritionalElement en;
		final double qty;
		Ingredient(NutritionalElement e, double q){
			this.en=e; this.qty=q;
		}
	}

	/** total weight of ingredients of the recipe */
	private double weight = 0.0;
	
	/**
	 * For the computation of the nutritional values we can
	 * adopt two different strategies:
	 * 
	 * 1. compute the values on-demand by iterating over the ingredients
	 *    This strategy makes use of the method compute
	 *    
	 * 2. maintain a running sum of nutritional values as the ingredients are added.
	 *    This strategy requires a set of additional attributes
	 */
	private static int STRATEGY=1;  // either 1 or 2
	
	// Strategy 1
	/**
	 * Method to compute on-demand the total amount (per 100g)
	 * of a given indicator of the raw materials
	 * used as ingredients in the recipe
	 * 
	 * @param xtractor function to extract the indicator of the raw material
	 * 
	 * @return the total amount per 100g
	 */
	private double compute(BiFunction<NutritionalElement,Double,Double> xtractor) {
		double result=0.0;
		for(Ingredient i : ingredients) {
			result += xtractor.apply(i.en, i.qty);
		}
		return result * 100 / weight;
	}

	// Strategy 2
	/** running sum of recipe total calories  */
	private double calories = 0.0;
	/** running sum of recipe total proteins  */
	private double proteins = 0.0;
	/** running sum of recipe total carbs  */
	private double carbs = 0.0;
	/** running sum of recipe total fat  */
	private double fat = 0.0;
	


	Recipe(String name, Food food) {
		this.name = name;
		this.food = food;
	}

	/**
	 * Adds the given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		NutritionalElement en = food.getRawMaterial(material);

		Ingredient ing = new Ingredient(en,quantity);
		ingredients.add(ing);
		weight += quantity;

		if( STRATEGY == 2 ) {
			calories+=en.getCalories()/100*quantity;
			proteins+=en.getProteins()/100*quantity;
			carbs+=en.getCarbs()/100*quantity;
			fat+=en.getFat()/100*quantity;
		}
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	
	@Override
	public double getCalories() {
		if(STRATEGY==1) {
			return compute(NutritionalElement::getCalories);
		}else{
			return calories * 100 / weight;
		}
	}
	

	@Override
	public double getProteins() {
		if(STRATEGY==1) {
			return compute(NutritionalElement::getProteins);
		}else{
			return proteins * 100 / weight;
		}
	}

	@Override
	public double getCarbs() {
		if(STRATEGY==1) {
			return compute(NutritionalElement::getCarbs);
		}else{
			return carbs * 100 / weight;
		}
	}

	@Override
	public double getFat() {
		if(STRATEGY==1) {
			return compute(NutritionalElement::getFat);
		}else{
			return fat * 100 / weight;
		}
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
	 * A string that contains all the ingredients, one per per line, 
	 * using the following format:
	 * {@code "Material : ###.#"} where <i>Material</i> is the name of the 
	 * raw material and <i>###.#</i> is the relative quantity. 
	 * 
	 * Lines are all terminated with character {@code '\n'} and the ingredients 
	 * must appear in the same order they have been added to the recipe.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Ingredient i : ingredients) {
			sb.append(i.en.getName())
			  .append(" : ")
			  .append(i.qty)
			  .append('\n');
		}
		return sb.toString();
	}

}
