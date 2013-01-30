package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public abstract class SoftwareComponent extends ModelElement{
	private int functionPoints;
	private List<HardwareSet> deploymentPossibilities=new LinkedList<>();
	
	public SoftwareComponent(String id, String name,int functionPoints) {
		setId(id);
		setName(name);
		this.setFunctionPoints(functionPoints);
	}
	public List<HardwareSet> getDeploymentPossibilities() {
		return deploymentPossibilities;
	}
	public void setDeploymentPossibilities(List<HardwareSet> deploymentPossibilities) {
		this.deploymentPossibilities = deploymentPossibilities;
	}
	public int getFunctionPoints() {
		return functionPoints;
	}
	public void setFunctionPoints(int functionPoints) {
		this.functionPoints = functionPoints;
	}
}