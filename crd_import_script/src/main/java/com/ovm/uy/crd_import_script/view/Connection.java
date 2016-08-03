package com.ovm.uy.crd_import_script.view;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.ovm.uy.crd_import_script.view.config.Configuration;

public class Connection {

	private static Connection instance; 
	java.sql.Connection connection;

	private Connection() {
	}

	public static Connection getInstance() {
		if (instance == null) {
			instance = new Connection();
		}
		return instance;
	}
 

	public java.sql.Connection getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			StringBuilder sConnection = new StringBuilder();
			Class.forName("org.postgresql.Driver");

//			configuration.getProperty(Configuration.CONFIGURATION_HOST);
//			configuration.getProperty(Configuration.CONFIGURATION_USER);
//			configuration.getProperty(Configuration.CONFIGURATION_PASSWORD);
			sConnection.append("jdbc:postgresql://");
			sConnection.append(Configuration.getInstance().getProperty(Configuration.CONFIGURATION_HOST));
			sConnection.append("?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");

			// String url =
			// "jdbc:postgresql://localhost:5433/Ale?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

			connection = DriverManager.getConnection(sConnection.toString(),
					Configuration.getInstance().getProperty(Configuration.CONFIGURATION_USER),
					Configuration.getInstance().getProperty(Configuration.CONFIGURATION_PASSWORD));

			((org.postgresql.PGConnection) connection).addDataType("geometry", Class.forName("org.postgis.PGgeometry"));
			((org.postgresql.PGConnection) connection).addDataType("box3d", Class.forName("org.postgis.PGbox3d"));
		}
		return connection;
	}
}
