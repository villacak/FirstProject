package com.target.first.persistence.daos;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.target.first.persistence.entities.Price;
import com.target.first.persistence.helper.EntityManagerHelper;

/**
 * A data access object (DAO) providing persistence and search support for Price
 * entities. Transaction control of the save(), update() and delete() operations
 * must be handled externally by senders of these methods or must be manually
 * added to each of these methods for data to be persisted to the JPA datastore.
 * 
 * @see com.target.first.persistence.entities.Price
 * @author Klaus Villaca
 */
public class PriceDAO {
	// property constants
	public static final String PRODUCT_ID = "productId";
	public static final String PRICE = "price";

	private EntityManagerHelper emHelper = new EntityManagerHelper();

	private EntityManager getEntityManager() {
		return emHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved Price entity. All
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
	 * PriceDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Price entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(Price entity) {
		emHelper.log("saving Price instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			emHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			emHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent Price entity. This operation must be performed within
	 * the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * PriceDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            Price entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Price entity) {
		emHelper.log("deleting Price instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(Price.class, entity.getPk());
			getEntityManager().remove(entity);
			emHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			emHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved Price entity and return it or a copy of it to
	 * the sender. A copy of the Price entity parameter is returned when the JPA
	 * persistence mechanism has not previously been tracking the updated
	 * entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = PriceDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Price entity to update
	 * @return Price the persisted Price entity instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public Price update(Price entity) {
		emHelper.log("updating Price instance", Level.INFO, null);
		try {
			Price result = getEntityManager().merge(entity);
			emHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			emHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public Price findById(Integer id) {
		emHelper.log("finding Price instance with id: " + id, Level.INFO, null);
		try {
			Price instance = getEntityManager().find(Price.class, id);
			return instance;
		} catch (RuntimeException re) {
			emHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all Price entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Price property to query
	 * @param value
	 *            the property value to match
	 * @return List<Price> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<Price> findByProperty(String propertyName, final Object value) {
		emHelper.log("finding Price instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		try {
			final String queryString = "select model from Price model where model." + propertyName + "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			return query.getResultList();
		} catch (RuntimeException re) {
			emHelper.log("find by property name failed", Level.SEVERE, re);
			throw re;
		}
	}

	public List<Price> findByProductId(Object productId) {
		return findByProperty(PRODUCT_ID, productId);
	}

	public List<Price> findByPrice(Object price) {
		return findByProperty(PRICE, price);
	}

	/**
	 * Find all Price entities.
	 * 
	 * @return List<Price> all Price entities
	 */
	@SuppressWarnings("unchecked")
	public List<Price> findAll() {
		emHelper.log("finding all Price instances", Level.INFO, null);
		try {
			final String queryString = "select model from Price model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			emHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}
}