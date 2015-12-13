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

import com.target.first.persistence.entities.Product;
import com.target.first.persistence.helper.EntityManagerHelper;

public class TestProduct {

	private static EntityManager manager;
	private EntityManagerHelper emHelper;

	private ProductDAO ProductDAO;
	private Product Product;
	private Query query;
	private List<Product> listProduct;

	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		manager = EasyMock.createMock(EntityManager.class);
		emHelper = EasyMock.createMock(EntityManagerHelper.class);
		Product = EasyMock.createMock(Product.class);
		query = EasyMock.createMock(Query.class);
		listProduct = EasyMock.createMock(List.class);
		
		ProductDAO = new ProductDAO(emHelper);
	}

	
	@After
	public void tearDown() {
		EasyMock.verify();
		manager = null;
		emHelper = null;
		Product = null;
		query = null;
		listProduct = null;
		
		ProductDAO = null;
	}

	
	@Test
	public void testSaveSuccess() {
		System.out.println("testSaveSuccess");

		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager);
		emHelper.log("saving Product instance", Level.INFO, null);
		manager.persist(EasyMock.isA(Product.class));
		emHelper.log("save successful", Level.INFO, null);
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(emHelper, manager);

		ProductDAO.save(Product);
		EasyMock.verify(emHelper, manager);
	}

	
	@Test
	public void testSaveException() throws RuntimeException {
		System.out.println("testSaveException");

		boolean exceptionThrown = false;
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager);
		emHelper.log("saving Product instance", Level.INFO, null);
		manager.persist(EasyMock.isA(Product.class));
		EasyMock.expectLastCall().andThrow(new RuntimeException());
		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall();

		EasyMock.replay(emHelper, manager);

		try {
			ProductDAO.save(Product);
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
		EasyMock.expect(manager.getReference(EasyMock.isA(Class.class), EasyMock.isA(Object.class))).andReturn(Product);
		emHelper.log("deleting Product instance", Level.INFO, null);
		manager.remove(EasyMock.isA(Product.class));
		EasyMock.expectLastCall().anyTimes();
		emHelper.log("delete successful", Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.replay(emHelper, manager);

		ProductDAO.delete(Product);
		EasyMock.verify(emHelper, manager);
	}

	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteException() throws RuntimeException {
		System.out.println("testDeleteException");

		boolean exceptionThrown = false;
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.getReference(EasyMock.isA(Class.class), EasyMock.isA(Object.class))).andReturn(Product);
		emHelper.log("deleting Product instance", Level.INFO, null);
		manager.remove(EasyMock.isA(Product.class));
		EasyMock.expectLastCall().andThrow(new RuntimeException());

		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall();

		EasyMock.replay(emHelper, manager);

		try {
			ProductDAO.delete(Product);
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
		emHelper.log("updating Product instance", Level.INFO, null);
		EasyMock.expect(manager.merge(EasyMock.isA(Product.class))).andReturn(Product);
		emHelper.log("update successful", Level.INFO, null);
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(emHelper, manager);

		ProductDAO.update(Product);
		EasyMock.verify(emHelper, manager);
	}

	
	@Test
	public void testUpdateException() throws RuntimeException {
		System.out.println("testUpdateException");

		boolean exceptionThrown = false;
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager);
		emHelper.log("updating Product instance", Level.INFO, null);
		EasyMock.expect(manager.merge(EasyMock.isA(Product.class))).andThrow(new RuntimeException());
		EasyMock.expectLastCall();
		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall();

		EasyMock.replay(emHelper, manager);

		try {
			ProductDAO.update(Product);
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
		emHelper.log("finding Product instance with id: " + findIdParam, Level.INFO, null);
		EasyMock.expectLastCall().atLeastOnce();

		EasyMock.expect(manager.find(EasyMock.isA(Class.class), EasyMock.isA(Integer.class))).andReturn(Product).anyTimes();
		EasyMock.replay(emHelper, manager);
		
		Product findProduct = ProductDAO.findById(findIdParam);
		assertEquals("Product returned must be equal to the mocked", findProduct, Product);
	}
	

	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void testFindByIdSException() {
		System.out.println("testFindByIdException");
		
		boolean exceptionThrown = false;
		Integer findIdParam = new Integer(10);
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager);
		EasyMock.expect(manager.find(EasyMock.isA(Class.class), EasyMock.isA(Integer.class))).andThrow(new RuntimeException());

		emHelper.log("finding Product instance with id: " + findIdParam, Level.INFO, null);
		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(emHelper, manager);
		
		try {
			Product findProduct = ProductDAO.findById(findIdParam);
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

		emHelper.log("finding Product instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andReturn(query).anyTimes();
		EasyMock.expect(query.setParameter("propertyValue", value)).andReturn(query);
		EasyMock.expectLastCall();
		EasyMock.expect(query.getResultList()).andReturn(listProduct);
		EasyMock.replay(emHelper, manager, query, listProduct);
		
		List<Product> tempList = ProductDAO.findByProperty(propertyName, value);
		assertEquals("Product returned must be equal to the mocked", tempList, listProduct);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testFindByPropertyException() {
		System.out.println("testFindByPropertyException");
		
		boolean exceptionThrown = false;
		String propertyName = "propertyName";
		Object value = new Object();

		emHelper.log("finding Product instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andThrow(new RuntimeException());
		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(emHelper, manager, query, listProduct);
		
		try {
			List<Product> tempList = ProductDAO.findByProperty(propertyName, value);
		} catch (Exception e) {
			exceptionThrown = true;
		}
		assertTrue("Expect to throw an Exception", exceptionThrown);
		EasyMock.verify(emHelper, manager, query, listProduct);
	}
	
	
	@Test
	public void tesstFindByProductId() {
		System.out.println("tesstFindByProductId");
		
		String ID = "id";
		Object value = "Test";

		emHelper.log("finding Product instance with property: " + ID + ", value: " + value, Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andReturn(query).anyTimes();
		EasyMock.expect(query.setParameter("propertyValue", value)).andReturn(query);
		EasyMock.expectLastCall();
		EasyMock.expect(query.getResultList()).andReturn(listProduct);
		EasyMock.replay(emHelper, manager, query, listProduct);
		
		List<Product> tempList = ProductDAO.findById("Test");
		assertEquals("List<Product> returned must be equal to the mocked", tempList, listProduct);
	}
	
	
	@Test
	public void tesstFindById() {
		System.out.println("tesstFindById");
		
		String NAME = "name";
		Object value = "ProductValue";

		emHelper.log("finding Product instance with property: " + NAME + ", value: " + value, Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andReturn(query).anyTimes();
		EasyMock.expect(query.setParameter("propertyValue", value)).andReturn(query);
		EasyMock.expectLastCall();
		EasyMock.expect(query.getResultList()).andReturn(listProduct);
		EasyMock.replay(emHelper, manager, query, listProduct);
		
		List<Product> tempList = ProductDAO.findByName("ProductValue");
		assertEquals("List<Product> returned must be equal to the mocked", tempList, listProduct);
	}
	
	
	@Test
	public void testFindAllSuccess() {
		System.out.println("testFindAllSuccess");
		
		emHelper.log("finding all Product instances", Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andReturn(query).anyTimes();
		EasyMock.expect(query.getResultList()).andReturn(listProduct);
		EasyMock.replay(emHelper, manager, query, listProduct);
		
		List<Product> tempList = ProductDAO.findAll();
		assertEquals("List<Product> returned must be equal to the mocked", tempList, listProduct);
	}
	
	
	@SuppressWarnings("unused")
	@Test
	public void testFindAllException() {
		System.out.println("testFindAllException");
		
		boolean exceptionThrown = false;
		emHelper.log("finding all Product instances", Level.INFO, null);
		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andThrow(new RuntimeException());
		EasyMock.replay(emHelper, manager, query, listProduct);
		try {
			List<Product> tempList = ProductDAO.findAll();
		} catch (Exception e) {
			exceptionThrown = true;
		}
		assertTrue("Expect to throw an Exception", exceptionThrown);
		EasyMock.verify(emHelper, manager, query, listProduct);
	}
}

