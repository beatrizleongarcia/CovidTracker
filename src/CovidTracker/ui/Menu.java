package CovidTracker.ui;

import CovidTracker.db.ifaces.DBManager;
import CovidTracker.db.jdbc.JDBCManager;

public class Menu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBManager man = new JDBCManager();
		man.connect();
	}

}
