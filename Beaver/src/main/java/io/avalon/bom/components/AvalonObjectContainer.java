/**
 * 
 */
package io.avalon.bom.components;

import java.util.function.Supplier;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import lombok.Data;

/**
 * @author gte81
 *
 */
@Data
public class AvalonObjectContainer<E extends AvalonObject> {
	private final ListProperty<E> listProperty = new SimpleListProperty<>(FXCollections.observableArrayList());

//	public AvalonObject create(String name,String code) {
//		return E.createAvalonObject(name,code);
//	}
}