package CovidTracker.db.jpa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import CovidTracker.db.ifaces.UserManager;
import CovidTracker.db.pojos.users.Role;
import CovidTracker.db.pojos.users.User;
import CovidTracker.ui.InputOutput;



public class JPAUserManager implements UserManager {

	private EntityManager entman;

	@Override
	public void connect() {
		entman = Persistence.createEntityManagerFactory("user-company").createEntityManager();
		entman.getTransaction().begin();
		entman.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		entman.getTransaction().commit();
		List<Role> existingRoles = this.getRoles();
		if (existingRoles.size() < 5) {
			this.newRole(new Role("administrator"));
			this.newRole(new Role("CEO"));
			this.newRole(new Role("HHRR"));
			this.newRole(new Role("doctor"));
			this.newRole(new Role("informatic"));
		}
	}

	@Override
	public void disconnect() {
		entman.close();
	}

	@Override
	public void newUser(User u) {
		entman.getTransaction().begin();
		entman.persist(u);
		entman.getTransaction().commit();
	}

	@Override
	public void newRole(Role r) {
		entman.getTransaction().begin();
		entman.persist(r);
		entman.getTransaction().commit();
	}

	@Override
	public Role getRole(int id) {
		Query q = entman.createNativeQuery("SELECT * FROM roles WHERE id = ?", Role.class);
		q.setParameter(1, id);
		return (Role) q.getSingleResult();

	}

	@Override
	public List<Role> getRoles() {
		Query q = entman.createNativeQuery("SELECT * FROM roles", Role.class);
		return (List<Role>) q.getResultList();
	}

	@Override
	public User checkPassword(String email, String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] hash = md.digest();
			Query q = entman.createNativeQuery("SELECT * FROM users WHERE email = ? AND password = ?", User.class);
			q.setParameter(1, email);
			q.setParameter(2, hash);
			return (User) q.getSingleResult();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoResultException nre) {
			return null;
		}
		return null;
	}
	

	@Override
	public void changeRole(User user) {
		Query q = entman.createNativeQuery("SELECT * FROM users WHERE id = ?", User.class);
		q.setParameter(1, user.getId());
		User us = (User) q.getSingleResult();
		for(int x = 0; x< getRoles().size();x++) {
			System.out.println(getRoles().get(x));
			}
		System.out.println("Please enter the ID of the new user's role:");
		int id = InputOutput.get_int();
		Role role = getRole(id);

		// Begin transaction
		entman.getTransaction().begin();
		// Make changes
		us.setRole(role);
		role.addUser(us);
		// End transaction
		entman.getTransaction().commit();

	}

	@Override
	public void deleteRole(User user) {
		Query q2 = entman.createNativeQuery("SELECT * FROM users WHERE id = ?", User.class);
		q2.setParameter(1, user.getId());
		User us = (User) q2.getSingleResult();

		// Begin transaction
		entman.getTransaction().begin();
		// Remove the object
		entman.remove(us);
		// End transaction
		entman.getTransaction().commit();
	
	}

}