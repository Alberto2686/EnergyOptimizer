package energyOptimizer.elements;

public class FrequencyVoltage {
	private int frequency;
	private double voltage;
	private double performanceScore;

	public FrequencyVoltage(int frequency, double voltage, double performanceScore) {
		this.frequency = frequency;
		this.voltage = voltage;
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

	public double getPerformanceScore() {
		return performanceScore;
	}

	public void setPerformanceScore(double performanceScore) {
		this.performanceScore = performanceScore;
	}

	@Override
	public String toString() {
		return "(" + frequency + "MHz," + voltage + "V," + performanceScore + "PerformanceScore)";
	}
}