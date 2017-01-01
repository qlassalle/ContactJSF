package daos;

import models.Address;
import models.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAOContact {

	private static final String MAIL_ERROR = "contactAlreadyExists";
	private static Connection connexion;

	public DAOContact() {
		connexion = GlobalConnection.getInstance();
	}

	public String save(String nom, String prenom, String email) {
		// check if a user doesn't already exist with this email
		if (emailExists(email)) return MAIL_ERROR;
		connexion = GlobalConnection.getInstance();
		String req = "INSERT INTO contact(nom, prenom, email) VALUES(?, ?, ?)";
		try {
			try (PreparedStatement pstmt = connexion.prepareStatement(req)) {
				pstmt.setString(1, nom);
				pstmt.setString(2, prenom);
				pstmt.setString(3, email);
				pstmt.executeUpdate();
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		finally {
			GlobalConnection.closeConnection(connexion);
		}
	}

	public String update(int id, String nom, String prenom, String email) {
		connexion = GlobalConnection.getInstance();
		String req = "update contact set id = ?, nom = ?, prenom = ?, email = ? where id = ?";
		try {
			PreparedStatement stmt = connexion.prepareStatement(req);
			stmt.setInt(1, id);
			stmt.setString(2, nom);
			stmt.setString(3, prenom);
			stmt.setString(4, email);
			stmt.setInt(5, id);
			stmt.executeUpdate();
			stmt.close();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		finally {
			GlobalConnection.closeConnection(connexion);
		}
	}

	public String delete(int id) {
		connexion = GlobalConnection.getInstance();
		String req = "delete from contact where id = ?";
		try {
			try (PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setInt(1, id);
				stmt.executeUpdate();
				stmt.close();
			}
		} catch (SQLException sqle) {
			return sqle.getMessage();
		}
		finally {
			GlobalConnection.closeConnection(connexion);
		}
		return null;
	}

	public List<Contact> getContactByFirstName(String lastName) {
		return getContact(lastName);
	}

	public List<Contact> getAllContacts() {
		connexion = GlobalConnection.getInstance();
		List<Contact> lesContacts = new ArrayList<Contact>();
		ResultSet result = null;
		try {
			try (Statement stmt = connexion.createStatement()) {
				result = stmt.executeQuery("select * from contact ORDER BY nom ASC");
				while (result.next()) {
					lesContacts.add(new Contact(result.getLong(1), result.getString(2), result.getString(3),
							result.getString(4)));
				}

			} finally {
				if (result != null)
					result.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			GlobalConnection.closeConnection(connexion);
		}
		return lesContacts;
	}

	public List<Contact> getContact(String lastName) {
		connexion = GlobalConnection.getInstance();
		List<Contact> lesContacts = new ArrayList<Contact>();
		Contact c = null;
		try {
			String req = "select * from contact where nom like ?";
			ResultSet result;
			try (PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setString(1, "%" + lastName + "%");
				result = stmt.executeQuery();
				while (result.next()) {
					c = new Contact(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));
					lesContacts.add(c);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			GlobalConnection.closeConnection(connexion);
		}
		return lesContacts;
	}

	public Contact getContactById(int id) {
		connexion = GlobalConnection.getInstance();
		Contact c = null;
		try {
			String req = "select * from contact where id like ?";
			ResultSet result;
			try (PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setInt(1, id);
				result = stmt.executeQuery();
				while (result.next()) {
					c = new Contact(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			// no need to close here, this method is already called be a method closing connection
		}
		return c;
	}


	public Address getContactAddress(int id)
	{
		connexion = GlobalConnection.getInstance();
		Address address = null;
		try {
			String req = "select idAddress from contact where id = ?";
			ResultSet result;
			try (PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setInt(1, id);
				result = stmt.executeQuery();
				while(result.next()){
					DAOAddress daoa = new DAOAddress();
					address = daoa.getAddress(result.getInt(1));
				}
			}
		}
		catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			GlobalConnection.closeConnection(connexion);
		}
		return address;
	}

	public String addAddress(int id, int idAddress) {
		connexion = GlobalConnection.getInstance();
		try {
			String req = "update contact set idAddress = ? where id = ?;";
			try(PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setInt(1, idAddress);
				stmt.setInt(2, id);
				stmt.executeUpdate();
				return null;
			}
		} catch(SQLException sqle) {
			return sqle.getMessage();
		}
		finally {
			GlobalConnection.closeConnection(connexion);
		}

	}

	public Map<Integer, String> getGroupes(int idContact) {
		connexion = GlobalConnection.getInstance();
		Map<Integer, String> lesGroupes = new HashMap<Integer, String>();
		try {
			String req = "select idGroupe from contact_groupe where idContact = ?";
			ResultSet result, groupeResult;
			try (PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setInt(1, idContact);
				result = stmt.executeQuery();
				while(result.next()){
					String sql = "select id, nom from groupe where id = ?";
					try(PreparedStatement pstmt = connexion.prepareStatement(sql)) {
						pstmt.setInt(1, result.getInt(1));
						groupeResult = pstmt.executeQuery();
						while(groupeResult.next()) {
							lesGroupes.put(groupeResult.getInt(1), groupeResult.getString(2));
						}
					}
				}
			}
		}
		catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			GlobalConnection.closeConnection(connexion);
		}
		return !lesGroupes.isEmpty() ? lesGroupes : null;
	}

	private boolean emailExists(String email) {
		connexion = GlobalConnection.getInstance();
		try {
			String req = "SELECT * FROM contact WHERE email = ?";
			ResultSet result;
			try (PreparedStatement pstmt = connexion.prepareStatement(req)) {
				pstmt.setString(1, email);
				result = pstmt.executeQuery();
				if (result.next()) {
					return true;
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			GlobalConnection.closeConnection(connexion);
		}
		return false;
	}
}
