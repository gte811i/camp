package avalon.selenium;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import avalon.selenium.InsightSelenium;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import processExcelCMD.CmdExcel;
import utils.CmdLogger;

@Component
public class InsightController extends CmdLogger{
	public static Logger logger=LoggerFactory.getLogger(InsightController.class);
	static Stage primaryStage;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private RadioButton avalonRdBtn;
	@FXML
	private RadioButton competitorRdBtn;
	@FXML
	private RadioButton iafRdBtn;
	@FXML
	private RadioButton fireRdBtn;
	@FXML
	private RadioButton barnDoorRdBtn;
	@FXML
	private Button downloadBtn;
	@FXML
	private CheckBox selectChkBtn;
	@FXML
	private Button unzipBtn;
	@FXML
	private Button detailsBtn;
	@Autowired
	InsightSelenium is;
	@Value("${avalon.JobTrackerXLS.Location}")
	private String jobTrackerFolder;
	@Value("${avalon.Download.Location}")
	private String downloadFolder;
//	= new InsightSelenium();
	/**
	 * 
	 */
	public InsightController() {
		logger.debug("Loading InsightController UserName");
	}
	public String getUserName() {
		return is.getUserName();
	}
	@FXML
	void selectSearch(ActionEvent event) {
		logger.debug("Selecting Button");
	}

	@FXML
	void unzipFolders(ActionEvent event)
	{	
		CmdExcel.setTrackerFolder(jobTrackerFolder);
		CmdExcel.setDownloadFolder(downloadFolder);
		logger.debug("Location of JobTracker.xls is: " + CmdExcel.getTrackerFolder());
		logger.debug("Location of Download Location is: " + CmdExcel.getDownloadFolder());
		CmdExcel.copyData();
		CmdExcel.copySpreadsheets("A","Combined");
		CmdExcel.copySpreadsheets("F","Combined");
		CmdExcel.copySpreadsheets("C","Combined");
		CmdExcel.copySpreadsheets("O","Combined");
		CmdExcel.copySpreadsheets("B","Combined");
		CmdExcel tmp = new CmdExcel();
		tmp.unzipProjects();
		CmdExcel.copyExcelData();
	}

	@FXML
	void detailsDownload(ActionEvent event)
	{
		CmdExcel.setTrackerFolder(jobTrackerFolder);
		CmdExcel.setDownloadFolder(downloadFolder);
		logger.debug("Location of JobTracker.xls is: " + CmdExcel.getTrackerFolder());
		logger.debug("Location of Download Folder is: " + CmdExcel.getDownloadFolder());
		CmdExcel tmp = new CmdExcel();
		ArrayList<String> urls = tmp.downloadDetails();
		is.openWindow(urls.get(0));
	}

	@FXML
	void runDownload(ActionEvent event)
	{
		boolean pause = selectChkBtn.isSelected();
		try {
			is.loginCMD();
			if(avalonRdBtn.isSelected()) {
				is.openSearch("Avalon");
				is.iterateSearch("A",pause);
				is.getProjectList("A");
				is.downloadDocuments();
			}
			if(competitorRdBtn.isSelected()) {
				is.openSearch("Competitors");
				is.iterateSearch("C",pause);
				is.getProjectList("C");
				is.downloadDocuments();
			}
			if(fireRdBtn.isSelected()){
				is.openSearch("FireRated");
				is.iterateSearch("F",pause);
				is.getProjectList("F");
				is.downloadDocuments();
			}
			if(barnDoorRdBtn.isSelected()){
				is.openSearch("BarnDoor");
				is.iterateSearch("B",pause);
				is.getProjectList("B");
				is.downloadDocuments();
			}
			if(iafRdBtn.isSelected()){
				is.openSearch("IAF");
				is.iterateSearch("O",pause);
				is.getProjectList("O");
				is.downloadDocuments();
			}
		} catch (Exception e1) {
			System.err.println("ERROR: " + e1.getMessage());
			e1.printStackTrace();
		}

	}

	@FXML
	void initialize() {
		assert avalonRdBtn != null : "fx:id=\"avalonRdBtn\" was not injected: check your FXML file 'InsightViewer.fxml'.";
		assert competitorRdBtn != null : "fx:id=\"competitorRdBtn\" was not injected: check your FXML file 'InsightViewer.fxml'.";
		assert iafRdBtn != null : "fx:id=\"iafRdBtn\" was not injected: check your FXML file 'InsightViewer.fxml'.";
		assert fireRdBtn != null : "fx:id=\"fireRdBtn\" was not injected: check your FXML file 'InsightViewer.fxml'.";

	}    
	@FXML
	void exit(ActionEvent event) {
		WindowEvent wev = new WindowEvent(primaryStage,WindowEvent.WINDOW_CLOSE_REQUEST);
		Event.fireEvent(primaryStage, wev);
		is.quit();
	}
	public void quit() {
		if(is != null)
			is.quit();
	}
	/**
	 * @param primaryStage the primaryStage to set
	 */
	public static void setPrimaryStage(Stage primaryStage) {
		InsightController.primaryStage = primaryStage;
	}

}

