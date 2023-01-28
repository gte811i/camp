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
	private StringProperty code = new SimpleStringProperty();

	/**
	 * @param id
	 * @param name
	 */
	@PersistenceCreator
	public Finish(IntegerProperty id, StringProperty name, StringProperty code) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
	}
	public Finish(String name, String code) {
		super();
		this.name.set(name);
		this.code.set(code);
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
	public String getCode() {
		return code.get();
	}
	public void setCode(String code) {
		this.code.set(code);
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	public StringProperty codeProperty() {
		return code;
	}
	public IntegerProperty idProperty() {
		return id;
	}

	@Override
	public String toString() {
		return name.get();
	}
}
