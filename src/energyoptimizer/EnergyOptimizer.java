package energyoptimizer;

public class EnergyOptimizer {
	public static void main(String[] args) {
		Project project = new Project();
		UMLparser parser = new UMLparser(project);
		parser.parseXmlFile("Example/scenario.uml");
		project.generateAlternatives();
		System.out.print(project);
		project.calculateEnergyConsumption();
		project.findBestSystem();
		project.visualize("/");
	}
}