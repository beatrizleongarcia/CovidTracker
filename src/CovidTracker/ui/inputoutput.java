package CovidTracker.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import CovidTracker.db.ifaces.DBManager;
import CovidTracker.db.jdbc.JDBCManager;
import CovidTracker.db.jpa.JPAUserManager;
import CovidTracker.db.pojos.Covid_Test;
import CovidTracker.db.pojos.Doctor;
import CovidTracker.db.pojos.Patient;
import CovidTracker.db.pojos.Quarantine;
import CovidTracker.db.pojos.Symptoms;
import CovidTracker.db.pojos.users.Role;
import CovidTracker.db.pojos.users.User;
import jdk.jshell.execution.Util;

public class inputoutput {

	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	Patient patient;
	private static JDBCManager man = new JDBCManager();

	public static Patient addPatient() {
		List<Covid_Test> tests = new ArrayList<Covid_Test>();
		List<Symptoms> symptoms = new ArrayList<Symptoms>();
		List<Quarantine> quarantines = new ArrayList<Quarantine>();
		try {
			System.out.println("Introduce a new patient");
			System.out.println("Name:");
			String name = get_String();
			System.out.println("Date of birth (yyyy-MM-dd)");
			String dob = get_String();
			LocalDate date = create_date(dob);
			System.out.println("Job title");
			String job_title = get_String();
			System.out.println("Salary");
			Float salary = get_Float();
			
			System.out.println("How many symptoms does the patient have? (0 for no synmptoms)");
			Integer number_symptoms = get_int();
			if(number_symptoms>0) {
			for (int j = 0; j <= number_symptoms; j++) {
				Symptoms symp = addSymptoms();
				symptoms.add(symp);
			}}
			System.out.println("How many quarantines the patient has been in?");
			Integer number_qua = get_int();
			for (int j = 0; j <= number_qua; j++) {
				Quarantine qua  = addQuarantine();
				quarantines.add(qua);
			}
			
		
			Patient pat = new Patient(name, Date.valueOf(date), job_title, salary, symptoms,quarantines);
			return pat;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	private static Quarantine addQuarantine() {
		try {
			while (true) {
				System.out.println("What is the reason of quarentine?");
				System.out.println("1.The patient have been in contact with someone infected");
				System.out.println("2.The patient have symptoms");
				System.out.println("3.Positive covid test ");
				System.out.println("4.Routine covid test that has been positive");
				System.out.println("\nChoose an option : ");

				int opcion = get_int();

				switch (opcion) {
				case 1:
					String type = "contact";
					return new Quarantine(type);
				case 2:
					type = "symptoms";
					return new Quarantine(type);
				case 3:
					type = "confirmed";
					return new Quarantine(type);
				case 4:
					type = "Routine test";
					return new Quarantine(type);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Covid_Test addCovid_Test() {
		Covid_Test tests = null;
		try {
			System.out.println("Complete the information about the covid test");
			System.out.println("Public or private test:");
			String public_private = get_String();
			System.out.println("Type of test(pcr,rapid test or antibody test");
			String type_test = get_String();
			System.out.println("Date of the test (yyyy-MM-dd):");
			String date_of_test = get_String();
			LocalDate date = create_date(date_of_test);
			System.out.println("Price of the test:");
			Float price = get_Float();
			tests = new Covid_Test(public_private, type_test, Date.valueOf(date), price);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tests;
	}

	public static Symptoms addSymptoms() {
		try {
			while (true) {
				System.out.println("What is the symptom?");
				System.out.println("1.Fever");
				System.out.println("2.Dry cough");
				System.out.println("3.Tireness ");
				System.out.println("4.Ache and pains");
				System.out.println("5.Diarrhoea");
				System.out.println("6.Loss taste and smell");
				System.out.println("\nChoose an option : ");

				int opcion = get_int();

				switch (opcion) {
				case 1:
					String type = "Fever";
					return new Symptoms(type);
				case 2:
					type = "Dry cough";
					return new Symptoms(type);
				case 3:
					type = "Tireness";
					return new Symptoms(type);
				case 4:
					type = "Ache and pains";
					return new Symptoms(type);
				case 5:
					type = "Diarrhoea";
					return new Symptoms(type);
				case 6:
					type = "Loss taste and smell";
					return new Symptoms(type);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static LocalDate create_date(String dob) {
		LocalDate dobDate = LocalDate.parse(dob, formatter);
		return dobDate;
	}

	public static int get_int() {

		int id = 0;
		try {
			id = Integer.parseInt(in.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static String get_String() {

		String a = null;
		try {
			a = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return a;
	}

	public static Float get_Float() {

		Float f = null;
		try {
			f = Float.parseFloat(in.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}

	public static String getNamefromKeyboard() {
		String a = null;
		try {
			System.out.println("Enter the patient's name:");
			a = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return a;
	}

}
