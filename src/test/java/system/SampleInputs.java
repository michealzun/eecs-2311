package system;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import converter.InstrumentSetting;
import converter.Score;
import utility.MusicXMLCreator;
import utility.Settings;

class SampleInputs {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
    void init() {
    	Settings.getInstance().setInstrumentSetting(InstrumentSetting.AUTO);
    	Settings.getInstance().setDefaultTuning();
    }

	@AfterEach
	void tearDown() throws Exception {
		Settings.getInstance().setInstrumentSetting(InstrumentSetting.AUTO);
		Settings.getInstance().setDefaultTuning();
	}

	@Disabled
	@Test
	void test() throws IOException, URISyntaxException {
		
		URL outDirURL = this.getClass().getClassLoader().getResource("../../resources/test/outputs");
		Path outDirPath = Path.of(outDirURL.toURI());
		File outDir= outDirPath.toFile();
		File[] outputFiles = outDir.listFiles();
		for (File file : outputFiles) file.delete();
		
		URL inputDirURL = this.getClass().getClassLoader().getResource("../../resources/test/system/");
		Path inputDirPath = Path.of(inputDirURL.toURI());
		File inputDir = inputDirPath.toFile();
		File[] inputFiles = inputDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
		for (File input : inputFiles) {
			String inputText = Files.readString(input.toPath());
			Score score = new Score(inputText);
			MusicXMLCreator mxlc = new MusicXMLCreator(score);
			String mxlOutput = mxlc.generateMusicXML();
			Path outFile = outDirPath.resolve(input.getName()+".musicxml");
			Files.writeString(outFile, mxlOutput);
		}
		Runtime r = Runtime.getRuntime();
		String command = "open " + outDirPath.toAbsolutePath();
		@SuppressWarnings("unused")
		Process p = r.exec(command);
	}

}
