/**
 * 
 */
package io.avalon.bom.components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.avalon.bom.database.ItemRepository;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * @author gte81
 *
 */
@Component
public class Elevation {
	@Autowired
	ItemRepository finishItemRepo;
	private final ListProperty<Finish> finishListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());

	@PostConstruct
	public void init() {
		finishListProperty.addAll(finishItemRepo.findAll());
	}
	public ListProperty<Finish> finishListProperty() {
		return finishListProperty;
	}

}
