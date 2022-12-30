/**
 * 
 */
package processExcelCMD;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author Shane
 *
 */
public class CmdProject {
	public static final String TITLE ="Project Title";
	public static final String ID ="Project ID";
	public static final String STAGE = "Stage";
	public static final String BID_DATE = "Bid Date";
	public static final String STATE = "State/Province";
	public static final String STREET = "Street Address";
	public static final String CITY = "City";
	public static final String VALUE = "Project Value";
	public static final String FLOOR = "Floor Area sq. ft.";
	public static final String ZIP = "Postal Code";

	public static final String TYPE_OLD = "Type";
	public static final String JOB_TYPE_OLD = "Job Type";
	public static final String FOLDER_LINK = "Folder Link";
	public static final String PM_LINK= "PM Link";
	public static final String NUM_OLD = "Project Number";
	public static final String ACTION_OLD = "Action";
	public static final String STATE_OLD = "State";
	public static final String ADDRESS_OLD = "Address";
	public static final String VALUE_OLD = "Value";
	public static final String SQFT_OLD = "Sq. Ft.";
	public static final String ZIP_OLD = "Zip";
	public static final String WEEK = "Week";
	public static final String YEAR = "Year";
	public static final String MONTH = "Month";
	public static final String LINK = "Link";
	public static final String FOLDER_NAME = "Folder Name";
	public static final String FOLLOW = "Follow-Up";
	public static final String ISSUE_TYPE = "Issue Type";
	public static final String ISSUE_KEY = "Issue key";
	public static final String TIME_ZONE = "Time Zone";
	public static final String TZ = "TZ";


	ArrayList<String> header;
	HashMap<String,Integer> hashIdx;
	/**
	 * 
	 */
	public CmdProject() {
		hashIdx = new HashMap<>();
		header = new ArrayList<>();
		header.add(TITLE);
		header.add(ID);
		header.add(STAGE);
		header.add(BID_DATE);
		header.add(STATE);
		header.add(STREET);
		header.add(CITY);
		header.add(VALUE);
		header.add(FLOOR);
		header.add(ZIP);

		header.add(TYPE_OLD);
		header.add(JOB_TYPE_OLD);
		header.add(FOLDER_LINK);
		header.add(PM_LINK);
		header.add(NUM_OLD);
		header.add(STATE_OLD);
		header.add(ACTION_OLD);
		header.add(ADDRESS_OLD);
		header.add(VALUE_OLD);
		header.add(SQFT_OLD);
		header.add(ZIP_OLD);
		header.add(WEEK);
		header.add(YEAR);
		header.add(MONTH);
		header.add(LINK);
		header.add(FOLDER_NAME);
		header.add(FOLLOW);
		header.add(ISSUE_TYPE);
		header.add(ISSUE_KEY);
		header.add(TIME_ZONE);
		header.add(TZ);
	}
	
	public int getIdx(String key)
	{
		return hashIdx.get(key).intValue();
	}
	
	public Project convertToProject(Row r)
	{
		Project project = new Project(r.getCell(hashIdx.get(TITLE)),r.getCell(hashIdx.get(ID)),r.getCell(hashIdx.get(STAGE)),r.getCell(hashIdx.get(BID_DATE)),
				r.getCell(hashIdx.get(STATE)),r.getCell(hashIdx.get(STREET)),r.getCell(hashIdx.get(CITY)),r.getCell(hashIdx.get(VALUE)),r.getCell(hashIdx.get(FLOOR)),r.getCell(hashIdx.get(ZIP)));
		return project;
	}
	public String rowToString(Row r)
	{
		String value = ID +":" + Double.valueOf(r.getCell(hashIdx.get(ID).intValue()).getNumericCellValue()).intValue()+" ";
		value +=TITLE +":"+ r.getCell(hashIdx.get(TITLE).intValue())+" ";
		return value;
	}
	public String toStringHash()
	{
		return hashIdx.toString();
	}
	public void processCell(Cell cell)
	{
		if(header.contains(cell.getStringCellValue()))
			hashIdx.put(cell.getStringCellValue(),Integer.valueOf(cell.getColumnIndex()));
	}
}
