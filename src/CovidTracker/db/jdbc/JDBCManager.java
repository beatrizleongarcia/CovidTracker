package CovidTracker.db.jdbc;

import java.awt.font.FontRenderContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import CovidTracker.db.ifaces.DBManager;
import db.pojos.Doctor;
import db.pojos.Patient;
import db.pojos.Symptoms;
import jobseeker.db.pojos.Job;
import jobseeker.db.pojos.Person;

public class JDBCManager implements DBManager {

	private Connection c;
	public Patient p;

	@Override
	public void connect() {

		try {
			// Open database connection
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/covidTracker.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");

			this.create();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error, database exception.");
		} catch (Exception e) {
			System.out.println("Error, couldn't connect to data based.");
			e.printStackTrace();
		}
	}

	private void create() {
		Statement stmt;
		try {
			// Create tables: begin
			stmt = c.createStatement();
			String sql = "CREATE TABLE doctor" + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL,"
					+ "hospital TEXT NOT NULL)";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE symptoms" + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "type TEXT NOT NULL)";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE quarantine" + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "time INTEGER NOT NULL,"
					+ "reason TEXT NOT NULL)";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE patient" + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL,"
					+ "dob DATE NOT NULL," + "job_title TEXT NOT NULL," + "salary INTEGER NOT NULL,"
					+ "days_off_work INTEGER NOT NULL," + "economic_impact INTEGER NOT NULL,"
					+ "doctor_id INTEGER REFERENCES doctor(id)," + "symptoms_id INTEGER REFERENCES symptoms(id),"
					+ "quarantine_id INTEGER REFERENCES quarantine(id))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE covid_test" + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "date_of_test DATE NOT NULL,"
					+ "price REAL NOT NULL," + "laboratory TEXT NOT NULL," + "type TEXT NOT NULL,"
					+ "pb_pv TEXT NOT NULL," + "patient_id INTEGER REFERENCES patient(id))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE patient_symptoms" + "(patient_id INTEGER REFERENCES patient(id),"
					+ "symptoms_id INTEGER REFERENCES symptoms(id)," + "PRIMARY KEY (patient_id , symptoms_id))";
			stmt.executeUpdate(sql);

			this.symptoms_table();
			this.quarantine_table();

			stmt.close();
			System.out.println("Tables created.");
		} catch (SQLException e) {
			if (!e.getMessage().contains("already exists")) {
				e.printStackTrace();
			}
		}

	}

	private void symptoms_table() {
		Statement stmt;
		try {
			stmt = c.createStatement();
			String sql = "INSERT INTO symptoms (type) "
					+ "VALUES ('fever'), ('dry cough'), ('tireness'), ('ache and pains'), ('diarrhoea'), ('loss of taste and smell')";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void quarantine_table() {
		Statement stmt;
		try {
			stmt = c.createStatement();
			String sql = "INSERT INTO quarantine (reason,time) "
					+ "VALUES ('contact','10'), ('symptoms','10'), ('confirmed','10'), ('routine test','10')";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void disconnect() {
		try {
			// Close database connection
			c.close();
			System.out.println("Database connection close");
		} catch (SQLException e) {
			System.out.println("There was a problem while closing the database connection.");
			e.printStackTrace();
		}
	}

	@Override
	public boolean LookReplacement(Patient p) {
		int days = p.getDays_off_work();
		if (days > 10) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Patient searchPatientByName(String name) {
		Patient pat = null;
		String sql = "SELECT * FROM employees WHERE name LIKE ?";
		try {
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, name);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String patient_name = rs.getString("name");
				Date dob = rs.getDate("dob");
				String job_title = rs.getString("job_title");
				float salary = rs.getFloat("salary");
				float economic_impact = rs.getFloat("economic_impact");
				int days_off_work = rs.getInt("days_off_work");
				int doctor_id = rs.getInt("doctor_id");
				Doctor doctor = searchDoctorbyId(doctor_id);
				pat = new Patient(id, patient_name, dob, job_title, salary, days_off_work,
						economic_impact, doctor);
			}
			rs.close();
			prep.close();
			System.out.println("Search finished");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pat;
	}

	@Override
	public void ModifyPatient(Patient p) {
		try {
			System.out.println("Insert the name of the patient");
			p = searchPatientByName(inputoutput.get_String());
			System.out.println(
					"What aspect of your patient do you want to change? \n (name/salary/job title/date of birth/days off work)");
			String modification = inputoutput.get_String();

			if (modification != null) {
				if (modification == "name") {
					System.out.println("Insert new name");
					String new_name = inputoutput.get_String();
					p.setName(new_name);
				} else if (modification == "salary") {
					System.out.println("Insert new salary");
					float new_salary = inputoutput.get_Float();
					p.setSalary(new_salary);
				} else if (modification == "job title") {
					System.out.println("Insert new job title");
					String new_job_title = inputoutput.get_String();
					p.setJob_title(new_job_title);
				} else if (modification == "date of birth") {
					System.out.println("Insert date of birth: (yyyy-MM-dd)");
					String dob = inputoutput.get_String();
					p.setDob(Date.valueOf(inputoutput.create_date(dob)));
				} 

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Doctor searchDoctorbyName(String name) {
		Doctor doc = null;
		String sql = "SELECT * FROM doctor WHERE name LIKE ?";
		try {
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, name);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String doctor_name = rs.getString("name");
				String hospital = rs.getString("hospital");
				doc = new Doctor(id, doctor_name, hospital);
			}
			rs.close();
			prep.close();
			System.out.println("Search finished");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	@Override
	public Doctor searchDoctorbyId(int id) {
		Doctor doc = null;
		String sql = "SELECT * FROM doctor WHERE name LIKE ?";
		try {
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id_doctor = rs.getInt("id");
				String doctor_name = rs.getString("name");
				String hospital = rs.getString("hospital");
				doc = new Doctor(id_doctor, doctor_name, hospital);
			}
			rs.close();
			prep.close();
			System.out.println("Search finished");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}


	@Override
	public void symptoms_patient(Patient p, Symptoms s) {
		try {
			String sql = "INSERT INTO patient_symptoms(patient_id, symptoms_id) VALUES (?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, p.getId());
			prep.setInt(2, s.getId());
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	@Override
	public void addPerson(Patient p) {
		Statement stmt;
		try {
			stmt = c.createStatement();
			String sql = "INSERT INTO patient (id,name, dob, job_tittle, salary, day_off_work, economic_impact, doctor_id,symptoms_id,quarentine_id) "
					+ "VALUES ('" + p.getId() + "', '" + p.getName() + "', '" + p.getDob() + "', '" + p.getJob_title()
					+ "', '" + p.getSalary() + "', '" + p.getDays_off_work() + "', '" + p.getEconomic_impact() + ")";
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("Patient info processed");
			System.out.println("Records inserted.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete_patient(String name) {
		try {
			String sql = "DELETE FROM patient WHERE name = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, name);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Patient newPerson() {
		// TODO Auto-generated method stub
		return null;
	}

}
