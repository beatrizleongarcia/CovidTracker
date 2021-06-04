package CovidTracker.db.ifaces;


import java.sql.Date;
import java.util.List;

import CovidTracker.db.pojos.*;

public interface DBManager {

	public void connect(); // connect to the data base
	public void disconnect();//disconnect from the data base
	public void addPerson(Patient p); //add a person  to the data base 
	public Patient searchPatientByName(String name); // search a patient by name 
	public void ModifyPatient(Patient p); //introduce that name of a patient, show all his info. and modify it 
	public Doctor searchDoctorbyId(int id);//the program search the doctor's id 
	public Doctor searchDoctorbyName(String name); //search a doctor by name using searchDoctorbyId
	public void delete_patient(String name);// Delete a patient
	public void symptoms_patient(Patient p, Symptoms s);// insert into the table symptoms_patient
	public Patient test_patient(Patient pat);// add a test to a patient
	public Date last_test(Patient patnotest);// get the data of the test to generate days off work
	public void viewDoctors();// print the names of the doctors
	public void quarantine_patient(Patient p, Quarantine s); //insert into the table quarantine_patient
	public void dropTables(); // Drop all the tables of the data base 
	public void viewPatient(int id); // View a patient from an id
	public Integer searchDoctorId(String name);//Search the doctor id from a name
	public List<Integer> searchSymptomsId(Integer id);// get a list of all the symptom's id from a patient's id
	public String searchSymptomstype(Integer id);// get the symptom's type from a symptom's id
	public List<Integer> searchQuarantineId(Integer id);// get a list of all the quarantine's id from a patient's id
	public String searchQuarantinereason(Integer id); //get the quarantine's reason from a quarantiene's id
	public void viewPatientsName();// view all the patinet's names
	public List<Doctor> viewAllDoctors(); // get a list with all the doctors
	public List<Patient> viewAllPatients();// get a list with all the patients


	
}
