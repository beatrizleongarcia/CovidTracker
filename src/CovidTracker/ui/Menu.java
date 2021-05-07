package CovidTracker.ui;


import java.security.MessageDigest;
import CovidTracker.db.jdbc.JDBCManager;
import CovidTracker.db.jpa.JPAUserManager;
import CovidTracker.db.pojos.Patient;
import CovidTracker.db.pojos.users.Role;
import CovidTracker.db.pojos.users.User;


public class Menu {

	private static JDBCManager dbman = new JDBCManager();
	private static JPAUserManager paman = new JPAUserManager();

	public static void main(String[] args) throws Exception {
		dbman.connect();
        paman.connect();
		while (true) {
			System.out.println("WELCOME! ");
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
			System.out.println("2.View a patient. ");
			System.out.println("3.Introduce a new a patient. ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = inputoutput.get_int();

			switch (opcion) {
			case 1:
				String name = inputoutput.getNamefromKeyboard();
				Patient patient = dbman.searchPatientByName(name);
				break;
			case 2:
				name = inputoutput.getNamefromKeyboard();
				patient = dbman.searchPatientByName(name);
				break;
			case 3:
				dbman.addPerson(inputoutput.addPatient());

				break;
			case 0:
				System.exit(0);

			}

		}
	}

	private static void MenuCEO() throws Exception {
		while (true) {
			System.out.println("1.View a patient. ");
			System.out.println("2.Search a patient. ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = inputoutput.get_int();
			switch (opcion) {
			case 1:
				String name = inputoutput.getNamefromKeyboard();
				Patient patient = dbman.searchPatientByName(name);
				break;
			case 2:
				name = inputoutput.getNamefromKeyboard();
				patient = dbman.searchPatientByName(name);
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
			
				break;
			case 2:
				modify();
				break;
			case 0:
				System.exit(0);

			}
		}

	}
	private static User modify() throws Exception {

		// List of roles
		System.out.println(paman.getRoles());
		// Ask the user for a role
		System.out.println("Please enter the role ID of the user:");
		int id = inputoutput.get_int();
		Role role = paman.getRole(id);
		// List of user of the choosen role
		System.out.println(role.getUsers());
		// Ask the user for the ID of the user
		System.out.println("Please enter the ID of the user:");
	    id = inputoutput.get_int();
	    User u = new User(id,role);
		return u;
	}
}
