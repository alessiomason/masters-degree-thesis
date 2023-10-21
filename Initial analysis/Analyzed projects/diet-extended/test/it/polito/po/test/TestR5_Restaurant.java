package it.polito.po.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import diet.Food;
import diet.Menu;
import diet.Restaurant;
import diet.Takeaway;

public class TestR5_Restaurant {

	private Food food;

	@Before
	public void setUp() throws Exception {
		food = new Food();
	}

	@Test
	public void testRestaurantGetName() {
		Restaurant r = new Restaurant("Napoli", food);
		assertNotNull("Misisng restaurant name", r.getName());
		assertEquals("Wrong restaurant name", "Napoli", r.getName());
	}

	@Test
	public void testRestaurantCreateMenu() {
		Restaurant r = new Restaurant("Napoli", food);
		Menu m = r.createMenu("M1");
		r.createMenu("M2");
		
		assertNotNull("Missing created menu",m);
		assertSame("Retrieved wrong menu",m, r.getMenu("M1"));
	}

	@Test
	public void testTakeawayAddRestaurant() {
		Restaurant napoli = new Restaurant("Napoli", food);
		Restaurant torino = new Restaurant("Torino", food);
		Takeaway t = new Takeaway();
		t.addRestaurant(napoli);
		t.addRestaurant(torino);
		Collection<String> restaurants = t.restaurants();
		assertNotNull("Missing restaurants", restaurants);
		assertEquals("Missing restaurant", 2, restaurants.size());
		assertTrue(restaurants.contains("Napoli"));
		assertTrue(restaurants.contains("Torino"));
	}

}
