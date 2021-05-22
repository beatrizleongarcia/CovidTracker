package CovidTracker.db.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

import java.io.Serializable;
@Entity
@Table(name = "doctor")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Doctor")
@XmlType(propOrder = { "name","hospital","patients"})
public class Doctor implements Serializable {

	private static final long serialVersionUID = 8612696465626368239L;
	@Id
	@GeneratedValue(generator="doctor")
	@TableGenerator(name="doctor", table="sqlite_sequence",
		pkColumnName="name", valueColumnName="seq", pkColumnValue="doctor")
	@XmlTransient
	private Integer id;
	@XmlElement
	private String name;
	@XmlElement
	private String hospital;
	@OneToMany(mappedBy="doctor")
	@XmlElement(name = "Patient")
    @XmlElementWrapper(name = "patient")
	private List<Patient> patients;
	@XmlTransient
	@OneToMany(mappedBy="doctor")
	private List<Covid_Test> tests;

	public Doctor() {
		super();
		this.patients= new ArrayList<Patient>();
		this.tests= new ArrayList<Covid_Test>();
	}

	public Doctor(Integer id, String name, String hospital) {
		this.id= id;
		this.name = name;
		this.hospital= hospital;
		this.patients= new ArrayList<Patient>();
	}

	public Doctor(String name, String hospital) {
		this.name = name;
		this.hospital= hospital;
		this.patients= new ArrayList<Patient>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
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
		result = prime * result + ((hospital == null) ? 0 : hospital.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((patients == null) ? 0 : patients.hashCode());
		result = prime * result + ((tests == null) ? 0 : tests.hashCode());
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
		Doctor other = (Doctor) obj;
		if (hospital == null) {
			if (other.hospital != null)
				return false;
		} else if (!hospital.equals(other.hospital))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (patients == null) {
			if (other.patients != null)
				return false;
		} else if (!patients.equals(other.patients))
			return false;
		if (tests == null) {
			if (other.tests != null)
				return false;
		} else if (!tests.equals(other.tests))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Doctor [id=" + id + ", name=" + name + ", hospital=" + hospital + ", patients=" + patients + ", tests="
				+ tests + "]";
	}

}
