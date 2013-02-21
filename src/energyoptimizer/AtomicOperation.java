package energyoptimizer;

public class AtomicOperation extends ModelElement {
	private int number;

	public AtomicOperation(String id, String name, int number) {
		setId(id);
		setName(name);
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String toString() {
		return getName() + " id:" + getId() + " number:" + number;
	}
}