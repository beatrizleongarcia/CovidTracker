package CovidTracker.db.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;


import db.pojos.Covid_Test;
import db.pojos.Doctor;
import db.pojos.Patient;
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
		List <Covid_Test> tests= new ArrayList <Covid_Test>();

		System.out.println("Introduce a new patient");
		//MIRAR LO DEL ID PARA QUE SE GENERE SOLO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		System.out.println("Name:");
		String name =in.readLine();
		System.out.println("Date of birth:");
		Date dob;//MIRAR CLASE EN LA QUE LO EXPLICA !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		System.out.println("Job title");
		String job_tittle = in.readLine();
		System.out.println("Salary");
		Float salary =Float.parseFloat(in.readLine());
		System.out.println("Days that the patient has been off work");
		Integer days_off =Integer.parseInt(in.readLine());
		System.out.println("Economic impact: "); //// esto hay que calcularlo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
		Float economic_impact =Float.parseFloat(in.readLine());
		System.out.println("Doctor that has done the test");
		Integer doctor_id = Integer.parseInt(in.readLine());
		Doctor doc = searchDoctorbyId();
		System.out.println("How many covid tests the patient has done?");
		Integer test_number = Integer.parseInt(in.readLine());
		for(int i=0; i<test_number; i++) {
			Covid_Test test= addCovid_Test();
			tests.add(test);
		}
		System.out.println("How many synmptoms the patient has done? (0 for no synmptoms)");
		Integer number_symptoms= Integer.parseInt(in.readLine());
		for(int j=0; j< number_symptoms)
		
		
		
		//para el doctor: hay que sacar la lista de doctores, y que se elija el suyo y se añada solo !!!!!!!!!!!!!!!1
		//sintomas : crear metodo para pedir los sintomas dando opciones !!!!!!!!!!!!!!!111111111111111111111111!!!!!!!!!!!!!!!
		//quarentena : lo mismo que sintomas !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		return 	new Patient(name, dob, job_tittle, salary, days_off_work,economic_impact, doc,tests, symptoms_id,quatentine_id);

	}

	public Covid_Test addCovid_Test() {
		try {
			System.out.println("Public or private test:");
			String public_private = in.readLine();
			System.out.println("Type of test:");
			String type_test = in.readLine();
			System.out.println("Date of the test:");
			Date date_of_test;
			System.out.println("Price of the test:");
			Float price = Float.parseFloat(in.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Symptoms addSynmptoms() {
		try {
			System.out.println("What is the symptom?");
			String type = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// verse la clase en la que explica como meter la fecha
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	public static LocalDate crear_fecha(String dob) {
		LocalDate dobDate = LocalDate.parse(dob);
		return dobDate;
	}

	// BORRAR ESTOS DOS METODOS DE ABAJO QUE HA HECHO BASILIO O QUE NOS ESPLIQUE
	// PARA QUE SON !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public static int get_int() {
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		return id;
	}

	public static String get_String() {
		Scanner sc = new Scanner(System.in);
		String a = sc.nextLine();
		return a;
	}

}
