package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class Component extends SoftwareComponent implements LifelineElement{
	private List<AtomicOperation> atomicOperations = new LinkedList<>();
	private List<AtomicOperationConsumption> atomicOperationConsumptions = new LinkedList<>();
	private List<Connector> connectors = new LinkedList<>();
	private String hardwareSetId;
	private List<HardwareSet> hardwareSets = new LinkedList<>();
	private UsageCPU usageCPU;
	private UsageHDD usageHDD;
	private UsageMemory usageMemory;
	
	public Component(String id, String name) {
		super(id,name);
	}
	public List<AtomicOperation> getAtomicOperations() {
		return atomicOperations;
	}
	public void setAtomicOperations(List<AtomicOperation> atomicOperations) {
		this.atomicOperations = atomicOperations;
	}
	public List<Connector> getConnectors() {
		return connectors;
	}
	public void setConnectors(List<Connector> connectors) {
		this.connectors = connectors;
	}
	public String getHardwareSetId() {
		return hardwareSetId;
	}
	public void setHardwareSetId(String hardwareSetId) {
		this.hardwareSetId = hardwareSetId;
	}
	public List<HardwareSet> getHardwareSets() {
		return hardwareSets;
	}
	public void setHardwareSets(List<HardwareSet> hardwareSets) {
		this.hardwareSets = hardwareSets;
	}
	public UsageCPU getUsageCPU() {
		return usageCPU;
	}
	public void setUsageCPU(UsageCPU usageCPU) {
		this.usageCPU = usageCPU;
	}
	public UsageHDD getUsageHDD() {
		return usageHDD;
	}
	public void setUsageHDD(UsageHDD usageHDD) {
		this.usageHDD = usageHDD;
	}
	public UsageMemory getUsageMemory() {
		return usageMemory;
	}
	public void setUsageMemory(UsageMemory usageMemory) {
		this.usageMemory = usageMemory;
	}
	public List<AtomicOperationConsumption> getAtomicOperationConsumptions() {
		return atomicOperationConsumptions;
	}
	public void setAtomicOperationConsumptions(
			List<AtomicOperationConsumption> atomicOperationConsumptions) {
		this.atomicOperationConsumptions = atomicOperationConsumptions;
	}
	@Override
	public String toString(){
		String string="",string2="";
		for(Connector c:connectors)
			string+=c.getName()+"("+(c.isProvided()?"P":"R")+"), ";
		for(HardwareSet hws:hardwareSets)
			string2+=hws.getName() + " or ";
		return getName()+" atomic operations: "+atomicOperations+" atomic operation consumptions: "+atomicOperationConsumptions+" connectors: ["+string.substring(0, string.length()-2)+"]+ deployable on: "+string2.substring(0, string2.length()-4)+" - Usage: CPU: "+usageCPU+" HDD: "+usageHDD+" Memory: "+usageMemory;
	}
}