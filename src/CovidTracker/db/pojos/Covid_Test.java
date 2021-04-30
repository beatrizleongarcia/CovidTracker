package CovidTracker.db.pojos;

import java.io.Serializable;
import java.sql.Date;

public class Covid_Test implements Serializable {


	private static final long serialVersionUID = -524555949615115069L;

	
	private Integer id;
	private String public_private;
	private String type_test;
	private String laboratory;
	private Date date_of_test;
	private Float price;
	private Doctor doctor;
	private Patient patient;
	
	public Covid_Test() {
		super();
	}

	public Covid_Test(String public_private2, String type_test2, Date date_of_test2, Float price2) {
		
		this.date_of_test = date_of_test2;
		this.price = price2;
		this.public_private = public_private2;
		this.type_test = type_test2;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPublic_private() {
		return public_private;
	}

	public void setPublic_private(String public_private) {
		this.public_private = public_private;
	}

	public String getType_test() {
		return type_test;
	}

	public void setType_test(String type_test) {
		this.type_test = type_test;
	}

	public String getLaboratory() {
		return laboratory;
	}

	public void setLaboratory(String laboratory) {
		this.laboratory = laboratory;
	}

	public Date getDate_of_test() {
		return date_of_test;
	}

	public void setDate_of_test(Date date_of_test) {
		this.date_of_test = date_of_test;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
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
		Covid_Test other = (Covid_Test) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Covid_Test [id=" + id + ", public_private=" + public_private + ", type_test=" + type_test
				+ ", laboratory=" + laboratory + ", date_of_test=" + date_of_test + ", price=" + price + ", doctor="
				+ doctor + ", patient=" + patient + "]";
	}
	
	
	
	
    
	
}
