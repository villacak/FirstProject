package com.target.first.services.rest.endpoints;

import java.util.List;

import javax.ws.rs.Consumes;
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
import com.target.first.services.rest.utils.ResponseCreator;

@Path("v1/product")
public class ProductRest {
	
	/**
	 * Interface for search one ID and return a list or one Product
	 * 
	 * @param id
	 * @param jsonAsString
	 * @return
	 */
	@POST
	@Path("/detailsById/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response productDetailsById(@PathParam("id") final String id, final String jsonAsString) {
		Response response = null;
		String jsonAsStringToReturn = null;
		if (id != null) {
			final Search search = new Search();
			jsonAsStringToReturn = search.searchForSingleId(id);
			
			ResponseCreator createResponse = new ResponseCreator();
			response = createResponse.wrapResponseWithRightCode(jsonAsStringToReturn);
		} else {
			response = ExceptionsToJson.parseExceptionReceived(null, HTTPEnums.CODE_404.getCode());
		}		
		return response;
	}
	
	
	/**
	 * Interface for search across an id list and return a list
	 * 
	 * @param ids
	 * @param jsonAsString
	 * @return
	 */
	@POST
	@Path("/listDetailsById/{ids: .*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response productListDetailsById(@QueryParam("ids") List<String> ids, String jsonAsString) {
		Response response = null;
		String jsonAsStringToReturn = null;
		if (ids != null) {
			final Search search = new Search();
			jsonAsStringToReturn = search.searchForIdList(ids);
			ResponseCreator responseCreator = new ResponseCreator();
			response = responseCreator.wrapResponseWithRightCode(jsonAsStringToReturn);
		} else {
			response = ExceptionsToJson.parseExceptionReceived(null, HTTPEnums.CODE_404.getCode());
		}
		return response;
	}

}
