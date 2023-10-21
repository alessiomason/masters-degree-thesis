package diet1;

import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement, Comparable<Menu> {
	
	private String name;
	private double totalCalories;
	private double totalProteins;
	private double totalCarbohydrates;
	private double totalFat;
	private Food foodContainer;	// classe Food che contiene i NutritionalElement da inserire nel menù
	private Collection<NutritionalElement> recipes;
	private Collection<NutritionalElement> products;
	
	public Menu(String name, Food foodContainer) {
		this.name = name;
		this.foodContainer = foodContainer;
		this.totalCalories = 0;
		this.totalProteins = 0;
		this.totalCarbohydrates = 0;
		this.totalFat = 0;
		this.recipes = new HashSet<>();
		this.products = new HashSet<>();
	}
	
	/**
	 * Adds a given serving size of a recipe.
	 * 
	 * The recipe is a name of a recipe defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
	
	public Menu addRecipe(String recipe, double quantity) {
		NutritionalElement r = foodContainer.getRecipe(recipe);
		recipes.add(r);
		
		totalCalories += r.getCalories()/100*quantity;
		totalProteins += r.getProteins()/100*quantity;
		totalCarbohydrates += r.getCarbs()/100*quantity;
		totalFat += r.getFat()/100*quantity;
		
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
	public Menu addProduct(String product) {
		NutritionalElement p = foodContainer.getProduct(product);
		recipes.add(p);
		
		totalCalories += p.getCalories();
		totalProteins += p.getProteins();
		totalCarbohydrates += p.getCarbs();
		totalFat += p.getFat();
		
		return this;
	}

	/**
	 * Name of the menu
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		return totalCalories;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		return totalProteins;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		return totalCarbohydrates;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		return totalFat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean 	indicator
	 */
	@Override
	public boolean per100g() {
		// nutritional values are provided for the whole menu.
		return false;
	}
	
	public int compareTo(Menu m) {
		return this.getName().compareTo(m.getName());
	}
}
