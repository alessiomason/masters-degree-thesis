package diet1;

public class RawMaterial implements NutritionalElement, Comparable<RawMaterial> {
	private String name;
	private double calories;
	private double proteins;
	private double carbohydrates;
	private double fat;
	
	public RawMaterial(String name) {
		this.name = name;
	}

	public RawMaterial(String name, double calories, double proteins, double carbohydrates, double fat) {
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
		return true;	// nel caso delle materie prime i valori nutritivi sono espressi sempre per 100 grammi
	}

	@Override
	public int compareTo(RawMaterial r) {
		return this.getName().compareTo(r.getName());
	}
}
