package CovidTracker.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import CovidTracker.db.ifaces.DBManager;
import CovidTracker.db.jdbc.JDBCManager;
import CovidTracker.db.jdbc.inputoutput;

public class Menu {
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	private static void Menu() {
        System.out.println("1. ");
        System.out.println("0.EXIT. ");
        System.out.println("\nChoose an option : ");
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBManager man = new JDBCManager();
		man.connect();
		
		String user=inputoutput.User();
		String password=inputoutput.Password();
		
	}
}
