package CovidTracker.ui;

import java.io.File;
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

			int opcion = inputoutput.get_int();

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
		String email = inputoutput.get_String();

		System.out.println("Enter your password:");
		String password = inputoutput.get_String();
		// List the roles
		System.out.println(paman.getRoles());
		// Ask the user for a role
		System.out.println("Please enter yout role ID:");
		int id = inputoutput.get_int();
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
		String email = inputoutput.get_String();
		// Ask the user for a password
		System.out.println("Enter your password:");
		String password = inputoutput.get_String();
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
			System.out.println("2.View a patient from a XML file");
			System.out.println("3.Introduce a new a patient. ");
			System.out.println("4.Save a patient in a XML file ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = inputoutput.get_int();

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
		String name = inputoutput.getNamefromKeyboard();
		Patient patient = dbman.searchPatientByName(name);
		if (patient == null) {
			System.out.println("There are no patients");
		} else
			System.out.println(patient);
	}

	private static void viewXML() throws Exception {
		System.out.println("Introduce the name of the file where the patient is stored: ");
		String filename = inputoutput.get_String();
		dc.Checker(filename);
		jaxb.xml2JavaPAT(filename);
	}

	private static void newpatXML() throws Exception {
		System.out.println("Introduce the name of the new file: ");
		String filename = inputoutput.get_String();
		jaxb.java2XmlPAT(filename);
	}

	private static void newpat() throws Exception {
		Patient pat = inputoutput.addPatient(); // Introduce the patient

		dbman.viewDoctors();
		System.out.println("Write the name of the doctor that has done the test");
		String doctor_name = inputoutput.get_String();
		Doctor doc = dbman.searchDoctorbyName(doctor_name);
		pat.addDoctor(doc);
		dbman.addPerson(pat);
		Date date = dbman.last_test(pat);
		LocalDate dateToday = LocalDate.now();
		pat.func_daysoff(Date.valueOf(dateToday), date);
		pat.func_economic();
		dbman.change_daysoffwork(pat);
		dbman.change_economicimpact(pat);

		List<Symptoms> symptoms = pat.getSymptoms();
		if(symptoms.size()!= 0 ) {
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
			System.out.println("2.View a patient from a XML file");
			System.out.println("3.Delete a patient. ");
			System.out.println("4.Add a new doctor");
			System.out.println("5.Save a doctor in a XML file");
			System.out.println("6.View a doctor");
			System.out.println("7.View a doctor from a XML file");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = inputoutput.get_int();
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
			case 0:
				Menu.menuPrinicpal();

			}
		}

	}

	private static void viewDoc() throws Exception {
		String name = inputoutput.getDocfromKeyboard();
		Doctor doc = dbman.searchDoctorbyName(name);
		if (doc == null) {
			System.out.println("There are no doctors with that name");
		} else
			System.out.println(doc);
	}

	private static void viewDocXML() throws Exception {
		System.out.println("Introduce the name of the file where the doctor is stored: ");
		String filename = inputoutput.get_String();
		dc.Checker(filename);
		jaxb.xml2JavaDOC(filename);
	}

	private static void deletepat() throws Exception {
		String name = inputoutput.getNamefromKeyboard();
		dbman.delete_patient(name);
	}

	private static void adddoc() throws Exception {
		Doctor doc = inputoutput.addDoctor();
		dbman.addDoctor(doc);
		
	}

	private static void adddocXML() throws Exception {
		System.out.println("Introduce the name of the new file: ");
		String filename = inputoutput.get_String();
		jaxb.java2XmlDOC(filename);
	}

	private static void MenuHHRR() throws Exception {
		while (true) {
			System.out.println("\n1.Look replacement. ");
			System.out.println("2.Modify patient. ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = inputoutput.get_int();
			switch (opcion) {
			case 1:
				replacement();
				break;
			case 2:
				modifypat();
				break;
			case 0:
				Menu.menuPrinicpal();

			}
		}

	}

	private static void replacement() throws Exception {
		String name = inputoutput.getNamefromKeyboard();
		Patient patient = dbman.searchPatientByName(name);
		int days = patient.getDays_off_work();
		if (days > 20) {
			System.out.println("Look for a replacement");
		} else {
			System.out.println("It is not necesary to look for a replacemnet yet.");
		}
	}

	private static void modifypat() throws Exception {
		String name = inputoutput.getNamefromKeyboard();
		Patient patient = dbman.searchPatientByName(name);
		dbman.ModifyPatient(patient);
	}

	private static void MenuDoc() throws Exception {
		while (true) {
			System.out.println("\n1. Introduce new patient. ");
			System.out.println("2. Save a patient in a XML file ");
			System.out.println("3. Add a patient's covid test");
			System.out.println("4. List patients");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = inputoutput.get_int();
			switch (opcion) {
			case 1:
				newpat();
				break;
			case 2:
				newpatXML();
				break;
			case 3:
				addcovid();
				break;
			case 4:
				listpat();
				break;
			case 0:
				Menu.menuPrinicpal();

			}

		}

	}

	private static void addcovid() throws Exception {
		String name = inputoutput.getNamefromKeyboard();
		Patient patient = dbman.searchPatientByName(name);
		dbman.test_patient(patient);
	}

	private static void listpat() throws Exception {

		String name = inputoutput.getDocfromKeyboard();
		Doctor doc = dbman.searchDoctorbyName(name);
		dbman.viewPatient(doc.getId());
	}

	private static void MenuInformatic() throws Exception {
		while (true) {
			System.out.println("\n1.Delete a user from a role. ");
			System.out.println("2.Modify the role of a user. ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = inputoutput.get_int();
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
		System.out.println(paman.getRoles());
		// Ask the user for a role
		System.out.println("Please enter the role ID of the user:");
		int id = inputoutput.get_int();
		Role role = paman.getRole(id);
		// List of user of the chosen role
		System.out.println(role.getUsers());
		// Ask the user for the ID of the user
		System.out.println("Please enter the ID of the user:");
		id = inputoutput.get_int();
		User u = new User(id, role);
		paman.changeRole(u);
		System.out.println("The user's role has been changed correctly");

	}

	private static void delete() throws Exception {

		// List of roles
		System.out.println(paman.getRoles());
		// Ask the user for a role
		System.out.println("Please enter the role ID of the user you want to eliminate:");
		int id = inputoutput.get_int();
		Role role = paman.getRole(id);
		// List of user of the chosen role
		System.out.println(role.getUsers());
		// Ask the user for the ID of the user
		System.out.println("Please enter the ID of the user:");
		id = inputoutput.get_int();
		User u = new User(id, role);
		paman.deleteRole(u);
		System.out.println("The user has been removed correctly");
	}

}
