package daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GlobalConnection {
	
	private final String urlWindows = "jdbc:mysql://localhost:3306/contact";
	private final String utilisateur = "root";
	private final String motDePasseWindows = "";
	

	private static Connection connection;
	
	private static final GlobalConnection INSTANCE = new GlobalConnection();
		
	private GlobalConnection() {
		connection = setConnection();
	}

	private Connection setConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! <br/>"
					+ e.getMessage());
		}
		Connection connexion = null;
		try {
			connexion = DriverManager.getConnection(urlWindows, utilisateur, motDePasseWindows);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return connexion;
	}
		
	public static Connection getInstance() {
		if(connection == null) {
			new GlobalConnection();
		}
		return connection;
	}
	
	public static void closeConnection(Connection connection) {
		if(connection !=  null){
			try {
				connection.close();
				GlobalConnection.connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	}
}