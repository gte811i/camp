/**
 * 
 */
package CrmImport;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import CrmImport.BaseCsv.CsvType;

/**
 * @author scarleton3
 *
 */
public class BarCsv implements BaseCsv{
	public BarCsv() {
//		type = CsvType.BAR;
	}

	@CsvBindByName(column="Status",required=false)
//	@CsvBindByPosition(position = 0)
	private String status;	
	@CsvBindByName(column="Activity Level",required=false)
//	@CsvBindByPosition(position = 1)
	private String activityLevel;	
	@CsvBindByName(column="Contact",required=false)
//	@CsvBindByPosition(position = 2)
	private String contact;	
	@CsvBindByName(column="Company Name",required=false)
//	@CsvBindByPosition(position = 3)
	private String companyName;	
	@CsvBindByName(column="Source",required=false)
//	@CsvBindByPosition(position = 4)
	private String source;	
	@CsvBindByName(column="Phone",required=false)
//	@CsvBindByPosition(position = 5)
	private String phone;	
	@CsvBindByName(column="Email",required=false)
//	@CsvBindByPosition(position = 6)
	private String email;	
	@CsvBindByName(column="Business Type",required=false)
//	@CsvBindByPosition(position = 7)
	private String businessType;	
	@CsvBindByName(column="Trades",required=false)
//	@CsvBindByPosition(position = 8)
	private String trades;	
	@CsvBindByName(column="Last Active",required=false)
//	@CsvBindByPosition(position = 9)
	private String lastActive;
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the activityLevel
	 */
	public String getActivityLevel() {
		return activityLevel;
	}
	/**
	 * @param activityLevel the activityLevel to set
	 */
	public void setActivityLevel(String activityLevel) {
		this.activityLevel = activityLevel;
	}
	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	 * @return the businessType
	 */
	public String getBusinessType() {
		return businessType;
	}
	/**
	 * @param businessType the businessType to set
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	/**
	 * @return the trades
	 */
	public String getTrades() {
		return trades;
	}
	/**
	 * @param trades the trades to set
	 */
	public void setTrades(String trades) {
		this.trades = trades;
	}
	/**
	 * @return the lastActive
	 */
	public String getLastActive() {
		return lastActive;
	}
	/**
	 * @param lastActive the lastActive to set
	 */
	public void setLastActive(String lastActive) {
		this.lastActive = lastActive;
	}	

}
