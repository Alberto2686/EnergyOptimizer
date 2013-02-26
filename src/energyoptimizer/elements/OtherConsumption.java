package energyOptimizer.elements;

public class OtherConsumption extends ModelElement {
	private double consumption;
	private int energyPoints;

	public OtherConsumption(String name, double consumption, int energyPoints) {
		setName(name);
		this.consumption = consumption;
		this.energyPoints = energyPoints;
	}

	public double getConsumption() {
		return consumption;
	}

	public void setConsumption(double consumption) {
		this.consumption = consumption;
	}

	public int getEnergyPoints() {
		return energyPoints;
	}

	public void setEnergyPoints(int energyPoints) {
		this.energyPoints = energyPoints;
	}

	public String toString() {
		return getName() + " " + consumption + "W, " + energyPoints + "EP";
	}
}