package com.target.first.persistence.daos;

import java.util.List;

import com.target.first.persistence.entities.Price;

/**
 * Interface for PriceDAO.
 * @author MyEclipse Persistence Tools
 */

public interface IPriceDAO {
		/**
	 Perform an initial save of a previously unsaved Price entity. 
	 All subsequent persist actions of this entity should use the #update() method.
	 This operation must be performed within the a database transaction context for the entity's data to be permanently saved to the persistence store, i.e., database. 
	 This method uses the {@link javax.persistence.EntityManager#persist(Object) EntityManager#persist} operation.
	 	 
	 * <pre> 
	 *   EntityManagerHelper.beginTransaction();
	 *   IPriceDAO.save(entity);
	 *   EntityManagerHelper.commit();
	 * </pre>
	   @param entity Price entity to persist
	  @throws RuntimeException when the operation fails
	 */
    public void save(Price entity);
    /**
	 Delete a persistent Price entity.
	  This operation must be performed 
	 within the a database transaction context for the entity's data to be
	 permanently deleted from the persistence store, i.e., database. 
	 This method uses the {@link javax.persistence.EntityManager#remove(Object) EntityManager#delete} operation.
	 	  
	 * <pre>
	 *   EntityManagerHelper.beginTransaction();
	 *   IPriceDAO.delete(entity);
	 *   EntityManagerHelper.commit();
	 *   entity = null;
	 * </pre>
	   @param entity Price entity to delete
	 @throws RuntimeException when the operation fails
	 */
    public void delete(Price entity);
   /**
	 Persist a previously saved Price entity and return it or a copy of it to the sender. 
	 A copy of the Price entity parameter is returned when the JPA persistence mechanism has not previously been tracking the updated entity. 
	 This operation must be performed within the a database transaction context for the entity's data to be permanently saved to the persistence
	 store, i.e., database. This method uses the {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge} operation.
	 	 
	 * <pre>
	 *   EntityManagerHelper.beginTransaction();
	 *   entity = IPriceDAO.update(entity);
	 *   EntityManagerHelper.commit();
	 * </pre>
	   @param entity Price entity to update
	 @return Price the persisted Price entity instance, may not be the same
	 @throws RuntimeException if the operation fails
	 */
	public Price update(Price entity);
	public Price findById( Integer id);
	 /**
	 * Find all Price entities with a specific property value.  
	 
	  @param propertyName the name of the Price property to query
	  @param value the property value to match
	  	  @return List<Price> found by query
	 */
	public List<Price> findByProperty(String propertyName, Object value
		);
	public List<Price> findByProductId(Object productId
		);
	public List<Price> findByPrice(Object price
		);
	/**
	 * Find all Price entities.
	  	  @return List<Price> all Price entities
	 */
	public List<Price> findAll(
		);	
}