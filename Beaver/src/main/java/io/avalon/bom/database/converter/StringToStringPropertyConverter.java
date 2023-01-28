/**
 * 
 */
package io.avalon.bom.database.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author gte81
 *
 */
@ReadingConverter
public class StringToStringPropertyConverter implements Converter<String, StringProperty> {
	@Override
	public StringProperty convert(String source) {
		return new SimpleStringProperty(source);
	}
}
