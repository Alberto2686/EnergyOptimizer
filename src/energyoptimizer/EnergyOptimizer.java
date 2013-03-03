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
		EnergyOptimizer.path=path;
		project = new Project();
		UMLparser parser = new UMLparser(project);
		parser.parseXmlFile(path);
		allCombinations();
		//energyPointReductionEuristic();
	}
	
	private static void allCombinations(){
		project.generateAlternatives();
		project.log(path);
		project.calculateEnergyConsumption();
		project.findBestSystem();
		project.visualize(path);
	}
	
	private static void energyPointReductionEuristic(){
		project.generateAlternativesEP();
		project.log(path);
		project.calculateEnergyConsumptionEP();
		project.findBestSystemEP();
		project.visualizeEP(path);
	}
}
