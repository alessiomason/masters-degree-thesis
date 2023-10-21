package diet1;

import java.util.Map;
import java.util.TreeMap;

/**
 * Represents an order in the take-away system
 */
public class Order {
 
	/**
	 * Defines the possible order status
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED;
	}
	/**
	 * Defines the possible valid payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD;
	}
	
	private static class OrderedMenu {
		private Menu menu;
		private int quantity;
		
		public OrderedMenu(Menu menu, int quantity) {
			this.menu = menu;
			this.quantity = quantity;
		}
		
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		@Override
		public String toString() {
			return menu.getName() + "->" + quantity;
		}
	}
	
	private User user;
	private Restaurant restaurant;
	private String time;
	private OrderStatus status;
	private PaymentMethod payMethod;
	private Map<String, OrderedMenu> orderedMenus;
		
	public Order(User user, Restaurant restaurant, String time) {
		this.user = user;
		this.restaurant = restaurant;
		this.time = time;
		this.status = OrderStatus.ORDERED;
		this.payMethod = PaymentMethod.CASH;
		orderedMenus = new TreeMap<>();
	}

	/**
	 * Total order price
	 * @return order price
	 */
	public double Price() {
		return -1.0;
	}
	
	/**
	 * define payment method
	 * 
	 * @param method payment method
	 */
	public void setPaymentMethod(PaymentMethod method) {
		this.payMethod = method;
	}
	
	/**
	 * get payment method
	 * 
	 * @return payment method
	 */
	public PaymentMethod getPaymentMethod() {
		return payMethod;
	}
	
	/**
	 * change order status
	 * @param newStatus order status
	 */
	public void setStatus(OrderStatus newStatus) {
		this.status = newStatus;
	}
	
	/**
	 * get current order status
	 * @return order status
	 */
	public OrderStatus getStatus() {
		return status;
	}
	
	public String getRestaurantName() {
		return restaurant.getName();
	}
	
	public String getUserName() {
		return user.toString();
	}
	
	public String getTime() {
		return time;
	}
	
	/**
	 * Add a new menu with the relative order to the order.
	 * The menu must be defined in the {@link Food} object
	 * associated to the restaurant that created the order.
	 * 
	 * @param menu     name of the menu
	 * @param quantity quantity of the menu
	 * @return this order to enable method chaining
	 */
	public Order addMenus(String menu, int quantity) {
		if (orderedMenus.containsKey(menu))
			orderedMenus.get(menu).setQuantity(quantity);
		
		else {	// bastava fare la put, che nel caso sovrascrive già di suo
			OrderedMenu newOrdMenu = new OrderedMenu(restaurant.getMenu(menu), quantity);
			orderedMenus.put(menu, newOrdMenu);
		}
		
		return this;
	}
	
	/**
	 * Converts to a string as:
	 * <pre>
	 * RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
	 * 	MENU_NAME_1->MENU_QUANTITY_1
	 * 	...
	 * 	MENU_NAME_k->MENU_QUANTITY_k
	 * </pre>
	 */
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append(restaurant.getName()).append(", ")
				.append(user).append(" : ")
				.append("(").append(time)
				.append(")").append(":")
				.append("\n");
		
		for (OrderedMenu m:orderedMenus.values())
			output.append("\t").append(m).append("\n");
		
		return output.toString();
	}
	
}
