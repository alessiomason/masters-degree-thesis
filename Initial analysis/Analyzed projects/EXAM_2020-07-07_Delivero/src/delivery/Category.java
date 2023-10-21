package delivery;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private String categoryName;
	private List<Restaurant> restaurants = new ArrayList<>();

	public Category(String categoryName) {
		super();
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}
	
	public void addRestaurant(Restaurant r) {
		restaurants.add(r);
	}

	public List<Restaurant> getRestaurants() {
		return restaurants;
	}
}
