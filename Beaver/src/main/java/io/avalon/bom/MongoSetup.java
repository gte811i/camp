package io.avalon.bom;
import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.avalon.bom.database.DatabaseAccessSetup;
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
@Log4j2
public class MongoSetup{
	public static void main(String[] args){
		log.debug("Setting up MongoDB");
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BOMConfig.class);
		DatabaseAccessSetup dbSetup = ctx.getBean(DatabaseAccessSetup.class);
		dbSetup.setup();
	}
}
