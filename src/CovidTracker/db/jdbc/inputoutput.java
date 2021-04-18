package CovidTracker.db.jdbc;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
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
		Float economic_impact =Float.parseFloat(in.readLine());´
		
		//para el doctor: hay que sacar la lista de doctores, y que se elija el suyo y se añada solo !!!!!!!!!!!!!!!1
		//sintomas : crear metodo para pedir los sintomas dando opciones !!!!!!!!!!!!!!!111111111111111111111111!!!!!!!!!!!!!!!
		//quarentena : lo mismo que sintomas !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		return 	new Patient(name, dob, job_tittle, salary, days_off_work,economic_impact, doctor_id,symptoms_id,quatentine_id);

	}

	//verse la clase en la que explica como meter la fecha !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	public static LocalDate crear_fecha(String dob) {
        LocalDate dobDate= LocalDate.parse(dob);
        return dobDate;
}



	
	
	
	//BORRAR ESTOS DOS METODOS DE ABAJO QUE HA HECHO BASILIO O QUE NOS ESPLIQUE PARA QUE SON !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
