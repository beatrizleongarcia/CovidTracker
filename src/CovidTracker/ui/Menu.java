package CovidTracker.ui;

import java.security.MessageDigest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import CovidTracker.db.jaxb.DTDChecker;
import CovidTracker.db.jaxb.Jaxb;
import CovidTracker.db.jdbc.JDBCManager;
import CovidTracker.db.jpa.JPAUserManager;
import CovidTracker.db.pojos.Doctor;
import CovidTracker.db.pojos.Patient;
import CovidTracker.db.pojos.Quarantine;
import CovidTracker.db.pojos.Symptoms;
import CovidTracker.db.pojos.users.Role;
import CovidTracker.db.pojos.users.User;

public class Menu {

	private static JDBCManager dbman = new JDBCManager();
	private static JPAUserManager paman = new JPAUserManager();
	private static Jaxb jaxb = new Jaxb();
	private static DTDChecker dc = new DTDChecker();

	public static void main(String[] args) throws Exception {
		menuPrinicpal();
	}

	public static void menuPrinicpal() throws Exception {
		dbman.connect();
		paman.connect();

		while (true) {
			System.out.println("\nWELCOME! ");
			System.out.println("\nChoose an option : ");
			System.out.println("1.Register ");
			System.out.println("2.Log in");
			System.out.println("0.EXIT. ");

			int opcion = InputOutput.get_int();

			switch (opcion) {
			case 1:
				register();
				break;
			case 2:
				login();
			case 0:
				dbman.disconnect();
				paman.disconnect();
				System.exit(0);
				break;
			default:
				break;
			}
		}
	}

	private static void register() throws Exception {

		System.out.println("Enter your email address:");
		String email = InputOutput.get_String();

		System.out.println("Enter your password:");
		String password = InputOutput.get_String();
		// List the roles
		for (int x = 0; x < paman.getRoles().size(); x++) {
			System.out.println(paman.getRoles().get(x));
		}
		// Ask the user for a role
		System.out.println("\nPlease enter yout role ID:");
		int id = InputOutput.get_int();
		Role role = paman.getRole(id);
		// Generate the hash
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		User user = new User(email, hash, role);
		paman.newUser(user);
	}

	private static void login() throws Exception {
		// Ask the user for an email
		System.out.println("Enter your email address: ");
		String email = InputOutput.get_String();
		// Ask the user for a password
		System.out.println("Enter your password:");
		String password = InputOutput.get_String();
		User user = paman.checkPassword(email, password);
		if (user == null) {
			System.out.println("Wrong email or password");
			return;
		} else if (user.getRole().getName().equalsIgnoreCase("administrator")) {
			MenuAdm();
		} else if (user.getRole().getName().equalsIgnoreCase("CEO")) {
			MenuCEO();
		} else if (user.getRole().getName().equalsIgnoreCase("HHRR")) {
			MenuHHRR();
		} else if (user.getRole().getName().equalsIgnoreCase("doctor")) {
			MenuDoc();
		} else if (user.getRole().getName().equalsIgnoreCase("informatic")) {
			MenuInformatic();
		}

		// Check the type of the user and redirect her to the proper menu
	}

	private static void MenuAdm() throws Exception {
		while (true) {
			System.out.println("\n1.View a patient. ");
			System.out.println("2.Add a patient from a XML file");
			System.out.println("3.Introduce a new a patient. ");
			System.out.println("4.Save a patient in a XML file ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = InputOutput.get_int();

			switch (opcion) {
			case 1:
				view();
				break;
			case 2:
				viewXML();
				break;
			case 3:
				newpat();
				break;
			case 4:
				newpatXML();
				break;
			case 0:
				Menu.menuPrinicpal();

			}

		}
	}

	private static void view() throws Exception {
		System.out.println("Patient's list:");
		dbman.viewPatientsName();
		String name = InputOutput.getNamefromKeyboard();
		Patient patient = dbman.searchPatientByName(name);
		if (patient == null) {
			System.out.println("That person is not in the data base");
		}else {
		       Integer doctor_id = dbman.searchDoctorId(name);
				Doctor doc = dbman.searchDoctorbyId(doctor_id);
				List<Integer> symptoms_id = dbman.searchSymptomsId(patient.getId());
				List<Integer> quarantine_id = dbman.searchQuarantineId(patient.getId());
				System.out.println(patient);
				System.out.println("Doctor: " + doc.getName());
				System.out.println("\nSymptom's types:");
				for (int i = 0; i < symptoms_id.size(); i++) {
					System.out.println(dbman.searchSymptomstype(symptoms_id.get(i)));
				}
				System.out.println("\nQuarantine's reasons:");
				for (int j = 0; j < quarantine_id.size(); j++) {
					System.out.println(dbman.searchQuarantinereason(quarantine_id.get(j)));
				}
			}
	}

	private static void viewXML() throws Exception {
		System.out.println("Introduce the name of the file where the patient is stored: ");
		String filename = InputOutput.get_String();
		dc.Checker(filename);
		jaxb.xml2JavaPAT(filename);
	}

	private static void newpatXML() throws Exception {
		System.out.println("Introduce the name of the new file: ");
		String filename = InputOutput.get_String();
		jaxb.java2XmlPAT(filename);
	}

	private static void newpat() throws Exception {
		Patient pat = InputOutput.addPatient(); // Introduce the patient
        System.out.println("\nDoctors:");
		dbman.viewDoctors();
		Doctor doc =null;
		while(doc== null) {
		System.out.println("Write the name and surname of the doctor that has done the test");
		String doctor_name = InputOutput.get_String();
		doc = dbman.searchDoctorbyName(doctor_name);
		if(doc== null) {
			System.out.println("That doctor isn't in the data base");
		}
		}
		pat.addDoctor(doc);
		dbman.addPerson(pat);
		Date date = dbman.last_test(pat);
		LocalDate dateToday = LocalDate.now();
		pat.func_daysoff(Date.valueOf(dateToday), date);
		pat.func_economic();
		dbman.change_daysoffwork(pat);
		dbman.change_economicimpact(pat);

		List<Symptoms> symptoms = pat.getSymptoms();
		if (symptoms.size() != 0) {
			for (int x = 0; x < symptoms.size(); x++) {
				dbman.symptoms_patient(pat, symptoms.get(x));// add to the many to many table
			}
		}
		List<Quarantine> qua = pat.getQuarantine();
		for (int x = 0; x < qua.size(); x++) {
			dbman.quarantine_patient(pat, qua.get(x));// add to the many to many table

		}

	}

	private static void MenuCEO() throws Exception {
		while (true) {
			System.out.println("\n1.View a patient. ");
			System.out.println("2.Add a patient from a XML file");
			System.out.println("3.Delete a patient. ");
			System.out.println("4.Add a new doctor");
			System.out.println("5.Save a doctor in a XML file");
			System.out.println("6.View a doctor");
			System.out.println("7.Add a doctor from a XML file");
			System.out.println("8.Create an Html file for a doctor");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = InputOutput.get_int();
			switch (opcion) {
			case 1:
				view();
				break;
			case 2:
				viewXML();
				break;
			case 3:
				deletepat();
				break;
			case 4:
				adddoc();
				break;
			case 5:
				adddocXML();
				break;
			case 6:
				viewDoc();
				break;
			case 7:
				viewDocXML();
				break;
			case 8:
				createHtmldoc();
				break;
			case 0:
				Menu.menuPrinicpal();

			}
		}

	}

	private static void viewDoc() throws Exception {

		System.out.println("\nDoctors:");
		dbman.viewDoctors();
		System.out.println("\nEnter the doctor's name and surname: ");
		String name = InputOutput.get_String();
		Doctor doc = dbman.searchDoctorbyName(name);

		if (doc == null) {
			System.out.println("There are no doctors with that name");
		} else {
			System.out.println(doc);
			System.out.println("\nPatients:");
			dbman.viewPatient(doc.getId());
		}
	}

	private static void viewDocXML() throws Exception {
		System.out.println("Introduce the name of the file where the doctor is stored: ");
		String filename = InputOutput.get_String();
		dc.Checker(filename);
		jaxb.xml2JavaDOC(filename);
	}

	private static void deletepat() throws Exception {
		System.out.println("Patient's list:");
		dbman.viewPatientsName();
		String name = InputOutput.getNamefromKeyboard();
		Patient patient = dbman.searchPatientByName(name);
		if (patient == null) {
			System.out.println("That person is not in the data base");
		}else {
		dbman.delete_patient(name);
		}	
		
	}

	private static void adddoc() throws Exception {
		Doctor doc = InputOutput.addDoctor();
		dbman.addDoctor(doc);

	}

	private static void createHtmldoc() throws Exception {
		System.out.println("Introduce the name of the file where the doctor is stored: ");
		String filename = InputOutput.get_String();
		dc.Checker(filename);
		System.out.println("Introduce the name of the new Html file: ");
		String filename2 = InputOutput.get_String();
		jaxb.simpleTransform("./files/" + filename + ".xml", "./files/Doctor-Style.xslt",
				"./files/" + filename2 + ".html");
		;
	}

	private static void adddocXML() throws Exception {
		System.out.println("Introduce the name of the new file: ");
		String filename = InputOutput.get_String();
		jaxb.java2XmlDOC(filename);
	}

	private static void MenuHHRR() throws Exception {
		while (true) {
			System.out.println("\n1.Look replacement. ");
			System.out.println("2.Modify patient. ");
			System.out.println("3.View all the patient's information.");
			System.out.println("4.View all the doctor's information. ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = InputOutput.get_int();
			switch (opcion) {
			case 1:
				replacement();
				break;
			case 2:
				modifypat();
				break;
			case 3:
				allpatients();
				break;
			case 4:
				alldoctors();
				break;
			case 0:
				Menu.menuPrinicpal();

			}
		}

	}

	private static void replacement() throws Exception {
		System.out.println("Patient's list:");
		dbman.viewPatientsName();
		String name = InputOutput.getNamefromKeyboard();
		Patient patient = dbman.searchPatientByName(name);
		if(patient == null) {
			System.out.println("That person isn't in the data base");;
		}else {
		int days = patient.getDays_off_work();
		if (days > 20) {
			System.out.println("Look for a replacement");
		} else {
			System.out.println("It is not necesary to look for a replacemnet yet.");
		}
		}
	}

	private static void modifypat() throws Exception {
		System.out.println("Patient's list:");
		dbman.viewPatientsName();
		String name = InputOutput.getNamefromKeyboard();
		Patient patient = dbman.searchPatientByName(name);
		if(patient == null) {
			System.out.println("That person isn't in the data base");;
		}else {
		dbman.ModifyPatient(patient);
		}
	}

	private static void allpatients() throws Exception {
		System.out.println("\nAll patients's stored in the data base:\n ");
		List<Patient> patients = dbman.viewAllPatients();
		for (int x = 0; x < patients.size(); x++) {
			System.out.println(patients.get(x));
		}

	}

	private static void alldoctors() throws Exception {
		System.out.println("\nAll doctor's stored in the data base:\n ");
		List<Doctor> doctors = dbman.viewAllDoctors();
		for (int x = 0; x < doctors.size(); x++) {
			System.out.println(doctors.get(x));
		}

	}

	private static void MenuDoc() throws Exception {
		while (true) {
			System.out.println("\n1. Introduce new patient. ");
			System.out.println("2.Save a patient in a XML file ");
			System.out.println("3.Create an Html file for a patient");
			System.out.println("4. Add a patient's covid test");
			System.out.println("5. List patients");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = InputOutput.get_int();
			switch (opcion) {
			case 1:
				newpat();
				break;
			case 2:
				newpatXML();
				break;
			case 3:
				createHtmlpat();
				break;
			case 4:
				addcovid();
				break;
			case 5:
				listpat();
				break;
			case 0:
				Menu.menuPrinicpal();

			}

		}

	}

	private static void addcovid() throws Exception {
		System.out.println("Patient's list:");
		dbman.viewPatientsName();
		String name = InputOutput.getNamefromKeyboard();
		Patient patient = dbman.searchPatientByName(name);
		if (patient == null) {
			System.out.println("That person is not in the data base");
		}else {
		int id_doctor = dbman.searchDoctorId(patient.getName());
		Doctor doc = dbman.searchDoctorbyId(id_doctor);
		patient.setDoctor(doc);
		dbman.test_patient(patient);
		}
	}

	private static void createHtmlpat() throws Exception {
		System.out.println("Introduce the name of the file where the patient is stored: ");
		String filename = InputOutput.get_String();
		dc.Checker(filename);
		System.out.println("Introduce the name of the new Html file: ");
		String filename2 = InputOutput.get_String();
		jaxb.simpleTransform("./files/" + filename + ".xml", "./files/Patient-Style.xslt",
				"./files/" + filename2 + ".html");
		;
	}

	private static void listpat() throws Exception {
		System.out.println("\nDoctors:");
		dbman.viewDoctors();
		String name = InputOutput.getDocfromKeyboard();
		Doctor doc = dbman.searchDoctorbyName(name);
		if (doc == null) {
			System.out.println("There are no doctors with that name");
		} else {
		dbman.viewPatient(doc.getId());
		}
	}

	private static void MenuInformatic() throws Exception {
		while (true) {
			System.out.println("\n1.Delete a user from a role. ");
			System.out.println("2.Modify the role of a user. ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = InputOutput.get_int();
			switch (opcion) {
			case 1:
				delete();
				break;
			case 2:
				modify();
				break;
			case 0:
				Menu.menuPrinicpal();

			}
		}

	}

	private static void modify() throws Exception {

		// List of roles
		for (int x = 0; x < paman.getRoles().size(); x++) {
			System.out.println(paman.getRoles().get(x));
		}
		// Ask the user for a role
		System.out.println("\nPlease enter the role ID of the user:");
		int id = InputOutput.get_int();
		Role role = paman.getRole(id);
		// List of user of the chosen role
		for (int x = 0; x < role.getUsers().size(); x++) {
			System.out.println(role.getUsers().get(x));
		}
		// Ask the user for the ID of the user
		System.out.println("\nPlease enter the ID of the user:");
		int iduser = InputOutput.get_int();
		User u = new User(iduser, role);
		paman.changeRole(u);
		System.out.println("The user's role has been changed correctly");

	}

	private static void delete() throws Exception {

		// List of roles
		for (int x = 0; x < paman.getRoles().size(); x++) {
			System.out.println(paman.getRoles().get(x));
		}
		// Ask the user for a role
		System.out.println("\nPlease enter the role ID of the user you want to eliminate:");
		int id = InputOutput.get_int();
		Role role = paman.getRole(id);
		// List of user of the chosen role
		for (int x = 0; x < role.getUsers().size(); x++) {
			System.out.println(role.getUsers().get(x));
		}
		// Ask the user for the ID of the user
		System.out.println("\nPlease enter the ID of the user:");
		id = InputOutput.get_int();
		User u = new User(id, role);
		paman.deleteRole(u);
		System.out.println("The user has been removed correctly");
	}

}
