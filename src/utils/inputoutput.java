package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class inputoutput {

	
		private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		
		public static String Welcome() {
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
		
	

}
