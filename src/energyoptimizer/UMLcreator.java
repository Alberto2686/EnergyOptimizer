package energyOptimizer;

import energyOptimizer.elements.*;

public class UMLcreator {

	public static String createDeploymentDiagram(SoftwareSystem system, String id, String name, boolean withRespectToEP) {
		String deploymentDiagram = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		deploymentDiagram += "<uml:Model xmi:version=\"" + Utils.getVersionFromDate() + "\" xmlns:xmi=\"http://www.omg.org/spec/XMI/20110701\" xmlns:uml=\"http://www.eclipse.org/uml2/4.0.0/UML\" xmi:id=\"" + id + "\" name=\"" + name + "\">\n";
		for (HardwareSetAlternative hardwareSetAlternative : system.getHardwareSystem().getHardwareSetAlternatives())
			if (withRespectToEP)
				deploymentDiagram += createDevice(hardwareSetAlternative, system.getDeploymentEP());
			else
				deploymentDiagram += createDevice(hardwareSetAlternative, system.getDeploymentW());
		deploymentDiagram += "</uml:Model>";
		return deploymentDiagram;
	}

	private static String createDevice(HardwareSetAlternative hardwareSetAlternative, DeploymentAlternative deploymentAlternative) {
		String device = "\t<packagedElement xmi:type=\"uml:Device\" xmi:id=\"" + hardwareSetAlternative.getHardwareSet().getId() + "\" name=\"" + hardwareSetAlternative.getHardwareSet().getName() + "\">\n";
		device += createExecutionEnvironment(hardwareSetAlternative.getPlatformAlternative(), hardwareSetAlternative.getHardwareSet().getId(), deploymentAlternative);
		device += createNode(hardwareSetAlternative.getCpuAlternative(), true);
		device += createNode(hardwareSetAlternative.getHddAlternative(), true);
		device += createNode(hardwareSetAlternative.getMemoryAlternative(), true);
		device += createNode(hardwareSetAlternative.getNetworkAlternative(), true);
		device += createNode(hardwareSetAlternative.getOtherAlternative(), false);
		device += "\t</packagedElement>\n";
		return device;
	}

	private static String createNode(HardwareAlternative hardwareAlternative, boolean showAllAlternativeComponents) {
		String node = "";
		if (showAllAlternativeComponents)
			for (HardwareComponent hardwareComponent : hardwareAlternative.getHardwareComponents())
				node += "\t\t<nestedNode xmi:id=\"" + hardwareComponent.getId() + "\" name=\"" + hardwareComponent.getName() + "\"/>\n";
		else
			node += "\t\t<nestedNode xmi:id=\"" + hardwareAlternative.getId() + "\" name=\"" + hardwareAlternative.getName() + "\"/>\n";

		return node;
	}

	private static String createExecutionEnvironment(HardwareAlternative platformAlternative, String hardwareSetId, DeploymentAlternative deploymentAlternative) {
		String executionEnvironment = "\t\t<nestedNode xmi:type=\"uml:ExecutionEnvironment\" xmi:id=\"" + platformAlternative.getId() + "\" name=\"" + platformAlternative.getName() + "\">\n";
		for (DeployedComponent deployedComponent : deploymentAlternative.getDeployedComponents())
			if (deployedComponent.getHardwareSet().getId().equals(hardwareSetId))
				executionEnvironment += createArtifact(deployedComponent.getComponent());
		executionEnvironment += "\t\t</nestedNode>\n";
		return executionEnvironment;
	}

	private static String createArtifact(Component component) {
		return "\t\t\t<nestedClassifier xmi:type=\"uml:Artifact\" xmi:id=\"" + component.getId() + "\" name=\"" + component.getName() + "\" />\n";
	}
}