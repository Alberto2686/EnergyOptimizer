package energyoptimizer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AlternativesGenerator {
	private Project project;

	/**
	 * @param functionalRequirements
	 * @param components
	 */
	public AlternativesGenerator(Project project) {
		this.project = project;
	}

	public List<SoftwareSystem> generateAlternatives() {
		generateHardwareSetAlternatives();// O(HWS)*O(cpuAlt*hddAlt*memAlt*netAlt*plaAlt*othAlt)
		generateAllDeploymentAlternatives();
		generateHardwareSystemAlternatives();
		return generateSystems();
	}

	public List<SoftwareSystem> generateAlternativesOld() {
		generateHardwareSetAlternatives();
		generateDeploymentAlternatives();
		generateHardwareSystemAlternatives();
		generateSystems();
		return generateSystems();
	}

	private void generateAllDeploymentAlternatives() {
		List<HardwareSet> sequenceHardwareSets = new LinkedList<>();
		int hardwarePossibilities[] = new int[project.getComponents().size()];
		int repetitions[] = new int[project.getComponents().size()];
		int k = 0;
		int alternatives = 1;
		for (Component sc : project.getComponents()) {
			hardwarePossibilities[k] = sc.getHardwareSets().size();
			alternatives *= sc.getHardwareSets().size();
			repetitions[k++] = alternatives / sc.getHardwareSets().size();
			for (HardwareSet hws : sc.getHardwareSets())
				addHardwareSetIfNotPresent(sequenceHardwareSets, hws);
		}
		for (int i = 0; i < alternatives; i++) {
			DeploymentAlternative deploymentAlternative = new DeploymentAlternative();
			for (int j = 0; j < project.getComponents().size(); j++) {
				int index = (i / repetitions[j]) % project.getComponents().get(j).getHardwareSets().size();
				HardwareSet hws = project.getComponents().get(j).getHardwareSets().get(index);
				DeployedComponent deployedComponent = new DeployedComponent(project.getComponents().get(j), hws);
				deploymentAlternative.getDeployedComponents().add(deployedComponent);
			}
			deploymentAlternative.initializeId();
			project.getDeploymentAlternatives().add(deploymentAlternative);
		}
	}

	private void generateHardwareSetAlternatives() {
		for (HardwareSet hws : project.getHardwareSets())
			hws.generateHardwareSetAlternatives();
	}

	private void generateDeploymentAlternatives() {
		for (FunctionalRequirement fr : project.getFunctionalRequirements()) {
			for (SequenceAlternative sa : fr.getSequenceAlternatives()) {
				List<Component> sequenceComponents = sa.getComponents();
				List<HardwareSet> sequenceHardwareSets = new LinkedList<>();
				int hardwarePossibilities[] = new int[sequenceComponents.size()];
				int repetitions[] = new int[sequenceComponents.size()];
				int i = 0;
				int alternatives = 1;
				for (Component sc : sequenceComponents) {
					hardwarePossibilities[i] = sc.getHardwareSets().size();
					alternatives *= sc.getHardwareSets().size();
					repetitions[i++] = alternatives / sc.getHardwareSets().size();
					for (HardwareSet hws : sc.getHardwareSets())
						addHardwareSetIfNotPresent(sequenceHardwareSets, hws);
				}
				generateDeploymentAlternatives(fr, sa, sequenceComponents, sequenceHardwareSets, alternatives, hardwarePossibilities, repetitions);
			}
		}
	}

	private void generateDeploymentAlternatives(FunctionalRequirement functionalRequirement, SequenceAlternative sequenceAlternative, List<Component> sequenceComponents, List<HardwareSet> sequenceHardwareSets, int alternatives, int[] hardwarePossibilities, int[] repetitions) {
		for (int i = 0; i < alternatives; i++) {
			DeploymentAlternative deploymentAlternative = new DeploymentAlternative();
			for (int j = 0; j < sequenceComponents.size(); j++) {
				int index = (i / repetitions[j]) % sequenceComponents.get(j).getHardwareSets().size();
				HardwareSet hws = sequenceComponents.get(j).getHardwareSets().get(index);
				DeployedComponent deployedComponent = new DeployedComponent(sequenceComponents.get(j), hws);
				deploymentAlternative.getDeployedComponents().add(deployedComponent);
			}
			deploymentAlternative.initializeId();
			addDeploymentAlternativeIfNotPresent(deploymentAlternative, sequenceAlternative, functionalRequirement);
		}
	}

	private List<SoftwareSystem> generateSystems() {
		List<SoftwareSystem> systems = new ArrayList<>();
		for (HardwareSystem hardwareSystem : project.getHardwareSystems())
			for (DeploymentAlternative deploymentAlternative : project.getDeploymentAlternatives())
				systems.add(new SoftwareSystem(hardwareSystem, deploymentAlternative, project.getFunctionalRequirements().size()));
		return systems;
	}

	private void generateHardwareSystemAlternatives() {
		int alternatives = 1;
		int possibilities[] = new int[project.getHardwareSets().size()];
		int repetitions[] = new int[project.getHardwareSets().size()];
		int i = 0;
		for (HardwareSet hws : project.getHardwareSets()) {
			alternatives *= hws.getHardwareSetAlternatives().size();
			possibilities[i] = hws.getHardwareSetAlternatives().size();
			repetitions[i++] = alternatives / hws.getHardwareSetAlternatives().size();
		}
		generateHardwareSystems(alternatives, possibilities, repetitions);
	}

	private void generateHardwareSystems(int alternatives, int[] possibilities, int[] repetitions) {
		for (int i = 0; i < alternatives; i++) {
			HardwareSystem hardwareSystem = new HardwareSystem();
			for (int j = 0; j < project.getHardwareSets().size(); j++) {
				int index = (i / repetitions[j]) % project.getHardwareSets().get(j).getHardwareSetAlternatives().size();
				HardwareSetAlternative hwa = project.getHardwareSets().get(j).getHardwareSetAlternatives().get(index);
				hardwareSystem.getHardwareSetAlternatives().add(hwa);
			}
			project.getHardwareSystems().add(hardwareSystem);
		}
	}

	public void addHardwareSetIfNotPresent(List<HardwareSet> sequenceHardwareSets, HardwareSet hardwareSet) {
		for (HardwareSet hws : sequenceHardwareSets)
			if (hws.getId().equals(hardwareSet.getId()))
				return;
		sequenceHardwareSets.add(hardwareSet);
	}

	public void addDeploymentAlternativeIfNotPresent(DeploymentAlternative deploymentAlternative, SequenceAlternative sequenceAlternative, FunctionalRequirement functionalRequirement) {
		for (DeploymentAlternative da : project.getDeploymentAlternatives())
			if (da.getId().equals(deploymentAlternative.getId())) {// deploymentAlternative
																	// already
																	// present
				da.addFunctionalRequirementsCoveredIfNotPresent(functionalRequirement);
				for (DeploymentAlternative sda : sequenceAlternative.getDeploymentAlternatives())
					if (sda.getId().equals(da.getId()))
						return;
				sequenceAlternative.getDeploymentAlternatives().add(da);
				return;
			}
		project.getDeploymentAlternatives().add(deploymentAlternative);
		sequenceAlternative.getDeploymentAlternatives().add(deploymentAlternative);
		deploymentAlternative.getFunctionalRequirementsCovered().add(functionalRequirement);
	}
}