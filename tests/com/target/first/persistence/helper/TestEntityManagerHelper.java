package com.target.first.persistence.helper;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		manager = EasyMock.createMock(EntityManager.class);
		emf = EasyMock.createMock(EntityManagerFactory.class);
		query = EasyMock.createMock(Query.class);
		manager = EasyMock.createMock(EntityManager.class);
		threadLocal = EasyMock.createMock(ThreadLocal.class);

		emHelper = new EntityManagerHelper(emf, threadLocal);
	}

	@After
	public void tearDown() {
		EasyMock.verify();
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
}
