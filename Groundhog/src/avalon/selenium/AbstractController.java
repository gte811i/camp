/**
 * 
 */
package avalon.selenium;

import javafx.stage.Stage;
import utils.CmdLogger;

/**
 * @author scarleton3
 *
 */
public abstract class AbstractController extends CmdLogger{
	/** The initial JavaFx stage. */
	protected Stage primaryStage;

	/**
	 * @return the primaryStage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * @param primaryStage the primaryStage to set
	 */
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

}
