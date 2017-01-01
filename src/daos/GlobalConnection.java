package daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GlobalConnection {

	private static Connection connection;
    private final String url = "jdbc:mysql://localhost:8889/contact";
    private final String utilisateur = "root";
    private final String mdp = "root";

	private GlobalConnection() {
		connection = setConnection();
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

    private Connection setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! <br/>"
                    + e.getMessage());
        }
        Connection connexion = null;
        try {
            connexion = DriverManager.getConnection(url, utilisateur, mdp);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return connexion;
    }
}