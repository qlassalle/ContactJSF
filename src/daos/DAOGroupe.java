package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Contact;
import models.Groupe;

public class DAOGroupe {
	
	private static Connection connexion;
	
	public DAOGroupe() {
		connexion = GlobalConnection.getInstance();
	}
	
	public String save(String name) {
		connexion = GlobalConnection.getInstance();
		Statement stmt;
		try {
			stmt = connexion.createStatement();
			stmt.executeUpdate("insert into groupe(nom) values (\"" + name + "\");");
			stmt.close();
			return null;
		} catch (SQLException sqle) {
			return sqle.getMessage();
		} finally {
			GlobalConnection.closeConnection(connexion);
		}
	}

	public List<Groupe> getAllGroupes() {
		connexion = GlobalConnection.getInstance();
		List<Groupe> lesGroupes = new ArrayList<Groupe>();
		ResultSet result = null;
		try {
			try (Statement stmt = connexion.createStatement()) {
				result = stmt.executeQuery("select * from groupe order by nom asc");
				while(result.next()) {
					lesGroupes.add(new Groupe(result.getInt(1), result.getString(2)));		
				}
			} finally {
				if(result != null) {
					result.close();
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			GlobalConnection.closeConnection(connexion);
		}
		return lesGroupes;
	}

	public Integer getNbMembre(int id) {
		connexion = GlobalConnection.getInstance();
		try {
			String req = "select count(id) from contact_groupe where idGroupe = ?";
			ResultSet result = null;
			try (PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setInt(1, id);
				result = stmt.executeQuery();
				while(result.next()) {
					return result.getInt(1);
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}

	public void AddContact(int id, String[] ids) {
		deleteAllContacts(id);
		connexion = GlobalConnection.getInstance();
		Statement stmt;
		String req;
		try {
			for(String contact : ids) {
				req = "insert into contact_groupe(idGroupe, idContact) values("+id+"," +contact+");";
				stmt = connexion.createStatement();
				stmt.executeUpdate(req);
				stmt.close();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			GlobalConnection.closeConnection(connexion);
		}	
	}
	
	public List<Contact> getMembres(int idGroupe)
	{
		connexion = GlobalConnection.getInstance();
		List<Contact> lesContacts = new ArrayList<Contact>();
		DAOContact daoc = new DAOContact();
		ResultSet result = null;
		try {
			try(Statement stmt = connexion.createStatement()) {
				result = stmt.executeQuery("select * from contact_groupe where idGroupe = " + idGroupe);
				while(result.next()) {
					System.out.println(result.getInt("idContact"));
					lesContacts.add(daoc.getContactById(result.getInt("idContact")));
				}
			} finally {
				if(result != null) {
					result.close();
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} 
		finally {
			GlobalConnection.closeConnection(connexion);
		}
		return lesContacts;
	}

	public String delete(int idGroupe) {
		connexion = GlobalConnection.getInstance();
		String req = "delete from groupe where id = ? ";
		try {
			try(PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setInt(1, idGroupe);
				stmt.executeUpdate();
				stmt.close();
				return null;
			}
		} catch(SQLException sqle) {
			return sqle.getMessage();
		} finally {
			GlobalConnection.closeConnection(connexion);
		}
	}
	
	private void deleteAllContacts(int idGroupe) {
		connexion = GlobalConnection.getInstance();
		String req = "delete from contact_groupe where idGroupe = ?";
		try {
			try(PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setInt(1, idGroupe);
				stmt.executeUpdate();
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			GlobalConnection.closeConnection(connexion);
		}
	}

	public String removeFromGroupe(int idContact, int idGroupe) {
		connexion = GlobalConnection.getInstance();
		String req = "delete from contact_groupe where idContact = ? and idGroupe = ?";
		try {
			try (PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setInt(1, idContact);
				stmt.setInt(2, idGroupe);
				stmt.executeUpdate();
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
			return sqle.getMessage();
		} finally {
			GlobalConnection.closeConnection(connexion);
		}
		return null;
	}
}
