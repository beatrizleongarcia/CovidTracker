package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class inputoutput {

	public static void main(String[] args) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		
		public static String Welcome() {
			System.out.println("Welcome: Log in:");
			System.out.println("Log in:");
			System.out.println("-User:");
			String user = in.readLine();
			System.out.println("-Password:");
			String password = in.readLine();
		}
		
	}

}
