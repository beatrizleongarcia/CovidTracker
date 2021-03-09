package CovidTracker.db.ifaces;
import java.util.List;

public interface DBManager {

	public void connect();
	public void disconnect();
	public void addPerson(Person p);
	public Person getPerson(int id);
	public List<Person> searchPersonByName(String name);
	public void LookReplacement(String title);
	public void ModifyPatient(Person p);

}
