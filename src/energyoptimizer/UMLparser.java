package energyoptimizer;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UMLparser {
	Document dom;
	String file;
	Project project;
	
	public UMLparser(Project project) {
		this.project=project;
	}

	public void parseXmlFile(String file){
		this.file = file;
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			dom = db.parse(file);


		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		parseDocument();
	}
	
	private void parseDocument(){
		//get the root element
		Element docEle = dom.getDocumentElement();

		//get a nodelist of elements
		NodeList stakeholders = docEle.getElementsByTagName("Profile:Stakeholder");
		NodeList association = docEle.getElementsByTagName("Profile:Association_enhanced");
		NodeList hardwareSets = docEle.getElementsByTagName("Profile:Hardware_set");
		NodeList cpuAlternatives = docEle.getElementsByTagName("Profile:CPU_alternative");
		NodeList hddAlternatives = docEle.getElementsByTagName("Profile:HDD_alternative");
		NodeList memoryAlternatives = docEle.getElementsByTagName("Profile:Memory_alternative");
		NodeList networkAlternatives = docEle.getElementsByTagName("Profile:Network_alternative");
		NodeList platformAlternatives= docEle.getElementsByTagName("Profile:Platform_alternative");
		NodeList otherAlternatives = docEle.getElementsByTagName("Profile:Other_alternative");
		NodeList cpus = docEle.getElementsByTagName("Profile:CPU");
		NodeList hdds = docEle.getElementsByTagName("Profile:HDD");
		NodeList memories = docEle.getElementsByTagName("Profile:Memory");
		NodeList networks = docEle.getElementsByTagName("Profile:Network");
		NodeList platforms = docEle.getElementsByTagName("Profile:Platform");
		NodeList others = docEle.getElementsByTagName("Profile:Other");
		NodeList cpusUsages = docEle.getElementsByTagName("Profile:CPU_usage");
		NodeList hddsUsages = docEle.getElementsByTagName("Profile:HDD_usage");
		NodeList memoriesUsages = docEle.getElementsByTagName("Profile:Memory_usage");
		NodeList networksUsages = docEle.getElementsByTagName("Profile:Network_usage");
		NodeList lifelines = docEle.getElementsByTagName("Profile:Lifeline_enhanced");
		NodeList components= docEle.getElementsByTagName("Profile:Component_enhanced");
		NodeList connectors = docEle.getElementsByTagName("Profile:Connector_enhanced");
		NodeList ranges = docEle.getElementsByTagName("Profile:Range");
		
		NodeList UMLelements = docEle.getElementsByTagName("packagedElement");
		
		//First step: UseCase, Stakeholder, Hardware_set
		if(UMLelements != null && UMLelements.getLength() > 0) {
			for(int i = 0 ; i < UMLelements.getLength();i++) {
				Element element = (Element) UMLelements.item(i);
				//System.out.println(element.getAttribute("xmi:type"));
				switch(element.getAttribute("xmi:type")){
					case "uml:UseCase":
						project.getFunctionalRequirements().add(new FunctionalRequirement(element.getAttribute("name"),element.getAttribute("xmi:id")));
					break;
					case "uml:Actor":
						for(int j=0; j<stakeholders.getLength();j++)
							if(((Element)stakeholders.item(j)).getAttribute("base_Actor").equals(element.getAttribute("xmi:id")))
								for(int k=0; k<ranges.getLength();k++)
									if(((Element)ranges.item(k)).getAttribute("xmi:id").equals(((Element)stakeholders.item(j)).getAttribute("average_number")))
										project.getStakeholders().add(new Stakeholder(element.getAttribute("name"),element.getAttribute("xmi:id"),getInt(ranges, k,"min"),getInt(ranges, k,"max")));		
					break;
					case "uml:Package":
						for(int j=0; j<hardwareSets.getLength();j++){
							if(((Element)hardwareSets.item(j)).getAttribute("base_Package").equals(element.getAttribute("xmi:id"))){
								HardwareSet temp = new HardwareSet();
								//project.getHardwareSets().add()
							}
						}
					break;
				}
			}
		}
		
		//Second step: Association_Enhanced
		if(UMLelements != null && UMLelements.getLength() > 0) {
			for(int i = 0 ; i < UMLelements.getLength();i++) {
				Element element = (Element) UMLelements.item(i);
				switch(element.getAttribute("xmi:type")){
					case "uml:Association":
						for(int j=0; j<association.getLength();j++)
							if(((Element)association.item(j)).getAttribute("base_Association").equals(element.getAttribute("xmi:id"))){
								Node ownedEnd1 = element.getFirstChild();
								while (!ownedEnd1.getNodeName().equals("ownedEnd"))
									ownedEnd1=ownedEnd1.getNextSibling();
								Node ownedEnd2 = ownedEnd1.getNextSibling();
								while (!ownedEnd2.getNodeName().equals("ownedEnd"))
									ownedEnd2=ownedEnd2.getNextSibling();
								
								AssociationEnhanced temp = new AssociationEnhanced(((Element)ownedEnd2).getAttribute("type"), ((Element)ownedEnd1).getAttribute("type"), Double.parseDouble(((Element)association.item(j)).getAttribute("probability")));
								if(project.isActor(((Element)ownedEnd1).getAttribute("type")))
									temp = new AssociationEnhanced(((Element)ownedEnd1).getAttribute("type"), ((Element)ownedEnd2).getAttribute("type"), Double.parseDouble(((Element)association.item(j)).getAttribute("probability")));
								temp.bind(project.getStakeholders(), project.getFunctionalRequirements());
								project.getAssociations().add(temp);
							}
					break;
				}
			}
		}
		
	}

	private int getInt(NodeList nodelist, int position, String attribute) {
		return Integer.parseInt(((Element)nodelist.item(position)).getAttribute(attribute));
	}
}