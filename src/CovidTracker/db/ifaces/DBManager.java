package CovidTracker.db.ifaces;


import db.pojos.*;

public interface DBManager {

	public void connect(); 
	public void disconnect();
	public void addPerson(Patient p); //add a person  to the data base 
	public Patient getPatient(String name); //introduce name and return patient 
	public Patient getPatient_id(int id); //introduce id and return patient 
	public Patient searchPatientByName(String name); // search a patient by name 
	public boolean LookReplacement(Patient p); //introduce job, return all patients with that job, select one patient and replace him 
	public void ModifyPatient(Patient p); //introduce that name of a patient, show all his info. and modify it 
	public Patient newPerson(); // create and return a new patient

}
