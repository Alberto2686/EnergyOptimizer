package energyoptimizer;

public class HardwareSetAlternative {
	private HardwareSet hardwareSet;
	private HardwareAlternative cpuAlternative, hddAlternatives, memoryAlternative, networkAlternatives, platformAlternatives, otherAlternatives;

	public HardwareSetAlternative(HardwareSet hardwareSet) {
		this.hardwareSet=hardwareSet;
	}

	public HardwareAlternative getCpuAlternative() {
		return cpuAlternative;
	}

	public void setCpuAlternative(HardwareAlternative cpuAlternative) {
		this.cpuAlternative = cpuAlternative;
	}

	public HardwareAlternative getHddAlternatives() {
		return hddAlternatives;
	}

	public void setHddAlternatives(HardwareAlternative hddAlternatives) {
		this.hddAlternatives = hddAlternatives;
	}

	public HardwareAlternative getMemoryAlternative() {
		return memoryAlternative;
	}

	public void setMemoryAlternative(HardwareAlternative memoryAlternative) {
		this.memoryAlternative = memoryAlternative;
	}

	public HardwareAlternative getNetworkAlternatives() {
		return networkAlternatives;
	}

	public void setNetworkAlternatives(HardwareAlternative networkAlternatives) {
		this.networkAlternatives = networkAlternatives;
	}

	public HardwareAlternative getPlatformAlternatives() {
		return platformAlternatives;
	}

	public void setPlatformAlternatives(HardwareAlternative platformAlternatives) {
		this.platformAlternatives = platformAlternatives;
	}

	public HardwareAlternative getOtherAlternatives() {
		return otherAlternatives;
	}

	public void setOtherAlternatives(HardwareAlternative otherAlternatives) {
		this.otherAlternatives = otherAlternatives;
	}
	public HardwareSet getHardwareSet() {
		return hardwareSet;
	}

	@Override
	public String toString(){
		return hardwareSet.getName()+"= "+cpuAlternative.getName()+" "+hddAlternatives.getName()+" "+memoryAlternative.getName()+" "+platformAlternatives.getName()+" "+otherAlternatives.getName();
	}
}