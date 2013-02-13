package energyoptimizer;

public class HardwareSetAlternative {
	private HardwareSet hardwareSet;
	private HardwareAlternative cpuAlternative, hddAlternative, memoryAlternative, networkAlternative, platformAlternative, otherAlternative;

	public HardwareSetAlternative(HardwareSet hardwareSet) {
		this.hardwareSet=hardwareSet;
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

	@Override
	public String toString(){
		return hardwareSet.getName()+"= "+cpuAlternative.getName()+" "+hddAlternative.getName()+" "+memoryAlternative.getName()+" "+platformAlternative.getName()+" "+otherAlternative.getName();
	}
}