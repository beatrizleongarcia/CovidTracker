package CovidTracker.db.jaxb;

import java.io.File;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import CovidTracker.db.ifaces.JaxbManager;
import CovidTracker.db.pojos.Doctor;
import CovidTracker.db.pojos.Patient;
import CovidTracker.ui.InputOutput;

public class Jaxb implements JaxbManager {
	private static EntityManager em;

	private static void printPatients() {
		Query q1 = em.createNativeQuery("SELECT * FROM patient", Patient.class);
		List<Patient> ps = (List<Patient>) q1.getResultList();
		// Print the patients
		for (Patient p : ps) {
			System.out.println(p.getId() + "." + p.getName());
		}
	}

	private static void printDoctor() {
		Query q1 = em.createNativeQuery("SELECT * FROM doctor", Doctor.class);
		List<Doctor> ds = (List<Doctor>) q1.getResultList();
		// Print the doctors
		for (Doctor d : ds) {
			System.out.println(d.getId() + "." + d.getName());
		}
	}

	@Override
	public void java2XmlPAT(String filename) throws Exception {
		// Get the entity manager
		em = Persistence.createEntityManagerFactory("user-company").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();

		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Patient.class);
		// Get the marshaller
		Marshaller marshaller = jaxbContext.createMarshaller();

		// Pretty formatting
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Choose the patient to turn into an XML
		printPatients();
		System.out.print("Choose a patient's id to turn into an XML file:");
		int pat_id = InputOutput.get_int();
		Query q2 = em.createNativeQuery("SELECT * FROM patient WHERE id = ?", Patient.class);
		q2.setParameter(1, pat_id);
		Patient pat = (Patient) q2.getSingleResult();

		// Use the Marshaller to marshal the Java object to a file
		File file = new File("./files/" + filename + ".xml");
		marshaller.marshal(pat, file);
		// Printout
		marshaller.marshal(pat, System.out);

	}

	@Override
	public void java2XmlDOC(String filename) throws Exception {
		
		// Get the entity manager
		em = Persistence.createEntityManagerFactory("user-company").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();

		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Doctor.class);
		// Get the marshaller
		Marshaller marshaller = jaxbContext.createMarshaller();

		// Pretty formatting
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Choose the doctor to turn into an XML
		printDoctor();
		System.out.print("Choose the doctor's id to turn into an XML file:");
		int doc_id = InputOutput.get_int();
		Query q2 = em.createNativeQuery("SELECT * FROM doctor WHERE id = ?", Doctor.class);
		q2.setParameter(1, doc_id);
		Doctor doc = (Doctor) q2.getSingleResult();

		// Use the Marshaller to marshal the Java object to a file
		File file = new File("./files/" + filename + ".xml");
		marshaller.marshal(doc, file);
		// Printout
		marshaller.marshal(doc, System.out);

	}

	@Override
	public void simpleTransform(String sourcePath, String xsltPath, String resultDir) {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	private final String PERSISTENCE_PROVIDER = "user-company";
	private EntityManagerFactory factory;

	@Override
	public void xml2JavaPAT(String filename) throws JAXBException {

		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Patient.class);
		// Get the unmarshaller
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// Use the Unmarshaller to unmarshal the XML document from a file
		File file = new File("./files/" + filename + ".xml");
		Patient patient = (Patient) unmarshaller.unmarshal(file);

		// Print the patient
		System.out.println("\nPatient's information:");
		System.out.println("Name: " + patient.getName());
		System.out.println("Date of birth: " + patient.getDob());
		System.out.println("Salary: " + patient.getSalary());
		System.out.println("Job tittle: " + patient.getJob_title());
		System.out.println("Days off work: " + patient.getDays_off_work());
		System.out.println("Economic impact: " + patient.getEconomic_impact());

		// Store the patient in the database
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
		em.persist(patient);

		// End transaction
		tx1.commit();
	}

	@Override
	public void xml2JavaDOC(String filename) throws JAXBException {

		// Create the JAXBContext
		JAXBContext jaxbContext = JAXBContext.newInstance(Doctor.class);
		// Get the unmarshaller
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// Use the Unmarshaller to unmarshal the XML document from a file
		File file = new File("./files/" + filename + ".xml");
		Doctor doctor = (Doctor) unmarshaller.unmarshal(file);

		// Print the doctor and his patients
		System.out.println("\nDoctor's information:");
		System.out.println("Name: " + doctor.getName());
		System.out.println("Hospital: " + doctor.getHospital());
		System.out.println("\nInformation about the patients:");
		List<Patient> pat = doctor.getPatients();
		for (Patient patient : pat) {
			System.out.println("\nPatient: ");
			System.out.println("Name: " + patient.getName());
			System.out.println("Date of birth: " + patient.getDob());
			System.out.println("Job tittle: " + patient.getJob_title());
			System.out.println("Salary: " +patient.getSalary());
			System.out.println("Economic impact: " +patient.getEconomic_impact());
			System.out.println("Days off work: " + patient.getDays_off_work());
			
		}

		// Store the doctor in the database
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
		// We assume the patients are not already in the database
		for (Patient p : pat) {
			em.persist(p);
		}
		em.persist(doctor);

		// End transaction
		tx1.commit();
	}
}
