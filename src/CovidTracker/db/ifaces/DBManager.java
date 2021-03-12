package CovidTracker.db.ifaces;
import java.util.List;

import db.pojos.*;

public interface DBManager {

	public void connect();
	public void create();
	public void disconnect();
	public void addPerson(Patient p);
	public Patient getPatient_id(int id);
	public List<Patient> searchPatientByName(String name);
	public void LookReplacement(String title);
	public Patient ModifyPatient(Patient p);
	Patient addPerson();

}
