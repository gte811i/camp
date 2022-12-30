package processExcelCMD;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 
 */

/**
 * @author scarleton3
 *
 */
public class Project {
	public Project(Cell ty,Cell t,Cell i,Cell s,Cell bd,Cell st, Cell add, Cell c,Cell v, Cell sf,Cell zc){
		type = ty;
		title=t;
		id=i;
		stage=s;
		bidDate=bd;
		state=st;
		address = add;
		city = c;
		value = v;
		sqft = sf;
		zip = zc;
	}
	public Project(Cell t,Cell i,Cell s,Cell bd,Cell st, Cell add, Cell c,Cell v, Cell sf,Cell zc){
		title=t;
		id=i;
		stage=s;
		bidDate=bd;
		state=st;
		address = add;
		city = c;
		value = v;
		sqft = sf;
		zip = zc;
	}
	/**
	 * 
	 */
	public Project() {
		// TODO Auto-generated constructor stub
	}
	public Cell type;
	public Cell title;
	public Cell id;
	public Cell stage;
	public Cell bidDate;
	public Cell state;
	public Cell address;
	public Cell city;
	public Cell value;
	public Cell sqft;
	public Cell zip;
	
	private Cell summary;
	private Cell issueKey;
	private Cell issueId;
	private Cell status;
	private Cell priority;
	private Cell timeZone;
	/**
	 * @return the summary
	 */
	public Cell getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(Cell summary) {
		this.summary = summary;
	}
	/**
	 * @return the issueKey
	 */
	public Cell getIssueKey() {
		return issueKey;
	}
	/**
	 * @param issueKey the issueKey to set
	 */
	public void setIssueKey(Cell issueKey) {
		this.issueKey = issueKey;
	}
	/**
	 * @return the issueId
	 */
	public Cell getIssueId() {
		return issueId;
	}
	/**
	 * @param issueId the issueId to set
	 */
	public void setIssueId(Cell issueId) {
		this.issueId = issueId;
	}
	/**
	 * @return the status
	 */
	public Cell getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Cell status) {
		this.status = status;
	}
	/**
	 * @return the priority
	 */
	public Cell getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Cell priority) {
		this.priority = priority;
	}
	/**
	 * @return the timeZone
	 */
	public Cell getTimeZone() {
		return timeZone;
	}
	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(Cell timeZone) {
		this.timeZone = timeZone;
	}
}
