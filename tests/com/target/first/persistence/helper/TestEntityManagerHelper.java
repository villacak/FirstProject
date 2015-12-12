package com.target.first.persistence.helper;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestEntityManagerHelper {

	private static Query query;
	private static EntityManagerFactory emf;
	private static EntityManager manager;
	private static ThreadLocal<EntityManager> threadLocal = new ThreadLocal<>();
	private EntityManagerHelper emHelper;
	private EntityTransaction eTransaction;
	private Logger logger;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		manager = EasyMock.createMock(EntityManager.class);
		emf = EasyMock.createMock(EntityManagerFactory.class);
		query = EasyMock.createMock(Query.class);
		manager = EasyMock.createMock(EntityManager.class);
		threadLocal = EasyMock.createMock(ThreadLocal.class);
		logger = EasyMock.createMock(Logger.class);
		eTransaction =EasyMock.createMock(EntityTransaction.class);
		
		emHelper = new EntityManagerHelper(emf, threadLocal, logger);
	}

	@After
	public void tearDown() {
		EasyMock.verify();
		manager = null;
		emf = null;
		query = null;
		manager = null;
		threadLocal = null;
		logger = null;
		eTransaction = null;
		emHelper = null;
	}

	@Test
	public void testGetEntitySuccessConnectionOpen() {
		System.out.println("testGetEntitySuccessConnectionOpen");

		EasyMock.expect(threadLocal.get()).andReturn(manager);
		EasyMock.expect(manager.isOpen()).andReturn(true);
		EasyMock.replay(emf, threadLocal, manager);

		EntityManager tempEntityManager = emHelper.getEntityManager();
		assertNotNull(tempEntityManager);
	}

	@Test
	public void testGetEntitySuccessEntityManagerClosed() {
		System.out.println("testGetEntitySuccessEntityManagerClosed");

		EasyMock.expect(threadLocal.get()).andReturn(manager);
		EasyMock.expect(emf.createEntityManager()).andReturn(manager);
		EasyMock.expect(manager.isOpen()).andReturn(false);
		threadLocal.set(manager);
		EasyMock.expectLastCall();
		EasyMock.replay(emf, threadLocal, manager);

		EntityManager tempEntityManager = emHelper.getEntityManager();
		assertNotNull(tempEntityManager);
	}

	@Test
	public void testGetEntitySuccessEntityManagerNull() {
		System.out.println("testGetEntitySuccessEntityManagerNull");
		EasyMock.expect(threadLocal.get()).andReturn(null);
		EasyMock.expect(manager.isOpen()).andReturn(false);
		EasyMock.expect(emf.createEntityManager()).andReturn(manager);
		threadLocal.set(manager);
		EasyMock.expectLastCall();
		EasyMock.replay(emf, threadLocal, manager);

		EntityManager tempEntityManager = emHelper.getEntityManager();
		assertNotNull(tempEntityManager);
	}

	@Test
	public void testCloseEntityManagerSuccess() {
		System.out.println("testCloseEntityManagerSuccess");

		EasyMock.expect(threadLocal.get()).andReturn(manager).anyTimes();
		threadLocal.set(null);
		manager.close();
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(threadLocal, manager);

		emHelper.closeEntityManager();
	}
	
	@Test
	public void testCloseEntityManagerSuccessNullEntityManager() {
		System.out.println("testCloseEntityManagerSuccessNullEntityManager");

		EasyMock.expect(threadLocal.get()).andReturn(null).anyTimes();
		threadLocal.set(null);
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(threadLocal, manager);

		emHelper.closeEntityManager();
		EasyMock.verify(threadLocal, manager);
	}
	
	@Test
	public void testBeginTRansaction() {	
		System.out.println("testBeginTRansaction");

		EasyMock.expect(threadLocal.get()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.isOpen()).andReturn(true).anyTimes();		
		EasyMock.expect(manager.getTransaction()).andReturn(eTransaction).anyTimes();
		eTransaction.begin();
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(manager, threadLocal, eTransaction);
		
		emHelper.beginTransaction();

	}
	
	@Test
	public void testCommit() {	
		System.out.println("testCommit");
		
		EasyMock.expect(threadLocal.get()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.isOpen()).andReturn(true).anyTimes();		
		EasyMock.expect(manager.getTransaction()).andReturn(eTransaction).anyTimes();
		eTransaction.commit();
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(manager, threadLocal, eTransaction);
		
		emHelper.commit();
	}
	
	@Test
	public void testRollback() {
		System.out.println("testCommit");
		
		EasyMock.expect(threadLocal.get()).andReturn(manager).anyTimes();
		EasyMock.expect(manager.isOpen()).andReturn(true).anyTimes();		
		EasyMock.expect(manager.getTransaction()).andReturn(eTransaction).anyTimes();
		eTransaction.rollback();
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(manager, threadLocal, eTransaction);
		
		emHelper.rollback();
	}
	
	
	
	@Test
	public void testCreateQuery() {
		System.out.println("testCreateQuery");
		EasyMock.expect(threadLocal.get()).andReturn(manager);
		EasyMock.expect(manager.isOpen()).andReturn(true);
		EasyMock.expect(manager.createQuery(EasyMock.isA(String.class))).andReturn(query);
		EasyMock.replay(emf, threadLocal, manager, query);
		
		Query tempQry = emHelper.createQuery("Test");
		assertEquals("Received Query obj has to be equal to the mocked one.", tempQry, query);
	}
	
	@Test
	public void testLog() {
		logger.log(EasyMock.isA(Level.class), EasyMock.isA(String.class), EasyMock.isA(Throwable.class));
		EasyMock.expectLastCall().atLeastOnce();
		EasyMock.replay(logger);
		
		emHelper.log("Test", Level.FINE, new Throwable());
		EasyMock.verify(logger);
	}
}

