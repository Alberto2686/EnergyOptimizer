package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public abstract class SoftwareComponent extends ModelElement{
	private List<HardwareSet> deploymentPossibilities=new LinkedList<>();
	
	public SoftwareComponent(String id, String name) {
		setId(id);
		setName(name);
	}
	public List<HardwareSet> getDeploymentPossibilities() {
		return deploymentPossibilities;
	}
	public void setDeploymentPossibilities(List<HardwareSet> deploymentPossibilities) {
		this.deploymentPossibilities = deploymentPossibilities;
	}
}