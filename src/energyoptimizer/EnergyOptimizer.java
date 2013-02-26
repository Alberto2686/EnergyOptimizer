package energyOptimizer;

/**
 * @author Alberto
 *
 */
public class EnergyOptimizer {
	/**
	 * @param path
	 */
	public static void start(String path) {
		Project project = new Project();
		UMLparser parser = new UMLparser(project);
		parser.parseXmlFile(path);
		project.generateAlternatives();
		project.log(path);
		project.calculateEnergyConsumption();
		project.findBestSystem();
		project.visualize(path);
	}
}
