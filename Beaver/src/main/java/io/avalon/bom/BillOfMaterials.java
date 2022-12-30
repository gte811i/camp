package io.avalon.bom;
import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
/**
 * @author scarleton3
 *
 */
	//10.11.20.117
@Log4j2
public class BillOfMaterials extends Application{
	public static void main(String[] args){
		Application.launch(args);		
}
	//10.11.20.117
	@Override
	public void start(final Stage primaryStage)
	{
		log.debug("TEST!!");
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BOMConfig.class);
		BillOfMaterialsController.setPrimaryStage(primaryStage);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/front_end.fxml"));
//		CmdLogger.logger.debug("Location " + fxmlLoader.getLocation().toString());
		try {
			fxmlLoader.setControllerFactory(ctx::getBean);
			Parent root = fxmlLoader.load();
			BillOfMaterialsController controller = fxmlLoader.getController();
			controller.setCtx(ctx);
			Scene scene = new Scene(root, root.prefWidth(-1), root.prefHeight(-1));
			primaryStage.setScene(scene); 
			primaryStage.setResizable(true);
			primaryStage.show();
			primaryStage.setTitle("Beaver" + " v"+ controller.getVersion()); //set Version
			primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>(){
				@Override
				public void handle(WindowEvent window)
				{
					primaryStage.close();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
