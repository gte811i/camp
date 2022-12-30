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
public class ProjectCsv {
	@CsvBindByName(column="Type",required=false)
	@CsvBindByPosition(position = 0)
	private String type;	
	@CsvBindByPosition(position = 1)
	@CsvBindByName(column=	"Job Type",required=false)
	private String jobType;	
	@CsvBindByPosition(position = 2)
	@CsvBindByName(column="Folder Link",required=false)
	private String folderLink;	
	@CsvBindByPosition(position = 3)
	@CsvBindByName(column="Issue Key",required=false)
	private String issueKey;
	@CsvBindByPosition(position = 4)
	@CsvBindByName(column="PM Link",required=false)
	private String pmLink;
	@CsvBindByPosition(position = 5)
	@CsvBindByName(column="Project Number",required=false)
	private String projectNumber;
	@CsvBindByPosition(position = 6)
	@CsvBindByName(column="Project Title",required=false)
	private String projectTitle;
	@CsvBindByPosition(position = 7)
	@CsvBindByName(column="#AL Frames",required=false)
	private String alFrames;
	@CsvBindByPosition(position = 8)
	@CsvBindByName(column="#AL Doors",required=false)
	private String companyZip;
	@CsvBindByPosition(position = 9)
	@CsvBindByName(column="#AL Windows",required=false)
	private String contacts;
	@CsvBindByPosition(position = 10)
	@CsvBindByName(column="Action",required=false)
	private String action;
	@CsvBindByPosition(position = 11)
	@CsvBindByName(column="Bid Date",required=false)
	private String bidDate;
	@CsvBindByPosition(position = 12)
	@CsvBindByName(column="State",required=false)
	private String state;
	@CsvBindByPosition(position = 14)
	@CsvBindByName(column="Address",required=false)
	private String address;
	@CsvBindByPosition(position = 15)
	@CsvBindByName(column="City",required=false)
	private String city;
	@CsvBindByPosition(position = 16)
	@CsvBindByName(column="Zip",required=false)
	private String zip;
	@CsvBindByPosition(position = 17)
	@CsvBindByName(column="Value",required=false)
	private int value;
	@CsvBindByPosition(position = 18)
	@CsvBindByName(column="Sq. Ft.",required=false)
	private String sqft;
	@CsvBindByPosition(position = 22)
	@CsvBindByName(column="Link",required=false)
	private String link;
	@CsvBindByPosition(position = 23)
	@CsvBindByName(column="Folder Name",required=false)
	private String folderName;
	@CsvBindByPosition(position = 24)
	@CsvBindByName(column="Follow-Up",required=false)
	private String followUp;
	@CsvBindByPosition(position = 34)
	@CsvBindByName(column="Time Zone",required=false)
	private String timeZone;
	@CsvBindByPosition(position = 35)
	@CsvBindByName(column="TZ",required=false)
	private String timeZoneTxt;
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ProjectCsv))
			return false;	
		if (obj == this)
			return true;
		return (this.projectNumber.equals(((ProjectCsv) obj).projectNumber));
	}

	@Override
	public int hashCode(){
		return projectNumber.hashCode();//for simplicity reason
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the jobType
	 */
	public String getJobType() {
		return jobType;
	}

	/**
	 * @param jobType the jobType to set
	 */
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	/**
	 * @return the folderLink
	 */
	public String getFolderLink() {
		return folderLink;
	}

	/**
	 * @param folderLink the folderLink to set
	 */
	public void setFolderLink(String folderLink) {
		this.folderLink = folderLink;
	}

	/**
	 * @return the issueKey
	 */
	public String getIssueKey() {
		return issueKey;
	}

	/**
	 * @param issueKey the issueKey to set
	 */
	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}

	/**
	 * @return the pmLink
	 */
	public String getPmLink() {
		return pmLink;
	}

	/**
	 * @param pmLink the pmLink to set
	 */
	public void setPmLink(String pmLink) {
		this.pmLink = pmLink;
	}

	/**
	 * @return the projectNumber
	 */
	public String getProjectNumber() {
		return projectNumber;
	}

	/**
	 * @param projectNumber the projectNumber to set
	 */
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	/**
	 * @return the projectTitle
	 */
	public String getProjectTitle() {
		return projectTitle;
	}

	/**
	 * @param projectTitle the projectTitle to set
	 */
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	/**
	 * @return the alFrames
	 */
	public String getAlFrames() {
		return alFrames;
	}

	/**
	 * @param alFrames the alFrames to set
	 */
	public void setAlFrames(String alFrames) {
		this.alFrames = alFrames;
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
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the bidDate
	 */
	public String getBidDate() {
		return bidDate;
	}

	/**
	 * @param bidDate the bidDate to set
	 */
	public void setBidDate(String bidDate) {
		this.bidDate = bidDate;
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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the sqft
	 */
	public String getSqft() {
		return sqft;
	}

	/**
	 * @param sqft the sqft to set
	 */
	public void setSqft(String sqft) {
		this.sqft = sqft;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the folderName
	 */
	public String getFolderName() {
		return folderName;
	}

	/**
	 * @param folderName the folderName to set
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * @return the followUp
	 */
	public String getFollowUp() {
		return followUp;
	}

	/**
	 * @param followUp the followUp to set
	 */
	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getTimeZoneTxt() {
		return timeZoneTxt;
	}

	public void setTimeZoneTxt(String timeZoneTxt) {
		this.timeZoneTxt = timeZoneTxt;
	}

}
