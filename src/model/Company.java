package model;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
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

@SuppressWarnings("serial")
public class Company implements Serializable {
	public final static long CHANGING_TURN_SECONDS = 15;
	
	private char currentTurnLetter;
	private int currentTurnNumber;
	private char turnLetterToAssign;
	private int turnNumberToAssign;
	
	private ArrayList<User> users;
	private ArrayList<Turn> turns;
	private ArrayList<TurnType> turnTypes;
	private SoftwareDateTime softwareDateTime;
	
	public Company() {
		users = new ArrayList<User>();
		turns = new ArrayList<Turn>();
		turnTypes = new ArrayList<TurnType>();
		softwareDateTime = new SoftwareDateTime();
		currentTurnLetter = 'A';
		currentTurnNumber = 0;
		turnLetterToAssign = 'A';
		turnNumberToAssign = 0;
	}
	
	/**
	 * @return the users
	 */
	public ArrayList<User> getUsers() {
		return users;
	}

	/**
	 * @return the turns
	 */
	public ArrayList<Turn> getTurns() {
		return turns;
	}

	/**
	 * @return the turnTypes
	 */
	public ArrayList<TurnType> getTurnTypes() {
		return turnTypes;
	}

	public String showSoftwareDateTime() {
		return "Current date and time: " + softwareDateTime.getDate() + " " + softwareDateTime.getTime();
	}
	
	public void updateSoftwareDateTime() {
		long difference = softwareDateTime.getDifference();
		softwareDateTime.setTime(LocalTime.now().plusSeconds(difference));
	}
	
	public void changeSoftwareDateTime(String date, String time) throws DateTimeParseException, NewDateTimeIsBeforeException, NoMoreTurnsToCallException {
		LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));
		if(dateTime.toLocalDate().isAfter(softwareDateTime.getDate()) && dateTime.toLocalTime().isAfter(softwareDateTime.getTime())) {
			LocalTime t1 = dateTime.toLocalTime();
			LocalTime t2 = softwareDateTime.getTime();
			softwareDateTime.setDifference(t2.until(t1, ChronoUnit.SECONDS));
			softwareDateTime.setDate(dateTime.toLocalDate());
			softwareDateTime.setTime(dateTime.toLocalTime());
			attendTurn();
		}
		else {
			throw new NewDateTimeIsBeforeException("The new date and time must be after the current one");
		}
	}
	
	public void addTurnType(String name, float duration) throws NotEnoughFieldsException, TurnTypeExistsException {
		if(name.isBlank() || duration < 0) {
			throw new NotEnoughFieldsException("There are not enough filled up fields");
		}
		else if(turnTypeExists(name)) {
			throw new TurnTypeExistsException(": a turn type with that name has already been added", name);
		}
		else {
			turnTypes.add(new TurnType(name, duration));
			insertionSortTurnTypesByName();
		}
	}
	
	public void insertionSortTurnTypesByName() {
		for(int i = 1; i < turnTypes.size(); i++) {
			TurnType temp = turnTypes.get(i);
			int j = i - 1;
			while(j >= 0 && turnTypes.get(j).getName().compareTo(temp.getName()) > 0) {
				turnTypes.set(j + 1, turnTypes.get(j));
				j--;
			}
			turnTypes.set(j + 1, temp);
		}
	}
	
	public boolean turnTypeExists(String name) {
		boolean exists = false;
		for(int i = 0; i < turnTypes.size(); i++) {
			if(turnTypes.get(i).getName().equals(name)) {
				exists = true;
			}
		}
		return exists;
	}

	public String addUser(String idType, String id, String name, String lastName, String phone, String address) throws NotEnoughFieldsException, UserExistsException {
		String msg = "";
		if(idType.isBlank() || id.isBlank() || name.isBlank() || lastName.isBlank()) {
			throw new NotEnoughFieldsException("There are not enough filled up fields");
		}
		else if(userExists(id)){
			throw new UserExistsException(": an user with that ID has already been added", id);
		}
		else {
			users.add(new User(idType, id, name, lastName, phone, address));
			msg = "The user has been added";
		}
		return msg;
	}
	
	public String giveTurn(String id, String name) throws UserHasTurnException, UserDoesNotExistException, TurnTypesEmptyException, TurnTypeDoesNotExistException {
		String msg = "";
		if(turnTypes.isEmpty()) {
			throw new TurnTypesEmptyException("There are not turn types created");
		}
		else {
			if(turns.isEmpty()) {
				boolean x = true;
				for(int i = 0; i < users.size() && x; i++) {
					if(users.get(i).getId().equals(id)) {
						SoftwareDateTime dateTime = new SoftwareDateTime();
						dateTime.setDate(softwareDateTime.getDate());
						dateTime.setTime(softwareDateTime.getTime());
						turns.add(new Turn(turnLetterToAssign, turnNumberToAssign, users.get(i), binarySearchTurnType(name), dateTime));
						msg = "The turn " + turnLetterToAssign + turnNumberToAssign + " has been assigned to the user:\n" + users.get(i).toString();
						advanceTurnToAssign();
						x = false;
					}
				}
				if(x) {
					throw new UserDoesNotExistException("An user with that ID does not exist");
				}
			}
			else {
				boolean x = true;
				for(int i = 0; i < turns.size() && x; i++) {
					if(turns.get(i).getUser().getId().equals(id) && turns.get(i).isAttended() == false && turns.get(i).isLeft() == false) {
						x = false;
						throw new UserHasTurnException("This user has the turn " + turns.get(i).getTurnLetter() + turns.get(i).getTurnNumber() + " registered");
					}
				}
				if(x) {
					boolean y = true;
					for(int i = 0; i < users.size() && y; i++) {
						if(users.get(i).getId().equals(id)) {
							SoftwareDateTime dateTime = new SoftwareDateTime();
							dateTime.setDate(softwareDateTime.getDate());
							dateTime.setTime(softwareDateTime.getTime());
							turns.add(new Turn(turnLetterToAssign, turnNumberToAssign, users.get(i), binarySearchTurnType(name), dateTime));
							msg = "The turn " + turnLetterToAssign + turnNumberToAssign + " has been assigned to the user:\n" + users.get(i).toString();
							advanceTurnToAssign();
							y = false;
						}
					}
					if(y) {
						throw new UserDoesNotExistException("An user with that ID does not exist");
					}
				}
			}
		}
		return msg;
	}
	
	public TurnType binarySearchTurnType(String name) throws TurnTypeDoesNotExistException {
		TurnType out = null;
		int start = 0;
		int end = turnTypes.size() - 1;
		while(out == null && start <= end) {
			int mid = (start + end) / 2;
			if(turnTypes.get(mid).getName().equals(name)) {
				out = turnTypes.get(mid);
			}
			else if(turnTypes.get(mid).getName().compareTo(name) < 0) {
				start = mid + 1;
			}
			else {
				end = mid - 1;
			}
		}
		if(out == null) {
			throw new TurnTypeDoesNotExistException("A turn type with that name does not exist");
		}
		return out;
	}
	
	public String showTurnTypes() {
		String msg = "";
		for(int i = 0; i < turnTypes.size(); i++) {
			msg += turnTypes.get(i).toString() + "\n";
		}
		return msg;
	}
	
	public void advanceTurnToAssign() {
		if(turnLetterToAssign == 'Z' && turnNumberToAssign == 99) {
			turnLetterToAssign = 'A';
			turnNumberToAssign = 0;
		}
		else if(turnNumberToAssign == 99) {
			turnLetterToAssign++;
			turnNumberToAssign = 0;
		}
		else {
			turnNumberToAssign++;
		}
	}
	
	public String attendTurn() throws NoMoreTurnsToCallException {
		String msg = "";
		boolean x = true;
		updateSoftwareDateTime();
		for(int i = 0; i < turns.size(); i++) {
			if(turns.get(i).getTurnLetter() == currentTurnLetter && turns.get(i).getTurnNumber() == currentTurnNumber && (turns.get(i).getDateTime().getDate().isBefore(softwareDateTime.getDate()) || turns.get(i).getDateTime().getDate().isEqual(softwareDateTime.getDate())) && turns.get(i).getDateTime().getTime().isBefore(softwareDateTime.getTime()) && turns.get(i).isAttended() == false && turns.get(i).isLeft() == false) {
				msg += "Calling for the turn " + turns.get(i).getTurnLetter() + turns.get(i).getTurnNumber() + " of type " + turns.get(i).getTurnType().getName()
						+ " registered at " + turns.get(i).getDateTime().getTime() + "\n";
				setStatusTurn(turns.get(i));
				long duration = (long)(turns.get(i).getTurnType().getDuration() * 60);
				softwareDateTime.setDifference(softwareDateTime.getDifference() + duration + CHANGING_TURN_SECONDS);
				advanceCurrentTurn();
				x = false;
			}
		}
		if(x) {
			throw new NoMoreTurnsToCallException("There are not turns left to call");
		}
		return msg;
	}
	
	public void setStatusTurn(Turn turn) {
		double option = Math.random();
		if(option < 0.5) {
			turn.setAttended(true);
		}
		else {
			turn.setLeft(true);
		}
	}
	
	public void advanceCurrentTurn() {
		if(currentTurnLetter == 'Z' && currentTurnNumber == 99) {
			currentTurnLetter = 'A';
			currentTurnNumber = 0;
		}
		else if(currentTurnNumber == 99) {
			currentTurnLetter++;
			currentTurnNumber = 0;
		}
		else {
			currentTurnNumber++;
		}
	}
	
	public boolean userExists(String id) {
		boolean exists = false;
		for(int i = 0; i < users.size() && !exists; i++) {
			if(users.get(i).getId().equals(id)) {
				exists = true;
			}
		}
		return exists;
	}
	
	public String reportUserTurns(String id, int option) throws IOException {
		String msg = "";
		for(int i = 0; i < turns.size(); i++) {
			if(turns.get(i).getUser().getId().equals(id)) {
				if(turns.get(i).isAttended() == false && turns.get(i).isLeft() == false) {
					msg += turns.get(i).getTurnLetter() + "" + turns.get(i).getTurnNumber() + " registered at " + turns.get(i).getDateTime().getDate()
							+ " " + turns.get(i).getDateTime().getTime() + " has not been attended\n";
				}
				else if(turns.get(i).isAttended()) {
					msg += turns.get(i).getTurnLetter() + "" + turns.get(i).getTurnNumber() + " registered at " + turns.get(i).getDateTime().getDate()
							+ " " + turns.get(i).getDateTime().getTime() + " was attended\n";
				}
				else if(turns.get(i).isLeft()) {
					msg += turns.get(i).getTurnLetter() + "" + turns.get(i).getTurnNumber() + " registered at " + turns.get(i).getDateTime().getDate()
							+ " " + turns.get(i).getDateTime().getTime() + " the user was called but didn't show up\n";
				}
			}
		}
		switch(option) {
		case 1:
			return msg;
		case 2:
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("data\\ReportUserTurns" + id + ".txt")));
			bw.write(msg);
			bw.close();
			return "File generated in data\\ReportUserTurns" + id + ".txt";
		default:
			return "ERROR! No valid option";
		}
	}
	
	public String bubbleSortUserByNameAndLastName() {
		if(users.isEmpty() == false) {
			for(int i = 0; i < users.size() - 1; i++) {
				for(int j = 0; j < users.size() - i - 1; j++) {
					if(users.get(j).compareTo(users.get(j + 1)) > 0) {
						User temp = users.get(j);
						users.set(j, users.get(j + 1));
						users.set(j + 1, temp);
					}
				}
			}
			String msg = "";
			for(int i = 0; i < users.size(); i++) {
				msg += users.get(i).toString() + "\n";
			}
			return msg;
		}
		else {
			return "There are not users created";
		}
	}
	
	public String selectionSortUsersById() {
		if(users.isEmpty() == false) {
			for(int i = 0; i < users.size() - 1; i++) {
				int min = i;
				for(int j = i + 1; j < users.size(); j++) {
					if(users.get(j).getId().compareTo(users.get(min).getId()) < 0) {
						min = j;
					}
				}
				User temp = users.get(min);
				users.set(min, users.get(i));
				users.set(i, temp);
			}
			String msg = "";
			for(int i = 0; i < users.size(); i++) {
				msg += users.get(i).toString() + "\n";
			}
			return msg;
		}
		else {
			return "There are not users created";
		}
	}
	
	public String saveProgram(Company company) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data\\saved.comp"));
		oos.writeObject(company);
		oos.close();
		return "Data saved in data\\saved.comp";
	}
	
	public Company loadProgram() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data\\saved.comp"));
		Company company = (Company)ois.readObject();
		ois.close();
		return company;
	}
	
}
