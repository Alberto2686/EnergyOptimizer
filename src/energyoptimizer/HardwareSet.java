package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class HardwareSet {
	private String id, name;
	private HardwareComponent[] hardwareComponents;//used???
	private List<HardwareAlternative> cpuAlternatives=new LinkedList<>(), hddAlternatives=new LinkedList<>(), memoryAlternatives=new LinkedList<>(), networkAlternatives=new LinkedList<>(), platformAlternatives=new LinkedList<>(), otherAlternatives=new LinkedList<>();
	
	public HardwareSet(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HardwareComponent[] getHardwareComponents() {
		return hardwareComponents;
	}
	public void setHardwareComponents(HardwareComponent[] hardwareComponents) {
		this.hardwareComponents = hardwareComponents;
	}
	public List<HardwareAlternative> getCpuAlternatives() {
		return cpuAlternatives;
	}
	public void setCpuAlternatives(List<HardwareAlternative> cpuAlternatives) {
		this.cpuAlternatives = cpuAlternatives;
	}
	public List<HardwareAlternative> getHddAlternatives() {
		return hddAlternatives;
	}
	public void setHddAlternatives(List<HardwareAlternative> hddAlternatives) {
		this.hddAlternatives = hddAlternatives;
	}
	public List<HardwareAlternative> getMemoryAlternatives() {
		return memoryAlternatives;
	}
	public void setMemoryAlternatives(List<HardwareAlternative> memoryAlternatives) {
		this.memoryAlternatives = memoryAlternatives;
	}
	public List<HardwareAlternative> getNetworkAlternatives() {
		return networkAlternatives;
	}
	public void setNetworkAlternatives(List<HardwareAlternative> networkAlternatives) {
		this.networkAlternatives = networkAlternatives;
	}
	public List<HardwareAlternative> getPlatformAlternatives() {
		return platformAlternatives;
	}
	public void setPlatformAlternatives(
			List<HardwareAlternative> platformAlternatives) {
		this.platformAlternatives = platformAlternatives;
	}
	public List<HardwareAlternative> getOtherAlternatives() {
		return otherAlternatives;
	}
	public void setOtherAlternatives(List<HardwareAlternative> otherAlternatives) {
		this.otherAlternatives = otherAlternatives;
	}
	@Override
	public String toString(){
		return name+":\nCPU="+cpuAlternatives.toString()+"\nHDD="+hddAlternatives.toString()+"\nMemory="+memoryAlternatives.toString()+"\nNetwork="+networkAlternatives.toString()+"\nPlatform="+platformAlternatives.toString()+"\nOther="+otherAlternatives.toString();
	}
}