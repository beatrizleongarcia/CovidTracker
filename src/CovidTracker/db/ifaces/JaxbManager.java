package CovidTracker.db.ifaces;

import CovidTracker.db.jaxb.*;

public interface JaxbManager {
	public void printPatients();
	public  void java2XmlDOC(String filename);
}
