package parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class parser {

	public static void main(String[] args) throws Exception {
		
		List<Part> partList= new ArrayList<Part>();
	
		Document doc=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("music.xml"));  //<---- our input 
		doc.getDocumentElement().normalize();

		//for which instrument
		NodeList scorePart= doc.getElementsByTagName("score-part");
		
		for (int i=0; i<scorePart.getLength();i++)  //for each instrument
		{ 
                NodeList childNodes = scorePart.item(i).getChildNodes();
                for(int j = 0; j < childNodes.getLength(); j++){
                    Node detail = childNodes.item(j);
                    if(((Element)detail).getTagName()=="part-name") {
                    	System.out.println("instrument: " + ((Element)detail).getTagName());
                        /*enter code here for displaying each intrument type
                        eg:  part 1 = drum
                        
                        
                        
                         */
                    	
                    }
		}
                
		//for the staff
		NodeList parts= doc.getElementsByTagName("part");
		for (i=0; i<parts.getLength();i++)  //for each part
		{ 		
			Part newPart= new Part();
			newPart.id=((Element)parts).getAttribute("id");
			
			NodeList partChildren = parts.item(i).getChildNodes();
			for (int j=0; j<partChildren.getLength();j++){  
				Node partChild = partChildren.item(j);

				if(((Element)partChild).getTagName()=="measure") {// for each measure
					Measure newMeasure=new Measure();
					newMeasure.number=Integer. parseInt(((Element)partChild).getAttribute("number"));
					
					NodeList measureChildren = partChild.getChildNodes();
					for (int k=0; j<measureChildren.getLength();k++){  
						Node measureChild = measureChildren.item(k);
						
						
						if(((Element)measureChild).getTagName()=="attributes") {// for each measure's attribute
							NodeList attributeChildren = measureChild.getChildNodes();
							
							for (int l=0; l<attributeChildren.getLength();l++) {
								Node attributeChild=attributeChildren.item(l);
								
								switch(((Element)attributeChild).getTagName()) {
									case "divisions":    //measure's division
										newMeasure.divisions=Integer.parseInt(attributeChild.getTextContent());
										break;
										
									case "key": //measure's fifth
										newMeasure.fifth=Integer. parseInt(attributeChild.getFirstChild().getTextContent());
										break;
										
									case "clef":   //measure's clef
										NodeList clefChildren = attributeChild.getChildNodes();
										
										for (int m=0; m<clefChildren.getLength();m++) {     
											Node clefChild=clefChildren.item(m);
											 
											if(((Element)clefChild).getTagName()=="sign") {    // clef's sign
												newMeasure.clefSigh = clefChild.getTextContent();
											}else if (((Element)clefChild).getTagName()=="line") {   //clef's lines
												newMeasure.clefLine = Integer.parseInt(clefChild.getTextContent());
											}
										}								
										break;
										
									case "staff-details":     // staff stuff
										NodeList staffDetailsChildren = attributeChild.getChildNodes();
										
										for (int m=0; m<staffDetailsChildren.getLength();m++) {     
											Node clefChild=staffDetailsChildren.item(m);
											 
											if(((Element)clefChild).getTagName()=="staff-tuning") {    
												Line newLine=new Line();
												newLine.number= Integer.parseInt(((Element)clefChild).getAttribute("line")); //for each line
												NodeList staffTuningChildren=clefChild.getChildNodes();
												
												for (int n=0; n<staffTuningChildren.getLength();n++) {     
													Node staffTuningChild=staffTuningChildren.item(m);
													 
													if(((Element)staffTuningChild).getTagName()=="tuning-step") {    // clef's sign
														newLine.step=staffTuningChild.getTextContent();
													}else if (((Element)staffTuningChild).getTagName()=="tuning-octave") {   //clef's lines
														newLine.octavive= Integer.parseInt(clefChild.getTextContent());
													}
												}			
												newMeasure.lines.add(newLine);
											}
										}						
										break;
								}
							}
						}else if((((Element)measureChild).getTagName()=="note")){ // for each note
							NodeList noteChildren = measureChild.getChildNodes();
							
							Note newNote= new Note();
							for (int l=0; l<noteChildren.getLength();l++) {
								Node noteChild=noteChildren.item(l);
								
								switch(((Element)noteChild).getTagName()) { 
								case"pitch":                     //note pitch
									NodeList pitchChildren=noteChild.getChildNodes();
									for (int m=0; m<pitchChildren.getLength();m++) {
										Node pitchChild=pitchChildren.item(m);
										
										if(((Element)pitchChild).getTagName()=="step") {  //note step
											newNote.step= pitchChild.getTextContent();
										}else if(((Element)pitchChild).getTagName()=="octave") {   //note octave
											newNote.octave= Integer.parseInt(pitchChild.getTextContent());
										}
									}
									break;
								case"duration":                       // note duration
									newNote.duration=Integer.parseInt(noteChild.getTextContent());  
									break;
								case"voice":                          // note voice
									newNote.voice=Integer.parseInt(noteChild.getTextContent());  
									break;
								case"type":                               // note icon
									newNote.type=noteChild.getTextContent();  
									break;
								case"notations":
									NodeList technicalChildren=noteChild.getChildNodes().item(0).getChildNodes();
									
									for (int m=0; m<technicalChildren.getLength();m++) {
										Node technicalChild=technicalChildren.item(m);
										
										if(((Element)technicalChild).getTagName()=="string") {  //note step
											newNote.string= Integer.parseInt(technicalChild.getTextContent());
										}else if(((Element)technicalChild).getTagName()=="fret") {   //note octave
											newNote.fret= Integer.parseInt(technicalChild.getTextContent());
										}
									}
									break;							
							}
						}
					}
					newPart.measures.add(newMeasure);
	             }
			}
			partList.add(newPart);
		}
		}
	}
}
}