package daos;

import models.PhoneNumber;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOPhoneNumber {

	private static Connection connexion;

	public DAOPhoneNumber () {
		connexion = GlobalConnection.getInstance();
	}

	public List<PhoneNumber> getPhoneNumbers(int idContact) {
		connexion = GlobalConnection.getInstance();
		List<PhoneNumber> numbers = new ArrayList<PhoneNumber>();
		ResultSet result = null;
		try {
			String req = "select * from phone where idContact = ?";
			try(PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setInt(1, idContact);
				result = stmt.executeQuery();
				while(result.next()) {
					numbers.add(new PhoneNumber(result.getInt(1), result.getString(2), result.getString(3),
							result.getInt(4)));
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			GlobalConnection.closeConnection(connexion);
		}
		return numbers;
	}

	public String save(String kind, String number, int idContact) {
		connexion = GlobalConnection.getInstance();
		try {
			String req = "insert into phone(kind, number, idContact) values(?, ?, ?)";
			try (PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setString(1, kind);
				stmt.setString(2, number);
				stmt.setInt(3, idContact);
				stmt.executeUpdate();
				return null;
			}
		} catch (SQLException sqle) {
			return sqle.getMessage();
		} finally {
			GlobalConnection.closeConnection(connexion);
		}
	}

	public String update(int id, String kind, String number) {
		connexion = GlobalConnection.getInstance();
		int result = 0;
		String req = "update phone set id = ?, kind = ?, number = ? where id = ?";
		try {
			PreparedStatement stmt = connexion.prepareStatement(req);
			stmt.setInt(1, id);
			stmt.setString(2, kind);
			stmt.setString(3, number);
			stmt.setInt(4, id);
			result = stmt.executeUpdate();
			stmt.close();
			return null;
		} catch (SQLException e) {
			return e.getMessage();
		}
		finally {
			GlobalConnection.closeConnection(connexion);
		}
	}

	public String delete(int id){
		connexion = GlobalConnection.getInstance();
		String req = "delete from phone where id = ?";
		try{
			try(PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setInt(1, id);
				stmt.executeUpdate();
			}
		} catch(SQLException sqle) {
			return sqle.getMessage();
		}
		finally {
			GlobalConnection.closeConnection(connexion);
		}
		return null;
	}

	public PhoneNumber getPhoneNumber(int id) {
		connexion = GlobalConnection.getInstance();
		PhoneNumber phoneNumber = null;
		String req = "select * from phone where id = ?";
		try {
			try(PreparedStatement pstmt = connexion.prepareStatement(req)) {
				pstmt.setInt(1, id);
				ResultSet result = pstmt.executeQuery();
				while(result.next()) {
					phoneNumber = new PhoneNumber(result.getInt(1),
							result.getString(2), result.getString(3),
							result.getInt(4));
				}
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally{
			GlobalConnection.closeConnection(connexion);
		}
		return phoneNumber;
	}
}
