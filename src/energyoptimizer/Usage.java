package energyoptimizer;

public abstract class Usage {
	private int estimatedMilliseconds;
	private double utilization;
	private int energyPoints;
	public int getEstimatedMilliseconds() {
		return estimatedMilliseconds;
	}
	public void setEstimatedMilliseconds(int estimatedMilliseconds) {
		this.estimatedMilliseconds = estimatedMilliseconds;
	}
	public double getUtilization() {
		return utilization;
	}
	public void setUtilization(double utilization) {
		this.utilization = utilization;
	}
	public int getEnergyPoints() {
		return energyPoints;
	}
	public void setEnergyPoints(int energyPoints) {
		this.energyPoints = energyPoints;
	}
}