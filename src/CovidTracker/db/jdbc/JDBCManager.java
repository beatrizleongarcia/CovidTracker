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

public class JDBCManager implements DBManager {
	
	public Connection c;
	public Patient p;

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		try {
			// Open database connection
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:./SQLDB/covidTracker.SDLDB");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");

			this.create();
		} catch (SQLException e) {
			System.out.println("Error, database exception.");
		} catch (Exception e) {
			System.out.println("Error, couldn�t connect to data based.");
			e.printStackTrace();
		}
	}

	@Override
	public void create() {
		try {
			// Create tables: begin
			Statement stmt1 = c.createStatement();
			String sql1 = "CREATE TABLE doctor(\n" + "Id INTEGER PRIMARY KEY;\n" + "name TEXT NOT NULL;\n"
					+ "hospital TEXT NOT NULL;\n" + ")";

			stmt1.executeUpdate(sql1);
			stmt1.close();
			Statement stmt2 = c.createStatement();
			String sql2 = "CREATE TABLE symptoms(\n" + "Id INTEGER PRIMARY KEY;\n" + "type TEXT NOT NULL;\n" + ")";
			stmt2.executeUpdate(sql2);
			stmt2.close();
			Statement stmt3 = c.createStatement();
			String sql3 = "CREATE TABLE quarantine(\n" + "id INTEGER PRIMARY KEY\n" + "time INTEGER NOT NULL\n"
					+ "reason TEXT NOT NULL\n" + ")";
			stmt3.executeUpdate(sql3);
			stmt3.close();
			Statement stmt4 = c.createStatement();
			String sql4 = "CREATE TABLE patient(\n" + "id INTEGER PRIMARY KEY;\n" + "name TEXT NOT NULL;\n"
					+ "dob DATE NOT NULL;\n" + "job_title TEXT NOT NULL;\n" + "salary INTEGER NOT NULL;\n"
					+ "days_off_work INTEGER NOT NULL;\n" + "economic_impact INTEGER NOT NULL;\n"
					+ "doctor_id INTEGER REFERENCES doctor(id)\n" + "symptoms_id INTEGER REFERENCES symptoms(id)\n"
					+ "quarantine_id INTEGER REFERENCES quarantine(id)\n" + ")";
			stmt4.executeUpdate(sql4);
			stmt4.close();
			Statement stmt5 = c.createStatement();
			String sql5 = "CREATE TABLE covid_test(\n" + "Id INTEGER PRIMARY KEY;\n" + "date_of_test DATE NOT NULL;\n"
					+ "price REAL NOT NULL;\n" + "laboratory TEXT NOT NULL;\n" + "type TEXT NOT NULL;\n"
					+ "pb_pv TEXT NOT NULL;\n" + "patient_id INTEGER REFERENCES patient(id)\n" + ")";
			stmt5.executeUpdate(sql5);
			stmt5.close();
			Statement stmt6 = c.createStatement();
			String sql6 = "CREATE TABLE patient_symptoms(\n" + "patient_id INTEGER REFERENCES patient(id)\n"
					+ "contact_id INTEGER REFERENCES patient(id)\n" + ")";
			stmt6.executeUpdate(sql6);
			stmt6.close();
			System.out.println("Tables created.");
		} catch (SQLException e) {
			System.out.println("Error, database exception.");
		} catch (Exception e) {
			System.out.println("Error, couldn�t connect to data based.");
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
	public boolean LookReplacement(Patient p) {
		int days = p.getDays_off_work();
		if (days > 10) {
			return true;
		} else {
			return false;
		}

	}

	private Date create_fecha() {

		return null;
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
				// LocalDate dob = rs.get("dob");
				String job_title = rs.getString("job_title");
				float salary = rs.getFloat("salary");
				float economic_impact = rs.getFloat("economic_impact");
				int days_off_work = rs.getInt("days_off_work");
				Doctor doctor = (Doctor) rs.getObject("doctor");
				// Falta hacer el get del arraylist q no se hacerlo
				// List <Synmptoms> symptoms= rs.getArray("synmptoms");
				pat = new Patient(id, patient_name, dob, job_title, salary, days_off_work, economic_impact, doctor);
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
					p.setDob(inputoutput.crear_fecha(dob));
				} else if (modification == "days off work") {
					// aqui deberiamos hacer lo de hacer days off work variable y que vaya
					// actualizandose automaticamente
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Patient getPatient(String name) {
		String name_aux = inputoutput.get_String();
		Patient patient = new Patient();

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Patient getPatient_id(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPerson(Patient p) {
					Statement stmt = c.createStatement();
					String sql = "INSERT INTO patient (id,name, dob, job_tittle, salary, day_off_work, economic_impact, doctor_id,symptoms_id,quarentine_id) "
							+ "VALUES ('" + p.getId() + "', '" + p.getName()+  "', '" + p.getDob() + "', '"+ p.getJob_title() + "', '"+ p.getSalary() + "', '"+ p.getDays_off_work() + "', '"+ p.getEconomic_impact() + "', '"+ p.getDoctor() + "', '"+ p.getSynmptoms() + "', '"p.getQuarantine() +")";
					stmt.executeUpdate(sql);
					stmt.close();
					System.out.println("Patient info processed");
					System.out.println("Records inserted.");			

	}

	@Override
	public Patient newPerson() {
		// TODO Auto-generated method stub
		return null;
	}

}
