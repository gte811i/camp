package io.avalon.bom.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PrefixSelectionComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import io.avalon.bom.ControlsUtil;
import io.avalon.bom.components.AvalonObject;
import io.avalon.bom.components.Elevation;
import io.avalon.bom.components.Finish;
import io.avalon.bom.components.Throat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
	private URL location;
	@FXML
	private TextField buttTypeTF;

	@FXML
	private ChoiceBox<?> centerHungPivotCB;

	@FXML
	private PrefixSelectionComboBox<AvalonObject> colorCB;

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
	@Value("${version.semver}")
	private String version;

	private FXMLLoader fxmlLoader;
	AnnotationConfigApplicationContext ctx;

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
		ControlsUtil.setSearchParameters(colorCB);
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
				log.debug("ColorBox # Value: " +colorCB.getItems().get(newNumber.intValue()));
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
				log.debug("Box # Value: " +doubleEgressCB.getItems().get(newNumber.intValue()));
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
	void addColor(ActionEvent event) {
		loadFxmlLoader();
		databaseAccessView(Finish.class, "Color List");
	}
	@FXML
	void addThroat(ActionEvent event) {
		loadFxmlLoader();
		databaseAccessView(Throat.class, "Throat List");
	}

	@FXML
	void exit(ActionEvent event) {
		WindowEvent wev = new WindowEvent(primaryStage,WindowEvent.WINDOW_CLOSE_REQUEST);
		Event.fireEvent(primaryStage, wev);
	}
	private void loadFxmlLoader() {
		fxmlLoader = new FXMLLoader(getClass().getResource("/database_access.fxml"));
		try {
			fxmlLoader.setControllerFactory(ctx::getBean);
//			fxmlLoader.setControllerFactory(c -> {
//				   return new DatabaseAccessCtr<Finish>();});
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	private void databaseAccessView(Class<? extends AvalonObject> clazz, String title) {

//		DatabaseAccessCtr<?> controller = fxmlLoader.getController();
		DatabaseAccessController controller = fxmlLoader.getController();
		controller.getListProperty(clazz);
		//    		controller.setItemType(itemType);
		controller.setItemType(clazz);
		//    		controller.setItemType(Finish.class);
		controller.loadData(clazz.getSimpleName());
		Stage stage = new Stage();
		stage.setTitle(title);
		stage.setScene(new Scene(fxmlLoader.getRoot(), 450, 450));
		stage.show();
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

