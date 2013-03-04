package energyOptimizer.elements;

import energyOptimizer.Utils;

public class HardwareSetAlternative {
	private HardwareSet hardwareSet;
	private String id;
	private double time=0;

	private HardwareAlternative cpuAlternative, hddAlternative,
			memoryAlternative, networkAlternative, platformAlternative,
			otherAlternative;

	public HardwareSetAlternative(HardwareSet hardwareSet) {
		this.hardwareSet = hardwareSet;
	}

	public HardwareAlternative getCpuAlternative() {
		return cpuAlternative;
	}

	public void setCpuAlternative(HardwareAlternative cpuAlternative) {
		this.cpuAlternative = cpuAlternative;
	}

	public HardwareAlternative getHddAlternative() {
		return hddAlternative;
	}

	public void setHddAlternative(HardwareAlternative hddAlternative) {
		this.hddAlternative = hddAlternative;
	}

	public HardwareAlternative getMemoryAlternative() {
		return memoryAlternative;
	}

	public void setMemoryAlternative(HardwareAlternative memoryAlternative) {
		this.memoryAlternative = memoryAlternative;
	}

	public HardwareAlternative getNetworkAlternative() {
		return networkAlternative;
	}

	public void setNetworkAlternative(HardwareAlternative networkAlternative) {
		this.networkAlternative = networkAlternative;
	}

	public HardwareAlternative getPlatformAlternative() {
		return platformAlternative;
	}

	public void setPlatformAlternatives(HardwareAlternative platformAlternative) {
		this.platformAlternative = platformAlternative;
	}

	public HardwareAlternative getOtherAlternative() {
		return otherAlternative;
	}

	public void setOtherAlternative(HardwareAlternative otherAlternative) {
		this.otherAlternative = otherAlternative;
	}

	public HardwareSet getHardwareSet() {
		return hardwareSet;
	}

	public String getId() {
		return id;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public void initializeId() {
		this.id = Utils.getHash(cpuAlternative.getHash() + hddAlternative.getHash() + memoryAlternative.getHash() + networkAlternative.getHash() + platformAlternative.getHash() + otherAlternative.getHash());
	}

	public void initializeTime() {
		setTime(0);
	}
	
	public String toString() {
		return hardwareSet.getName() + "= " + cpuAlternative.getName() + " " + hddAlternative.getName() + " " + memoryAlternative.getName() + " " + platformAlternative.getName() + " " + otherAlternative.getName();
	}
}