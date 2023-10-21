package diet;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Order {
    
	private Time delivery_time;
	private double price;
	public enum OrderStatus {
		ORDERED, READY, DELIVERED;
	}
	public enum PaymentMethod {
		PAID, CASH, CARD;
	}
	
	private OrderStatus orderStatus;
	private PaymentMethod paymentType;
	private SortedMap<Menu, Integer> menuOrder;
	private Restaurant restaurant;
	private User user;
	
	public Order(Restaurant restaurant, User user, int h, int m) {
		this.restaurant = restaurant;
		this.user = user;
		this.orderStatus = OrderStatus.ORDERED;
		this.paymentType = PaymentMethod.CASH;
		this.delivery_time = restaurant.checkTime(new Time(h,m));
		this.menuOrder = new TreeMap<Menu, Integer>(Comparator.comparing(Menu::getName));
	}
	
	public double Price() {
		return this.price;
	}
	
	public void setPaymentMethod(PaymentMethod pm) {
		this.paymentType = pm;
	}
	
	public PaymentMethod getPaymentMethod() {
		return this.paymentType;
	}
	
	public void setStatus(OrderStatus os) {
		this.orderStatus = os;
	}
	
	public OrderStatus getStatus()
	{
		return this.orderStatus;
	}
	
	public Restaurant getRestaurant() {
		return this.restaurant;
	}
	
	public Order addMenus(String menu, int quantity) {
		Menu m = restaurant.getMenu(menu);
		menuOrder.put(m, quantity);
		return this;
	}
	
	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append(restaurant.getName()).append(", ").append(user).append(" : (");
		b.append(delivery_time).append("):\n");
		for (Map.Entry<Menu, Integer> m : menuOrder.entrySet()) {
			b.append("\t").append(m.getKey().getName()).append("->").append(m.getValue().toString()).append("\n");
		}
		return b.toString();
	}

	User getUser() {
		return user;
	}
	
	Time getDeliveryTime() {
		return this.delivery_time;
	}
}
