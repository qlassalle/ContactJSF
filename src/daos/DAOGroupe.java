package daos;

import models.Contact;
import models.Groupe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOGroupe {

	private static Connection connexion;

	public DAOGroupe() {
		connexion = GlobalConnection.getInstance();
	}

	public String save(String name) {
		connexion = GlobalConnection.getInstance();
		String req = "INSERT INTO groupe(nom) VALUES (?)";
		try {
			try (PreparedStatement pstmt = connexion.prepareStatement(req)) {
				pstmt.setString(1, name);
				pstmt.executeUpdate();
			}
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

	public void AddContact(int id, List<String> ids) {
		deleteAllContacts(id);
		// the user wants to remove all the users from the group
		if (ids == null) return;
		connexion = GlobalConnection.getInstance();
		String req = "INSERT INTO contact_groupe(idGroupe, idContact) VALUES(?, ?)";
		try {
			for(String contact : ids) {
				try (PreparedStatement pstmt = connexion.prepareStatement(req)) {
					pstmt.setInt(1, id);
					pstmt.setInt(2, Integer.valueOf(contact));
					pstmt.executeUpdate();
				}
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
		String req = "SELECT * FROM contact_groupe WHERE idGroupe = ?";
		try {
			try (PreparedStatement pstmt = connexion.prepareStatement(req)) {
				pstmt.setInt(1, idGroupe);
				result = pstmt.executeQuery();
				while(result.next()) {
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

    public String getGroupeName(int idGroupe) {
        connexion = GlobalConnection.getInstance();
        String req = "SELECT nom FROM groupe WHERE id = ?";
        try {
            try (PreparedStatement pstmt = connexion.prepareStatement(req)) {
                pstmt.setInt(1, idGroupe);
                ResultSet result = pstmt.executeQuery();
                while (result.next()) {
                    return result.getString(1);
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return "";
    }
}
