/**
 * 
 */
package io.avalon.bom.database.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import javafx.beans.property.StringProperty;

/**
 * @author gte81
 *
 */
@WritingConverter
public class StringPropertyToStringConverter implements Converter<StringProperty, String> {
	@Override
	public String convert(StringProperty source) {
		return source.get();
	}
}
