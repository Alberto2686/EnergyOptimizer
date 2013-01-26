package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class Component {
	private String id,name;
	private int functionPoints;
	private List<AtomicOperation> atomicOperations =new LinkedList<>();
	private List<Connector> connectors = new LinkedList<>();
	
	public Component(String id, String name, int functionPoints) {
		super();
		this.id = id;
		this.name = name;
		this.functionPoints = functionPoints;
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
	public int getFunctionPoints() {
		return functionPoints;
	}
	public void setFunctionPoints(int functionPoints) {
		this.functionPoints = functionPoints;
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
		return name+" fp:"+functionPoints+" atomic operations: "+atomicOperations+" connectors: ["+string.substring(0, string.length()-2)+"]";
	}
}