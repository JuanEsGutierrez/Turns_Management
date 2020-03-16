package ui;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.*;

import customExceptions.NewDateTimeIsBeforeException;
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
			System.out.println("MENU \n1. Change date and time \n2. Show time and date \n3. Add user \n4. Add turn types");
			System.out.println("5. Register turn \n6. Attend turn \n7. Generate report of turns that an user has registered"
			+ "\n8. Sort users by name and last name \n9. Sort users by ID \n10. Save program \n11. Load program \n12. Exit");
			main.company.updateSoftwareDateTime();
			System.out.println(main.company.showSoftwareDateTime());
			int option = Integer.parseInt(main.input.nextLine());
			switch(option) {
			case 1:
				main.changeSoftwareDateTimeMain();
				break;
			case 2:
				main.company.updateSoftwareDateTime();
				System.out.println(main.company.showSoftwareDateTime());
				break;
			case 3:
				main.addUserMain();
				break;
			case 4:
				main.addTurnTypeMain();
				break;
			case 5:
				main.giveTurnMain();
				break;
			case 6:
				main.attendTurnMain();
				break;
			case 7:
				main.reportUserTurnsMain();
				break;
			case 8:
				System.out.println(main.company.bubbleSortUserByNameAndLastName());
				break;
			case 9:
				System.out.println(main.company.selectionSortUsersById());
				break;
			case 10:
				main.saveProgramMain();
				break;
			case 11:
				main.loadProgramMain();
				break;
			case 12:
				x = false;
				break;
			}
		}
	}
	
	public Main() {
		input = new Scanner(System.in);
		company = new Company();
	}
	
	public void changeSoftwareDateTimeMain() {
		System.out.println("Write the date using the format YYYY-MM-DD");
		String date = input.nextLine();
		System.out.println("Write the time using the format HH:MM");
		String time = input.nextLine();
		try {
			company.changeSoftwareDateTime(date, time);
		} catch (DateTimeParseException dtpe) {
			System.out.println(dtpe.getMessage());
		} catch (NewDateTimeIsBeforeException ndtibe) {
			System.out.println(ndtibe.getMessage());
		} catch (NoMoreTurnsToCallException nmttce) {
			System.out.println(nmttce.getMessage());
		}
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
		company.updateSoftwareDateTime();
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
		}
		catch (NoMoreTurnsToCallException nmttce) {
			System.out.println(nmttce.getMessage());
		}
	}
	
	public void reportUserTurnsMain() {
		System.out.println("Write the ID");
		String id = input.nextLine().toUpperCase();
		System.out.println("1. Show report on console \n2. Show report on text archive");
		int option = Integer.parseInt(input.nextLine());
		try {
			System.out.println(company.reportUserTurns(id, option));
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	
	public void saveProgramMain() {
		try {
			System.out.println(company.saveProgram(company));
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	
	public void loadProgramMain() {
		try {
			company = company.loadProgram();
			System.out.println("Program loaded");
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	
}
