package CovidTracker.db.ifaces;


import java.sql.Date;
import java.util.List;

import CovidTracker.db.pojos.*;

public interface DBManager {

	public void connect(); 
	public void disconnect();
	public void addPerson(Patient p); //add a person  to the data base 
	public Patient searchPatientByName(String name); // search a patient by name 
	public void ModifyPatient(Patient p); //introduce that name of a patient, show all his info. and modify it 
	public Doctor searchDoctorbyId(int id);//the program search the doctor's id 
	public Doctor searchDoctorbyName(String name); //search a doctor by name using searchDoctorbyId
	public void delete_patient(String name);// Delete a patient
	public void symptoms_patient(Patient p, Symptoms s);//to insert into the table symptoms_patient
	public Patient test_patient(Patient pat);//borrar
	public Date last_test(Patient patnotest);// get the data of the test to generate days off work
	public void viewDoctors();// print the names of the doctors
	public void quarantine_patient(Patient p, Quarantine s);
	public void dropTables(); // Drop all the tables of the data base 
	void viewPatient(int id);
	public Integer searchDoctorId(String name);
	public List<Integer> searchSymptomsId(Integer id);
	public String searchSymptomstype(Integer id);
	public List<Integer> searchQuarantineId(Integer id);
	public String searchQuarantinereason(Integer id);

	
}
