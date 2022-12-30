package avalon.jira;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class BiddersCsv implements BaseCsv{
	public BiddersCsv() {
//		type = CsvType.BIDDER;
	}
	@CsvBindByName(column="Company Name",required=false)
//	@CsvBindByPosition(position = 0)
	private String companyName;	
	@CsvBindByName(column="Contact Name",required=false)
//	@CsvBindByPosition(position = 1)
	private String contactName;	
	@CsvBindByName(column="Added Date",required=false)
//	@CsvBindByPosition(position = 2)
	private String addedDate;	
	@CsvBindByName(column="Address",required=false)
//	@CsvBindByPosition(position = 3)
	private String address;	
	@CsvBindByName(column="Phone",required=false)
//	@CsvBindByPosition(position = 4)
	private String phone;	
//	@CsvBindByPosition(position = 5)
	@CsvBindByName(column="Email",required=false)
	private String email;	
	@CsvBindByName(column="Bidding Role",required=false)
//	@CsvBindByPosition(position = 6)
	private String biddingRole;	
	@CsvBindByName(column="Bid Rank",required=false)
//	@CsvBindByPosition(position = 7)
	private String bidRank;	
	@CsvBindByName(column="Bid Value",required=false)
//	@CsvBindByPosition(position = 8)
	private String bidValue;	
	@CsvBindByName(column="Fax Number",required=false)
//	@CsvBindByPosition(position = 9)
	private String fax;
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
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * @return the addedDate
	 */
	public String getAddedDate() {
		return addedDate;
	}
	/**
	 * @param addedDate the addedDate to set
	 */
	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
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
	 * @return the biddingRole
	 */
	public String getBiddingRole() {
		return biddingRole;
	}
	/**
	 * @param biddingRole the biddingRole to set
	 */
	public void setBiddingRole(String biddingRole) {
		this.biddingRole = biddingRole;
	}
	/**
	 * @return the bidRank
	 */
	public String getBidRank() {
		return bidRank;
	}
	/**
	 * @param bidRank the bidRank to set
	 */
	public void setBidRank(String bidRank) {
		this.bidRank = bidRank;
	}
	/**
	 * @return the bidValue
	 */
	public String getBidValue() {
		return bidValue;
	}
	/**
	 * @param bidValue the bidValue to set
	 */
	public void setBidValue(String bidValue) {
		this.bidValue = bidValue;
	}
	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}	

}
