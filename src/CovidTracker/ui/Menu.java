package CovidTracker.ui;


import java.security.MessageDigest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import CovidTracker.db.jdbc.JDBCManager;
import CovidTracker.db.jpa.JPAUserManager;
import CovidTracker.db.pojos.Doctor;
import CovidTracker.db.pojos.Patient;
import CovidTracker.db.pojos.Symptoms;
import CovidTracker.db.pojos.users.Role;
import CovidTracker.db.pojos.users.User;


public class Menu {

	private static JDBCManager dbman = new JDBCManager();
	private static JPAUserManager paman = new JPAUserManager();

	public static void main(String[] args) throws Exception {
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
		}else if (user.getRole().getName().equalsIgnoreCase("HHRR")) {
			MenuHHRR();
		} else if (user.getRole().getName().equalsIgnoreCase("doctor")) {
			 MenuDoc();
		}else if (user.getRole().getName().equalsIgnoreCase("informatic")) {
			 MenuInformatic();
		}
		
		// Check the type of the user and redirect her to the proper menu
	}
	
	private static void MenuAdm() throws Exception {
		while (true) {
			System.out.println("1.Look for a patient. ");
			System.out.println("2.Introduce a new a patient. ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = inputoutput.get_int();

			switch (opcion) {
			case 1:
				view();
				break;
			case 2:
				newpat();
				break;
			case 0:
				System.exit(0);

			}

		}
	}
	
	private static void view() throws Exception {
		String name = inputoutput.getNamefromKeyboard();
		Patient patient = dbman.searchPatientByName(name);
		if(patient == null) {
			System.out.println("There are no patients");	
		}else 
		System.out.println(patient);
	}
	
	private static void newpat() throws Exception{
		Patient pat = inputoutput.addPatient(); //Introduce the patient
		dbman.viewDoctors();
		System.out.println("Write the name of the doctor that has done the test");
		String doctor_name = inputoutput.get_String();
		Doctor doc = dbman.searchDoctorbyName(doctor_name);
        pat.addDoctor(doc);
		Date date = dbman.last_test(pat);
		LocalDate dateToday = LocalDate.now();
		pat.func_daysoff(Date.valueOf(dateToday),date);
		pat.func_economic(); 
		dbman.addPerson(pat);//Patient table no symptoms
		List <Symptoms> symptoms = pat.getSymptoms();
       for(int x = 0; x <=symptoms.size(); x++) {
    	  dbman.symptoms_patient(pat, symptoms.get(x));//add to the many to many table
    	   
       }
	}
	


	private static void MenuCEO() throws Exception {
		while (true) {
			System.out.println("1.View a patient. ");
			System.out.println("2.Delete a patient. ");
			System.out.println("3.Add a new doctor");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = inputoutput.get_int();
			switch (opcion) {
			case 1:
				view();
				break;
			case 2:
				String name = inputoutput.getNamefromKeyboard();
				//patient = dbman.searchPatientByName(name);
				
				break;
			case 0:
				System.exit(0);

			}
		}

	}

	private static void MenuHHRR() throws Exception {
		while (true) {
			System.out.println("1.Look replacement. ");
			System.out.println("2.Modify patient. ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = inputoutput.get_int();
			switch (opcion) {
			case 1:
				String name = inputoutput.getNamefromKeyboard();
				Patient patient = dbman.searchPatientByName(name);
				dbman.LookReplacement(patient);
				break;
			case 2:
				name = inputoutput.getNamefromKeyboard();
				patient = dbman.searchPatientByName(name);
				dbman.ModifyPatient(patient);
				break;
			case 0:
				System.exit(0);

			}
		}

	}

	private static void MenuDoc() throws Exception {
		while (true) {
			System.out.println("1.Introduce new patient. ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = inputoutput.get_int();
			switch (opcion) {
			case 1:
				dbman.addPerson(inputoutput.addPatient());
				break;
			case 0:
				System.exit(0);

			}

		}

	}
	
	private static void MenuInformatic() throws Exception {
		while (true) {
			System.out.println("1.Delete a user from a role. ");
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
				System.exit(0);

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
	    User u = new User(id,role);
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
	    User u = new User(id,role);
		paman.deleteRole(u);
		System.out.println("The user has been removed correctly");
	}
	

	
}
