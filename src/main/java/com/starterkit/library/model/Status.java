/**
 * 
 */
package com.starterkit.library.model;

import com.starterkit.library.booksProvider.data.BookStatus;

/**
 * @author HSIENKIE
 *
 */
public enum Status {

	ANY, FREE, LOAN, MISSING;

	public static Status fromBookStatus(BookStatus status) {
		return Status.valueOf(status.name());
	}

	public BookStatus toBookStatus() {
		if (this == ANY) {
			return null;
		}
		return BookStatus.valueOf(this.name());
	}

}
