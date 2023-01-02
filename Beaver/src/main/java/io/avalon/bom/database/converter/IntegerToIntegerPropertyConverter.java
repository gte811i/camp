/**
 * 
 */
package io.avalon.bom.database.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author gte81
 *
 */
@ReadingConverter
public class IntegerToIntegerPropertyConverter implements Converter<Integer, IntegerProperty> {
	@Override
	public IntegerProperty convert(Integer source) {
		return new SimpleIntegerProperty(source);
	}
}
