package models;

import java.util.List;

public class Groupe {


	private int id;
	private List<Contact> contacts;
	private String name;

	public Groupe(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void setId(int value) {
		this.id = value;
	}

	public int getId() {
		return this.id;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	
}
