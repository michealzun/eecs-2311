package GUI;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JFrame;
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
import parser.Measure;
import parser.Note;
import parser.Parser;
import parser.Part;

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
	static int measureBarLength = 32;
	static int measureWidth=400;
	static int measureSpacing=100;
	static int measuresPerLine=4;
	String input="";

	static void drawMeasure(int measureNumber, Measure m) {

		String measureType="";
		switch(m.lines.size()) {
		case 1:
			measureType="\uD834\uDD16";
			break;
		case 2:
			measureType="\uD834\uDD17";
			break;
		case 3:
			measureType="\uD834\uDD18";
			break;
		case 4:
			measureType="\uD834\uDD19";
			break;
		case 5:
			measureType="\uD834\uDD1A";
			break;
		case 6:
			measureType="\uD834\uDD1B";
			break;
		}

		for(int i = 0; i < measureBarLength; i++) {
			drawString(measureType, 50 + measureWidth * (measureNumber % measuresPerLine) + i * 30, 40 + measureSpacing * (measureNumber / measuresPerLine));
		}

		for (int i = 0; i < m.notes.size(); i++)  {
			drawNote(50 + measureWidth * (measureNumber % measuresPerLine), 40 + measureSpacing * (measureNumber / measuresPerLine), m.notes.get(i));
		}
	}

	static void drawNote(int noteX, int noteY, Note n) {
		// Note Location
		int x = noteX + n.duration; 
		int y = noteY; 

		//WIP

		// Note Type
		String noteType="";
		switch(n.type) {
		case "whole":
			noteType="\uD834\uDD5D";
			break;
		case "half":
			noteType="\uD834\uDD5E";
			break;
		case "quarter":
			noteType="\uD834\uDD5F";
			break;
		case "eighth":
			noteType="\uD834\uDD60";
			break;
		case "sixteenth":
			noteType="\uD834\uDD61";
			break;
		case "thirtysixth":
			noteType="\uD834\uDD62";
			break;
		}
		drawString(noteType, x, y);
	}

	private static void drawString(String noteType, int x, int y) {
		JFrame f = new JFrame() {
			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

				Font font = new Font("Bravura", Font.PLAIN, 32);

				g2.setFont(font);
				g2.drawString(noteType, x, y);

			}
		};
		f.setSize(200,200);
		f.setVisible(true);
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.parser = new Parser();
		parser.setInput(input);
		Part p = parser.getSheetInfo().get(0); // only do the first instrument for now
		List<Measure> measures = p.measures;

		for (int i = 0; i < measures.size(); i++) {
			drawMeasure(measures.get(i).number, measures.get(i));
		}
		
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
