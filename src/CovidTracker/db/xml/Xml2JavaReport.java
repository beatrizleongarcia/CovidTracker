package CovidTracker.db.xml;

import java.io.File;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlType;

import CovidTracker.db.pojos.Patient;
import CovidTracker.db.pojos.Quarantine;
import CovidTracker.db.pojos.Symptoms;

public class Xml2JavaReport {

	private static final String PERSISTENCE_PROVIDER = "user-company";
	private static EntityManagerFactory factory;

	public static void xml2JavaPAT() throws JAXBException {

		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Patient.class);
		// Get the unmarshaller
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// Use the Unmarshaller to unmarshal the XML document from a file
		File file = new File("./xmls/External-Patient.xml");
		Patient patient = (Patient) unmarshaller.unmarshal(file);

		// Print the report
		System.out.println("Patient:");
		System.out.println("Name: " + patient.getName());
		System.out.println("Date of birth: " + patient.getDob());
		System.out.println("Salary: " + patient.getSalary());
		System.out.println("Job tittle: " + patient.getJob_title());
		System.out.println("Days off work: " + patient.getDays_off_work());
		System.out.println("Economic impact: " + patient.getEconomic_impact());
		System.out.println("Name of doctor: " + patient.getDoctor());
		List<Symptoms> symp = patient.getSymptoms();
		for (Symptoms sy : symp) {
			System.out.println("Symptoms: " + sy.getType());
		}
		List<Quarantine> qua = patient.getQuarantine();
		for (Quarantine quar: qua) {
			System.out.println("Quarantine: " + quar.getReason());
		}
		///falta lista de test!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 
		
		
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
		em.persist(patient);
		
		// End transaction
		tx1.commit();
	}
	
	///falta hacer el del doctor igual que el del paticente!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public static void xml2JavaDOC() throws JAXBException {

		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Doctor.class);
		// Get the unmarshaller
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// Use the Unmarshaller to unmarshal the XML document from a file
		File file = new File("./xmls/External-Doctor.xml");
		Doctor doctor = (Doctor) unmarshaller.unmarshal(file);

		// Print the report
		System.out.println("Doctor:");
		System.out.println("Name: " + report.getName());
		System.out.println("Content: " + report.getContent());
		System.out.println("Date: " + report.getLocalDate());
		List<Employee> emps = report.getAuthors();
		for (Employee emp : emps) {
			System.out.println("Author: " + emp.getName());
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
		for (Employee employee : emps) {
			em.persist(employee);
		}
		em.persist(doctor);
		
		// End transaction
		tx1.commit();
	}
}
