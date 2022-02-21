package GUI;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JPanel;
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
import parser.Parser;

public class ApplicationController implements Initializable {

	private Parser parser;

	@FXML
	private AnchorPane anchorpane;

	@FXML
	private BorderPane borderPane;

	// File Path 

	@FXML
	private Label pathTitle;

	@FXML
	private Label filePath;

	// Menu Bar Attributes

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
	private MenuItem exit;

	@FXML
	private MenuItem about;

	@FXML
	private Pane centerPane;

	// Button Attributes

	@FXML
	private Button openButton;

	@FXML
	private Button saveButton;

	@FXML
	private Button playPauseButton;

	@FXML
	private Button rewindButton;

	@FXML
	private Button stopButton;

	@FXML
	private ImageView playPauseImage;

	// Audio-Player Attributes

	boolean isPlaying = false;

	// PDF Attributes	

	// Initialization Phase

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.parser = new Parser();
	}

	// WIP

	@FXML
	void openPDF(ActionEvent event) {
		try {
			String filePath = openFile();
			this.filePath.setText(filePath);
			// build a controller
			SwingController controller = new SwingController();

			PropertiesManager properties = new PropertiesManager(System.getProperties(),
					ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));

			//			try {
			//				  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
			//				        | UnsupportedLookAndFeelException e) {
			//				  System.err.println("[ err. ] " + e);
			//				}

			// Build a SwingViewFactory configured with the controller 
			SwingViewBuilder factory  = new SwingViewBuilder(controller, properties);
			// Use the factory to build a JPanel that is pre-configured with a complete, active Viewer UI.
			JPanel viewerComponentPanel  = factory.buildViewerPanel();
			//			controller.setToolBarVisible(false);
			// add copy keyboard command
			ComponentKeyBinding.install(controller, viewerComponentPanel);
			// add interactive mouse link annotation support via callback
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

		}
	}

	public String openFile() {
		String userDirectory = System.getProperty("user.home");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(userDirectory + "/Desktop"));
		Stage stage = (Stage) anchorpane.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stage);
		return file.getAbsolutePath();
	}

	@FXML
	void aboutFile(ActionEvent event) throws IOException {
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

	// File Menu Methods

	@FXML
	void openFile(ActionEvent event) {
		String userDirectory = System.getProperty("user.home");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(userDirectory + "/Desktop"));
		Stage stage = (Stage) anchorpane.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stage);
		
		String input = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n"
				+ "<!DOCTYPE score-partwise PUBLIC \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\" \"http://www.musicxml.org/dtds/partwise.dtd\">\r\n"
				+ "<score-partwise version=\"3.1\">\r\n" + "  <identification>\r\n"
				+ "    <creator type=\"composer\"></creator>\r\n" + "  </identification>\r\n" + "  <part-list>\r\n"
				+ "    <score-part id=\"P1\">\r\n" + "      <part-name>Guitar</part-name>\r\n" + "    </score-part>\r\n"
				+ "  </part-list>\r\n" + "  <part id=\"P1\">\r\n" + "    <measure number=\"1\">\r\n"
				+ "      <attributes>\r\n" + "        <divisions>16</divisions>\r\n" + "        <key>\r\n"
				+ "          <fifths>0</fifths>\r\n" + "        </key>\r\n" + "        <clef>\r\n"
				+ "          <sign>TAB</sign>\r\n" + "          <line>5</line>\r\n" + "        </clef>\r\n"
				+ "        <staff-details>\r\n" + "          <staff-lines>6</staff-lines>\r\n"
				+ "          <staff-tuning line=\"1\">\r\n" + "            <tuning-step>E</tuning-step>\r\n"
				+ "            <tuning-octave>2</tuning-octave>\r\n" + "          </staff-tuning>\r\n"
				+ "          <staff-tuning line=\"2\">\r\n" + "            <tuning-step>A</tuning-step>\r\n"
				+ "            <tuning-octave>2</tuning-octave>\r\n" + "          </staff-tuning>\r\n"
				+ "          <staff-tuning line=\"3\">\r\n" + "            <tuning-step>D</tuning-step>\r\n"
				+ "            <tuning-octave>3</tuning-octave>\r\n" + "          </staff-tuning>\r\n"
				+ "          <staff-tuning line=\"4\">\r\n" + "            <tuning-step>G</tuning-step>\r\n"
				+ "            <tuning-octave>3</tuning-octave>\r\n" + "          </staff-tuning>\r\n"
				+ "          <staff-tuning line=\"5\">\r\n" + "            <tuning-step>B</tuning-step>\r\n"
				+ "            <tuning-octave>3</tuning-octave>\r\n" + "          </staff-tuning>\r\n"
				+ "          <staff-tuning line=\"6\">\r\n" + "            <tuning-step>E</tuning-step>\r\n"
				+ "            <tuning-octave>4</tuning-octave>\r\n" + "          </staff-tuning>\r\n"
				+ "        </staff-details>\r\n" + "      </attributes>\r\n" + "      <note>\r\n"
				+ "        <pitch>\r\n" + "          <step>E</step>\r\n" + "          <octave>2</octave>\r\n"
				+ "        </pitch>\r\n" + "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>6</string>\r\n" + "            <fret>0</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>B</step>\r\n" + "          <octave>2</octave>\r\n" + "        </pitch>\r\n"
				+ "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>5</string>\r\n" + "            <fret>2</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>E</step>\r\n" + "          <octave>3</octave>\r\n" + "        </pitch>\r\n"
				+ "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>4</string>\r\n" + "            <fret>2</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>G</step>\r\n" + "          <alter>1</alter>\r\n" + "          <octave>3</octave>\r\n"
				+ "        </pitch>\r\n" + "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>3</string>\r\n" + "            <fret>1</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>B</step>\r\n" + "          <octave>3</octave>\r\n" + "        </pitch>\r\n"
				+ "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>2</string>\r\n" + "            <fret>0</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>E</step>\r\n" + "          <octave>4</octave>\r\n" + "        </pitch>\r\n"
				+ "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>1</string>\r\n" + "            <fret>0</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>B</step>\r\n" + "          <octave>3</octave>\r\n" + "        </pitch>\r\n"
				+ "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>2</string>\r\n" + "            <fret>0</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>G</step>\r\n" + "          <alter>1</alter>\r\n" + "          <octave>3</octave>\r\n"
				+ "        </pitch>\r\n" + "        <duration>8</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>eighth</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>3</string>\r\n" + "            <fret>1</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "    </measure>\r\n"
				+ "    <measure number=\"2\">\r\n" + "      <attributes>\r\n" + "        <divisions>16</divisions>\r\n"
				+ "        <key>\r\n" + "          <fifths>0</fifths>\r\n" + "        </key>\r\n"
				+ "      </attributes>\r\n" + "      <note>\r\n" + "        <pitch>\r\n"
				+ "          <step>E</step>\r\n" + "          <octave>4</octave>\r\n" + "        </pitch>\r\n"
				+ "        <duration>64</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>whole</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>1</string>\r\n" + "            <fret>0</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <chord/>\r\n"
				+ "        <pitch>\r\n" + "          <step>B</step>\r\n" + "          <octave>3</octave>\r\n"
				+ "        </pitch>\r\n" + "        <duration>64</duration>\r\n" + "        <voice>1</voice>\r\n"
				+ "        <type>whole</type>\r\n" + "        <notations>\r\n" + "          <technical>\r\n"
				+ "            <string>2</string>\r\n" + "            <fret>0</fret>\r\n" + "          </technical>\r\n"
				+ "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n" + "        <chord/>\r\n"
				+ "        <pitch>\r\n" + "          <step>G</step>\r\n" + "          <alter>1</alter>\r\n"
				+ "          <octave>3</octave>\r\n" + "        </pitch>\r\n" + "        <duration>64</duration>\r\n"
				+ "        <voice>1</voice>\r\n" + "        <type>whole</type>\r\n" + "        <notations>\r\n"
				+ "          <technical>\r\n" + "            <string>3</string>\r\n" + "            <fret>1</fret>\r\n"
				+ "          </technical>\r\n" + "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n"
				+ "        <chord/>\r\n" + "        <pitch>\r\n" + "          <step>E</step>\r\n"
				+ "          <octave>3</octave>\r\n" + "        </pitch>\r\n" + "        <duration>64</duration>\r\n"
				+ "        <voice>1</voice>\r\n" + "        <type>whole</type>\r\n" + "        <notations>\r\n"
				+ "          <technical>\r\n" + "            <string>4</string>\r\n" + "            <fret>2</fret>\r\n"
				+ "          </technical>\r\n" + "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n"
				+ "        <chord/>\r\n" + "        <pitch>\r\n" + "          <step>B</step>\r\n"
				+ "          <octave>2</octave>\r\n" + "        </pitch>\r\n" + "        <duration>64</duration>\r\n"
				+ "        <voice>1</voice>\r\n" + "        <type>whole</type>\r\n" + "        <notations>\r\n"
				+ "          <technical>\r\n" + "            <string>5</string>\r\n" + "            <fret>2</fret>\r\n"
				+ "          </technical>\r\n" + "        </notations>\r\n" + "      </note>\r\n" + "      <note>\r\n"
				+ "        <chord/>\r\n" + "        <pitch>\r\n" + "          <step>E</step>\r\n"
				+ "          <octave>2</octave>\r\n" + "        </pitch>\r\n" + "        <duration>64</duration>\r\n"
				+ "        <voice>1</voice>\r\n" + "        <type>whole</type>\r\n" + "        <notations>\r\n"
				+ "          <technical>\r\n" + "            <string>6</string>\r\n" + "            <fret>0</fret>\r\n"
				+ "          </technical>\r\n" + "        </notations>\r\n" + "      </note>\r\n" + "    </measure>\r\n"
				+ "  </part>\r\n" + "</score-partwise>\r\n" + "";
		
		parser.setInput(input);
		System.out.println(parser.getInstrumentInfo());
		System.out.println(parser.getSheetInfo());
		


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
	void saveFile(ActionEvent event) {
		Stage stage = (Stage) anchorpane.getScene().getWindow();
		// Creates a File chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName("Untitled");
		// Extension Filter
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("MP3 Audio", "*.mp3"),
				new FileChooser.ExtensionFilter("WAV Audio", "*.wav"),
				new FileChooser.ExtensionFilter("PDF", "*.pdf"),
				new FileChooser.ExtensionFilter("ZIP", "*.zip"),
				new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif"),
				new FileChooser.ExtensionFilter("Plain Text", "*.txt")
				);
		try {
			fileChooser.showSaveDialog(stage);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void exitApp(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Music Previewer");
		alert.setHeaderText("You are about to exit the application!");
		alert.setContentText("Do you want to save before exiting?");

		/*
		 * Customize ButtonType
		 * ButtonType yesBtn = new ButtonType("YES", ButtonData.YES);
		 * ButtonType noBtn = new ButtonType("NO", ButtonData.CANCEL_CLOSE);
		 */

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

	// Media-Player Methods (Temporary - Plays .mp3 & .wav files)

	@FXML
	void playAudio(ActionEvent event) {
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
	void rewindAudio(ActionEvent event) {
		// WIP
	}

	@FXML
	void stopAudio(ActionEvent event) {
		// WIP
		isPlaying = false;
		this.playPauseImage.setImage(new Image("image_assets/play.png"));
		this.playPauseButton.setText("Play");
	}

	@FXML
	void playPauseBtnImage(MouseEvent event) {
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
