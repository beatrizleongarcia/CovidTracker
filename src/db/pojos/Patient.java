package db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Patient implements Serializable {

	private static final long serialVersionUID = 6791061415881333978L;
	
	private Integer id;
	private Float salary;
	private String dni;
	private String name;
	private Date dob;
	private String job_title;
	private Float economic_impact;
	private Integer days_off_work;
	private Doctor doctor;
	private List <Covid_Test> tests;
	private List <Synmptoms> synmptoms;
	private List <Quarantine> quarantine;

	
	public Patient() {
		super();
		this.quarantine= new ArrayList<Quarantine>();
		this.synmptoms= new ArrayList<Synmptoms>();
		this.tests= new ArrayList<Covid_Test>();		
	}
	
	public Float getSalary() {
		return salary;
	}
	public void setSalary(Float salary) {
		this.salary = salary;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getJob_title() {
		return job_title;
	}
	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}
	public Integer getDays_off_work() {
		return days_off_work;
	}
	public void setDays_off_work(Integer days_off_work) {
		this.days_off_work = days_off_work;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public List<Synmptoms> getSynmptoms() {
		return synmptoms;
	}

	public void setSynmptoms(List<Synmptoms> synmptoms) {
		this.synmptoms = synmptoms;
	}

	public List<Quarantine> getQuarantine() {
		return quarantine;
	}

	public void setQuarantine(List<Quarantine> quarantine) {
		this.quarantine = quarantine;
	}

	
	public List<Covid_Test> getTests() {
		return tests;
	}

	public void setTests(List<Covid_Test> tests) {
		this.tests = tests;
	}
	public Float getEconomic_impact() {
		return economic_impact;
	}

	public void setEconomic_impact(Float economic_impact) {
		this.economic_impact = economic_impact;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", salary=" + salary + ", dni=" + dni + ", name=" + name + ", dob=" + dob
				+ ", job_title=" + job_title + ", days_off_work=" + days_off_work + ", doctor=" + doctor + ", tests="
				+ tests + ", synmptoms=" + synmptoms + ", quarantine=" + quarantine + "]";
	}

	
    
	

	
	
   
}
