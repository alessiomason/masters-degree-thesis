package it.polito.po.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import diet.*;
import java.util.Collection;


public class TestR1_RawMaterials {
	Food food = new Food();
	
	@Before
	public void setUp() {
		food = new Food();
	}

  @Test
  public void testDefinition(){
  	int initialSize = food.rawMaterials().size();
  	food.defineRawMaterial("Pasta", 350, 12, 72.2, 1.5);
	int finalSize = food.rawMaterials().size();
	
	assertEquals("Initially there should be no raw material", 0,initialSize);
	assertEquals("Expecing one product", 1, finalSize);
  }
  
  @Test
  public void testRawMaterialsCollection(){
	food.defineRawMaterial("Pasta", 350, 12, 72.2, 1.5);
	
	Collection<NutritionalElement> c = food.rawMaterials();
	
	NutritionalElement en = (NutritionalElement)c.iterator().next();
	
	assertEquals("Pasta",en.getName());
	assertEquals(350,en.getCalories(),0.001); 
	assertEquals(12,en.getProteins(),0.001); 
	assertEquals(72.2,en.getCarbs(),0.001); 
	assertEquals(1.5,en.getFat(),0.001); 
	assertTrue(en.per100g());
  }

  @Test
  public void testRawMaterials(){
	food.defineRawMaterial("Pasta", 350, 12, 72.2, 1.5);
	NutritionalElement en = food.getRawMaterial("Pasta");
	
	assertEquals("Pasta",en.getName());
	assertEquals(350,en.getCalories(),0.001); 
	assertEquals(12,en.getProteins(),0.001); 
	assertEquals(72.2,en.getCarbs(),0.001); 
	assertEquals(1.5,en.getFat(),0.001); 
	assertTrue(en.per100g());
  }

  @Test
  public void testRawMaterialsCollectionsSorted(){
	food.defineRawMaterial("Zucchero", 400, 0, 100, 0);
	food.defineRawMaterial("Mais", 70, 2.7, 12, 1.3);
	food.defineRawMaterial("Pasta", 350, 12, 72.2, 1.5);
	
	String prev="A";
	for(NutritionalElement e : food.rawMaterials()) {
		assertTrue("Raw materials are not sorted",
					e.getName().compareTo(prev)>=0);
		prev = e.getName();
	}
  }

}
