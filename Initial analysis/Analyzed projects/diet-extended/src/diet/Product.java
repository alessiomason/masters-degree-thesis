package diet;


class Product implements NutritionalElement {

	private String name;
	private double calories;
	private double proteins;
	private double carbs;
	private double fat;

	public Product(String nome, double calories, double proteins, double carbs, double fat) {
		this.name = nome;
		this.calories = calories;
		this.proteins = proteins;
		this.carbs = carbs;
		this.fat = fat;
	}

	public String getName() {
		return name;
	}

	public double getCalories() {
		return calories;
	}

	public double getProteins() {
		return proteins;
	}

	public double getCarbs() {
		return carbs;
	}

	public double getFat() {
		return fat;
	}

	public boolean per100g() {
		return false;
	}
}