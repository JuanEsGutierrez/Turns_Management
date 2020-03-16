package model;

import static org.junit.Assert.*;

import org.junit.Test;
import customExceptions.*;

public class CompanyTest {
	
	Company c;
	
	public void setup1() {
		c = new Company();
		TurnType tt1 = new TurnType("anciano", 1.5f);
		c.getTurnTypes().add(tt1);
		
	}
	
	public void setup2() {
		c = new Company();
		User u1 = new User("CC", "1068895", "Pedro", "Hernandez", "3165577", "cra 23 #20-36");
		User u2 = new User("CC","63883786" ,"Sandra", "Gutierrez", "", "");
		User u3 = new User("CC", "10097733", "Alfonso", "Bustamante", "", "");
		c.getUsers().add(u1);
		c.getUsers().add(u2);
		c.getUsers().add(u3);
		
	}
	
	public void setup3() {
		c = new Company();
		User u1 = new User("CC", "1068895", "Pedro", "Hernandez", "3165577", "cra 23 #20-36");
		User u2 = new User("CC","63883786" ,"Sandra", "Gutierrez", "", "");
		User u3 = new User("CC", "10097733", "Alfonso", "Bustamante", "", "");
		User u4 = new User("CC", "44695237", "Andres", "Lopez", "", "");
		c.getUsers().add(u1);
		c.getUsers().add(u2);
		c.getUsers().add(u3);
		c.getUsers().add(u4);
		TurnType tt1 = new TurnType("anciano", 1.5f);
		TurnType tt2 = new TurnType("embarazada", 1f);
		TurnType tt3 = new TurnType("adulto", 2.5f);
		c.getTurnTypes().add(tt1);
		c.getTurnTypes().add(tt2);
		c.getTurnTypes().add(tt3);
	}
	
	public void setup4()throws Exception {
		c = new Company();
		User u1 = new User("CC", "1068895", "Pedro", "Hernandez", "3165577", "cra 23 #20-36");
		c.getUsers().add(u1);
		TurnType tt1 = new TurnType("adulto", 2.5f);
		c.getTurnTypes().add(tt1);
		c.giveTurn("1068895", tt1.getName());
	}

	@Test
	public void test1() throws Exception{
		setup1();
		c.addUser("CC", "1068895", "Pedro", "Hernandez", "3165577", "cra 23 #20-36");
		assertEquals("The size is not correct, this means that the client was not added", 1, c.getUsers().size());
		assertEquals("The id does not match, that means that the parameter was not joined correctly", "1068895", c.getUsers().get(0).getId());
	}
	
	@Test
	public void test2() throws Exception {
		setup2();
		c.addUser("CC", "44695237", "Andres", "Lopez", "", "");
		assertEquals("The size is not correct, this means that the client was not added", 4, c.getUsers().size());
		assertEquals("The id does not match, that means that the parameter was not joined correctly", "44695237", c.getUsers().get(3).getId());
		
	}
	
	@Test(expected =UserExistsException.class)
	public void test3() throws Exception{
		setup3();
		c.addUser("CC", "44695237", "Andres", "Lopez", "", "");
	}
	
	@Test
	public void test4() throws Exception {
		setup1();
		User temp = new User ("CC", "6455443", "Gabriel", "Martinez", "", "");
		c.getUsers().add(temp);
		assertEquals("The objects are not equals", temp, c.getUsers().get(0));
		
	}
	
	@Test
	public void test5() {
		setup2();
		c.selectionSortUsersById();
		assertEquals("The first user does not match", "10097733", c.getUsers().get(0).getId());
		assertEquals("The second user does not match", "1068895", c.getUsers().get(1).getId());
		assertEquals("The first user does not match", "63883786", c.getUsers().get(2).getId());
	}
	
	@Test
	public void test6() {
		setup1();
		assertEquals("The method does not return the \"There are not users created\"", "There are not users created", c.bubbleSortUserByNameAndLastName());
		assertEquals("The method does not return the \"There are not users created\"", "There are not users created", c.selectionSortUsersById());
	}
	
	@Test
	public void test7() {
		setup3();
		c.insertionSortTurnTypesByName();
		assertEquals("The first turn type does not match", "adulto", c.getTurnTypes().get(0).getName());
		assertEquals("The first turn type does not match", "anciano", c.getTurnTypes().get(1).getName());
		assertEquals("The first turn type does not match", "embarazada", c.getTurnTypes().get(2).getName());
	}
	
	@Test(expected = UserHasTurnException.class)
	public void test8() throws Exception{
		setup4();
		c.giveTurn("1068895", "adulto");
	}
	
	@Test(expected = UserDoesNotExistException.class)
	public void test14()throws Exception{
		setup1();
		c.giveTurn("1068895", "anciano");
	}
	
	@Test(expected = NoMoreTurnsToCallException.class)
	public void test15()throws Exception{
		setup1();
		c.attendTurn();
	}

}
