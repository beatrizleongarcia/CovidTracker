package CovidTracker.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import CovidTracker.db.ifaces.DBManager;
import CovidTracker.db.jdbc.JDBCManager;
import CovidTracker.db.jdbc.inputoutput;
import db.pojos.Patient;

public class Menu {
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static JDBCManager man = new JDBCManager();

	public static void menu() throws Exception {
        man.connect();
		while (true) {
			System.out.println("WELCOME! ");
			System.out.println("1.Enter user and pasword ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion;
			opcion = inputoutput.getOptionfromKeyboard();

			switch (opcion) {
			case 1:
				inputoutput.getUserfromKeyboard();
				inputoutput.getPasswordfromKeyboard();
				break;
			case 2:
				man.disconnect();
			}
		}
	}

	public static void main(String[] args) {
		man.connect();
		man.disconnect();
		//try {
			//menu();
			//Falta acceder a cada menu según el nombre de ususario
		//} catch (Exception e) {
			//e.printStackTrace();
		//}

	}

	public static void MenuAdm() throws Exception {
		while (true) {
			System.out.println("1.Look for a patient. ");
			System.out.println("2.View a patient. ");
			System.out.println("3.Introduce a new a patient. ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion;
			opcion = inputoutput.getOptionfromKeyboard();

			switch (opcion) {
			case 1:
				String name = inputoutput.getNamefromKeyboard();
				Patient patient = man.searchPatientByName(name);
				break;
			case 2:
				name = inputoutput.getNamefromKeyboard();
				patient = man.getPatient(name);
				break;
			case 3:
				man.addPerson(inputoutput.addPatient());

				break;
			case 0:
				System.exit(0);

			}

		}
	}

	public static void MenuCEO() throws Exception {
		while (true) {
			System.out.println("1.View a patient. ");
			System.out.println("2.Search a patient. ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion;
			opcion = inputoutput.getOptionfromKeyboard();
			switch (opcion) {
			case 1:
				String name = inputoutput.getNamefromKeyboard();
				Patient patient = man.getPatient(name);
				break;
			case 2:
				name = inputoutput.getNamefromKeyboard();
				patient = man.searchPatientByName(name);
				break;
			case 0:
				System.exit(0);

			}
		}

	}

	public void MenuHHRR() throws Exception {
		while (true) {
			System.out.println("1.Look replacement. ");
			System.out.println("2.Modify patient. ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion;
			opcion = inputoutput.getOptionfromKeyboard();
			switch (opcion) {
			case 1:
				String name = inputoutput.getNamefromKeyboard();
				Patient patient = man.searchPatientByName(name);
				man.LookReplacement(patient);
				break;
			case 2:
				name = inputoutput.getNamefromKeyboard();
				patient = man.searchPatientByName(name);
				man.ModifyPatient(patient);
				break;
			case 0:
				System.exit(0);

			}
		}

	}

	public void MenuDoc() throws Exception {
		while (true) {
			System.out.println("1.Introduce new patient. ");
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion;
			opcion = inputoutput.getOptionfromKeyboard();
			switch (opcion) {
			case 1:
				man.addPerson(inputoutput.addPatient());
				break;
			case 0:
				System.exit(0);

			}

		}

	}
}
