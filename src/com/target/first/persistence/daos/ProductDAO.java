package com.target.first.persistence.daos;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.target.first.persistence.entities.Product;
import com.target.first.persistence.helper.EntityManagerHelper;

/**
 * A data access object (DAO) providing persistence and search support for
 * Product entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see com.target.first.persistence.entities.Product
 * @author Klaus Villaca
 */
public class ProductDAO {
	// property constants
	public static final String ID = "id";
	public static final String NAME = "name";

	private EntityManagerHelper emHelper;

	public ProductDAO() {
		emHelper = new EntityManagerHelper();
	}
	
	// Just for tests
	public ProductDAO(EntityManagerHelper emHelper) {
		this.emHelper = emHelper; 
	}
	
	
	private EntityManager getEntityManager() {
		return emHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved Product entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * 
	 * <pre>
	 * 
	 * EntityManagerHelper.beginTransaction();
	 * ProductDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Product entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */

	public void save(Product entity) {
		emHelper.log("saving Product instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			emHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			emHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent Product entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * ProductDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            Product entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Product entity) {
		emHelper.log("deleting Product instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(Product.class, entity.getPk());
			getEntityManager().remove(entity);
			emHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			emHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved Product entity and return it or a copy of it
	 * to the sender. A copy of the Product entity parameter is returned when
	 * the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = ProductDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Product entity to update
	 * @return Product the persisted Product entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public Product update(Product entity) {
		emHelper.log("updating Product instance", Level.INFO, null);
		try {
			Product result = getEntityManager().merge(entity);
			emHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			emHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public Product findById(Integer id) {
		emHelper.log("finding Product instance with id: " + id, Level.INFO, null);
		try {
			Product instance = getEntityManager().find(Product.class, id);
			return instance;
		} catch (RuntimeException re) {
			emHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all Product entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Product property to query
	 * @param value
	 *            the property value to match
	 * @return List<Product> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<Product> findByProperty(String propertyName, final Object value) {
		emHelper.log("finding Product instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from Product model where model." + propertyName
					+ "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			return query.getResultList();
		} catch (RuntimeException re) {
			emHelper.log("find by property name failed", Level.SEVERE, re);
			throw re;
		}
	}

	public List<Product> findById(Object id) {
		return findByProperty(ID, id);
	}

	public List<Product> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	/**
	 * Find all Product entities.
	 * 
	 * @return List<Product> all Product entities
	 */
	@SuppressWarnings("unchecked")
	public List<Product> findAll() {
		emHelper.log("finding all Product instances", Level.INFO, null);
		try {
			final String queryString = "select model from Product model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			emHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}