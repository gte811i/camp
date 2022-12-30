/**
 * 
 */
package avalon.jira;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.Comment;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.google.common.base.Objects;
//import com.google.common.base.Objects.toStringHelper;
import io.atlassian.util.concurrent.Promise;

/**
 * @author scarleton3
 *
 */
public class AvalonJiraClient{
	public static Logger logger=LoggerFactory.getLogger(JiraImportCrm.class);
	private String username;
	private String password;
	private String jiraUrl;
	private JiraRestClient restClient;
	Set<String> fields = new HashSet<>();

	public AvalonJiraClient(String username, String password, String jiraUrl) {
		this.username = username;
		this.password = password;
		this.jiraUrl = jiraUrl;
		this.restClient = getJiraRestClient();
		resetFields();
	}

	private void resetFields() {
		fields.clear();
		fields.add("summary");
		fields.add("issuetype");
		fields.add("created");
		fields.add("updated");
		fields.add("project");
		fields.add("status");
	}
	private JiraRestClient getJiraRestClient() {
		return new AsynchronousJiraRestClientFactory()
				.createWithBasicHttpAuthentication(getJiraUri(), this.username, this.password);
	}
	private URI getJiraUri() {
		return URI.create(this.jiraUrl);
	}

	public Set<Issue> getProjects(){
		Set<Issue> issueSet = new HashSet<>();
		resetFields();
		fields.add(ProjectTypes.PROJECT_ZIP);
		fields.add(ProjectTypes.PROJECT_TIME_ZONE);
		fields.add(ProjectTypes.PROJECT_TIME_ZONE_TXT);
		fields.add(ProjectTypes.PROJECT_PROP_NUMBER);
		fields.add(ProjectTypes.PROJECT_ADDRESS);
		fields.add(ProjectTypes.PROJECT_FOLDER_NAME);
		fields.add(ProjectTypes.PROJECT_QUOTE_PRICE);
		fields.add(ProjectTypes.PROJECT_EMAIL);
		fields.add(ProjectTypes.PROJECT_AL_WINDOWS);
		fields.add(ProjectTypes.PROJECT_AL_FRAME);
		fields.add(ProjectTypes.PROJECT_DIRECT_QUOTE);
		fields.add(ProjectTypes.PROJECT_CITY);
		fields.add(ProjectTypes.PROJECT_JOB_TYPE);
		fields.add(ProjectTypes.PROJECT_PROJECT_NUMBER);
		fields.add(ProjectTypes.PROJECT_FOLLOW_UP);
		fields.add(ProjectTypes.PROJECT_BID_DATE);
		fields.add(ProjectTypes.PROJECT_STATE);
		fields.add(ProjectTypes.PROJECT_VALUE);
		fields.add(ProjectTypes.PROJECT_PHONE_NUMBER);
		fields.add(ProjectTypes.PROJECT_AL_DOORS); 
		fields.add(ProjectTypes.PROJECT_LINK);
		fields.add(ProjectTypes.PROJECT_QUOTES);
		return getIssues(ProjectTypes.AVALON,ProjectTypes.PROJECT);
	}
	public String createProjectIssue(ProjectCsv project) {
		IssueRestClient issueClient = restClient.getIssueClient();
		DateTimeFormatter jiraDateTime = DateTimeFormatter.ISO_LOCAL_DATE;
		DateTimeFormatter csvDateTime = DateTimeFormatter.ofPattern("M/d/yyyy");
		LocalDate bidDate = LocalDate.parse(project.getBidDate(), csvDateTime);
		LocalDate followUpDate = LocalDate.parse(project.getFollowUp(), csvDateTime);
		
		IssueInput newIssue = new IssueInputBuilder(
				ProjectTypes.AVALON, ProjectTypes.ISSUE_PROJECT, project.getProjectTitle())
				.setFieldValue(ProjectTypes.PROJECT_ADDRESS, project.getAddress())
				.setFieldValue(ProjectTypes.PROJECT_CITY, project.getCity())
				.setFieldValue(ProjectTypes.PROJECT_ZIP, project.getZip())
				.setFieldValue(ProjectTypes.PROJECT_PROJECT_NUMBER, Integer.valueOf(project.getProjectNumber()))
				.setFieldValue(ProjectTypes.PROJECT_VALUE, Integer.valueOf(project.getValue()))
				.setFieldValue(ProjectTypes.PROJECT_FOLDER_NAME, project.getFolderName())
				.setFieldValue(ProjectTypes.PROJECT_LINK, project.getLink())
				.setFieldValue(ProjectTypes.PROJECT_FOLLOW_UP, followUpDate.format(jiraDateTime))
				.setFieldValue(ProjectTypes.PROJECT_BID_DATE, bidDate.format(jiraDateTime))
				.setFieldValue(ProjectTypes.PROJECT_TIME_ZONE, ComplexIssueInputFieldValue.with("value", project.getTimeZone()))
//				.setFieldValue(ProjectTypes.PROJECT_TIME_ZONE_TXT, ComplexIssueInputFieldValue.with("value", project.getTimeZoneTxt()))
				.setFieldValue(ProjectTypes.PROJECT_JOB_TYPE, ComplexIssueInputFieldValue.with("value", project.getJobType()))
				.setFieldValue(ProjectTypes.PROJECT_STATE, ComplexIssueInputFieldValue.with("value", project.getState()))
				.build();
		logger.debug("Issue is: " + newIssue.toString());
		return issueClient.createIssue(newIssue).claim().getKey();
	}

	public Set<Issue> getCompanies(){
		resetFields();
		fields.add(ProjectTypes.COMPANY_ADDRESS);
		fields.add(ProjectTypes.COMPANY_CITY);
		fields.add(ProjectTypes.COMPANY_STATE);
		fields.add(ProjectTypes.COMPANY_ZIP);
		fields.add(ProjectTypes.COMPANY_PHONE);
		fields.add(ProjectTypes.COMPANY_CONTACTS);
		fields.add(ProjectTypes.COMPANY_QUOTES);
		fields.add(ProjectTypes.COMPANY_EMAIL);
		return getIssues(ProjectTypes.CRM,ProjectTypes.COMPANY);
	}
	public String createCompanyIssue(CompanyCsv company) {
		IssueRestClient issueClient = restClient.getIssueClient();
		IssueInput newIssue = new IssueInputBuilder(
				ProjectTypes.CRM, ProjectTypes.ISSUE_COMPANY, company.getSummary())
				.setFieldValue(ProjectTypes.COMPANY_EMAIL, company.getCompanyEmail())
				.setFieldValue(ProjectTypes.COMPANY_PHONE, company.getCompanyPhone())
				.setFieldValue(ProjectTypes.COMPANY_ADDRESS, company.getCompanyAddress())
				.setFieldValue(ProjectTypes.COMPANY_CITY, company.getCompanyCity())
				.setFieldValue(ProjectTypes.COMPANY_STATE, company.getCompanyState())
				.setFieldValue(ProjectTypes.COMPANY_ZIP, company.getCompanyZip())
				.build();
		logger.debug("Company is: " + newIssue.toString());
		return issueClient.createIssue(newIssue).claim().getKey();
	}

	public String createQuoteIssue(QuotesCsv quotes) {
		IssueRestClient issueClient = restClient.getIssueClient();
		IssueInput newIssue = new IssueInputBuilder(
				ProjectTypes.CRM, ProjectTypes.ISSUE_QUOTE, quotes.getSummary())
				.setFieldValue(ProjectTypes.QUOTE_COMPANY, quotes.getQuoteCompany())
				.setFieldValue(ProjectTypes.QUOTE_CONTACT, quotes.getQuoteContact())
//				.setFieldValue(ProjectTypes.QUOTE_PRICE, Integer.valueOf(quotes.getQuotePrice()))
				.setFieldValue(ProjectTypes.QUOTE_PROJET, quotes.getQuoteProject())
				.build();
		logger.debug("Quote is: " + newIssue.toString());
		return issueClient.createIssue(newIssue).claim().getKey();
	}
	public Set<Issue> getQuotes(){
		Set<Issue> issueSet = new HashSet<>();
		resetFields();
		fields.add(ProjectTypes.QUOTE_COMPANY);
		fields.add(ProjectTypes.QUOTE_CONTACT);
		fields.add(ProjectTypes.QUOTE_PRICE);
		fields.add(ProjectTypes.QUOTE_PROJET);
		return getIssues(ProjectTypes.CRM,ProjectTypes.QUOTE);
	}
	public String createContactIssue(ContactCsv contact) {
		IssueRestClient issueClient = restClient.getIssueClient();
		IssueInput newIssue = new IssueInputBuilder(
				ProjectTypes.CRM, ProjectTypes.ISSUE_CONTACT, contact.getSummary())
				.setFieldValue(ProjectTypes.CONTACT_ADDRESS, contact.getStreetAddress())
				.setFieldValue(ProjectTypes.CONTACT_CITY, contact.getCity())
				.setFieldValue(ProjectTypes.CONTACT_STATE, contact.getState())
				.setFieldValue(ProjectTypes.CONTACT_ZIP, contact.getZip())
				.setFieldValue(ProjectTypes.CONTACT_PHONE, contact.getPhoneBusiness())
				.setFieldValue(ProjectTypes.CONTACT_CELL, contact.getMobileBusiness())
				.setFieldValue(ProjectTypes.CONTACT_EMAIL, contact.getEmail())
				.setFieldValue(ProjectTypes.CONTACT_DEPARTMENT, contact.getDepartment())
				.setFieldValue(ProjectTypes.CONTACT_POSITION, contact.getPosition())
				.setFieldValue(ProjectTypes.CONTACT_FIRST_NAME, contact.getFirstName())
				.setFieldValue(ProjectTypes.CONTACT_LAST_NAME, contact.getLastName())
				.setFieldValue(ProjectTypes.CONTACT_COMPANY, contact.getCompany())
				.build();
		logger.debug("Contact is: " + newIssue.toString());
		return issueClient.createIssue(newIssue).claim().getKey();
	}
	public Set<Issue> getContacts(){
		Set<Issue> issueSet = new HashSet<>();
		resetFields();
		fields.add(ProjectTypes.CONTACT_ADDRESS);
		fields.add(ProjectTypes.CONTACT_CITY);
		fields.add(ProjectTypes.CONTACT_STATE);
		fields.add(ProjectTypes.CONTACT_ZIP);
		fields.add(ProjectTypes.CONTACT_PHONE);
		fields.add(ProjectTypes.CONTACT_CELL);
		fields.add(ProjectTypes.CONTACT_QUOTES);
		fields.add(ProjectTypes.CONTACT_EMAIL);
		fields.add(ProjectTypes.CONTACT_COMPANY);
		fields.add(ProjectTypes.CONTACT_DEPARTMENT);
		fields.add(ProjectTypes.CONTACT_POSITION);
		fields.add(ProjectTypes.CONTACT_FIRST_NAME);
		fields.add(ProjectTypes.CONTACT_LAST_NAME);
		return getIssues(ProjectTypes.CRM,ProjectTypes.CONTACT);
	}

	public Set<Issue> getIssues(String project, String issueType) {
		logger.debug("GETISSUES");
		SearchRestClient searchClient = restClient.getSearchClient();
		Set<Issue> issueSet = new HashSet<>();
		int startIdx = 0;
		int maxResults = 1000;

		String jqlQuery="";
		if(!project.isEmpty())
			jqlQuery += "project = " + project;
		if(!issueType.isEmpty() && !jqlQuery.isEmpty())
			jqlQuery += " AND " + "issuetype = " + issueType;
		else if(!issueType.isEmpty())
			jqlQuery += "issuetype = " + issueType;
		logger.debug("Prior to searchJQL");
		Promise<SearchResult> results = searchClient.searchJql(jqlQuery,Integer.valueOf(maxResults),Integer.valueOf(startIdx),fields);
		logger.debug("After to searchJQL");
		int totalResults = results.claim().getTotal();
		collectIssues(issueSet, results.claim().getIssues());
		while(startIdx < totalResults) {
			results = searchClient.searchJql(jqlQuery,Integer.valueOf(maxResults),Integer.valueOf(startIdx),fields);
			collectIssues(issueSet, results.claim().getIssues());
			startIdx += maxResults;
		}
		logger.debug("After to searchJQL2");
		return issueSet;
	}

	private static void collectIssues(Set<Issue> issueSet,Iterable<Issue> issues) {
		for(Issue issue:issues) {
			issueSet.add(issue);
		}
		logger.debug("Search results: " + issueSet.size());
	}
	public String createIssue(String projectKey, Long issueType, String issueSummary) {
		IssueRestClient issueClient = restClient.getIssueClient();
		IssueInput newIssue = new IssueInputBuilder(
				projectKey, issueType, issueSummary).build();
		return issueClient.createIssue(newIssue).claim().getKey();
	}
	public void addComment(Issue issue, String commentBody) {
		restClient.getIssueClient()
		.addComment(issue.getCommentsUri(), Comment.valueOf(commentBody));
	}
	public List<Comment> getAllComments(String issueKey) {
		return StreamSupport.stream(getIssue(issueKey).getComments().spliterator(), false)
				.collect(Collectors.toList());
	}
	public void updateIssueDescription(String issueKey, String newDescription) {
		IssueInput input = new IssueInputBuilder()
				.setDescription(newDescription)
				.build();
		restClient.getIssueClient()
		.updateIssue(issueKey, input)
		.claim();
	}
	public Issue getIssue(String issueKey) {
		return restClient.getIssueClient()
				.getIssue(issueKey) 
				.claim();
	}
	public void deleteIssue(String issueKey, boolean deleteSubtasks) {
		restClient.getIssueClient()
		.deleteIssue(issueKey, deleteSubtasks)
		.claim();
	}
}
