package diet1;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

/**
 * Represents the main class in the
 * take-away system.
 * 
 * It allows adding restaurant, users, and creating orders.
 *
 */
public class Takeaway {
	
	private Map<String, Restaurant> restaurants;
	private Set<User> users;

	public Takeaway() {
		restaurants = new HashMap<>();
		users = new HashSet<>();
	}

	/**
	 * Adds a new restaurant to the take-away system
	 * 
	 * @param r the restaurant to be added
	 */
	public void addRestaurant(Restaurant r) {
		String name = r.getName();
		restaurants.put(name, r);
	}
	
	/**
	 * Returns the collections of restaurants
	 * 
	 * @return collection of added restaurants
	 */
	public Collection<String> restaurants() {
		// evito di restituire direttamente il keyset, per non avere
		// modifiche dall'esterno
		return new LinkedList<>(restaurants.keySet());
	}
	
	/**
	 * Define a new user
	 * 
	 * @param firstName first name of the user
	 * @param lastName  last name of the user
	 * @param email     email
	 * @param phoneNumber telephone number
	 * @return
	 */
	public User registerUser(String firstName, String lastName, String email, String phoneNumber) {
		User u = new User(firstName, lastName, email, phoneNumber);
		users.add(u);
		
		return u;
	}
	
	/**
	 * Gets the collection of registered users
	 * 
	 * @return the collection of users
	 */
	public Collection<User> users() {
		return users.stream().sorted(comparing(User::getLastName).thenComparing(User::getFirstName)).collect(toList());
	}
	
	/**
	 * Create a new order by a user to a given restaurant.
	 * 
	 * The order is initially empty and is characterized
	 * by a desired delivery time. 
	 * 
	 * @param user				user object
	 * @param restaurantName	restaurant name
	 * @param h					delivery time hour
	 * @param m					delivery time minutes
	 * @return
	 */
	public Order createOrder(User user, String restaurantName, int h, int m) {
		Restaurant r = restaurants.get(restaurantName);
		String time = h + ":" + m;
		Order o = new Order(user, r, r.firstTimeOpen(time));
		r.addOrder(o);
		
		return o;
	}
	
	/**
	 * Retrieves the collection of restaurant that are open
	 * at the given time.
	 * 
	 * @param time time to check open
	 * 
	 * @return collection of restaurants
	 */
	public Collection<Restaurant> openedRestaurants(String time){
		return restaurants.values().stream().filter(r -> r.isOpen(time))
				.sorted(comparing(Restaurant::getName)).collect(toList());
	}
	
}
