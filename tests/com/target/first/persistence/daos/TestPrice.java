package com.target.first.persistence.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.target.first.persistence.entities.Price;
import com.target.first.persistence.helper.EntityManagerHelper;

public class TestPrice {

	private static EntityManager manager;
	private EntityManagerHelper emHelper;

	private PriceDAO priceDAO;
	private Price price;
	private Query query;
	private List<Price> listPrice;

	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		manager = EasyMock.createMock(EntityManager.class);
		emHelper = EasyMock.createMock(EntityManagerHelper.class);
		price = EasyMock.createMock(Price.class);
		query = EasyMock.createMock(Query.class);
		listPrice = EasyMock.createMock(List.class);
		
		priceDAO = new PriceDAO(emHelper);
	}

	
	@After
	public void tearDown() {
		EasyMock.verify();
		manager = null;
		emHelper = null;
	}

	
	@Test
	public void testSaveSuccess() {
		System.out.println("testSaveSuccess");

		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager);
		emHelper.log("saving Price instance", Level.INFO, null);
		manager.persist(EasyMock.isA(Price.class));
		emHelper.log("save successful", Level.INFO, null);
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(emHelper, manager);

		priceDAO.save(price);
		EasyMock.verify(emHelper, manager);
	}

	
	@Test
	public void testSaveException() throws RuntimeException {
		System.out.println("testSaveException");

		boolean exceptionThrown = false;
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager);
		emHelper.log("saving Price instance", Level.INFO, null);
		manager.persist(EasyMock.isA(Price.class));
		EasyMock.expectLastCall().andThrow(new RuntimeException());
		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall();

		EasyMock.replay(emHelper, manager);

		try {
			priceDAO.save(price);
		} catch (Exception e) {
			exceptionThrown = true;
		}
		assertTrue("Expect to throw an Exception", exceptionThrown);
		EasyMock.verify(emHelper, manager);
	}

	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteSuccess() {
		System.out.println("testDeleteSuccess");

		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.getReference(EasyMock.isA(Class.class), EasyMock.isA(Object.class))).andReturn(price);
		emHelper.log("deleting Price instance", Level.INFO, null);
		manager.remove(EasyMock.isA(Price.class));
		EasyMock.expectLastCall().anyTimes();
		emHelper.log("delete successful", Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.replay(emHelper, manager);

		priceDAO.delete(price);
		EasyMock.verify(emHelper, manager);
	}

	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteException() throws RuntimeException {
		System.out.println("testDeleteException");

		boolean exceptionThrown = false;
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.getReference(EasyMock.isA(Class.class), EasyMock.isA(Object.class))).andReturn(price);
		emHelper.log("deleting Price instance", Level.INFO, null);
		manager.remove(EasyMock.isA(Price.class));
		EasyMock.expectLastCall().andThrow(new RuntimeException());

		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall();

		EasyMock.replay(emHelper, manager);

		try {
			priceDAO.delete(price);
		} catch (Exception e) {
			exceptionThrown = true;
		}
		assertTrue("Expect to throw an Exception", exceptionThrown);
		EasyMock.verify(emHelper, manager);
	}
	
	
	@Test
	public void testUpdateSuccess() {
		System.out.println("testUpdateSuccess");

		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager);
		emHelper.log("updating Price instance", Level.INFO, null);
		EasyMock.expect(manager.merge(EasyMock.isA(Price.class))).andReturn(price);
		emHelper.log("update successful", Level.INFO, null);
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(emHelper, manager);

		priceDAO.update(price);
		EasyMock.verify(emHelper, manager);
	}

	
	@Test
	public void testUpdateException() throws RuntimeException {
		System.out.println("testUpdateException");

		boolean exceptionThrown = false;
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager);
		emHelper.log("updating Price instance", Level.INFO, null);
		EasyMock.expect(manager.merge(EasyMock.isA(Price.class))).andThrow(new RuntimeException());
		EasyMock.expectLastCall();
		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall();

		EasyMock.replay(emHelper, manager);

		try {
			priceDAO.update(price);
		} catch (Exception e) {
			exceptionThrown = true;
		}
		assertTrue("Expect to throw an Exception", exceptionThrown);
		EasyMock.verify(emHelper, manager);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindByIdSuccess() {
		System.out.println("testFindByIdSuccess");
		
		Integer findIdParam = new Integer(10);
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager);
		emHelper.log("finding Price instance with id: " + findIdParam, Level.INFO, null);
		EasyMock.expectLastCall().atLeastOnce();

		EasyMock.expect(manager.find(EasyMock.isA(Class.class), EasyMock.isA(Integer.class))).andReturn(price).anyTimes();
		EasyMock.replay(emHelper, manager);
		
		Price findPrice = priceDAO.findById(findIdParam);
		assertEquals("Price returned must be equal to the mocked", findPrice, price);
	}
	

	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void testFindByIdSException() {
		System.out.println("testFindByIdException");
		
		boolean exceptionThrown = false;
		Integer findIdParam = new Integer(10);
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager);
		EasyMock.expect(manager.find(EasyMock.isA(Class.class), EasyMock.isA(Integer.class))).andThrow(new RuntimeException());

		emHelper.log("finding Price instance with id: " + findIdParam, Level.INFO, null);
		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(emHelper, manager);
		
		try {
			Price findPrice = priceDAO.findById(findIdParam);
		} catch (Exception e) {
			exceptionThrown = true;
		}
		assertTrue("Expect to throw an Exception", exceptionThrown);
		EasyMock.verify(emHelper, manager);
	}
	
	
	@Test
	public void testFindByPropertySuccess() {
		System.out.println("testFindByPropertySuccess");
		
		String propertyName = "propertyName";
		Object value = new Object();

		emHelper.log("finding Price instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andReturn(query).anyTimes();
		EasyMock.expect(query.setParameter("propertyValue", value)).andReturn(query);
		EasyMock.expectLastCall();
		EasyMock.expect(query.getResultList()).andReturn(listPrice);
		EasyMock.replay(emHelper, manager, query, listPrice);
		
		List<Price> tempList = priceDAO.findByProperty(propertyName, value);
		assertEquals("Price returned must be equal to the mocked", tempList, listPrice);
	}
	
	
	@SuppressWarnings("unused")
	@Test
	public void testFindByPropertyException() {
		System.out.println("testFindByPropertyException");
		
		boolean exceptionThrown = false;
		String propertyName = "propertyName";
		Object value = new Object();

		emHelper.log("finding Price instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andThrow(new RuntimeException());
		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(emHelper, manager, query, listPrice);
		
		try {
			List<Price> tempList = priceDAO.findByProperty(propertyName, value);
		} catch (Exception e) {
			exceptionThrown = true;
		}
		assertTrue("Expect to throw an Exception", exceptionThrown);
		EasyMock.verify(emHelper, manager, query, listPrice);
	}
	
	
	@Test
	public void tesstFindByProductId() {
		System.out.println("tesstFindByProductId");
		
		String PRODUCT_ID = "productId";
		Object value = "Test";

		emHelper.log("finding Price instance with property: " + PRODUCT_ID + ", value: " + value, Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andReturn(query).anyTimes();
		EasyMock.expect(query.setParameter("propertyValue", value)).andReturn(query);
		EasyMock.expectLastCall();
		EasyMock.expect(query.getResultList()).andReturn(listPrice);
		EasyMock.replay(emHelper, manager, query, listPrice);
		
		List<Price> tempList = priceDAO.findByProductId("Test");
		assertEquals("List<Price> returned must be equal to the mocked", tempList, listPrice);
	}
	
	
	@Test
	public void tesstFindByPrice() {
		System.out.println("tesstFindByPrice");
		
		String PRICE = "price";
		Object value = "priceValue";

		emHelper.log("finding Price instance with property: " + PRICE + ", value: " + value, Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andReturn(query).anyTimes();
		EasyMock.expect(query.setParameter("propertyValue", value)).andReturn(query);
		EasyMock.expectLastCall();
		EasyMock.expect(query.getResultList()).andReturn(listPrice);
		EasyMock.replay(emHelper, manager, query, listPrice);
		
		List<Price> tempList = priceDAO.findByPrice("priceValue");
		assertEquals("List<Price> returned must be equal to the mocked", tempList, listPrice);
	}
	
	@Test
	public void testFindAllSuccess() {
		System.out.println("testFindAllSuccess");
		
		emHelper.log("finding all Price instances", Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andReturn(query).anyTimes();
		EasyMock.expect(query.getResultList()).andReturn(listPrice);
		EasyMock.replay(emHelper, manager, query, listPrice);
		
		List<Price> tempList = priceDAO.findAll();
		assertEquals("List<Price> returned must be equal to the mocked", tempList, listPrice);
	}
	
	
	@SuppressWarnings("unused")
	@Test
	public void testFindAllException() {
		System.out.println("testFindAllException");
		
		boolean exceptionThrown = false;
		emHelper.log("finding all Price instances", Level.INFO, null);
		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andThrow(new RuntimeException());
		EasyMock.replay(emHelper, manager, query, listPrice);
		try {
			List<Price> tempList = priceDAO.findAll();
		} catch (Exception e) {
			exceptionThrown = true;
		}
		assertTrue("Expect to throw an Exception", exceptionThrown);
		EasyMock.verify(emHelper, manager, query, listPrice);
	}
}

