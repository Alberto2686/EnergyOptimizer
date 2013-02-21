package energyoptimizer;

public class ExactTime {
	private int milliseconds;
	private Cpu cpu;

	public int getMilliseconds() {
		return milliseconds;
	}

	public void setMilliseconds(int milliseconds) {
		this.milliseconds = milliseconds;
	}

	public Cpu getCpu() {
		return cpu;
	}

	public void setCpu(Cpu cpu) {
		this.cpu = cpu;
	}

	public String toString() {
		return milliseconds + "ms on " + cpu.getName();
	}
}