package model;
import java.util.*;

import customExceptions.NoMoreTurnsToCallException;
import customExceptions.NotEnoughFieldsException;
import customExceptions.TurnTypeDoesNotExistException;
import customExceptions.TurnTypeExistsException;
import customExceptions.TurnTypesEmptyException;
import customExceptions.UserDoesNotExistException;
import customExceptions.UserExistsException;
import customExceptions.UserHasTurnException;

public class Company {
	private char currentTurnLetter;
	private int currentTurnNumber;
	private char turnLetterToAssign;
	private int turnNumberToAssign;
	
	private ArrayList<User> users;
	private ArrayList<Turn> turns;
	private ArrayList<TurnType> turnTypes;
	private SoftwareTime softwareTime;
	
	public Company() {
		users = new ArrayList<User>();
		turns = new ArrayList<Turn>();
		turnTypes = new ArrayList<TurnType>();
		softwareTime = new SoftwareTime();
		currentTurnLetter = 'A';
		currentTurnNumber = 0;
		turnLetterToAssign = 'A';
		turnNumberToAssign = 0;
	}
	
	public String showSoftwareTime() {
		return "Current time and date: " + softwareTime.getTime() + " " + softwareTime.getDate();
	}
	
	public void updateSoftwareTime(long difference) {
		difference *= 1000000;
		softwareTime.setTime(softwareTime.getTime().plusNanos(difference));
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
			sortTurnTypesByName();
		}
	}
	
	public void sortTurnTypesByName() {
		for(int i = 1; i < turnTypes.size(); i++) {
			TurnType temp = turnTypes.get(i);
			int j = i - 1;
			while(j >= 0 && turnTypes.get(j).getName().compareTo(temp.getName()) < 0) {
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
						turns.add(new Turn(turnLetterToAssign, turnNumberToAssign, users.get(i), binarySearchTurnType(name)));
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
							turns.add(new Turn(turnLetterToAssign, turnNumberToAssign, users.get(i), binarySearchTurnType(name)));
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
		for(int i = 0; i < turns.size() && x; i++) {
			if(turns.get(i).getTurnLetter() == currentTurnLetter && turns.get(i).getTurnNumber() == currentTurnNumber && turns.get(i).isAttended() == false && turns.get(i).isLeft() == false) {
				msg = "Calling for the turn " + turns.get(i).getTurnLetter() + turns.get(i).getTurnNumber();
				x = false;
			}
		}
		if(x) {
			throw new NoMoreTurnsToCallException("There are not turns left to call");
		}
		return msg;
	}
	
	public void setStatusTurn(int option) {
		boolean x = true;
		for(int i = 0; i < turns.size() && x; i++) {
			if(turns.get(i).getTurnLetter() == currentTurnLetter && turns.get(i).getTurnNumber() == currentTurnNumber && turns.get(i).isAttended() == false && turns.get(i).isLeft() == false) {
				x = false;
				if(option == 1) {
					turns.get(i).setAttended(true);
					advanceCurrentTurn();
				}
				else if(option == 2) {
					turns.get(i).setLeft(true);
					advanceCurrentTurn();
				}
			}
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
	
}
