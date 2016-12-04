package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Address;
import models.Contact;

public class DAOContact {

	private static Connection connexion;
	
	public DAOContact() {
		connexion = GlobalConnection.getInstance();
	}

	public String save(String nom, String prenom, String email) {
		connexion = GlobalConnection.getInstance();
		Statement stmt;
		try {
			stmt = connexion.createStatement();
			stmt.executeUpdate("insert into contact(nom, prenom, email) values(\"" + nom + "\",\""
					+ prenom + "\",\"" + email + "\");");
			stmt.close();
			return null;

		} catch (SQLException e) {
			return e.getMessage();
		}
		finally {
			GlobalConnection.closeConnection(connexion);
		}
	}

	public String update(int id, String nom, String prenom, String email) {
		connexion = GlobalConnection.getInstance();
		int result = 0;
		String req = "update contact set id = ?, nom = ?, prenom = ?, email = ? where id = ?";
		System.out.println(id + " " + nom + " " + prenom + " " + email);
		try {
			PreparedStatement stmt = connexion.prepareStatement(req);
			stmt.setInt(1, id);
			stmt.setString(2, nom);
			stmt.setString(3, prenom);
			stmt.setString(4, email);
			stmt.setInt(5, id);
			result = stmt.executeUpdate();
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

	public List<Contact> getContactByFirstName(String firstName) {
		return getContact(firstName);
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

	public List<Contact> getContact(String firstName) {
		connexion = GlobalConnection.getInstance();
		List<Contact> lesContacts = new ArrayList<Contact>();
		Contact c = null;
		try {
			String req = "select * from contact where nom like ?";
			ResultSet result;
			try (PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setString(1, "%" + firstName + "%");
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
}
