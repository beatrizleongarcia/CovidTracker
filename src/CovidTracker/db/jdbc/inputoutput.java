package CovidTracker.db.jdbc;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.sql.Date;
import java.util.Scanner;

import db.pojos.Patient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import CovidTracker.db.ifaces.DBManager;
import CovidTracker.db.jdbc.JDBCManager;
import db.pojos.Patient;
import jdk.jshell.execution.Util;

public class inputoutput {

	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public String User() {
		String user = "";
		try {
			System.out.println("Welcome:");
			System.out.println("Log in:");
			System.out.println("-User:");
			user = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public String Password() {
		String password = "";
		try {
			System.out.println("-Password:");
			password = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}

	public  void MenuAdm() {
		do {
		       System.out.println("1.Look for a patient. ");
		       System.out.println("2.View a patient. ");
		       System.out.println("3.introduce a new a patient. ");
		       System.out.println("0.EXIT. ");
		       System.out.println("\nChoose an option : ");
		       try {
		        int opcion = Integer.parseInt(in.readLine());
		        JDBCManager man = new JDBCManager();
		        switch(opcion) {
		        case 1:
		        String name= in.readLine(); 
		        Patient patient =man.searchPatientByName(name);
		        break;
		        case 2:
		        int id = Integer.parseInt(in.readLine()); 
		           patient = man.getPatient(id);
		        break;
		        case 3:
		        patient = man.addPerson();
		            
		        break;
		        case 0:
		        System.exit(0);
		       
		        }
		       }
		}while(opcion=!0)
		}

	public  void MenuCEO() {
			do {
			       System.out.println("1.View a patient. ");
			       System.out.println("2.Search. ");
			       System.out.println("0.EXIT. ");
			       System.out.println("\nChoose an option : ");
			       try {
			        int opcion = Integer.parseInt(in.readLine());
			        JDBCManager man = new JDBCManager();
			        switch(opcion) {
			        case 1:
			        int id = Integer.parseInt(in.readLine()); 
			           patient = man.getPatient(id);
			        break;
			        case 2:
			        	
			        break;
			        case 0:
			        System.exit(0);
			       
			        }
			       }
			}while(opcion=!0)
			}

	public  void MenuHHRR() {
			do {
			       System.out.println("1.Look replacement. ");
			       System.out.println("2.Modify patient. ");
			       System.out.println("0.EXIT. ");
			       System.out.println("\nChoose an option : ");
			       try {
			        int opcion = Integer.parseInt(in.readLine());
			        JDBCManager man = new JDBCManager();
			        switch(opcion) {
			        case 1:
			        int id = Integer.parseInt(in.readLine()); 
			           patient = man.getPatient(id);
			        break;
			        case 0:
			        System.exit(0);
			       
			        }
			       }
			}while(opcion=!0)
			}
	
	public  void MenuDoc() {
		do {
		       System.out.println("1.Introduce new patient. ");
		       System.out.println("0.EXIT. ");
		       System.out.println("\nChoose an option : ");
		       try {
		        int opcion = Integer.parseInt(in.readLine());
		        JDBCManager man = new JDBCManager();
		        switch(opcion) {
		        case 1:
		        int id = Integer.parseInt(in.readLine()); 
		           patient = man.getPatient(id);
		        break;
		        case 0:
		        System.exit(0);
		       
		        }
		       }
		}while(opcion=!0)
		}
	

	public Patient addPatient() {
		Scanner sc = new Scanner(System.in);
		Patient pat = new Patient();

		System.out.println("Introduce a new patient");
		System.out.println("Introduce the name");
		String name = sc.nextLine();
		pat.setName(name);
		System.out.println("Introduce the id of the patient");
		String dni = sc.nextLine();
		pat.setDni(dni);
		System.out.println("Introduce the job title");
		String job = sc.nextLine();
		pat.setName(name);
		System.out.println("Introduce the date of birth of the patient");
		Date dob = crear_fecha();
		pat.setDob(dob);
		System.out.println("Introduce the days that the patient has been off");
		Integer days_off = sc.nextInt();

		return pat;

	}

	private Date crear_fecha() {
		// TODO Auto-generated method stub
		return null;
	}

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
