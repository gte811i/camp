package avalon.cmd;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//import org.junit.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.StaleElementReferenceException;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxProfile;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedCondition;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import com.gargoylesoftware.htmlunit.html.HtmlButton;
//import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
//import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
//
//import io.ddavison.conductor.Browser;
//import io.ddavison.conductor.Config;
//import io.ddavison.conductor.Locomotive;
//import selenium.HomePage;
//import selenium.State;
//import selenium.StateMachine;
//
//import org.junit.Test;
//
//@Config(
//		browser = Browser.CHROME,
//		url     = "https://insight.cmdgroup.com"
//		)
//public class TestSel extends Locomotive {
//	By searchSelect = By.id("drpSearch-inputEl");
//	By searchList = By.className("x-boundlist-item");
//	By avalon = By.id("Avalon");
//	By fire = By.id("FireRated");
//	By competitor = By.id("Competitors");
//	By other = By.id("IAF");
//	By search = By.id("spnSearchClose0");
//	By matches = By.id("grdProjectMatches-body");
//	By tree = By.id("treeview-1007");
//	By record = By.id("treeview-1007-record-31");
//	// look for tr class: "unviewed-row x-grid-row"
//	By gridName = By.className("x-grid-cell-colProjectName");
//	By gridFav = By.className("x-grid-checkheader-favourite");
//	By gridId = By.className("x-grid-cell-colProjectCode");
//	By gridBidDate = By.className("x-grid-cell-colBidDate");
//	By gridUpdateDate = By.className("x-grid-cell-colUpdateDate");
//	By gridStage = By.className("x-grid-cell-colStage");
//	By gridAddress = By.className("x-grid-cell-colStreetAddress");
//	By gridCity = By.className("x-grid-cell-colCity");
//	By gridCounty = By.className("x-grid-cell-colCounty");
//	By gridState = By.className("x-grid-cell-colState");
//	By gridZip = By.className("x-grid-cell-colPostalCode");
//	By gridRole = By.className("x-grid-cell-colRole");
//	By gridCompany = By.className("x-grid-cell-colCompanyName");
//	By gridContact = By.className("x-grid-cell-colContactName");
//	By gridPhone = By.className("x-grid-cell-colPhone");
//	By gridEmail = By.className("x-grid-cell-colEmail");
//	By gridValue = By.className("x-grid-cell-colValue");
//	By gridFloorArea = By.className("x-grid-cell-colFloorArea");
//	By gridMatchingDocs = By.className("x-grid-cell-colMatchingDocs");
//
//	By unviewed = By.className("unviewed-row");
//	By downloadDocs = By.id("projectDownloadDoc");
//	By fav = By.id("spnProjectFavourite");
//	By specDocs = By.className("specDocs");
//	By planDocs = By.className("planDocs");
//	By planAddendaDocs = By.className("planAddendaDocs");
//	By expandor = By.className("fancytree-expander");
//	By checkbox = By.className("fancytree-checkbox");
//	By match = By.className("matchDocs");
//	By title = By.className("fancytree-title");
//	By lastsib = By.className("fancytree-lastsib");
//	By regNode = By.className("regularNode");
//	By selectedNode = By.className("selectedNode");
//	By exportType = By.id("exportTypes");
//	By showExportDetails = By.id("showExportDetails");
//	By menuExport = By.id("menuExport");
//	By btnExport = By.id("BtnExport");
//	By btnIncludePrjDetails = By.id("chkBoxExportProjectInfo");
//	By corner = By.className("ui-corner-all");
//
//	public void loginCMD(){
//		validatePresent(HomePage.LOC_LNK_LOGIN);
//		By userName = By.id("txtUserName");
//		By password = By.id("txtPassword");
//		By btnLogon = By.id("btnLogon");
//		setText(userName, "gte811i@hotmail.com");
//		setText(password, "LaMas123");
//		click(btnLogon);
//	}
//
//	public void openSearch(By search){
//		WebElement wer = new WebDriverWait(driver, 60).until((WebDriver dr1) -> dr1.findElement(By.id("btnMenu")));
//		click(searchSelect);
//		for(WebElement we: driver.findElements(search))
//		{
//			System.out.println("Test" + we.getText());
//		}
//		click(search);
//	}
//	public void openProject(){
//		WebElement wer3 = new WebDriverWait(driver, 30).until((WebDriver dr1) -> dr1.findElement(search));
//		WebElement wer4 = new WebDriverWait(driver, 30).until((WebDriver dr1) -> dr1.findElement(record));
//		driver.findElement(record).getText();
//		WebElement prjName = driver.findElement(record).findElement(gridName);
//		System.out.println("Name:");
//		System.out.println(prjName.getText());
//		System.out.println(driver.findElement(record).getText());
//		System.out.println("DONE");
//		prjName.click();
//		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
//		WebElement projectFile = new WebDriverWait(driver, 60).until((WebDriver dr1) -> dr1.findElement(downloadDocs));
//		driver.findElement(downloadDocs).click();
//		waitForWindow(".*Document.*");//documentcenter.cmdgroup.com");
//		switchToWindow(".*Document.*");
//	}
//
//	public void exportPdf() throws InterruptedException
//	{
//		driver.findElement(exportType).click();
//		WebElement multiE = driver.findElement(menuExport);//.findElement(multi);
//		List<WebElement> cl= multiE.findElements(corner);
//		cl.get(0).click();
//		driver.findElement(btnExport).click();
//		Thread.sleep(3000);
//		driver.findElement(showExportDetails).click();
//	}
//
//	public void exportZip() throws InterruptedException
//	{
//		driver.findElement(exportType).click();
//		WebElement multiEE = driver.findElement(menuExport);//.findElement(multi);
//		List<WebElement> cll= multiEE.findElements(corner);
//		cll.get(1).click();
//		driver.findElement(exportType).click();
//		driver.findElement(btnIncludePrjDetails).click();
//		driver.findElement(btnExport).click();
//		Thread.sleep(3000);
//		driver.findElement(exportType).click();
//		driver.findElement(btnIncludePrjDetails).click();
//		driver.findElement(showExportDetails).click();
//	}
//
//	public void downloadSpec() throws InterruptedException
//	{
//		Thread.sleep(1500); //need to sleep .5 sec for 
//
//		List<WebElement> allElements = driver.findElements(By.xpath("//div[@id='plansTree']/ul/li")); 
//		StateMachine sm = new StateMachine();
//		String spec = new String();
//		for (WebElement element: allElements) {
//			List<WebElement> moreElement = element.findElements(title);
//			System.out.println("Elements: " + element.getText());
//			for(WebElement e: moreElement)
//			{
//				System.out.println(":"+e.getText());
//				String[] list = e.getText().split("\n");
//
//				if(sm.currentState.equals(State.MatchSpecState))
//				{
//					String[] lista = e.getText().split("\\(");
//					System.out.println("State Machine Spec Section: " + lista[0]);
//					if(lista[0].equals("Structural "))
//					{
//						WebElement structDoc = e.findElement(By.xpath("parent::*")).findElement(checkbox);
//						structDoc.click();
//						exportPdf();
//						structDoc.click();
//						driver.findElement(match).findElement(expandor).click();
//					}
//					else{
//						Actions actions = new Actions(driver);
//						spec = lista[0];
//						spec = spec.replaceAll("^[0-9]*", "$0 ");
//						sm.onEventTextValue(lista[0]);
//						By bySpec = By.xpath("//span[text()='"+spec+"']/parent::span");
//						driver.findElement(match).findElement(expandor).click();
//						Thread.sleep(500);
//						try{
//							WebElement specs = driver.findElement(specDocs).findElement(expandor);
//							specs.click();
//							List<WebElement> allSpecSections = driver.findElements(bySpec);
//							System.out.println("Specs: " + allSpecSections.toString());
//							for(WebElement we: allSpecSections){
//								//						System.out.println("All: " + we.getText().length());
//								if(we.getText().length()>0){
//									WebElement specSection =we.findElement(By.xpath("parent::*")).findElement(checkbox);
//									actions.moveToElement(specSection);
//									//actions.click();
//									actions.perform();					
//									specSection.click();
//									exportPdf();
//									specSection.click();
//								}
//							}
//							actions.moveToElement(specs);
//							actions.click();
//							actions.perform();
//						} catch(NoSuchElementException ne) {
//							System.out.println("No SpecDocs Element");
//						}
//					}
//					//					WebElement specSection = allSpecSections.get(0).findElement(By.xpath("parent::*")).findElement(checkbox);
//					return;
//				}
//				sm.onEventTextValue(list[0]);
//			}
//		}
//	}
//
//	public void findArchDocuments() throws InterruptedException
//	{
//		try{
//			if(driver.findElements(planAddendaDocs).size() != 0)
//				driver.findElement(planAddendaDocs).findElement(expandor).click(); //if not exist??
//			driver.findElement(planDocs).findElement(expandor).click();
//			By arch = By.xpath("//span[text()='Architectural ']");
//			By byArch = By.xpath("//span[text()='Architectural ']/parent/::span");
//			Pattern construction = Pattern.compile(".*\\bConstruction Plan\\b.*");
//			Pattern door = Pattern.compile(".*\\bDoor\\b.*");
//			Pattern opening = Pattern.compile(".*\\bOpening\\b.*");
//			Pattern schedule = Pattern.compile(".*\\bSchedules\\b.*");
//			Pattern frame = Pattern.compile(".*\\bFrame\\b.*");
//			Pattern floor = Pattern.compile(".*\\bFloor Plan\\b.*");
//			Pattern partition = Pattern.compile(".*\\bPartition\\b.*");
//			Pattern demolition = Pattern.compile(".*\\bDemolition\\b.*");
//			Pattern window = Pattern.compile(".*\\bWindow\\b.*");
//			Pattern ie = Pattern.compile(".*\\bInterior Elevations\\b.*");
//			Actions actions = new Actions(driver);
//
//			List<WebElement> allArch = driver.findElements(arch);
//			for(WebElement archWE : allArch)
//			{
//				System.out.println("Arch: " + archWE.getText());
//				actions.moveToElement(archWE);
//				actions.click();
//				actions.perform();
//				//			archWE.click();
//			}
//			List<WebElement> docs = driver.findElements(title);
//			List<WebElement> docsText = docs.stream().filter(d -> d.getText().length() > 0).collect(Collectors.toList());
//			for(WebElement d : docsText){
//				System.out.println("Documents:" + d.getText().length() + ":" + d.getText());
//				String docText = d.getText();
//				if((construction.matcher(docText).lookingAt()||door.matcher(docText).lookingAt() || frame.matcher(docText).lookingAt() ||floor.matcher(docText).lookingAt()
//						||opening.matcher(docText).lookingAt()||window.matcher(docText).lookingAt()||partition.matcher(docText).lookingAt()||ie.matcher(docText).lookingAt()||schedule.matcher(docText).lookingAt())&& !demolition.matcher(docText).lookingAt())
//				{
//					System.out.println("MATCH: " + docText);
//					try{
//						WebElement chk = d.findElement(By.xpath("parent::*")).findElement(checkbox);
//						actions.moveToElement(chk);
//						actions.perform();
//						//						Thread.sleep(500);
//						chk.click();
//					}
//					catch(StaleElementReferenceException ee)
//					{
//						System.out.println("Can't find: " + ee);
//					}
//				}
//			}
//			exportZip();
//			if(driver.findElements(planDocs).size() != 0) {
//				driver.findElement(planDocs).findElement(expandor).click();
//				driver.findElement(planDocs).findElement(checkbox).click();
//				driver.findElement(planDocs).findElement(checkbox).click();
//			} else {
//				driver.findElement(selectedNode).findElement(expandor).click();
//				driver.findElement(selectedNode).findElement(checkbox).click();
//				driver.findElement(selectedNode).findElement(checkbox).click();
//			}
//			if(driver.findElements(planAddendaDocs).size() != 0){
//				driver.findElement(planAddendaDocs).findElement(expandor).click();
//				driver.findElement(planAddendaDocs).findElement(checkbox).click();
//				driver.findElement(planAddendaDocs).findElement(checkbox).click();
//			}
//			System.out.println("ArchDocs: " + allArch.toString());
//			//					driver.findElement(planDocs).findElement(By.xpath("parent::*")).findElement(arch).click();
//			//					test.click();
//		}
//		catch(NoSuchElementException e)
//		{
//			System.out.println(e);
//		}
//	}
//
//	void iterateSearch() throws InterruptedException
//	{
//		WebElement wer3 = new WebDriverWait(driver, 30).until((WebDriver dr1) -> dr1.findElement(search));
//		WebElement wer4 = new WebDriverWait(driver, 30).until((WebDriver dr1) -> dr1.findElement(record));
//		List<WebElement> un = driver.findElements(unviewed);
//		for(WebElement ue:un){
//			System.out.println("Unviewed Prj: " + ue.getText());
//			Actions actions = new Actions(driver);
//			actions.moveToElement(ue.findElement(gridMatchingDocs));
//			//			actions.click();
//			actions.perform();					
//			ue.findElement(gridMatchingDocs).click();
//			waitForWindow(".*Document.*");//documentcenter.cmdgroup.com");
//			switchToWindow(".*Document.*");
//
//			downloadSpec();
//			findArchDocuments();
//			driver.close();
//			switchToWindow(".*insight.*");
//		}
//	}
//	@Test
//	public void downloadProjects() {
//		try {
//			loginCMD();
//			openSearch(avalon);
//			iterateSearch();
//			openSearch(competitor);
//			iterateSearch();
//			openSearch(other);
//			iterateSearch();
//			openSearch(fire);
//			iterateSearch();
//			//			openProject();
//			//			downloadSpec();
//			//			findArchDocuments();
//			Thread.sleep(1500); //need to sleep .5 sec for 
//
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}
//}