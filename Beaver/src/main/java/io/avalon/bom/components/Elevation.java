/**
 * 
 */
package io.avalon.bom.components;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.avalon.bom.database.repository.FinishRepository;
import io.avalon.bom.database.repository.ThroatRepository;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author gte81
 *
 */
@Component
@Log4j2
public class Elevation {
	@Autowired
	FinishRepository finishItemRepo;
	@Autowired
	ThroatRepository throatItemRepo;

	private final ListProperty<AvalonObject> finishListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ListProperty<AvalonObject> throatListProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
	private HashMap<String,ListProperty<AvalonObject>> propertyMap = new HashMap<>();

	@PostConstruct
	public void init() {
		finishListProperty.addAll(finishItemRepo.findAll());
		throatListProperty.addAll(throatItemRepo.findAll());
		
		finishListProperty.addListener(new ListChangeListener<AvalonObject>() {
			@Override
			public void onChanged(Change<? extends AvalonObject> c) {
				c.next();
				c.getRemoved().forEach(x->{
					log.debug("Deleting: " + x +  "");
					finishItemRepo.delete((Finish) x);
				});
				c.getAddedSubList().forEach(x->{
					log.debug("Adding: " + x +  "");
					finishItemRepo.insert((Finish)x);
				});
			}
		  });
		throatListProperty.addListener(new ListChangeListener<AvalonObject>() {
			@Override
			public void onChanged(Change<? extends AvalonObject> c) {
				c.next();
				c.getRemoved().forEach(x->{
					log.debug("Deleting: " + x +  "");
					throatItemRepo.delete((Throat) x);
				});
				c.getAddedSubList().forEach(x->{
					log.debug("Adding: " + x +  "");
					throatItemRepo.insert((Throat)x);
				});
			}
		  });
		propertyMap.put(Finish.class.getSimpleName(), finishListProperty);
		propertyMap.put(Throat.class.getSimpleName(), throatListProperty);
	}
	public ListProperty<AvalonObject> finishListProperty() {
		return finishListProperty;
	}
	public ListProperty<AvalonObject> throatListProperty() {
		return throatListProperty;
	}
    public <T> void listProperty(Class<? extends AvalonObject> data) {
    }
	/**
	 * @return the propertyMap
	 */
	public HashMap<String, ListProperty<AvalonObject>> getPropertyMap() {
		return propertyMap;
	}

}
