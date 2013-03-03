package energyOptimizer.elements;

public abstract class HardwareComponent extends ModelElement {
	private double consumptionIndicator = 0;

	public double getConsumptionIndicator() {
		return consumptionIndicator;
	}

	public void setConsumptionIndicator(double consumptionIndicator) {
		this.consumptionIndicator = consumptionIndicator;
	}
}