/**
 * 
 */
package com.starterkit.library.booksProvider;

import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import com.starterkit.library.RESTServiceClient.RestServiceClient;
import com.starterkit.library.booksProvider.data.BookVO;
import com.starterkit.library.booksProvider.data.StatusVO;

/**
 * @author HSIENKIE
 *
 */
public class BookProvider {
	
	private static final Logger LOG = Logger.getLogger(BookProvider.class);

	private final RestServiceClient restServiceClinet = new RestServiceClient();

	public Collection<BookVO> findBooks(String title, String Authors, StatusVO statusVO) {
		LOG.debug("Entering findBooks()");
		
		Collection<BookVO> result = restServiceClinet.searchBooks(title, Authors, statusVO);

		LOG.debug("Leaving findBooks()");
		return result;
	}
	
	
}
