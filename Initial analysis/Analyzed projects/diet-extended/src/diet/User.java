package diet;

import java.util.LinkedList;

public class User implements Comparable<User>{
	
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private LinkedList<Order> orderHistory;
	
	public User(String first_name, String last_name, String email, String phone_number) {
		this.firstName = first_name;
		this.lastName = last_name;
		this.email = email;
		this.phoneNumber = phone_number;
		this.orderHistory = new LinkedList<Order>();
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phoneNumber;
	}
	
	public void SetEmail(String email) {
		this.email = email;
	}
	
	public void setPhone(String phone) {
		this.phoneNumber = phone;
	}
	
	
	public void addOrder(Order order) {
		orderHistory.add(order);
	}
	
	public int numOrders(Restaurant r) {
		int sum = 0;
		for (Order o: orderHistory) {
			if (o.getRestaurant() == r) {
				sum++;
			}
		}
		return sum;
	}
	
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName;
	}
	
	@Override
	public int compareTo(User u) {
		int last = this.lastName.compareTo(u.getLastName());
		if (last == 0) {
			return this.firstName.compareTo(u.getFirstName());
		} 
		return last;

	}
		


}
