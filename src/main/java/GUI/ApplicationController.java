package GUI;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ApplicationController {

	@FXML
	private AnchorPane anchorpane;
	
	@FXML
    private MenuBar menuBar;
	
	@FXML
    private Menu FileBar;
	
	@FXML
    private HBox hBox;
	
	@FXML
    private Separator buttonSeperator;

	@FXML
	private MenuItem open;

	@FXML
	private MenuItem save;
	
	@FXML
	private MenuItem close;
	
	@FXML
    private MenuItem about;
	
// 	@FXML
//     private Button playPauseButton;
	
// 	@FXML
// 	private Button rewindButton;
	 
// 	@FXML
//     private Button stopButton;
	
    @FXML
    private Button openButton;
	
	@FXML
    private Button saveButton;
	
	
// 	private File directory;
// 	private File[] files;

// 	private ArrayList<File> songs;

// 	private int songNumber;

// 	private Media media;
// 	private MediaPlayer mediaPlayer;

// 	boolean isPlaying = false;
	
	
// 	public void initialize(URL arg0, ResourceBundle resources) {
// 		songs = new ArrayList<File>();

// 		directory = new File("music");

// 		files = directory.listFiles();

// 		if(files != null) {
// 			for (File file : files) {
// 				songs.add(file);
// 				System.out.println(file);
// 			}
// 		}

// 		media = new Media(songs.get(songNumber).toURI().toString());
// 		mediaPlayer = new MediaPlayer(media);
// 	}

	@FXML
	void aboutFile(ActionEvent event) {
		Stage stage = (Stage) anchorpane.getScene().getWindow();
	}

	@FXML
	void closeProgram(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Music Previewer");
		alert.setHeaderText("You are about to exit the application!");
		alert.setContentText("Do you want to save before exiting?");

		if (alert.showAndWait().get() == ButtonType.OK) {
			Stage stage = (Stage) anchorpane.getScene().getWindow();
			System.out.println("Successfully exited the application!");
			stage.close();
		}
	}

	@FXML
	void openFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		Stage stage = (Stage) anchorpane.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stage);

		if (file != null) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.open(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void saveFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		Stage stage = (Stage) anchorpane.getScene().getWindow();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("text file", "*.txt"), 
				new FileChooser.ExtensionFilter("pdf", "*.pdf")
				);

		try {
			File file = fileChooser.showSaveDialog(stage);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
// 	public void playMedia() {
// 		System.out.println("Plays Media!");
// //		if (isPlaying) {
// //			mediaPlayer.pause();
// //			isPlaying = false;
// //		}
// //		else {
// //			mediaPlayer.play();
// //			isPlaying = true;
// //		}
// 	}
	
// 	public void rewindMedia() {
// 		System.out.println("Rewinds Media");
// //		mediaPlayer.seek(Duration.seconds(0));
// 	}
	
// 	public void stopMedia() {
// 		System.out.println("Stops Media!");
// //		mediaPlayer.stop();
// //		isPlaying = false;
// 	}

}
