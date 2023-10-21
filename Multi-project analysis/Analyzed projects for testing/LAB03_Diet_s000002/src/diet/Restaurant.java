package diet2;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import diet2.Order.OrderStatus;
import static java.util.Comparator.*;

/**
 * Represents a restaurant in the take-away system
 *
 */
public class Restaurant {
	
	private String name;
	private Food food;
	private String[] workingHours;
	private Map<String, Menu> menus;
	private Set<Order> orders;
	
	/**
	 * Constructor for a new restaurant.
	 * 
	 * Materials and recipes are taken from
	 * the food object provided as argument.
	 * 
	 * @param name	unique name for the restaurant
	 * @param food	reference food object
	 */
	public Restaurant(String name, Food food) {
		this.name = name;
		this.food = food;
		this.menus = new HashMap<>();
		this.orders = new HashSet<>();
	}
	
	/**
	 * gets the name of the restaurant
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Define opening hours.
	 * 
	 * The opening hours are considered in pairs.
	 * Each pair has the initial time and the final time
	 * of opening intervals.
	 * 
	 * for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00, 
	 * is should be called as {@code setHours("08:15", "14:00", "19:00", "00:00")}.
	 * 
	 * @param hm a list of opening hours
	 */
	public void setHours(String... hm) {
		workingHours = hm;
	}
	
	public String firstTimeOpen(String time) {
		String[] timeHM = time.split(":");
		int h = Integer.parseInt(timeHM[0]);
		int m = Integer.parseInt(timeHM[1]);
		
		while (true) {
			if (m == 60) {
				m = 0;
				h++;
			}
			
			h %= 24;
			
			String timeUpdated = String.format("%02d", h) + ":" + String.format("%02d", m);
			
			if (this.isOpen(timeUpdated))
				return timeUpdated;
			
			m++;
		}
	}
	
	public boolean isOpen(String time) {
		for (int i=0; i<workingHours.length; i+=2) {
			String open = workingHours[i];
			String close = workingHours[i+1];
			
			if (timeCmp(time, open) >= 0 && timeCmp(time, close) <0)
				return true;	
		}
		
		return false;
	}
	
	private static int timeCmp(String time1, String time2) {
		String[] time1HM = time1.split(":");
		String[] time2HM = time2.split(":");
		int h1 = Integer.parseInt(time1HM[0]);
		int h2 = Integer.parseInt(time2HM[0]);
		int m1 = Integer.parseInt(time1HM[1]);
		int m2 = Integer.parseInt(time2HM[1]);
		
		if (h1<h2 || (h1==h2 && m1<m2))
			return -1;
		
		if (h1>h2 || (h1==h2 && m1>m2))
			return 1;
		
		return 0;
	}
	
	public Menu getMenu(String name) {
		return menus.get(name);
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		Menu m = food.createMenu(name);
		menus.put(name, m);
		return m;
	}

	public void addOrder(Order o) {
		orders.add(o);
	}
	
	/**
	 * Find all orders for this restaurant with 
	 * the given status.
	 * 
	 * The output is a string formatted as:
	 * <pre>
	 * Napoli, Judi Dench : (19:00):
	 * 	M6->1
	 * Napoli, Ralph Fiennes : (19:00):
	 * 	M1->2
	 * 	M6->1
	 * </pre>
	 * 
	 * The orders are sorted by name of restaurant, name of the user, and delivery time.
	 * 
	 * @param status the status of the searched orders
	 * 
	 * @return the description of orders satisfying the criterion
	 */
	public String ordersWithStatus(OrderStatus status) {
		StringBuilder output = new StringBuilder();
		orders.stream().filter(o -> o.getStatus().equals(status))
				.sorted(comparing(Order::getRestaurantName)
						.thenComparing(Order::getUserName)
						.thenComparing(Order::getTime))
				.forEach(o -> output.append(o.toString()));
		
		return output.toString();
	}
}
