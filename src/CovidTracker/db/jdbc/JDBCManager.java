package CovidTracker.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import CovidTracker.db.ifaces.DBManager;
import db.pojos.Patient;

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

	@Override
	public void addPerson(Patient p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Patient getPatient(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patient> searchPatientByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ModifyPatient(Patient p) {
		// TODO Auto-generated method stub
		
	}

}
