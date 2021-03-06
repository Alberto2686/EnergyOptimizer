package energyOptimizer;

import java.util.ArrayList;
import java.util.List;

import energyOptimizer.elements.*;

public class Project {
	private String name;
	private double defaultCpuScore;
	private List<FunctionalRequirement> functionalRequirements = new ArrayList<>();
	private List<Stakeholder> stakeholders = new ArrayList<>();
	private List<AssociationEnhanced> associations = new ArrayList<>();
	private List<HardwareSet> hardwareSets = new ArrayList<>();
	private List<Component> components = new ArrayList<>();
	private List<Connector> connectors = new ArrayList<>();
	private List<Interface> interfaces = new ArrayList<>();
	private List<DeploymentAlternative> deploymentAlternatives = new ArrayList<>();
	private List<HardwareSystem> hardwareSystems = new ArrayList<>();
	private List<HardwareSystem> hardwareSystemsCalculated = new ArrayList<>();
	private List<HardwareSystem> hardwareSystemsEP = new ArrayList<>();
	private List<HardwareSystem> hardwareSystemsW = new ArrayList<>();
	private List<SoftwareSystem> systemsEP = new ArrayList<>();
	private List<SoftwareSystem> systemsW = new ArrayList<>();
	private List<SoftwareSystem> systems = new ArrayList<>();
	private boolean isWReliable = true;
	SoftwareSystem bestSoftwareSystemEP;
	SoftwareSystem bestSoftwareSystemW;
	SoftwareSystem worstSoftwareSystemEP;
	SoftwareSystem worstSoftwareSystemW;

	public double getDefaultCpuScore() {
		return defaultCpuScore;
	}

	public void setDefaultCpuScore(double defaultCpuScore) {
		this.defaultCpuScore = defaultCpuScore;
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

	public List<HardwareSystem> getHardwareSystems() {
		return hardwareSystems;
	}

	public void setHardwareSystems(List<HardwareSystem> hardwareSystems) {
		this.hardwareSystems = hardwareSystems;
	}

	public List<HardwareSystem> getHardwareSystemsEP() {
		return hardwareSystemsEP;
	}

	public void setHardwareSystemsEP(List<HardwareSystem> hardwareSystemsEP) {
		this.hardwareSystemsEP = hardwareSystemsEP;
	}

	public List<SoftwareSystem> getSystems() {
		return systems;
	}

	public void setSystems(List<SoftwareSystem> systems) {
		this.systems = systems;
	}

	public List<HardwareSystem> getHardwareSystemsCalculated() {
		return hardwareSystemsCalculated;
	}

	public void setHardwareSystemsCalculated(List<HardwareSystem> hardwareSystemsCalculated) {
		this.hardwareSystemsCalculated = hardwareSystemsCalculated;
	}

	public boolean isActor(String id) {
		boolean isActor = false;
		for (Stakeholder stakeholder : stakeholders)
			if (stakeholder.getId().equals(id))
				isActor = true;
		return isActor;
	}

	public LifelineElement getLifelineElement(String id) {
		for (Component component : components)
			if (component.getIdProfile().equals(id))
				return component;
		for (Stakeholder stakeholder : stakeholders)
			if (stakeholder.getIdProfile().equals(id))
				return stakeholder;
		return null;
	}

	public void generateAlternatives() {
		systems.addAll((new AlternativesGenerator(this)).generateAlternatives());
	}

	public void generateAlternativesGeneration(int generation) {
		newGeneration();
		systems.addAll((new AlternativesGenerator(this)).generateAlternatives(generation));
	}

	private void newGeneration() {
		hardwareSystemsCalculated.addAll(hardwareSystems);
		hardwareSystems.clear();
		systems.clear();
	}

	public void generateAlternativesEP() {
		systemsEP.addAll((new AlternativesGenerator(this)).generateAlternativesEP());
	}

	public void generateAlternativesOld() {
		AlternativesGenerator alternativesGenerator = new AlternativesGenerator(this);
		systems.addAll(alternativesGenerator.generateAlternativesOld());
	}

	public void calculateEnergyConsumption() {
		int i = 1;
		System.out.println("EVALUATING SYSTEMS\n");
		for (SoftwareSystem system : systems) {// was:validsystem
			System.out.println("\tSystem " + (i++) + "\n\t\t" + system);
			int j = 0;
			Calculator calculator = new Calculator(this);
			for (FunctionalRequirement functionalRequirement : functionalRequirements) {
				for (SequenceAlternative sequenceAlternative : functionalRequirement.getSequenceAlternatives()) {
					double[] consumption = calculator.calculateEnergyConsumption(system, functionalRequirement, sequenceAlternative);
					double[] staticConsumption = calculator.calculatePlatformAndOtherConsuptions(system, functionalRequirement);
					// TODO: controllare condizioni
					if (system.getConsumptionEnergyPoints()[j] == -1 || consumption[0] < system.getConsumptionEnergyPoints()[j]) {
						system.getConsumptionEnergyPoints()[j] = Math.round(consumption[0] + staticConsumption[0]);
						system.getBestSequenceAlternativesEnergyPoint().add(sequenceAlternative);
					}
					if (system.getConsumptionWatt()[j] == -1 || consumption[1] > 0 && consumption[1] < system.getConsumptionWatt()[j]) {
						system.getConsumptionWatt()[j] = Math.round(consumption[1] + staticConsumption[1]);
						system.getBestSequenceAlternativesWatt().add(sequenceAlternative);
					}
				}
				j++;
			}
			system.calculateTotals();
			system.printAnalysisResultsSummary();
			System.out.println("\n\n");
		}
	}

	public boolean calculateEnergyConsumptionGeneration() {
		int i = 1;
		boolean feasible = false;
		System.out.println("EVALUATING SYSTEMS\n");
		for (SoftwareSystem system : systems) {
			System.out.println("\tSystem " + (i++) + "\n\t\t" + system);
			int j = 0;
			double totalTime = 0;
			Calculator calculator = new Calculator(this);
			for (FunctionalRequirement functionalRequirement : functionalRequirements) {
				double functionalRequirementTime = 0;
				for (SequenceAlternative sequenceAlternative : functionalRequirement.getSequenceAlternatives()) {
					double[] consumption = calculator.calculateEnergyConsumptionW(system, functionalRequirement, sequenceAlternative);
					if (system.getConsumptionWatt()[j] == -1 || consumption[1] > 0 && consumption[1] < system.getConsumptionWatt()[j]) {
						functionalRequirementTime = consumption[0];
						system.getConsumptionWatt()[j] = Math.round(consumption[1] * 100) / 100.0;
						system.getBestSequenceAlternativesWatt().add(sequenceAlternative);
					}
				}
				totalTime += functionalRequirementTime;
				j++;
			}
			if (totalTime <= 3600) {
				feasible = true;
				system.setValid(true);
				system.calculateTotalsW();
				system.printAnalysisResultsSummaryW();
			}
			System.out.println("\n\n");
		}
		return feasible;
	}

	public void calculateEnergyConsumptionEP() {
		int i = 1;
		System.out.println("EVALUATING SYSTEMS\n");
		for (SoftwareSystem system : systemsEP) {
			System.out.println("\tSystem " + (i++) + "\n\t\t" + system);
			int j = 0;
			Calculator calculator = new Calculator(this);

			for (FunctionalRequirement functionalRequirement : functionalRequirements) {
				if (i == 6 && functionalRequirement.getName().contains("FR7"))
					System.out.print(true);
				for (SequenceAlternative sequenceAlternative : functionalRequirement.getSequenceAlternatives()) {
					double consumption = calculator.calculateEnergyConsumptionEP(system, functionalRequirement, sequenceAlternative);

					if (system.getConsumptionEnergyPoints()[j] == -1 || consumption < system.getConsumptionEnergyPoints()[j]) {
						system.getConsumptionEnergyPoints()[j] = Math.round(consumption);
						system.getBestSequenceAlternativesEnergyPoint().add(sequenceAlternative);
					}
				}
				j++;
			}
			system.calculateTotalsEP();
			system.printAnalysisResultsSummaryEP();
			System.out.println("\n\n");
		}
	}

	public void findBestSystem() {
		for (SoftwareSystem softwareSystem : systems)
			softwareSystem.refine();
		bestSoftwareSystemEP = systems.get(0);
		bestSoftwareSystemW = systems.get(0);
		worstSoftwareSystemEP = systems.get(0);
		worstSoftwareSystemW = systems.get(0);
		for (SoftwareSystem softwareSystem : systems) {
			if (softwareSystem.getSystemConsumptionEP() < bestSoftwareSystemEP.getSystemConsumptionEP())
				bestSoftwareSystemEP = softwareSystem;
			else if (softwareSystem.getSystemConsumptionEP() > worstSoftwareSystemEP.getSystemConsumptionEP())
				worstSoftwareSystemEP = softwareSystem;
			if (softwareSystem.getSystemConsumptionW() < bestSoftwareSystemW.getSystemConsumptionW())
				bestSoftwareSystemW = softwareSystem;
			else if (softwareSystem.getSystemConsumptionW() > worstSoftwareSystemW.getSystemConsumptionW())
				worstSoftwareSystemW = softwareSystem;
		}
	}

	public void findBestSystemGeneration() {
		for (SoftwareSystem softwareSystem : systems)
			softwareSystem.refine();
		bestSoftwareSystemW = systems.get(0);
		worstSoftwareSystemW = systems.get(0);
		for (SoftwareSystem softwareSystem : systems) {
			if (softwareSystem.getSystemConsumptionW() < bestSoftwareSystemW.getSystemConsumptionW())
				bestSoftwareSystemW = softwareSystem;
			else if (softwareSystem.getSystemConsumptionW() > worstSoftwareSystemW.getSystemConsumptionW())
				worstSoftwareSystemW = softwareSystem;
		}
	}

	public void findBestSystemEP() {
		for (SoftwareSystem softwareSystem : systemsEP)
			softwareSystem.refine();
		bestSoftwareSystemEP = systemsEP.get(0);
		worstSoftwareSystemEP = systemsEP.get(0);
		for (SoftwareSystem softwareSystem : systemsEP) {
			if (softwareSystem.getSystemConsumptionEP() < bestSoftwareSystemEP.getSystemConsumptionEP())
				bestSoftwareSystemEP = softwareSystem;
			else if (softwareSystem.getSystemConsumptionEP() > worstSoftwareSystemEP.getSystemConsumptionEP())
				worstSoftwareSystemEP = softwareSystem;
		}
	}

	public void finalizeFunctionalRequirements() {
		for (FunctionalRequirement functionalRequirement : functionalRequirements)
			functionalRequirement.calculateCoefficient();
	}

	public void visualize(String path) {
		Visualizer visualizer = new Visualizer(name, path, isWReliable, bestSoftwareSystemEP, worstSoftwareSystemEP, bestSoftwareSystemW, worstSoftwareSystemW);
		visualizer.visualize();
	}

	public void visualizeGeneration(String path) {
		new Visualizer(name, path, isWReliable, bestSoftwareSystemW, worstSoftwareSystemW).visualizeW();
	}

	public void visualizeEP(String path) {
		new Visualizer(name, path, bestSoftwareSystemEP, worstSoftwareSystemEP).visualizeEP();
	}

	public void log(String path) {
		String newPath = path.substring(0, path.lastIndexOf("/"));
		Utils.writeFile(newPath, "log.txt", toString());
		System.out.println(this);
	}

	public boolean isWReliable() {
		return isWReliable;
	}

	public void setWReliable(boolean isWReliable) {
		this.isWReliable = isWReliable;
	}

	public int sortHardwareAlternatives() {
		int maxDepth = 1;
		for (HardwareSet hardwareSet : hardwareSets) {
			maxDepth = hardwareSet.getCpuAlternatives().size() > maxDepth ? hardwareSet.getCpuAlternatives().size() : maxDepth;
			maxDepth = hardwareSet.getHddAlternatives().size() > maxDepth ? hardwareSet.getHddAlternatives().size() : maxDepth;
			maxDepth = hardwareSet.getMemoryAlternatives().size() > maxDepth ? hardwareSet.getMemoryAlternatives().size() : maxDepth;
			maxDepth = hardwareSet.getNetworkAlternatives().size() > maxDepth ? hardwareSet.getNetworkAlternatives().size() : maxDepth;
			maxDepth = hardwareSet.getPlatformAlternatives().size() > maxDepth ? hardwareSet.getPlatformAlternatives().size() : maxDepth;
			maxDepth = hardwareSet.getOtherAlternatives().size() > maxDepth ? hardwareSet.getOtherAlternatives().size() : maxDepth;
			hardwareSet.setCpuAlternatives(Utils.sortHardwareAlternatives(hardwareSet.getCpuAlternatives()));
			hardwareSet.setHddAlternatives(Utils.sortHardwareAlternatives(hardwareSet.getHddAlternatives()));
			hardwareSet.setMemoryAlternatives(Utils.sortHardwareAlternatives(hardwareSet.getMemoryAlternatives()));
			hardwareSet.setNetworkAlternatives(Utils.sortHardwareAlternatives(hardwareSet.getNetworkAlternatives()));
			hardwareSet.setPlatformAlternatives(Utils.sortHardwareAlternatives(hardwareSet.getPlatformAlternatives()));
			hardwareSet.setOtherAlternatives(Utils.sortHardwareAlternatives(hardwareSet.getOtherAlternatives()));
		}
		return maxDepth;
	}

	public String toString() {
		String status = "Project " + name + " status:\n";
		int i = 1;

		status += "\n\tFunctional requirements:\n";
		for (FunctionalRequirement fr : functionalRequirements)
			status += "\t\t" + fr + "\n";
		status += "\n\tStakeholders:\n";
		for (Stakeholder sh : stakeholders)
			status += "\t\t" + sh + "\n";
		status += "\n\tAssociations enhanced:\n";
		for (AssociationEnhanced ae : associations)
			status += "\t\t" + ae + "\n";
		status += "\n\tHardware sets:\n";
		for (HardwareSet hws : hardwareSets)
			status += "\t\t" + hws + "\n";
		status += "\n\tComponents:\n";
		for (Component c : components)
			status += "\t\t" + c + "\n";
		status += "\n\tConnectors:\n";
		for (Connector c : connectors)
			status += "\t\t" + c + "\n";
		status += "\n\tSequence alternatives:\n";
		for (FunctionalRequirement fr : functionalRequirements)
			for (SequenceAlternative sa : fr.getSequenceAlternatives())
				status += "\t\t" + fr.getName() + ": " + sa + "\n";
		status += "\n\tDeployment alternatives:\n";
		for (DeploymentAlternative da : deploymentAlternatives)
			status += "\t\t" + (i++) + ": " + da + "\n";
		status += "\n\tHardware set alternatives:\n";
		for (HardwareSet hws : hardwareSets) {
			i = 1;
			status += "\t\t" + hws.getName() + ":\n";
			for (HardwareSetAlternative hsa : hws.getHardwareSetAlternatives()) {
				status += "\t\t" + (i++) + ": " + hsa;
				status += "\n";
			}
		}
		i = 1;
		status += "\n\tHardware systems:\n";
		for (HardwareSystem hs : hardwareSystems)
			status += "\t\t" + (i++) + ": " + hs + "\n";
		i = 1;
		status += "\n\tSystems:\n";
		for (SoftwareSystem sys : systems)
			status += "\t\tSystem " + (i++) + ":\n\t\t" + sys + "\n";
		return status;
	}
}