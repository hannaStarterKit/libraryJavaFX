/**
 * 
 */
package com.starterkit.library.booksProvider.data;

/**
 * @author HSIENKIE
 *
 */
public class BookVO {

	private String title;
	private String authors;
	private StatusVO status;

	public BookVO(String title, String authors, StatusVO status) {
		this.title = title;
		this.authors = authors;
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public StatusVO getStatus() {
		return status;
	}

	public void setStatus(StatusVO status) {
		this.status = status;
	}
}
