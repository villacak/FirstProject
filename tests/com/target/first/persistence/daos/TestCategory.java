package com.target.first.persistence.daos;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.target.first.persistence.entities.Category;
import com.target.first.persistence.helper.EntityManagerHelper;

public class TestCategory {

	private static EntityManager manager;
	private EntityManagerHelper emHelper;

	private CategoryDAO categoryDAO;
	private Category category;
	private Query query;
	private List<Category> listCategory;

	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		manager = EasyMock.createMock(EntityManager.class);
		emHelper = EasyMock.createMock(EntityManagerHelper.class);
		category = EasyMock.createMock(Category.class);
		query = EasyMock.createMock(Query.class);
		listCategory = EasyMock.createMock(List.class);
		
		categoryDAO = new CategoryDAO(emHelper);
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
		emHelper.log("saving Category instance", Level.INFO, null);
		manager.persist(EasyMock.isA(Category.class));
		emHelper.log("save successful", Level.INFO, null);
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(emHelper, manager);

		categoryDAO.save(category);
		EasyMock.verify(emHelper, manager);
	}

	
	@Test
	public void testSaveException() throws RuntimeException {
		System.out.println("testSaveException");

		boolean exceptionThrown = false;
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager);
		emHelper.log("saving Category instance", Level.INFO, null);
		manager.persist(EasyMock.isA(Category.class));
		EasyMock.expectLastCall().andThrow(new RuntimeException());
		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall();

		EasyMock.replay(emHelper, manager);

		try {
			categoryDAO.save(category);
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
		EasyMock.expect(manager.getReference(EasyMock.isA(Class.class), EasyMock.isA(Object.class))).andReturn(category);
		emHelper.log("deleting Category instance", Level.INFO, null);
		manager.remove(EasyMock.isA(Category.class));
		EasyMock.expectLastCall().anyTimes();
		emHelper.log("delete successful", Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.replay(emHelper, manager);

		categoryDAO.delete(category);
		EasyMock.verify(emHelper, manager);
	}

	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteException() throws RuntimeException {
		System.out.println("testDeleteException");

		boolean exceptionThrown = false;
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.getReference(EasyMock.isA(Class.class), EasyMock.isA(Object.class))).andReturn(category);
		emHelper.log("deleting Category instance", Level.INFO, null);
		manager.remove(EasyMock.isA(Category.class));
		EasyMock.expectLastCall().andThrow(new RuntimeException());

		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall();

		EasyMock.replay(emHelper, manager);

		try {
			categoryDAO.delete(category);
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
		emHelper.log("updating Category instance", Level.INFO, null);
		EasyMock.expect(manager.merge(EasyMock.isA(Category.class))).andReturn(category);
		emHelper.log("update successful", Level.INFO, null);
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(emHelper, manager);

		categoryDAO.update(category);
		EasyMock.verify(emHelper, manager);
	}

	
	@Test
	public void testUpdateException() throws RuntimeException {
		System.out.println("testUpdateException");

		boolean exceptionThrown = false;
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager);
		emHelper.log("updating Category instance", Level.INFO, null);
		EasyMock.expect(manager.merge(EasyMock.isA(Category.class))).andThrow(new RuntimeException());
		EasyMock.expectLastCall();
		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall();

		EasyMock.replay(emHelper, manager);

		try {
			categoryDAO.update(category);
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
		emHelper.log("finding Category instance with id: " + findIdParam, Level.INFO, null);
		EasyMock.expectLastCall().atLeastOnce();

		EasyMock.expect(manager.find(EasyMock.isA(Class.class), EasyMock.isA(Integer.class))).andReturn(category).anyTimes();
		EasyMock.replay(emHelper, manager);
		
		Category findCategory = categoryDAO.findById(findIdParam);
		assertEquals("Category returned must be equal to the mocked", findCategory, category);
	}
	

	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void testFindByIdSException() {
		System.out.println("testFindByIdException");
		
		boolean exceptionThrown = false;
		Integer findIdParam = new Integer(10);
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager);
		EasyMock.expect(manager.find(EasyMock.isA(Class.class), EasyMock.isA(Integer.class))).andThrow(new RuntimeException());

		emHelper.log("finding Category instance with id: " + findIdParam, Level.INFO, null);
		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(emHelper, manager);
		
		try {
			Category findCategory = categoryDAO.findById(findIdParam);
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

		emHelper.log("finding Category instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andReturn(query).anyTimes();
		EasyMock.expect(query.setParameter("propertyValue", value)).andReturn(query);
		EasyMock.expectLastCall();
		EasyMock.expect(query.getResultList()).andReturn(listCategory);
		EasyMock.replay(emHelper, manager, query, listCategory);
		
		List<Category> tempList = categoryDAO.findByProperty(propertyName, value);
		assertEquals("Category returned must be equal to the mocked", tempList, listCategory);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testFindByPropertyException() {
		System.out.println("testFindByPropertyException");
		
		boolean exceptionThrown = false;
		String propertyName = "propertyName";
		Object value = new Object();

		emHelper.log("finding Category instance with property: " + propertyName + ", value: " + value, Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andThrow(new RuntimeException());
		emHelper.log(EasyMock.isA(String.class), EasyMock.isA(Level.class), EasyMock.isA(RuntimeException.class));
		EasyMock.expectLastCall().anyTimes();
		EasyMock.replay(emHelper, manager, query, listCategory);
		
		try {
			List<Category> tempList = categoryDAO.findByProperty(propertyName, value);
		} catch (Exception e) {
			exceptionThrown = true;
		}
		assertTrue("Expect to throw an Exception", exceptionThrown);
		EasyMock.verify(emHelper, manager, query, listCategory);
	}
	
	
	@Test
	public void tesstFindByDescription() {
		System.out.println("tesstFindByDescription");
		
		String DESCRIPTION = "description";
		Object value = "Test";

		emHelper.log("finding Category instance with property: " + DESCRIPTION + ", value: " + value, Level.INFO, null);
		EasyMock.expectLastCall();
		EasyMock.expect(emHelper.getEntityManager()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andReturn(query).anyTimes();
		EasyMock.expect(query.setParameter("propertyValue", value)).andReturn(query);
		EasyMock.expectLastCall();
		EasyMock.expect(query.getResultList()).andReturn(listCategory);
		EasyMock.replay(emHelper, manager, query, listCategory);
		
		List<Category> tempList = categoryDAO.findByDescription("Test");
		assertEquals("Category returned must be equal to the mocked", tempList, listCategory);
	}
	
	
}
