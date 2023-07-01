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
public class Throat extends AvalonObject{
	@Id
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private StringProperty code = new SimpleStringProperty();

	/**
	 * @param id
	 * @param name
	 */
	@PersistenceCreator
	public Throat(IntegerProperty id, StringProperty name, StringProperty code) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
	}
	public Throat(String name, String code) {
		super();
		this.name.set(name);
		this.code.set(code);
	}

	/**
	 * @param id
	 * @param name
	 */
	public Throat(Integer id, String name) {
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

	@Override
	public String getName() {
		return name.get();
	}

	@Override
	public void setName(String name) {
		this.name.set(name);
	}
	@Override
	public String getCode() {
		return code.get();
	}
	public void setCode(String code) {
		this.code.set(code);
	}
	
	@Override
	public StringProperty nameProperty() {
		return name;
	}
	@Override
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
	@Override
	public AvalonObject createAvalonObject(String name, String code) {
		return new Throat(name, code);
	}

}
