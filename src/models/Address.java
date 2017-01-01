package models;


public class Address {

	private int idAddress;
	private String street, city, zip, country;

	public Address(int idAddress, String street, String city, String zip, String country) {
		this.idAddress = idAddress;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.country = country;
	}

	public int getIdAddress() {
		return this.idAddress;
	}

	public void setIdAddress(int value) {
		this.idAddress = value;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String value) {
		this.street = value;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String value) {
		this.city = value;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String value) {
		this.zip = value;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String value) {
		this.country = value;
	}

	@Override
	public String toString() {
		return street + ", " + zip + " " + city + ", " + country;
	}

	
}
