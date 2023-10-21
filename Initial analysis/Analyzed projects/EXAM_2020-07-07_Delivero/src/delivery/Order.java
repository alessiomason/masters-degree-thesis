package delivery;

import java.util.HashMap;
import java.util.Map;

public class Order {
	private String customerName;
	private Restaurant restaurant;
	private Integer deliveryTime;
	private Integer deliveryDistance;
	private Map<String, Integer> dishesQuantities = new HashMap<>();
	private boolean assigned = false;
	
	public Order(String[] dishNames, int[] quantities, String customerName, Restaurant restaurant, Integer deliveryTime, Integer deliveryDistance) {
		super();
		this.customerName = customerName;
		this.restaurant = restaurant;
		this.deliveryTime = deliveryTime;
		this.deliveryDistance = deliveryDistance;
		
		for (int i=0; i<dishNames.length; i++)
			dishesQuantities.put(dishNames[i], quantities[i]);
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}

	public String getCustomerName() {
		return customerName;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public Integer getDeliveryTime() {
		return deliveryTime;
	}

	public Integer getDeliveryDistance() {
		return deliveryDistance;
	}

	public Map<String, Integer> getDishesQuantities() {
		return dishesQuantities;
	}
}
