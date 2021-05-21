package CovidTracker.db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import CovidTracker.db.xml.utils.SQLDateAdapter;

@Entity
@Table(name = "patient")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Patient")
@XmlType(propOrder = { "name","dob", "salary","job_title", "economic_impact", "days_off_work"})
public class Patient implements Serializable {

	private static final long serialVersionUID = 6791061415881333978L;
	@Id
	@GeneratedValue(generator="patient")
	@TableGenerator(name="patient", table="sqlite_sequence",
		pkColumnName="name", valueColumnName="seq", pkColumnValue="patient")
	@XmlTransient
	private Integer id;
	@XmlAttribute
	private String name;
	@XmlElement
	private Float salary;
	@XmlElement
	@XmlJavaTypeAdapter(CovidTracker.db.xml.utils.SQLDateAdapter.class)
	private Date dob;
	@XmlElement
	private String job_title;
	@XmlElement
	private Float economic_impact;
	@XmlElement
	private Integer days_off_work;
	@XmlTransient
	private Doctor doctor;
	@XmlTransient
	private List <Covid_Test> tests;
	@XmlTransient
	private List <Symptoms> symptoms;
	@XmlTransient
	private List <Quarantine> quarantine;

	
	public Patient() {
		super();
		this.economic_impact = (float) 0;
		this.days_off_work= 0;
		this.quarantine= new ArrayList<Quarantine>();
		this.symptoms= new ArrayList<Symptoms>();
		this.tests= new ArrayList<Covid_Test>();		
	}
	
	public Patient( String name, Date dob, String job_title, float salary,
			Doctor doctor, List<Covid_Test> tests, List<Symptoms>symptoms ,List<Quarantine>quarantine) {
        this.name=name;
        this.dob=dob;
        this.job_title=job_title;
        this.salary=salary;
        this.days_off_work= 0;
        this.economic_impact= (float) 0;
        this.doctor=doctor;   
        this.quarantine= quarantine;
		this.symptoms= symptoms ;
		this.tests= tests;	
}

	
	

	public Patient(String patient_name, Date dob, String job_title, float salary, Doctor doctor) {
		this.name=patient_name;
        this.dob=dob;
        this.job_title=job_title;
        this.salary=salary;
        this.days_off_work= 0;
        this.economic_impact=(float) 0;
        this.doctor=doctor;   
	}

	public Patient(int id, String patient_name, Date dob, String job_title, float salary, int days_off_work,
			float economic_impact, Doctor doctor) {
		this.id = id;
		this.name=patient_name;
        this.dob=dob;
        this.job_title=job_title;
        this.salary=salary;
        this.days_off_work= 0;
        this.economic_impact=(float) 0;
        this.doctor=doctor;
	}

	public Patient(String name, Date dob, String job_title, Float salary, List<Symptoms> symptoms,
			List<Quarantine> quarantines) {
		
		this.name=name;
        this.dob=dob;
        this.job_title=job_title;
        this.salary=salary;  
        this.quarantine= quarantines;
		this.symptoms= symptoms;
		this.days_off_work= 0;
        this.economic_impact=(float) 0;
        this.tests= new ArrayList<Covid_Test>();	
		
		
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
	
	public Float getEconomic_impact() {
		return economic_impact;
	}
	public void setEconomic_impact(Float economic_impact) {
		this.economic_impact = economic_impact;
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
		return "Patient [id=" + id + ", salary=" + salary + ", name=" + name + ", dob=" + dob
				+ ", job_title=" + job_title + ", days_off_work=" + days_off_work + ", doctor=" + doctor + ", tests="
				+ tests + ", synmptoms=" + symptoms + ", quarantine=" + quarantine + "]";
	}
	
	public void func_daysoff(Date today,Date date2) {
		long difference = today.getTime()-date2.getTime();
		long days = difference/(1000*60*60*24); //time is in miliseconds
		setDays_off_work((int) days); 
	}

	public void func_economic() {
		Float ec;
		ec = this.days_off_work * (this.salary/30);
		setEconomic_impact(ec);	
	}

	public void addNewTest(Covid_Test test) {
		if(test!=null){
		tests.add(test);
		}else {
			System.out.println("No existe el test");
		}
	}
	
	public void addDoctor(Doctor doc) {
		this.doctor = doc;
	}
	
}
