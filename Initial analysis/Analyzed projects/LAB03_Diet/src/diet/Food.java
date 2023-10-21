package diet;

import java.util.Collection;
import java.util.TreeSet;


/**
 * Facade class for the diet management.
 * It allows defining and retrieving raw materials and products.
 *
 */
public class Food {

	private Collection<NutritionalElement> rawMaterials;
	private Collection<NutritionalElement> products;
	private Collection<NutritionalElement> recipes;
	private Collection<NutritionalElement> menus;
	
	public Food() {
		rawMaterials = new TreeSet<>();
		products = new TreeSet<>();
//		products = new TreeSet<>((p1, p2) -> p1.getName().compareTo(p2.getName()));
		recipes = new TreeSet<>();
		menus = new TreeSet<>();
	}

	/**
	 * Define a new raw material.
	 * 
	 * The nutritional values are specified for a conventional 100g amount
	 * @param name 		unique name of the raw material
	 * @param calories	calories per 100g
	 * @param proteins	proteins per 100g
	 * @param carbs		carbs per 100g
	 * @param fat 		fats per 100g
	 */
	public void defineRawMaterial(String name, double calories, double proteins, double carbs,  double fat) {
		RawMaterial rm = new RawMaterial(name, calories, proteins, carbs, fat);
		rawMaterials.add(rm);
	}
	
	/**
	 * Retrieves the collection of all defined raw materials
	 * 
	 * @return collection of raw materials through the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> rawMaterials() {
		return rawMaterials;
	}
	
	/**
	 * Retrieves a specific raw material, given its name
	 * 
	 * @param name  name of the raw material
	 * 
	 * @return  a raw material through the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRawMaterial(String name) {
		for (NutritionalElement e:rawMaterials) {
			if (e.getName() == name)
				return e;
		}
		
		return null;
	}

	/**
	 * Define a new packaged product.
	 * The nutritional values are specified for a unit of the product
	 * 
	 * @param name 		unique name of the product
	 * @param calories	calories for a product unit
	 * @param proteins	proteins for a product unit
	 * @param carbs		carbs for a product unit
	 * @param fat 		fats for a product unit
	 */
	public void defineProduct(String name, double calories, double proteins,  double carbs, double fat) {
		Product p = new Product(name, calories, proteins, carbs, fat);
		products.add(p);
	}
	
	/**
	 * Retrieves the collection of all defined products
	 * 
	 * @return collection of products though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> products() {
		return products;
	}
	
	/**
	 * Retrieves a specific product, given its name
	 * @param name  name of the product
	 * @return  a product through the {@link NutritionalElement} interface
	 */
	public NutritionalElement getProduct(String name) {
		for (NutritionalElement p:products) {
			if (p.getName() == name)
				return p;
		}
		
		return null;
	}
	
	/**
	 * Creates a new recipe stored in this Food container.
	 *  
	 * @param name name of the recipe
	 * 
	 * @return the newly created Recipe object
	 */
	public Recipe createRecipe(String name) {
		Recipe r = new Recipe(name, this);
		recipes.add(r);
		
		return r;
	}
	
	/**
	 * Retrieves the collection of all defined recipes
	 * 
	 * @return collection of recipes through the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> recipes() {
		return recipes;
	}
	
	/**
	 * Retrieves a specific recipe, given its name
	 * 
	 * @param name  name of the recipe
	 * 
	 * @return  a recipe through the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRecipe(String name) {		
		for (NutritionalElement r:recipes) {
			if (r.getName() == name)
				return r;
		}
		
		return null;
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		Menu m = new Menu(name, this);
		menus.add(m);
		
		return m;
	}
	
	public Menu getMenu(String name) {
		for (NutritionalElement m:menus) {
			if (m.getName() == name)
				return (Menu) m;
		}
		
		return null;
	}
}
