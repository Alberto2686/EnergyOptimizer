package energyoptimizer;

public class Stakeholder {
	private String ID;
	private int min, max;
	
	public Stakeholder(String ID, int min, int max) {
		this.ID=ID;
		this.min=min;
		this.max=max;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
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
}
