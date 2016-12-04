package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Address;

public class DAOAddress {
	
	private static Connection connexion;
	
	public DAOAddress() {
		connexion = GlobalConnection.getInstance();
	}

	public Address getAddress(int id)
	{
		connexion = GlobalConnection.getInstance();
		Address address = null;
		try {
			String req = "select * from address where id = ? ";
			ResultSet result;
			try (PreparedStatement stmt = connexion.prepareStatement(req)){
				stmt.setInt(1, id);
				result = stmt.executeQuery();
				while(result.next()){
					address = new Address(result.getInt(1), result.getString(2),
							result.getString(3), result.getString(4), result.getString(5));
				}
			} 
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			// no need to close here, this method is already called by a method closing connection
		}
		return address;
	}


	public int save(String street, String city, String zip, String country) {
		connexion = GlobalConnection.getInstance();
		PreparedStatement stmt = null;
		try
		{
			String req = "insert into address(street, city, zip, country) values (?,?,?,?);";
			stmt = connexion.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, street);
			stmt.setString(2, city);
			stmt.setString(3, zip);
			stmt.setString(4, country);
			stmt.executeUpdate();
			try (ResultSet id = stmt.getGeneratedKeys()) {
				if(id.next()) {
					return id.getInt(1);
				}
			}
			stmt.close();
			return 0;
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
			return 0;
		}
		finally {
			GlobalConnection.closeConnection(connexion);
		}
	}

	public String delete(int id) {
		connexion = GlobalConnection.getInstance();
		String req = "delete from address where id = ?;";
		try {
			try(PreparedStatement stmt = connexion.prepareStatement(req)){
				stmt.setInt(1, id);
				stmt.executeUpdate();
				stmt.close();
				return null;
			}
		} catch (SQLException sqle) {
			return sqle.getMessage();
		} finally {
			GlobalConnection.closeConnection(connexion);
		}
	}

	public List<Address> getAllAddresses() {
		connexion = GlobalConnection.getInstance();
		List<Address> addresses = new ArrayList<Address>();
		String req = "select * from address ORDER BY zip ASC";
		try {
			try(Statement stmt = connexion.createStatement()) {
				ResultSet result = stmt.executeQuery(req);
				while(result.next()) {
					addresses.add(new Address(result.getInt(1), result.getString(2), result.getString(3),
							result.getString(4), result.getString(5)));
				}
			}
		}
		catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return addresses;
	}

	public String update(String street, String city, String zip, String country, int id) {
		connexion = GlobalConnection.getInstance();
		String req = "update address set id = ?, street = ?, city = ?, zip = ?, country = ? where id = ?";
		try {
			try(PreparedStatement stmt = connexion.prepareStatement(req)) {
				stmt.setInt(1, id);
				stmt.setString(2, street);
				stmt.setString(3, city);
				stmt.setString(4, zip);
				stmt.setString(5, country);
				stmt.setInt(6, id);
				stmt.executeUpdate();
				return null;
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
			return sqle.getMessage();
		} finally {
			GlobalConnection.closeConnection(connexion);
		}
	}
}
