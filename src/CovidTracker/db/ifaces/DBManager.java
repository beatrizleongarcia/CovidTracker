package CovidTracker.db.ifaces;


import CovidTracker.db.pojos.*;

public interface DBManager {

	public void connect(); 
	public void disconnect();
	public void addPerson(Patient p); //add a person  to the data base 
	public Patient searchPatientByName(String name); // search a patient by name 
	public boolean LookReplacement(Patient p); //introduce job, return all patients with that job, select one patient and replace him 
	public void ModifyPatient(Patient p); //introduce that name of a patient, show all his info. and modify it 
	public Doctor searchDoctorbyId(int id);//the program search the doctor's id 
	public Doctor searchDoctorbyName(String name); //search a doctor by name using searchDoctorbyId
	public void delete_patient(String name);// Delete a patient
	public void symptoms_patient(Patient p, Symptoms s);//to insert into the table symptoms_patient
	public Patient test_patient();
}
