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
			String name = in.readLine();
			System.out.println("Date of birth (yyyy-MM-dd)");
			String dob = in.readLine();
			LocalDate date = create_date(dob);
			System.out.println("Job title");
			String job_title = in.readLine();
			System.out.println("Salary");
			Float salary = Float.parseFloat(in.readLine());
			System.out.println("Doctor that has done the test");
			String doctor_name = in.readLine();
			Doctor doc = man.searchDoctorbyName(doctor_name);
			Patient pat = new Patient(name, Date.valueOf(date), job_title, salary, doc);
			man.addPerson(pat);

			// DELETE
			System.out.println("How many covid tests the patient has done?");
			Integer test_number = Integer.parseInt(in.readLine());
			for (int i = 0; i < test_number; i++) {
				Covid_Test test = addCovid_Test();
				tests.add(test);
			}
			System.out.println("How many symptoms the patient has done? (0 for no synmptoms)");
			Integer number_symptoms = Integer.parseInt(in.readLine());
			for (int j = 0; j < number_symptoms; j++) {
				Symptoms symp = addSymptoms();
				symptoms.add(symp);
			}
			System.out.println("How many times this patient has been on quarentine?");
			Integer number_quarantine = Integer.parseInt(in.readLine());
			for (int j = 0; j < number_quarantine; j++) {
				Quarantine qua = addQuarantine();
				quarantines.add(qua);
			}
			// return new Patient(name, Date.valueOf(date), job_title, salary, doc, tests,
			// symptoms, quarantines);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	private static Quarantine addQuarantine() {
		String reason;
		Integer time;
		Quarantine qua = null;
		try {
			System.out.println("Reason of quarantine:");
			reason = in.readLine();
			System.out.println("How many days of quarantine?");
			time = Integer.parseInt(in.readLine());
			qua = new Quarantine(reason, time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qua;
	}

	public static Covid_Test addCovid_Test() {
		Covid_Test tests = null;
		try {
			System.out.println("Public or private test:");
			String public_private = in.readLine();
			System.out.println("Type of test:");
			String type_test = in.readLine();
			System.out.println("Date of the test:");
			String date_of_test = in.readLine();
			LocalDate date = create_date(date_of_test);
			System.out.println("Price of the test:");
			Float price = Float.parseFloat(in.readLine());
			tests = new Covid_Test(public_private, type_test, Date.valueOf(date), price);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tests;
	}

	public static Symptoms addSymptoms() {
		try {
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
