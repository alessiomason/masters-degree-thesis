package it.polito.po.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import diet.Takeaway;
import diet.User;

public class TestR6_Users {

	@Test
	public void testTakeawayRegisterUser() {
		Takeaway t = new Takeaway();
		User u = t.registerUser("Marco", "Rossi", "marco.rossi@example.com", "123456789");
		assertNotNull("Missing new registered user",u );
		assertEquals("Marco", u.getFirstName());
		assertEquals("Rossi", u.getLastName());
		assertEquals("marco.rossi@example.com", u.getEmail());
		assertEquals("123456789", u.getPhone());
	}

	@Test
	public void testUserToString() {
		Takeaway t = new Takeaway();
		User u = t.registerUser("Marco", "Rossi", "marco.rossi@example.com", "123456789");
		assertNotNull("Missing new registered user",u );
		String us = u.toString();
		assertNotNull(us);
		assertFalse("toString method non redefined for User",us.contains("@"));
		// We split and reorder to allow both "first last" and "last first"
		String[] usa = us.split(" +");
		Arrays.sort(usa);
		assertArrayEquals(new String[] {"Marco","Rossi"}, usa);
	}

	@Test
	public void testUserSetEmail() {
		Takeaway t = new Takeaway();
		User u = t.registerUser("Marco", "Rossi", "marco.rossi@example.com", "123456789");
		assertNotNull("Missing new registered user",u );
		u.SetEmail("marco@example.com");
		assertEquals("Wrong email","marco@example.com", u.getEmail());
	}

	@Test
	public void testUserSetPhone() {
		Takeaway t = new Takeaway();
		User u = t.registerUser("Marco", "Rossi", "marco.rossi@example.com", "123456789");
		assertNotNull("Missing new registered user",u );
		u.setPhone("987654321");
		assertEquals("Wrong mobile","987654321", u.getPhone());
	}

	@Test
	public void testTakeawayUsers() {
		Takeaway t = new Takeaway();
		User u1 = t.registerUser("Giuseppe", "Verdi", "marco.rossi@example.com", "123456789");
		User u2 = t.registerUser("Marco", "Rossi", "marco.rossi@example.com", "123456789");
		User u3 = t.registerUser("Giovanni", "Rossi", "marco.rossi@example.com", "123456789");
		Collection<User> usersc = t.users();
		assertNotNull("Missing user list",usersc);
		assertEquals("Wrong number of users",3,usersc.size());
		assertTrue("Missing user in list",usersc.contains(u1));
		assertTrue("Missing user in list",usersc.contains(u1));
		assertTrue("Missing user in list",usersc.contains(u1));
		User[] users = usersc.toArray(new User[3]);
		assertEquals("Wrong orderd in users",u3, users[0]);
		assertEquals("Wrong orderd in users",u2, users[1]);
		assertEquals("Wrong orderd in users",u1, users[2]);
	}

}
