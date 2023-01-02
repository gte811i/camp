package io.avalon.bom;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import io.avalon.bom.components.Elevation;
import io.avalon.bom.components.Finish;
import io.avalon.bom.database.DatabaseAccessController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
@Component
public class BillOfMaterialsController{
	@Autowired
	Elevation elevation;
	static Stage primaryStage;
	@FXML
	private ResourceBundle resources;
    @FXML
    private Button colorAddBtn;
	@FXML
	private URL location;
	@FXML
    private TextField buttTypeTF;

    @FXML
    private ChoiceBox<?> centerHungPivotCB;

    @FXML
    private ChoiceBox<Finish> colorCB;

    @FXML
    private TextField concealedCloserTF;

    @FXML
    private TextField concealedOverheadTF;

    @FXML
    private TextField concealedVerticalTF;

    @FXML
    private TextField coordinatorTF;

    @FXML
    private TextField deadBoltTF;

    @FXML
    private TextArea descriptionTF;

    @FXML
    private TextField doorPositionSwitchTF;

    @FXML
    private ChoiceBox<?> doubleEgressCB;

    @FXML
    private TextField doubleLipStrikeTF;

    @FXML
    private ChoiceBox<?> eagleTypeCB;

    @FXML
    private TextField electricStrikeTF;

    @FXML
    private TextField electronicPowerTF;

    @FXML
    private TextArea elevationTF;

    @FXML
    private TextField emergencyStopTF;

    @FXML
    private TextField flushBoltTF;

    @FXML
    private ChoiceBox<?> frameTypeCB;

    @FXML
    private ComboBox<?> hardwareGroupCmbB;

    @FXML
    private TextField headTF;

    @FXML
    private TextField heightTF;

    @FXML
    private TextField hingeJambTF;

    @FXML
    private TextField hingeStackTF;

    @FXML
    private ChoiceBox<?> hingeTypeCB;

    @FXML
    private CheckBox leftSidelightCxBx;

    @FXML
    private TextField localElectrifiedTF;

    @FXML
    private TextField noStrikeTF;

    @FXML
    private ChoiceBox<?> pairFrameCB;

    @FXML
    private TextField pivotTF;

    @FXML
    private TextField projectIDTF;

    @FXML
    private TextField projectNameTF;

    @FXML
    private TextField quantityTF;

    @FXML
    private CheckBox rightSidelightCxBx;

    @FXML
    private TextField rollerLatchTF;

    @FXML
    private Button setHeadDefaultsBtn;

    @FXML
    private Button setHingeDefaultsBtn;

    @FXML
    private Button setStrikeDefaultsBtn;

    @FXML
    private ChoiceBox<?> singleSwingCB;

    @FXML
    private TextField strikeDoorPositionSwitchTF;

    @FXML
    private TextField strikeJambTF;

    @FXML
    private TextField strikePlateTF;

    @FXML
    private TextField strikeRollerLatchTF;

    @FXML
    private TextField surfaceCloserTF;

    @FXML
    private ChoiceBox<?> systemTypeCB;

    @FXML
    private ChoiceBox<?> throatSizeCB;

    @FXML
    private CheckBox transomCxBx;

    @FXML
    private ChoiceBox<?> trimProfileSizeCB;

    @FXML
    private TextField widthTF;
    AnnotationConfigApplicationContext ctx;
	@Value("${version.semver}")
	private String version;

    /**
	 * 
	 */
	public BillOfMaterialsController() {
		// TODO Auto-generated constructor stub
//		super(InsightController.class.getName());
	}
	@FXML
	void selectSearch(ActionEvent event) {
		log.debug("Selecting Button");
	}
	
	@FXML
	void setDefaults(ActionEvent event) {
		log.debug("TEST");
	}

	@FXML
	void initialize() {
		log.debug("Checking Initialization");
//	      buttTypeTF;
	      centerHungPivotCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
		        System.out.println("Box # Value: " +centerHungPivotCB.getItems().get(newNumber.intValue()));
		      }
		    });
 	      colorCB.itemsProperty().bind(elevation.finishListProperty());
	      colorCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
		        System.out.println("Box # Value: " +colorCB.getItems().get(newNumber.intValue()));
		      }
		    });
//	      concealedCloserTF;
//	      concealedOverheadTF;
//	      concealedVerticalTF;
//	      coordinatorTF;
//	      deadBoltTF;
//	      descriptionTF;
//	      doorPositionSwitchTF;
	      doubleEgressCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
		        System.out.println("Box # Value: " +doubleEgressCB.getItems().get(newNumber.intValue()));
		      }
		    });
//	      doubleLipStrikeTF;
	      eagleTypeCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
		        System.out.println("Box # Value: " +eagleTypeCB.getItems().get(newNumber.intValue()));
		      }
		    });
//	      electricStrikeTF;
//	      electronicPowerTF;
//	      elevationTF;
//	      emergencyStopTF;
//	      flushBoltTF;
	      frameTypeCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
		        System.out.println("Box # Value: " +frameTypeCB.getItems().get(newNumber.intValue()));
		      }
		    });
//	      hardwareGroupCmbB;
//	      headTF;
//	      heightTF;
//	      hingeJambTF;
//	      hingeStackTF;
	      hingeTypeCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
		        System.out.println("Box # Value: " +hingeTypeCB.getItems().get(newNumber.intValue()));
		      }
		    });
//	      leftSidelightCxBx;
//	      localElectrifiedTF;
//	      noStrikeTF;
	      pairFrameCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
		        System.out.println("Box # Value: " +pairFrameCB.getItems().get(newNumber.intValue()));
		      }
		    });
//	      pivotTF;
//	      projectIDTF;
//	      projectNameTF;
//	      quantityTF;
//	      rightSidelightCxBx;
//	      rollerLatchTF;
//	      setHeadDefaultsBtn;
//	      setHingeDefaultsBtn;
//	      setStrikeDefaultsBtn;
	      singleSwingCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
		        System.out.println("Box # Value: " +singleSwingCB.getItems().get(newNumber.intValue()));
		      }
		    });
//	      strikeDoorPositionSwitchTF;
//	      strikeJambTF;
//	      strikePlateTF;
//	      strikeRollerLatchTF;
//	      surfaceCloserTF;
	      systemTypeCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
		        System.out.println("Box # Value: " +systemTypeCB.getItems().get(newNumber.intValue()));
		      }
		    });;
	      throatSizeCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
		        System.out.println("Box # Value: " +throatSizeCB.getItems().get(newNumber.intValue()));
		      }
		    });
	      trimProfileSizeCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
		        System.out.println("Box # Value: " +trimProfileSizeCB.getItems().get(newNumber.intValue()));
		      }
		    });
//	      transomCxBx;
//	      widthTF;
	}
	
    @FXML
    void selectFrameType(ActionEvent event) {
		log.debug("SelectFrameType");
    }

    @FXML
    void selectLeftSidelight(ActionEvent event) {
		log.debug("SelectLeftSidelight");

    }

    @FXML
    void selectRightSidelight(ActionEvent event) {
		log.debug("SelectRightSidelight");

    }

    @FXML
    void selectTransom(ActionEvent event) {
		log.debug("SelectTransom");

    }

    @FXML
    void setHeadDefaults(ActionEvent event) {
		log.debug("SelectHeadDefaults");

    }

    @FXML
    void setHingeJambDefaults(ActionEvent event) {
		log.debug("SelectHingeJambDefaults");

    }

    @FXML
    void setStrikeJambDefaults(ActionEvent event) {
		log.debug("SelectStrikeJambDefaults");

    }	
	
	@FXML
	void exit(ActionEvent event) {
		WindowEvent wev = new WindowEvent(primaryStage,WindowEvent.WINDOW_CLOSE_REQUEST);
		Event.fireEvent(primaryStage, wev);
	}
    @FXML
    void addColor(ActionEvent event) {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/database_access.fxml"));
    	try {
//    		fxmlLoader.setController(new DatabaseAccessController());
    		fxmlLoader.setControllerFactory(ctx::getBean);
//    		fxmlLoader.;
    		Parent root = fxmlLoader.load();
    		DatabaseAccessController controller = fxmlLoader.getController();
    		controller.setItemType("Finish");
    		Stage stage = new Stage();
    		stage.setTitle("Color List");
    		stage.setScene(new Scene(root, 450, 450));
    		stage.show();
    		// Hide this current window (if this is what you want)
    		//            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
	/**
	 * @param primaryStage the primaryStage to set
	 */
	public static void setPrimaryStage(Stage primaryStage) {
		BillOfMaterialsController.primaryStage = primaryStage;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
}

