package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class DeploymentAlternative {
	private List<DeployedComponent> deployedComponents = new LinkedList<>();
	private List<FunctionalRequirement> functionalRequirementsCovered = new LinkedList<>();
	private String id;
	
	public List<DeployedComponent> getDeployedComponents() {
		return deployedComponents;
	}
	public void setDeployedComponents(List<DeployedComponent> deployedComponents) {
		this.deployedComponents = deployedComponents;
	}
	public List<FunctionalRequirement> getFunctionalRequirementsCovered() {
		return functionalRequirementsCovered;
	}
	public void setFunctionalRequirementsCovered(
			List<FunctionalRequirement> functionalRequirementsCovered) {
		this.functionalRequirementsCovered = functionalRequirementsCovered;
	}
	public String getId() {
		return id;
	}
	public void initializeId(){
		String hashSequence="";
		for(DeployedComponent dc:deployedComponents)
			hashSequence+=dc.getId();
		id=Utils.getHash(hashSequence);
	}
	
	public void addFunctionalRequirementsCoveredIfNotPresent(FunctionalRequirement functionalRequirement) {
		for(FunctionalRequirement fr:functionalRequirementsCovered)
			if(functionalRequirement.getId().equals(fr.getId()))
				return;
		functionalRequirementsCovered.add(functionalRequirement);
	}

	@Override
	public String toString(){
		String string="";
		for(FunctionalRequirement fr:functionalRequirementsCovered)
			string+=fr.getName()+" ";
		return deployedComponents+" covers:"+string;
	}
}