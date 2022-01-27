//package GUI;
//
//import java.io.File;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.ResourceBundle;
//
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//
//public class MediaController implements Initializable {
//	@FXML
//	private Button playPauseButton;
//	
//	private File directory;
//	private File[] files;
//
//	private ArrayList<File> songs;
//
//	private int songNumber;
//
//	private Media media;
//	private MediaPlayer mediaPlayer;
//
//	boolean isPlaying = false;
//
//	@Override
//	public void initialize(URL arg0, ResourceBundle resources) {
//		songs = new ArrayList<File>();
//
//		directory = new File("music");
//
//		files = directory.listFiles();
//
//		if(files != null) {
//			for (File file : files) {
//				songs.add(file);
//				System.out.println(file);
//			}
//		}
//
//		media = new Media(songs.get(songNumber).toURI().toString());
//		mediaPlayer = new MediaPlayer(media);
//		
//	}
//	public void playMedia() {
//		if (isPlaying) {
//			mediaPlayer.pause();
//			isPlaying = false;
//		}
//		else {
//			mediaPlayer.play();
//			isPlaying = true;
//		}
//	}
//}
