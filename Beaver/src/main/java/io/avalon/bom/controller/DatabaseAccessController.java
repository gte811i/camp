/**
 * 
 */
package io.avalon.bom.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.avalon.bom.components.AvalonObject;
import io.avalon.bom.components.Elevation;
import io.avalon.bom.components.Finish;
import io.avalon.bom.database.repository.FinishRepository;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import io.avalon.bom.components.AvalonObjectContainer;
@Log4j2
@Data
@Component
public class DatabaseAccessController implements GenericDataBaseAccessController{

	@Autowired
	Elevation elevation;
	//	@Autowired
	Class<? extends AvalonObject> itemType;
	private ListProperty<AvalonObject> avalonObjectListProperty;

	@FXML
	private Button addBtn;

	@FXML
	private TableView<AvalonObject> tblValues;

	@FXML
	private TableColumn<AvalonObject, String> idCol;

	@FXML
	private TableColumn<AvalonObject, String> valueCol;

	@FXML
	private Button deleteBtn;

	@FXML
	private TextField valueTxtField;

	@FXML
	private TextField idTxtField;

	public <S extends AvalonObject> void getListProperty(Class<S> type) {
		log.debug("TypeS: " + type.descriptorString());
		//    	log.debug("TypeT: " + itemType.descriptorString());
		//        this.type = type;
	}
	@FXML
	void initialize() {
		log.debug("Checking Initialization");
	}
	@PostConstruct
	public void init() {
		log.debug("Data creation started...");
		//		finishItemRepo.save(new Finish("Clear Anodized", "Clear Anodized"));
		//		finishItemRepo.save(new Finish("Black Anodized", "Black Anodized"));
		//		finishItemRepo.save(new Finish("A2", "A2"));
		//		allValues.getItems().addAll(itemList);
		log.debug("Data creation Finished...");
	}

	@FXML
	void addValue(ActionEvent event) {
		Finish data = new Finish(valueTxtField.getText(),idTxtField.getText());
		avalonObjectListProperty.add(data);
	}

	@FXML
	void deleteValue(ActionEvent event) {
		AvalonObject data = tblValues.getSelectionModel().getSelectedItem();
		avalonObjectListProperty.remove(data);
	}

	public void loadData(String propertyName) {
		avalonObjectListProperty = elevation.getPropertyMap().get(propertyName);
		tblValues.itemsProperty().bind(avalonObjectListProperty);
		tblValues.getColumns().get(0);
		idCol.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
		valueCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		valueCol.setCellFactory(TextFieldTableCell.forTableColumn());
		valueCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue()));
		tblValues.setEditable(true);
		tblValues.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AvalonObject>() {
			@Override
			public void changed(ObservableValue<? extends AvalonObject> observable, AvalonObject oldValue, AvalonObject newValue) {
				valueTxtField.setText(newValue.getName());
				idTxtField.setText(newValue.getCode());
			}
		});
		log.debug("Data is for: " + propertyName);
		tblValues.getItems().forEach(x->{
			log.debug("Data is: " + x);
		});
	}
}
