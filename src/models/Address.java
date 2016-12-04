package models;


public class Address {

	private int id;
	private String street, city, zip, country;
	
	public Address(int id, String street, String city, String zip, String country) {
		this.id = id;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.country = country;
	}

	public void setId(int value) {
		this.id = value;
	}

	public int getId() {
		return this.id;
	}

	public void setStreet(String value) {
		this.street = value;
	}

	public String getStreet() {
		return this.street;
	}

	public void setCity(String value) {
		this.city = value;
	}

	public String getCity() {
		return this.city;
	}
	
	public void setZip(String value) {
		this.zip = value;
	}

	public String getZip() {
		return this.zip;
	}

	public void setCountry(String value) {
		this.country = value;
	}

	public String getCountry() {
		return this.country;
	}

	@Override
	public String toString() {
		return street + ", " + zip + " " + city + ", " + country;
	}

	
}
