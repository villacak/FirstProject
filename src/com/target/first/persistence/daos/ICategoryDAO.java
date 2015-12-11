package com.target.first.persistence.daos;

import java.util.List;


import com.target.first.persistence.entities.Category;

/**
 * Interface for CategoryDAO.
 * @author MyEclipse Persistence Tools
 */

public interface ICategoryDAO {
		/**
	 Perform an initial save of a previously unsaved Category entity. 
	 All subsequent persist actions of this entity should use the #update() method.
	 This operation must be performed within the a database transaction context for the entity's data to be permanently saved to the persistence store, i.e., database. 
	 This method uses the {@link javax.persistence.EntityManager#persist(Object) EntityManager#persist} operation.
	 	 
	 * <pre> 
	 *   EntityManagerHelper.beginTransaction();
	 *   ICategoryDAO.save(entity);
	 *   EntityManagerHelper.commit();
	 * </pre>
	   @param entity Category entity to persist
	  @throws RuntimeException when the operation fails
	 */
    public void save(Category entity);
    /**
	 Delete a persistent Category entity.
	  This operation must be performed 
	 within the a database transaction context for the entity's data to be
	 permanently deleted from the persistence store, i.e., database. 
	 This method uses the {@link javax.persistence.EntityManager#remove(Object) EntityManager#delete} operation.
	 	  
	 * <pre>
	 *   EntityManagerHelper.beginTransaction();
	 *   ICategoryDAO.delete(entity);
	 *   EntityManagerHelper.commit();
	 *   entity = null;
	 * </pre>
	   @param entity Category entity to delete
	 @throws RuntimeException when the operation fails
	 */
    public void delete(Category entity);
   /**
	 Persist a previously saved Category entity and return it or a copy of it to the sender. 
	 A copy of the Category entity parameter is returned when the JPA persistence mechanism has not previously been tracking the updated entity. 
	 This operation must be performed within the a database transaction context for the entity's data to be permanently saved to the persistence
	 store, i.e., database. This method uses the {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge} operation.
	 	 
	 * <pre>
	 *   EntityManagerHelper.beginTransaction();
	 *   entity = ICategoryDAO.update(entity);
	 *   EntityManagerHelper.commit();
	 * </pre>
	   @param entity Category entity to update
	 @return Category the persisted Category entity instance, may not be the same
	 @throws RuntimeException if the operation fails
	 */
	public Category update(Category entity);
	public Category findById( Integer id);
	 /**
	 * Find all Category entities with a specific property value.  
	 
	  @param propertyName the name of the Category property to query
	  @param value the property value to match
	  	  @return List<Category> found by query
	 */
	public List<Category> findByProperty(String propertyName, Object value
		);
	public List<Category> findByDescription(Object description
		);
	/**
	 * Find all Category entities.
	  	  @return List<Category> all Category entities
	 */
	public List<Category> findAll(
		);	
}