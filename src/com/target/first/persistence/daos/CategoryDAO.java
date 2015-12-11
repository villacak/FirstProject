package com.target.first.persistence.daos;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.target.first.persistence.entities.Category;
import com.target.first.persistence.helper.EntityManagerHelper;

/**
 * A data access object (DAO) providing persistence and search support for
 * Category entities. Transaction control of the save(), update() and delete()
 * operations must be handled externally by senders of these methods or must be
 * manually added to each of these methods for data to be persisted to the JPA
 * datastore.
 * 
 * @see com.target.first.persistence.entities.Category
 * @author MyEclipse Persistence Tools
 */
public class CategoryDAO implements ICategoryDAO {
	// property constants
	public static final String DESCRIPTION = "description";

	private EntityManagerHelper emHelper = new EntityManagerHelper();

	private EntityManager getEntityManager() {
		return emHelper.getEntityManager();
	}

	/**
	 * Perform an initial save of a previously unsaved Category entity. All
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
	 * CategoryDAO.save(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Category entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(Category entity) {
		emHelper.log("saving Category instance", Level.INFO, null);
		try {
			getEntityManager().persist(entity);
			emHelper.log("save successful", Level.INFO, null);
		} catch (RuntimeException re) {
			emHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Delete a persistent Category entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * CategoryDAO.delete(entity);
	 * EntityManagerHelper.commit();
	 * entity = null;
	 * </pre>
	 * 
	 * @param entity
	 *            Category entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(Category entity) {
		emHelper.log("deleting Category instance", Level.INFO, null);
		try {
			entity = getEntityManager().getReference(Category.class, entity.getPk());
			getEntityManager().remove(entity);
			emHelper.log("delete successful", Level.INFO, null);
		} catch (RuntimeException re) {
			emHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved Category entity and return it or a copy of it
	 * to the sender. A copy of the Category entity parameter is returned when
	 * the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * 
	 * <pre>
	 * EntityManagerHelper.beginTransaction();
	 * entity = CategoryDAO.update(entity);
	 * EntityManagerHelper.commit();
	 * </pre>
	 * 
	 * @param entity
	 *            Category entity to update
	 * @return Category the persisted Category entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public Category update(Category entity) {
		emHelper.log("updating Category instance", Level.INFO, null);
		try {
			Category result = getEntityManager().merge(entity);
			emHelper.log("update successful", Level.INFO, null);
			return result;
		} catch (RuntimeException re) {
			emHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}

	public Category findById(Integer id) {
		emHelper.log("finding Category instance with id: " + id, Level.INFO, null);
		try {
			Category instance = getEntityManager().find(Category.class, id);
			return instance;
		} catch (RuntimeException re) {
			emHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * Find all Category entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Category property to query
	 * @param value
	 *            the property value to match
	 * @return List<Category> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<Category> findByProperty(String propertyName, final Object value) {
		emHelper.log("finding Category instance with property: " + propertyName + ", value: " + value, Level.INFO,
				null);
		try {
			final String queryString = "select model from Category model where model." + propertyName
					+ "= :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			return query.getResultList();
		} catch (RuntimeException re) {
			emHelper.log("find by property name failed", Level.SEVERE, re);
			throw re;
		}
	}

	public List<Category> findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	/**
	 * Find all Category entities.
	 * 
	 * @return List<Category> all Category entities
	 */
	@SuppressWarnings("unchecked")
	public List<Category> findAll() {
		emHelper.log("finding all Category instances", Level.INFO, null);
		try {
			final String queryString = "select model from Category model";
			Query query = getEntityManager().createQuery(queryString);
			return query.getResultList();
		} catch (RuntimeException re) {
			emHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}