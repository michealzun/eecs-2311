package parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Parser {
	
    private Document doc;
    
    public void setInput(String input) {
    	try {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // input string into document format
		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(new InputSource(new StringReader(input)));
		doc.getDocumentElement().normalize();
    	}catch(Exception e) {}
	}

	public String getInstrumentInfo() {
		String instrumentInfo = "";
		NodeList scorePart = doc.getElementsByTagName("score-part");

		for (int i = 0; i < scorePart.getLength(); i++) // for each instrument
		{
			if (scorePart.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Node detail = scorePart.item(i).getChildNodes().item(1);
				instrumentInfo += "instrument " + ((Element) scorePart.item(i)).getAttribute("id") + " : "+ ((Element) detail).getTextContent() + "\n";
			}

		}
		return instrumentInfo;
    }

	public List<Part> getSheetInfo(){
		List<Part> partList = new ArrayList<Part>(); 

		NodeList parts = doc.getElementsByTagName("part");
		for (int i = 0; i < parts.getLength(); i++) { // for each part
			if (parts.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Part newPart = new Part();
				newPart.id = ((Element) parts.item(i)).getAttribute("id");

				NodeList partChildren = parts.item(i).getChildNodes();
				for (int j = 0; j < partChildren.getLength(); j++) {
					if (partChildren.item(j).getNodeType() == Node.ELEMENT_NODE) {
						Node partChild = partChildren.item(j);
						if (((Element) partChild).getTagName() == "measure") {// for each measure

							Measure newMeasure = new Measure();
							newMeasure.number = Integer.parseInt(((Element) partChild).getAttribute("number"));
							NodeList measureChildren = partChild.getChildNodes();
							for (int k = 0; k < measureChildren.getLength(); k++) {
								if (measureChildren.item(k).getNodeType() == Node.ELEMENT_NODE) {
									Node measureChild = measureChildren.item(k);
									if (((Element) measureChild).getTagName() == "attributes") {// for each measure's
																								// attribute
										NodeList attributeChildren = measureChild.getChildNodes();

										for (int l = 0; l < attributeChildren.getLength(); l++) {
											if (attributeChildren.item(l).getNodeType() == Node.ELEMENT_NODE) {
												Node attributeChild = attributeChildren.item(l);

												switch (((Element) attributeChild).getTagName()) {
												case "divisions": // measure's division
													newMeasure.divisions = Integer
															.parseInt(attributeChild.getTextContent());
													break;

												case "key": // measure's fifth
													NodeList keyChildren =attributeChild.getChildNodes();
													for (int m = 0; m < keyChildren.getLength(); m++) {
														if(keyChildren.item(m).getNodeType() == Node.ELEMENT_NODE) {
															Node keyChild = keyChildren.item(m);
															if(((Element) keyChild).getTagName() == "fifth");
																newMeasure.fifth = Integer.parseInt(keyChild.getTextContent());
														}
													}
													break;

												case "clef": // measure's clef
													NodeList clefChildren = attributeChild.getChildNodes();

													for (int m = 0; m < clefChildren.getLength(); m++) {
														if (clefChildren.item(m).getNodeType() == Node.ELEMENT_NODE) {
															Node clefChild = clefChildren.item(m);

															if (((Element) clefChild).getTagName() == "sign") { // clef sign
																newMeasure.clefSigh = clefChild.getTextContent();
															} else if (((Element) clefChild).getTagName() == "line") { // clef lines
																newMeasure.clefLine = Integer.parseInt(clefChild.getTextContent());
															}
														}
													}
													break;

												case "staff-details": // staff stuff
													NodeList staffDetailsChildren = attributeChild.getChildNodes();

													for (int m = 0; m < staffDetailsChildren.getLength(); m++) {
														if (staffDetailsChildren.item(m).getNodeType() == Node.ELEMENT_NODE) {
															Node clefChild = staffDetailsChildren.item(m);
														
															if (((Element) clefChild).getTagName() == "staff-tuning") {
																Line newLine = new Line();
																newLine.number = Integer.parseInt(((Element) clefChild).getAttribute("line")); // for each line
																NodeList staffTuningChildren = clefChild.getChildNodes();

																for (int n = 0; n < staffTuningChildren.getLength(); n++) {
																	if (staffTuningChildren.item(n).getNodeType() == Node.ELEMENT_NODE) {
																		Node staffTuningChild = staffTuningChildren.item(n);

																		if (((Element) staffTuningChild).getTagName() == "tuning-step") { // clef's sign
																			newLine.step = staffTuningChild.getTextContent();
																		} else if (((Element) staffTuningChild).getTagName() == "tuning-octave") { // clef's lines
																			newLine.octave = clefChild.getTextContent();
																		}
																	}
																}
																newMeasure.lines.add(newLine);
															}
														}
														
													}
													break;
												}
											}
										}
									} else if ((((Element) measureChild).getTagName() == "note")) { // for each note
										NodeList noteChildren = measureChild.getChildNodes();

										Note newNote = new Note();
										for (int l = 0; l < noteChildren.getLength(); l++) {
											if (noteChildren.item(l).getNodeType() == Node.ELEMENT_NODE) {
												Node noteChild = noteChildren.item(l);

												switch (((Element) noteChild).getTagName()) {
												case "pitch": // note pitch
													NodeList pitchChildren = noteChild.getChildNodes();
													for (int m = 0; m < pitchChildren.getLength(); m++) {
														if (pitchChildren.item(m).getNodeType() == Node.ELEMENT_NODE) {
															Node pitchChild = pitchChildren.item(m);

															if (((Element) pitchChild).getTagName() == "step") { // note step
																newNote.step = pitchChild.getTextContent();
															} else if (((Element) pitchChild).getTagName() == "octave") { // note octave
																newNote.octave = Integer.parseInt(pitchChild.getTextContent());
															}
														}
													}
													break;
												case "duration": // note duration
													newNote.duration = Integer.parseInt(noteChild.getTextContent());
													break;
												case "voice": // note voice
													newNote.voice = Integer.parseInt(noteChild.getTextContent());
													break;
												case "type": // note icon
													newNote.type = noteChild.getTextContent();
													break;
												case "notations":
													NodeList notationChildren = noteChild.getChildNodes(); 
													for (int m = 0; m < notationChildren.getLength(); m++) {
														if (notationChildren.item(m).getNodeType() == Node.ELEMENT_NODE) {
															Node notationChild = notationChildren.item(m);
															
															if (((Element) notationChild).getTagName() == "technical") {
																NodeList technicalChildren = notationChild.getChildNodes(); 
																
																for (int n = 0; n < technicalChildren.getLength(); n++) {
																	if (technicalChildren.item(n).getNodeType() == Node.ELEMENT_NODE) {
																		Node technicalChild = technicalChildren.item(n);
																		
																		if (((Element) technicalChild).getTagName() == "string") { // note step
																			newNote.string = Integer.parseInt(technicalChild.getTextContent());
																		} else if (((Element) technicalChild).getTagName() == "fret") { // note octave
																			newNote.fret = Integer.parseInt(technicalChild.getTextContent());
																		}
																			
																	}
																}
																
															}
																
														}
													}
													break;
												}
											}
										}
										newMeasure.notes.add(newNote);
									}
									newPart.measures.add(newMeasure);
								}
							}
						}
						
					}
				}
				partList.add(newPart);
			}
		}
		return partList;
	}
}