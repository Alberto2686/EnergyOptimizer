package energyoptimizer;

public class FrequencyVoltage {
	private int frequency;
	private double voltage;
	private int energyPoints;
	private double performanceScore;
	
	public FrequencyVoltage(int frequency, double voltage, int energyPoints, double performanceScore) {
		this.frequency = frequency;
		this.voltage = voltage;
		this.energyPoints = energyPoints;
		this.setPerformanceScore(performanceScore);
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
	public double getPerformanceScore() {
		return performanceScore;
	}
	public void setPerformanceScore(double performanceScore) {
		this.performanceScore = performanceScore;
	}
	@Override
	public String toString(){
		return "("+frequency+"MHz,"+voltage+"V,"+energyPoints+"maxEP,"+performanceScore+"PerformanceScore)";
	}
}