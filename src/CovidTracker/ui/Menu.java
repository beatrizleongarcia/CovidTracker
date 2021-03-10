package CovidTracker.ui;

import CovidTracker.db.ifaces.DBManager;

public class Menu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBManager man = new DBManager();
		man.connect();
	}

}
