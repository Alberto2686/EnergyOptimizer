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
		NodeList frequencies = docEle.getElementsByTagName("Profile:Frequency_voltage");
		NodeList atomicOperations = docEle.getElementsByTagName("Profile:Atomic_operation");
		NodeList consumptions = docEle.getElementsByTagName("Profile:Consumption");
		
		NodeList UMLelements = docEle.getElementsByTagName("packagedElement");
		
		//First step: UseCase, Stakeholder, Hardware set, Hardware alternative, Hardware component, Software component, Interface
		if(UMLelements != null && UMLelements.getLength() > 0) {
			for(int i = 0 ; i < UMLelements.getLength();i++) {
				Element element = (Element) UMLelements.item(i);
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
						//Hardware sets
						for(int j=0; j<hardwareSets.getLength();j++)
							if(((Element)hardwareSets.item(j)).getAttribute("base_Package").equals(element.getAttribute("xmi:id"))){
								HardwareSet temp = new HardwareSet(element.getAttribute("xmi:id"),element.getAttribute("name"));
								project.getHardwareSets().add(temp);
							}
						//CPU Alternatives and CPUs
						for(int j=0; j<cpuAlternatives.getLength();j++)
							if(((Element)cpuAlternatives.item(j)).getAttribute("base_Package").equals(element.getAttribute("xmi:id")))
								for(HardwareSet hwSet:project.getHardwareSets())
									if (hwSet.getId().equals(((Element)element.getParentNode()).getAttribute("xmi:id"))){
										HardwareAlternative hwAlternative=new HardwareAlternative(element.getAttribute("xmi:id"),element.getAttribute("name"));
										NodeList hwComponents = element.getChildNodes();
										for(int k=0;k<hwComponents.getLength();k++)
											if(hwComponents.item(k).getNodeName().equals("packagedElement")){
												Element hwComponent = (Element)hwComponents.item(k);
												for(int l=0; l<cpus.getLength();l++){
													if(((Element)cpus.item(l)).getAttribute("base_Class").equals(hwComponent.getAttribute("xmi:id"))){
														Element hwComponentProfile=(Element)cpus.item(l);
														Cpu cpu= new Cpu(hwComponent.getAttribute("name"),hwComponent.getAttribute("xmi:id"),parseInt(hwComponentProfile.getAttribute("cores")),parseInt(hwComponentProfile.getAttribute("productive_process")),parseDouble(hwComponentProfile.getAttribute("thermal_design_power")));
														for(int m=0; m<frequencies.getLength();m++){
															if(hwComponentProfile.getAttribute("frequencies_voltages").contains(((Element)frequencies.item(m)).getAttribute("xmi:id")))
																cpu.getFrequenciesVoltages().add(new FrequencyVoltage(getInt(frequencies,m,"frequency"), getDouble(frequencies,m,"voltage")));
														}
														hwAlternative.getHardwareComponents().add(cpu);
													}
												}
											}
										hwSet.getCpuAlternatives().add(hwAlternative);
									}
							
						//HDD Alternatives and HDDs
						for(int j=0; j<hddAlternatives.getLength();j++)
							if(((Element)hddAlternatives.item(j)).getAttribute("base_Package").equals(element.getAttribute("xmi:id"))){
								for(HardwareSet hwSet:project.getHardwareSets())
									if (hwSet.getId().equals(((Element)element.getParentNode()).getAttribute("xmi:id"))){
										HardwareAlternative hwAlternative=new HardwareAlternative(element.getAttribute("xmi:id"),element.getAttribute("name"));
										NodeList hwComponents = element.getChildNodes();
										for(int k=0;k<hwComponents.getLength();k++)
											if(hwComponents.item(k).getNodeName().equals("packagedElement")){
												Element hwComponent = (Element)hwComponents.item(k);
												for(int l=0; l<hdds.getLength();l++){
													if(((Element)hdds.item(l)).getAttribute("base_Class").equals(hwComponent.getAttribute("xmi:id"))){
														Element hwComponentProfile=(Element)hdds.item(l);
														double bandwidth=parseDouble(hwComponentProfile.getAttribute("bandwidth"));
														double workConsumption=parseDouble(hwComponentProfile.getAttribute("work_consumption"));
														double idleConsumption=parseDouble(hwComponentProfile.getAttribute("idle_consumption"));
														Hdd hdd= new Hdd(hwComponent.getAttribute("name"),hwComponent.getAttribute("xmi:id"),bandwidth,workConsumption,idleConsumption);
														hwAlternative.getHardwareComponents().add(hdd);
													}
												}
											}
										hwSet.getHddAlternatives().add(hwAlternative);
									}
							}
						//Memory Alternatives and Memories
						for(int j=0; j<memoryAlternatives.getLength();j++)
							if(((Element)memoryAlternatives.item(j)).getAttribute("base_Package").equals(element.getAttribute("xmi:id"))){
								for(HardwareSet hwSet:project.getHardwareSets())
									if (hwSet.getId().equals(((Element)element.getParentNode()).getAttribute("xmi:id"))){
										HardwareAlternative hwAlternative=new HardwareAlternative(element.getAttribute("xmi:id"),element.getAttribute("name"));
										NodeList hwComponents = element.getChildNodes();
										for(int k=0;k<hwComponents.getLength();k++)
											if(hwComponents.item(k).getNodeName().equals("packagedElement")){
												Element hwComponent = (Element)hwComponents.item(k);
												for(int l=0; l<memories.getLength();l++){
													if(((Element)memories.item(l)).getAttribute("base_Class").equals(hwComponent.getAttribute("xmi:id"))){
														Element hwComponentProfile=(Element)memories.item(l);
														double bandwidth=parseDouble(hwComponentProfile.getAttribute("bandwidth"));
														double workConsumption=parseDouble(hwComponentProfile.getAttribute("work_consumption"));
														double idleConsumption=parseDouble(hwComponentProfile.getAttribute("idle_consumption"));
														Memory memory= new Memory(hwComponent.getAttribute("name"),hwComponent.getAttribute("xmi:id"),bandwidth,workConsumption,idleConsumption);
														hwAlternative.getHardwareComponents().add(memory);
													}
												}
											}
										hwSet.getMemoryAlternatives().add(hwAlternative);
									}
							}
						//Network Alternatives and Network devices
						for(int j=0; j<networkAlternatives.getLength();j++)
							if(((Element)networkAlternatives.item(j)).getAttribute("base_Package").equals(element.getAttribute("xmi:id"))){
								for(HardwareSet hwSet:project.getHardwareSets())
									if (hwSet.getId().equals(((Element)element.getParentNode()).getAttribute("xmi:id"))){
										HardwareAlternative hwAlternative=new HardwareAlternative(element.getAttribute("xmi:id"),element.getAttribute("name"));
										NodeList hwComponents = element.getChildNodes();
										for(int k=0;k<hwComponents.getLength();k++)
											if(hwComponents.item(k).getNodeName().equals("packagedElement")){
												Element hwComponent = (Element)hwComponents.item(k);
												for(int l=0; l<networks.getLength();l++){
													if(((Element)networks.item(l)).getAttribute("base_Class").equals(hwComponent.getAttribute("xmi:id"))){
														Element hwComponentProfile=(Element)networks.item(l);
														double bandwidth=parseDouble(hwComponentProfile.getAttribute("bandwidth"));
														double mbConsumption=parseDouble(hwComponentProfile.getAttribute("MB_consumption"));
														Network network= new Network(hwComponent.getAttribute("xmi:id"),hwComponent.getAttribute("name"),bandwidth,mbConsumption);
														hwAlternative.getHardwareComponents().add(network);
													}
												}
											}
										hwSet.getNetworkAlternatives().add(hwAlternative);
									}
							}
						//Platform Alternatives and Platforms
						for(int j=0; j<platformAlternatives.getLength();j++)
							if(((Element)platformAlternatives.item(j)).getAttribute("base_Package").equals(element.getAttribute("xmi:id"))){
								for(HardwareSet hwSet:project.getHardwareSets())
									if (hwSet.getId().equals(((Element)element.getParentNode()).getAttribute("xmi:id"))){
										HardwareAlternative hwAlternative=new HardwareAlternative(element.getAttribute("xmi:id"),element.getAttribute("name"));
										NodeList hwComponents = element.getChildNodes();
										for(int k=0;k<hwComponents.getLength();k++)
											if(hwComponents.item(k).getNodeName().equals("packagedElement")){
												Element hwComponent = (Element)hwComponents.item(k);
												for(int l=0; l<platforms.getLength();l++){
													if(((Element)platforms.item(l)).getAttribute("base_Class").equals(hwComponent.getAttribute("xmi:id"))){
														Element hwComponentProfile=(Element)platforms.item(l);
														double virtualization=parseDouble(hwComponentProfile.getAttribute("virtualization"));
														double scheduling=parseDouble(hwComponentProfile.getAttribute("scheduling"));
														double framework=parseDouble(hwComponentProfile.getAttribute("framework"));
														double jvm=parseDouble(hwComponentProfile.getAttribute("JVM"));
														double gc=parseDouble(hwComponentProfile.getAttribute("garbage_collector"));
														double os=parseDouble(hwComponentProfile.getAttribute("operative_system"));
														Platform platform= new Platform(hwComponent.getAttribute("name"),hwComponent.getAttribute("xmi:id"),virtualization,scheduling,framework,jvm,gc,os);
														hwAlternative.getHardwareComponents().add(platform);
													}
												}
											}
										hwSet.getPlatformAlternatives().add(hwAlternative);
									}
							}
						//Other Alternatives and Others
						for(int j=0; j<otherAlternatives.getLength();j++)
							if(((Element)otherAlternatives.item(j)).getAttribute("base_Package").equals(element.getAttribute("xmi:id"))){
								for(HardwareSet hwSet:project.getHardwareSets())
									if (hwSet.getId().equals(((Element)element.getParentNode()).getAttribute("xmi:id"))){
										HardwareAlternative hwAlternative=new HardwareAlternative(element.getAttribute("xmi:id"),element.getAttribute("name"));
										NodeList hwComponents = element.getChildNodes();
										for(int k=0;k<hwComponents.getLength();k++)
											if(hwComponents.item(k).getNodeName().equals("packagedElement")){
												Element hwComponent = (Element)hwComponents.item(k);
												for(int l=0; l<others.getLength();l++){
													if(((Element)others.item(l)).getAttribute("base_Class").equals(hwComponent.getAttribute("xmi:id"))){
														Element hwComponentProfile=(Element)others.item(l);
														double busses=parseDouble(hwComponentProfile.getAttribute("busses"));
														double sensors=parseDouble(hwComponentProfile.getAttribute("sensors"));
														double cooling=parseDouble(hwComponentProfile.getAttribute("cooling"));
														double peripheralDevices=parseDouble(hwComponentProfile.getAttribute("peripheral_devices"));
														double display=parseDouble(hwComponentProfile.getAttribute("display"));
														double ups=parseDouble(hwComponentProfile.getAttribute("uninterruptible_power_supply"));
														Other other= new Other(hwComponent.getAttribute("name"),hwComponent.getAttribute("xmi:id"),busses,sensors,cooling,peripheralDevices,display,ups);
														for(int m=0; m<consumptions.getLength();m++)
															if(hwComponentProfile.getAttribute("other").contains(((Element)consumptions.item(m)).getAttribute("xmi:id")))
																other.getOtherConsumption().add(new OtherConsumption(parseString(consumptions,m,"source"), getDouble(consumptions,m,"consumption")));
														hwAlternative.getHardwareComponents().add(other);
													}
												}
											}
										hwSet.getOtherAlternatives().add(hwAlternative);
									}
							}
					break;
					case "uml:Component":
						for(int j=0; j<components.getLength();j++)
							if(((Element)components.item(j)).getAttribute("base_Component").equals(element.getAttribute("xmi:id"))){
								Component component=new Component(element.getAttribute("xmi:id"), element.getAttribute("name"),getInt(components,j,"function_points"));
								for(int k=0; k<atomicOperations.getLength();k++)
									if(((Element)atomicOperations.item(k)).getAttribute("xmi:id").equals(((Element)components.item(j)).getAttribute("atomic_operations")))
										component.getAtomicOperations().add(new AtomicOperation(((Element)atomicOperations.item(k)).getAttribute("name"),getDouble(atomicOperations, k, "cost"),getInt(atomicOperations, k,"number")));
								project.getComponents().add(component);
							}
					break;
					case "uml:Interface":
						project.getInterfaces().add(new Interface(element.getAttribute("xmi:id"),element.getAttribute("name")));
					break;
					//TODO:sequence!
				}
			}
		}
		
		//Second step: Association_Enhanced, Connector
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
								
								AssociationEnhanced temp = new AssociationEnhanced(((Element)ownedEnd2).getAttribute("type"), ((Element)ownedEnd1).getAttribute("type"), getDouble(association,j,"probability"));
								if(project.isActor(((Element)ownedEnd1).getAttribute("type")))
									temp = new AssociationEnhanced(((Element)ownedEnd1).getAttribute("type"), ((Element)ownedEnd2).getAttribute("type"), getDouble(association,j,"probability"));
								temp.bind(project.getStakeholders(), project.getFunctionalRequirements());
								project.getAssociations().add(temp);
							}
					break;
					case "uml:Usage":
						for(int j=0; j<connectors.getLength();j++)
							if(((Element)connectors.item(j)).getAttribute("base_Usage").equals(element.getAttribute("xmi:id"))){
								Connector connector = new Connector(element.getAttribute("xmi:id"),element.getAttribute("name"),element.getAttribute("client"),element.getAttribute("supplier"),getInt(connectors,j,"function_points"),false);
								connector.bind(project.getInterfaces(),project.getComponents());
								project.getConnectors().add(connector);
							}
					break;
					case "uml:InterfaceRealization":
						for(int j=0; j<connectors.getLength();j++)
							if(((Element)connectors.item(j)).getAttribute("base_InterfaceRealization").equals(element.getAttribute("xmi:id"))){
								Connector connector = new Connector(element.getAttribute("xmi:id"),element.getAttribute("name"),element.getAttribute("client"),element.getAttribute("supplier"),getInt(connectors,j,"function_points"),true);
								connector.bind(project.getInterfaces(),project.getComponents());
								project.getConnectors().add(connector);
							}
					break;
				}
			}
		}
		
	}

	private String parseString(NodeList nodelist, int position, String attribute) {
		try{
			return ((Element)nodelist.item(position)).getAttribute(attribute);
		} catch(Exception e){}
		return "";
	}
	
	private int getInt(NodeList nodelist, int position, String attribute) {
		return parseInt(((Element)nodelist.item(position)).getAttribute(attribute));
	}

	private double getDouble(NodeList nodelist, int position, String attribute) {
		return parseDouble(((Element)nodelist.item(position)).getAttribute(attribute));
	}
	
	private int parseInt(String input){
		try{
			return Integer.parseInt(input);
		} catch(Exception e){}
		return 0;
	}
	
	private double parseDouble(String input){
		try{
			return Double.parseDouble(input);
		} catch(Exception e){}
		return 0;
	}
}