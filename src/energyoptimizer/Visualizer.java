package energyOptimizer;

import energyOptimizer.elements.SoftwareSystem;

public class Visualizer {
	private SoftwareSystem bestSoftwareSystemEP, worstSoftwareSystemEP,
			bestSoftwareSystemW, worstSoftwareSystemW;
	private String name, savePath;
	private boolean isWReliable;

	public Visualizer(String name, String path, boolean isWReliable, SoftwareSystem bestSoftwareSystemEP, SoftwareSystem worstSoftwareSystemEP, SoftwareSystem bestSoftwareSystemW, SoftwareSystem worstSoftwareSystemW) {
		this.name = name;
		this.savePath = path.substring(0, path.lastIndexOf("/"));
		this.isWReliable = isWReliable;
		this.bestSoftwareSystemEP = bestSoftwareSystemEP;
		this.worstSoftwareSystemEP = worstSoftwareSystemEP;
		this.bestSoftwareSystemW = bestSoftwareSystemW;
		this.worstSoftwareSystemW = worstSoftwareSystemW;
	}

	public void visualize() {
		visualizeBestAndWorstSystems();
		createDeploymentDiagram();
	}

	private void createDeploymentDiagram() {
		Utils.writeFile(savePath, "bestEP.uml", UMLcreator.createDeploymentDiagram(bestSoftwareSystemEP, "depEP" + Utils.getVersionFromDate(), name, true));
		Utils.writeFile(savePath, "bestW.uml", UMLcreator.createDeploymentDiagram(bestSoftwareSystemW, "depW" + Utils.getVersionFromDate(), name, false));
		System.out.println(UMLcreator.createDeploymentDiagram(bestSoftwareSystemEP, "depEP" + Utils.getVersionFromDate(), name, true));
		System.out.println(UMLcreator.createDeploymentDiagram(bestSoftwareSystemW, "depW" + Utils.getVersionFromDate(), name, false));

		double ratioEP = Math.round(10000 - (10000 * bestSoftwareSystemEP.getSystemConsumptionEP()) / worstSoftwareSystemEP.getSystemConsumptionEP()) / 100.0;
		double ratioW = Math.round(10000 - (10000 * bestSoftwareSystemW.getSystemConsumptionW()) / worstSoftwareSystemW.getSystemConsumptionW()) / 100.0;
		System.out.println("Efficiency improvement: " + ratioEP + "% EP and " + ratioW + "% W");
	}

	private void visualizeBestAndWorstSystems() {
		String reliability = isWReliable ? "" : "but some consumption parameters were missing";
		String results = "BEST SYSTEMS:\nEVALUATING ENERGY POINTS:\n" + bestSoftwareSystemEP.bestEpToString() + "\n" + bestSoftwareSystemEP.getSystemConsumptionEP() + "EP\n\n\nEVALUATING WATT CONSUMPTION:\n" + bestSoftwareSystemW.bestWToString() + "\n" + bestSoftwareSystemW.getSystemConsumptionW() + "W " + reliability;
		System.out.println("\n\n\n" + results);
		Utils.writeFile(savePath, "results.txt", results);
		System.out.println("WORST OF BESTS: " + worstSoftwareSystemEP.getSystemConsumptionEP() + "EP " + worstSoftwareSystemW.getSystemConsumptionW() + "W");
	}
}