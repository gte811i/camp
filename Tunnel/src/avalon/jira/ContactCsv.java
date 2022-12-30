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
public class ContactCsv {
	/**
	 * @param fullName
	 */
	public ContactCsv(String fullName) {
		super();
		this.summary = fullName;
	}
	/**
	 * 
	 */
	public ContactCsv() {
		// TODO Auto-generated constructor stub
	}
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
	@CsvBindByName(column="E-mail",required=false)
	private String email;	
	@CsvBindByPosition(position = 4)
	@CsvBindByName(column="Phone (Business)",required=false)
	private String phoneBusiness;	
	@CsvBindByPosition(position = 5)
	@CsvBindByName(column="Cell (Business)",required=false)
	private String mobileBusiness;	
	@CsvBindByPosition(position = 6)
	@CsvBindByName(column="First Name",required=false)
	private String firstName;	
	@CsvBindByPosition(position = 7)
	@CsvBindByName(column="Last Name",required=false)
	private String lastName;	
	@CsvBindByPosition(position = 8)
	@CsvBindByName(column="Department",required=false)
	private String department;	
	@CsvBindByPosition(position = 9)
	@CsvBindByName(column="Position",required=false)
	private String position;	
	@CsvBindByPosition(position = 10)
	@CsvBindByName(column="Contact Organization",required=false)
	private String company;	
	@CsvBindByPosition(position = 11)
	@CsvBindByName(column="Street & Number",required=false)
	private String streetAddress;	
	@CsvBindByPosition(position = 12)
	@CsvBindByName(column="State",required=false)
	private String state;	
	@CsvBindByPosition(position = 13)
	@CsvBindByName(column="City",required=false)
	private String city;	
	@CsvBindByPosition(position = 14)
	@CsvBindByName(column="Zip",required=false)
	private String zip;	
	@CsvBindByPosition(position = 15)
	@CsvBindByName(column="Contact Quotes",required=false)
	private String contactQuotes;
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ContactCsv))
			return false;	
		if (obj == this)
			return true;
		return (this.summary.equals(((ContactCsv) obj).summary)
				&&this.company.equals(((ContactCsv) obj).company)
				&&this.email.equals(((ContactCsv) obj).email));
	}

	@Override
	public int hashCode(){
		return summary.hashCode()+company.hashCode()+email.hashCode();//for simplicity reason
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the phoneBusiness
	 */
	public String getPhoneBusiness() {
		return phoneBusiness;
	}
	/**
	 * @param phoneBusiness the phoneBusiness to set
	 */
	public void setPhoneBusiness(String phoneBusiness) {
		this.phoneBusiness = phoneBusiness;
	}
	/**
	 * @return the mobileBusiness
	 */
	public String getMobileBusiness() {
		return mobileBusiness;
	}
	/**
	 * @param mobileBusiness the mobileBusiness to set
	 */
	public void setMobileBusiness(String mobileBusiness) {
		this.mobileBusiness = mobileBusiness;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return the contactOrganization
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * @param company the contactOrganization to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}
	/**
	 * @param streetAddress the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return the contactQuotes
	 */
	public String getContactQuotes() {
		return contactQuotes;
	}
	/**
	 * @param contactQuotes the contactQuotes to set
	 */
	public void setContactQuotes(String contactQuotes) {
		this.contactQuotes = contactQuotes;
	}	
}
