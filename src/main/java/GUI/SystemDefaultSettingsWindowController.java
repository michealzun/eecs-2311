package GUI;

import java.io.File;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SystemDefaultSettingsWindowController extends Application {

	private MainViewController mvc;
	Preferences prefs;

	@FXML public TextField inputFolderField;
	@FXML public TextField outputFolderField;
	@FXML private ComboBox<String> cmbErrorSensitivity;
	@FXML private ComboBox<String> cmbNumerator;
	@FXML private ComboBox<String> cmbDenominator;

	public SystemDefaultSettingsWindowController() {
		prefs = Preferences.userRoot();
	}
	
	public void setMainViewController(MainViewController mvcInput) {
		mvc = mvcInput;
	}

	public void initialize() {

		String inputFolder = prefs.get("inputFolder", System.getProperty("user.home"));
		inputFolderField.setText(inputFolder);
		String outputFolder = prefs.get("outputFolder", System.getProperty("user.home"));
		outputFolderField.setText(outputFolder);
		
		cmbErrorSensitivity.getItems().removeAll(cmbErrorSensitivity.getItems());
		cmbErrorSensitivity.getItems().addAll("Level 1 - Minimal Error Checking", "Level 2 - Standard Error Checking", "Level 3 - Advanced Error Checking", "Level 4 - Detailed Error Checking");
		String errStr = prefs.get("errorSensitivity", "2");
		int err = Integer.parseInt(errStr);
		cmbErrorSensitivity.getSelectionModel().select(err - 1);
		
		cmbNumerator.getItems().removeAll(cmbNumerator.getItems());
		for (int i =1; i<=16; i++) cmbNumerator.getItems().add(i + "");
		String tsNumStr = prefs.get("tsNum", "4");
		int tsNum = Integer.parseInt(tsNumStr);
		cmbNumerator.getSelectionModel().select(tsNum + "");
		
		cmbDenominator.getItems().removeAll(cmbDenominator.getItems());
		cmbDenominator.getItems().addAll("2", "4", "8", "16", "32");
		String tsDenStr = prefs.get("tsDen", "4");
		int tsDen = Integer.parseInt(tsDenStr);
		cmbDenominator.getSelectionModel().select(tsDen + "");
	}
	
	@FXML private void handleErrorSensitivity() {
		int err;
		switch (cmbErrorSensitivity.getValue().toString()) {
		case "Level 1 - Minimal Error Checking" -> err = 1;
		case "Level 2 - Standard Error Checking" -> err = 2;
		case "Level 3 - Advanced Error Checking" -> err = 3;
		case "Level 4 - Detailed Error Checking" -> err = 4;
		default -> err = 4;
		}
		prefs.put("errorSensitivity", err+"");
		mvc.refresh();
	}

	@FXML
	private void handleChangeOutputFolder() {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(new File(prefs.get("outputFolder", System.getProperty("user.home"))));
		File selected = dc.showDialog(MainApp.STAGE);
		outputFolderField.setText(selected.getAbsolutePath());
		prefs.put("outputFolder", selected.getAbsolutePath());
	}

	@FXML
	private void handleChangeInputFolder() {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(new File(prefs.get("inputFolder", System.getProperty("user.home"))));
		File selected = dc.showDialog(MainApp.STAGE);
		inputFolderField.setText(selected.getAbsolutePath());
		prefs.put("inputFolder", selected.getAbsolutePath());
	}
	
	@FXML
	private void handleTSNumerator() {
		String value = cmbNumerator.getValue().toString();
		prefs.put("tsNum", value);
	}
	
	@FXML
	private void handleTSDenominator() {
		String value = cmbDenominator.getValue().toString();
		prefs.put("tsDen", value);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {}
}