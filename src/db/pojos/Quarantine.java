package db.pojos;

import java.io.Serializable;
import java.util.List;

public class Quarantine implements Serializable{
	

	private static final long serialVersionUID = 3509916136418043506L;



	private Integer id;
	private Integer time;
	private String reason;
	private List <Synmptoms> synmptoms;
	private Patient patient;
	
	public Quarantine() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<Synmptoms> getSynmptoms() {
		return synmptoms;
	}

	public void setSynmptoms(List<Synmptoms> synmptoms) {
		this.synmptoms = synmptoms;
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
		Quarantine other = (Quarantine) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Quarantine [id=" + id + ", time=" + time + ", reason=" + reason + ", synmptoms=" + synmptoms
				+ ", partient=" + patient + "]";
	}
	
	

}
