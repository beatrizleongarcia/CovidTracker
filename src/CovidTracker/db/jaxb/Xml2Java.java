package CovidTracker.db.jaxb;

import java.io.File;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlType;

import CovidTracker.db.pojos.Covid_Test;
import CovidTracker.db.pojos.Doctor;
import CovidTracker.db.pojos.Patient;
import CovidTracker.db.pojos.Quarantine;
import CovidTracker.db.pojos.Symptoms;

public class Xml2Java {

	private static final String PERSISTENCE_PROVIDER = "user-company";
	private static EntityManagerFactory factory;

	public static void xml2JavaPAT(String filename) throws JAXBException {

		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Patient.class);
		// Get the unmarshaller
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// Use the Unmarshaller to unmarshal the XML document from a file
		File file = new File("./files/"+filename+".xml");
		Patient patient = (Patient) unmarshaller.unmarshal(file);

		// Print the report
		System.out.println("Patient's information:");
		System.out.println("Name: " + patient.getName());
		System.out.println("Date of birth: " + patient.getDob());
		System.out.println("Salary: " + patient.getSalary());
		System.out.println("Job tittle: " + patient.getJob_title());
		System.out.println("Days off work: " + patient.getDays_off_work());
		System.out.println("Economic impact: " + patient.getEconomic_impact());
		System.out.println("Name of doctor: " + patient.getDoctor().getName());
		List<Symptoms> symp = patient.getSymptoms();
		for (Symptoms sy : symp) {
			System.out.println("Symptom: " + sy.getType());
		}
		List<Quarantine> qua = patient.getQuarantine();
		for (Quarantine quar: qua) {
			System.out.println("Quarantine resason: " + quar.getReason());
		}
		System.out.println("Information about covid test:");
		List<Covid_Test> test = patient.getTests();
		for (Covid_Test covtest: test) {
			System.out.println("Type of test: " + covtest.getType_test());
			System.out.println("Public/Private:"+ covtest.getPublic_private());
			System.out.println("Laboratory: " + covtest.getLaboratory());
			System.out.println("Date of the test:"+ covtest.getDate_of_test());
			System.out.println("Price: " + covtest.getPrice());
		}

		
		// Store the report in the database
		// Create entity manager
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_PROVIDER);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();

		// Create a transaction
		EntityTransaction tx1 = em.getTransaction();

		// Start transaction
		tx1.begin();

		// Persist
		for (Symptoms symptoms : symp) {
			em.persist(symptoms);
		}
		for (Quarantine quarantine : qua) {
			em.persist(quarantine);
		}
		for (Covid_Test covid : test) {
			em.persist(covid);
		}
		em.persist(patient);
		
		// End transaction
		tx1.commit();
	}
	
	

	public static void xml2JavaDOC(String filename) throws JAXBException {

		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Doctor.class);
		// Get the unmarshaller
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// Use the Unmarshaller to unmarshal the XML document from a file
		File file = new File("./files/"+filename+".xml");
		Doctor doctor = (Doctor) unmarshaller.unmarshal(file);

		// Print the report
		System.out.println("Doctor:");
		System.out.println("Name: " + doctor.getName());
		System.out.println("Hospital: " + doctor.getHospital());
		System.out.println("Information about the patients:");
		List<Patient> pat = doctor.getPatients();
		for (Patient patient: pat) {
			System.out.println("Patient's information:");
			System.out.println("Name: " + patient.getName());
			System.out.println("Date of birth: " + patient.getDob());
			System.out.println("Job tittle: " + patient.getJob_title());
			List<Symptoms> symp = patient.getSymptoms();
			for (Symptoms sy : symp) {
				System.out.println("Symptom: " + sy.getType());
			}
			List<Quarantine> qua = patient.getQuarantine();
			for (Quarantine quar: qua) {
				System.out.println("Quarantine resason: " + quar.getReason());
			}
			System.out.println("Information about the covid tests:");
			List<Covid_Test> test = patient.getTests();
			for (Covid_Test covtest: test) {
				System.out.println("Type of test: " + covtest.getType_test());
				System.out.println("Public/Private:"+ covtest.getPublic_private());
				System.out.println("Laboratory: " + covtest.getLaboratory());
				System.out.println("Date of the test:"+ covtest.getDate_of_test());
				System.out.println("Price: " + covtest.getPrice());
			}
		}

		// Store the report in the database
		// Create entity manager
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_PROVIDER);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();

		// Create a transaction
		EntityTransaction tx1 = em.getTransaction();

		// Start transaction
		tx1.begin();

		// Persist
		// We assume the authors are not already in the database
		// In a real world, we should check if they already exist
		// and update them instead of inserting as new
		for (Patient p : pat) {
			em.persist(p);
		}
		/*/List<Doctor>  = 
		for (Covid_Test covid : test) {
			em.persist(covid);
		}*/
		em.persist(doctor);
		
		// End transaction
		tx1.commit();
	}
}
