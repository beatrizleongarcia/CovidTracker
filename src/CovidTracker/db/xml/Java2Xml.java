package CovidTracker.db.xml;

import java.io.File;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import CovidTracker.db.pojos.Doctor;
import CovidTracker.db.pojos.Patient;
import CovidTracker.ui.inputoutput;

public class Java2Xml {

	// Put entity manager and buffered reader here so it can be used
	// in several methods
	private static EntityManager em;
		
	private static void printPatients() {
		Query q1 = em.createNativeQuery("SELECT * FROM patient", Patient.class);
		List<Patient> ps = (List<Patient>) q1.getResultList();
		// Print the departments
		for (Patient p : ps) {
			System.out.println(p);
		}
	}
	private static void printDoctor() {
		Query q1 = em.createNativeQuery("SELECT * FROM doctor", Doctor.class);
		List<Doctor> ds = (List<Doctor>) q1.getResultList();
		// Print the departments
		for (Doctor d : ds) {
			System.out.println(d);
		}
	}
	public static void java2XmlPAT() throws Exception {
		// Get the entity manager
		// Note that we are using the class' entity manager
		em = Persistence.createEntityManagerFactory("user-company").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
				
		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Patient.class);
		// Get the marshaller
		Marshaller marshaller = jaxbContext.createMarshaller();
		
		// Pretty formatting
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		
		// Choose the report to turn into an XML
		// Choose his new department
		printPatients();
		System.out.print("Choose a patient to turn into an XML file:");
		int pat_id = inputoutput.get_int();
		Query q2 = em.createNativeQuery("SELECT * FROM patient WHERE id = ?", Patient.class);
		q2.setParameter(1, pat_id);
		Patient pat = (Patient) q2.getSingleResult();
		
		// Use the Marshaller to marshal the Java object to a file
		File file = new File("./xmls/Sample-Report.xml");
		marshaller.marshal(pat, file);
		// Printout
		marshaller.marshal(pat, System.out);

	}
	
	public static void java2XmlDOC() throws Exception {
		// Get the entity manager
		// Note that we are using the class' entity manager
		em = Persistence.createEntityManagerFactory("user-company").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
				
		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Doctor.class);
		// Get the marshaller
		Marshaller marshaller = jaxbContext.createMarshaller();
		
		// Pretty formatting
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		
		// Choose the report to turn into an XML
		// Choose his new department
		printPatients();
		System.out.print("Choose a doctor to turn into an XML file:");
		int doc_id = inputoutput.get_int();
		Query q2 = em.createNativeQuery("SELECT * FROM patient WHERE id = ?", Doctor.class);
		q2.setParameter(1, doc_id);
		Doctor doc = (Doctor) q2.getSingleResult();
		
		// Use the Marshaller to marshal the Java object to a file
		File file = new File("./xmls/Sample-Report.xml");
		marshaller.marshal(doc, file);
		// Printout
		marshaller.marshal(doc, System.out);

	}
}
