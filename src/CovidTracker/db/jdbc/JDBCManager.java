package CovidTracker.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
			Connection c = DriverManager.getConnection("jdbc:sqlite:./db/covidTracker.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
			
			this.create();
		} 
		catch(SQLException e) {
			System.out.println("Error, database exception.");
		}catch (Exception e) {
			System.out.println("Error, couldn´t connect to data based.");
			e.printStackTrace();
		}
	}

	@Override
	public void create() {
	try {
	// Create tables: begin
	Statement stmt1 = c.createStatement();
	String sql1 = "CREATE TABLE doctor(\n" 
	+ "Id INTEGER PRIMARY KEY;\n" 
	+ "name TEXT NOT NULL;\n"
	+ "hospital TEXT NOT NULL;\n" 
	+ ")";

	stmt1.executeUpdate(sql1);
	stmt1.close();
	Statement stmt2 = c.createStatement();
	String sql2 = "CREATE TABLE symptoms(\n"
	    + "Id INTEGER PRIMARY KEY;\n"
	+ "type TEXT NOT NULL;\n"
	+ ")";
	stmt2.executeUpdate(sql2);
	stmt2.close();
	Statement stmt3 = c.createStatement();
	String sql3 = "CREATE TABLE quarantine(\n"
	+ "id INTEGER PRIMARY KEY\n"
	+ "time INTEGER NOT NULL\n"
	+ "reason TEXT NOT NULL\n"
	+ ")";
	stmt3.executeUpdate(sql3);
	stmt3.close();
	Statement stmt4 = c.createStatement();
	String sql4 = "CREATE TABLE patient(\n"
	+ "id INTEGER PRIMARY KEY;\n"
	+ "name TEXT NOT NULL;\n"
	+ "date_birth DATE NOT NULL;\n"
	+ "job_title TEXT NOT NULL;\n"
	+ "Salary INTEGER NOT NULL;\n" 
	+ "days_off_work INTEGER NOT NULL;\n"
	+ "economic_impact INTEGER NOT NULL;\n"
	+ "doctor_id INTEGER REFERENCES doctor(id)\n"
	+ "symptoms_id INTEGER REFERENCES symptoms(id)\n"
	+ "quarantine_id INTEGER REFERENCES quarantine(id)\n"
	+ ")";
	stmt4.executeUpdate(sql4);
	stmt4.close();
	Statement stmt5 = c.createStatement();
	String sql5 = "CREATE TABLE covid_test(\n"
	+ "Id INTEGER PRIMARY KEY;\n"
	+ "date_of_test DATE NOT NULL;\n"
	+ "price REAL NOT NULL;\n"
	    + "laboratory TEXT NOT NULL;\n"
	+ "type TEXT NOT NULL;\n"
	+ "pb_pv TEXT NOT NULL;\n"
	+ "patient_id INTEGER REFERENCES patient(id)\n"
	+ ")";
	stmt5.executeUpdate(sql5);
	stmt5.close();
	Statement stmt6 = c.createStatement();
	String sql6 = "CREATE TABLE patient_symptoms(\n"
	+ "patient_id INTEGER REFERENCES patient(id)\n"
	+ "contact_id INTEGER REFERENCES patient(id)\n"
	+ ")";
	stmt6.executeUpdate(sql6);
	stmt6.close();
	System.out.println("Tables created.");
	} catch(SQLException e) {
		System.out.println("Error, database exception.");
	}catch (Exception e) {
		System.out.println("Error, couldn´t connect to data based.");
		e.printStackTrace();
	}

	}
	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		try {
			c.close();
			System.out.println("Database connection closed.");
		} catch (Exception e) {
			System.out.println("Error, database exception.");
			e.printStackTrace();
		}
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
