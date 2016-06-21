/**
 * 
 */
package com.starterkit.library.booksProvider;

import java.util.Collection;

import org.apache.log4j.Logger;
import com.starterkit.library.RESTServiceClient.RestServiceClient;
import com.starterkit.library.booksProvider.data.BookTo;
import com.starterkit.library.booksProvider.data.BookStatus;

/**
 * @author HSIENKIE
 *
 */
public class BookProvider {

	private static final Logger LOG = Logger.getLogger(BookProvider.class);

	private final RestServiceClient restServiceClinet = new RestServiceClient();

	public Collection<BookTo> findBooks(String title, String authors, BookStatus statusVO) {
		LOG.debug("Entering findBooks()");
		String status;
		if (statusVO == null) {
			status = "";
		} else {
			status = statusVO.name();
		}
		LOG.debug(status);
		Collection<BookTo> result = restServiceClinet.findBooks(title, authors, status);

		LOG.debug("Leaving findBooks()");
		return result;
	}
	
	public void addBook(String title, String authors, BookStatus statusVO) {
		LOG.debug("Entering addBook()");
		restServiceClinet.addBook(new BookTo(null, title, authors, statusVO));
		LOG.debug("Leaving addBoos()");
	}

}
