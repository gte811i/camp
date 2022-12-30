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
import io.avalon.bom.components.Finish;
/**
 * @author gte81
 *
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
@Log4j2
@Data
@Component
public class DatabaseAccessController {
	@Autowired
	ItemRepository finishItemRepo;
	@Autowired
	CustomItemRepository customRepo;
	String itemType;

	@FXML
	private Button addBtn;

	@FXML
	private TableView<?> allValues;

	@FXML
	private Button deleteBtn;

	@FXML
	private TextField valueTxtField;
	@FXML
	void initialize() {
		log.debug("Checking Initialization");
	}
	@PostConstruct
	public void init() {
		log.debug("Data creation started...");
		List<Finish> itemList = new ArrayList<>();
		itemList = finishItemRepo.findAll();

		itemList.forEach(item -> log.debug(item));

//		finishItemRepo.save(new Finish("Clear Anodized", "Clear Anodized"));
//		finishItemRepo.save(new Finish("Black Anodized", "Black Anodized"));
//		finishItemRepo.save(new Finish("A2", "A2"));
		
		log.debug("Data creation Finished...");
	}
}
