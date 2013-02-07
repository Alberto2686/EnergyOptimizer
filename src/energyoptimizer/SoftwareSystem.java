package energyoptimizer;

public class SoftwareSystem {
	private HardwareSystem hardwareSystem;
	private DeploymentAlternative deploymentAlternative;
	private double consumptionWatt[];
	private double consumptionEnergyPoints[];
	private SequenceAlternative bestSequenceAlternativesWatt[];
	private SequenceAlternative bestSequenceAlternativesEnergyPoint[];
	private double systemConsumptionEP=0;
	private double systemConsumptionW=0;
	
	public SoftwareSystem(HardwareSystem hardwareSystem, DeploymentAlternative deploymentAlternative, int numberOfFunctionalRequirements) {
		this.hardwareSystem = hardwareSystem;
		this.deploymentAlternative = deploymentAlternative;
		consumptionWatt=new double[numberOfFunctionalRequirements];
		consumptionEnergyPoints=new double[numberOfFunctionalRequirements];
		bestSequenceAlternativesWatt=new SequenceAlternative[numberOfFunctionalRequirements];
		bestSequenceAlternativesEnergyPoint=new SequenceAlternative[numberOfFunctionalRequirements];
		for(int i=0; i<numberOfFunctionalRequirements;i++){
			consumptionWatt[i]=Double.MAX_VALUE;
			consumptionEnergyPoints[i]=Double.MAX_VALUE;
		}
	}
	
	public HardwareSystem getHardwareSystem() {
		return hardwareSystem;
	}

	public void setHardwareSystem(HardwareSystem hardwareSystem) {
		this.hardwareSystem = hardwareSystem;
	}

	public DeploymentAlternative getDeploymentAlternative() {
		return deploymentAlternative;
	}

	public void setDeploymentAlternative(DeploymentAlternative deploymentAlternative) {
		this.deploymentAlternative = deploymentAlternative;
	}
	

	public double[] getConsumptionWatt() {
		return consumptionWatt;
	}

	public void setConsumptionWatt(double[] consumptionWatt) {
		this.consumptionWatt = consumptionWatt;
	}

	public double[] getConsumptionEnergyPoints() {
		return consumptionEnergyPoints;
	}

	public void setConsumptionEnergyPoints(double[] consumptionEnergyPoints) {
		this.consumptionEnergyPoints = consumptionEnergyPoints;
	}

	public SequenceAlternative[] getBestSequenceAlternativesWatt() {
		return bestSequenceAlternativesWatt;
	}

	public void setBestSequenceAlternativesWatt(
			SequenceAlternative[] bestSequenceAlternativesWatt) {
		this.bestSequenceAlternativesWatt = bestSequenceAlternativesWatt;
	}

	public SequenceAlternative[] getBestSequenceAlternativesEnergyPoint() {
		return bestSequenceAlternativesEnergyPoint;
	}

	public void setBestSequenceAlternativesEnergyPoint(
			SequenceAlternative[] bestSequenceAlternativesEnergyPoint) {
		this.bestSequenceAlternativesEnergyPoint = bestSequenceAlternativesEnergyPoint;
	}

	public double getSystemConsumptionEP() {
		return systemConsumptionEP;
	}

	public double getSystemConsumptionW() {
		return systemConsumptionW;
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

	public void calculateTotals() {//TODO:rimuovere controllo maxvalue quando ci saranno tutti i FR definiti
		for(double ep:consumptionEnergyPoints)
			if(ep!=Double.MAX_VALUE)
				systemConsumptionEP+=ep;
		for(double w:consumptionWatt)
			if(w!=Double.MAX_VALUE)
				systemConsumptionW+=w;
	}
}