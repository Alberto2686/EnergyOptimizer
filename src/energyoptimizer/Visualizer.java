package energyOptimizer;

import energyOptimizer.elements.*;

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

	public Visualizer(String name, String path, SoftwareSystem bestSoftwareSystemEP, SoftwareSystem worstSoftwareSystemEP) {
		this.name = name;
		this.savePath = path.substring(0, path.lastIndexOf("/"));
		this.bestSoftwareSystemEP = bestSoftwareSystemEP;
		this.worstSoftwareSystemEP = worstSoftwareSystemEP;
	}

	public void visualize() {
		visualizeBestAndWorstSystems();
		createDeploymentDiagram();
	}

	public void visualizeEP() {
		visualizeBestAndWorstSystemsEP();
		createDeploymentDiagramEP();
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

	private void createDeploymentDiagramEP() {
		Utils.writeFile(savePath, "bestEP.uml", UMLcreator.createDeploymentDiagram(bestSoftwareSystemEP, "depEP" + Utils.getVersionFromDate(), name, true));
		System.out.println(UMLcreator.createDeploymentDiagram(bestSoftwareSystemEP, "depEP" + Utils.getVersionFromDate(), name, true));

		double ratioEP = Math.round(10000 - (10000 * bestSoftwareSystemEP.getSystemConsumptionEP()) / worstSoftwareSystemEP.getSystemConsumptionEP()) / 100.0;
		System.out.println("Efficiency improvement: " + ratioEP + "% EP");
	}

	private void visualizeBestAndWorstSystems() {
		String reliability = isWReliable ? "" : "but some consumption parameters were missing";
		String results = "BEST SYSTEMS:\nEVALUATING ENERGY POINTS:\n" + bestSoftwareSystemEP.bestEpToString() + "\n" + bestSoftwareSystemEP.getSystemConsumptionEP() + "EP\n\n\nEVALUATING WATT CONSUMPTION:\n" + bestSoftwareSystemW.bestWToString() + "\n" + bestSoftwareSystemW.getSystemConsumptionW() + "W " + reliability;
		System.out.println("\n\n\n" + results);
		Utils.writeFile(savePath, "results.txt", results);
		System.out.println("WORST OF BESTS: " + worstSoftwareSystemEP.getSystemConsumptionEP() + "EP " + worstSoftwareSystemW.getSystemConsumptionW() + "W");
	}

	private void visualizeBestAndWorstSystemsEP() {
		String results = "BEST SYSTEMS:\nEVALUATING ENERGY POINTS:\n" + bestSoftwareSystemEP.bestEpToString() + "\n" + bestSoftwareSystemEP.getSystemConsumptionEP();
		System.out.println("\n\n\n" + results);
		Utils.writeFile(savePath, "results.txt", results);
		System.out.println("WORST OF BESTS: " + worstSoftwareSystemEP.getSystemConsumptionEP() + "EP");
	}
}