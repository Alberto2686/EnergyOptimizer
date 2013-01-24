package energyoptimizer;

public class FrequencyVoltage {
	private int frequency;
	private double voltage;
	public FrequencyVoltage(int frequency, double voltage) {
		this.frequency = frequency;
		this.voltage = voltage;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public double getVoltage() {
		return voltage;
	}
	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}
	@Override
	public String toString(){
		return "("+frequency+","+voltage+")";
	}
}