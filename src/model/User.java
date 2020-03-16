package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Comparable<User>, Serializable {
	private String idType;
	private String id;
	private String name;
	private String lastName;
	private String phone;
	private String address;
	
	public User(String idType, String id, String name, String lastName, String phone, String address) {
		this.idType = idType;
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "(ID type: " + idType + ", ID: " + id + ", name: " + name + ", Last name: " + lastName + ", Phone number: "
				+ phone + ", Address: " + address + ")";
	}

	@Override
	public int compareTo(User u) {
		if(name.compareToIgnoreCase(u.name) < 0) {
			return -1;
		}
		else if(name.compareToIgnoreCase(u.name) > 0) {
			return 1;
		}
		else {
			if(lastName.compareToIgnoreCase(u.lastName) < 0) {
				return -1;
			}
			else if(lastName.compareToIgnoreCase(u.lastName) > 0) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}
	
}
