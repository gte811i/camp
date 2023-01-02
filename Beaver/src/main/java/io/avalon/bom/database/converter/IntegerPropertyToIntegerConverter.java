/**
 * 
 */
package io.avalon.bom.database.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import javafx.beans.property.IntegerProperty;

/**
 * @author gte81
 *
 */
@WritingConverter
public class IntegerPropertyToIntegerConverter implements Converter<IntegerProperty, Integer> {
	@Override
	public Integer convert(IntegerProperty source) {
		return source.get();
	}
}
