package diet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import diet.Order.OrderStatus;

public class Restaurant implements Comparable<Restaurant> {
	
	private Food food;
	private String name;
    private ArrayList<WorkingHours> working_hours; //08:30-14:00  19:00-00:00
	private Map<String, Menu> menus;
	private List<Order> orders = new LinkedList<Order>();
	
	
	public Restaurant(String name, Food food) { 
		this.name = name;
		this.food = food;
		working_hours = new ArrayList<WorkingHours>();
		menus = new HashMap<String, Menu>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setHours(String ... hm) {
		working_hours.clear();
		for(int i=0; i<hm.length/2; i++) {
			working_hours.add(new WorkingHours(hm[2*i], hm[2*i+1]));
		}
	}
	
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	public Menu getMenu(String name) {
		return menus.get(name);
	}
	
	public Menu createMenu(String name) {
		Menu m = food.createMenu(name);
		menus.put(name, m);
		return m;
	}

	
	public Time checkTime(Time t) {
		Collections.sort(working_hours);
		for(WorkingHours w : working_hours) {
			if( w.includes(t) ) return t;
		}
		for(WorkingHours w : working_hours) {
			if (w.getOpen().compareTo(t) > 0) {
				return w.getOpen();
			}
		}
		return working_hours.get(0).getOpen();
	}

	public String ordersWithStatus(OrderStatus status) {
		StringBuffer b = new StringBuffer();
		orders.sort(Comparator.comparing((Order o)->o.getRestaurant().getName())
				    .thenComparing(Order::getUser)
				    .thenComparing(Order::getDeliveryTime));
		for (Order o: orders) {
			if (o.getStatus() == status){
				b.append(o);
			}
		}
		return b.toString();
	}

	@Override
	public int compareTo(Restaurant o) {
		return this.getName().compareTo(o.getName());
	}
}
