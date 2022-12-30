/**
 * 
 */
package CrmImport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.bean.BeanField;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;


/**
 * @author scarleton3
 *
 */
public class CrmImport {
	public static Logger logger=LoggerFactory.getLogger(CrmImport.class);

	private static String AVALON_DIR="C:\\Users\\scarleton\\Google Drive\\Avalon";
	HashSet<BarCsv> barList=new HashSet<>();
	HashSet<BiddersCsv> biddersList=new HashSet<>();
	HashSet<ContactCsv> contactAddsList = new HashSet<>();
	HashSet<CompanyCsv> companyAddsList = new HashSet<>();
	HashMap<String,ProjectCsv> projectsHashMap = new HashMap<>();
	HashMap<Long,List<BarCsv>> projectBarHash = new HashMap<>();
	HashMap<Long,List<BiddersCsv>> projectBidderHash = new HashMap<>();

	public void collectCsvData() throws IOException, InterruptedException {
		File projectsFile = new File(AVALON_DIR+"\\Projects.xlsx");
		File contactsFile = new File(AVALON_DIR+"\\Contacts.xlsx");
		File companiesFile = new File(AVALON_DIR+"\\Companies.xlsx");
		File quotesFile = new File(AVALON_DIR+"\\Quotes.xlsx");
		ProcessBuilder pb = new ProcessBuilder("cscript","xlsTocsv.vbs",contactsFile.getAbsolutePath(),contactsFile.getAbsolutePath().replace("xlsx", "csv"));
		pb.directory(new File(AVALON_DIR));
		Process process = pb.start();
		int errCode = process.waitFor();
		pb = new ProcessBuilder("cscript","xlsTocsv.vbs",companiesFile.getAbsolutePath(),companiesFile.getAbsolutePath().replace("xlsx", "csv"));
		pb.directory(new File(AVALON_DIR));
		process = pb.start();
		errCode = process.waitFor();
		pb = new ProcessBuilder("cscript","xlsTocsv.vbs",quotesFile.getAbsolutePath(),quotesFile.getAbsolutePath().replace("xlsx", "csv"));
		pb.directory(new File(AVALON_DIR));
		process = pb.start();
		errCode = process.waitFor();
		pb = new ProcessBuilder("cscript","xlsTocsv.vbs",projectsFile.getAbsolutePath(),projectsFile.getAbsolutePath().replace("xlsx", "csv"));
		pb.directory(new File(AVALON_DIR));
		process = pb.start();
		errCode = process.waitFor();
		Reader contacts = new FileReader(contactsFile.getAbsolutePath().replace("xlsx", "csv"));
		Reader companies = new FileReader(companiesFile.getAbsolutePath().replace("xlsx", "csv"));
		Reader quotes = new FileReader(quotesFile.getAbsolutePath().replace("xlsx", "csv"));
		Reader projects = new FileReader(projectsFile.getAbsolutePath().replace("xlsx", "csv"));

		List<QuotesCsv> quotesList = new CsvToBeanBuilder<QuotesCsv>(quotes).withType(QuotesCsv.class).withSkipLines(4).build().parse();
		List<ContactCsv> contactList = new CsvToBeanBuilder<ContactCsv>(contacts).withType(ContactCsv.class).withSkipLines(4).build().parse();
		List<CompanyCsv> companyList = new CsvToBeanBuilder<CompanyCsv>(companies).withType(CompanyCsv.class).withSkipLines(4).build().parse();
		List<ProjectCsv> projectList = new CsvToBeanBuilder<ProjectCsv>(projects).withType(ProjectCsv.class).withSkipLines(4).build().parse();

//		projectsHashMap.put(project.getProjectNumber(),project);
}
	
	public void collectData() {
		File projectsFile = new File(AVALON_DIR+"\\Job Tracker.xlsm");
		try(Reader projects = new FileReader(projectsFile.getAbsolutePath().replace("xlsm", "csv"));) {
			ProcessBuilder pb = new ProcessBuilder("cscript","xlsTocsv.vbs",projectsFile.getAbsolutePath(),projectsFile.getAbsolutePath().replace("xlsm", "csv"));
			pb.directory(new File(AVALON_DIR));
			Process process = pb.start();
			int errCode = process.waitFor();
			List<ProjectCsv> projectsListNoFilter = new CsvToBeanBuilder<ProjectCsv>(projects).withType(ProjectCsv.class).withSkipLines(1).build().parse();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y");		
			for(ProjectCsv project:projectsListNoFilter) {
				try {
				LocalDate projectBidDate = LocalDate.parse(project.getBidDate(), formatter);
				boolean bidding = projectBidDate.isAfter(LocalDate.now().minusDays(5));
				if(bidding) 
					projectsHashMap.put(project.getProjectNumber(),project);
				}catch(DateTimeParseException e) {
					logger.debug(e.getMessage());
					continue;
				}
			}
			File dir = new File(AVALON_DIR);
			Pattern id = Pattern.compile(".*\\d{10}.*");
			IOFileFilter regexfilter =   new RegexFileFilter(id);
			IOFileFilter filterxls = FileFilterUtils.suffixFileFilter("xls");
			IOFileFilter filterfiles = FileFilterUtils.and(regexfilter,filterxls);
			IOFileFilter filtercsv = FileFilterUtils.suffixFileFilter("csv");
			Collection<File> found = FileUtils.listFiles(dir,filterfiles, TrueFileFilter.INSTANCE);
			for (File f : found) {
				String fileDir = f.getParent();
				String dirOnly = fileDir.substring(fileDir.lastIndexOf("\\")+1,fileDir.length());
				// Only collect projects that are current
				if(!projectsHashMap.containsKey(dirOnly))
					continue;
				System.out.println("File is:" +f.getAbsolutePath());
				File fcsv = new File(f.getAbsolutePath().replace("xls", "csv"));
				if(!fcsv.exists()) {
					pb = new ProcessBuilder("cscript","xlsTocsv.vbs",f.getAbsolutePath(),f.getAbsolutePath().replace("xls", "csv"));
					pb.directory(new File(AVALON_DIR));
					process = pb.start();
					errCode = process.waitFor();
				}
				try {
					Long projectNumber = Long.valueOf(dirOnly);
					try(Reader bardata = new FileReader(f.getAbsolutePath().replace("xls", "csv"));
							Reader bidderdata = new FileReader(f.getAbsolutePath().replace("xls", "csv"));){
						CsvToBean<BarCsv> bar =new CsvToBeanBuilder<BarCsv>(bardata).withType(BarCsv.class).build();
						CsvToBean<BiddersCsv> bidders =new CsvToBeanBuilder<BiddersCsv>(bidderdata).withType(BiddersCsv.class).build();
						if(f.getAbsolutePath().contains("BAR")) {
							List<BarCsv> tmp = bar.parse();
							tmp.remove(tmp.size()-1);
							barList.addAll(tmp);
							bardata.close();
							bidderdata.close();
							projectBarHash.put(projectNumber, tmp);
						}else {
							List<BiddersCsv> tmp = bidders.parse();
							tmp.remove(tmp.size()-1);
							biddersList.addAll(tmp);
							bardata.close();
							bidderdata.close();
							projectBidderHash.put(projectNumber, tmp);
						}

					} catch(FileNotFoundException e) {
						e.printStackTrace();
					}
				} catch(NumberFormatException e) {
					logger.debug("Could not convert: " + dirOnly);
					continue;
				}
			}
		} catch (Exception e ) {
			e.printStackTrace();
		}
	}
	private static String output(InputStream inputStream) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + System.getProperty("line.separator"));
			}
		} finally {
			br.close();
		}
		return sb.toString();
	}

	public static <T> Set<T> findDuplicates(Collection<T> collection) {
		Set<T> uniques = new HashSet<>();
		return collection.stream()
				.filter(e -> !uniques.add(e))
				.collect(Collectors.toSet());
	}

	//	public static void main(String[] args){
	//		try {
	//			for(BarCsv bar:barList) {
	//				if(bar.getContact()==null || bar.getContact()=="")
	//					bar.setCompanyName("Null");
	//				if(bar.getCompanyName()==null )
	//					bar.setCompanyName("");
	//				ContactCsv dupContact = contactList.stream().filter(x-> {
	//					logger.debug("BAR: " + bar.getContact() + "BAR Company: " + bar.getCompanyName() + "x Sum: " + x.getSummary() + " xcomp:" + x.getCompany());
	//					if(bar.getContact().replace("  ", " ").equals(x.getSummary()) & bar.getCompanyName().equals(x.getCompany()))
	//						return true;
	//					return false;
	//				}).findAny().orElse(null);
	//				CompanyCsv dupCompany = companyList.stream().filter(x-> bar.getCompanyName().equals(x.getSummary()))
	//						.findAny().orElse(null);
	//				ContactCsv contact = new ContactCsv();
	//				CompanyCsv comp = new CompanyCsv();
	//				if(dupContact == null) {
	//					contact.setSummary(bar.getContact());
	//					contact.setCompany(bar.getCompanyName());
	//					contact.setEmail(bar.getEmail());
	//					contact.setPhoneBusiness(bar.getPhone());
	//					contactAddsList.add(contact);
	//				}
	//				if(dupCompany == null) {
	//					comp.setSummary(bar.getCompanyName());
	//					comp.setContacts(bar.getContact());
	//					companyAddsList.add(comp);
	//				}
	//			}
	//			for(BiddersCsv bidder:biddersList) {
	//				if(bidder.getContactName()==null|| bidder.getContactName()=="")
	//					bidder.setContactName("Null");
	//				if(bidder.getCompanyName()==null)
	//					bidder.setCompanyName("");
	//				ContactCsv dupContact = contactList.stream().filter(x-> 
	//				bidder.getContactName().replace("  ", " ").equals(x.getSummary())&
	//				bidder.getCompanyName().equals(x.getCompany()))
	//						.findAny().orElse(null);
	//				CompanyCsv dupCompany = companyList.stream().filter(x-> bidder.getCompanyName().equals(x.getSummary()))
	//						.findAny().orElse(null);
	//
	//				ContactCsv contact = new ContactCsv();
	//				CompanyCsv comp = new CompanyCsv();
	//				String address = bidder.getAddress();
	//				String add[] = null;
	//				if(address != null) {
	//					add = address.split(",");
	//					if(dupCompany == null) {
	//						int offset = 0;
	//						if(add.length > 4)
	//							offset++;
	//						comp.setSummary(bidder.getCompanyName());
	//						comp.setCompanyAddress(add[0]);
	//						comp.setCompanyCity(add[1+offset]);
	//						comp.setCompanyState(add[2+offset]);
	//						comp.setCompanyZip(add[3+offset]);
	//						comp.setContacts(bidder.getContactName());
	//						//						comp.setPreviousCustomer("false");
	//						//						comp.setSignedTC("false");
	//						companyAddsList.add(comp);
	//					}
	//					if(dupContact == null) {
	//						contact.setSummary(bidder.getContactName());
	//						contact.setCompany(bidder.getCompanyName());
	//						contact.setStreetAddress(bidder.getAddress());
	//						contact.setEmail(bidder.getEmail());
	//						contact.setPhoneBusiness(bidder.getPhone());
	//						contactAddsList.add(contact);
	//					}
	//				}
	//			}
	//			//			List<ContactCsv> dedup = new ArrayList<>(findDuplicates(contactList));
	//			Set<ContactCsv> scon = new HashSet<>();
	//			Set<CompanyCsv> scom = new HashSet<>();
	//			scon.addAll(contactAddsList);
	//			contactAddsList.clear();
	//			contactAddsList.addAll(scon);
	//			scom.addAll(companyAddsList);
	//			companyAddsList.clear();
	//			companyAddsList.addAll(scom);
	//
	//			final CustomMappingStrategy<ContactCsv> mappingStrategyCon = new CustomMappingStrategy<>();
	//			mappingStrategyCon.setType(ContactCsv.class);
	//			final CustomMappingStrategy<CompanyCsv> mappingStrategyCom = new CustomMappingStrategy<>();
	//			mappingStrategyCom.setType(CompanyCsv.class);
	//			Writer writercon = new FileWriter(AVALON_DIR+"\\NewContacts.csv");
	//			Writer writercom = new FileWriter(AVALON_DIR+"\\NewCompanies.csv");
	//			StatefulBeanToCsv<ContactCsv> beanToCsvCon= new StatefulBeanToCsvBuilder<ContactCsv>(writercon).withMappingStrategy(mappingStrategyCon).build();
	//			StatefulBeanToCsv<CompanyCsv> beanToCsvCom= new StatefulBeanToCsvBuilder<CompanyCsv>(writercom).withMappingStrategy(mappingStrategyCom).build();
	//			beanToCsvCon.write(new ArrayList<>(contactAddsList));
	//			beanToCsvCom.write(new ArrayList<>(companyAddsList));
	//			writercon.close();
	//			writercom.close();
	//		} catch (Exception e ) {
	//			e.printStackTrace();
	//		}
	//	}
	static class CustomMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {
		@Override
		public String[] generateHeader() {
			final int numColumns = findMaxFieldIndex();
			if (!isAnnotationDriven() || numColumns == -1) {
				return super.generateHeader();
			}

			header = new String[numColumns + 1];

			BeanField beanField;
			for (int i = 0; i <= numColumns; i++) {
				beanField = findField(i);
				String columnHeaderName = extractHeaderName(beanField);
				header[i] = columnHeaderName;
			}
			return header;
		}

		private String extractHeaderName(final BeanField beanField) {
			if (beanField == null || beanField.getField() == null || beanField.getField().getDeclaredAnnotationsByType(CsvBindByName.class).length == 0) {
				return StringUtils.EMPTY;
			}

			final CsvBindByName bindByNameAnnotation = beanField.getField().getDeclaredAnnotationsByType(CsvBindByName.class)[0];
			return bindByNameAnnotation.column();
		}
	}
	/**
	 * @return the barList
	 */
	public HashSet<BarCsv> getBarList() {
		return barList;
	}
	/**
	 * @param barList the barList to set
	 */
	public void setBarList(HashSet<BarCsv> barList) {
		this.barList = barList;
	}
	/**
	 * @return the biddersList
	 */
	public HashSet<BiddersCsv> getBiddersList() {
		return biddersList;
	}
	/**
	 * @param biddersList the biddersList to set
	 */
	public void setBiddersList(HashSet<BiddersCsv> biddersList) {
		this.biddersList = biddersList;
	}
	/**
	 * @return the contactAddsList
	 */
	public HashSet<ContactCsv> getContactAddsList() {
		return contactAddsList;
	}
	/**
	 * @param contactAddsList the contactAddsList to set
	 */
	public void setContactAddsList(HashSet<ContactCsv> contactAddsList) {
		this.contactAddsList = contactAddsList;
	}
	/**
	 * @return the companyAddsList
	 */
	public HashSet<CompanyCsv> getCompanyAddsList() {
		return companyAddsList;
	}
	/**
	 * @param companyAddsList the companyAddsList to set
	 */
	public void setCompanyAddsList(HashSet<CompanyCsv> companyAddsList) {
		this.companyAddsList = companyAddsList;
	}
	/**
	 * @return the projectsList
	 */
	public HashMap<String,ProjectCsv> getProjectsList() {
		return projectsHashMap;
	}
	/**
	 * @param projectsList the projectsList to set
	 */
	public void setProjectsList(HashMap<String,ProjectCsv> projectsList) {
		this.projectsHashMap = projectsList;
	}
	/**
	 * @return the projectBarHash
	 */
	public HashMap<Long, List<BarCsv>> getProjectBarHash() {
		return projectBarHash;
	}
	/**
	 * @param projectBarHash the projectBarHash to set
	 */
	public void setProjectBarHash(HashMap<Long, List<BarCsv>> projectBarHash) {
		this.projectBarHash = projectBarHash;
	}
	/**
	 * @return the projectBidderHash
	 */
	public HashMap<Long, List<BiddersCsv>> getProjectBidderHash() {
		return projectBidderHash;
	}
	/**
	 * @param projectBidderHash the projectBidderHash to set
	 */
	public void setProjectBidderHash(HashMap<Long, List<BiddersCsv>> projectBidderHash) {
		this.projectBidderHash = projectBidderHash;
	}
}