package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import parser.Measure;
import parser.Note;
import parser.Parser;
import parser.Part;

@SuppressWarnings("serial")
public class UnicodeText extends JPanel {
	private Parser parse = new Parser();
	private int measureBarLength = 8;
	private int measureWidth = 200;
	private int measureSpacing = 100;
	private int measuresPerLine = 4;
	private int fontSize = 50;
	private BufferedImage paintImage;
	private Graphics2D g2d;

	public UnicodeText(String input) {
		parse.setInput(input);
		//		if (f == null) {
		this.setFont(new Font("Bravura", Font.PLAIN, fontSize));
		this.setSize(1000,1000);
		paintImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		this.setBackground(Color.decode("#FFFFFF"));
		this.setVisible(true);
		//		}
	}

	public void saveImage() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		// WIP
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.jpg", "jpg"));
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			try { 
				ImageIO.write(paintImage, "jpg", fileChooser.getSelectedFile());
			} catch (IOException ex) {
				System.out.println("Failed to save image!");
			}
		} else {
			System.out.println("No file choosen!");
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Part p = parse.getSheetInfo().get(0); // only do the first instrument for now
		List<Measure> measures = p.measures;
		this.g2d = (Graphics2D) g;
		this.g2d.drawImage(paintImage, 0, 0, null);
		GraphicsEnvironment.getLocalGraphicsEnvironment();
		for (int i = 0; i < measures.size(); i++) {
			drawMeasure(measures.get(i));
		}
		g2d.dispose();
	}

	/*
	 * public void run() {
			Part p = parse.getSheetInfo().get(0); // only do the first instrument for now
			List<Measure> measures = p.measures;

			for (int i = 0; i < measures.size(); i++) {
				drawMeasure(measures.get(i));
			}
			return f;
		}
	 */

	private void drawMeasure(Measure m) {
		String measureType = "";
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
		int measureStartingX = 100 + measureWidth * ((m.number-1) % measuresPerLine);
		int measureStartingY = 100 + measureSpacing * ((m.number-1) / measuresPerLine);

		for(int i = 0; i < measureBarLength; i++) {
			System.out.println(measureStartingX + i * 50 +7);
			drawString(measureType, measureStartingX + i * 23 +7, measureStartingY);
		}
		// Measure Seps
		if((m.number-1) % measuresPerLine == 0) {
			drawString("\uD834\uDD00",measureStartingX, measureStartingY-10);
			drawString("\uD834\uDD00",measureStartingX, measureStartingY+10);
		}
		drawString("\uD834\uDD00",measureStartingX+measureSpacing*2, measureStartingY-10);
		drawString("\uD834\uDD00",measureStartingX+measureSpacing*2, measureStartingY+10);

		for (int i = 0; i < m.notes.size(); i++)  {
			drawNote(measureStartingX, measureStartingY, m.notes.get(i));
		}
	}

	private void drawNote(int noteX, int noteY, Note n) {
		// Note Location
		int x = noteX; 
		int y = noteY; 

		// WIP

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


	private void drawString(String noteType, int x, int y) {
		//		paintComponent(getGraphics());
		Graphics2D g = paintImage.createGraphics();
		g.setFont(new Font("Bravura", Font.PLAIN, fontSize));
		g.setBackground(Color.white);
		g.setPaint(Color.black);
		g.drawString(noteType, x, y);
		g.dispose();
	}

}

