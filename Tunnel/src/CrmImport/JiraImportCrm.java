/**
 * 
 */
package CrmImport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;


/**
 * @author scarleton3
 *
 */
public class JiraImportCrm {
	public static Logger logger=LoggerFactory.getLogger(JiraImportCrm.class);
	private HashSet<ContactCsv> contactAddList = new HashSet<>();
	private HashSet<CompanyCsv> companyAddList = new HashSet<>();
	private HashSet<QuotesCsv> quoteAddList = new HashSet<>();
	private List<ProjectCsv> projectAddList = new ArrayList<>();
	private HashSet<BiddersCsv> biddersList;
	private HashSet<BarCsv> barList;
	private HashMap<String,ProjectCsv> projectsCurrentFilterMap;
	private Set<Issue> projects;
	private Set<Issue> companies;
	private Set<Issue> contacts;
	private Set<Issue> quotes; 
	private HashMap<Long, List<BarCsv>> projectBarHash;
	private HashMap<Long, List<BiddersCsv>>projectBiddersHash;
	private CrmImport crm;
	private AvalonJiraClient jiraClient;
	private String companyKey;
	private String contactKey;
	private String projectKey;
	private Long pn; 
	public static void main(String[] args){
		JiraImportCrm jira = new JiraImportCrm();
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
	Predicate<Issue> filterProjects = new Predicate<Issue>() {
		@Override
		public boolean test(Issue t) {
			Double pnL = (Double)t.getField(ProjectTypes.PROJECT_PROJECT_NUMBER).getValue();
			if(pnL == null)
				return false;
			if(pnL.longValue() == Long.valueOf(pn.toString()).longValue())
				return true;
			return false;
		}
	};
	Predicate<Issue> filterQuotes = new Predicate<Issue>() {
		@Override
		public boolean test(Issue t) {
			String quoteCompany = (String) t.getField(ProjectTypes.QUOTE_COMPANY).getValue();
			String quoteContact= (String) t.getField(ProjectTypes.QUOTE_CONTACT).getValue();
			String quoteProject = (String) t.getField(ProjectTypes.QUOTE_PROJET).getValue();
			if(companyKey.equals(quoteCompany)&&contactKey.equals(quoteContact)&&projectKey.equals(quoteProject))
				return true;
			return false;
		}
	}; 
	public void collectContacts() {
		for(ContactCsv contact: contactAddList) {
			String companySummary = contact.getCompany();
			companyKey = companies.stream().filter(x-> x.getSummary().equals(companySummary)).findAny().get().getKey();
			contact.setCompany(companyKey);
		}
	}
	public void collectQuotes() {
		for(ProjectCsv project : projectsCurrentFilterMap.values()) {
			pn = Long.valueOf(project.getProjectNumber());
			List<BarCsv> projectBar = projectBarHash.get(pn);
			List<BiddersCsv> projectBidders = projectBiddersHash.get(pn);
			try {
				logger.debug("CollectingQuotes");
				Issue projectIssue = projects.stream().filter(filterProjects).findAny().orElse(null);
				if(projectIssue == null) {
					logger.debug("Cannot find Project: " + pn);
					continue;
				}
				projectKey = projectIssue.getKey();
				int quoteNumber =0;
				if(projectBar != null)
					for(BarCsv bar:projectBar) {
						if(!bar.getContact().isEmpty() && !bar.getCompanyName().isEmpty()) {
							companyKey = companies.stream().filter(x->x.getSummary().equals(bar.getCompanyName())).findFirst().get().getKey();
							contactKey = contacts.stream().filter(x->x.getSummary().equals(bar.getContact())).findFirst().get().getKey();
							Issue quoteIssue = quotes.stream().filter(filterQuotes).findFirst().orElse(null);
							if(quoteIssue != null) {
								logger.debug("Quote is already in system: " + quoteIssue.getKey());
								continue;
							}
							QuotesCsv quote = new QuotesCsv();
							quote.setQuoteCompany(companyKey);
							quote.setQuoteContact(contactKey);
							quote.setQuoteProject(projectKey);
							quote.setQuotePrice("0");
							quote.setQuoteNumber("0");
							quote.setSummary(projectKey+"-"+quoteNumber++);
							quoteAddList.add(quote);
						}
					}
				if(projectBidders != null)
					for(BiddersCsv bidder:projectBidders) {
						if(!bidder.getContactName().isEmpty() && !bidder.getCompanyName().isEmpty()) {
							companyKey = companies.stream().filter(x->x.getSummary().equals(bidder.getCompanyName())).findFirst().get().getKey();
							contactKey = contacts.stream().filter(x->x.getSummary().equals(bidder.getContactName())).findFirst().get().getKey();
							Issue quoteIssue =quotes.stream().filter(filterQuotes).findFirst().orElse(null);
							if(quoteIssue != null) {
								logger.debug("Quote is already in system: " + quoteIssue.getKey());
								continue;
							}
							QuotesCsv quote = new QuotesCsv();
							quote.setQuoteCompany(companyKey);
							quote.setQuoteContact(contactKey);
							quote.setQuoteProject(projectKey);
							quote.setSummary(projectKey+"-"+quoteNumber++);
							quoteAddList.add(quote);
						}
					}
			}catch(NoSuchElementException e) {
				logger.debug("Error in Finding Project, Company, Contact for Quote");
			}
		}
	}
	public void initJira() {
		jiraClient = new AvalonJiraClient(
				"scarleton", 
				"lamas123", 
				"http://10.1.10.235:8080");
	}
	public void getJiraCompanies() {
		companies = jiraClient.getCompanies();
		logger.debug("Companies in Jira: " + companies.size());
	}
	public void getJiraContacts() {
		contacts = jiraClient.getContacts();
		logger.debug("Contacts in Jira: " + contacts.size());
	}
	public void getJiraProjects() {
		logger.debug("GetJiraProjects");
		projects = jiraClient.getProjects();
		logger.debug("Projects in Jira: " + projects.size());
	}
	public void getJiraQuotes() {
		quotes = jiraClient.getQuotes();
		logger.debug("Quotes in Jira: " + quotes.size());
	}
	public void getJiraIssues() {
		getJiraCompanies();
		getJiraContacts();
		getJiraProjects();
	}

	public void collectCrm() {
		crm = new CrmImport();
		crm.collectData();
		logger.debug("AfterCollectData");
		barList = crm.getBarList();
		biddersList = crm.getBiddersList();
		projectBarHash = crm.getProjectBarHash();
		projectBiddersHash = crm.getProjectBidderHash();
		projectsCurrentFilterMap = crm.getProjectsList();
	}
	public void collectProjects() {
		HashMap<String,Issue> projectNumberMap = new HashMap<>();
		for(Issue project:projects) {
			Double projectNumber = (Double) project.getField(ProjectTypes.PROJECT_PROJECT_NUMBER).getValue();
			if(projectNumber != null) 
				projectNumberMap.put(String.valueOf(projectNumber.longValue()), project);
		}
		for(ProjectCsv project: projectsCurrentFilterMap.values()) {
			if(!projectNumberMap.containsKey(project.getProjectNumber())) 
				projectAddList.add(project);
		}
	}
	public boolean addQuotes() {
		logger.debug("Number of Quotes to add is: "+ quoteAddList.size());
		if(quoteAddList.isEmpty())
			return false;
		for(QuotesCsv quote : quoteAddList) {
			logger.debug("Quote to Add: " + quote.getQuoteProject() + ":" + quote.getQuoteContact() +":" + quote.getQuoteCompany());
			String newKey = jiraClient.createQuoteIssue(quote);
			logger.debug("New Quote created: " + newKey);

		}
		return true;
	}
	public boolean addProjects() {
		logger.debug("Number of Projects to add is: "+ projectAddList.size());
		if(projectAddList.isEmpty())
			return false;
		for(ProjectCsv project : projectAddList) {
			logger.debug("Project to Add: " + project.getProjectTitle());
			String newKey = jiraClient.createProjectIssue(project);
			logger.debug("New Project created: " + newKey);
		}
		return true;
	}
	public boolean addContacts() {
		logger.debug("Number of Contacts to add is: "+ contactAddList.size());
		if(contactAddList.isEmpty())
			return false;
		for(ContactCsv contact:contactAddList) {
			logger.debug("Contact to Add is: " + contact.getSummary());
			String newKey = jiraClient.createContactIssue(contact);
			logger.debug("New Contact created: " + newKey);
		}
		return true;
	}
	public boolean addCompanies() {
		logger.debug("Number of Companies to add is: "+ companyAddList.size());
		if(companyAddList.isEmpty())
			return false;
		for(CompanyCsv company:companyAddList) {
			logger.debug("Company to Add is: " + company.getSummary());
			String newKey = jiraClient.createCompanyIssue(company);
			logger.debug("New Company created: " + newKey);
		}
		return true;
	}

	public void collectBidders() {
		for(BiddersCsv bidder:biddersList) {
			logger.debug("Checking out: " +bidder.getContactName());
			if(bidder.getContactName()==null)
				bidder.setContactName("");
			if(bidder.getCompanyName()==null)
				bidder.setCompanyName("");
			Issue contactIssue = contacts.stream().filter(x-> {
				String contactCompany="";
				String contactCompanyKey = (String) x.getField(ProjectTypes.CONTACT_COMPANY).getValue();
				bidder.setContactName(bidder.getContactName().replace("  ", " "));
				if(contactCompanyKey != null)
					//matching company in system with this contact; i.e. if jira contact name and company match return true
					if(!contactCompanyKey.isEmpty())
						contactCompany=companies.stream().filter(y->y.getKey().equals(contactCompanyKey)).findFirst().get().getSummary();
				if(bidder.getContactName().equals(x.getSummary()) & bidder.getCompanyName().equals(contactCompany))
					return true;
				return false;
			}).findAny().orElse(null);


			Issue companyIssue = companies.stream().filter(x-> bidder.getCompanyName().equals(x.getSummary()))
					.findAny().orElse(null);

			ContactCsv contact = new ContactCsv();
			CompanyCsv comp = new CompanyCsv();
			String address = bidder.getAddress();
			String add[] = null;
			if(address != null) {
				add = address.split(",");
				if(companyIssue == null && !bidder.getCompanyName().isEmpty()) {
					int offset = 0;
					if(add.length > 4)
						offset++;
					comp.setSummary(bidder.getCompanyName());
					comp.setCompanyAddress(add[0]);
					comp.setCompanyCity(add[1+offset]);
					comp.setCompanyState(add[2+offset]);
					comp.setCompanyZip(add[3+offset]);
					comp.setContacts(bidder.getContactName());
					companyAddList.add(comp);
				}
				if(contactIssue == null && !bidder.getContactName().isEmpty()) {
					contact.setSummary(bidder.getContactName());
					contact.setCompany(bidder.getCompanyName());
					contact.setStreetAddress(bidder.getAddress());
					contact.setEmail(bidder.getEmail());
					contact.setPhoneBusiness(bidder.getPhone());
					contactAddList.add(contact);
				}
			}
		}
	}
	public void collectBar() {
		for(BarCsv bar:barList) {
			logger.debug("Checking out: " +bar.getContact());
			if(bar.getContact()==null)
				bar.setContact("");
			if(bar.getCompanyName()==null )
				bar.setCompanyName("");
			Issue contactIssue =contacts.stream().filter( x-> {
				String contactCompany="";
				String contactCompanyKey = (String) x.getField(ProjectTypes.CONTACT_COMPANY).getValue();
				if(contactCompanyKey != null) {
					bar.setContact(bar.getContact().replace("  ", " "));
					if(!contactCompanyKey.isEmpty())
						contactCompany=companies.stream().filter(y->y.getKey().equals(contactCompanyKey)).findFirst().get().getSummary();
				}
				if(bar.getContact().equals(x.getSummary()) & bar.getCompanyName().equals(contactCompany))
					return true;
				return false;
			}).findAny().orElse(null);
			Issue companyIssue = companies.stream().filter(x-> bar.getCompanyName().equals(x.getSummary()))
					.findAny().orElse(null);
			ContactCsv contact = new ContactCsv();
			CompanyCsv comp = new CompanyCsv();
			if(contactIssue == null && !bar.getContact().isEmpty()) {
				contact.setSummary(bar.getContact());
				contact.setCompany(bar.getCompanyName());
				contact.setEmail(bar.getEmail());
				contact.setPhoneBusiness(bar.getPhone());
				contactAddList.add(contact);
			}
			if(companyIssue == null && !bar.getCompanyName().isEmpty()) {
				comp.setSummary(bar.getCompanyName());
				comp.setContacts(bar.getContact());
				companyAddList.add(comp);
			}
		}
	}
}
