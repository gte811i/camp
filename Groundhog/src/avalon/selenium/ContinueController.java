package avalon.selenium;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.http.MessageConstraintException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ContinueController extends AbstractController{

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label messageLabel;
	@FXML
    private Label detailsLabel;
    @FXML
    private Label projectName;
	@FXML
    private Label detailsLabel11;
    @FXML
    private HBox actionParent;
    @FXML
    private Button cancelButton;
    @FXML
    private HBox okParent;
    @FXML
    private Button okButton;
    private boolean okay=false;
    @FXML
    void initialize() {
        assert messageLabel != null : "fx:id=\"messageLabel\" was not injected: check your FXML file 'ContinueDownload.fxml'.";
        assert detailsLabel != null : "fx:id=\"detailsLabel\" was not injected: check your FXML file 'ContinueDownload.fxml'.";
        assert projectName != null : "fx:id=\"detailsLabel1\" was not injected: check your FXML file 'ContinueDownload.fxml'.";
        assert detailsLabel11 != null : "fx:id=\"detailsLabel11\" was not injected: check your FXML file 'ContinueDownload.fxml'.";
        assert actionParent != null : "fx:id=\"actionParent\" was not injected: check your FXML file 'ContinueDownload.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'ContinueDownload.fxml'.";
        assert okParent != null : "fx:id=\"okParent\" was not injected: check your FXML file 'ContinueDownload.fxml'.";
        assert okButton != null : "fx:id=\"okButton\" was not injected: check your FXML file 'ContinueDownload.fxml'.";

    }
    @FXML
    void onOkay() {
    	logger.debug("On Okay");
    	okay = true;
    	primaryStage.close();
    }
    @FXML
    void onCancel() {
    	System.out.println("On Cancel");
    	okay = false;
    	primaryStage.close();
    }
	/**
	 * @return the okay
	 */
	public boolean isOkay() {
		return okay;
	}
	/**
	 * @param okay the okay to set
	 */
	public void setOkay(boolean okay) {
		this.okay = okay;
	}
	/**
	 * @param messageLabel the messageLabel to set
	 */
	public void setMessageLabel(String message) {
		messageLabel.setText(message);
	}
    /**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String name) {
		projectName.setText(name);
	}

    	
}
