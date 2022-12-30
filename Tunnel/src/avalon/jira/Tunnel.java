package avalon.jira;
import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 
 */

/**
 * @author scarleton3
 *
 */
public class Tunnel {
	public static void main(String[] args){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TunnelConfig.class);
		JiraImportCrm jira = ctx.getBean(JiraImportCrm.class);
		jira.initJira();
		jira.collectCrm();
		
		//Get Project and connect with quotes
		jira.getJiraProjects();
		jira.collectProjects();
		if(jira.addProjects())
			jira.getJiraProjects();
		//Get Companies & Contacts
		jira.getJiraCompanies();
		jira.getJiraContacts();
		jira.collectBar();
		jira.collectBidders();
		if(jira.addCompanies())
			jira.getJiraCompanies();
		jira.collectContacts();
		if(jira.addContacts())
			jira.getJiraContacts();

		jira.getJiraQuotes();
		jira.collectQuotes();
		jira.addQuotes();

	}
}
