/**
 * 
 */
package com.starterkit.library.RESTServiceClient;


import java.util.List;

import org.apache.log4j.Logger;

import com.starterkit.library.booksProvider.data.BookTo;
import com.starterkit.library.mapper.BookMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * @author HSIENKIE
 *
 */
public class RestServiceClient {

	private static final Logger LOG = Logger.getLogger(RestServiceClient.class);

	private ClientConfig clientConfig = new DefaultClientConfig();
	
	private Client client;
	
	public RestServiceClient() {
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		client = Client.create(clientConfig);
	}

	public List<BookTo> findBooks(String title, String authors, String status) {
		LOG.debug("Entering findBooks() client");
		WebResource webResource = client.resource("http://localhost:8080/webstore/findBooks").queryParam("title", title)
				.queryParam("author", authors).queryParam("status", status);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		List<BookTo> books = webResource.get(new GenericType<List<BookTo>>() {});

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
		LOG.debug(response);
//		String books = response.getEntity(String.class);
//		return BookMapper.jsonToBookTo(books);
		return books;
	}
}
