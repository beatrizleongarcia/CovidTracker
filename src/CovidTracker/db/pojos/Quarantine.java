package CovidTracker.db.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quarantine implements Serializable{
	

	private static final long serialVersionUID = 3509916136418043506L;

	
	private Integer id;
	private Integer time;
	private String reason;
	private List <Patient> patients;
	
	public Quarantine() {
		super();
	}
	public Quarantine(String reason,Integer time) {
		this.reason= reason;
		this.time = time;
	}

	public Quarantine(String type, int id) {
		this.reason = type;
		this.id = id;
		this.time = 10;
		this.patients = new ArrayList<>();
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

	public List<Patient> getPatients() {
		return patients;
	}
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
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
		return "Quarantine [id=" + id + ", time=" + time + ", reason=" + reason + "]";
	}
	
	

}
