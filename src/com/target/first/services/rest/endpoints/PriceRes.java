package com.target.first.services.rest.endpoints;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.target.first.persistence.enums.HTTPEnums;
import com.target.first.services.rest.helper.Search;
import com.target.first.services.rest.utils.ExceptionsToJson;

/**
 * Endpoints - Rest Interface
 * 
 * @author Klaus 
 *
 */
@Path("v1/price")
public class PriceRes {
	
	// In order to make this class testable we sacrifice security and memory
	private Search search;
	private Response response;
	private ExceptionsToJson exceptionToJson;
	
	public PriceRes() {
		search = new Search();
	}
	
	/**
	 * REMOVE all constructors made for test if deploying in prod.
	 * This is the only place where I did put this comment.
	 * 
	 * @param search
	 * @param response
	 */
	public PriceRes(Search search, Response response, ExceptionsToJson exceptionToJson) {
		this.search = search;
		this.response = response;
		this.exceptionToJson = exceptionToJson;
	}
	
	
	/**
	 * Interface for search one ID and return a list or one Price associated with that ID
	 * e.g. : http://localhost:8080/FirstProject/rest/v1/price/detailsById/1000
	 * 
	 * @param id
	 * @param jsonAsString
	 * @return
	 */
	@POST
	@Path("/priceById/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response productDetailsById(@PathParam("id") final String id, final String jsonAsString) {
		if (id != null && id.trim().length() > 0) {
			response = search.searchForSingleId(id);
		} else {
			if (exceptionToJson == null)
				exceptionToJson = new ExceptionsToJson();
			response = exceptionToJson.parseExceptionReceived(null, HTTPEnums.CODE_404.getCode());
		}		
		return response;
	}
	
	
	/**
	 * Interface for search across an id list and return a price list associated with that id
	 * e.g. : http://localhost:8080/FirstProject/rest/v1/price/listDetailsById?ids="1000"&ids="1002"
	 * 
	 * @param ids
	 * @param jsonAsString
	 * @return
	 */
	@GET
	@Path("/listDetailsById")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response productListDetailsById(@QueryParam("ids") List<String> ids, String jsonAsString) {
		if (ids != null && ids.size() > 0) {
			response = search.searchForIdList(ids);
		} else {
			if (exceptionToJson == null)
				exceptionToJson = new ExceptionsToJson();
			response = exceptionToJson.parseExceptionReceived(null, HTTPEnums.CODE_404.getCode());
		}
		return response;
	}

}
