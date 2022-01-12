package GUI;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ShowMXLController extends Application {
	
	public File saveFile;
    private MainViewController mvc;
	public Highlighter highlighter;

	@FXML public CodeArea mxlText;
	@FXML TextField gotoMeasureField;
	@FXML Button goToline;

	public ShowMXLController() {}

	@FXML 
	public void initialize() {
		mxlText.setParagraphGraphicFactory(LineNumberFactory.get(mxlText));
	}

    public void setMainViewController(MainViewController mvcInput) {
    	mvc = mvcInput;
    }
    
    public void update() {
		mxlText.replaceText(mvc.converter.getMusicXML());
		mxlText.moveTo(0);
		mxlText.requestFollowCaret();
        mxlText.requestFocus();
	}
    
	@FXML
	private void saveMXLButtonHandle() {
		mvc.saveMXLButtonHandle();
	}

	//TODO add go to line button
	@FXML
	private void handleGotoMeasure() {
		int measureNumber = Integer.parseInt(gotoMeasureField.getText() );
		if (!goToMeasure(measureNumber)) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Measure " + measureNumber + " could not be found.");
			alert.setHeaderText(null);
			alert.show();
		}
	}

    private boolean goToMeasure(int measureCount) {
    	//Pattern textBreakPattern = Pattern.compile("((\\R|^)[ ]*(?=\\R)){2,}|^|$");
    	Pattern mxlMeasurePattern = Pattern.compile("<measure number=\"" + measureCount + "\">");
        Matcher mxlMeasureMatcher = mxlMeasurePattern.matcher(mxlText.getText());
        
        if (mxlMeasureMatcher.find()) {
        	int pos = mxlMeasureMatcher.start();
        	mxlText.moveTo(pos);
        	mxlText.requestFollowCaret();
        	Pattern newLinePattern = Pattern.compile("\\R");
        	Matcher newLineMatcher = newLinePattern.matcher(mxlText.getText().substring(pos));
        	for (int i = 0; i < 30; i++) newLineMatcher.find();
        	int endPos = newLineMatcher.start();
        	mxlText.moveTo(pos+endPos);
        	mxlText.requestFollowCaret();
        	//mxlText.moveTo(pos);
            mxlText.requestFocus();
            return true;
            }
        else return false;        
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {}
}