package com.target.first.services.rest.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.first.persistence.daos.ProductDAO;
import com.target.first.persistence.entities.Product;
import com.target.first.persistence.enums.HTTPEnums;
import com.target.first.services.rest.utils.ExceptionsToJson;
import com.target.first.services.rest.utils.ResponseCreator;

/**
 * Method that make the search for one single ID or for an id list
 * 
 * @author Klaus Villaca
 *
 */
public class Search {

	private static final String ID = "id";

	/**
	 * Search using the persistence layer for all records with the same Id
	 * 
	 * @param id
	 * @return
	 */
	public Response searchForSingleId(final String id) {
		Response response = null;
		String singleValueToReturn = null;
		try {
			final ProductDAO productDAO = new ProductDAO();
			final List<Product> productListTemp = productDAO.findByProperty(ID, Integer.parseInt(id));
			if (productListTemp != null && productListTemp.size() > 0) {
				final List<Product> productList = new ArrayList<>();
				for (Product p : productListTemp) {
					productList.add(p);
				}
				final ObjectMapper mapper = new ObjectMapper();
				singleValueToReturn = mapper.writeValueAsString(productList);
			} else {
				final ExceptionsToJson exceptionToJson = new ExceptionsToJson();
				singleValueToReturn = exceptionToJson.parseExceptionReceivedToString(null,
						HTTPEnums.CODE_404.getCode());
			}
		} catch (Exception e) {
			final ExceptionsToJson exceptionToJson = new ExceptionsToJson();
			if (e instanceof NullPointerException) {
				singleValueToReturn = exceptionToJson.parseExceptionReceivedToString(e, HTTPEnums.CODE_404.getCode());
			} else {
				singleValueToReturn = exceptionToJson.parseExceptionReceivedToString(e, HTTPEnums.CODE_417.getCode());
			}
		}
		ResponseCreator createResponse = new ResponseCreator();
		response = createResponse.wrapResponseWithRightCode(singleValueToReturn);
		return response;
	}

	/**
	 * Search using the persistence layer for all records from an id list
	 * 
	 * @param ids
	 * @return
	 */
	public Response searchForIdList(final List<String> ids) {
		Response response = null;
		String multipleValuesToReturn = null;
		Map<String, List<Product>> multipleIdsMap = new HashMap<>();
		try {
			final ProductDAO productDAO = new ProductDAO();
			for (String id : ids) {
				final List<Product> productVectorList = productDAO.findByProperty(ID, Integer.parseInt(id));

				// It's needed to extract from Vetor to ArrayList to don't have
				// json parse problems.
				if (productVectorList != null && productVectorList.size() > 0) {
					final List<Product> productList = new ArrayList<>();
					for (Product p : productVectorList) {
						productList.add(p);
					}
					if (productList != null && productList.size() > 0) {
						multipleIdsMap.put(id, productList);
					}
				}
			}
			
			if (multipleIdsMap.size() == 0) {
				final ExceptionsToJson exceptionToJson = new ExceptionsToJson();
				multipleValuesToReturn = exceptionToJson.parseExceptionReceivedToString(null,
						HTTPEnums.CODE_404.getCode());
			}
			
			final ObjectMapper mapper = new ObjectMapper();
			multipleValuesToReturn = mapper.writeValueAsString(multipleIdsMap);
		} catch (Exception e) {
			final ExceptionsToJson exceptionToJson = new ExceptionsToJson();
			if (e instanceof NullPointerException) {
				multipleValuesToReturn = exceptionToJson.parseExceptionReceivedToString(e,
						HTTPEnums.CODE_404.getCode());
			} else {
				multipleValuesToReturn = exceptionToJson.parseExceptionReceivedToString(e,
						HTTPEnums.CODE_417.getCode());
			}
		}
		
		ResponseCreator createResponse = new ResponseCreator();
		response = createResponse.wrapResponseWithRightCode(multipleValuesToReturn);
		return response;
	}
}
