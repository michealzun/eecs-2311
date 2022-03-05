package GUI;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

public class About {
	
	@FXML
    private Hyperlink authors;

	@FXML
	void authorLink(ActionEvent event) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI("https://github.com/michealzun/eecs-2311/graphs/contributors"));
	}

	@FXML
	void githubLink(ActionEvent event) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI("https://github.com/michealzun/eecs-2311"));
	}

}
