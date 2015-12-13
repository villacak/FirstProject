package com.target.first.services.rest.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.EncodingFilter;




@ApplicationPath("/rest")
public class Application extends ResourceConfig {
	
//	private static final String STANDARD_MESSAGE = "FirstProject - Log ";
	
	public Application() {
		packages("com.target.first.services.rest.endpoints", "com.target.first.services.rest.filter");
		register(EntityFilteringFeature.class);
		
		// Some logs to see request been received
		register(new LoggingFilter(java.util.logging.Logger.getLogger("STANDARD_MESSAGE"), true));
		EncodingFilter.enableFor(this, GZipEncoder.class);
		property(ServerProperties.TRACING, "ALL");
	}
	
	
}
