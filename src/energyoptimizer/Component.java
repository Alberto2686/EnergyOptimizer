package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class Component extends SoftwareComponent {
	private List<AtomicOperation> atomicOperations =new LinkedList<>();
	private List<Connector> connectors = new LinkedList<>();
	private String hardwareSetId;
	private List<HardwareSet> hardwareSets = new LinkedList<>();
	
	public Component(String id, String name, int functionPoints) {
		super(id,name,functionPoints);
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
	@Override
	public String toString(){
		String string="",string2="";
		for(Connector c:connectors)
			string+=c.getName()+"("+(c.isProvided()?"P":"R")+"), ";
		for(HardwareSet hws:hardwareSets)
			string2+=hws.getName() + " or ";
		return getName()+" fp:"+getFunctionPoints()+" atomic operations: "+atomicOperations+" connectors: ["+string.substring(0, string.length()-2)+"]+ deployable on: "+string2.substring(0, string2.length()-4);
	}
}