package energyOptimizer.elements;

import java.util.LinkedList;
import java.util.List;

import energyOptimizer.Utils;

public class DeploymentAlternative {
	private List<DeployedComponent> deployedComponents = new LinkedList<>();
	private String id;

	public List<DeployedComponent> getDeployedComponents() {
		return deployedComponents;
	}

	public void setDeployedComponents(List<DeployedComponent> deployedComponents) {
		this.deployedComponents = deployedComponents;
	}

	public String getId() {
		return id;
	}

	public void initializeId() {
		String hashSequence = "";
		for (DeployedComponent dc : deployedComponents)
			hashSequence += dc.getId();
		id = Utils.getHash(hashSequence);
	}

	@Override
	public String toString() {
		return deployedComponents.toString();
	}
}