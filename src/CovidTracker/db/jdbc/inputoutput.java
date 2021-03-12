package CovidTracker.db.jdbc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.Scanner;

import db.pojos.Patient;

public class inputoutput {

	
		private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		
		public static String User() {
			String user="";
			String password="";
			try {
			System.out.println("Welcome:");
			System.out.println("Log in:");
			System.out.println("-User:");
			user = in.readLine();
			System.out.println("-Password:");
			password = in.readLine();
			}
			catch(Exception e) {
				e.printStackTrace();
				
			}
			return user;
		}
		
		public static String Password() {
			String password="";
			try {
			System.out.println("-Password:");
			password = in.readLine();
			}
			catch(Exception e) {
				e.printStackTrace();
				
			}
			return password;
		}
		
		
		public Patient addPatient() {
		Scanner sc= new Scanner(System.in);
		Patient pat= new Patient();
		
		System.out.println("Introduce a new patient");
		System.out.println("Introduce the name");
		String name=sc.nextLine();
	    pat.setName(name);
		System.out.println("Introduce the id of the patient");
		String dni=sc.nextLine();
	    pat.setDni(dni);
		System.out.println("Introduce the job title");
		String job=sc.nextLine();
	    pat.setName(name);
		System.out.println("Introduce the date of birth of the patient");
		Date dob=crear_fecha();
		pat.setDob(dob);
		System.out.println("Introduce the days that the patient has been off");
		Integer days_off=sc.nextInt();
		
		return pat;

	
	}

		private Date crear_fecha() {
			// TODO Auto-generated method stub
			return null;
		}
		public static int get_int() {
			Scanner sc= new Scanner(System.in);
			int id= sc.nextInt();
			return id;	
		}
		public static String get_String() {
			Scanner sc= new Scanner(System.in);
			String a= sc.nextLine();
			return a;		
		}
		
	

}
