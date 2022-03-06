package GUI;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.icepdf.ri.common.*;
import org.icepdf.ri.util.PropertiesManager;
import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ApplicationController extends Application implements Initializable {

	private MusicPlayer musicPlayer;
	private UnicodeText unicode;
	private String musicXMLString;
	private boolean isPlaying = false;

	// UI Layout Attribute(s)

	@FXML
	private AnchorPane anchorpane;

	@FXML
	private BorderPane borderPane;

	@FXML
	private Pane centerPane;

	// UI File Path Attribute(s)

	@FXML
	private Label pathTitle;

	@FXML
	private Label filePath;

	// UI Menu Bar Attribute(s)

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

	// UI Button Attribute(s)

	@FXML
	private Button homeButton;

	@FXML
	private Button saveButton;

	@FXML
	private Button openPDF;

	@FXML
	private Button playPauseButton;

	@FXML
	private ImageView playPauseImage;

	@FXML
	private Button pauseButton;

	@FXML
	private Button rewindButton;

	@FXML
	private Button stopButton;

	@FXML
	private Button manualButton;

	// Initialization Phase
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tooltip();
		pauseButton.setDisable(true);
		rewindButton.setDisable(true);
		stopButton.setDisable(true);
	}
	
	// Button Tooltips
	
	public void tooltip() {
		Tooltip homeTip = new Tooltip("Close current previewer and go to TAB2XML panel");
		homeButton.setTooltip(homeTip);

		Tooltip saveButtonTip = new Tooltip("Save sheet music as PDF document");
		saveButton.setTooltip(saveButtonTip);

		Tooltip openPDFTip = new Tooltip("Open documents in embedded pdf viewer");
		openPDF.setTooltip(openPDFTip);

		Tooltip playPauseButtonTip = new Tooltip("Play sheet music");
		playPauseButton.setTooltip(playPauseButtonTip);

		Tooltip pauseButtonTip = new Tooltip("Pause sheet music");
		pauseButton.setTooltip(pauseButtonTip);
		
		Tooltip rewindButtonTip = new Tooltip("Rewind to start of sheet music");
		rewindButton.setTooltip(rewindButtonTip);

		Tooltip stopButtonTip = new Tooltip("Stop sheet music");
		stopButton.setTooltip(stopButtonTip);

		Tooltip manualButtonTip = new Tooltip("Retrieve user manual");
		manualButton.setTooltip(manualButtonTip);
	}

	public void setMusicXMLString(String musicSheet) {
		musicXMLString = musicSheet;
	}

	public void displaySheetMusic(String musicSheet) {
		// Retrieve, Parse and Display XMLString 
		musicXMLString = musicSheet;
		unicode = new UnicodeText(musicXMLString);
		centerPane.getChildren().add(unicode);
		// Initializes Sheet Music Player
		try {
			musicPlayer = new MusicPlayer(musicSheet);
			musicPlayer.run();
		} catch (ParserConfigurationException | MidiUnavailableException e) {
			e.printStackTrace();
		}
	}

	// Button Methods

	// Closer previewer and goes to TAB2XML input panel
	@FXML
	private void homeBtn(ActionEvent event) {
		Stage stage = (Stage) anchorpane.getScene().getWindow();
		stage.close();
	}

	private String openFile() {
		String userDirectory = System.getProperty("user.home");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(userDirectory + "/Desktop"));
		Stage stage = (Stage) anchorpane.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stage);
		return file.getAbsolutePath();
	} 

	// Embedded pdf previewer (view sheet music in previewer)
	@FXML
	private void pdfBtn(ActionEvent event) {
		try {
			pathTitle.setText("File Path");
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
			stage.setMaximized(!stage.isMaximized());
			stage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		saveButton.setDisable(true);
	}

	// About page
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
	
	// Open user manual
	@FXML
	private void manualBtn(ActionEvent event) {
		try {
			Desktop.getDesktop().browse(new URI("https://drive.google.com/file/d/145ux549id4GujhYk7V6eDImr-z0rWSo-/view?usp=sharing"));
		} 
		catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	// Open docs with external applications
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
		pathTitle.setText("File Path");
		filePath.setText(file.toString());
	}

	// Save sheet music
	@FXML
	private void saveBtn(ActionEvent event) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		DirectoryChooser dc = new DirectoryChooser();
		File directory = dc.showDialog(null);
		if (directory != null) {
			directory = new File(directory.getAbsolutePath()); // + "/dafaultFilename.extension"
		}

		WritableImage nodeshot = unicode.snapshot(new SnapshotParameters(), null);
		File file = new File("musicXML.png");

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		PDImageXObject image;
		PDPageContentStream content;
		try {
			image = PDImageXObject.createFromFile("musicXML.png", document);
			content = new PDPageContentStream(document, page);
			content.drawImage(image, 0, 200);
			content.close();
			document.addPage(page);
			document.save(directory + "/Sheet Music.pdf");
			document.close();
			file.delete();
		} catch (IOException ex) {
			Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
		}
		pathTitle.setText("Save Path");
		filePath.setText(directory.toString());
	}

	// Exit prompt
	@FXML
	private void exitApp(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sheet Music Previewer");
		alert.setHeaderText("You are about to exit the application!");
		alert.setContentText("Are you sure you want to exit before saving the sheet music?");

		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);

		Optional<ButtonType> result = alert.showAndWait(); 
		if (result.get() == ButtonType.YES) {
			Stage stage = (Stage) anchorpane.getScene().getWindow();
			stage.close();
		}
		else if (result.get() == ButtonType.CANCEL) {
			alert.close();
		}
	}

	// Media Controls

	@FXML
	public void playBtn(ActionEvent event) throws ParserConfigurationException, MidiUnavailableException, InvalidMidiDataException, InterruptedException {
		musicPlayer.play();
		pauseButton.setDisable(false);
		rewindButton.setDisable(false);
		stopButton.setDisable(false);
	}

	@FXML
	void pauseBtn(ActionEvent event) throws MidiUnavailableException, InvalidMidiDataException {
		musicPlayer.pause();
	}

	@FXML
	private void rewindBtn(ActionEvent event) {
		musicPlayer.rewind();
	}

	@FXML
	private void stopBtn(ActionEvent event) throws MidiUnavailableException {
		musicPlayer.stop();
		playPauseImage.setImage(new Image("image_assets/play.png"));
		playPauseButton.setText("Play");
	}
	
	// WIP 
	@FXML
	private void playPauseBtnImage(MouseEvent event) {
		if (isPlaying == false) {
			this.playPauseImage.setImage(new Image("image_assets/play.png"));
			this.playPauseButton.setText("Play");
		}
		else if (isPlaying == true) {
			this.playPauseImage.setImage(new Image("image_assets/pause.png"));
			this.playPauseButton.setText("Pause");
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
	}

}
