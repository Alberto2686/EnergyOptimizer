package energyoptimizer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Project {
	private String name;
	private double defaultCpuScore;
	private List<FunctionalRequirement> functionalRequirements = new ArrayList<>();
	private List<Stakeholder> stakeholders = new ArrayList<>();
	private List<AssociationEnhanced> associations = new ArrayList<>();
	private List<HardwareSet> hardwareSets = new ArrayList<>();
	private List<Component> components = new ArrayList<>();
	private List<Connector> connectors = new ArrayList<>();
	private List<Interface> interfaces = new ArrayList<>();
	private List<DeploymentAlternative> deploymentAlternatives = new ArrayList<>();
	private List<HardwareSystem> hardwareSystems = new ArrayList<>();
	private List<SoftwareSystem> systems = new ArrayList<>();
	private List<SoftwareSystem> validSystems = new ArrayList<>();
	private boolean isWReliable=true;
	
	public double getDefaultCpuScore() {
		return defaultCpuScore;
	}
	public void setDefaultCpuScore(double defaultCpuScore) {
		this.defaultCpuScore = defaultCpuScore;
	}
	public List<FunctionalRequirement> getFunctionalRequirements() {
		return functionalRequirements;
	}
	public void setFunctionalRequirements(List<FunctionalRequirement> functionalRequirements) {
		this.functionalRequirements = functionalRequirements;
	}
	public List<DeploymentAlternative> getDeploymentAlternatives() {
		return deploymentAlternatives;
	}
	public void setDeploymentAlternatives(List<DeploymentAlternative> deploymentAlternatives) {
		this.deploymentAlternatives = deploymentAlternatives;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HardwareSet> getHardwareSets() {
		return hardwareSets;
	}

	public void setHardwareSets(List<HardwareSet> hardwareSets) {
		this.hardwareSets = hardwareSets;
	}

	public List<Stakeholder> getStakeholders() {
		return stakeholders;
	}
	public void setStakeholders(List<Stakeholder> stakeholders) {
		this.stakeholders = stakeholders;
	}
	public List<AssociationEnhanced> getAssociations() {
		return associations;
	}
	public void setAssociations(List<AssociationEnhanced> associations) {
		this.associations = associations;
	}
	
	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public List<Connector> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<Connector> connectors) {
		this.connectors = connectors;
	}

	public List<Interface> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<Interface> interfaces) {
		this.interfaces = interfaces;
	}

	public boolean isActor(String id){
		boolean isActor = false;
		for(Stakeholder stakeholder:stakeholders)
			if(stakeholder.getId().equals(id))
				isActor=true;
		return isActor;
	}
	
	public LifelineElement getLifelineElement(String id){
		for(Component component:components)
			if(component.getIdProfile().equals(id))
				return component;
		for(Stakeholder stakeholder:stakeholders)
			if(stakeholder.getIdProfile().equals(id))
				return stakeholder;
		return null;
	}
	
	public void generateAlternatives(){
		generateHardwareSetAlternatives();
		generateAllDeploymentAlternatives();
		generateHardwareSystemAlternatives();
		generateSystems();
	}
	
	public void generateAlternativesOld(){
		generateHardwareSetAlternatives();
		generateDeploymentAlternatives();
		generateHardwareSystemAlternatives();
		generateSystems();
	}

	private void generateAllDeploymentAlternatives(){
		List<HardwareSet> sequenceHardwareSets = new LinkedList<>();
		int hardwarePossibilities[] = new int[components.size()];
		int repetitions[] = new int[components.size()];
		int k=0;
		int alternatives = 1;
		for(Component sc: components){
			hardwarePossibilities[k]=sc.getHardwareSets().size();
			alternatives*=sc.getHardwareSets().size();
			repetitions[k++]=alternatives/sc.getHardwareSets().size();
			for(HardwareSet hws:sc.getHardwareSets())
				addHardwareSetIfNotPresent(sequenceHardwareSets,hws);
		}
		for(int i=0;i<alternatives;i++){
			DeploymentAlternative deploymentAlternative = new DeploymentAlternative();
			for(int j=0;j<components.size();j++){
				int index=(i/repetitions[j])%components.get(j).getHardwareSets().size();
				HardwareSet hws = components.get(j).getHardwareSets().get(index);
				DeployedComponent deployedComponent = new DeployedComponent(components.get(j),hws);
				deploymentAlternative.getDeployedComponents().add(deployedComponent);
			}
			deploymentAlternative.initializeId();
			deploymentAlternatives.add(deploymentAlternative);
		}
	}
	
	private void generateHardwareSetAlternatives(){
		for(HardwareSet hws:hardwareSets)
			hws.generateHardwareSetAlternatives();
	}
	
	private void generateDeploymentAlternatives(){
		for(FunctionalRequirement fr : getFunctionalRequirements()){
			for(SequenceAlternative sa : fr.getSequenceAlternatives()){
				List<Component> sequenceComponents = sa.getComponents();
				List<HardwareSet> sequenceHardwareSets = new LinkedList<>();
				int hardwarePossibilities[] = new int[sequenceComponents.size()];
				int repetitions[] = new int[sequenceComponents.size()];
				int i=0;
				int alternatives = 1;
				for(Component sc: sequenceComponents){
					hardwarePossibilities[i]=sc.getHardwareSets().size();
					alternatives*=sc.getHardwareSets().size();
					repetitions[i++]=alternatives/sc.getHardwareSets().size();
					for(HardwareSet hws:sc.getHardwareSets())
						addHardwareSetIfNotPresent(sequenceHardwareSets,hws);
				}
				generateDeploymentAlternatives(fr,sa,sequenceComponents,sequenceHardwareSets,alternatives,hardwarePossibilities,repetitions);
			}
		}
	}
	
	private void generateDeploymentAlternatives(FunctionalRequirement functionalRequirement,SequenceAlternative sequenceAlternative, List<Component>sequenceComponents,List<HardwareSet>sequenceHardwareSets,int alternatives,int[]hardwarePossibilities,int[]repetitions){
		for(int i=0;i<alternatives;i++){
			DeploymentAlternative deploymentAlternative = new DeploymentAlternative();
			for(int j=0;j<sequenceComponents.size();j++){
				int index=(i/repetitions[j])%sequenceComponents.get(j).getHardwareSets().size();
				HardwareSet hws = sequenceComponents.get(j).getHardwareSets().get(index);
				DeployedComponent deployedComponent = new DeployedComponent(sequenceComponents.get(j),hws);
				deploymentAlternative.getDeployedComponents().add(deployedComponent);
			}
			deploymentAlternative.initializeId();
			addDeploymentAlternativeIfNotPresent(deploymentAlternative, sequenceAlternative, functionalRequirement);
		}
	}
	
	private void generateSystems() {
		for(HardwareSystem hardwareSystem : hardwareSystems)
			for(DeploymentAlternative deploymentAlternative : deploymentAlternatives)
				systems.add(new SoftwareSystem(hardwareSystem,deploymentAlternative,functionalRequirements.size()));
	}
	
	private void generateHardwareSystemAlternatives() {
		int alternatives = 1;
		int possibilities[] = new int[hardwareSets.size()];
		int repetitions[] = new int[hardwareSets.size()];
		int i=0;
		for(HardwareSet hws:hardwareSets){
			alternatives*=hws.getHardwareSetAlternatives().size();
			possibilities[i]=hws.getHardwareSetAlternatives().size();
			repetitions[i++]=alternatives/hws.getHardwareSetAlternatives().size();
		}
		generateHardwareSystems(alternatives,possibilities,repetitions);
	}
	
	private void generateHardwareSystems(int alternatives,int[]possibilities,int[]repetitions){
		for(int i=0;i<alternatives;i++){
			HardwareSystem hardwareSystem = new HardwareSystem();
			for(int j=0;j<hardwareSets.size();j++){
				int index=(i/repetitions[j])%hardwareSets.get(j).getHardwareSetAlternatives().size();
				HardwareSetAlternative hwa = hardwareSets.get(j).getHardwareSetAlternatives().get(index);
				hardwareSystem.getHardwareSetAlternatives().add(hwa);
			}
			hardwareSystems.add(hardwareSystem);
		}
	}
	
	public void addHardwareSetIfNotPresent(List<HardwareSet> sequenceHardwareSets, HardwareSet hardwareSet){
		for(HardwareSet hws:sequenceHardwareSets)
			if(hws.getId().equals(hardwareSet.getId()))
				return;
		sequenceHardwareSets.add(hardwareSet);
	}
	
	public void addDeploymentAlternativeIfNotPresent(DeploymentAlternative deploymentAlternative,SequenceAlternative sequenceAlternative,FunctionalRequirement functionalRequirement){
		for(DeploymentAlternative da:deploymentAlternatives)
			if(da.getId().equals(deploymentAlternative.getId())){//deploymentAlternative already present
				da.addFunctionalRequirementsCoveredIfNotPresent(functionalRequirement);
				for(DeploymentAlternative sda:sequenceAlternative.getDeploymentAlternatives())
					if(sda.getId().equals(da.getId()))
						return;
				sequenceAlternative.getDeploymentAlternatives().add(da);
				return;
			}
		deploymentAlternatives.add(deploymentAlternative);
		sequenceAlternative.getDeploymentAlternatives().add(deploymentAlternative);
		deploymentAlternative.getFunctionalRequirementsCovered().add(functionalRequirement);
	}
	
	public void refineSystems(){
		for(SoftwareSystem system:systems)
			if(checkCoverage(system))
				validSystems.add(system);
		for(SoftwareSystem system:validSystems)
			System.out.println("VALID:"+system);
	}
	
	public void calculateEnergyConsumption() {
		int i=1;
		for(SoftwareSystem system : systems){//was:validsystem
			System.out.println((i++)+") Evaluating "+system);
			int j=0;

			for(FunctionalRequirement functionalRequirement:functionalRequirements){
				for(SequenceAlternative sequenceAlternative : functionalRequirement.getSequenceAlternatives()){
					system.getActuallyUsedHardwareSets().clear();
						double[] consumption = calculateEnergyConsumption(system,functionalRequirement,sequenceAlternative);
						double[] staticConsumption = calculatePlatformAndOtherConsuptions(system, functionalRequirement);
						
						if(system.getConsumptionEnergyPoints()[j]==-1||consumption[0]<system.getConsumptionEnergyPoints()[j]){
							system.getConsumptionEnergyPoints()[j]=Math.rint(consumption[0]+staticConsumption[0]);
							system.getBestSequenceAlternativesEnergyPoint().add(sequenceAlternative);
						}
						if(system.getConsumptionWatt()[j]==-1||consumption[1]>0&&consumption[1]<system.getConsumptionWatt()[j]){
							system.getConsumptionWatt()[j]=Math.rint((consumption[1]+staticConsumption[1])/1000.0);
							system.getBestSequenceAlternativesWatt().add(sequenceAlternative);
						}
				}
				j++;
			}
			//system.printAnalysisResults();
		}
	}

	private double[] calculateEnergyConsumption(SoftwareSystem system, FunctionalRequirement functionalRequirement, SequenceAlternative sequenceAlternative) {
		double consumption[]={0,0};
		for(Message message:sequenceAlternative.getMessages()){
			double[] componentConsumption=calculateComponentConsumption(message.getReceiver(), system);
			consumption[0]+=componentConsumption[0];
			if(componentConsumption[1]!=-1)
				consumption[1]+=componentConsumption[1];
			if(usesNetwork(message,system)){
				double[] networkConsumption=calculateNetworkConsumption(message, system);
				consumption[0]+=networkConsumption[0];
				if(networkConsumption[1]!=-1)
					consumption[1]+=networkConsumption[1];
				else
					isWReliable=false;
			}
		}
		consumption[0]*=functionalRequirement.getCoefficient();
		consumption[1]*=functionalRequirement.getCoefficient();
		return consumption;
	}

	private double[] calculatePlatformAndOtherConsuptions(SoftwareSystem system, FunctionalRequirement functionalRequirement) {
		double consumption[]={0,0};
		for(HardwareSetAlternative hardwareSetAlternative:system.getHardwareSystem().getHardwareSetAlternatives())
			if(system.getActuallyUsedHardwareSets().contains(hardwareSetAlternative.getHardwareSet())){
				double consumptionOther[]=Utils.consumptionOther((Other)hardwareSetAlternative.getOtherAlternative().getHardwareComponents().toArray()[0]);
				double consumptionPlatform[]=Utils.consumptionPlatform((Platform)hardwareSetAlternative.getPlatformAlternative().getHardwareComponents().toArray()[0]);
				consumption[0]+=consumptionOther[0]+consumptionPlatform[0];
				consumption[1]+=consumptionOther[1]+consumptionPlatform[1];
			}
		consumption[0]*=functionalRequirement.getCoefficient();
		consumption[1]*=functionalRequirement.getCoefficient();
			
		return consumption;
	}
	
	private boolean usesNetwork(Message message,SoftwareSystem system){
		try{
			Component sender = (Component)message.getSender();
			Component receiver = (Component)message.getReceiver();
			HardwareSet senderHS = null,receiverHS = null;
			for(DeployedComponent deployedComponent:system.getDeploymentAlternative().getDeployedComponents()){
				if(deployedComponent.getComponent().equals(sender))
					senderHS=deployedComponent.getHardwareSet();
				if(deployedComponent.getComponent().equals(receiver))
					receiverHS=deployedComponent.getHardwareSet();
			}
			return (senderHS!=null&&receiverHS!=null&&!senderHS.equals(receiverHS));
		}catch(Exception e){}
		return false;
	}

	//retrieves network devices and updates the used HWset list if needed
	private List<Network> getNetworkDevices(Component component, SoftwareSystem system){
		HardwareSet hardwareSet=null;
		List<Network> networkDevices=new LinkedList<>();
		for(DeployedComponent deployedComponent:system.getDeploymentAlternative().getDeployedComponents())
			if(deployedComponent.getComponent().equals(component))
				hardwareSet=deployedComponent.getHardwareSet();
		if(!system.getActuallyUsedHardwareSets().contains(hardwareSet))
			system.getActuallyUsedHardwareSets().add(hardwareSet);
		for(HardwareSetAlternative hardwareSetAlternative:system.getHardwareSystem().getHardwareSetAlternatives())
			if(hardwareSetAlternative.getHardwareSet().equals(hardwareSet))
				for(HardwareComponent networkDevice:hardwareSetAlternative.getNetworkAlternative().getHardwareComponents())
					networkDevices.add((Network)networkDevice);
		return networkDevices;
	}

	private double[] calculateComponentConsumption(LifelineElement receiver,SoftwareSystem system) {
		double componentConsumption[]={-1,-1};
		try{
			Component component = (Component) receiver;
			HardwareSetAlternative hardwareSetAlternative=null;
			for(DeployedComponent dc:system.getDeploymentAlternative().getDeployedComponents())
				if(dc.getComponent().equals(component))
					for(HardwareSetAlternative hsa:system.getHardwareSystem().getHardwareSetAlternatives())
						if(dc.getHardwareSet().equals(hsa.getHardwareSet()))
							hardwareSetAlternative=hsa;
			double componentConsumptionCPU[] = calculateCPUConsumption(component,hardwareSetAlternative.getCpuAlternative());
			double componentConsumptionHDD[] = calculateHDDConsumption(component,hardwareSetAlternative.getHddAlternative());
			double componentConsumptionMemory[] = calculateMemoryConsumption(component, hardwareSetAlternative.getMemoryAlternative());
			componentConsumption[0]=componentConsumptionCPU[0]+componentConsumptionHDD[0]+componentConsumptionMemory[0];
			if(componentConsumptionCPU[1]==-1||componentConsumptionHDD[1]==-1||componentConsumptionMemory[1]==-1)
				isWReliable=false;
			if(componentConsumptionCPU[1]!=-1)
				componentConsumption[1]+=componentConsumptionCPU[1];
			if(componentConsumptionHDD[1]!=-1)
				componentConsumption[1]+=componentConsumptionHDD[1];
			if(componentConsumptionMemory[1]!=-1)
				componentConsumption[1]+=componentConsumptionMemory[1];
		}catch(Exception e){}
		return componentConsumption;
	}
	
	private double[] calculateNetworkConsumption(Message message,SoftwareSystem system) {
		double consumption[]={0,-1};
		for(Connector connector:connectors)
			if(connector.getComponent().equals(message.getSender())&&connector.getToInterface().equals(message.getSignature())){
				consumption[0]=connector.getEnergyPoints();
				consumption[1]=Utils.consumptionNetwork(connector.getSize().getSize(),getNetworkDevices((Component)message.getSender(),system),getNetworkDevices((Component)message.getReceiver(),system));
			}
		return consumption;
	}
	
	private double[] calculateCPUConsumption(Component component,HardwareAlternative hardwareAlternative) {
		double consumption[]={-1,-1};
		consumption[0]=component.getUsageCPU().getEnergyPoints();
		for(HardwareComponent cpu:hardwareAlternative.getHardwareComponents()){
			Double temp = Utils.consumptionCPU((Cpu)cpu, component.getUsageCPU(), defaultCpuScore,component.getAtomicOperations(),component.getAtomicOperationConsumptions());
			if(temp!=-1&&(temp<consumption[1]||consumption[1]==-1))
				consumption[1]=temp;
		}
		return consumption; 
	}
	private double[] calculateHDDConsumption(Component component, HardwareAlternative hardwareAlternative) {
		double consumption[]={-1,-1};
		consumption[0]=component.getUsageHDD().getEnergyPoints();
		for(HardwareComponent hdd:hardwareAlternative.getHardwareComponents()){
			Double temp = Utils.consumptionHDD((Hdd)hdd, component.getUsageHDD());
			if(temp!=-1&&(temp<consumption[1]||consumption[1]==-1))
				consumption[1]=temp;
		}
		return consumption;
	}
	private double[] calculateMemoryConsumption(Component component, HardwareAlternative hardwareAlternative) {
		double consumption[]={-1,-1};
		consumption[0]=component.getUsageMemory().getEnergyPoints();
		for(HardwareComponent memory:hardwareAlternative.getHardwareComponents()){
			Double temp = Utils.consumptionMemory((Memory)memory, component.getUsageMemory());
			if(temp!=-1&&(temp<consumption[1]||consumption[1]==-1))
				consumption[1]=temp;
		}
		return consumption;
	}
	/*private boolean checkFeasible(SoftwareSystem system, SequenceAlternative sequenceAlternative) {
		for(DeploymentAlternative deploymentAlternative: sequenceAlternative.getDeploymentAlternatives())
			if(system.getDeploymentAlternative().equals(deploymentAlternative))
				return true;
		return false;
	}*/
	
	private boolean checkCoverage(SoftwareSystem system) {
		for(FunctionalRequirement functionalRequirementToCover:functionalRequirements){
			if(!checkCoverage(functionalRequirementToCover,system.getDeploymentAlternative()))
					return false;
		}
		return true;
	}
	
	private boolean checkCoverage(FunctionalRequirement functionalRequirementToCover, DeploymentAlternative deploymentAlternative){
		for(FunctionalRequirement functionalRequirementCovered : deploymentAlternative.getFunctionalRequirementsCovered())
			if(functionalRequirementToCover.equals(functionalRequirementCovered))
				return true;
		return false;
	}
	
	public void findBestSystem() {
		for(SoftwareSystem softwareSystem:systems){
			softwareSystem.refine();
			softwareSystem.calculateTotals();
		}
		SoftwareSystem bestSoftwareSystemEP=systems.get(0);
		SoftwareSystem bestSoftwareSystemW=systems.get(0);
		for(SoftwareSystem softwareSystem:systems){
			if(softwareSystem.getSystemConsumptionEP()<bestSoftwareSystemEP.getSystemConsumptionEP())
				bestSoftwareSystemEP=softwareSystem;
			if(softwareSystem.getSystemConsumptionW()<bestSoftwareSystemW.getSystemConsumptionW())
				bestSoftwareSystemW=softwareSystem;
		}
		visualizeBestSystem(bestSoftwareSystemEP,bestSoftwareSystemW);
	}
	
	private void visualizeBestSystem(SoftwareSystem bestSoftwareSystemEP,SoftwareSystem bestSoftwareSystemW) {
		String reliability=isWReliable?"":"but some consumption parameters were missing";
		System.out.println("\n\n\nBEST SYSTEMS:\nEVALUATING ENERGY POINTS:\n"+bestSoftwareSystemEP.bestEpToString()+"\n"+bestSoftwareSystemEP.getSystemConsumptionEP()+"EP\nEVALUATING WATT CONSUMPTION:\n"+bestSoftwareSystemW.bestWToString()+"\n"+bestSoftwareSystemW.getSystemConsumptionW()+"W "+reliability);
	}
	public void finalizeFunctionalRequirements() {
		for(FunctionalRequirement functionalRequirement:functionalRequirements)
			functionalRequirement.calculateCoefficient();
	}
	
	@Override
	public String toString(){
		String status="Project "+name+" status:\n";
		int i=1;
		
		status+="\n\tFunctional requirements:\n";
		for(FunctionalRequirement fr:functionalRequirements)
			status+="\t\t"+fr+"\n";
		status+="\n\tStakeholders:\n";
		for(Stakeholder sh:stakeholders)
			status+="\t\t"+sh+"\n";
		status+="\n\tAssociations enhanced:\n";
		for(AssociationEnhanced ae:associations)
			status+="\t\t"+ae+"\n";
		status+="\n\tHardware sets:\n";
		for(HardwareSet hws:hardwareSets)
			status+="\t\t"+hws+"\n";
		status+="\n\tComponents:\n";
		for(Component c:components)
			status+="\t\t"+c+"\n";
		status+="\n\tConnectors:\n";
		for(Connector c:connectors)
			status+="\t\t"+c+"\n";
		status+="\n\tSequence alternatives:\n";
		for(FunctionalRequirement fr:functionalRequirements)
			for(SequenceAlternative sa:fr.getSequenceAlternatives())
				status+="\t\t"+sa+"\n";
		status+="\n\tDeployment alternatives:\n";
		for(DeploymentAlternative da:deploymentAlternatives)
			status+="\t\t"+(i++)+" "+da+"\n";
		status+="\n\tHardware set alternatives:\n";
		for(HardwareSet hws:hardwareSets){
			i=1;
			status+="\t\t"+hws.getName()+":\n";
			for(HardwareSetAlternative hsa: hws.getHardwareSetAlternatives()){
				status+="\t\t"+(i++)+": "+hsa;
				status+="\n";
			}
		}
		i=1;
		status+="\n\tHardware systems:\n";
		for(HardwareSystem hs:hardwareSystems)
			status+="\t\t"+(i++)+" "+hs+"\n";
		i=1;
		status+="\n\tSystems:\n";
		for(SoftwareSystem sys:systems)
			status+="\t\t"+(i++)+" "+sys+"\n";
		return status;
	}
}