/**
 * 
 */
package com.starterkit.library.model;

import java.util.ArrayList;
import java.util.List;

import com.starterkit.library.booksProvider.data.BookVO;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * @author HSIENKIE
 *
 */
public class BookSearch {

	private final StringProperty title = new SimpleStringProperty();
	private final StringProperty authors = new SimpleStringProperty();
	private final ObjectProperty<Status> status = new SimpleObjectProperty<>();
	private final ListProperty<BookVO> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	public final String getTitle() {
		return title.get();
	}

	public final void setTitle(String value) {
		title.set(value);
	}

	public StringProperty titleProperty() {
		return title;
	}
	
	public final String getAuthors() {
		return authors.get();
	}

	public final void setAuthors(String value) {
		authors.set(value);
	}

	public StringProperty authorsProperty() {
		return authors;
	}

	public final Status getStatus() {
		return status.get();
	}

	public final void setStatus(Status value) {
		status.set(value);
	}

	public ObjectProperty<Status> statusProperty() {
		return status;
	}

	public final List<BookVO> getResult() {
		return result.get();
	}

	public final void setResult(List<BookVO> value) {
		result.setAll(value);
	}

	public ListProperty<BookVO> resultProperty() {
		return result;
	}

	@Override
	public String toString() {
		return "BookSearch [title=" + title + ", authors=" + authors + ",status=" + status + ", result=" + result + "]";
	}

}
