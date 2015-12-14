package com.target.first.services.rest.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.target.first.persistence.daos.PriceDAO;
import com.target.first.persistence.entities.Price;

public class TestSearchPrice {
	
	private SearchPrice search;
	private PriceDAO priceDAO;
	private List<Price> priceList;
	
	
	@Before
	public void setUp() throws JsonProcessingException {
		priceDAO = EasyMock.createMock(PriceDAO.class);

		Price price = new Price();
		price.setPk(1);
		price.setPrice(new BigDecimal("199.09"));
		price.setProductId(1000);
		
		priceList = new ArrayList<>();
		priceList.add(price);
		
		search = new SearchPrice(priceDAO);
	}
	
	@After
	public void tearDown() {
		priceList = null;
		priceDAO = null;
		search = null;
	}
	
	@Test
	public void testSearchForSingleIdSuccess() throws JSONException {
		System.out.println("testSearchForSingleIdSuccess");
		
		
		String id = "1000";
		EasyMock.expect(priceDAO.findByProductId(Integer.parseInt(id))).andReturn(priceList);
		EasyMock.replay(priceDAO);
		
		Response resp = search.searchForSingleId(id);
		assertNotNull(resp);
		
		assertEquals("Expected to receive code 200 ok",200, resp.getStatus());
		assertNotNull("Expected not null", resp.getEntity());
		
		String jsonFrom = (String) resp.getEntity();
		JSONArray json = new JSONArray(jsonFrom);
		assertNotNull("Expecte to have a well formed JSON", json);
	}
	
	
	@Test
	public void testSearchForSingleIdSuccessNullReturn() throws JSONException {
		System.out.println("testSearchForSingleIdSuccessNullReturn");
		
		
		String id = "1000";
		EasyMock.expect(priceDAO.findByProductId(Integer.parseInt(id))).andReturn(null);
		EasyMock.replay(priceDAO);
		
		Response resp = search.searchForSingleId(id);
		assertNotNull(resp);
		
		assertEquals("Expected to receive code 404 Not Found",404, resp.getStatus());
		assertNotNull("Expected not null", resp.getEntity());
		
		String jsonFrom = (String) resp.getEntity();
		JSONObject json = new JSONObject(jsonFrom);
		assertNotNull("Expecte to have a well formed JSON", json);
	}

	@Test
	public void testSearchForSingleIdSuccessListEmptyReturn() throws JSONException {
		System.out.println("testSearchForSingleIdSuccessListEmptyReturn");
		
		
		String id = "1000";
		EasyMock.expect(priceDAO.findByProductId(Integer.parseInt(id))).andReturn(new ArrayList<>());
		EasyMock.replay(priceDAO);
		
		Response resp = search.searchForSingleId(id);
		assertNotNull(resp);
		
		assertEquals("Expected to receive code 404 Not Found",404, resp.getStatus());
		assertNotNull("Expected not null", resp.getEntity());
		
		String jsonFrom = (String) resp.getEntity();
		JSONObject json = new JSONObject(jsonFrom);
		assertNotNull("Expecte to have a well formed JSON", json);
	}
	
	
	
	
	@Test
	public void testSearchForListIdSuccess() throws JSONException {
		System.out.println("testSearchForListIdSuccess");
		
		String id = "1000";
		List<String> ids = new ArrayList<>();
		ids.add(id);
		
		EasyMock.expect(priceDAO.findByProductId(Integer.parseInt(id))).andReturn(priceList);
		EasyMock.replay(priceDAO);
		
		Response resp = search.searchForIdList(ids);
		assertNotNull(resp);
		
		assertEquals("Expected to receive code 200 ok",200, resp.getStatus());
		assertNotNull("Expected not null", resp.getEntity());
		
		String jsonFrom = (String) resp.getEntity();
		JSONObject json = new JSONObject(jsonFrom);
		assertNotNull("Expecte to have a well formed JSON", json);
	}
	
	
	@Test
	public void testSearchForListIdSuccessNullReturn() throws JSONException {
		System.out.println("testSearchForListIdSuccessNullReturn");
		
		
		String id = "1000";
		List<String> ids = new ArrayList<>();
		ids.add(id);
		
		EasyMock.expect(priceDAO.findByProductId(Integer.parseInt(id))).andReturn(null);
		EasyMock.replay(priceDAO);
		
		Response resp = search.searchForIdList(ids);
		assertNotNull(resp);
		
		assertEquals("Expected to receive code 404 Not Found",404, resp.getStatus());
		assertNotNull("Expected not null", resp.getEntity());
		
		String jsonFrom = (String) resp.getEntity();
		JSONObject json = new JSONObject(jsonFrom);
		assertNotNull("Expecte to have a well formed JSON", json);
	}

	@Test
	public void testSearchForListIdSuccessListEmptyReturn() throws JSONException {
		System.out.println("testSearchForListIdSuccessListEmptyReturn");
		
		
		String id = "1000";
		List<String> ids = new ArrayList<>();
		ids.add(id);
		
		EasyMock.expect(priceDAO.findByProductId(Integer.parseInt(id))).andReturn(new ArrayList<>());
		EasyMock.replay(priceDAO);
		
		Response resp = search.searchForIdList(ids);
		assertNotNull(resp);
		
		assertEquals("Expected to receive code 404 Not Found",404, resp.getStatus());
		assertNotNull("Expected not null", resp.getEntity());
		
		String jsonFrom = (String) resp.getEntity();
		JSONObject json = new JSONObject(jsonFrom);
		assertNotNull("Expecte to have a well formed JSON", json);
	}
}
