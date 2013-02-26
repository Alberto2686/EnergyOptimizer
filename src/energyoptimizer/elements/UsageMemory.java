package energyOptimizer.elements;

public class UsageMemory extends Usage {
	private Size size;

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public String toString() {
		return "EP:" + getEnergyPoints() + ", estimated time: " + getEstimatedMilliseconds() + "ms, utilization: " + getUtilization() + ", size:" + size;
	}
}