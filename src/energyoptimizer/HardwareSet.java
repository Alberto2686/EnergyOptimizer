package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class HardwareSet extends ModelElement{
	private List<HardwareAlternative> cpuAlternatives=new LinkedList<>(), hddAlternatives=new LinkedList<>(), memoryAlternatives=new LinkedList<>(), networkAlternatives=new LinkedList<>(), platformAlternatives=new LinkedList<>(), otherAlternatives=new LinkedList<>();
	private List<HardwareSetAlternative> hardwareSetAlternatives=new LinkedList<>();
	
	public HardwareSet(String id, String name) {
		setName(name);
		setId(id);
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
	public List<HardwareSetAlternative> getHardwareSetAlternatives() {
		return hardwareSetAlternatives;
	}
	public void setHardwareSetAlternatives(
			List<HardwareSetAlternative> hardwareSetAlternatives) {
		this.hardwareSetAlternatives = hardwareSetAlternatives;
	}
	public void generateHardwareSetAlternatives(){  
		generateDummyHardwareAlternatives();
		for(HardwareAlternative cpuAlternative: cpuAlternatives)
			for(HardwareAlternative hddAlternative:hddAlternatives)
				for(HardwareAlternative memoryAlternative:memoryAlternatives)
					for(HardwareAlternative networkAlternative:networkAlternatives) 
						for(HardwareAlternative platformAlternative:platformAlternatives) 
							for(HardwareAlternative otherAlternative:otherAlternatives){
								HardwareSetAlternative hardwareSetAlternative = new HardwareSetAlternative(this);
								hardwareSetAlternative.setCpuAlternative(cpuAlternative);
								hardwareSetAlternative.setHddAlternatives(hddAlternative);
								hardwareSetAlternative.setMemoryAlternative(memoryAlternative);
								hardwareSetAlternative.setNetworkAlternatives(networkAlternative);
								hardwareSetAlternative.setPlatformAlternatives(platformAlternative);
								hardwareSetAlternative.setOtherAlternatives(otherAlternative);
								hardwareSetAlternatives.add(hardwareSetAlternative);
							}
	}
	
	private void generateDummyHardwareAlternatives() {
		if(cpuAlternatives.size()==0)
			cpuAlternatives.add(new HardwareAlternative("", ""));
		if(hddAlternatives.size()==0)
			hddAlternatives.add(new HardwareAlternative("", ""));
		if(memoryAlternatives.size()==0)
			memoryAlternatives.add(new HardwareAlternative("", ""));
		if(networkAlternatives.size()==0)
			networkAlternatives.add(new HardwareAlternative("", ""));
		if(platformAlternatives.size()==0)
			platformAlternatives.add(new HardwareAlternative("", ""));
		if(otherAlternatives.size()==0)
			otherAlternatives.add(new HardwareAlternative("", ""));
	}
	
	@Override
	public String toString(){
		String string="\n\t\t\tCPU:";
		for(HardwareAlternative alt:cpuAlternatives)
			string+="\n\t\t\t\t"+alt.toString();
		string+="\n\t\t\tHDD:";
		for(HardwareAlternative alt:hddAlternatives)
			string+="\n\t\t\t\t"+alt.toString();
		string+="\n\t\t\tMemory:";
		for(HardwareAlternative alt:memoryAlternatives)
			string+="\n\t\t\t\t"+alt.toString();
		string+="\n\t\t\tNetwork device:";
		for(HardwareAlternative alt:networkAlternatives)
			string+="\n\t\t\t\t"+alt.toString();
		string+="\n\t\t\tPlatform:";
		for(HardwareAlternative alt:platformAlternatives)
			string+="\n\t\t\t\t"+alt.toString();
		string+="\n\t\t\tOther:";
		for(HardwareAlternative alt:otherAlternatives)
			string+="\n\t\t\t\t"+alt.toString();
		return getName()+string;
	}
}