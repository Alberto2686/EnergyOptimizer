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
	public void setId(String id) {
		this.id = id;
	}
	public void generateId(){
		//TODO:generate idS
	}

	@Override
	public String toString(){
		String string="";
		for(FunctionalRequirement fr:functionalRequirementsCovered)
			string+=fr.getName()+" ";
		return deployedComponents+" covers:"+string;
	}
}