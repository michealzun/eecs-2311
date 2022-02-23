package GUI;

import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;

import org.icepdf.ri.common.*;
import org.icepdf.ri.util.PropertiesManager;
import org.xml.sax.SAXException;

import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ApplicationController implements Initializable {
	private String musicXMLString = "";
	private boolean isPlaying = false;
	private UnicodeText unicode;

	// Layout UI Attribute(s)

	@FXML
	private AnchorPane anchorpane;

	@FXML
	private BorderPane borderPane;

	@FXML
	private Pane centerPane;

	// File Path UI Attribute(s)

	@FXML
	private Label pathTitle;

	@FXML
	private Label filePath;

	// Menu Bar UI Attribute(s)

	@FXML
	private MenuBar menuBar;

	@FXML
	private Menu fileBtn;

	@FXML
	private HBox hButtonBar;

	@FXML
	private HBox hMenuBar;

	@FXML
	private Separator buttonSeperator;

	@FXML
	private MenuItem open;

	@FXML
	private MenuItem save;

	@FXML
	private MenuItem exit;

	@FXML
	private MenuItem about;

	// Button UI Attribute(s)

	@FXML
	private Button homeButton;

	@FXML
	private Button openButton;

	@FXML
	private Button saveButton;

	@FXML
	private Button openPDF;

	@FXML
	private Button playPauseButton;

	@FXML
	private ImageView playPauseImage;

	@FXML
	private Button rewindButton;

	@FXML
	private Button stopButton;

	@FXML
	private Button manualButton;

	// Initialization Phase

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void displaySheetMusic(String musicSheet) throws IOException {
		this.musicXMLString = musicSheet;
		SwingNode swingNode = new SwingNode();
		createSwingContent(swingNode);
		this.centerPane.getChildren().add(swingNode);

	}

	private void createSwingContent(final SwingNode swingNode) {
		this.unicode = new UnicodeText(musicXMLString);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				swingNode.setContent(unicode);
			}
		});
	}

	// Button Methods

	@FXML
	private void homeBtn(ActionEvent event) {
		
	}

	@FXML
	private void pdfBtn(ActionEvent event) {
		try {
			String filePath = openFile();
			this.filePath.setText(filePath);
			// Build a controller
			SwingController controller = new SwingController();

			PropertiesManager properties = new PropertiesManager(System.getProperties(),
					ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));

			// Build a SwingViewFactory configured with the controller 
			SwingViewBuilder factory  = new SwingViewBuilder(controller, properties);
			// Use the factory to build a JPanel that is pre-configured with a complete, active Viewer UI.
			JPanel viewerComponentPanel  = factory.buildViewerPanel();
			//			controller.setToolBarVisible(false);
			// Add copy keyboard command
			ComponentKeyBinding.install(controller, viewerComponentPanel);
			// Add interactive mouse link annotation support via callback
			controller.getDocumentViewController().setAnnotationCallback(
					new org.icepdf.ri.common.MyAnnotationCallback(controller.getDocumentViewController())
					);
			// Create a JFrame to display the panel in
			controller.openDocument(filePath);
			SwingNode swingNode = new SwingNode();
			swingNode.setContent(viewerComponentPanel);
			this.centerPane.getChildren().add(swingNode);
			Stage stage = (Stage) centerPane.getScene().getWindow();
			stage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMusicXMLString(String musicXMLString) {
		this.musicXMLString = musicXMLString;
	}

	private String openFile() {
		String userDirectory = System.getProperty("user.home");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(userDirectory + "/Desktop"));
		Stage stage = (Stage) anchorpane.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stage);
		return file.getAbsolutePath();
	}

	@FXML
	private void aboutFile(ActionEvent event) throws IOException {
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
			Scene scene = new Scene(root);

			Image icon = new Image("image_assets/icon.png");
			stage.getIcons().add(icon);
			stage.setTitle("About Previewer");

			stage.setScene(scene);
			stage.show();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void manualBtn(ActionEvent event) {
		try {
			Desktop.getDesktop().browse(new URI("https://drive.google.com/file/d/145ux549id4GujhYk7V6eDImr-z0rWSo-/view?usp=sharing"));
		} 
		catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void openBtn(ActionEvent event) throws SAXException, IOException, ParserConfigurationException {
		String userDirectory = System.getProperty("user.home");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(userDirectory + "/Desktop"));
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
		filePath.setText(file.toString());
	}

	@FXML
	private void saveBtn(ActionEvent event) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		unicode.saveImage();
//		Stage stage = (Stage) anchorpane.getScene().getWindow();
//		// Creates a File chooser
//		FileChooser fileChooser = new FileChooser();
//		fileChooser.setInitialFileName("Untitled");
//		// Extension Filter
//		fileChooser.getExtensionFilters().addAll(
//				new FileChooser.ExtensionFilter("MP3 Audio", "*.mp3"),
//				new FileChooser.ExtensionFilter("WAV Audio", "*.wav"),
//				new FileChooser.ExtensionFilter("PDF", "*.pdf"),
//				new FileChooser.ExtensionFilter("ZIP", "*.zip"),
//				new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif"),
//				new FileChooser.ExtensionFilter("Plain Text", "*.txt")
//				);
//		try {
//			fileChooser.showSaveDialog(stage);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@FXML
	private void exitApp(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Music Previewer");
		alert.setHeaderText("You are about to exit the application!");
		alert.setContentText("Do you want to save before exiting?");

		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);

		Optional<ButtonType> result = alert.showAndWait(); 
		if (result.get() == ButtonType.YES) {
			Stage stage = (Stage) anchorpane.getScene().getWindow();
			stage.close();
			System.out.println("Exited the application!");
		}
		else if (result.get() == ButtonType.CANCEL) {
			alert.close();
			System.out.println("Exit request cancelled!");
		}
	}

	// Media Methods

	@FXML
	private void playBtn(ActionEvent event) {
		if (isPlaying) {
			// WIP
			isPlaying = false;
		}
		else {
			// WIP
			isPlaying = true;
		}
	}

	@FXML
	private void rewindBtn(ActionEvent event) {
		// WIP
	}

	@FXML
	private void stopBtn(ActionEvent event) {
		// WIP
		isPlaying = false;
		this.playPauseImage.setImage(new Image("image_assets/play.png"));
		this.playPauseButton.setText("Play");
	}

	@FXML
	private void playPauseBtnImage(MouseEvent event) {
		// WIP
		if (isPlaying == false) {
			this.playPauseImage.setImage(new Image("image_assets/play.png"));
			this.playPauseButton.setText("Play");
		}
		else {
			this.playPauseImage.setImage(new Image("image_assets/pause.png"));
			this.playPauseButton.setText("Pause");
		}
	}

}
