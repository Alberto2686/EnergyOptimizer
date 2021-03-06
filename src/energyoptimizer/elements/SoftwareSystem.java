package energyOptimizer.elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SoftwareSystem {
	private HardwareSystem hardwareSystem;
	private List<HardwareSet> actuallyUsedHardwareSets = new LinkedList<>();
	private DeploymentAlternative deploymentAlternative,
			deploymentEP = new DeploymentAlternative(),
			deploymentW = new DeploymentAlternative();
	private double consumptionWatt[];
	private double consumptionEnergyPoints[];
	private List<SequenceAlternative> bestSequenceAlternativesWatt;
	private List<SequenceAlternative> bestSequenceAlternativesEnergyPoint;
	private double systemConsumptionEP = 0;
	private double systemConsumptionW = 0;
	private boolean valid = false; 

	public SoftwareSystem(HardwareSystem hardwareSystem, DeploymentAlternative deploymentAlternative, int numberOfFunctionalRequirements) {
		this.hardwareSystem = hardwareSystem;
		this.deploymentAlternative = deploymentAlternative;
		consumptionWatt = new double[numberOfFunctionalRequirements];
		consumptionEnergyPoints = new double[numberOfFunctionalRequirements];
		bestSequenceAlternativesWatt = new ArrayList<>(numberOfFunctionalRequirements);
		bestSequenceAlternativesEnergyPoint = new ArrayList<>(numberOfFunctionalRequirements);
		for (int i = 0; i < numberOfFunctionalRequirements; i++) {
			bestSequenceAlternativesEnergyPoint.add(i, new SequenceAlternative());
			bestSequenceAlternativesWatt.add(i, new SequenceAlternative());
			consumptionWatt[i] = -1;
			consumptionEnergyPoints[i] = -1;
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

	public void setBestSequenceAlternativesWatt(List<SequenceAlternative> bestSequenceAlternativesWatt) {
		this.bestSequenceAlternativesWatt = bestSequenceAlternativesWatt;
	}

	public List<SequenceAlternative> getBestSequenceAlternativesEnergyPoint() {
		return bestSequenceAlternativesEnergyPoint;
	}

	public void setBestSequenceAlternativesEnergyPoint(List<SequenceAlternative> bestSequenceAlternativesEnergyPoint) {
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

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public List<HardwareSet> getActuallyUsedHardwareSets() {
		return actuallyUsedHardwareSets;
	}

	public void setActuallyUsedHardwareSets(List<HardwareSet> actuallyUsedHardwareSets) {
		this.actuallyUsedHardwareSets = actuallyUsedHardwareSets;
	}

	public void calculateTotals() {
		calculateTotalsEP();
		calculateTotalsW();
	}

	public void calculateTotalsEP() {
		for (double ep : consumptionEnergyPoints)
			systemConsumptionEP += ep;
	}

	public void calculateTotalsW() {
		for (double w : consumptionWatt)
			systemConsumptionW += w;
		systemConsumptionW=Math.round(systemConsumptionW*100)/100000.0;
	}

	public void refine() {
		List<Component> usedComponentsEP = getNecessaryComponents(bestSequenceAlternativesEnergyPoint);
		List<Component> usedComponentsW = getNecessaryComponents(bestSequenceAlternativesWatt);
		for (Component component : usedComponentsEP) {
			for (DeployedComponent deployedComponent : deploymentAlternative.getDeployedComponents())
				if (deployedComponent.getComponent().equals(component))
					deploymentEP.getDeployedComponents().add(deployedComponent);
		}
		for (Component component : usedComponentsW) {
			for (DeployedComponent deployedComponent : deploymentAlternative.getDeployedComponents())
				if (deployedComponent.getComponent().equals(component))
					deploymentW.getDeployedComponents().add(deployedComponent);
		}
	}

	private List<Component> getNecessaryComponents(List<SequenceAlternative> sequenceAlternatives) {
		List<Component> necessaryComponents = new LinkedList<>();
		for (SequenceAlternative sa : sequenceAlternatives)
			for (Component component : sa.getComponents())
				if (!necessaryComponents.contains(component))
					necessaryComponents.add(component);
		return necessaryComponents;
	}

	public void printAnalysisResults() {
		String string = "";
		for (int i = 0; i < bestSequenceAlternativesEnergyPoint.size(); i++)
			string += i + ": " + bestSequenceAlternativesEnergyPoint.get(i) + "CONSUMPTION:" + consumptionEnergyPoints[i] + "EP\n";
		for (int i = 0; i < bestSequenceAlternativesWatt.size(); i++)
			string += i + ": " + bestSequenceAlternativesWatt.get(i) + "CONSUMPTION:" + consumptionWatt[i] + "KW\n";
		System.out.println(string);
	}

	public void printAnalysisResultsEP() {
		String string = "";
		for (int i = 0; i < bestSequenceAlternativesEnergyPoint.size(); i++)
			string += i + ": " + bestSequenceAlternativesEnergyPoint.get(i) + "CONSUMPTION:" + consumptionEnergyPoints[i] + "EP\n";
		System.out.println(string);
	}

	public String bestEpToString() {
		String string = "";
		for (HardwareSetAlternative hardwareSetAlternative : hardwareSystem.getHardwareSetAlternatives()) {
			string += hardwareSetAlternative + " hosts ";
			for (DeployedComponent deployedComponent : deploymentEP.getDeployedComponents())
				if (deployedComponent.getHardwareSet().equals(hardwareSetAlternative.getHardwareSet()))
					string += deployedComponent.getComponent().getName() + ", ";
			string = string.substring(0, string.length() - 2) + "\n";
		}
		for (int i = 0; i < consumptionEnergyPoints.length; i++) {
			String res = "uncalculable\n";
			if (consumptionEnergyPoints[i] > 0)
				res = consumptionEnergyPoints[i] + "EP\n";
			string += "FR" + (i + 1) + " = " + res;
		}
		return string;
	}

	public String bestWToString() {
		String string = "";
		for (HardwareSetAlternative hardwareSetAlternative : hardwareSystem.getHardwareSetAlternatives()) {
			string += hardwareSetAlternative + " hosts ";
			for (DeployedComponent deployedComponent : deploymentW.getDeployedComponents())
				if (deployedComponent.getHardwareSet().equals(hardwareSetAlternative.getHardwareSet()))
					string += deployedComponent.getComponent().getName() + ", ";
			string = string.substring(0, string.length() - 2) + "\n";
		}
		for (int i = 0; i < consumptionWatt.length; i++) {
			String res = "uncalculable\n";
			if (consumptionWatt[i] > 0)
				res = consumptionWatt[i] + "W\n";
			string += "FR" + (i + 1) + " = " + res;
		}
		return string;
	}

	public void initializeTime(HardwareSet hardwareSet) {
		for(HardwareSetAlternative hardwareSetAlternative : hardwareSystem.getHardwareSetAlternatives())
			if(hardwareSetAlternative.getHardwareSet().equals(hardwareSet)){
				hardwareSetAlternative.initializeTime();
			}
	}

	public void printAnalysisResultsSummary() {
		String string = "";
		for (int i = 0; i < consumptionEnergyPoints.length; i++)
			string += "\t\tFR" + (i + 1) + ": " + consumptionEnergyPoints[i] + "EP\n";
		string += "\n";
		for (int i = 0; i < consumptionWatt.length; i++)
			string += "\t\tFR" + (i + 1) + ": " + consumptionWatt[i] + "W\n";
		string += "\n\t\tTOTAL : " + systemConsumptionEP + "EP and " + systemConsumptionW + "W";
		System.out.println(string);
	}

	public void printAnalysisResultsSummaryW() {
		String string = "";
		for (int i = 0; i < consumptionWatt.length; i++)
			string += "\t\tFR" + (i + 1) + ": " + consumptionWatt[i] + "KW\n";
		string += "\n\t\tTOTAL : " + systemConsumptionW + "W";
		System.out.println(string);
	}

	public void printAnalysisResultsSummaryEP() {
		String string = "";
		for (int i = 0; i < consumptionEnergyPoints.length; i++)
			string += "\t\tFR" + (i + 1) + ": " + consumptionEnergyPoints[i] + "EP\n";
		string += "\n\t\tTOTAL : " + systemConsumptionEP + "EP";
		System.out.println(string);
	}

	public String toString() {
		String string = "";
		for (HardwareSetAlternative hardwareSetAlternative : hardwareSystem.getHardwareSetAlternatives()) {
			string += hardwareSetAlternative + " hosts ";
			for (DeployedComponent deployedComponent : deploymentAlternative.getDeployedComponents())
				if (deployedComponent.getHardwareSet().equals(hardwareSetAlternative.getHardwareSet()))
					string += deployedComponent.getComponent().getName() + ", ";
			string = string.substring(0, string.length() - 2) + "\n\t\t";
		}
		return string;
	}
}