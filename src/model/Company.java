package model;
import java.util.*;

import customExceptions.NoMoreTurnsToCallException;
import customExceptions.NotEnoughFieldsException;
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
	
	public Company() {
		users = new ArrayList<User>();
		turns = new ArrayList<Turn>();
		currentTurnLetter = 'A';
		currentTurnNumber = 0;
		turnLetterToAssign = 'A';
		turnNumberToAssign = 0;
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
	
	public String giveTurn(String id) throws UserHasTurnException, UserDoesNotExistException {
		String msg = "";
		if(turns.isEmpty()) {
			boolean x = true;
			for(int i = 0; i < users.size() && x; i++) {
				if(users.get(i).getId().equals(id)) {
					turns.add(new Turn(turnLetterToAssign, turnNumberToAssign, users.get(i)));
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
						turns.add(new Turn(turnLetterToAssign, turnNumberToAssign, users.get(i)));
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
