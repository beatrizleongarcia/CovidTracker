package CovidTracker.db.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import CovidTracker.db.ifaces.DBManager;
import CovidTracker.db.pojos.Covid_Test;
import CovidTracker.db.pojos.Doctor;
import CovidTracker.db.pojos.Patient;
import CovidTracker.db.pojos.Quarantine;
import CovidTracker.db.pojos.Symptoms;
import CovidTracker.ui.inputoutput;

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
					+ "dob DATE NOT NULL," + "job_title TEXT NOT NULL," + "salary REAL NOT NULL,"
					+ "days_off_work INTEGER NOT NULL," + "economic_impact REAL NOT NULL,"
					+ "doctor_id INTEGER REFERENCES doctor(id))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE covid_test" + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "date_of_test DATE NOT NULL,"
					+ "price REAL NOT NULL," + "laboratory TEXT NOT NULL," + "type TEXT NOT NULL,"
					+ "pb_pv TEXT NOT NULL," + "patient_id INTEGER REFERENCES patient(id))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE patient_symptoms" + "(patient_id INTEGER REFERENCES patient(id),"
					+ "symptoms_id INTEGER REFERENCES symptoms(id)," + "PRIMARY KEY (patient_id , symptoms_id))";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE patient_quarantine" + "(patient_id INTEGER REFERENCES patient(id),"
					+ "quarantine_id INTEGER REFERENCES quarantine(id)," + "PRIMARY KEY (patient_id , quarantine_id))";
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
		String sql = "SELECT * FROM patient WHERE name LIKE ?";
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
			System.out.println("What feature do you want to change: ");
			System.out.println("1. Name");
			System.out.println("2.Salary ");
			System.out.println("3.Job tittle ");
			System.out.println("4.Date of birth");

			Integer feature = inputoutput.get_int();
			String sql;
			PreparedStatement prep;

			switch (feature) {
			case 1:
				sql = "UPDATE patient SET name =?";
				prep = c.prepareStatement(sql);
				System.out.println("Introduce the new name:");
				String name = inputoutput.get_String();
				prep.setString(1, name);
				prep.executeUpdate();
				break;
			case 2:
				sql = "UPDATE patient SET salary =?";
				prep = c.prepareStatement(sql);
				System.out.println("Introduce the new salary:");
				Float salary = inputoutput.get_Float();
				prep.setFloat(1, salary);
				prep.executeUpdate();
				break;
			case 3:
				sql = "UPDATE patient SET job_tittle =?";
				prep = c.prepareStatement(sql);
				System.out.println("Introduce the new job tittle:");
				String job = inputoutput.get_String();
				prep.setString(1, job);
				prep.executeUpdate();
				break;
			case 4:
				sql = "UPDATE patient SET dob =?";
				prep = c.prepareStatement(sql);
				System.out.println("Introduce the new date of birth (yyyy-MM-dd):");
				String dob = inputoutput.get_String();
				Date d = Date.valueOf(inputoutput.create_date(dob));
				prep.setDate(1, d);
				prep.executeUpdate();
				break;
			default:
				System.out.println("That optiont does not exist");
				break;
			}

			System.out.println("Update finished.");

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
	public void quarantine_patient(Patient p, Symptoms s) {
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
		PreparedStatement prep;
		Doctor doc = p.getDoctor();
		int doctor_id = doc.getId();
	    
		try {

			String sql = "INSERT INTO patient (name, dob, job_tittle, salary, day_off_work, economic_impact, doctor_id) "
					+ "VALUES ('" + p.getName() + "', '" + p.getDob() + "', '" + p.getJob_title() + "', '"
					+ p.getSalary() + "', '" + p.getDays_off_work() + "', '" + p.getEconomic_impact() + "', '"
					+ doctor_id +"', '"+ ")";
			prep = c.prepareStatement(sql);
			prep.executeUpdate();
			String sql2 = "SELECT last_insert_rowid()";
			PreparedStatement prep2 = c.prepareStatement(sql2);
			ResultSet rs = prep2.executeQuery();
			rs.next();
			int id = rs.getInt(1);
			prep2.close();
			rs.close();
			p.setId(id);
			prep.close();
			System.out.println("Patient info processed");
			System.out.println("Records inserted.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void addCovid_Test(Covid_Test t) {
		PreparedStatement prep;
		Doctor doc = t.getDoctor();
		int doctor_id = doc.getId();
		Patient pat = t.getPatient();
		int patient_id = pat.getId();
		try {

			String sql = "INSERT INTO covid_test (id, public_private, type_test, laboratory, date_of_test, price, doctor_id, patient_id) "
					+ "VALUES ('" + t.getId() + "', '" + t.getPublic_private() + "', '" + t.getType_test() + "', '"
					+ t.getLaboratory() + "', '" + t.getDate_of_test() + "' , '" + t.getPrice() + "', '" + doctor_id
					+ "' , '" + patient_id + ")";
			prep = c.prepareStatement(sql);
			prep.executeUpdate();
			prep.close();
			System.out.println("Covid test info processed");
			System.out.println("Records inserted.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void addDoctor(Doctor d) {
		PreparedStatement prep;
		try {
			String sql = "INSERT INTO doctor (id, name , hospital) " + "VALUES ('" + d.getId() + "', '" + d.getName()
					+ "', '" + d.getHospital() + ")";
			prep = c.prepareStatement(sql);
			prep.executeUpdate();
			prep.close();
			System.out.println("Doctor info processed");
			System.out.println("Records inserted.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Patient test_patient(Patient pat) {
		Covid_Test test = inputoutput.addCovid_Test();
		pat.addNewTest(test);
		addCovid_Test(test);
		return pat;
	}

	@Override
	public Date last_test(Patient pattest, Patient patnotest) {
		pattest = test_patient(patnotest);
		int id = pattest.getId();
		String sql = "SELECT * FROM covid_test WHERE patient_id = '" + id + "' ORDER BY date_of_test DESC ";
		PreparedStatement prep;
		Date last_date = null;
		try {
			prep = c.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			rs.next();
			last_date = rs.getDate("date_of_test");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return last_date;

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
	public void viewDoctors() {

		String sql = "SELECT name FROM doctor";
		try {
			PreparedStatement prep = c.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {

				String name = rs.getString("name");
				System.out.println(name);
			}
			rs.close();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	

}
