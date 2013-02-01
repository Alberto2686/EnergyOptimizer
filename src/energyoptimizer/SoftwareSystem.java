package energyoptimizer;

public class SoftwareSystem {
	private HardwareSystem hardwareSystem;
	private DeploymentAlternative deploymentAlternative;
	
	public SoftwareSystem(HardwareSystem hardwareSystem, DeploymentAlternative deploymentAlternative) {
		this.hardwareSystem = hardwareSystem;
		this.deploymentAlternative = deploymentAlternative;
	}
	
	@Override
	public String toString(){
		String string="System covering: ";
		for(FunctionalRequirement functionalRequirement : deploymentAlternative.getFunctionalRequirementsCovered())
			string+=functionalRequirement.getName()+", ";
		string=string.substring(0,string.length()-2)+"\n\t\t";
		for(HardwareSetAlternative hardwareSetAlternative : hardwareSystem.getHardwareSetAlternatives()){
			string+=hardwareSetAlternative+" hosts ";
			for(DeployedComponent deployedComponent : deploymentAlternative.getDeployedComponents())
				if(deployedComponent.getHardwareSet().equals(hardwareSetAlternative.getHardwareSet()))
					string+=deployedComponent.getComponent().getName()+", ";
			string=string.substring(0,string.length()-2)+"\n\t\t";
		}
		return string;
	}
}