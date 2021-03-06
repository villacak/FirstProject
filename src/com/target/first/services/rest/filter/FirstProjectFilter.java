package com.target.first.services.rest.filter;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

/**
 * Filter, where we can intercept the request before it reaches any REST interface, check data if necessary
 * then add your token, if using it, in the header response.
 * 
 * To be able to test this class is necessary to override most of those methods from the interface as test frameworks
 * just work well mocking values, form calls and returns though if it's needed to set values inside the mock object to
 * retrieve it later, it will not work.
 * 
 * @author Klaus Villaca
 *
 */
public class FirstProjectFilter implements ContainerRequestFilter, ContainerResponseFilter {

	private static final String HEADER_KEY = "FirstProject";
	private static final String HEADER_VALUE = "by: Klaus Villaca - ";
	
	/**
	 * This method is called when response returns from Request
	 */
	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
		final Date tempDate = new Date();
		response.getHeaders().add(HEADER_KEY, HEADER_VALUE + tempDate.toString());
	}

	/**
	 * This method is called when the request first reach the filter, here is the place to add validations before anything reach
	 * any REST interface.
	 */
	@Override
	public void filter(ContainerRequestContext request) throws IOException {
		// TODO Auto-generated method stub

	}

}
