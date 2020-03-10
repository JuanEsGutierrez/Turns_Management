package ui;
import java.util.*;

import customExceptions.NoMoreTurnsToCallException;
import customExceptions.NotEnoughFieldsException;
import customExceptions.TurnTypeDoesNotExistException;
import customExceptions.TurnTypeExistsException;
import customExceptions.TurnTypesEmptyException;
import customExceptions.UserDoesNotExistException;
import customExceptions.UserExistsException;
import customExceptions.UserHasTurnException;
import model.Company;

public class Main {
	private Scanner input;
	private Company company;
	
	public static void main(String[] args) {
		Main main = new Main();
		boolean x = true;
		while(x) {
			long start = System.currentTimeMillis();
			System.out.println("MENU \n1.Show time and date \n2. Add user \n3.Add turn types \n4. Register turn \n5. Attend turn \n6. Exit");
			System.out.println(main.company.showSoftwareTime());
			int option = Integer.parseInt(main.input.nextLine());
			switch(option) {
			case 1:
				long end = System.currentTimeMillis();
				long difference = end - start;
				main.company.updateSoftwareTime(difference);
				System.out.println(main.company.showSoftwareTime());
				break;
			case 2:
				main.addUserMain();
				break;
			case 3:
				main.addTurnTypeMain();
				break;
			case 4:
				main.giveTurnMain();
				break;
			case 5:
				main.attendTurnMain();
				break;
			case 6:
				x = false;
				break;
			}
		long end = System.currentTimeMillis();
		long difference = end - start;
		main.company.updateSoftwareTime(difference);
		}
	}
	
	public Main() {
		input = new Scanner(System.in);
		company = new Company();
	}
	
	public void addTurnTypeMain() {
		System.out.println("Write the name");
		String name = input.nextLine().toUpperCase();
		System.out.println("Write the duration");
		float duration = Float.parseFloat(input.nextLine());
		try {
			company.addTurnType(name, duration);
		} catch (NotEnoughFieldsException nefe) {
			System.out.println(nefe.getMessage());
		} catch (TurnTypeExistsException ttee) {
			System.out.println(ttee.getName() + ttee.getMessage());
		}
	}
	
	public void addUserMain() {
		System.out.println("Write the ID type");
		String idType = input.nextLine();
		System.out.println("Write the ID");
		String id = input.nextLine().toUpperCase();
		System.out.println("Write the name");
		String name = input.nextLine();
		System.out.println("Write the last name");
		String lastName = input.nextLine();
		System.out.println("Write the phone number");
		String phone = input.nextLine();
		System.out.println("Write the address");
		String address = input.nextLine();
		try {
			System.out.println(company.addUser(idType, id, name, lastName, phone, address));
		} catch (NotEnoughFieldsException nefe) {
			System.out.println(nefe.getMessage());
		} catch (UserExistsException uee) {
			System.out.println(uee.getId() + uee.getMessage());
		}
	}
	
	public void giveTurnMain() {
		System.out.println("Write the ID");
		String id = input.nextLine().toUpperCase();
		System.out.println(company.showTurnTypes());
		System.out.println("Write the turn type name");
		String name = input.nextLine().toUpperCase();
		try {
			System.out.println(company.giveTurn(id, name));
		} catch (UserHasTurnException uhte) {
			System.out.println(uhte.getMessage());
		} catch (UserDoesNotExistException udnee) {
			System.out.println(udnee.getMessage());
		} catch (TurnTypeDoesNotExistException ttdnee) {
			System.out.println(ttdnee.getMessage());
		} catch (TurnTypesEmptyException ttee) {
			System.out.println(ttee.getMessage());
		}
	}
	
	public void attendTurnMain() {
		try {
			System.out.println(company.attendTurn());
			System.out.println("1. Attended     2.Left");
			int option = Integer.parseInt(input.nextLine());
			switch(option) {
			case 1:
				company.setStatusTurn(option);
				break;
			case 2:
				company.setStatusTurn(option);
				break;
			default:
				System.out.println("ERROR! No valid option");
				break;
			}
		}
		catch (NoMoreTurnsToCallException nmttce) {
			System.out.println(nmttce.getMessage());
		}
	}
	
}
