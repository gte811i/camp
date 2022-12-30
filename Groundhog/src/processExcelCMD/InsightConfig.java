/**
 * 
 */
package processExcelCMD;

import javax.swing.plaf.nimbus.State;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

import avalon.selenium.StateMachine;

/**
 * @author gte81
 *
 */
@Configuration
@PropertySource("file:config/insight.properties")
@EnableAsync
@ComponentScan("avalon.selenium")
@EnableMBeanExport
public class InsightConfig {
	
}
