package energyoptimizer;

public class EnergyOptimizer {
	public static void main(String[] args) {
		Project project = new Project();
		UMLparser parser = new UMLparser(project);
		parser.parseXmlFile("Example/scenario.uml");
		//TODO: DEPLOYMENTALTERNATIVE GENERATION
		project.generateAlternatives();
		System.out.print(project.toString());
	}
}