package it.polito.po.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestR1_RawMaterials.class, TestR2_Products.class, TestR3_Recipes.class, TestR4_Menu.class })
public class AllTests {

}
