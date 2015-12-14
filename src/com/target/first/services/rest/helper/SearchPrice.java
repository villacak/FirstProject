package com.target.first.services.rest.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.first.persistence.daos.PriceDAO;
import com.target.first.persistence.entities.Price;
import com.target.first.persistence.enums.HTTPEnums;
import com.target.first.services.rest.utils.ExceptionsToJson;
import com.target.first.services.rest.utils.ResponseCreator;

/**
 * Method that make the search for one single ID or for an id list
 * 
 * @author Klaus Villaca
 *
 */
public class SearchPrice {
	
	private PriceDAO priceDAO;
	

	
	public SearchPrice() {
		priceDAO = new PriceDAO();
	}
	
	public SearchPrice(PriceDAO productDAO) {
		this.priceDAO = productDAO;
	}
	
	
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
			final List<Price> priceListTemp = priceDAO.findByProductId(Integer.parseInt(id));
			if (priceListTemp != null && priceListTemp.size() > 0) {
				final List<Price> priceList = new ArrayList<>();
				for (Price p : priceListTemp) {
					priceList.add(p);
				}
				final ObjectMapper mapper = new ObjectMapper();
				singleValueToReturn = mapper.writeValueAsString(priceList);
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
		Map<String, List<Price>> multipleIdsMap = new HashMap<>();
		try {
			for (String id : ids) {
				final List<Price> priceVectorList = priceDAO.findByProductId(Integer.parseInt(id));

				// It's needed to extract from Vetor to ArrayList to don't have
				// json parse problems.
				if (priceVectorList != null && priceVectorList.size() > 0) {
					final List<Price> priceList = new ArrayList<>();
					for (Price p : priceVectorList) {
						priceList.add(p);
					}
					if (priceList != null && priceList.size() > 0) {
						multipleIdsMap.put(id, priceList);
					}
				}
			}
			
			if (multipleIdsMap.size() == 0) {
				final ExceptionsToJson exceptionToJson = new ExceptionsToJson();
				multipleValuesToReturn = exceptionToJson.parseExceptionReceivedToString(null,
						HTTPEnums.CODE_404.getCode());
			} else {
				final ObjectMapper mapper = new ObjectMapper();
				multipleValuesToReturn = mapper.writeValueAsString(multipleIdsMap);
			}
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
