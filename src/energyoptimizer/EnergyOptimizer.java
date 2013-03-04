package energyOptimizer;

/**
 * @author Alberto
 * 
 */
public class EnergyOptimizer {
	static Project project;
	static String path;

	/**
	 * @param path
	 */
	public static void start(String path) {
		EnergyOptimizer.path = path;
		project = new Project();
		UMLparser parser = new UMLparser(project);
		parser.parseXmlFile(path);
		int maxDepth=project.sortHardwareAlternatives();
		//allCombinations();
		generationBased(1,maxDepth);
		energyPointReductionEuristic();
	}

	private static void generationBased(int start, int maxDepth) {
		boolean done = false;
		int i=start;
		while (!done&&i<=maxDepth) {
			project.generateAlternativesGeneration(i++);
			project.log(path);
			done = project.calculateEnergyConsumptionGeneration();
		}
		project.findBestSystemGeneration();
		project.visualizeGeneration(path);
	}

	private static void allCombinations() {
		/*project.generateAlternatives();
		project.log(path);
		project.calculateEnergyConsumption();
		project.findBestSystem();
		project.visualize(path);*/
	}

	private static void energyPointReductionEuristic() {
		project.generateAlternativesEP();
		project.log(path);
		project.calculateEnergyConsumptionEP();
		project.findBestSystemEP();
		project.visualizeEP(path);
	}
}
