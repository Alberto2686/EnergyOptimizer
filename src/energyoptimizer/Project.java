package energyoptimizer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Project {
	private String name;
	private List<FunctionalRequirement> functionalRequirements = new ArrayList<>();
	private List<Stakeholder> stakeholders = new ArrayList<>();
	private List<AssociationEnhanced> associations = new ArrayList<>();
	private List<HardwareSet> hardwareSets = new ArrayList<>();
	private List<Component> components = new ArrayList<>();
	private List<Connector> connectors = new ArrayList<>();
	private List<Interface> interfaces = new ArrayList<>();
	private List<DeploymentAlternative> deploymentAlternatives = new ArrayList<>();
	
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
	
	@Override
	public String toString(){
		String status="Project "+name+" status:\n";
		int i=1;
		
		status+="\n\tFunctional requirements:\n";
		for(FunctionalRequirement fr:functionalRequirements)
			status+="\t\t"+fr.toString()+"\n";
		status+="\n\tStakeholders:\n";
		for(Stakeholder sh:stakeholders)
			status+="\t\t"+sh.toString()+"\n";
		status+="\n\tAssociations enhanced:\n";
		for(AssociationEnhanced ae:associations)
			status+="\t\t"+ae.toString()+"\n";
		status+="\n\tHardware sets:\n";
		for(HardwareSet hws:hardwareSets)
			status+="\t\t"+hws.toString()+"\n";
		status+="\n\tComponents:\n";
		for(Component c:components)
			status+="\t\t"+c.toString()+"\n";
		status+="\n\tConnectors:\n";
		for(Connector c:connectors)
			status+="\t\t"+c.toString()+"\n";
		status+="\n\tSequence alternatives:\n";
		for(FunctionalRequirement fr:functionalRequirements)
			for(SequenceAlternative sa:fr.getSequenceAlternatives())
				status+="\t\t"+sa.toString()+"\n";
		status+="\n\tDeployment alternatives:\n";
		for(DeploymentAlternative da:deploymentAlternatives)
			status+="\t\t"+(i++)+" "+da.toString()+"\n";
		status+="\n\tHardware set alternatives:\n";
		i=1;
		for(HardwareSet hws:hardwareSets){
			status+="\t\t"+hws.getName()+":\n";
			for(List<HardwareAlternative> hwsa: hws.getHardwareSetAlternatives()){
				status+="\t\t"+(i++)+": ";
				for(HardwareAlternative ha:hwsa)
					status+=ha.getName()+" - ";
				status=status.substring(0,status.length()-3);
				status+="\n";
			}
		}
		return status;
	}
}