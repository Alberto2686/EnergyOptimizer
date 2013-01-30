package energyoptimizer;

public class Network extends HardwareComponent {
	private double bandwidth,mbConsumption;

	public Network(String id, String name, double bandwidth,double mbConsumption) {
		setName(name);
		setId(id);
		this.bandwidth = bandwidth;
		this.mbConsumption = mbConsumption;
	}

	public double getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(double bandwidth) {
		this.bandwidth = bandwidth;
	}

	public double getWorkConsumption() {
		return mbConsumption;
	}

	public void setMbConsumption(double mbConsumption) {
		this.mbConsumption = mbConsumption;
	}
	@Override
	public String toString(){
		return getName()+" bandwidth:"+bandwidth+" consumption per MB:"+mbConsumption;
	}
}