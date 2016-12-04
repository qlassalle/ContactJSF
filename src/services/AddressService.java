package services;

import java.util.List;

import daos.DAOAddress;
import models.Address;

public class AddressService {

	private DAOAddress daoa;
	
	public AddressService() {
		daoa = new DAOAddress();
	}

	public int addAddress(String street, String city, String zip, String country) {
		
		return daoa.save(street, city, zip, country);
	}
	
	public String delete(int idAddress) {
		return daoa.delete(idAddress);
	}
	
	public List<Address> getAllAddresses() {
		return daoa.getAllAddresses();
	}

	public String update(String street, String city, String zip, String country, int id) {
		return daoa.update(street,city,zip,country,id);
	}
	
	public Address getAddress(int id) {
		return daoa.getAddress(id);
	}
}
