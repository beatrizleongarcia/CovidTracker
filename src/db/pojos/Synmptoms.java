package db.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Synmptoms implements Serializable {
  

	private static final long serialVersionUID = -1713530039308307123L;

	
	private Integer id;
	private String type;
	private List <Patient> patients;
	private Quarantine quarantine;
	
	public Synmptoms() {
		super();
		this.patients= new ArrayList<Patient>();		
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

	public Quarantine getQuarantine() {
		return quarantine;
	}

	public void setQuarantine(Quarantine quarantine) {
		this.quarantine = quarantine;
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
		Synmptoms other = (Synmptoms) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Synmptoms [id=" + id + ", type=" + type + ", patients=" + patients + ", quarantine=" + quarantine + "]";
	}
	
	
	
	
}