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
import org.apache.logging.log4j.Marker;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.log4j.Log4j2;

/**
 * @author scarleton3
 *
 */
//@Config(
//		browser = Browser.FIREFOX,
//		url     = "https://insight.cmdgroup.com"
//		)
@Component
@Log4j2
public class InsightSelenium {
	@Value("${avalon.username}")
	private String userName;

	Pattern p;
	Matcher m;
	int attempts;
	int MAX_ATTEMPTS = 20;
	Duration sixty =Duration.ofSeconds(60);
	Duration thirty =Duration.ofSeconds(30);
	Duration five=Duration.ofSeconds(5);
	//	private static String userName="scarleton3";
	By searchSelect = By.id("drpSearch-inputEl");
	By moreBidders = By.id("lnkBidder");
	By moreBARs = By.id("btnbuyerActivityReport-btnEl");
	//	By moreBARs = By.id("lnkBuyersActivityReport");
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
	By href = By.tagName("a");

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
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
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

		if (size > 1) log.error("WARN: There are more than 1 " + by.toString() + " 's!");

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
		log.debug("Opening Search for: " + searchName);
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
			log.debug("Test:" + we.getText());
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
		log.debug("Name:");
		log.debug(prjName.getText());
		log.debug(driver.findElement(record).getText());
		log.debug("DONE:");
		prjName.click();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		new WebDriverWait(driver, sixty).until((WebDriver dr1) -> dr1.findElement(downloadDocs));
		driver.findElement(downloadDocs).click();
		log.debug("openProject WaitForWindow");
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
			log.debug("Elements: " + element.getText());
			for(WebElement e: moreElement)
			{
				log.debug(":"+e.getText());

				String[] list = e.getText().split("\n");

				if(sm.currentState.equals(State.MatchSpecState))
				{
					String[] lista = e.getText().split("\\(");
					log.debug("State Machine Spec Section: " + lista[0]);
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
								log.debug("Documents:" + d.getText().length() + ":" + d.getText());
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
										log.debug("Can't find: " + ee);
									}
								}
							}
							//------


							List<WebElement> allSpecSections = driver.findElements(bySpec);
							log.debug("Specs: " + allSpecSections.toString());
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
							log.debug("No SpecDocs Element");
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
				log.debug("Exported Document: "+docName);
			}
			else {
				driver.manage().window().setSize(newSizeShrink);
				log.debug("Will Export Document: "+docName);
			}
			return true;
		}catch (Exception c) {
			log.error("Failed Export Document: "+docName);
			//			Cannot Click Document b/c Downloading window is Open");
		}
		return false;
	}
	
	private boolean exists(By element) {
		Duration implicitWait = driver.manage().timeouts().getImplicitWaitTimeout();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(0));
		final List<WebElement> signOut = driver.findElements(element);
		driver.manage().timeouts().implicitlyWait(implicitWait); // Restore implicit wait to previous value
		if (signOut.isEmpty())
			return false;
		return true;
	}
	public void findArchDocuments() throws InterruptedException
	{
		try{
			if(exists(planAddendaDocs))
				driver.findElement(planAddendaDocs).findElement(expandor).click();
			if(exists(planDocs))
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
				log.debug("Arch: " + archWE.getText());
				actionsIs.moveToElement(archWE);
				actionsIs.click();
				Thread.sleep(500);
				actionsIs.perform();
				//			archWE.click();
			}
			List<WebElement> docs = driver.findElements(title);
			List<WebElement> docsText = docs.stream().filter(d -> d.getText().length() > 0).collect(Collectors.toList());
			for(WebElement d : docsText){
				log.debug("Documents:" + d.getText().length() + ":" + d.getText());
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
						log.debug("Can't find: " + ee);
					}
				}
			}
			exportZip();
			log.debug("ArchDocs: " + allArch.toString());
		}
		catch(NoSuchElementException e)
		{
			log.error(e.toString());
		}
	}

	private boolean okay;
	public boolean popUpContinue(String message, String projectName)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ContinueDownload.fxml"));
		log.debug("Location " + fxmlLoader.getLocation().toString());
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
					log.debug("Exit Continue Popup: " + okay);
				}};
				dialog.setOnHidden(closeEvent);
				dialog.setOnCloseRequest(closeEvent);
				dialog.showAndWait();
		} catch (IOException e) {
			log.error(e.toString());
		}
		log.debug("Exit Continue Popup1: " + okay);
		return okay;
	}

	public void getProjectList(String renameProjecList) throws InterruptedException
	{
		By status = By.id("FilterByStatusFilterHeader");
		By showOnly = By.id("rbFilterConditionShowOnly-boxLabelEl");
		By trackProject = By.id("rbFilterByStatusTracked-boxLabelEl");
		By applyFilter = By.id("btnApplyFilterByStatusSelection");
		By exportDropDown = By.id("btnOpenExportPopup-btnEl");
		By exportExcel = By.id("btnExportPopup-btnEl");
		By radioOptionBtn = By.id("radioOptionList-boxLabelEl");
		By divFilterStatus = By.id("divFilterByStatusFilterGroup");
		try{
			WebElement divFilterStatusEl = driver.findElement(status);
			Actions actions = new Actions(driver);
			actions.moveToElement(divFilterStatusEl)
			.click()
			.pause(250)
			.perform();
			log.debug("Hitting Filter");
			WebElement showOnlyEl = driver.findElement(showOnly);
			WebElement applyFilterEl = driver.findElement(applyFilter);
			WebElement trackProjectEl = driver.findElement(trackProject);
			log.debug("Hitting Show Only");
			actions.moveToElement(showOnlyEl)
			.click()
			.pause(250)
			.perform();
			log.debug("Hitting Tracked Projects");
			actions.moveToElement(trackProjectEl)
			.click()
			.pause(250)
			.perform();
			log.debug("Hitting Apply");
			actions.moveToElement(applyFilterEl)
			.click()		
			.pause(250)
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
			if(files != null) {
				while(files.length == 0){
					Thread.sleep(500);
					files = dir.listFiles(fileFilter);
					log.debug("Moving: "+ files.toString());
				}
				try {
					FileUtils.copyFile(files[0], new File("C:\\Users\\"+userName+"\\Downloads\\"+renameProjecList+".xls"));
					FileUtils.deleteQuietly(files[0]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else
				log.debug("No Project List");
		} catch(NoSuchElementException e){
			log.error(e.getMessage());
			return;
		}
		return;
	}
	public void viewProject(WebElement ue,boolean fav) throws InterruptedException
	{
		Actions actionsIs = new Actions(driver);
		WebElement weRightClick;
		waitForWindow(".*ProjectInformation.*");//documentcenter.cmdgroup.com");
		ArrayList<String> tabs2 = new ArrayList<> (driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1)); //TODO: Sometimes tab is slow and tabs is only 1 when should be 2
		new WebDriverWait(driver, sixty).until((WebDriver dr1) -> dr1.findElement(downloadDocs));
//		Thread.sleep(2500);
		if(fav)
		{
			WebElement action = driver.findElement(favorite);
			actionsIs.moveToElement(action).pause(250).perform();
			actionsIs.click().perform();
			try{
				downloadBidders();
			} catch (NoSuchElementException e){
				log.error("Bidder List does not Exist:");
			}
			try{
				downloadBuyerActivityReport();
			} catch (NoSuchElementException e){
				log.error("Buyer Activity Report does not Exist:");
			}
		}
		driver.close();
		driver.switchTo().window(orgHandle);
	}
	private void downloadBidders() throws InterruptedException {
		By exportBidders = By.id("btnbidderExport-btnEl");
		By entityCode = By.id("EntityCode");
		Actions actionsIs = new Actions(driver);
		try{
			WebElement moreBidder = driver.findElement(moreBidders);
			actionsIs.moveToElement(moreBidder).click().pause(250).perform();
		} catch(NoSuchElementException e){
			log.error("No More Bidders");
		}
		WebElement exportBidder = driver.findElement(exportBidders);
		actionsIs.moveToElement(exportBidder).pause(250).perform();
		int scroll = 250;
		((JavascriptExecutor) driver).executeScript("scrollBy(0," + String.valueOf(scroll)+")","");
		actionsIs.moveToElement(exportBidder).click().pause(250).perform();
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

	}
	private void downloadBuyerActivityReport() {
		By exportBuyerActivity = By.id("btnbuyerActivityReport");
		By projectShareBy = By.id("projectShareHistoryExport");
		Actions actionsIs = new Actions(driver);
//		try {
//			WebElement moreBAR = driver.findElement(moreBARs);
//			actionsIs.moveToElement(moreBAR).click().pause(250).perform();
//			log.debug("Downloading BAR");
//		} catch(NoSuchElementException e){
//			log.error("No More BAR");
//		}
		WebElement exportBuyer = driver.findElement(exportBuyerActivity);
		WebElement projectShare = driver.findElement(projectShareBy);
		actionsIs.moveToElement(projectShare).build().perform();;
		actionsIs.click(exportBuyer).build().perform();
		log.debug("Got " + exportBuyer.getText());
	}
	private boolean clickDocument(WebElement ue) throws Exception {
		boolean found = false;
		new WebDriverWait(driver, five).until((WebDriver dr1) -> dr1.findElement(gridMatchingDocs));
		WebElement we = ue.findElement(gridMatchingDocs);
		Actions actionsIs = new Actions(driver);
		WebElement clicky = we.findElement(href);
		try {
			int scroll = 50;
//			Thread.sleep(250);
			log.debug("Clicking: " + clicky.getText());
			//Scroll to take care of stupid agent online/offline at bottom right
			actionsIs.moveToElement(clicky).build().perform();
			((JavascriptExecutor) driver).executeScript("scrollBy(0," + String.valueOf(scroll)+")","");
		} catch( MoveTargetOutOfBoundsException e) {
			log.error("Move Out of Bounds: ");
		}
		actionsIs.click(clicky);
		actionsIs.build().perform();
		found = true;
		return found;
	}
	public void iterateSearch(String renameProjectList,boolean pause) throws InterruptedException
	{
		Thread.sleep(500);
		List<WebElement> un = refreshWindow();
		int i = 0;
		WebElement ue = null;
//		boolean moreProjects;
//		if(!un.isEmpty())
//			moreProjects = true;
//		}
//		else
//			moreProjects = false;

		while(i < un.size()) {
			ue = un.get(i++);
			log.debug("Unviewed Prj: " + ue.getText());
			String projectName = ue.getText();
			int scroll = 50;
			boolean found = false;
			while(!found) {
				try {
					found = clickDocument(ue);
				}catch (Exception e ) {
					log.debug("Error: " + e);
					Thread.sleep(2500);
					ArrayList<String> tabs2 = new ArrayList<> (driver.getWindowHandles());
					driver.switchTo().window(tabs2.get(0));
					((JavascriptExecutor) driver).executeScript("scrollBy(0," + String.valueOf(scroll)+")","");
				}
			}
//			Need to wait and then must switch to get document data
			log.debug("IterateSearch WaitForWindow");
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
//			un = refreshWindow();
			openProjectTab(ue);
			viewProject(ue,saveProject);
//			if(i==1) 
//				moreProjects = false; 
//			else
//				un = refreshWindow();
//			ue = un.get(i++);
		}
		refreshWindow();
	}
	private void openProjectTab(WebElement ue) {
		int scroll = 50;
		boolean found = false;
//		WebElement ue = un.get(0);
		while(!found) {
			try {
				log.debug("Finding: " + ue.getText());
				Actions actionsIs = new Actions(driver);
				WebElement weRightClick;

				new WebDriverWait(driver, five).until((WebDriver dr1) -> dr1.findElement(gridMatchingDocs));
				weRightClick = ue.findElement(gridId);
				actionsIs.moveToElement(weRightClick).perform();
//				((JavascriptExecutor) driver).executeScript("scrollBy(0," + String.valueOf(scroll)+")","");
				actionsIs.moveToElement(weRightClick).perform();
				actionsIs.contextClick(weRightClick).perform();
				WebElement tab = driver.findElement(newTab);
				actionsIs.moveToElement(tab).click().perform();
				found = true;
			}catch (Exception e ) {
				log.debug("Error: " + e);
			}
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
			log.debug(String.format("#switchToWindow() : title=%s ; url=%s",	
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
		log.debug("#waitForWindow() : Window doesn't exist yet. [" + regex + "] Trying again. " + (attempts+1) + "/" + MAX_ATTEMPTS);
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
		log.debug("Viewed Prj: " + vi.getText());
		try {
			new WebDriverWait(driver, sixty).until((WebDriver dr1) -> dr1.findElement(gridMatchingDocs));
			WebElement docButton = vi.findElement(gridMatchingDocs);
			docButton.click();
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click()", docButton);
		}catch(NoSuchElementException e){
			log.error("No Such Element" + e);
		}catch(ElementClickInterceptedException e) {
			log.error("Using JavaScript to Click");
			WebElement docButton = vi.findElement(gridMatchingDocs);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click()", docButton);
		}
		log.debug("DownloadDocuments WaitForWindow");
		waitForWindow(".*Document.*");//documentcenter.cmdgroup.com");
		switchToWindow(".*Document.*");
		Actions actionsIs = new Actions(driver);
		WebElement exportDetailsEl = driver.findElement(exportDetails);
		actionsIs.moveToElement(exportDetailsEl).click().perform();
		try {
			new WebDriverWait(driver, sixty).until((WebDriver dr1) -> dr1.findElement(exportItem));
		} catch(TimeoutException e) {
			log.error("No Export Items");
		}
		List<WebElement> exList = driver.findElements(exportItem);
		for(WebElement ex:exList){
			log.debug("Export Item: " + ex.getText());
			try{
				WebElement downloadEl =	ex.findElement(download);
				actionsIs.moveToElement(downloadEl).click().perform();
			}catch (NoSuchElementException e ) {
				log.error("Fault:" + e);
			}
		}
		try{
			WebElement deleteAllEl =	driver.findElement(deleteAll);
			actionsIs.moveToElement(deleteAllEl).click().pause(500).perform();
			WebElement deleteAllYesEl =	driver.findElement(deleteAllYes);
			actionsIs.moveToElement(deleteAllYesEl).click().perform();
		}catch(NoSuchElementException e)
		{
			log.debug("No documents to download");
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
