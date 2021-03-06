package energyOptimizer.elements;

public class Hdd extends HardwareComponent {
	private double bandwidth, workConsumption, idleConsumption;

	public Hdd(String name, String id, double bandwidth, double workConsumption, double idleConsumption) {
		setName(name);
		setId(id);
		this.bandwidth = bandwidth;
		this.workConsumption = workConsumption;
		this.idleConsumption = idleConsumption;
		setConsumptionIndicator(workConsumption/bandwidth);
	}

	public double getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(double bandwidth) {
		this.bandwidth = bandwidth;
	}

	public double getWorkConsumption() {
		return workConsumption;
	}

	public void setWorkConsumption(double workConsumption) {
		this.workConsumption = workConsumption;
	}

	public double getIdleConsumption() {
		return idleConsumption;
	}

	public void setIdleConsumption(double idleConsumption) {
		this.idleConsumption = idleConsumption;
	}

	@Override
	public String toString() {
		return getName() + " bandwidth:" + bandwidth + " workConsumption:" + workConsumption + " idleConsumption:" + idleConsumption + " consumption indicator:" + getConsumptionIndicator();
	}
}