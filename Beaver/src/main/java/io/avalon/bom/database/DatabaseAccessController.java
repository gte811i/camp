/**
 * 
 */
package io.avalon.bom.database;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.avalon.bom.BillOfMaterialsController;
import io.avalon.bom.components.Elevation;
import io.avalon.bom.components.Finish;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
/**
 * @author gte81
 *
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
@Log4j2
@Data
@Component
public class DatabaseAccessController {
	@Autowired
	Elevation elevation;
	@Autowired
	ItemRepository finishItemRepo;
	String itemType;

	@FXML
	private Button addBtn;

	@FXML
	private TableView<Finish> tblValues;

	@FXML
    private TableColumn<Finish, Integer> idCol;

    @FXML
    private TableColumn<Finish, String> valueCol;

    @FXML
	private Button deleteBtn;

	@FXML
	private TextField valueTxtField;
	@FXML
	void initialize() {
		log.debug("Checking Initialization");
		tblValues.itemsProperty().bind(elevation.finishListProperty());
		tblValues.getColumns().get(0);
//		idCol.setCellValueFactory(cellData -> cellData.getValue()..idProperty());
		valueCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		valueCol.setCellFactory(TextFieldTableCell.forTableColumn());
		valueCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue()));

		tblValues.setEditable(true);
		tblValues.getItems().forEach(x->{
			log.debug("Data is: " + x);
		});
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
    	Finish data = new Finish(10,valueTxtField.getText());
    	elevation.finishListProperty().add(data);
//    	finishItemRepo.insert(data);
//		itemList = finishItemRepo.findAll();
    }

    @FXML
    void deleteValue(ActionEvent event) {
    	Finish data = tblValues.getSelectionModel().getSelectedItem();
    	elevation.finishListProperty().remove(data);
    }
}
