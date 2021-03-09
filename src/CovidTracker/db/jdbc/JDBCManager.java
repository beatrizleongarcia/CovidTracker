package CovidTracker.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import CovidTracker.db.ifaces.DBManager;

public class JDBCManager implements DBManager {
	public Connection c;
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		try {
			// Open database connection
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:./db/company.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
			
			// Here is where I do things with the database
			
			// Close database connection
			c.close();
			System.out.println("Database connection closed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void LookReplacement(String title) {
		// TODO Auto-generated method stub

	}

}
