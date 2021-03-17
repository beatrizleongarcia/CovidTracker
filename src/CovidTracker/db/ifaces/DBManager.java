package CovidTracker.db.ifaces;
import java.util.List;

import db.pojos.*;

public interface DBManager {

	public void connect();
	public void create();
	public void disconnect();
	public void newPerson(Patient p);
	public Patient getPatient_id(int id);
	public Patient searchPatientByName(String name);
	public void LookReplacement(String title);
	public void ModifyPatient(Patient p);
	public Patient addPerson();

}
