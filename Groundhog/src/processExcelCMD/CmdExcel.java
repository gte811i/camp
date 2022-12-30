package processExcelCMD;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import processExcelCMD.CmdProject;
import processExcelCMD.Project;
import utils.CmdLogger;

/**
 * @author Shane
 *
 */

public class CmdExcel extends CmdLogger{
	private static String trackerFolder = "C://Users//scarleton//Google Drive//Avalon//";
	private static String downloadFolder = "C://Users//scarleton//Downloads//";


	public static void copySpreadsheets(String fileName,String sheetName)
	{
		try(InputStream inTracker = new FileInputStream(trackerFolder+"Job Tracker.xlsm");
				InputStream inNew = new FileInputStream(trackerFolder + fileName + ".xls");
				Workbook wbNew = WorkbookFactory.create(inNew);) {
			// Excel Spreadsheet to copy from
			CmdProject cpy = new CmdProject();
			ArrayList<Project> newProjects = new ArrayList<>();

			Sheet sheetData = wbNew.getSheetAt(0);
			Row header = sheetData.getRow(0);
			for (Cell cell :header)
				cpy.processCell(cell);
			sheetData.removeRow(sheetData.getRow(0));
			// Print out the list of projects found
			for(Row r : sheetData)
			{
				logger.debug(cpy.rowToString(r));
				Project project = cpy.convertToProject(r);
				newProjects.add(project);
			}
			/// Old Data
			try(Workbook wb = WorkbookFactory.create(inTracker)){
				Sheet sheetCombined = wb.getSheet(sheetName);
				Row headerJT = sheetCombined.getRow(0);
				CmdProject jobTracker = new CmdProject();
				for (Cell cell :headerJT)
					jobTracker.processCell(cell);
				logger.debug(jobTracker.toStringHash());

				HashMap<Integer,Integer> mapId = new HashMap<>();
				for(Row r : sheetCombined)
				{
					Cell b =r.getCell(jobTracker.getIdx(CmdProject.NUM_OLD));
					Cell c =r.getCell(jobTracker.getIdx(CmdProject.TITLE));
					try{
						CellType type = b.getCellType();
						switch(type)
						{
						case NUMERIC:
							logger.debug("Numeric ID: " + Double.valueOf((b.getNumericCellValue())).intValue()+" Title: " + c.getStringCellValue());
							mapId.put(Double.valueOf((b.getNumericCellValue())).intValue(),r.getRowNum());
							break;
						case ERROR:
							break;
						case STRING:
						case FORMULA:
							logger.debug("ID: " + b.getStringCellValue()+" Title: " + c.getStringCellValue());
							mapId.put(Integer.getInteger(b.getStringCellValue()),r.getRowNum());
							break;
						default:
							break;
						}
					}catch(NullPointerException | IllegalStateException e)
					{
						logger.debug("Row Error:" + r.getRowNum());
						logger.error(e.toString());
						continue;
					}
				}

				int lastRow = sheetCombined.getLastRowNum() + 1;
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				CellStyle hlink_style = wb.createCellStyle();
				CreationHelper createHelper = wb.getCreationHelper();
				CellStyle cellStyle = wb.createCellStyle();

				for(Project p :newProjects)
				{
					Integer value = mapId.get(Double.valueOf(p.id.getNumericCellValue()).intValue());
					if(value != null)
					{
						String date = p.bidDate.getStringCellValue();
						CellType type = p.id.getCellType();
						switch(type)
						{
						case NUMERIC:
							logger.debug("Update Project ID: " + Double.valueOf((p.id.getNumericCellValue())).intValue()
									+" Title: " + p.title.getStringCellValue() + " Date: " + date);
							break;
						case STRING:
						case FORMULA:
							logger.debug("Update Project ID: " + p.id.getStringCellValue()
							+" Title: " + p.title.getStringCellValue() + " Date: " + date);
							break;
						default:
							logger.debug("Problem with Title: " + p.title.getStringCellValue() + " Date: " + date);
							break;
						}

						try{
							Date tmp = formatter.parse(date);
							sheetCombined.getRow(value.intValue()).getCell(jobTracker.getIdx(CmdProject.BID_DATE)).setCellValue(tmp);
						}catch(ParseException e){
							logger.debug("Unparsable Date:");
						}
						Row rr = sheetCombined.getRow(value.intValue());
						CellStyle styleCurrencyFormat = wb.createCellStyle();
						styleCurrencyFormat.setDataFormat((short)7);
						sheetCombined.getRow(value.intValue()).getCell(jobTracker.getIdx(CmdProject.TITLE)).setCellValue(p.title.getStringCellValue());
						sheetCombined.getRow(value.intValue()).getCell(jobTracker.getIdx(CmdProject.ACTION_OLD)).setCellValue(p.stage.getStringCellValue());

						if(p.zip.getCellType()==CellType.STRING)
						{
							if(rr.getCell(jobTracker.getIdx(CmdProject.ZIP_OLD))== null)
							{
								rr.createCell(jobTracker.getIdx(CmdProject.ZIP_OLD)).setCellValue(p.zip.getStringCellValue());
							}
							else
								sheetCombined.getRow(value.intValue()).getCell(jobTracker.getIdx(CmdProject.ZIP_OLD)).setCellValue(p.zip.getStringCellValue());
						}

						if(p.value.getCellType()==CellType.NUMERIC)
						{
							if(rr.getCell(jobTracker.getIdx(CmdProject.VALUE_OLD))== null)
							{
								rr.createCell(jobTracker.getIdx(CmdProject.VALUE_OLD)).setCellValue(p.value.getNumericCellValue());
							}
							else
								sheetCombined.getRow(value.intValue()).getCell(jobTracker.getIdx(CmdProject.VALUE_OLD)).setCellValue(p.value.getNumericCellValue());
						}
						if(p.sqft.getCellType()==CellType.STRING)
						{
							if(rr.getCell(jobTracker.getIdx(CmdProject.SQFT_OLD))== null)
								rr.createCell(jobTracker.getIdx(CmdProject.SQFT_OLD)).setCellValue(p.sqft.getStringCellValue());
							else
								sheetCombined.getRow(value.intValue()).getCell(jobTracker.getIdx(CmdProject.SQFT_OLD)).setCellValue(p.sqft.getStringCellValue());
						}
					}
					else{
						logger.debug("NEW ID Value: " + Double.valueOf(p.id.getNumericCellValue()).intValue()+ 
								" Title: " + p.title.getStringCellValue());
						cellStyle.setDataFormat(
								createHelper.createDataFormat().getFormat("m/d/yyyy"));
						Row pr = sheetCombined.getRow(lastRow-1);
						Row r = sheetCombined.createRow(lastRow++);
						r.createCell(jobTracker.getIdx(CmdProject.TITLE)).setCellValue(p.title.getStringCellValue());
						Hyperlink hl= p.title.getHyperlink();
						Hyperlink hll = createHelper.createHyperlink(HyperlinkType.URL);
						hll.setAddress(hl.getAddress());
						r.getCell(jobTracker.getIdx(CmdProject.TITLE)).setHyperlink(hll);
						try{
							Date tmp = formatter.parse(p.bidDate.getStringCellValue());
							r.createCell(jobTracker.getIdx(CmdProject.BID_DATE)).setCellValue(tmp);
							r.getCell(jobTracker.getIdx(CmdProject.BID_DATE)).setCellStyle(cellStyle); 	
						} catch(ParseException e){
							logger.debug("Title: " + p.title.getStringCellValue() + " "+ e.toString());
						}

						Cell fl_cell =pr.getCell(jobTracker.getIdx(CmdProject.FOLDER_LINK));
						Cell jt_cell =pr.getCell(jobTracker.getIdx(CmdProject.JOB_TYPE_OLD));
						Cell pml_cell =pr.getCell(jobTracker.getIdx(CmdProject.PM_LINK));
						Cell week_cell =pr.getCell(jobTracker.getIdx(CmdProject.WEEK));
						Cell year_cell =pr.getCell(jobTracker.getIdx(CmdProject.YEAR));
						Cell month_cell =pr.getCell(jobTracker.getIdx(CmdProject.MONTH));
						Cell fn_cell =pr.getCell(jobTracker.getIdx(CmdProject.FOLDER_NAME));
						Cell fu_cell =pr.getCell(jobTracker.getIdx(CmdProject.FOLLOW));
						Cell it_cell =pr.getCell(jobTracker.getIdx(CmdProject.ISSUE_TYPE));
						Cell time_cell =pr.getCell(jobTracker.getIdx(CmdProject.TIME_ZONE));
						Cell tz_cell =pr.getCell(jobTracker.getIdx(CmdProject.TZ));
						Cell link_cell =pr.getCell(jobTracker.getIdx(CmdProject.LINK));


						String fl_formula = fl_cell.getCellFormula().replace(String.valueOf(lastRow-1),String.valueOf(lastRow));
						String jt_formula = jt_cell.getCellFormula().replace(String.valueOf(lastRow-1),String.valueOf(lastRow));
						String pml_formula = pml_cell.getCellFormula().replace(String.valueOf(lastRow-1),String.valueOf(lastRow));
						String w_formula = week_cell.getCellFormula().replace(String.valueOf(lastRow-1),String.valueOf(lastRow));
						String m_formula = month_cell.getCellFormula().replace(String.valueOf(lastRow-1),String.valueOf(lastRow));
						String y_formula = year_cell.getCellFormula().replace(String.valueOf(lastRow-1),String.valueOf(lastRow));
						String fn_formula = fn_cell.getCellFormula().replace(String.valueOf(lastRow-1),String.valueOf(lastRow));
						String fu_formula = fu_cell.getCellFormula().replace(String.valueOf(lastRow-1),String.valueOf(lastRow));
						String time_formula = time_cell.getCellFormula().replace(String.valueOf(lastRow-1),String.valueOf(lastRow));
						String tz_formula = tz_cell.getCellFormula().replace(String.valueOf(lastRow-1),String.valueOf(lastRow));
						String link_formula = link_cell.getCellFormula().replace(String.valueOf(lastRow-1),String.valueOf(lastRow));


						r.createCell(jobTracker.getIdx(CmdProject.FOLDER_LINK)).setCellFormula(fl_formula);
						r.createCell(jobTracker.getIdx(CmdProject.LINK)).setCellFormula(link_formula);
						r.createCell(jobTracker.getIdx(CmdProject.JOB_TYPE_OLD)).setCellFormula(jt_formula);
						r.createCell(jobTracker.getIdx(CmdProject.PM_LINK)).setCellFormula(pml_formula);
						r.createCell(jobTracker.getIdx(CmdProject.WEEK)).setCellFormula(w_formula);
						r.createCell(jobTracker.getIdx(CmdProject.YEAR)).setCellFormula(y_formula);
						r.createCell(jobTracker.getIdx(CmdProject.MONTH)).setCellFormula(m_formula);
						r.createCell(jobTracker.getIdx(CmdProject.FOLDER_NAME)).setCellFormula(fn_formula);
						r.createCell(jobTracker.getIdx(CmdProject.FOLLOW)).setCellFormula(fu_formula);
						r.createCell(jobTracker.getIdx(CmdProject.ISSUE_TYPE)).setCellValue(it_cell.getStringCellValue());;
						r.createCell(jobTracker.getIdx(CmdProject.TIME_ZONE)).setCellFormula(time_formula);
						r.createCell(jobTracker.getIdx(CmdProject.TZ)).setCellFormula(tz_formula);

						r.createCell(jobTracker.getIdx(CmdProject.ACTION_OLD)).setCellValue(p.stage.getStringCellValue());
						r.createCell(jobTracker.getIdx(CmdProject.STATE_OLD)).setCellValue(p.state.getStringCellValue());
						r.createCell(jobTracker.getIdx(CmdProject.NUM_OLD)).setCellValue(p.id.getNumericCellValue());
						r.createCell(jobTracker.getIdx(CmdProject.ADDRESS_OLD)).setCellValue(p.address.getStringCellValue());
						r.createCell(jobTracker.getIdx(CmdProject.CITY)).setCellValue(p.city.getStringCellValue());
						r.createCell(jobTracker.getIdx(CmdProject.TYPE_OLD)).setCellValue(fileName);
						r.createCell(jobTracker.getIdx(CmdProject.SQFT_OLD)).setCellValue(p.sqft.getStringCellValue());
						r.createCell(jobTracker.getIdx(CmdProject.ZIP_OLD)).setCellValue(p.zip.getStringCellValue());

						r.getCell(jobTracker.getIdx(CmdProject.FOLDER_LINK)).setCellStyle(fl_cell.getCellStyle());
						r.getCell(jobTracker.getIdx(CmdProject.PM_LINK)).setCellStyle(pml_cell.getCellStyle());
						r.getCell(jobTracker.getIdx(CmdProject.FOLLOW)).setCellStyle(fu_cell.getCellStyle()); 

					}
				}

				try(FileOutputStream fileOut = new FileOutputStream(trackerFolder+"Job Tracker.xlsm")){
					wb.write(fileOut);
				}
				wb.close();
				wbNew.close();
			}
		} catch (EncryptedDocumentException | IOException e) {
			logger.error(e.toString());
		}
	}

	public static void main(String[] args){
		//		copySpreadsheets("A","Combined");
		//		copySpreadsheets("F","Combined");
		//		copySpreadsheets("C","Combined");
		//		copySpreadsheets("O","Combined");
		CmdExcel tmp = new CmdExcel();
		//		tmp.extractProjects();
		//		tmp.downloadDetails();
		//		tmp.importPrep();
		tmp.unzipProjects();
	}

	public void extractProjects()
	{
		try(InputStream pmPrep = new FileInputStream(trackerFolder+"Job Tracker.xlsm");
				Workbook avalon = WorkbookFactory.create(pmPrep)) {
			Sheet cmd = avalon.getSheet("Combined");
			Sheet export = avalon.getSheet("JT2");
			ArrayList<Row> copyRow = new ArrayList<>();
			CmdProject avalonHeader = new CmdProject();
			Row header = cmd.getRow(0);
			for (Cell cell :header)
				avalonHeader.processCell(cell);
			Consumer<Row> getDetails = (Row r) -> {
				try{
					if(r.getCell(avalonHeader.getIdx(CmdProject.ISSUE_KEY)).getCellType() == CellType.BLANK)
					{
						logger.debug("Need Import:" + r.getCell(avalonHeader.getIdx(CmdProject.TITLE)));
						copyRow.add(r);
					}
				}catch( NullPointerException e){
					logger.debug(e.toString());
				}
			};
			Consumer<Row> cpy = (Row r) -> {
			};
			cmd.forEach(getDetails);
			copyRow.forEach(cpy);
		} catch (IOException |EncryptedDocumentException e) {
			logger.error(e.toString());
		}

	}

	public ArrayList<String> downloadDetails()
	{
		ArrayList<String> links = new ArrayList<>();
		try(InputStream pmPrep = new FileInputStream(trackerFolder+"Job Tracker.xlsm");
				Workbook avalon = WorkbookFactory.create(pmPrep)) {
			Sheet cmd = avalon.getSheet("Combined");
			CmdProject avalonHeader = new CmdProject();
			Row header = cmd.getRow(0);
			for (Cell cell :header)
				avalonHeader.processCell(cell);
			Consumer<Row> getDetails = (Row r) -> 
			{
				int date = avalonHeader.getIdx(CmdProject.BID_DATE);
				try{
					if(r.getCell(date).getCellType() == CellType.NUMERIC)
					{
						Date bid = r.getCell(date).getDateCellValue();
						Date now= new Date();
						GregorianCalendar future = new GregorianCalendar();
						future.add(Calendar.DAY_OF_YEAR, 7);
						if(bid.after(now) && bid.before(future.getTime()))
						{
							Hyperlink link = r.getCell(avalonHeader.getIdx(CmdProject.TITLE)).getHyperlink();
							logger.debug("Get Details from:" + r.getCell(avalonHeader.getIdx(CmdProject.TITLE)));
							logger.debug("Address is:" + link.getAddress());
							links.add(link.getAddress());
						}
					}
				}catch( NullPointerException e){
					logger.debug(e.toString());
				}
			};
			cmd.forEach(getDetails);
		} catch (IOException |EncryptedDocumentException e) {
			logger.error(e.toString());
		}
		return links;
	}

	public void importPrep()
	{
		try(InputStream pmPrep = new FileInputStream(trackerFolder+"Job Tracker.xlsm");
				Workbook avalon = WorkbookFactory.create(pmPrep)) {
			Sheet cmd = avalon.getSheet("Combined");
			Row header = cmd.getRow(0);
			Sheet importPM = avalon.getSheet("PM Import");
			Row headerImport = importPM.getRow(0);
			cmd.removeRow(header);
			CmdProject cpy = new CmdProject();
			CmdProject paste = new CmdProject();
			for (Cell cell :header)
				cpy.processCell(cell);
			List<Project> transferProjects = new ArrayList<>();
			for(Row r : cmd)
			{
				logger.debug("Project ID New:" + r.getCell(cpy.getIdx(CmdProject.TITLE)));
				if(r.getCell(cpy.getIdx(CmdProject.BID_DATE)).getDateCellValue().after(new Date())){
					Project project = cpy.convertToProject(r);
					transferProjects.add(project);
				}
			}

		} catch (EncryptedDocumentException | IOException e) {
			logger.error(e.toString());
		}
	}

	public void unzipProjects()
	{
		File curDir = new File(trackerFolder);
		File[] zips = getZipFiles(curDir);
		if(zips == null) {
			logger.debug("No files to Unzip");
			return;
		}
			
		for(File f:zips)
		{
			String unzipName = f.getName().replaceAll("_", " ");
			Pattern pattern = Pattern.compile("[0-9]{10}+");
			Matcher matcher = pattern.matcher(unzipName);
			String unzipFolder; 
			if (matcher.find())
			{
				unzipName = matcher.group();
				try {
					unzipFolder = curDir.getPath()+"\\"+unzipName; 
					unzip(f.getPath(),unzipFolder);
					Consumer<Path> rename = (Path fi) -> 
					{
						File newFile= new File("");
						if(fi.getFileName().toString().startsWith("Project"))
						{
							String newName = fi.toFile().getParentFile().getParentFile().toPath() + "\\" + fi.getFileName();
							newFile = new File(newName);
							logger.debug("PPath:"+newFile.getAbsolutePath());
							logger.debug("PName1:"+fi);
							logger.debug("PName:"+fi.getFileName());
						}
						else
						{
							if(fi.getFileName().toString().length()>=19) {
								String newName = fi.toFile().getParentFile().getParentFile().getParentFile().toPath() + "\\"+fi.getFileName().toString().substring(19);
								newFile = new File(newName);
								logger.debug("Test: " + fi.getFileName().toString().substring(19));
								logger.debug("Path:"+newFile.getAbsolutePath());
								logger.debug("Name1:"+fi);
								logger.debug("Name:"+fi.getFileName());
							}
						}
						try {
							Files.move(fi, newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
						} catch (IOException e) {
							logger.debug(e.toString());
						}
					};
					Consumer<Path> delete = (Path fi) -> 
					{
						if(fi.getParent().endsWith("Avalon"))
						{
							logger.debug("Do not delete: " + fi);
						} else
							try {
								FileUtils.deleteDirectory(fi.toFile());
							} catch (IOException e) {
								logger.debug(e.toString());
							}
					};
					Files.walk(Paths.get(unzipFolder))
					.filter(Files::isRegularFile)
					.forEach(rename);
					Files.walk(Paths.get(unzipFolder))
					.filter(Files::isDirectory)
					.forEach(delete);
					FileUtils.deleteQuietly(f);
				} catch (IOException e) {
					logger.debug(e.toString());
				}
			}
		}
		Path start = Paths.get(curDir.toString());
		int maxDepth = 1;
		Consumer<Path> renameSpec = (Path fi) -> 
		{
			String specName = fi.getFileName().toString().replaceAll("_", " ");
			Pattern pattern = Pattern.compile("[0-9]{10}+");
			Matcher matcher = pattern.matcher(specName);
			String unzipFolder=""; 
			if (matcher.find()){
				unzipFolder = matcher.group();
				File uFolder = new File(fi.getParent()+"\\"+unzipFolder + "\\" + fi.getFileName());
				logger.debug("Folder: " + uFolder);
				logger.debug("File: " + fi);
				try {
					Files.move(fi, uFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
		};
		try (Stream<Path> stream = Files.find(start, maxDepth, (path, attr) ->
		String.valueOf(path).endsWith(".pdf"))) {
			stream.forEach(renameSpec);
		} catch (IOException e) {
			logger.debug(e.toString());
		}
	}
	private File[] getZipFiles(File curDir){
		File[] zipFiles = curDir.listFiles(new FileFilter(){
			private final String[] okFileExtensions = 
					new String[] {"zip"};
			@Override
			public boolean accept(File pathname) {
				for (String extension : okFileExtensions)
				{
					if (pathname.getName().toLowerCase().endsWith(extension))
					{
						return true;
					}
				}
				return false;
			}

		});
		return zipFiles;
	}

	public void unzip(String zipFilePath, String destDirectory) throws IOException {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		try(ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))){
			ZipEntry entry = zipIn.getNextEntry();
			// iterates over entries in the zip file
			while (entry != null) {
				String filePath = destDirectory + File.separator + entry.getName();
				File newFile = new File(filePath);
				new File(newFile.getParent()).mkdirs();
				extractFile(zipIn, filePath);
				zipIn.closeEntry();
				entry = zipIn.getNextEntry();
			}
			zipIn.close();
		}
	}
	/**
	 * Extracts a zip entry (file entry)
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));)
		{
			byte[] bytesIn = new byte[4096];
			int read = 0;
			while ((read = zipIn.read(bytesIn)) != -1) {
				bos.write(bytesIn, 0, read);
			}
			bos.close();
		}
	}

	private static void getAllFiles(File curDir) {
		File[] filesList = curDir.listFiles();
		for(File f : filesList){
			if(f.isDirectory())
				getAllFiles(f);
			if(f.isFile()){
				logger.debug(f.getName());
			}
		}
	}

	/**
	 * 
	 */
	public static void copyData() {
		File dir = new File(downloadFolder);
		FileFilter fileFilter = new WildcardFileFilter("Project List*.xls");
		long cutoff = System.currentTimeMillis()- (1000*60*60*4); //last hour
		FileFilter ageFilter = new AgeFileFilter(cutoff,false);
		File[] files = dir.listFiles(ageFilter);
		if(files == null) {
			logger.debug("No Files to Move From: " + downloadFolder +" To: " + trackerFolder);
			return;
		}
		for(File f: files)
		{
			logger.debug("File to move:"+f.toString());
			File moveTo = new File(trackerFolder+f.getName());
			if(moveTo.exists())
				moveTo.delete();
			try {
				FileUtils.moveFile(f, moveTo);
			} catch(IOException e){
				logger.error("Error copy data"+e);
			}
		}
	}

	/**
	 * 
	 */
	public static void copyExcelData() {
		File dir = new File(trackerFolder);
		FileFilter fileFilter = new WildcardFileFilter("*.xls");
		File[] files = dir.listFiles(fileFilter);
		if(files == null) {
			logger.debug("No Files to Copy Data From/To in: " + trackerFolder);
			return;
		}
		for(File f: files)
		{
			logger.debug("File to move:"+f.toString());
			Pattern id = Pattern.compile("\\d{10}");
			Matcher m = id.matcher(f.toString());
			if(m.find()){
				String projectId = m.group();
				File moveTo = new File(trackerFolder+projectId+"\\"+f.getName());
				if(moveTo.exists())
					moveTo.delete();
				try {
					FileUtils.moveFile(f, moveTo);
				} catch(IOException e){
					logger.error("Error copy data"+e);
				}
			}
		}
	}
	/**
	 * @param trackerFolder the trackerLocation to set
	 */
	public static void setTrackerFolder(String trackerFolder) {
		CmdExcel.trackerFolder = trackerFolder;
	}
	/**
	 * @return the trackerLocation
	 */
	public static String getTrackerFolder() {
		return trackerFolder;
	}

	/**
	 * @return the downloadFolder
	 */
	public static String getDownloadFolder() {
		return downloadFolder;
	}

	/**
	 * @param downloadFolder the downloadFolder to set
	 */
	public static void setDownloadFolder(String downloadFolder) {
		CmdExcel.downloadFolder = downloadFolder;
	}

}
