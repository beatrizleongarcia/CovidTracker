package CovidTracker.db.ifaces;

import java.util.List;

import CovidTracker.db.pojos.users.Role;
import CovidTracker.db.pojos.users.User;


public interface UserManager {

	public void connect();//connect to the data base 
	public void disconnect(); //disconnect
	public void newUser(User u); // create a new user
	public void newRole(Role r); // create a new role (admin, ceo,hhrr,doctor)
	public Role getRole(int id); // pass the id and returns the role
	public List<Role> getRoles(); // show list of roles 
	public User checkPassword(String email, String password); // check if the password is correct 
	public void deleteRole(User user);// delete a user from a role DELETE (remove)
	public void changeRole(User user);//Change the role of the user passed UPDATE(setter)
}

