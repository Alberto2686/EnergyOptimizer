package energyoptimizer;

public class AtomicOperationConsumption extends ModelElement {
	private double cost;
	private String cpuId;
	private Cpu cpu;

	public AtomicOperationConsumption(String id, String name, double cost, String cpuId) {
		setId(id);
		setName(name);
		this.cost = cost;
		this.setCpuId(cpuId);
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Cpu getCpu() {
		return cpu;
	}

	public void setCpu(Cpu cpu) {
		this.cpu = cpu;
	}

	public String getCpuId() {
		return cpuId;
	}

	public void setCpuId(String cpuId) {
		this.cpuId = cpuId;
	}

	public String toString() {
		return getName() + " id:" + getId() + " cost:" + cost + " on " + cpu.getName();
	}
}