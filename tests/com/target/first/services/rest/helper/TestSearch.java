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
import com.target.first.persistence.daos.ProductDAO;
import com.target.first.persistence.entities.Category;
import com.target.first.persistence.entities.Price;
import com.target.first.persistence.entities.Product;

public class TestSearch {
	
	private static final String ID = "id";
	
	private Search search;
	private ProductDAO productDAO;
	private Product product;
	private List<Product> productList;
	
	
	@Before
	public void setUp() throws JsonProcessingException {
		productDAO = EasyMock.createMock(ProductDAO.class);
		
		Category category = new Category();
		category.setPk(1);
		category.setDescription("Dummy Category");

		Price price = new Price();
		price.setPk(1);
		price.setPrice(new BigDecimal("199.09"));
		
		product = new Product();
		product.setPk(1);
		product.setId(1000);
		product.setName("Dummy Product");
		product.setCategoryBean(category);
		product.setPriceBean(price);
		
		productList = new ArrayList<Product>();
		productList.add(product);
		
		search = new Search(productDAO);
	}
	
	@After
	public void tearDown() {
		productList = null;
		productDAO = null;
		search = null;
	}
	
	@Test
	public void testSearchForSingleIdSuccess() throws JSONException {
		System.out.println("testSearchForSingleIdSuccess");
		
		
		String id = "1000";
		EasyMock.expect(productDAO.findByProperty(ID, Integer.parseInt(id))).andReturn(productList);
		EasyMock.replay(productDAO);
		
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
		EasyMock.expect(productDAO.findByProperty(ID, Integer.parseInt(id))).andReturn(null);
		EasyMock.replay(productDAO);
		
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
		EasyMock.expect(productDAO.findByProperty(ID, Integer.parseInt(id))).andReturn(new ArrayList<>());
		EasyMock.replay(productDAO);
		
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
		
		EasyMock.expect(productDAO.findByProperty(ID, Integer.parseInt(id))).andReturn(productList);
		EasyMock.replay(productDAO);
		
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
		
		EasyMock.expect(productDAO.findByProperty(ID, Integer.parseInt(id))).andReturn(null);
		EasyMock.replay(productDAO);
		
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
		
		EasyMock.expect(productDAO.findByProperty(ID, Integer.parseInt(id))).andReturn(new ArrayList<>());
		EasyMock.replay(productDAO);
		
		Response resp = search.searchForIdList(ids);
		assertNotNull(resp);
		
		assertEquals("Expected to receive code 404 Not Found",404, resp.getStatus());
		assertNotNull("Expected not null", resp.getEntity());
		
		String jsonFrom = (String) resp.getEntity();
		JSONObject json = new JSONObject(jsonFrom);
		assertNotNull("Expecte to have a well formed JSON", json);
	}
	
	
	
}
