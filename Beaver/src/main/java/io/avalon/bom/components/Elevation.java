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
import javafx.collections.ListChangeListener;
import lombok.extern.log4j.Log4j2;

/**
 * @author gte81
 *
 */
@Component
@Log4j2
public class Elevation {
	@Autowired
	ItemRepository finishItemRepo;
	private final ListProperty<Finish> finishListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());

	@PostConstruct
	public void init() {
		finishListProperty.addAll(finishItemRepo.findAll());
		
		finishListProperty.addListener(new ListChangeListener<Finish>() {
			@Override
			public void onChanged(Change<? extends Finish> c) {
				c.next();
				c.getRemoved().forEach(x->{
					log.debug("Deleting: " + x +  "");
					finishItemRepo.delete(x);
				});
				c.getAddedSubList().forEach(x->{
					log.debug("Adding: " + x +  "");
					finishItemRepo.insert(x);
				});
			}
		  });
	}
	public ListProperty<Finish> finishListProperty() {
		return finishListProperty;
	}

}
