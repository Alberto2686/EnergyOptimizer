package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class UsageCPU extends Usage {
	private int cycles;
	private List<ExactTime> exactTimes = new LinkedList<>();

	public int getCycles() {
		return cycles;
	}

	public void setCycles(int cycles) {
		this.cycles = cycles;
	}

	public List<ExactTime> getExactTimes() {
		return exactTimes;
	}

	public void setExactTimes(List<ExactTime> exactTimes) {
		this.exactTimes = exactTimes;
	}

	public String toString() {
		return "EP:" + getEnergyPoints() + ", estimated milliseconds on def: " + getEstimatedMilliseconds() + ", exactTimes: " + exactTimes + ", utilization: " + getUtilization() + ", cycles:" + getCycles();
	}
}