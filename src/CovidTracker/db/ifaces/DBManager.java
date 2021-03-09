package CovidTracker.db.ifaces;
import java.util.List;

import db.pojos.*;

public interface DBManager {

	public void connect();
	public void disconnect();
	public void addPerson(Patient p);
	public Patient getPatient(int id);
	public List<Patient> searchPatientByName(String name);
	public void LookReplacement(String title);
	public void ModifyPatient(Patient p);

}
