package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class Component {
	private String id,name;
	private int functionPoints;
	private List<AtomicOperation> atomicOperations =new LinkedList<>();
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
	@Override
	public String toString(){
		return name+" fp:"+functionPoints+" atomic operations: "+atomicOperations;
	}
}