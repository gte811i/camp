/**
 * 
 */
package io.avalon.bom;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import io.avalon.bom.database.converter.IntegerPropertyToIntegerConverter;
import io.avalon.bom.database.converter.IntegerToIntegerPropertyConverter;
import io.avalon.bom.database.converter.StringPropertyToStringConverter;
import io.avalon.bom.database.converter.StringToStringPropertyConverter;


/**
 * @author gte81
 *
 */
@Configuration
//@PropertySource("file:config/insight.properties")
@EnableAsync
@ComponentScan("io.avalon.bom")
@EnableMBeanExport
@PropertySource("classpath:application.properties")
@PropertySource("file:config/version.properties")
@EnableMongoRepositories
@SpringBootApplication
public class BOMConfig {
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {

	      return new PropertySourcesPlaceholderConfigurer();
	}
	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(
				Arrays.asList(
						new StringToStringPropertyConverter(),
						new StringPropertyToStringConverter(),
						new IntegerPropertyToIntegerConverter(),
						new IntegerToIntegerPropertyConverter()
						));
	}
}
