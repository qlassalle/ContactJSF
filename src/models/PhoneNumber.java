package models;

public class PhoneNumber {

	private int idContact, id;
	private String kind, number;
	
	public PhoneNumber(int id, String kind, String number, int idContact) {
		this.id = id;
		this.kind = kind;
		this.number = number;
		this.idContact = idContact;
	}

	public void setIdContact(int value) {
		this.idContact = value;
	}

	public int getIdContact() {
		return this.idContact;
	}

	public void setId(int value) {
		this.id = value;
	}

	public int getId() {
		return this.id;
	}

	public void setKind(String value) {
		this.kind = value;
	}

	public String getKind() {
		return this.kind;
	}

	public void setNumber(String value) {
		this.number = value;
	}

	public String getNumber() {
		return this.number;
	}

}
