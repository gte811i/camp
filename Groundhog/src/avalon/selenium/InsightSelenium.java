/**
 * 
 */
package avalon.selenium;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.CmdLogger;

/**
 * @author scarleton3
 *
 */
//@Config(
//		browser = Browser.FIREFOX,
//		url     = "https://insight.cmdgroup.com"
//		)
@Component
public class InsightSelenium {
	@Value("${avalon.username}")
	private String userName;

	Pattern p;
	Matcher m;
	int attempts;
	int MAX_ATTEMPTS = 10;
	Duration sixty =Duration.ofSeconds(60);
	Duration thirty =Duration.ofSeconds(30);
	Duration five=Duration.ofSeconds(5);
	//	private static String userName="scarleton3";
	public static Logger logger=LoggerFactory.getLogger(CmdLogger.class);
	By searchSelect = By.id("drpSearch-inputEl");
	By moreBidders = By.id("lnkBidder");
	By moreBARs = By.id("lnkBuyersActivityReport");
	By searchList = By.className("x-boundlist-item");
	By search = By.id("spnSearchClose0");
	By matches = By.id("grdProjectMatches-body");
	By tree = By.id("treeview-1007");
	By record = By.id("treeview-1007-record-33");
	By grd = By.id("grdProjectMatches-body");
	// look for tr class: "unviewed-row x-grid-row"
	By gridName = By.className("x-grid-cell-colProjectName");
	By gridFav = By.className("x-grid-checkheader-favourite");
	By gridId = By.className("x-grid-cell-colProjectCode");
	By gridBidDate = By.className("x-grid-cell-colBidDate");
	By gridUpdateDate = By.className("x-grid-cell-colUpdateDate");
	By gridStage = By.className("x-grid-cell-colStage");
	By gridAddress = By.className("x-grid-cell-colStreetAddress");
	By gridCity = By.className("x-grid-cell-colCity");
	By gridCounty = By.className("x-grid-cell-colCounty");
	By gridState = By.className("x-grid-cell-colState");
	By gridZip = By.className("x-grid-cell-colPostalCode");
	By gridRole = By.className("x-grid-cell-colRole");
	By gridCompany = By.className("x-grid-cell-colCompanyName");
	By gridContact = By.className("x-grid-cell-colContactName");
	By gridPhone = By.className("x-grid-cell-colPhone");
	By gridEmail = By.className("x-grid-cell-colEmail");
	By gridValue = By.className("x-grid-cell-colValue");
	By gridFloorArea = By.className("x-grid-cell-colFloorArea");
	By gridMatchingDocs = By.className("x-grid-cell-colMatchingDocs");

	By unviewed = By.className("unviewed-row");
	By viewed = By.className("viewed-row");
	By downloadDocs = By.id("projectDownloadDoc");
	By fav = By.id("spnProjectFavourite");
	By specDocs = By.className("specDocs");
	By planDocs = By.className("planDocs");
	By planAddendaDocs = By.className("planAddendaDocs");
	By expandor = By.className("fancytree-expander");
	By checkbox = By.className("fancytree-checkbox");
	By match = By.className("matchDocs");
	By title = By.className("fancytree-title");
	By lastsib = By.className("fancytree-lastsib");
	By regNode = By.className("regularNode");
	By selectedNode = By.className("selectedNode");
	By exportType = By.id("exportTypes");
	By showExportDetails = By.id("showExportDetails");
	By menuExport = By.id("menuExport");
	By btnExport = By.id("BtnExport");
	By btnIncludePrjDetails = By.id("chkBoxExportProjectInfo");
	By corner = By.className("ui-corner-all");
	//"x-menu-item-link"
	//"x-menu-item-default" "x-menu-item"
	By newTab = By.id("menuitem-1002-textEl");
	By favorite = By.className("star-header");
	By favo = By.id("spnProjectFavourite");
	By favor = By.className("project-header-color");
	//	By mark = By.id("btnMarkAs-btnEl");
	//	By mark = By.id("btnMarkAs-btnEl");
	String baseUrl = "https://insight.cmdgroup.com";

	By mark = By.id("spnMarkAs");
	String orgHandle;
	private WebDriver driver; 

	public void loginCMD() {
//		System.setProperty("webdriver.firefox.marionette","C:\\geckodriver.exe");
//		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		driver = new ChromeDriver();
		//comment the above 2 lines and uncomment below 2 lines to use Chrome
		//WebDriver driver = new ChromeDriver();

		String expectedTitle = "Welcome: Mercury Tours";
		String actualTitle = "";

		// launch Fire fox and direct it to the Base URL
		driver.get(baseUrl);

		validatePresent(HomePage.LOC_LNK_LOGIN);
		By userName = By.id("txtUserName");
		By password = By.id("txtPassword");
		By btnLogon = By.id("btnLogon");
		setText(userName, "gte811i@hotmail.com");
		setText(password, "LaMas123");
		click(btnLogon);
		orgHandle = driver.getWindowHandle();
	}
	public boolean isPresent(By by) {
		return driver.findElements(by).size() > 0;
	}
	public void validatePresent(By by) {
		waitForElement(by);
		assertTrue("Element " + by.toString() + " does not exist!",
				isPresent(by));
		return;
	}
	public void click(String css) {
		click(By.cssSelector(css));
		return;
	}
	public WebElement waitForElement(By by) {
		int attempts = 0;
		int size = driver.findElements(by).size();

		while (size == 0) {
			size = driver.findElements(by).size();
			if (attempts == MAX_ATTEMPTS) fail(String.format("Could not find %s after %d seconds",
					by.toString(),
					MAX_ATTEMPTS));
			attempts++;
			try {
				Thread.sleep(1000); // sleep for 1 second.
			} catch (Exception x) {
				fail("Failed due to an exception during Thread.sleep!");
				x.printStackTrace();
			}
		}

		if (size > 1) System.err.println("WARN: There are more than 1 " + by.toString() + " 's!");

		return driver.findElement(by);
	}
	public void click(By by) {
		waitForCondition(ExpectedConditions.not(ExpectedConditions.invisibilityOfElementLocated(by)));
		waitForCondition(ExpectedConditions.elementToBeClickable(by));
		waitForElement(by).click();
		return;
	}
	public void waitForCondition(ExpectedCondition<?> condition) {
		waitForCondition(condition, 60);
		return;
	}

	public void waitForCondition(ExpectedCondition<?> condition, long timeOutInSeconds) {
		waitForCondition(condition, timeOutInSeconds, 1000); // poll every second
		return;
	}

	public void waitForCondition(ExpectedCondition<?> condition, long timeOutInSeconds, long sleepInMillis) {
		WebDriverWait wait = new WebDriverWait(driver, sixty);
		wait.until(condition);
		return;
	}
	public void setText(By by, String text) {
		waitForCondition(ExpectedConditions.not(ExpectedConditions.invisibilityOfElementLocated(by)));
		waitForCondition(ExpectedConditions.elementToBeClickable(by));
		WebElement element = waitForElement(by);
		element.clear();
		element.sendKeys(text);
		return;
	}
	public void openUrl(String name)
	{
		this.baseUrl = name;
	}

	public void openSearch(String searchName){
		logger.debug("Opening Search for: " + searchName);
		By searchTerm = By.id(searchName);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new WebDriverWait(driver, sixty).until((WebDriver dr1) -> dr1.findElement(searchSelect));
		((JavascriptExecutor) driver).executeScript("scroll(0,0)","");
		Actions actionsOS = new Actions(driver);
		WebElement ss = driver.findElement(searchSelect);
		actionsOS.moveToElement(ss).click().perform();
		for(WebElement we: driver.findElements(searchTerm))
		{
			logger.debug("Test:" + we.getText());
			actionsOS.moveToElement(we).perform();
		}
		WebElement st = driver.findElement(searchTerm);
		actionsOS.click(st);
		actionsOS.perform();
	}
	public void openProject(){
		new WebDriverWait(driver, thirty).until((WebDriver dr1) -> dr1.findElement(record));
		driver.findElement(record).getText();
		WebElement prjName = driver.findElement(record).findElement(gridName);
		logger.debug("Name:");
		logger.debug(prjName.getText());
		logger.debug(driver.findElement(record).getText());
		logger.debug("DONE:");
		prjName.click();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		new WebDriverWait(driver, sixty).until((WebDriver dr1) -> dr1.findElement(downloadDocs));
		driver.findElement(downloadDocs).click();
		waitForWindow(".*Document.*");//documentcenter.cmdgroup.com");
		switchToWindow(".*Document.*");
	}

	public void exportPdf() throws InterruptedException
	{
		driver.findElement(exportType).click();
		WebElement multiE = driver.findElement(menuExport);//.findElement(multi);
		List<WebElement> cl= multiE.findElements(corner);
		cl.get(0).click();
		driver.findElement(btnExport).click();
	}

	public void exportZip() throws InterruptedException
	{
		driver.findElement(exportType).click();
		WebElement multiEE = driver.findElement(menuExport);//.findElement(multi);
		List<WebElement> cll= multiEE.findElements(corner);
		cll.get(1).click();
		driver.findElement(exportType).click();
		driver.findElement(btnIncludePrjDetails).click();
		driver.findElement(btnExport).click();
		Thread.sleep(3000);
		driver.findElement(exportType).click();
		driver.findElement(btnIncludePrjDetails).click();
	}

	public void downloadSpec() throws InterruptedException
	{
		Pattern glazingDoc = Pattern.compile(".*\\bGlazing\\b.*");
		String glazing = "Glazing";
		Thread.sleep(1500); //need to sleep .5 sec for 

		List<WebElement> allElements = driver.findElements(By.xpath("//div[@id='plansTree']/ul/li")); 
		StateMachine sm = new StateMachine();
		String spec = new String();
		for (WebElement element: allElements) {
			List<WebElement> moreElement = element.findElements(title);
			logger.debug("Elements: " + element.getText());
			for(WebElement e: moreElement)
			{
				logger.debug(":"+e.getText());

				String[] list = e.getText().split("\n");

				if(sm.currentState.equals(State.MatchSpecState))
				{
					String[] lista = e.getText().split("\\(");
					logger.debug("State Machine Spec Section: " + lista[0]);
					if(lista[0].equals("Structural "))
					{
						WebElement structDoc = e.findElement(By.xpath("parent::*")).findElement(checkbox);
						structDoc.click();
						exportPdf();
						structDoc.click();
						driver.findElement(match).findElement(expandor).click();
					}
					else{
						Actions actionsIs = new Actions(driver);
						driver.findElement(match).findElement(expandor).click();
						spec = lista[0];
						spec = spec.replaceAll("^[0-9]*", "$0 ");
						sm.onEventTextValue(lista[0]);
						By bySpec = By.xpath("//span[text()='"+spec+"']/parent::span");
						Thread.sleep(500);
						try{
							WebElement specs = driver.findElement(specDocs).findElement(expandor);
							specs.click();

							///-----
							List<WebElement> docs = driver.findElements(title);
							List<WebElement> docsText = docs.stream().filter(d -> d.getText().length() > 0).collect(Collectors.toList());
							for(WebElement d : docsText){
								logger.debug("Documents:" + d.getText().length() + ":" + d.getText());
								String docText = d.getText();
								if(docText.contains(glazing))
								{
									try{
										boolean found = false;
										WebElement chk = d.findElement(By.xpath("parent::*")).findElement(checkbox);
										while(!found) {
											found=clickDocuments(docText,actionsIs, chk,true);
										}
									}
									catch(StaleElementReferenceException ee)
									{
										logger.debug("Can't find: " + ee);
									}
								}
							}
							//------


							List<WebElement> allSpecSections = driver.findElements(bySpec);
							logger.debug("Specs: " + allSpecSections.toString());
							for(WebElement we: allSpecSections){
								//						System.out.println("All: " + we.getText().length());
								if(we.getText().length()>0){
									WebElement specSection =we.findElement(By.xpath("parent::*")).findElement(checkbox);
									actionsIs.moveToElement(specSection);
									//actions.click();
									actionsIs.perform();
									clickExportResize(specSection);
								}
							}
							actionsIs.moveToElement(specs);
							actionsIs.click();
							actionsIs.perform();
						} catch(NoSuchElementException ne) {
							logger.debug("No SpecDocs Element");
						}
					}
					//					WebElement specSection = allSpecSections.get(0).findElement(By.xpath("parent::*")).findElement(checkbox);
					return;
				}
				sm.onEventTextValue(list[0]);
			}
		}
	}
	private void clickExportResize(WebElement chk) throws InterruptedException {
		Dimension oldSize = driver.manage().window().getSize();
		Dimension newSizeGrow = new Dimension(oldSize.getWidth(), oldSize.getHeight()+1);
		Dimension newSizeShrink = new Dimension(oldSize.getWidth(), oldSize.getHeight()-1);
		driver.manage().window().setSize(newSizeGrow);
//		chk.click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", chk);
		exportPdf();
		driver.manage().window().setSize(newSizeShrink);
//		chk.click();
		js.executeScript("arguments[0].click()", chk);
	}
	private boolean clickDocuments(String docName,Actions actionsIs, WebElement chk, boolean pdfDownload) {
		Dimension oldSize = driver.manage().window().getSize();
		Dimension newSizeGrow = new Dimension(oldSize.getWidth(), oldSize.getHeight()+1);
		Dimension newSizeShrink = new Dimension(oldSize.getWidth(), oldSize.getHeight()-1);
		try {
			actionsIs.moveToElement(chk);
			actionsIs.perform();
			driver.manage().window().setSize(newSizeGrow);
			chk.click();
			if(pdfDownload) {
				exportPdf();
				driver.manage().window().setSize(newSizeShrink);
				chk.click();
				logger.debug("Exported Document: "+docName);
			}
			else {
				driver.manage().window().setSize(newSizeShrink);
				logger.debug("Will Export Document: "+docName);
			}
			return true;
		}catch (Exception c) {
			logger.error("Failed Export Document: "+docName);
			//			Cannot Click Document b/c Downloading window is Open");
		}
		return false;
	}
	public void findArchDocuments() throws InterruptedException
	{
		try{
			if(driver.findElements(planAddendaDocs).size() != 0)
				driver.findElement(planAddendaDocs).findElement(expandor).click(); //if not exist??
			driver.findElement(planDocs).findElement(expandor).click();
			By arch = By.xpath("//span[text()='Architectural ']");
			By others = By.xpath("//span[text()='Other ']");
			Pattern construction = Pattern.compile(".*\\bConstruction Plan\\b.*");
			Pattern door = Pattern.compile(".*\\bDoor\\b.*");
			Pattern opening = Pattern.compile(".*\\bOpening\\b.*");
			Pattern schedule = Pattern.compile(".*\\bSchedules\\b.*");
			Pattern frame = Pattern.compile(".*\\bFrame\\b.*");
			Pattern floor = Pattern.compile(".*\\bFloor Plan\\b.*");
			Pattern partition = Pattern.compile(".*\\bPartition\\b.*");
			Pattern demolition = Pattern.compile(".*\\bDemolition\\b.*");
			Pattern window = Pattern.compile(".*\\bWindow\\b.*");
			Pattern ie = Pattern.compile(".*\\bInterior Elevations\\b.*");
			Pattern wall = Pattern.compile(".*\\bWall\\b.*");
			Actions actionsIs = new Actions(driver);

			List<WebElement> allArch = driver.findElements(arch);
			List<WebElement> allOther = driver.findElements(others);
			allArch.addAll(allOther);
			for(WebElement archWE : allArch)
			{
				if(archWE.getText().isEmpty())
					continue;
				logger.debug("Arch: " + archWE.getText());
				actionsIs.moveToElement(archWE);
				actionsIs.click();
				Thread.sleep(500);
				actionsIs.perform();
				//			archWE.click();
			}
			List<WebElement> docs = driver.findElements(title);
			List<WebElement> docsText = docs.stream().filter(d -> d.getText().length() > 0).collect(Collectors.toList());
			for(WebElement d : docsText){
				logger.debug("Documents:" + d.getText().length() + ":" + d.getText());
				String docText = d.getText();
				if((construction.matcher(docText).lookingAt()||door.matcher(docText).lookingAt() || frame.matcher(docText).lookingAt() ||floor.matcher(docText).lookingAt()
						||opening.matcher(docText).lookingAt()||window.matcher(docText).lookingAt()||partition.matcher(docText).lookingAt()||ie.matcher(docText).lookingAt()||schedule.matcher(docText).lookingAt()||wall.matcher(docText).lookingAt())&& !demolition.matcher(docText).lookingAt())
				{
					try{
						boolean found = false;
						WebElement chk = d.findElement(By.xpath("parent::*")).findElement(checkbox);
						while(!found) {
							found=clickDocuments(docText,actionsIs, chk,false);
						}


					}
					catch(StaleElementReferenceException ee)
					{
						logger.debug("Can't find: " + ee);
					}
				}
			}
			exportZip();
			logger.debug("ArchDocs: " + allArch.toString());
		}
		catch(NoSuchElementException e)
		{
			logger.error(e.toString());
		}
	}

	private boolean okay;
	public boolean popUpContinue(String message, String projectName)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ContinueDownload.fxml"));
		CmdLogger.logger.debug("Location " + fxmlLoader.getLocation().toString());
		try {
			Parent popUp = fxmlLoader.load();
			ContinueController cc = fxmlLoader.getController();
			cc.setMessageLabel(message);
			cc.setProjectName(projectName);
			Scene scene = new Scene(popUp, popUp.prefWidth(-1), popUp.prefHeight(-1)+50);
			Stage dialog = new Stage();
			dialog.setTitle("Continuation");
			dialog.setResizable(true);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setScene(scene);
			cc.setPrimaryStage(dialog);
			EventHandler<WindowEvent> closeEvent = new EventHandler<>(){
				@Override
				public void handle(WindowEvent event) {
					okay = cc.isOkay();
					logger.debug("Exit Continue Popup: " + okay);
				}};
				dialog.setOnHidden(closeEvent);
				dialog.setOnCloseRequest(closeEvent);
				dialog.showAndWait();
		} catch (IOException e) {
			logger.error(e.toString());
		}
		logger.debug("Exit Continue Popup1: " + okay);
		return okay;
	}

	public void getProjectList(String renameProjecList) throws InterruptedException
	{
		By status = By.id("FilterByStatusFilterHeader");
		By showOnly = By.id("rbFilterConditionShowOnly-boxLabelEl");
		By trackProject = By.id("rbFilterByStatusTracked-displayEl");
		By applyFilter = By.id("btnApplyFilterByStatusSelection");
		By exportDropDown = By.id("btnOpenExportPopup-btnEl");
		By exportExcel = By.id("btnExportPopup-btnEl");
		By radioOptionBtn = By.id("radioOptionList-boxLabelEl");
		By divFilterStatus = By.id("divFilterByStatusFilterGroup");
		try{
			WebElement divFilterStatusEl = driver.findElement(status);
			Actions actions = new Actions(driver);
			actions.moveToElement(divFilterStatusEl).click().perform();
			WebElement showOnlyEl = driver.findElement(showOnly);
			WebElement applyFilterEl = driver.findElement(applyFilter);
			WebElement trackProjectEl = driver.findElement(trackProject);
			actions.moveToElement(showOnlyEl)
			.click()
			.moveToElement(trackProjectEl)
			.click()
			.moveToElement(applyFilterEl)
			.click()		
			.perform();

			WebElement exportDropDownEl = driver.findElement(exportDropDown);
			actions.moveToElement(exportDropDownEl).click().perform();
			WebElement radioOptionBtnEl = driver.findElement(radioOptionBtn);
			actions.moveToElement(radioOptionBtnEl).click().perform();
			WebElement exportExcelEl = driver.findElement(exportExcel);
			actions.moveToElement(exportExcelEl).click().perform();
			File dir = new File("C:\\Users\\"+userName+"\\Downloads\\");
			FileFilter fileFilter = new WildcardFileFilter("Project List*.xls");
			File[] files = dir.listFiles(fileFilter);
			while(files.length == 0){
				Thread.sleep(500);
				files = dir.listFiles(fileFilter);
				logger.debug("Moving: "+ files.toString());
			}
			try {
				FileUtils.copyFile(files[0], new File("C:\\Users\\"+userName+"\\Downloads\\"+renameProjecList+".xls"));
				FileUtils.deleteQuietly(files[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}


		} catch(NoSuchElementException e){
			logger.error(e.getMessage());
			return;
		}
		return;
	}
	public void viewProject(WebElement ue,boolean fav) throws InterruptedException
	{
		By exportBidders = By.id("btnbidderExport-btnEl");
		By exportBuyerActivity = By.id("btnbuyerActivityReport");
		By entityCode = By.id("EntityCode");
		Actions actionsIs = new Actions(driver);
		WebElement weRightClick;
		//		boolean getDocs = false;			boolean found2 = false;
		//		while(!getDocs) {
		//			try {
		//				weRightClick = ue.findElement(gridId);
		//				actionsIs.moveToElement(weRightClick).perform();
		//				actionsIs.contextClick(weRightClick).perform();
		//				WebElement tab = driver.findElement(newTab);
		//				actionsIs.moveToElement(tab).click().perform();
		//				getDocs = true;
		//			} catch (Exception e ) {
		//				logger.debug("Error: " + e);
		//				Thread.sleep(2500);
		//				ArrayList<String> tabs2 = new ArrayList<> (driver.getWindowHandles());
		//				driver.switchTo().window(tabs2.get(0));
		//				Thread.sleep(2500);
		//			}
		//		}
		Thread.sleep(2500);
		ArrayList<String> tabs2 = new ArrayList<> (driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		new WebDriverWait(driver, sixty).until((WebDriver dr1) -> dr1.findElement(downloadDocs));
		Thread.sleep(2500);
		if(fav)
		{
			WebElement action = driver.findElement(favorite);
			actionsIs.moveToElement(action).perform();
			actionsIs.click().perform();
			try{
				try{
					WebElement moreBidder = driver.findElement(moreBidders);
					actionsIs.moveToElement(moreBidder).click().perform();
				} catch(NoSuchElementException e){
					logger.error("No More Bidders");
				}
				WebElement exportBidder = driver.findElement(exportBidders);
				actionsIs.moveToElement(exportBidder).perform();
				int scroll = 250;
				((JavascriptExecutor) driver).executeScript("scrollBy(0," + String.valueOf(scroll)+")","");
				actionsIs.moveToElement(exportBidder).click().perform();
				WebElement entityCodeEl = driver.findElement(entityCode);
				String id = entityCodeEl.getAttribute("value");
				File bidderOld = new File("C:\\Users\\"+userName+"\\Downloads\\Prospective Bidders.xls");
				while(!bidderOld.exists())
					Thread.sleep(500);
				File bidderNew = new File("C:\\Users\\"+userName+"\\Downloads\\Prospective Bidders"+id+".xls");
				try {
					FileUtils.moveFile(bidderOld, bidderNew);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (NoSuchElementException e){
				logger.error("Bidder List does not Exist:");
			}
			try{
				try {
					WebElement moreBAR = driver.findElement(moreBARs);
					actionsIs.moveToElement(moreBAR).click().perform();
				} catch(NoSuchElementException e){
					logger.error("No More BAR");
				}
				WebElement exportBuyer = driver.findElement(exportBuyerActivity);
				actionsIs.moveToElement(exportBuyer).click().perform();
			} catch (NoSuchElementException e){
				logger.error("Buyer Activity Report does not Exist:");
			}
		}

		driver.close();
		driver.switchTo().window(orgHandle);
	}
	public void iterateSearch(String renameProjectList,boolean pause) throws InterruptedException
	{
		Thread.sleep(500);
		List<WebElement> un = refreshWindow();
		WebElement ue = null;
		boolean moreProjects;
		if(!un.isEmpty()) {
			moreProjects = true;
			ue = un.get(0);
		}
		else
			moreProjects = false;

		while(moreProjects) {
			logger.debug("Unviewed Prj: " + ue.getText());
			String projectName = ue.getText();
			int scroll = 0;
			boolean found = false;
			while(!found) {
				try {
					new WebDriverWait(driver, five).until((WebDriver dr1) -> dr1.findElement(gridMatchingDocs));
					ue.findElement(gridMatchingDocs).click();
					Thread.sleep(1000);
					found = true;
				}catch (Exception e ) {
					logger.debug("Error: " + e);
					Thread.sleep(2500);
					ArrayList<String> tabs2 = new ArrayList<> (driver.getWindowHandles());
					driver.switchTo().window(tabs2.get(0));
					scroll += 250;
					((JavascriptExecutor) driver).executeScript("scroll(0," + String.valueOf(scroll)+")","");
				}
			}
			Thread.sleep(2000);
			waitForWindow(".*Document.*");//documentcenter.cmdgroup.com");
			switchToWindow(".*Document.*");
			boolean saveProject = false;
			if(pause){
				if(popUpContinue("Download Specification Documents?",projectName))
				{
					downloadSpec();
					saveProject = true;
				}
			} else 
				downloadSpec();

			if(pause){
				if(popUpContinue("Download Plan Documents?",projectName))
				{
					findArchDocuments();
					saveProject = true;
				}
			} else
				findArchDocuments();

			driver.close();
			driver.switchTo().window(orgHandle);
			un = refreshWindow();
			scroll = 0;
			found = false;
			ue = un.get(0);
			while(!found) {
				try {
					Actions actionsIs = new Actions(driver);
					WebElement weRightClick;

					new WebDriverWait(driver, five).until((WebDriver dr1) -> dr1.findElement(gridMatchingDocs));
					weRightClick = ue.findElement(gridId);
					actionsIs.moveToElement(weRightClick).perform();
					actionsIs.contextClick(weRightClick).perform();
					WebElement tab = driver.findElement(newTab);
					actionsIs.moveToElement(tab).click().perform();
					Thread.sleep(1000);
					found = true;
				}catch (Exception e ) {
					logger.debug("Error: " + e);
//					Thread.sleep(2500);
					scroll += 250;
					((JavascriptExecutor) driver).executeScript("scroll(0," + String.valueOf(scroll)+")","");
				}
			}

			viewProject(ue,saveProject);
			if(un.size()==1) 
				moreProjects = false; 
			else
				un = refreshWindow();
			ue = un.get(0);
		}
	}
	public List<WebElement> refreshWindow() {
		driver.navigate().refresh();
		new WebDriverWait(driver, thirty).until((WebDriver dr1) -> dr1.findElement(tree));
		new WebDriverWait(driver, thirty).until((WebDriver dr1) -> dr1.findElement(grd));
		new WebDriverWait(driver, thirty).until((WebDriver dr1) -> dr1.findElement(record));

		return driver.findElements(unviewed);
	}
	public void switchToWindow(String regex) {
		Set<String> windows = driver.getWindowHandles();

		for (String window : windows) {
			driver.switchTo().window(window);
			System.out.println(String.format("#switchToWindow() : title=%s ; url=%s",	
					driver.getTitle(),
					driver.getCurrentUrl()));

			p = Pattern.compile(regex);
			m = p.matcher(driver.getTitle());

			if (m.find()) return;
			m = p.matcher(driver.getCurrentUrl());
			if (m.find()) return;
		}

		fail("Could not switch to window with title / url: " + regex);
		return;
	}
	public void waitForWindow(String regex) {
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			try {
				driver.switchTo().window(window);
				p = Pattern.compile(regex);
				m = p.matcher(driver.getCurrentUrl());

				if (m.find()) {
					attempts = 0;
					switchToWindow(regex);
					return;
				}
				// try for title
				m = p.matcher(driver.getTitle());

				if (m.find()) {
					attempts = 0;
					switchToWindow(regex);
					return;
				}
			} catch(NoSuchWindowException e) {
				if (attempts <= MAX_ATTEMPTS) {
					attempts++;

					try {Thread.sleep(1000);}catch(Exception x) { x.printStackTrace(); }
					waitForWindow(regex);
					return;
				}
				fail("Window with url|title: " + regex + " did not appear after " + MAX_ATTEMPTS + " tries. Exiting.");
			}
		}

		// when we reach this point, that means no window exists with that title..
		if (attempts == MAX_ATTEMPTS) {
			fail("Window with title: " + regex + " did not appear after " + MAX_ATTEMPTS + " tries. Exiting.");
			return;
		}
		System.out.println("#waitForWindow() : Window doesn't exist yet. [" + regex + "] Trying again. " + (attempts+1) + "/" + MAX_ATTEMPTS);
		attempts++;
		try {Thread.sleep(1000);}catch(Exception x) { x.printStackTrace(); }
		waitForWindow(regex);
		return;
	}

	public void openWindow(String newUrl)
	{
		driver.navigate().to(newUrl);
		loginCMD();
		String url = driver.getCurrentUrl();
		WebDriver driver2 = new FirefoxDriver();
		driver2.navigate().to(newUrl);
		driver2.quit();
		url = driver.getCurrentUrl();
	}

	/**
	 * 
	 */
	public void downloadDocuments() {
		By exportDetails = By.id("showExportDetails");
		By downloadDetails = By.id("Del");
		By deleteAll = By.id("DeleteAllButton");
		By exportItem = By.className("exportItem");
		By download = By.className("downloadImage");
		By deleteAllYes = By.id("deleteAllExport_Yes");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		new WebDriverWait(driver, sixty).until((WebDriver dr1) -> dr1.findElement(viewed));
		List<WebElement> un = driver.findElements(viewed);
		WebElement vi = un.get(0);
		logger.debug("Viewed Prj: " + vi.getText());
		try {
			new WebDriverWait(driver, sixty).until((WebDriver dr1) -> dr1.findElement(gridMatchingDocs));
			WebElement docButton = vi.findElement(gridMatchingDocs);
			docButton.click();
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click()", docButton);
		}catch(NoSuchElementException e){
			logger.error("No Such Element" + e);
		}catch(ElementClickInterceptedException e) {
			logger.error("Using JavaScript to Click");
			WebElement docButton = vi.findElement(gridMatchingDocs);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click()", docButton);
		}
		waitForWindow(".*Document.*");//documentcenter.cmdgroup.com");
		switchToWindow(".*Document.*");
		Actions actionsIs = new Actions(driver);
		WebElement exportDetailsEl = driver.findElement(exportDetails);
		actionsIs.moveToElement(exportDetailsEl).click().perform();
		new WebDriverWait(driver, sixty).until((WebDriver dr1) -> dr1.findElement(exportItem));
		List<WebElement> exList = driver.findElements(exportItem);
		for(WebElement ex:exList){
			logger.debug("Export Item: " + ex.getText());
			try{
				WebElement downloadEl =	ex.findElement(download);
				actionsIs.moveToElement(downloadEl).click().perform();
			}catch (NoSuchElementException e ) {
				logger.error("Fault:" + e);
			}
		}
		try{
			WebElement deleteAllEl =	driver.findElement(deleteAll);
			actionsIs.moveToElement(deleteAllEl).click().perform();
			Thread.sleep(500);
			WebElement deleteAllYesEl =	driver.findElement(deleteAllYes);
			actionsIs.moveToElement(deleteAllYesEl).click().perform();
		}catch(NoSuchElementException | InterruptedException e)
		{
			logger.debug("No documents to download");
		}
		driver.close();
		driver.switchTo().window(orgHandle);
	}

	public void quit() {
		if(driver != null)
			driver.quit();
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return driver;
	}
	/**
	 * @param driver the driver to set
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

}
