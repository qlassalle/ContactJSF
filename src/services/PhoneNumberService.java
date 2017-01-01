package services;

import daos.DAOPhoneNumber;
import models.PhoneNumber;

import java.util.List;

public class PhoneNumberService {

	DAOPhoneNumber daop;

	public PhoneNumberService() {
		daop = new DAOPhoneNumber();
	}
	
	public List<PhoneNumber> getPhoneNumbers(int idContact) {
		return daop.getPhoneNumbers(idContact);
	}
	
	public String save(String kind, String number, int idContact) {
		return daop.save(kind, number, idContact);
	}
	
	public String update(int id, String kind, String number) {
		return daop.update(id, kind, number);
	}
	
	public String delete(int id) {
		return daop.delete(id);
	}
	
	public PhoneNumber getPhoneNumber(int id) {
		return daop.getPhoneNumber(id);
	}
}
