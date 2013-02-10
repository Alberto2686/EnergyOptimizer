package energyoptimizer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SoftwareSystem {
	private HardwareSystem hardwareSystem;
	private DeploymentAlternative deploymentAlternative,deploymentEP=new DeploymentAlternative(),deploymentW=new DeploymentAlternative();
	private double consumptionWatt[];
	private double consumptionEnergyPoints[];
	private List<SequenceAlternative> bestSequenceAlternativesWatt;
	private List<SequenceAlternative> bestSequenceAlternativesEnergyPoint;
	private double systemConsumptionEP=0;
	private double systemConsumptionW=0;
	
	public SoftwareSystem(HardwareSystem hardwareSystem, DeploymentAlternative deploymentAlternative, int numberOfFunctionalRequirements) {
		this.hardwareSystem = hardwareSystem;
		this.deploymentAlternative = deploymentAlternative;
		consumptionWatt=new double[numberOfFunctionalRequirements];
		consumptionEnergyPoints=new double[numberOfFunctionalRequirements];
		bestSequenceAlternativesWatt=new ArrayList<>(numberOfFunctionalRequirements);
		bestSequenceAlternativesEnergyPoint=new ArrayList<>(numberOfFunctionalRequirements);
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

	public List<SequenceAlternative> getBestSequenceAlternativesWatt() {
		return bestSequenceAlternativesWatt;
	}

	public void setBestSequenceAlternativesWatt(
			List<SequenceAlternative> bestSequenceAlternativesWatt) {
		this.bestSequenceAlternativesWatt = bestSequenceAlternativesWatt;
	}

	public List<SequenceAlternative> getBestSequenceAlternativesEnergyPoint() {
		return bestSequenceAlternativesEnergyPoint;
	}

	public void setBestSequenceAlternativesEnergyPoint(
			List<SequenceAlternative> bestSequenceAlternativesEnergyPoint) {
		this.bestSequenceAlternativesEnergyPoint = bestSequenceAlternativesEnergyPoint;
	}

	public void setSystemConsumptionEP(double systemConsumptionEP) {
		this.systemConsumptionEP = systemConsumptionEP;
	}

	public void setSystemConsumptionW(double systemConsumptionW) {
		this.systemConsumptionW = systemConsumptionW;
	}

	public double getSystemConsumptionEP() {
		return systemConsumptionEP;
	}

	public double getSystemConsumptionW() {
		return systemConsumptionW;
	}

	public DeploymentAlternative getDeploymentEP() {
		return deploymentEP;
	}

	public void setDeploymentEP(DeploymentAlternative deploymentEP) {
		this.deploymentEP = deploymentEP;
	}

	public DeploymentAlternative getDeploymentW() {
		return deploymentW;
	}

	public void setDeploymentW(DeploymentAlternative deploymentW) {
		this.deploymentW = deploymentW;
	}

	public void calculateTotals() {
		for(double ep:consumptionEnergyPoints)
			systemConsumptionEP+=ep;
		for(double w:consumptionWatt)
			systemConsumptionW+=w;
	}
	
	public void refine(){
		List<Component> usedComponentsEP=getNecessaryComponents(bestSequenceAlternativesEnergyPoint);
		List<Component> usedComponentsW=getNecessaryComponents(bestSequenceAlternativesWatt);
		for(Component component:usedComponentsEP){
			for(DeployedComponent deployedComponent:deploymentAlternative.getDeployedComponents())
				if(deployedComponent.getComponent().equals(component))
					deploymentEP.getDeployedComponents().add(deployedComponent);
		}
		for(Component component:usedComponentsW){
			for(DeployedComponent deployedComponent:deploymentAlternative.getDeployedComponents())
				if(deployedComponent.getComponent().equals(component))
					deploymentW.getDeployedComponents().add(deployedComponent);
		}	
	}

	private List<Component> getNecessaryComponents(List<SequenceAlternative> sequenceAlternatives) {
		List<Component> necessaryComponents=new LinkedList<>();
		for(SequenceAlternative sa:sequenceAlternatives)
			for(Component component:sa.getComponents())
				if(!necessaryComponents.contains(component))
					necessaryComponents.add(component);
		return necessaryComponents;
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

	public void printAnalysisResults(){
		String string="";
		for(int i=0;i<bestSequenceAlternativesEnergyPoint.size();i++)
			string+=i+": "+bestSequenceAlternativesEnergyPoint.get(i)+"CONSUMPTION:"+consumptionEnergyPoints[i]+"EP\n";
		for(int i=0;i<bestSequenceAlternativesWatt.size();i++)
			string+=i+": "+bestSequenceAlternativesWatt.get(i)+"CONSUMPTION:"+consumptionWatt[i]+"W\n";
		System.out.println(string);
	}

	public String bestEpToString() {
		String string="System covering: ";
		for(FunctionalRequirement functionalRequirement : deploymentAlternative.getFunctionalRequirementsCovered())
			string+=functionalRequirement.getName()+", ";
		string=string.substring(0,string.length()-2)+"\n\t\t";
		for(HardwareSetAlternative hardwareSetAlternative : hardwareSystem.getHardwareSetAlternatives()){
			string+=hardwareSetAlternative+" hosts ";
			for(DeployedComponent deployedComponent : deploymentEP.getDeployedComponents())
				if(deployedComponent.getHardwareSet().equals(hardwareSetAlternative.getHardwareSet()))
					string+=deployedComponent.getComponent().getName()+", ";
			string=string.substring(0,string.length()-2)+"\n\t\t";
		}
		return string;
	}

	public String bestWToString() {
		String string="System covering: ";
		for(FunctionalRequirement functionalRequirement : deploymentAlternative.getFunctionalRequirementsCovered())
			string+=functionalRequirement.getName()+", ";
		string=string.substring(0,string.length()-2)+"\n\t\t";
		for(HardwareSetAlternative hardwareSetAlternative : hardwareSystem.getHardwareSetAlternatives()){
			string+=hardwareSetAlternative+" hosts ";
			for(DeployedComponent deployedComponent : deploymentW.getDeployedComponents())
				if(deployedComponent.getHardwareSet().equals(hardwareSetAlternative.getHardwareSet()))
					string+=deployedComponent.getComponent().getName()+", ";
			string=string.substring(0,string.length()-2)+"\n\t\t";
		}
		return string;
	}
}