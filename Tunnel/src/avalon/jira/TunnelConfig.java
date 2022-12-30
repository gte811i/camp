/**
 * 
 */
package avalon.jira;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * @author gte81
 *
 */
@Configuration
@PropertySource("file:config/tunnel.properties")
@PropertySource("file:config/version.properties")
@EnableAsync
@ComponentScan("avalon.jira")
@EnableMBeanExport
public class TunnelConfig {
	
}
