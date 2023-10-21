package it.polito.po.test;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;

import diet.*;


public class TestR3_Recipes {
	private Food food;
	
	@Before
	public void setUp() {
		food = new Food();
		food.defineRawMaterial("Zucchero", 400, 0, 100, 0);
		food.defineRawMaterial("Mais", 70, 2.7, 12, 1.3);
		food.defineRawMaterial("Pasta", 350, 12, 72.2, 1.5);
		food.defineRawMaterial("Olio", 900, 0, 0, 100);
		food.defineRawMaterial("Nutella", 530, 6.8, 56, 31);
	}

  @Test
  public void testCreateRecipe() {
  	Food dieta = new Food();

  	/*Recipe r = */ dieta.createRecipe("Pasta col Mais");
  	
	assertNotNull("Missing recipe",dieta.getRecipe("Pasta col Mais"));
	
	assertNotNull("Missing recipes",dieta.recipes());
	assertEquals("Wrong number of recipes", 1,dieta.recipes().size());
  }

  @Test
  public void testCreateManyRecipe() {
  	Food dieta = new Food();

  	dieta.createRecipe("Pasta alla Norma");
  	dieta.createRecipe("Melanzane alla Parmigiana");
  	dieta.createRecipe("Bistecca alla Fiorentina");
  	dieta.createRecipe("Tiramisu'");
  	
	assertNotNull("Missing recipe",dieta.getRecipe("Tiramisu'"));
	assertNotNull("Missing recipes",dieta.recipes());
	assertEquals("Wrong number of recipes", 4,dieta.recipes().size());
  }
  
  @Test
  public void testRecipesOrder() {
  	Food dieta = new Food();

  	dieta.createRecipe("Pasta alla Norma");
  	dieta.createRecipe("Melanzane alla Parmigiana");
  	dieta.createRecipe("Bistecca alla Fiorentina");
  	dieta.createRecipe("Tiramisu'");
  	dieta.createRecipe("Valdostana");
  	dieta.createRecipe("Ratatouille");
  	dieta.createRecipe("Bagna caoda");
  	
  	Collection<NutritionalElement> recipes = dieta.recipes();
  	assertNotNull(recipes);
  	assertEquals("Mancano delle ricette",7,recipes.size());
  	String prev="A";
  	for(NutritionalElement e : recipes) {
  		String name = e.getName();
  		assertTrue("Wrong products order: '" + name + 
  					"' should come before '" + prev + "'",
  				name.compareTo(prev)>=0);
  		prev = e.getName();
  	}
  }

  @Test
  public void testRecipe() {

	Recipe r = food.createRecipe("Pasta e Nutella");
  	
	r.addIngredient("Pasta",70);
	r.addIngredient("Nutella",30);
	assertEquals("Wrong recipe calories value",350 *0.7 + 530 *0.3, r.getCalories(), 0.001);
	assertEquals("Wrong recipe proteins value",12 *0.7 + 6.8 *0.3 , r.getProteins(), 0.001);
	assertEquals("Wrong recipe carbs value",72.2 *0.7 + 56 *0.3, r.getCarbs(), 0.001);
	assertEquals("Wrong recipe fat value",1.5 *0.7 + 31 *0.3 , r.getFat(), 0.001);
	assertTrue(r.per100g());
  }
	
  @Test
  public void testRecipe2() {
	Recipe r = food.createRecipe("Pasta col Mais");
  	
	r.addIngredient("Pasta",70);
	r.addIngredient("Mais",70);
	r.addIngredient("Olio",13);
	
	assertEquals("Wrong recipe calories value",(350 *0.7 + 70 *0.7 + 900 *0.13)*100/153, r.getCalories(), 0.001);
	assertEquals("Wrong recipe proteins value",(12 *0.7 + 2.7 *0.7 + 0 *0.13)*100/153, r.getProteins(), 0.001);
	assertEquals("Wrong recipe carbs value",(72.2 *0.7 + 12 *0.7 + 0 *0.13)*100/153, r.getCarbs(), 0.001);
	assertEquals("Wrong recipe fat value",(1.5 *0.7 + 1.3 *0.7 + 100 *0.13)*100/153, r.getFat(), 0.001);
  }
  
  @Test
  public void testToString() {
		Recipe r = food.createRecipe("Pasta col Mais");
	  	
		r.addIngredient("Pasta",70);
		r.addIngredient("Mais",70);
		r.addIngredient("Olio",13);
		
		String rs = r.toString();
		
		assertNotNull("Missing Recipe string representation",rs);
		assertTrue("Empty Recipe string representation",rs.length()>0);
		assertFalse("Recipe string representation non defined",rs.startsWith("diet.Recipe"));
		
		String[] lines = rs.split("\n");
		
		assertEquals("Numero di ingredienti errato in '" + rs + "'",
					 3,lines.length);

		assertTrue("First line should contain Pasta", lines[0].trim().startsWith("Pasta"));
		assertTrue("First line should contain Mais",lines[1].trim().startsWith("Mais"));
		assertTrue("First line should contain Olio",lines[2].trim().startsWith("Olio"));
  }

}
