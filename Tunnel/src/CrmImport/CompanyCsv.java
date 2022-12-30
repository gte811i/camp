/**
 * 
 */
package CrmImport;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

/**
 * @author scarleton3
 *
 */
public class CompanyCsv {
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
	@CsvBindByName(column="Company Phone",required=false)
	private String companyPhone;
	@CsvBindByPosition(position = 4)
	@CsvBindByName(column="Company E-mail",required=false)
	private String companyEmail;
	@CsvBindByPosition(position = 5)
	@CsvBindByName(column="Company Address",required=false)
	private String companyAddress;
	@CsvBindByPosition(position = 6)
	@CsvBindByName(column="Company City",required=false)
	private String companyCity;
	@CsvBindByPosition(position = 7)
	@CsvBindByName(column="Company State",required=false)
	private String companyState;
	@CsvBindByPosition(position = 8)
	@CsvBindByName(column="Company Zip",required=false)
	private String companyZip;
	@CsvBindByPosition(position = 9)
	@CsvBindByName(column="Contacts",required=false)
	private String contacts;
	@CsvBindByPosition(position = 10)
	@CsvBindByName(column="Company Quotes",required=false)
	private String companyQuotes;
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CompanyCsv))
			return false;	
		if (obj == this)
			return true;
		return (this.summary.equals(((CompanyCsv) obj).summary));
	}

	@Override
	public int hashCode(){
		return summary.hashCode();//for simplicity reason
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
	 * @return the companyPhone
	 */
	public String getCompanyPhone() {
		return companyPhone;
	}
	/**
	 * @param companyPhone the companyPhone to set
	 */
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	/**
	 * @return the companyEmail
	 */
	public String getCompanyEmail() {
		return companyEmail;
	}
	/**
	 * @param companyEmail the companyEmail to set
	 */
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	/**
	 * @return the companyAddress
	 */
	public String getCompanyAddress() {
		return companyAddress;
	}
	/**
	 * @param companyAddress the companyAddress to set
	 */
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	/**
	 * @return the companyCity
	 */
	public String getCompanyCity() {
		return companyCity;
	}
	/**
	 * @param companyCity the companyCity to set
	 */
	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}
	/**
	 * @return the companyState
	 */
	public String getCompanyState() {
		return companyState;
	}
	/**
	 * @param companyState the companyState to set
	 */
	public void setCompanyState(String companyState) {
		this.companyState = companyState;
	}
	/**
	 * @return the companyZip
	 */
	public String getCompanyZip() {
		return companyZip;
	}
	/**
	 * @param companyZip the companyZip to set
	 */
	public void setCompanyZip(String companyZip) {
		this.companyZip = companyZip;
	}
	/**
	 * @return the contacts
	 */
	public String getContacts() {
		return contacts;
	}
	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	/**
	 * @return the companyQuotes
	 */
	public String getCompanyQuotes() {
		return companyQuotes;
	}
	/**
	 * @param companyQuotes the companyQuotes to set
	 */
	public void setCompanyQuotes(String companyQuotes) {
		this.companyQuotes = companyQuotes;
	}
}
