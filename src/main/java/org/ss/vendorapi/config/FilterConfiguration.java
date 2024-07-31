package org.ss.vendorapi.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.Filter;

@Configuration
public class FilterConfiguration {

	 @Autowired
	 private List<Filter> filters;

	    @PostConstruct
	    public void init() {
	        filters.forEach(filter -> System.out.println("Filter: " + filter.getClass().getName()));
	    }
}
