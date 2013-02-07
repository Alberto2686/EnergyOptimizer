package energyoptimizer;

public class FrequencyVoltage {
	private int frequency;
	private double voltage;
	private int energyPoints;
	
	public FrequencyVoltage(int frequency, double voltage, int energyPoints) {
		this.frequency = frequency;
		this.voltage = voltage;
		this.energyPoints = energyPoints;
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
	public int getEnergyPoints() {
		return energyPoints;
	}
	public void setEnergyPoints(int energyPoints) {
		this.energyPoints = energyPoints;
	}
	@Override
	public String toString(){
		return "("+frequency+"MHz,"+voltage+"V,"+energyPoints+"maxEP)";
	}
}