package energyoptimizer;

import java.util.ArrayList;
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
	
	public Project(String name){
		this.name=name;
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
	
	@Override
	public String toString(){
		String status="Project "+name+" status:\n";
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
		for(HardwareSet hs:hardwareSets)
			status+="\t\t"+hs.toString()+"\n";
		status+="\n\tComponents:\n";
		for(Component c:components)
			status+="\t\t"+c.toString()+"\n";
		status+="\n\tConnectors:\n";
		for(Connector c:connectors)
			status+="\t\t"+c.toString()+"\n";
		status+="\n\tDeployment alternatives:\n";
		for(DeploymentAlternative da:deploymentAlternatives)
			status+="\t\t"+da.toString()+"\n";
		return status;
	}
}