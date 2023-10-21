package it.polito.po.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import diet.Food;
import diet.Order;
import diet.Restaurant;
import diet.Takeaway;
import diet.User;

public class TestR8_Information {

	private Takeaway t;
	private Restaurant r;
	private Restaurant r2;
	private User u;
	private User u2;

	@Before
	public void setUp() throws Exception {
		Food food = new Food();
		t = new Takeaway();
		r = new Restaurant("Napoli", food);
		r.setHours("08:15", "14:00", "19:00", "23:59");
		r.createMenu("M1");
		r.createMenu("M2");
		t.addRestaurant(r);
		r2 = new Restaurant("Milano", food);
		r2.setHours("08:15", "12:00", "19:00", "23:59");
		t.addRestaurant(r2);
		u = t.registerUser("Marco", "Rossi", "marco.rossi@example.com", "123456789");
		u2 = t.registerUser("Giovanni", "Rossi", "giovanni.rossi@example.com", "123456789");
	}

	@Test
	public void testOpenedRestaurants() {
		Collection<Restaurant> openc = t.openedRestaurants("08:15");
		assertNotNull("Missing open restaurant list",openc);
		assertEquals("Wrong number of open restaurants",2,openc.size());
		Restaurant[] open = openc.toArray(new Restaurant[2]);
		assertNotNull("Missing opened restaurants",open[0]);
		assertEquals(r2.getName(), open[0].getName());
		assertNotNull("Missing second opened restaurant",open[1]);
		assertEquals(r.getName(), open[1].getName());
	}

	@Test
	public void testOpenedRestaurantsClosing() {
		Collection<Restaurant> open = t.openedRestaurants("12:10");
		assertNotNull("Missing open restaurant list",open);
		assertEquals(1,open.size());
		assertEquals(r.getName(),open.iterator().next().getName());
	}

	@Test
	public void testOpenedRestaurantsEmpty() {
		Collection<Restaurant> openc = t.openedRestaurants("15:00");
		assertNotNull("Missing open restaurant list",openc);
		assertEquals("Wrong number of open restaurants",0,openc.size());
	}

	@Test
	public void testOrdersWithStatus() {
		Order o = t.createOrder(u, "Napoli", 9, 0);
		assertNotNull("Missing order",o);
		o.addMenus("M2", 2);
		o.addMenus("M1", 1);
		t.createOrder(u2, "Napoli", 9, 0).addMenus("M1", 1);
		t.createOrder(u, "Napoli", 8, 59).addMenus("M1", 1);
		t.createOrder(u, "Napoli", 9, 0).addMenus("M1", 1).setStatus(Order.OrderStatus.READY);
		assertEquals(
				"Napoli, Giovanni Rossi : (09:00):\n\tM1->1\nNapoli, Marco Rossi : (08:59):\n\tM1->1\nNapoli, Marco Rossi : (09:00):\n\tM1->1\n\tM2->2",
				r.ordersWithStatus(Order.OrderStatus.ORDERED).trim());
	}
}
