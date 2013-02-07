package energyoptimizer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Project {
	private String name;
	private int defaultCpuScore;
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
	
	public int getDefaultCpuScore() {
		return defaultCpuScore;
	}
	public void setDefaultCpuScore(int defaultCpuScore) {
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
		generateDeploymentAlternatives();
		generateHardwareSystemAlternatives();
		generateSystems();
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
			if(!checkCoverage(system))//TODO: only for test: remove exclamation mark when done
				validSystems.add(system);
	}
	
	public void calculateEnergyConsumption() {
		refineSystems();
		int i=1;
		for(SoftwareSystem system : validSystems){
			System.out.println((i++)+") Evaluating "+system);
			int j=0;
			for(FunctionalRequirement functionalRequirement:functionalRequirements){
				System.out.println("\tEvaluating FR["+j+"] = "+functionalRequirement.getName());
				for(SequenceAlternative sequenceAlternative : functionalRequirement.getSequenceAlternatives()){
					if(checkFeasible(system,sequenceAlternative)){
						System.out.println("\t\tEvaluating "+sequenceAlternative.getMessages());
						double[] consumption = calculateEnergyConsumption(system,functionalRequirement,sequenceAlternative);
						System.out.println("\t\tConsumption = "+consumption[0]+"EP "+consumption[1]+"W");
						if(consumption[0]<system.getConsumptionEnergyPoints()[j]){
							system.getConsumptionEnergyPoints()[j]=consumption[0];
							system.getBestSequenceAlternativesEnergyPoint()[j]=sequenceAlternative;
						}
						if(consumption[1]<system.getConsumptionWatt()[j]){
							system.getConsumptionWatt()[j]=consumption[1];
							system.getBestSequenceAlternativesWatt()[j]=sequenceAlternative;
						}
					}
				}
				j++;
			}
		}
	}

	private double[] calculateEnergyConsumption(SoftwareSystem system, FunctionalRequirement functionalRequirement, SequenceAlternative sequenceAlternative) {
		double consumption[]={0,0};
		for(Message message:sequenceAlternative.getMessages()){
			double[] componentConsumption=calculateComponentConsumption(message.getReceiver());
			consumption[0]+=componentConsumption[0];
			consumption[1]+=componentConsumption[1];
			if(usesNetwork(message,system)){
				double[] networkConsumption=calculateNetworkConsumption(message);
				consumption[0]+=networkConsumption[0];
				consumption[1]+=networkConsumption[1];
			}
		}
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
				//TODO: cancellare quando finito
					//for(HardwareSetAlternative hardwareSetAlternative:system.getHardwareSystem().getHardwareSetAlternatives())
						//if(hardwareSetAlternative.getHardwareSet().equals(deployedComponent.getHardwareSet()))
							//senderHS=hardwareSetAlternative.getHardwareSet();
				if(deployedComponent.getComponent().equals(receiver))
					receiverHS=deployedComponent.getHardwareSet();
			}
			return (senderHS!=null&&receiverHS!=null&&!senderHS.equals(receiverHS));
		}catch(Exception e){}
		return false;
	}
	//private HardwareSetAlternative retreiveHardwareSetAlternativ
	private double[] calculateComponentConsumption(LifelineElement receiver) {
		double componentConsumption[]={0,0};
		try{
			Component component = (Component) receiver;
			double componentConsumptionCPU[] = calculateCPUConsumption(component);
			double componentConsumptionHDD[] = calculateHDDConsumption(component);
			double componentConsumptionMemory[] = calculateMemoryConsumption(component);
			componentConsumption[0]=componentConsumptionCPU[0]+componentConsumptionHDD[0]+componentConsumptionMemory[0];
			componentConsumption[1]=componentConsumptionCPU[1]+componentConsumptionHDD[1]+componentConsumptionMemory[1];
		}catch(Exception e){}
		return componentConsumption;
	}
	private double[] calculateNetworkConsumption(Message message) {
		double consumption[]={0,0};
		for(Connector connector:connectors){
			if(connector.getComponent().equals(message.getSender())&&connector.getToInterface().equals(message.getSignature()))
				consumption[0]=connector.getEnergyPoints();
		}
		// TODO Auto-generated method stub
		return consumption;
	}
	private double[] calculateCPUConsumption(Component component) {
		// TODO Auto-generated method stub
		double consumption[]={0,0};
		consumption[0]=component.getUsageCPU().getEnergyPoints();
		return consumption; 
	}
	private double[] calculateHDDConsumption(Component component) {
		double consumption[]={0,0};
		consumption[0]=component.getUsageHDD().getEnergyPoints();
		// TODO Auto-generated method stub
		return consumption;
	}
	private double[] calculateMemoryConsumption(Component component) {
		double consumption[]={0,0};
		consumption[0]=component.getUsageMemory().getEnergyPoints();
		// TODO Auto-generated method stub
		return consumption;
	}
	private boolean checkFeasible(SoftwareSystem system, SequenceAlternative sequenceAlternative) {
		for(DeploymentAlternative deploymentAlternative: sequenceAlternative.getDeploymentAlternatives())
			if(system.getDeploymentAlternative().equals(deploymentAlternative))
				return true;
		return false;
	}
	
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
		for(SoftwareSystem softwareSystem:systems)
			softwareSystem.calculateTotals();
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
		System.out.println("\n\n\nBEST SYSTEMS:\nEVALUATING ENERGY POINTS:\n"+bestSoftwareSystemEP+"\n"+bestSoftwareSystemEP.getSystemConsumptionEP()+"EP\nEVALUATING WATT CONSUMPTION:\n"+bestSoftwareSystemW+"\n"+bestSoftwareSystemW.getSystemConsumptionW()+"W");
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