package delivery;

import java.util.List;
import java.util.Map;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class Delivery {
	private Map<String, Category> categories = new HashMap<>();
	private Map<String, Restaurant> restaurants = new HashMap<>();
	private Map<Integer, Order> orders = new LinkedHashMap<>();
	
	// R1
	
    /**
     * adds one category to the list of categories managed by the service.
     * 
     * @param category name of the category
     * @throws DeliveryException if the category is already available.
     */
	public void addCategory (String category) throws DeliveryException {
		Category c = new Category(category);
		
		if (categories.get(category) != null)
			throw new DeliveryException();
		
		categories.put(category, c);
	}
	
	/**
	 * retrieves the list of defined categories.
	 * 
	 * @return list of category names
	 */
	public List<String> getCategories() {
		return categories.values().stream()
								  .map(Category::getCategoryName)
								  .collect(toList());
	}
	
	/**
	 * register a new restaurant to the service with a related category
	 * 
	 * @param name     name of the restaurant
	 * @param category category of the restaurant
	 * @throws DeliveryException if the category is not defined.
	 */
	public void addRestaurant (String name, String category) throws DeliveryException {
		Category cat = categories.get(category);
		
		if (cat == null)
			throw new DeliveryException();
		
		Restaurant r = new Restaurant(name, cat);
		restaurants.put(name, r);
		cat.addRestaurant(r);
	}
	
	/**
	 * retrieves an ordered list by name of the restaurants of a given category. 
	 * It returns an empty list in there are no restaurants in the selected category 
	 * or the category does not exist.
	 * 
	 * @param category name of the category
	 * @return sorted list of restaurant names
	 */
	public List<String> getRestaurantsForCategory(String category) {
		Category cat = categories.get(category);
		if (cat == null)
			return new ArrayList<>();
		
        return cat.getRestaurants().stream()
        						   .map(Restaurant::getName)
        						   .sorted()
        						   .collect(toList());
	}
	
	// R2
	
	/**
	 * adds a dish to the list of dishes of a restaurant. 
	 * Every dish has a given price.
	 * 
	 * @param name             name of the dish
	 * @param restaurantName   name of the restaurant
	 * @param price            price of the dish
	 * @throws DeliveryException if the dish name already exists
	 */
	public void addDish(String name, String restaurantName, float price) throws DeliveryException {
		Restaurant r = restaurants.get(restaurantName);
		
		r.addDish(name, price);
	}
	
	/**
	 * returns a map associating the name of each restaurant with the 
	 * list of dish names whose price is in the provided range of price (limits included). 
	 * If the restaurant has no dishes in the range, it does not appear in the map.
	 * 
	 * @param minPrice  minimum price (included)
	 * @param maxPrice  maximum price (included)
	 * @return map restaurant -> dishes
	 */
	public Map<String,List<String>> getDishesByPrice(float minPrice, float maxPrice) {
		Map<String, List<String>> mappa = new HashMap<>();
		
		for (Restaurant r:restaurants.values()) {
			List<String> lista = r.getDishesByPrice(minPrice, maxPrice);
			
			if (lista.size() > 0) {
				mappa.put(r.getName(), lista);
			}
		}
		
        return mappa;
	}
	
	/**
	 * retrieve the ordered list of the names of dishes sold by a restaurant. 
	 * If the restaurant does not exist or does not sell any dishes 
	 * the method must return an empty list.
	 *  
	 * @param restaurantName   name of the restaurant
	 * @return alphabetically sorted list of dish names 
	 */
	public List<String> getDishesForRestaurant(String restaurantName) {
		Restaurant rest = restaurants.get(restaurantName);
		if (rest == null)
			return new ArrayList<>();
		
        return rest.getDishes().stream()
        					   .map(Dish::getName)
        					   .sorted()
        					   .collect(toList());
	}
	
	/**
	 * retrieves the list of all dishes sold by all restaurants belonging to the given category. 
	 * If the category is not defined or there are no dishes in the category 
	 * the method must return and empty list.
	 *  
	 * @param category     the category
	 * @return 
	 */
	public List<String> getDishesByCategory(String category) {
		Category cat = categories.get(category);
		if (cat == null)
			return new ArrayList<>();
		
        return cat.getRestaurants().stream()
        						  .flatMap(r -> r.getDishes().stream())
        						  .map(Dish::getName)
        						  .collect(toList());
	}
	
	//R3
	
	/**
	 * creates a delivery order. 
	 * Each order may contain more than one product with the related quantity. 
	 * The delivery time is indicated with a number in the range 8 to 23. 
	 * The delivery distance is expressed in kilometers. 
	 * Each order is assigned a progressive order ID, the first order has number 1.
	 * 
	 * @param dishNames        names of the dishes
	 * @param quantities       relative quantity of dishes
	 * @param customerName     name of the customer
	 * @param restaurantName   name of the restaurant
	 * @param deliveryTime     time of delivery (8-23)
	 * @param deliveryDistance distance of delivery
	 * 
	 * @return order ID
	 */
	public int addOrder(String dishNames[], int quantities[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance) {
		Restaurant rest = restaurants.get(restaurantName);
		Order o = new Order(dishNames, quantities, customerName, rest, deliveryTime, deliveryDistance);
		
		Integer id = orders.size() + 1;
		orders.put(id, o);
		rest.addOrder(o);
		
	    return id;
	}
	
	/**
	 * retrieves the IDs of the orders that satisfy the given constraints.
	 * Only the  first {@code maxOrders} (according to the orders arrival time) are returned
	 * they must be scheduled to be delivered at {@code deliveryTime} 
	 * whose {@code deliveryDistance} is lower or equal that {@code maxDistance}. 
	 * Once returned by the method the orders must be marked as assigned 
	 * so that they will not be considered if the method is called again. 
	 * The method returns an empty list if there are no orders (not yet assigned) 
	 * that meet the requirements.
	 * 
	 * @param deliveryTime required time of delivery 
	 * @param maxDistance  maximum delivery distance
	 * @param maxOrders    maximum number of orders to retrieve
	 * @return list of order IDs
	 */
	public List<Integer> scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders) {
		List<Integer> lista = new ArrayList<>();
		
		for (Integer i:orders.keySet()) {
			if (lista.size() >= maxOrders)
				break;
			
			Order o = orders.get(i);
			
			if (!o.isAssigned() && o.getDeliveryTime() == deliveryTime && o.getDeliveryDistance() <= maxDistance) {
				lista.add(i);
				o.setAssigned(true);
			}
		}
		
        return lista;
	}
	
	/**
	 * retrieves the number of orders that still need to be assigned
	 * @return the unassigned orders count
	 */
	public int getPendingOrders() {
        return (int) orders.values().stream()
        					  		.filter(o -> !o.isAssigned())
        					  		.count();
	}
	
	// R4
	/**
	 * records a rating (a number between 0 and 5) of a restaurant.
	 * Ratings outside the valid range are discarded.
	 * 
	 * @param restaurantName   name of the restaurant
	 * @param rating           rating
	 */
	public void setRatingForRestaurant(String restaurantName, int rating) {
		Restaurant restaurant = restaurants.get(restaurantName);
		restaurant.addRating(rating);
	}
	
	/**
	 * retrieves the ordered list of restaurant. 
	 * 
	 * The restaurant must be ordered by decreasing average rating. 
	 * The average rating of a restaurant is the sum of all rating divided by the number of ratings.
	 * 
	 * @return ordered list of restaurant names
	 */
	public List<String> restaurantsAverageRating() {
        return restaurants.values().stream()
        						   .filter(r -> r.getAverageRating() >= 0)
        						   .sorted(comparing(Restaurant::getAverageRating, reverseOrder()))
        						   .map(Restaurant::getName)
        						   .collect(toList());
	}
	
	//R5
	/**
	 * returns a map associating each category to the number of orders placed to any restaurant in that category. 
	 * Also categories whose restaurants have not received any order must be included in the result.
	 * 
	 * @return map category -> order count
	 */
	public Map<String,Long> ordersPerCategory() {
		Map<String, Long> mappa = new HashMap<>();
		
		for (Category c:categories.values()) {
			Long numberOfOrders = 0L;
			
			for (Restaurant r:restaurants.values()) {
				if (r.getCategory().equals(c))
					numberOfOrders += r.getNumberOfOrders();
			}
			
			mappa.put(c.getCategoryName(), numberOfOrders);
		}
        return mappa;
	}
	
	/**
	 * retrieves the name of the restaurant that has received the higher average rating.
	 * 
	 * @return restaurant name
	 */
	public String bestRestaurant() {
        Restaurant bestRest = null;
        Double bestAvg = -1.0;
        
        for (Restaurant r:restaurants.values()) {
        	if (r.getAverageRating() > bestAvg) {
        		bestAvg = r.getAverageRating();
        		bestRest = r;
        	}
        }
        
        return bestRest.getName();
	}
}
