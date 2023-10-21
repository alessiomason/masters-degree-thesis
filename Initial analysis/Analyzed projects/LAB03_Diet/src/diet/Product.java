package diet;

public class Product implements NutritionalElement, Comparable<Product> {
	private String name;
	private double calories;
	private double proteins;
	private double carbohydrates;
	private double fat;
	
	public Product(String name, double calories, double proteins, double carbohydrates, double fat) {
		this.name = name;
		this.calories = calories;
		this.proteins = proteins;
		this.carbohydrates = carbohydrates;
		this.fat = fat;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getCalories() {
		return this.calories;
	}

	@Override
	public double getProteins() {
		return this.proteins;
	}

	@Override
	public double getCarbs() {
		return this.carbohydrates;
	}

	@Override
	public double getFat() {
		return this.fat;
	}

	@Override
	public boolean per100g() {
		return false;	// i valori nutritivi sono espressi sempre per prodotto intero e non 100 grammi
	}
	
	@Override
	public int compareTo(Product p) {
		return this.getName().compareTo(p.getName());
	}
}
