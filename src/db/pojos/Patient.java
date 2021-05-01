package db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient implements Serializable {

	private static final long serialVersionUID = 6791061415881333978L;
	
	private Integer id;
	private Float salary;
	private String name;
	private Date dob;
	private String job_title;
	private Float economic_impact;
	private Integer days_off_work;
	private Doctor doctor;
	private List <Covid_Test> tests;
	private List <Symptoms> symptoms;
	private List <Quarantine> quarantine;

	
	public Patient() {
		super();
		this.economic_impact = this.func_economic();
		this.days_off_work= this.func_daysoff(,); 
		this.quarantine= new ArrayList<Quarantine>();
		this.symptoms= new ArrayList<Symptoms>();
		this.tests= new ArrayList<Covid_Test>();		
	}
	
	public Patient( String name, Date dob, String job_title, float salary,
			Doctor doctor) {
        this.name=name;
        this.dob=dob;
        this.job_title=job_title;
        this.salary=salary;
       /this.days_off_work= 0;
        this.economic_impact=0;
        this.doctor=doctor;   
        /*this.quarantine= quarantine;
		this.symptoms= symptoms ;
		this.tests= tests;	*/
}

	
	

	public Patient(int id, String patient_name, Date dob, String job_title, float salary, int days_off_work,
			float economic_impact, Doctor doctor) {
		this.id = id;
		this.name=patient_name;
        this.dob=dob;
        this.job_title=job_title;
        this.salary=salary;
        this.days_off_work= this.func_daysoff();
        this.economic_impact=this.func_economic();
        this.doctor=doctor;   
	}

	public Float getSalary() {
		return salary;
	}
	public void setSalary(Float salary) {
		this.salary = salary;
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

	public List<Symptoms> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List<Symptoms> symptoms) {
		this.symptoms = symptoms;
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
		float economic_impact= getDays_off_work()*5;
		return economic_impact;
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
	
	public void addNewTest(Covid_Test test) {
		tests.add(test);
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", salary=" + salary + ", name=" + name + ", dob=" + dob
				+ ", job_title=" + job_title + ", days_off_work=" + days_off_work + ", doctor=" + doctor + ", tests="
				+ tests + ", synmptoms=" + symptoms + ", quarantine=" + quarantine + "]";
	}
	
	public Integer func_daysoff(Date today,Date date2) {
		long difference = today.getTime()-date2.getTime();
		long days = difference/(1000*60*60*24); //time is in miliseconds
		return (int) days; 
	}

	public Float func_economic() {
		Float ec;
		ec = this.days_off_work * (this.salary/30);
		return ec;
				
	}
	
}
