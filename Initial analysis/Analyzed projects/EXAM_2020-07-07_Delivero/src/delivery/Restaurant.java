package delivery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;

public class Restaurant {
	private String name;
	private Category category;
	private Map<String, Dish> dishes = new HashMap<>();
	private List<Integer> ratings = new ArrayList<>();
	private List<Order> orders = new ArrayList<>();
	
	public Restaurant(String name, Category category) {
		super();
		this.name = name;
		this.category = category;
	}

	public String getName() {
		return name;
	}
	
	public Category getCategory() {
		return category;
	}

	public void addDish(String dishName, float price) throws DeliveryException {
		if (dishes.get(dishName) != null)
			throw new DeliveryException();
		
		Dish dish = new Dish(dishName, price);
		dishes.put(dishName, dish);
	}
	
	public Collection<Dish> getDishes() {
		return dishes.values();
	}

	public List<String> getDishesByPrice(float minPrice, float maxPrice) {
		List<Dish> inRangeDishes = new ArrayList<>();
		
		for (Dish d:dishes.values()) {
			if (d.getPrice() >= minPrice && d.getPrice() <= maxPrice)
				inRangeDishes.add(d);
		}
		
		return inRangeDishes.stream()
							.map(Dish::getName)
							.collect(toList());
	}
	
	public void addRating(int rating) {
		if (rating >= 0 && rating <= 5)
			ratings.add(rating);
	}
	
	public Double getAverageRating() {
		return ratings.stream()
					  .mapToInt(r -> r)
					  .average().orElse(-1.0);
	}
	
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	public Long getNumberOfOrders() {
		return (long) orders.size();
	}
}
