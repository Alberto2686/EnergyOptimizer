package energyoptimizer;

public class EnergyOptimizer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Project project = new Project("Project-Name");
		UMLparser parser = new UMLparser(project);
		parser.parseXmlFile("Example/scenario.uml");
		System.out.print(project.toString());
	}

}
