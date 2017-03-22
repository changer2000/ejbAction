package home.study.system;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class UserDatabaseEntityManager {
	

	/**
	 * It will produce the EntityManager for all DAO.
	 * It will be injected from persistence.xml
	 */
	@Produces
	//@SeaDatabase
	@PersistenceContext(unitName="ejbActionWeb")
	private EntityManager em;
}
