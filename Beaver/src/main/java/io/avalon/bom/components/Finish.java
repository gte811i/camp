package io.avalon.bom.components;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Document("Finish")
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.PUBLIC_ONLY, setterVisibility = Visibility.PUBLIC_ONLY)
public class Finish {
	@Id
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();

	/**
	 * @param id
	 * @param name
	 */
	@PersistenceCreator
	public Finish(IntegerProperty id, StringProperty name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * @param id
	 * @param name
	 */
	public Finish(Integer id, String name) {
		super();
		this.id.set(id);
		this.name.set(name);
	}

	public int getId() {
		return idProperty().get();
	}

	public void setId(IntegerProperty id) {
		this.id = id;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}
	public StringProperty nameProperty() {
		return name;
	}
	public IntegerProperty idProperty() {
		return id;
	}

	@Override
	public String toString() {
		return name.get();
	}
}
