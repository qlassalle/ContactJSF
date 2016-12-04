package models;

import java.util.List;

public class Contact {
	
	private long id;
	private String firstName, lastName, email;
	private List<PhoneNumber> phones;
	private List<Groupe> books;
	private Address address;
	
	public Contact(long id, String firstName, String lastName, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			Contact c = (Contact)obj;
			if(c.getId() == this.getId()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Contact{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				'}';
	}

	public void setId(long value) {
		this.id = value;
	}

	public long getId() {
		return this.id;
	}
	
	public void setFirstName(String value) {
		this.firstName = value;
	}

	public String getFirstName() {
		return this.firstName;
	}
	
	public void setLastName(String value) {
		this.lastName = value;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public String getEmail() {
		return this.email;
	}
	
	public List<PhoneNumber> getPhones() {
		return phones;
	}

	public void setPhones(List<PhoneNumber> phones) {
		this.phones = phones;
	}

	public List<Groupe> getBooks() {
		return books;
	}

	public void setBooks(List<Groupe> books) {
		this.books = books;
	}

	public void setAddress(Address value) {
		this.address = value;
	}

	public Address getAddress() {
		return this.address;
	}

}
