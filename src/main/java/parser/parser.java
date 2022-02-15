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
				Node child = partChildren.item(j);
				
				if(((Element)child).getTagName()=="measure") {// for each measure
					Measure newMeasure=new Measure();
					newMeasure.number=Integer. parseInt(((Element)child).getAttribute("number"));
					
					
					
					
					
				
					newPart.measures.add(newMeasure);
	             }
				
			}
			partList.add(newPart);

		}
		
		NodeList attributes= doc.getElementsByTagName("attributes");
		NodeList divisions= doc.getElementsByTagName("divisions");
		NodeList key= doc.getElementsByTagName("key");
		NodeList clef= doc.getElementsByTagName("clef");
		//for the staff
		NodeList staffDetails= doc.getElementsByTagName("staff-details");
		NodeList staffLines= doc.getElementsByTagName("staff-lines");
		NodeList staffTuning= doc.getElementsByTagName("staff-tuning");
		NodeList tuningStep= doc.getElementsByTagName("tuning-step");
		NodeList tuningOctave= doc.getElementsByTagName("tuning-octave");
		//for the notes
		NodeList note= doc.getElementsByTagName("note");
		NodeList pitch= doc.getElementsByTagName("pitch");
		NodeList step= doc.getElementsByTagName("step");
		NodeList octave= doc.getElementsByTagName("octave");
		NodeList duration= doc.getElementsByTagName("duration");
		NodeList voice= doc.getElementsByTagName("voice");
		NodeList type= doc.getElementsByTagName("type");
		NodeList notations= doc.getElementsByTagName("notations");
		NodeList technical= doc.getElementsByTagName("technical");
		NodeList string= doc.getElementsByTagName("string");
		NodeList fret= doc.getElementsByTagName("fret");
		
		


		}
	}
}
