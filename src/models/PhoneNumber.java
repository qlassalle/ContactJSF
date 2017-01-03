package models;

public class PhoneNumber {

    private int idContact, idPhoneNumber;
    private String kind, number;

    public PhoneNumber(int idPhoneNumber, String kind, String number, int idContact) {
        this.idPhoneNumber = idPhoneNumber;
        this.kind = kind;
		this.number = number;
		this.idContact = idContact;
	}

	public int getIdContact() {
		return this.idContact;
	}

    public void setIdContact(int value) {
        this.idContact = value;
    }

    public int getIdPhoneNumber() {
        return this.idPhoneNumber;
    }

    public void setIdPhoneNumber(int value) {
        this.idPhoneNumber = value;
    }

	public String getKind() {
		return this.kind;
	}

    public void setKind(String value) {
        this.kind = value;
    }

	public String getNumber() {
		return this.number;
	}

    public void setNumber(String value) {
        this.number = value;
    }

}
