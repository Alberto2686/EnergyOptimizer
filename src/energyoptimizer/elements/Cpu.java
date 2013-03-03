package energyOptimizer.elements;

import java.util.LinkedList;
import java.util.List;

public class Cpu extends HardwareComponent {
	private int cores;
	private double tdp;
	private List<FrequencyVoltage> frequenciesVoltages = new LinkedList<>();
	private FrequencyVoltage frequencyVoltageTDP;

	public Cpu(String name, String id, int cores, double tdp) {
		setName(name);
		setId(id);
		this.cores = cores;
		this.tdp = tdp;
	}

	public int getCores() {
		return cores;
	}

	public void setCores(int cores) {
		this.cores = cores;
	}

	public double getTdp() {
		return tdp;
	}

	public void setTdp(double tdp) {
		this.tdp = tdp;
	}

	public List<FrequencyVoltage> getFrequenciesVoltages() {
		return frequenciesVoltages;
	}

	public void setFrequenciesVoltages(List<FrequencyVoltage> frequenciesVoltages) {
		this.frequenciesVoltages = frequenciesVoltages;
	}

	public FrequencyVoltage getFrequencyVoltageTDP() {
		return frequencyVoltageTDP;
	}

	public void setFrequencyVoltageTDP(FrequencyVoltage frequencyVoltageTDP) {
		this.frequencyVoltageTDP = frequencyVoltageTDP;
		setConsumptionIndicator(tdp * frequencyVoltageTDP.getFrequency() * frequencyVoltageTDP.getVoltage() * frequencyVoltageTDP.getVoltage());
	}

	@Override
	public String toString() {
		return getName() + " cores:" + cores + " TDP:" + tdp + " TDP f/v: " + frequencyVoltageTDP + " f/v: " + frequenciesVoltages + " consumption indicator:" + getConsumptionIndicator();
	}
}