package com.target.first.services.rest.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.first.persistence.daos.ProductDAO;
import com.target.first.persistence.entities.Product;
import com.target.first.persistence.enums.HTTPEnums;
import com.target.first.services.rest.utils.ExceptionsToJson;

public class Search {

	private static final String ID = "id";
	
	/**
	 * Search using the persistence layer for all records with the same Id
	 * 
	 * @param id
	 * @return
	 */
	public String searchForSingleId(final String id) {
		String singleValueToReturn = null;
		try {
			final ProductDAO productDAO = new ProductDAO();
			final List<Product> productListTemp = productDAO.findByProperty(ID, Integer.parseInt(id));
			if (productListTemp != null && productListTemp.size() > 0) {
				final List<Product> productList = new ArrayList<>();
				for(Product p: productListTemp) {
					productList.add(p);
				}
				final ObjectMapper mapper = new ObjectMapper();
				singleValueToReturn = mapper.writeValueAsString(productList);				
			} else {
				singleValueToReturn = ExceptionsToJson.parseExceptionReceivedToString(null, HTTPEnums.CODE_404.getCode());
			}
		} catch (Exception e) {
			if (e instanceof NullPointerException) {
				singleValueToReturn = ExceptionsToJson.parseExceptionReceivedToString(e, HTTPEnums.CODE_404.getCode());
			} else {
				singleValueToReturn = ExceptionsToJson.parseExceptionReceivedToString(e, HTTPEnums.CODE_417.getCode());
			}
		}
		return singleValueToReturn;
	}
	
	
	/**
	 * Search using the persistence layer for all records from an id list
	 * 
	 * @param ids
	 * @return
	 */
	public String searchForIdList(final List<String> ids) {
		String multipleValuesToReturn = null;
		Map<String, List<Product>> multipleIdsMap = new HashMap<>();
		try {
			final ProductDAO productDAO = new ProductDAO();
			for(String id : ids) {
				final List<Product> productList = productDAO.findByProperty(ID, id);
				if (productList != null && productList.size() > 0) {
					multipleIdsMap.put(id, productList);
				}
			}
			final ObjectMapper mapper = new ObjectMapper();
			multipleValuesToReturn = mapper.writeValueAsString(multipleIdsMap);
		} catch (Exception e) {
			if (e instanceof NullPointerException) {
				multipleValuesToReturn = ExceptionsToJson.parseExceptionReceivedToString(e, HTTPEnums.CODE_404.getCode());
			} else {
				multipleValuesToReturn = ExceptionsToJson.parseExceptionReceivedToString(e, HTTPEnums.CODE_417.getCode());
			}
		}
		return multipleValuesToReturn;
	}
}
