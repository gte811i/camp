/**
 * 
 */
package avalon.jira;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

/**
 * @author scarleton3
 *
 */
public class QuotesCsv {
	@CsvBindByName(column="Key",required=false)
	@CsvBindByPosition(position = 0)
	private String key;	
	@CsvBindByPosition(position = 1)
	@CsvBindByName(column="Summary",required=false)
	private String summary;	
	@CsvBindByPosition(position = 2)
	@CsvBindByName(column="Issue Type",required=false)
	private String issueType;	
	@CsvBindByPosition(position = 3)
	@CsvBindByName(column="Quote Company",required=false)
	private String quoteCompany;	
	@CsvBindByPosition(position = 4)
	@CsvBindByName(column="Quote Contact",required=false)
	private String quoteContact;	
	@CsvBindByPosition(position = 5)
	@CsvBindByName(column="Quote Price $",required=false)
	private String quotePrice="0";
	@CsvBindByPosition(position = 6)
	@CsvBindByName(column="Quote Project",required=false)
	private String quoteProject;	
	@CsvBindByPosition(position = 7)
	@CsvBindByName(column="Quote Number",required=false)
	private String quoteNumber;	
	public QuotesCsv() {
		key = "";
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof QuotesCsv))
			return false;	
		if (obj == this)
			return true;
		return (this.summary.equals(((QuotesCsv) obj).summary)
				&&this.key.equals(((QuotesCsv)obj).key));
	}

	@Override
	public int hashCode(){
		return summary.hashCode() * key.hashCode();//for simplicity reason
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the issueType
	 */
	public String getIssueType() {
		return issueType;
	}

	/**
	 * @param issueType the issueType to set
	 */
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	/**
	 * @return the quoteCompany
	 */
	public String getQuoteCompany() {
		return quoteCompany;
	}

	/**
	 * @param quoteCompany the quoteCompany to set
	 */
	public void setQuoteCompany(String quoteCompany) {
		this.quoteCompany = quoteCompany;
	}

	/**
	 * @return the quoteContact
	 */
	public String getQuoteContact() {
		return quoteContact;
	}

	/**
	 * @param quoteContact the quoteContact to set
	 */
	public void setQuoteContact(String quoteContact) {
		this.quoteContact = quoteContact;
	}

	/**
	 * @return the quotePrice
	 */
	public String getQuotePrice() {
		return quotePrice;
	}

	/**
	 * @param quotePrice the quotePrice to set
	 */
	public void setQuotePrice(String quotePrice) {
		this.quotePrice = quotePrice;
	}

	/**
	 * @return the quoteProject
	 */
	public String getQuoteProject() {
		return quoteProject;
	}

	/**
	 * @param quoteProject the quoteProject to set
	 */
	public void setQuoteProject(String quoteProject) {
		this.quoteProject = quoteProject;
	}
	/**
	 * @return the quoteNumber
	 */
	public String getQuoteNumber() {
		return quoteNumber;
	}
	/**
	 * @param quoteNumber the quoteNumber to set
	 */
	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}

}
