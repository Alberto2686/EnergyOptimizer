package energyoptimizer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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

		//get profile elements
		NodeList model = docEle.getElementsByTagName("uml:Model");
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
		NodeList lifelines = docEle.getElementsByTagName("Profile:Lifeline_enhanced");
		NodeList components= docEle.getElementsByTagName("Profile:Component_enhanced");
		NodeList connectors = docEle.getElementsByTagName("Profile:Connector_enhanced");
		NodeList ranges = docEle.getElementsByTagName("Profile:Range");
		NodeList frequencies = docEle.getElementsByTagName("Profile:Frequency_voltage");
		NodeList atomicOperations = docEle.getElementsByTagName("Profile:Atomic_operation");
		NodeList consumptions = docEle.getElementsByTagName("Profile:Consumption");
		NodeList cpuUsages = docEle.getElementsByTagName("Profile:CPU_usage");
		NodeList hddUsages = docEle.getElementsByTagName("Profile:HDD_usage");
		NodeList memoryUsages = docEle.getElementsByTagName("Profile:Memory_usage");
		NodeList times = docEle.getElementsByTagName("Profile:Time");
		NodeList sizes = docEle.getElementsByTagName("Profile:Size");
		NodeList cpuTimes = docEle.getElementsByTagName("Profile:CPU_time");
		NodeList defaultCpu = docEle.getElementsByTagName("Profile:Default_CPU");
		
		NodeList UMLelements = docEle.getElementsByTagName("packagedElement");
		
		//set project name and defaults
		project.setName(getString(model, 0, "name"));
		project.setDefaultCpuScore(getDouble(defaultCpu, 0, "performance_score"));
		
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
							if(getString(stakeholders, j, "base_Actor").equals(element.getAttribute("xmi:id")))
								for(int k=0; k<ranges.getLength();k++)
									if(getString(ranges,k,"xmi:id").equals(getString(stakeholders, j, "average_number"))){
										Stakeholder stakeholder = new Stakeholder(element.getAttribute("name"),element.getAttribute("xmi:id"),getInt(ranges, k,"min"),getInt(ranges, k,"max")); 
										project.getStakeholders().add(stakeholder);
										stakeholder.setIdProfile(getString(stakeholders, j, "xmi:id"));
									}
					break;
					case "uml:Package":
						//Hardware sets
						for(int j=0; j<hardwareSets.getLength();j++)
							if(getString(hardwareSets, j, "base_Package").equals(element.getAttribute("xmi:id"))){
								HardwareSet temp = new HardwareSet(element.getAttribute("xmi:id"),element.getAttribute("name"));
								temp.setIdProfile(getString(hardwareSets, j, "xmi:id"));
								project.getHardwareSets().add(temp);
							}
						//CPU Alternatives and CPUs
						for(int j=0; j<cpuAlternatives.getLength();j++)
							if(getString(cpuAlternatives,j,"base_Package").equals(element.getAttribute("xmi:id")))
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
														cpu.setIdProfile(getString(cpus, l, "xmi:id"));
														for(int m=0; m<frequencies.getLength();m++){
															if(hwComponentProfile.getAttribute("frequencies_voltages").contains(((Element)frequencies.item(m)).getAttribute("xmi:id")))
																cpu.getFrequenciesVoltages().add(new FrequencyVoltage(getInt(frequencies,m,"frequency"), getDouble(frequencies,m,"voltage"), getInt(frequencies, m, "energy_points"),getDouble(frequencies, m,"performance_score")));}
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
																other.getOtherConsumption().add(new OtherConsumption(getString(consumptions,m,"source"), getDouble(consumptions,m,"consumption")));
														hwAlternative.getHardwareComponents().add(other);
													}
												}
											}
										hwSet.getOtherAlternatives().add(hwAlternative);
									}
							}
					break;
					//Components
					case "uml:Component":
						for(int j=0; j<components.getLength();j++)
							if(getString(components, j, "base_Component").equals(element.getAttribute("xmi:id"))){
								Component component=new Component(element.getAttribute("xmi:id"), element.getAttribute("name"));
								for(int k=0; k<atomicOperations.getLength();k++)
									if(getString(atomicOperations,k,"xmi:id").equals(getString(components,j,"bytecode")))
										component.getAtomicOperations().add(new AtomicOperation(getString(atomicOperations, k, "name"),getDouble(atomicOperations, k, "cost"),getInt(atomicOperations, k,"number")));
								for(int k=0; k<cpuUsages.getLength();k++)
									if(getString(cpuUsages,k,"xmi:id").equals(getString(components,j,"cpu_usage"))){
										UsageCPU usageCPU = new UsageCPU();
										usageCPU.setCycles(getInt(cpuUsages,k,"cycles"));
										usageCPU.setEnergyPoints(getInt(cpuUsages, k, "energy_points"));
										usageCPU.setEstimatedMilliseconds(getTime(getString(cpuUsages, k, "time"),times));
										usageCPU.setExactTimes(getExactTimes(getString(cpuUsages, k, "exact_time"),times,cpuTimes));
										usageCPU.setUtilization(getDouble(cpuUsages, k, "utilization"));
										component.setUsageCPU(usageCPU);
									}
								for(int k=0; k<hddUsages.getLength();k++)
									if(getString(hddUsages,k,"xmi:id").equals(getString(components,j,"hdd_usage"))){
										UsageHDD usageHDD = new UsageHDD();
										usageHDD.setEnergyPoints(getInt(hddUsages, k, "energy_points"));
										usageHDD.setEstimatedMilliseconds(getTime(getString(hddUsages, k, "time"),times));
										usageHDD.setSize(getSize(getString(hddUsages, k, "size"),sizes));
										usageHDD.setUtilization(getDouble(hddUsages, k, "utilization"));
										component.setUsageHDD(usageHDD);
									}
								for(int k=0; k<memoryUsages.getLength();k++)
									if(getString(memoryUsages,k,"xmi:id").equals(getString(components,j,"memory_usage"))){
										UsageMemory usageMemory = new UsageMemory();
										usageMemory.setEnergyPoints(getInt(memoryUsages, k, "energy_points"));
										usageMemory.setEstimatedMilliseconds(getTime(getString(memoryUsages, k, "time"),times));
										usageMemory.setSize(getSize(getString(memoryUsages, k, "size"),sizes));
										usageMemory.setUtilization(getDouble(memoryUsages, k, "utilization"));
										component.setUsageMemory(usageMemory);
									}
								project.getComponents().add(component);
								component.setIdProfile(getString(components, j, "xmi:id"));
								component.setHardwareSetId(getString(components, j, "hardware_set"));
							}
					break;
					//Interfaces
					case "uml:Interface":
						project.getInterfaces().add(new Interface(element.getAttribute("xmi:id"),element.getAttribute("name")));
					break;	
				}
			}
		}
		
		//Components are bound to the hardware sets they can deployed on
		for(Component component : project.getComponents()){
			for(HardwareSet hws : project.getHardwareSets())
				if (component.getHardwareSetId().contains(hws.getIdProfile()))
						component.getHardwareSets().add(hws);
		}
		
		//Second step: Association_Enhanced, Connector, Sequences
		if(UMLelements != null && UMLelements.getLength() > 0) {
			for(int i = 0 ; i < UMLelements.getLength();i++) {
				Element element = (Element) UMLelements.item(i);
				switch(element.getAttribute("xmi:type")){
					//Associations
					case "uml:Association":
						for(int j=0; j<association.getLength();j++)
							if(getString(association, j, "base_Association").equals(element.getAttribute("xmi:id"))){
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
					//Connectors
					case "uml:Usage":
						for(int j=0; j<connectors.getLength();j++)
							if(getString(connectors, j, "base_Usage").equals(element.getAttribute("xmi:id"))){
								Connector connector = new Connector(element.getAttribute("xmi:id"),element.getAttribute("name"),element.getAttribute("client"),element.getAttribute("supplier"),getInt(connectors,j,"energy_points"),false);
								connector.setSize(getSize(getString(connectors, j, "size"),sizes));
								connector.bind(project.getInterfaces(),project.getComponents());
								project.getConnectors().add(connector);
								connector.setIdProfile(getString(connectors, j, "xmi:id"));
							}
					break;
					case "uml:InterfaceRealization":
						for(int j=0; j<connectors.getLength();j++)
							if(getString(connectors, j, "base_InterfaceRealization").equals(element.getAttribute("xmi:id"))){
								Connector connector = new Connector(element.getAttribute("xmi:id"),element.getAttribute("name"),element.getAttribute("client"),element.getAttribute("supplier"),getInt(connectors,j,"energy_points"),true);
								connector.setSize(getSize(getString(connectors, j, "size"),sizes));
								connector.bind(project.getInterfaces(),project.getComponents());
								project.getConnectors().add(connector);
								connector.setIdProfile(getString(connectors, j, "xmi:id"));
							}
					break;
					//Sequence alternatives
					case "uml:Interaction":
						String usecaseId=element.getAttribute("useCase");
						NodeList interactionElements = element.getChildNodes();
						List<Element> interactionLifelines = new LinkedList<>();
						List<Element> interactionMessages = new LinkedList<>();
						List<Message> messagesList = new LinkedList<>();
						//List<Element> commonSequence= new LinkedList<>();
						List<List<Element>> sequenceAlternatives = new LinkedList<List<Element>>();
						
						for(int j=0; j<element.getElementsByTagName("lifeline").getLength();j++)
							interactionLifelines.add((Element)element.getElementsByTagName("lifeline").item(j));
						
						for(int j=0; j<element.getElementsByTagName("message").getLength();j++)
							interactionMessages.add((Element)element.getElementsByTagName("message").item(j));
						
						//commonSequence=getCommons(interactionElements);
						sequenceAlternatives=getSequenceAlternatives(interactionElements);
						
						for(List<Element> seq:sequenceAlternatives){
							//seq.addAll(commonSequence);
							for(FunctionalRequirement fr:project.getFunctionalRequirements())
								if(fr.getId().equals(usecaseId)){
									SequenceAlternative sequenceAlternative=new SequenceAlternative();
									for(Element seqElement:seq){
										if(seqElement.getAttribute("xmi:type").equals("uml:MessageOccurrenceSpecification"))
											for(Element messageElement:interactionMessages)
												if (messageElement.getAttribute("xmi:id").equals(seqElement.getAttribute("message")))
													//qua abbiamo seqElement che è un <fragment che punta a messageElement che è il <message
													//se seqElement identifica un sendmessage creo il messaggio e lo metto da parte
													if(messageElement.getAttribute("sendEvent").equals(seqElement.getAttribute("xmi:id"))){
														Message message=new Message(seqElement.getAttribute("message"),messageElement.getAttribute("name"),messageElement.getAttribute("sendEvent"),messageElement.getAttribute("receiveEvent"),messageElement.getAttribute("signature"));
														message.setSenderId(seqElement.getAttribute("covered"));
														messagesList.add(message);
													}
													//altrimente seqElement identifica un receivedmessage quindi cerco il sendmessage relativo, lo completo e lo aggiungo ai messaggi della seqAlternative
													else{
														for(Message mes:messagesList)
															if(mes.getReceiveEvent().equals(seqElement.getAttribute("xmi:id"))){
																//trovato! adesso completo
																mes.setReceiverId(seqElement.getAttribute("covered"));
																for(Element lifeline:interactionLifelines){
																	if(lifeline.getAttribute("xmi:id").equals(mes.getSenderId()))
																		mes.setSender(project.getLifelineElement(getLifelineElement(mes.getSenderId(),lifelines)));
																	if(lifeline.getAttribute("xmi:id").equals(mes.getReceiverId()))
																		mes.setReceiver(project.getLifelineElement(getLifelineElement(mes.getReceiverId(),lifelines)));
																}
																for(Interface in : project.getInterfaces())
																	if (mes.getSignatureId().equals(in.getId()))
																		mes.setSignature(in);
																
																sequenceAlternative.getMessages().add(mes);
															}
													}
									}
									fr.getSequenceAlternatives().add(sequenceAlternative);
									sequenceAlternative.initializeComponents();
								}
						}
					break;
				}
			}
		}
		project.finalizeFunctionalRequirements();
	}

	private Size getSize(String sizeId, NodeList sizes) {
		Size size= new Size(0, 0, 0, 0, 0);
		for(int i=0; i<sizes.getLength();i++)
			if(getString(sizes,i,"xmi:id").equals(sizeId)){
				size.addB(getInt(sizes, i, "B"));
				size.addKB(getInt(sizes, i, "KB"));
				size.addMB(getInt(sizes, i, "MB"));
				size.addGB(getInt(sizes, i, "GB"));
				size.addTB(getInt(sizes, i, "TB"));
			}
		return size;
	}

	private List<ExactTime> getExactTimes(String exactTimeIds, NodeList times, NodeList cpuTimes) {
		List<ExactTime> exactTimes = new LinkedList<>();
		for(int i=0; i<cpuTimes.getLength();i++)
			if(exactTimeIds.contains(getString(cpuTimes,i,"xmi:id"))){
				ExactTime exactTime = new ExactTime();
				exactTime.setMilliseconds(getTime(getString(cpuTimes, i, "time"), times));
				for(HardwareSet hs:project.getHardwareSets())
					for(HardwareAlternative ha:hs.getCpuAlternatives())
						for(HardwareComponent cpu: ha.getHardwareComponents())
							if(((Cpu)cpu).getIdProfile().equals(getString(cpuTimes,i,"CPU")))
								exactTime.setCpu((Cpu)cpu);
				exactTimes.add(exactTime);
			}
		return exactTimes;
	}

	private int getTime(String timeId, NodeList times) {
		for(int i=0; i<times.getLength();i++)
			if(getString(times,i,"xmi:id").equals(timeId))
				return (((getInt(times,i,"hours")*60+getInt(times,i,"minutes"))*60)+getInt(times,i,"seconds"))*1000+getInt(times,i,"milliseconds");
		return 0;
	}

	private List<Element> getCommons(NodeList nodelist){
		List<Element> refined = new LinkedList<>();
		for(int j=0; j<nodelist.getLength();j++){
			try{
				Element element = (Element)nodelist.item(j);
				if (element.getAttribute("xmi:type").equals("uml:MessageOccurrenceSpecification"))
						refined.add(element);
			}catch(Exception e){}
		}
		return refined;
	}
	
	private List<List<Element>> getSequenceAlternatives(NodeList nodelist){
		List<List<Element>> sequenceAlternatives = new LinkedList<>();
		for(int j=0; j<nodelist.getLength();j++){
			try{
				Element element = (Element)nodelist.item(j);
				if (element.getAttribute("xmi:type").equals("uml:CombinedFragment")){
					NodeList optionsRaw = element.getChildNodes();
					for(int i=0; i<optionsRaw.getLength();i++){
						try{
							Element option = (Element)optionsRaw.item(i);
							if (option.getTagName().equals("operand")){
								sequenceAlternatives.addAll(getSequenceAlternatives(option.getChildNodes()));
							}
						} catch(Exception e){}
					}
				}
			}catch(Exception e){}
		}
		if(sequenceAlternatives.isEmpty())
			sequenceAlternatives.add(new LinkedList<Element>());
		for(List<Element> seq:sequenceAlternatives)
			seq.addAll(getCommons(nodelist));
		
		return sequenceAlternatives;
	}

	private String getLifelineElement(String id,NodeList nodelist){
		for(int j=0; j<nodelist.getLength();j++){
			Element element = (Element)nodelist.item(j);
			if(element.getAttribute("base_Lifeline").equals(id))
				return element.getAttribute("element");
		}
		return "";
	}
	
	private String getString(NodeList nodelist, int position, String attribute) {
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