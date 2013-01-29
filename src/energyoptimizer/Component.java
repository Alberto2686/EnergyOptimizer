package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class Component extends SoftwareComponent {
	private List<AtomicOperation> atomicOperations =new LinkedList<>();
	private List<Connector> connectors = new LinkedList<>();
	
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
	@Override
	public String toString(){
		String string="";
		for(Connector c:connectors)
			string+=c.getName()+"("+(c.isProvided()?"P":"R")+"), ";
		return getName()+" fp:"+getFunctionPoints()+" atomic operations: "+atomicOperations+" connectors: ["+string.substring(0, string.length()-2)+"]";
	}
}