package ui;
import java.util.*;

import customExceptions.NoMoreTurnsToCallException;
import customExceptions.NotEnoughFieldsException;
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
			System.out.println("MENU \n1. Add user \n2. Register turn \n3. Attend turn \n4. Exit");
			int option = Integer.parseInt(main.input.nextLine());
			switch(option) {
			case 1:
				main.addUserMain();
				break;
			case 2:
				main.giveTurnMain();
				break;
			case 3:
				main.attendTurnMain();
				break;
			case 4:
				x = false;
				break;
			}
		}
	}
	
	public Main() {
		input = new Scanner(System.in);
		company = new Company();
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
		try {
			System.out.println(company.giveTurn(id));
		} catch (UserHasTurnException uhte) {
			System.out.println(uhte.getMessage());
		} catch (UserDoesNotExistException udnee) {
			System.out.println(udnee.getMessage());
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
