package avalon.cmd;
import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import avalon.selenium.InsightController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import processExcelCMD.InsightConfig;
import utils.CmdLogger;
/**
 * 
 */

/**
 * @author scarleton3
 *
 */
public class InsightScrapper extends Application{
	InsightController controller;
	public static void main(String[] args){
		Application.launch(args);
	}
	
	@Override
	public void stop() {
		controller.quit();
	}
	//10.11.20.117
	@Override
	public void start(final Stage primaryStage)
	{
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(InsightConfig.class);
		//		ctx.scan("avalon.selenium");
		//		ctx.refresh();
		//		is = ctx.getBean(InsightSelenium.class);

		CmdLogger.configure(); 
		InsightController.setPrimaryStage(primaryStage);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/InsightViewer.fxml"));
		if(fxmlLoader.getLocation() == null)
			CmdLogger.logger.error("Cannot find InsightViewer.fxml");
		else {
			CmdLogger.logger.debug("Location " + fxmlLoader.getLocation().toString());
			try {
				fxmlLoader.setControllerFactory(ctx::getBean);
				Parent root = fxmlLoader.load();
				Scene scene = new Scene(root, root.prefWidth(-1), root.prefHeight(-1));
				primaryStage.setScene(scene); 
				controller = fxmlLoader.getController();
				CmdLogger.logger.debug("UserName Loaded is: "+ controller.getUserName());
				controller.printData();
			} catch (IOException e) {
				e.printStackTrace();
			}
			primaryStage.setResizable(true);
			primaryStage.show();
			primaryStage.setTitle("Groundhog" + " v"+controller.getVersion()); //set Version
			primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>(){
				@Override
				public void handle(WindowEvent window)
				{
					controller.quit();
					primaryStage.close();
				}
			});
		}
	}
}
