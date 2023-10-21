package it.polito.po.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import diet.Food;
import diet.Order;
import diet.Restaurant;
import diet.Takeaway;
import diet.User;

public class TestR7_Orders {

	private Takeaway t;
	private User u;

	@Before
	public void setUp() throws Exception {
		Food food = new Food();
		t = new Takeaway();
		Restaurant r = new Restaurant("Napoli", food);
		r.setHours("08:15", "14:00", "19:00", "23:59");
		r.createMenu("M1");
		r.createMenu("M2");
		t.addRestaurant(r);
		u = t.registerUser("Marco", "Rossi", "marco.rossi@example.com", "123456789");
	}

	@Test
	public void testTakeawayCreateOrder() {
		Order o = t.createOrder(u, "Napoli", 19, 0);
		assertNotNull("Missing order",o);
		assertEquals(Order.OrderStatus.ORDERED, o.getStatus());
		assertEquals(Order.PaymentMethod.CASH, o.getPaymentMethod());
	}

	@Test
	public void testOrderToStringWorkingTime() {
		Order o = t.createOrder(u, "Napoli", 9, 30);
		assertNotNull("Missing order",o);
		assertEquals("Napoli, Marco Rossi : (09:30):", o.toString().trim());
	}

	@Test
	public void testOrderToStringOpenTime() {
		Order o = t.createOrder(u, "Napoli", 19, 0);
		assertNotNull("Missing order",o);
		assertEquals("Napoli, Marco Rossi : (19:00):", o.toString().trim());
	}

	@Test
	public void testOrderToStringCloseTime() {
		Order o = t.createOrder(u, "Napoli", 0, 0);
		assertNotNull("Missing order",o);
		assertEquals("Napoli, Marco Rossi : (08:15):", o.toString().trim());
		
	}

	@Test
	public void testOrderToStringInvalidTime() {
		Order o = t.createOrder(u, "Napoli", 15, 30);
		assertNotNull("Missing order",o);
		assertEquals("Napoli, Marco Rossi : (19:00):", o.toString().trim());
	}

	@Test
	public void testOrderSetStatus() {
		Order o = t.createOrder(u, "Napoli", 19, 0);
		assertNotNull("Missing order",o);
		o.setStatus(Order.OrderStatus.DELIVERED);
		assertEquals(Order.OrderStatus.DELIVERED, o.getStatus());
	}

	@Test
	public void testOrderSetPaymentMethod() {
		Order o = t.createOrder(u, "Napoli", 19, 0);
		assertNotNull("Missing order",o);
		o.setPaymentMethod(Order.PaymentMethod.PAID);
		assertEquals(Order.PaymentMethod.PAID, o.getPaymentMethod());
	}

	@Test
	public void testOrderAddMenus() {
		Order o = t.createOrder(u, "Napoli", 9, 0);
		assertNotNull("Missing order",o);
		o.addMenus("M1", 1);
		o.addMenus("M2", 2);
		assertEquals("Napoli, Marco Rossi : (09:00):\n\tM1->1\n\tM2->2", o.toString().trim());
	}

	@Test
	public void testOrderAddMenusOverwrite() {
		Order o = t.createOrder(u, "Napoli", 9, 0);
		assertNotNull("Missing order",o);
		o.addMenus("M1", 1);
		o.addMenus("M2", 2);
		o.addMenus("M1", 3);
		assertEquals("Napoli, Marco Rossi : (09:00):\n\tM1->3\n\tM2->2", o.toString().trim());
	}

	@Test
	public void testOrderAddMenusSorted() {
		Order o = t.createOrder(u, "Napoli", 9, 0);
		assertNotNull("Missing order",o);
		o.addMenus("M2", 2);
		o.addMenus("M1", 1);
		assertEquals("Napoli, Marco Rossi : (09:00):\n\tM1->1\n\tM2->2", o.toString().trim());
	}

}