/**
 * 
 */
package com.starterkit.library.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.starterkit.library.booksProvider.BookProvider;
import com.starterkit.library.booksProvider.data.BookVO;
import com.starterkit.library.booksProvider.data.StatusVO;

import org.apache.log4j.Logger;

import com.starterkit.library.model.BookSearch;
import com.starterkit.library.model.Status;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * @author HSIENKIE
 *
 */
public class BookController {

	private static final Logger LOG = Logger.getLogger(BookController.class);

	/**
	 * Resource bundle loaded with this controller. JavaFX injects a resource
	 * bundle specified in {@link FXMLLoader#load(URL, ResourceBundle)} call.
	 * <p>
	 * NOTE: The variable name must be {@code resources}.
	 * </p>
	 */
	@FXML
	private ResourceBundle resources;

	/**
	 * URL of the loaded FXML file. JavaFX injects an URL specified in
	 * {@link FXMLLoader#load(URL, ResourceBundle)} call.
	 * <p>
	 * NOTE: The variable name must be {@code location}.
	 * </p>
	 */
	@FXML
	private URL location;

	/**
	 * JavaFX injects an object defined in FXML with the same "fx:id" as the
	 * variable name.
	 */
	@FXML
	private TextField titleField;
	
	@FXML
	private TextField authorsField;

	@FXML
	private ComboBox<Status> statusField;

	@FXML
	private Button searchButton;

	@FXML
	private TableView<BookVO> resultTable;

	@FXML
	private TableColumn<BookVO, String> titleColumn;

	@FXML
	private TableColumn<BookVO, String> statusColumn;

	@FXML
	private TableColumn<BookVO, String> authorsColumn;

	private final BookSearch model = new BookSearch();
	
	private final BookProvider bookProvider = new BookProvider();

	public BookController() {
		LOG.debug("Constructor");
	}

	/**
	 * The JavaFX runtime calls this method after loading the FXML file.
	 * <p>
	 * The @FXML annotated fields are initialized at this point.
	 * </p>
	 * <p>
	 * NOTE: The method name must be {@code initialize}.
	 * </p>
	 */
	@FXML
	private void initialize() {
		LOG.debug("initialize()");

		initializeStatusField();

		initializeResultTable();

		/*
		 * Bind controls properties to model properties.
		 */
		titleField.textProperty().bindBidirectional(model.titleProperty());
		authorsField.textProperty().bindBidirectional(model.authorsProperty());
		statusField.valueProperty().bindBidirectional(model.statusProperty());
		resultTable.itemsProperty().bind(model.resultProperty());

		/*
		 * Preselect the default value for status.
		 */
		model.setStatus(Status.ANY);
		model.setAuthors("");

		/*
		 * This works also, because we are using bidirectional binding.
		 */
		// statusField.setValue(Status.ANY);

		/*
		 * Make the Search button inactive when the Title field is empty.
		 */
		searchButton.disableProperty().bind(titleField.textProperty().isEmpty());
	}
	
	private void initializeStatusField() {
		/*
		 * Add items to the list in combo box.
		 */
		for (Status status : Status.values()) {
			statusField.getItems().add(status);
		}

		/*
		 * Set cell factory to render internationalized texts for list items.
		 */
		statusField.setCellFactory(new Callback<ListView<Status>, ListCell<Status>>() {

			@Override
			public ListCell<Status> call(ListView<Status> param) {
				return new ListCell<Status>() {

					@Override
					protected void updateItem(Status item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							return;
						}
						String text = getInternationalizedText(item);
						setText(text);
					}
				};
			}
		});

		/*
		 * Set converter to display internationalized text for selected value.
		 */
		statusField.setConverter(new StringConverter<Status>() {

			@Override
			public String toString(Status object) {
				return getInternationalizedText(object);
			}

			@Override
			public Status fromString(String string) {
				/*
				 * Not used, because combo box is not editable.
				 */
				return null;
			}
		});
	}

	private void initializeResultTable() {
		/*
		 * Define what properties of BookVO will be displayed in different
		 * columns.
		 */
		titleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
		// titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		statusColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<BookVO, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<BookVO, String> param) {
						StatusVO status = param.getValue().getStatus();
						String text = getInternationalizedText(Status.fromStatusVO(status));
						return new ReadOnlyStringWrapper(text);
					}
				});
		authorsColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAuthors()));

		/*
		 * Show specific text for an empty table. This can also be done in FXML.
		 */
		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));

		// /*
		// * When table's row gets selected say given person's name.
		// */
		// resultTable.getSelectionModel().selectedItemProperty().addListener(new
		// ChangeListener<BookVO>() {
		//
		// @Override
		// public void changed(ObservableValue<? extends BookVO> observable,
		// BookVO oldValue, BookVO newValue) {
		// LOG.debug(newValue + " selected");
		//
		// if (newValue != null) {
		// Task<Void> backgroundTask = new Task<Void>() {
		//
		// @Override
		// protected Void call() throws Exception {
		// speaker.say(newValue.getName());
		// return null;
		// }
		//
		// @Override
		// protected void failed() {
		// LOG.error("Could not say name: " + newValue.getName(),
		// getException());
		// }
		// };
		// new Thread(backgroundTask).start();
		// }
		// }
		// });
	}

	/**
	 * Gets an internationalized text for given {@link Status} value.
	 *
	 * @param status
	 *            status
	 * @return text
	 */
	private String getInternationalizedText(Status status) {
		return resources.getString("status." + status.name());
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Search</b> button is
	 * clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 */
	@FXML
	private void searchButtonAction(ActionEvent event) {
		LOG.debug("'Search' button clicked");
		/**
		 * This implementation is correct.
		 * <p>
		 * The {@link BookProvider#findBooks(String, StatusVO)} call is executed
		 * in a background thread.
		 * </p>
		 */
		/*
		 * Use task to execute the potentially long running call in background
		 * (separate thread), so that the JavaFX Application Thread is not
		 * blocked.
		 */
		Task<Collection<BookVO>> backgroundTask = new Task<Collection<BookVO>>() {

			/**
			 * This method will be executed in a background thread.
			 */
			@Override
			protected Collection<BookVO> call() throws Exception {
				LOG.debug("call() called");

				/*
				 * Get the data.
				 */
				Collection<BookVO> result = bookProvider.findBooks( //
						model.getTitle(), //
						model.getAuthors(), //
						model.getStatus().toStatusVO());

				/*
				 * Value returned from this method is stored as a result of task
				 * execution.
				 */
				return result;
			}

			/**
			 * This method will be executed in the JavaFX Application Thread
			 * when the task finishes.
			 */
			@Override
			protected void succeeded() {
				LOG.debug("succeeded() called");

				/*
				 * Get result of the task execution.
				 */
				Collection<BookVO> result = getValue();

				/*
				 * Copy the result to model.
				 */
				model.setResult(new ArrayList<BookVO>(result));

				/*
				 * Reset sorting in the result table.
				 */
				resultTable.getSortOrder().clear();
			}
		};

		/*
		 * Start the background task. In real life projects some framework
		 * manages background tasks. You should never create a thread on your
		 * own.
		 */
		new Thread(backgroundTask).start();

	}

}