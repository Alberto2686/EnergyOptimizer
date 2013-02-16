package energyoptimizer;

public class EnergyOptimizerPlugin {
	public static void start(String path) {
		Project project = new Project();
		UMLparser parser = new UMLparser(project);
		parser.parseXmlFile(path);
		project.generateAlternatives();
		System.out.print(project);
		project.calculateEnergyConsumption();
		project.findBestSystem();
		project.createDeploymentDiagram(path);
	}
}
