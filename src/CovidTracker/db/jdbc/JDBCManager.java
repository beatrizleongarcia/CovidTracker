package CovidTracker.db.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import CovidTracker.db.ifaces.DBManager;
import CovidTracker.db.pojos.Covid_Test;
import CovidTracker.db.pojos.Doctor;
import CovidTracker.db.pojos.Patient;
import CovidTracker.db.pojos.Quarantine;
import CovidTracker.db.pojos.Symptoms;
import CovidTracker.ui.InputOutput;

public class JDBCManager implements DBManager {

	private Connection c;

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
					+ "days_off_work INTEGER," + "economic_impact REAL," + "doctor_id INTEGER REFERENCES doctor(id))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE covid_test" + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "date_of_test DATE NOT NULL,"
					+ "price REAL NOT NULL," + "laboratory TEXT NOT NULL," + "type TEXT NOT NULL,"
					+ "pb_pv TEXT NOT NULL," + "patient_id INTEGER REFERENCES patient(id),"
					+ "doctor_id INTEGER REFERENCES doctor(id))";
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

	@Override
	public void dropTables() {

		Statement stmt;
		try {
			stmt = c.createStatement();
			String sql5 = "DROP TABLE patient_symptoms ";
			stmt.executeUpdate(sql5);
			String sql6 = "DROP TABLE patient_quarantine ";
			stmt.executeUpdate(sql6);
			String sql4 = "DROP TABLE covid_test ";
			stmt.executeUpdate(sql4);
			String sql3 = "DROP TABLE patient";
			stmt.executeUpdate(sql3);
			String sql2 = "DROP TABLE quarantine";
			stmt.executeUpdate(sql2);
			String sql1 = "DROP TABLE symptoms";
			stmt.executeUpdate(sql1);
			String sql = "DROP TABLE doctor";
			stmt.executeUpdate(sql);

			stmt.close();
			System.out.println("Tables deleted");
		} catch (SQLException e) {
			e.printStackTrace();
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
				pat = new Patient(id, patient_name, dob, job_title, salary, days_off_work, economic_impact);
			}
			rs.close();
			prep.close();
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
			System.out.println("3.Job title ");
			System.out.println("4.Date of birth");

			Integer feature = InputOutput.get_int();
			String sql;
			PreparedStatement prep;

			switch (feature) {
			case 1:
				sql = "UPDATE patient SET name =? WHERE id=?";
				prep = c.prepareStatement(sql);
				System.out.println("Introduce the new name:");
				String name = InputOutput.get_String();
				prep.setString(1, name);
				prep.setInt(2, p.getId());
				prep.executeUpdate();
				break;
			case 2:
				sql = "UPDATE patient SET salary =? WHERE id=?";
				prep = c.prepareStatement(sql);
				System.out.println("Introduce the new salary:");
				Float salary = InputOutput.get_Float();
				prep.setFloat(1, salary);
				prep.setInt(2, p.getId());
				prep.executeUpdate();
				break;
			case 3:
				sql = "UPDATE patient SET job_title =? WHERE id=?";
				prep = c.prepareStatement(sql);
				System.out.println("Introduce the new job title:");
				String job = InputOutput.get_String();
				prep.setString(1, job);
				prep.setInt(2, p.getId());
				prep.executeUpdate();
				break;
			case 4:
				sql = "UPDATE patient SET dob =? WHERE id=?";
				prep = c.prepareStatement(sql);
				System.out.println("Introduce the new date of birth (yyyy-MM-dd):");
				String dob = InputOutput.get_String();
				Date d = Date.valueOf(InputOutput.createDate());
				prep.setDate(1, d);
				prep.setInt(2, p.getId());
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


	public void change_daysoffwork(Patient pat) {

		try {
			String sql = "UPDATE patient SET days_off_work =? WHERE id=?";
			PreparedStatement prep;
			prep = c.prepareStatement(sql);
			prep.setInt(1, pat.getDays_off_work());
			prep.setInt(2, pat.getId());
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void change_economicimpact(Patient pat) {

		try {
			String sql = "UPDATE patient SET economic_impact =? WHERE id=?";
			PreparedStatement prep;
			prep = c.prepareStatement(sql);
			prep.setFloat(1, pat.getEconomic_impact());
			prep.setInt(2, pat.getId());
			prep.executeUpdate();
		} catch (SQLException e) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	@Override
	public Doctor searchDoctorbyId(int id) {
		Doctor doc = null;
		String sql = "SELECT * FROM doctor WHERE id = ?";
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	@Override
	public Integer searchDoctorId(String name) {
		String sql = "SELECT doctor_id FROM patient WHERE name LIKE ?";
		try {
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, name);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id_doctor = rs.getInt("doctor_id");
				return id_doctor;
			}
			rs.close();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Integer> searchSymptomsId(Integer id) {
		List<Integer> id_symp = new ArrayList<Integer>();
		String sql = "SELECT symptoms_id FROM patient_symptoms WHERE patient_id = ?";
		try {
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id_symptoms = rs.getInt("symptoms_id");
				id_symp.add(id_symptoms);
			}

			rs.close();
			prep.close();
			return id_symp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String searchSymptomstype(Integer id) {
		String sql = "SELECT type FROM symptoms WHERE id = ?";
		try {
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				String type = rs.getString("type");
				return type;
			}
			rs.close();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Integer> searchQuarantineId(Integer id) {
		List<Integer> id_qua = new ArrayList<Integer>();
		String sql = "SELECT quarantine_id FROM patient_quarantine WHERE patient_id = ?";
		try {
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id_quarantine = rs.getInt("quarantine_id");
				id_qua.add(id_quarantine);
			}

			rs.close();
			prep.close();
			return id_qua;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String searchQuarantinereason(Integer id) {
		String sql = "SELECT reason FROM quarantine WHERE id = ?";
		try {
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				String reason = rs.getString("reason");
				return reason;
			}
			rs.close();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	public void quarantine_patient(Patient p, Quarantine s) {
		try {
			String sql = "INSERT INTO patient_quarantine(patient_id, quarantine_id) VALUES (?,?)";
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

		try {
			String sql = "INSERT INTO patient (name, dob, job_title, salary,days_off_work,economic_impact, doctor_id) "
					+ "VALUES (?,?,?,?,?,?,?)";
			prep = c.prepareStatement(sql);
			prep.setString(1, p.getName());
			prep.setDate(2, p.getDob());
			prep.setString(3, p.getJob_title());
			prep.setFloat(4, p.getSalary());
			prep.setInt(5, p.getDays_off_work());
			prep.setFloat(6, p.getEconomic_impact());
			prep.setInt(7, doc.getId());
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
			System.out.println("Records inserted. \n \n");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void addCovid_Test(Covid_Test t) {
		PreparedStatement prep;
		Doctor doc = t.getDoctor();
		Patient pat = t.getPatient();
		try {

			String sql = "INSERT INTO covid_test (date_of_test, price, laboratory, type, pb_pv, patient_id, doctor_id) "
					+ "VALUES (?,?,?,?,?,?,?)";
			prep = c.prepareStatement(sql);
			prep.setDate(1, t.getDate_of_test());
			prep.setFloat(2, t.getPrice());
			prep.setString(3, t.getLaboratory());
			prep.setString(4, t.getType_test());
			prep.setString(5, t.getPublic_private());
			prep.setInt(6, pat.getId());
			prep.setInt(7, doc.getId());
			prep.executeUpdate();
			prep.close();
			System.out.println("Covid test info processed");
			System.out.println("Records inserted. \n \n");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void addDoctor(Doctor d) {

		Statement stmt;
		try {
			stmt = c.createStatement();
			String sql = "INSERT INTO doctor (name , hospital) " + "VALUES ( '" + d.getName() + "', '" + d.getHospital()
					+ "')";
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("Doctor info processed");
			System.out.println("Records inserted.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Patient test_patient(Patient pat) {
		Covid_Test test = InputOutput.addCovid_Test();
		if(test!= null) {
		pat.addNewTest(test);
		}
		test.setPatient(pat);
		test.setDoctor(pat.getDoctor());
		addCovid_Test(test);
		return pat;
	}

	@Override
	public Date last_test(Patient patnotest) {
		Patient pattest = test_patient(patnotest);
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
			delete_covidtest(name);
			delete_quarantine(name);
			delete_symptoms(name);
			String sql = "DELETE FROM patient WHERE name = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, name);
			prep.executeUpdate();
			prep.close();
			System.out.println("The patient " + name + " has been deleted successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete_covidtest(String name) {
		try {
			Patient pat = searchPatientByName(name);
			Integer id = pat.getId();
			String sql = "DELETE FROM covid_test WHERE patient_id = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete_quarantine(String name) {
		try {
			Patient pat = searchPatientByName(name);
			Integer id = pat.getId();
			String sql = "DELETE FROM patient_quarantine WHERE patient_id = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void delete_symptoms(String name) {
		try {
			Patient pat = searchPatientByName(name);
			Integer id = pat.getId();
			String sql = "DELETE FROM patient_symptoms WHERE patient_id = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
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

	@Override
	public void viewPatientsName() {

		String sql = "SELECT name FROM patient";
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

	@Override
	public void viewPatient(int id) {

		String sql = "SELECT name FROM patient WHERE doctor_id = ?";
		try {
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
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

	@Override
	public List<Doctor> viewAllDoctors() {

		List<Doctor> doc = new ArrayList<Doctor>();
		String sql = "SELECT * FROM doctor";
		try {
			PreparedStatement prep = c.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {

				String name = rs.getString("name");
				Integer id = rs.getInt("id");
				String hospital = rs.getString("Hospital");
				Doctor d = new Doctor(name, id, hospital);
				doc.add(d);

			}
			rs.close();
			prep.close();
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Patient> viewAllPatients() {

		List<Patient> pat = new ArrayList<Patient>();
		String sql = "SELECT * FROM patient";
		try {
			PreparedStatement prep = c.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {

				int id = rs.getInt("id");
				String patient_name = rs.getString("name");
				Date dob = rs.getDate("dob");
				String job_title = rs.getString("job_title");
				float salary = rs.getFloat("salary");
				float economic_impact = rs.getFloat("economic_impact");
				int days_off_work = rs.getInt("days_off_work");
				Patient p = new Patient(id, patient_name, dob, job_title, salary, days_off_work, economic_impact);
				pat.add(p);
			}
			rs.close();
			prep.close();
			return pat;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
