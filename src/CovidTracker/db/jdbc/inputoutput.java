package CovidTracker.db.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import db.pojos.Covid_Test;
import db.pojos.Doctor;
import db.pojos.Patient;
import db.pojos.Quarantine;
import db.pojos.Symptoms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import CovidTracker.db.ifaces.DBManager;
import CovidTracker.db.jdbc.JDBCManager;
import db.pojos.Patient;
import jdk.jshell.execution.Util;

public class inputoutput {

	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	Patient patient;

	public static String getUserfromKeyboard() {
		try {
			String user;
			System.out.println("User:");
			user = in.readLine();

			return user;

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return null;

	}

	public static String getPasswordfromKeyboard() {
		try {
			String password;
			System.out.println("Password:");
			password = in.readLine();

			return password;

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return null;

	}

	public static int getOptionfromKeyboard() {

		try {
			int a;

			a = Integer.parseInt(in.readLine());

			return a;

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return 0;
	}

	public static String getNamefromKeyboard() {

		try {
			String name;
			System.out.println("Introduce the name of the patient:");
			name = in.readLine();

			return name;

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return null;

	}

	public static Patient addPatient() {
		List<Covid_Test> tests = new ArrayList<Covid_Test>();
		List<Symptoms> symptoms = new ArrayList<Symptoms>();
		List<Quarantine> quarantines = new ArrayList<Quarantine>();

		System.out.println("Introduce a new patient");
		System.out.println("Name:");
		String name = in.readLine();
		System.out.println("Date of birth (yyyy-MM-dd)");
		String dob = in.readLine();
		LocalDate date = create_date(dob);
		System.out.println("Job title");
		String job_tittle = in.readLine();
		System.out.println("Salary");
		Float salary = Float.parseFloat(in.readLine());
		System.out.println("Doctor that has done the test");
		Integer doctor_id = Integer.parseInt(in.readLine());
		Doctor doc = searchDoctorbyId();
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

		// para el doctor: hay que sacar la lista de doctores, y que se elija el suyo y
		// se añada solo !!!!!!!!!!!!!!!1
		// sintomas : crear metodo para pedir los sintomas dando opciones
		// !!!!!!!!!!!!!!!111111111111111111111111!!!!!!!!!!!!!!!
		// quarentena : lo mismo que sintomas
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		return new Patient(name, Date.valueOf(date), job_tittle, salary, doc, tests, symptoms, quarantines);

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
			 qua = new Quarantine(reason ,time);
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
			tests = new Covid_Test(public_private, type_test, Date.valueOf(date),price);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tests;
	}

	public static Symptoms addSymptoms() {
		Symptoms symp = null;
		try {
			System.out.println("What is the symptom?");
			String type = in.readLine();
			symp = new Symptoms(type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return symp;
	}

	// verse la clase en la que explica como meter la fecha
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static LocalDate create_date(String dob) {
		LocalDate dobDate = LocalDate.parse(dob, formatter);
		return dobDate;
	}

	// BORRAR ESTOS DOS METODOS DE ABAJO QUE HA HECHO BASILIO O QUE NOS ESPLIQUE
	// PARA QUE SON !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
		
		Float f = null ;
		try {
			f = Float.parseFloat(in.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}

}
