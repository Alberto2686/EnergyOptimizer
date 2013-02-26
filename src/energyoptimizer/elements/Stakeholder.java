package energyOptimizer.elements;

public class Stakeholder extends ModelElement implements LifelineElement {
	private int min, max;

	public Stakeholder(String name, String id, int min, int max) {
		setName(name);
		setId(id);
		this.min = min;
		this.max = max;
	}

	public Stakeholder(String name, String id) {
		setName(name);
		setId(id);
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public String toString() {
		return getName() + ": Min=" + min + " Max=" + max;
	}
}
