/**
 * 
 */
package io.avalon.bom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;


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
public class BOMConfig {
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {

	      return new PropertySourcesPlaceholderConfigurer();
	}
}
