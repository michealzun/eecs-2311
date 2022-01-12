package GUI;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import utility.GuitarUtils;
import utility.Settings;

public class CurrentSongSettingsWindowController extends Application {

	private MainViewController mvc;
	//Preferences prefs;
	private Node[][] gridPaneArray = null;

	@FXML public TextField outputFolderField;
	@FXML private ComboBox<String> cmbErrorSensitivity;
	@FXML private ComboBox<String> cmbNumerator;
	@FXML private ComboBox<String> cmbDenominator;
	@FXML private GridPane gridGuitarTuning;
	
	public CurrentSongSettingsWindowController() {
		//prefs = Preferences.userRoot();
	}
	
	public void setMainViewController(MainViewController mvcInput) {
		mvc = mvcInput;
	}

	public void initialize() {
		Settings s = Settings.getInstance();
		
		String outputFolder = s.outputFolder;
		if (outputFolder == null)
			outputFolderField.setPromptText("Not set yet...");
		else
			outputFolderField.setText(outputFolder);
		
		cmbErrorSensitivity.getItems().removeAll(cmbErrorSensitivity.getItems());
		cmbErrorSensitivity.getItems().addAll("Level 1 - Minimal Error Checking", "Level 2 - Standard Error Checking", "Level 3 - Advanced Error Checking", "Level 4 - Detailed Error Checking");
		int err = s.errorSensitivity;
		cmbErrorSensitivity.getSelectionModel().select(err - 1);
		
		cmbNumerator.getItems().removeAll(cmbNumerator.getItems());
		for (int i =1; i<=16; i++) cmbNumerator.getItems().add(i + "");
		int num = s.tsNum;
		cmbNumerator.getSelectionModel().select(num + "");
		
		cmbDenominator.getItems().removeAll(cmbDenominator.getItems());
		cmbDenominator.getItems().addAll("2", "4", "8", "16", "32");
		int den = s.tsDen;
		cmbDenominator.getSelectionModel().select(den + "");
		
		initializeGuitarTuning();

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
		Settings.getInstance().errorSensitivity = err;
		mvc.refresh();
	}

	@FXML
	private void handleBrowse() {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(new File(Settings.getInstance().outputFolder));
		File selected = dc.showDialog(MainApp.STAGE);
		outputFolderField.setText(selected.getAbsolutePath());
		Settings.getInstance().outputFolder = selected.getAbsolutePath();
		//System.out.println(Settings.getInstance().outputFolder);
		
		//prefs.put("outputFolder", selected.getAbsolutePath());
	}

	@FXML
	private void handleTSNumerator() {
		String value = cmbNumerator.getValue().toString();
		//prefs.put("tsNumerator", value);
		Settings.getInstance().tsNum = Integer.parseInt(value);
	}
	@FXML
	private void handleTSDenominator() {
		String value = cmbDenominator.getValue().toString();
		//prefs.put("tsDenominator", value);
		Settings.getInstance().tsDen = Integer.parseInt(value);
	}
	

	private void initializeGridPaneArray()
    {
       this.gridPaneArray = new Node[6][2];
       for(Node node : gridGuitarTuning.getChildren())
       {
    	  if (node instanceof ComboBox<?>)
          this.gridPaneArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
       }
    }
	
	@FXML private void handleTuning(ActionEvent e) {
		
		Node n = (Node) e.getSource();
		if(n instanceof ComboBox<?>)
	    {
			@SuppressWarnings("unchecked")
			ComboBox<String> cb = ( ComboBox<String> ) n;
			String newValue = cb.getValue().toString();
			//TODO Update as this only affects a copy of the tuning now
			Settings.getInstance().getGuitarTuning()[GridPane.getRowIndex(n)][GridPane.getColumnIndex(n)] = newValue;
	    }
		//System.out.println(GuitarUtils.toOneString(Settings.getInstance().guitarTuning));
	}

	private void initializeGuitarTuning() {
		initializeGridPaneArray();	
		for ( int string=0; string < 6; string++ )
		{
			Node n = this.gridPaneArray[string][0];
		    if(n instanceof ComboBox<?>)
		    {
		    	@SuppressWarnings("unchecked")
				ComboBox<String> cb = ( ComboBox<String> ) n;
		    	cb.getItems().addAll(GuitarUtils.KEY_LIST);
		    	cb.getSelectionModel().select(Settings.getInstance().getGuitarTuning()[string][0]);
		    }
		}
		for ( int string=0; string < 6; string++ )
		{
			Node n = this.gridPaneArray[string][1];
		    if(n instanceof ComboBox<?>)
		    {
		    	@SuppressWarnings("unchecked")
				ComboBox<String> cb = ( ComboBox<String> ) n;
		    	for (int i= 0; i < 10; i++)
		    		cb.getItems().add(i+"");
		    	cb.getSelectionModel().select(Settings.getInstance().getGuitarTuning()[string][1]);
		    }
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	}
}