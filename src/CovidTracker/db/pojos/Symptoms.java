package CovidTracker.db.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "symptoms")
public class Symptoms implements Serializable {
  

	private static final long serialVersionUID = -1713530039308307123L;

	@Id
	@GeneratedValue(generator="symptoms")
	@TableGenerator(name="symptoms", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq", pkColumnValue="symptoms")
	
	private Integer id;
	private String type;
	@ManyToMany(mappedBy = "symptoms")
	private List <Patient> patients;
	
	public Symptoms() {
		super();
		this.patients= new ArrayList<Patient>();		
	}

	public Symptoms(String type, int id) {
		this.type = type;
		this.id = id;
		this.patients= new ArrayList<>();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		Symptoms other = (Symptoms) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Synmptoms [id=" + id + ", type=" + type + ", patients=" + patients  + "]";
	}
	
	
	
	
}
