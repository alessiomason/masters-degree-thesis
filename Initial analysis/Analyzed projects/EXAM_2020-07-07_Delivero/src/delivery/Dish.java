package delivery;

public class Dish {
	private String name;
	private float price;
	
	public Dish(String name, float price) {
		super();
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		return price;
	}
}
