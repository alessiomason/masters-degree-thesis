package diet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import diet.Order.PaymentMethod;

public class Takeaway {

	private Collection<User> users = new ArrayList<User>();
	private Map<String, Restaurant> restaurants = new HashMap<String, Restaurant>();
	
	
	public void addRestaurant(Restaurant r) {
		restaurants.put(r.getName(), r);
	}
	
	public User registerUser(String firstName, String lastName, String email, String phoneNumber) {
		User u = new User(firstName, lastName, email, phoneNumber);
		users.add(u);
		return u;
	}
	
	public Order createOrder(User user, String restaurantName, int h, int m) {
		Restaurant restaurant = restaurants.get(restaurantName);
		
		Order o = new Order(restaurant, user, h, m);
		restaurant.addOrder(o);
		user.addOrder(o);
		o.setPaymentMethod(PaymentMethod.CASH);
		
		return o;
	}
	
	public Collection<User> users(){
		ArrayList<User> u = new ArrayList<User>(users);
		Collections.sort(u);
		return u;
	}
	
	public Collection<Restaurant> openedRestaurants(String time){
		ArrayList<Restaurant> opened_r = new ArrayList<Restaurant>();
		Time lt = new Time(time);
		for (Restaurant r : restaurants.values()) {
			if (r.checkTime(lt)==lt) {
				opened_r.add(r);
			}
		}
		Collections.sort(opened_r);
		return opened_r;
	}

	public Collection<String> restaurants() {
		return new LinkedList<>(restaurants.keySet());
	}
	
	
	
}
