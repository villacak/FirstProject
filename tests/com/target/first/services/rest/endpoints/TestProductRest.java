package com.target.first.services.rest.endpoints;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.target.first.persistence.enums.HTTPEnums;
import com.target.first.services.rest.helper.Search;
import com.target.first.services.rest.utils.ExceptionsToJson;

public class TestProductRest {

	private Response response;
	private Search search;
	private ExceptionsToJson exceptionToJson;

	private ProductRest productRest;

	@Before
	public void setUp() {
		response = EasyMock.createMock(Response.class);
		search = EasyMock.createMock(Search.class);
		exceptionToJson = EasyMock.createMock(ExceptionsToJson.class);

		productRest = new ProductRest(search, response, exceptionToJson);
	}

	@After
	public void tearDown() {
		response = null;
		search = null;
		exceptionToJson = null;

		productRest = null;
	}

	@Test
	public void testRequestSingleIdSuccess() {
		System.out.println("testRequestSingleIdSuccess");

		EasyMock.expect(search.searchForSingleId(EasyMock.isA(String.class))).andReturn(response);
		EasyMock.replay(search);
		Response tempResp = productRest.productDetailsById("1000", "Something");
		EasyMock.verify(search);
		assertNotNull("Response", tempResp);
	}

	@Test
	public void testRequestSingleId404() {
		System.out.println("testRequestSingleId404");

		EasyMock.expect(exceptionToJson.parseExceptionReceived(null, HTTPEnums.CODE_404.getCode())).andReturn(response)
				.anyTimes();
		EasyMock.replay(exceptionToJson);
		Response tempResp = productRest.productDetailsById("", "Something");
		EasyMock.verify(exceptionToJson);
		assertNotNull("Response", tempResp);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRequestMultipleIdSuccess() {
		System.out.println("testRequestMultipleIdSuccess");

		final List<String> ids = new ArrayList<>();
		ids.add("1000");
		EasyMock.expect(search.searchForIdList(EasyMock.isA(List.class))).andReturn(response);
		EasyMock.replay(search);
		Response tempResp = productRest.productListDetailsById(ids, "Something");
		EasyMock.verify(search);
		assertNotNull("Response", tempResp);
	}

	@Test
	public void testRequestMultipleId404() {
		System.out.println("testRequestMultipleId404");

		final List<String> ids = new ArrayList<>();
		EasyMock.expect(exceptionToJson.parseExceptionReceived(null, HTTPEnums.CODE_404.getCode())).andReturn(response)
				.anyTimes();
		EasyMock.replay(exceptionToJson);
		Response tempResp = productRest.productListDetailsById(ids, "Something");
		EasyMock.verify(exceptionToJson);
		assertNotNull("Response", tempResp);
	}

}
