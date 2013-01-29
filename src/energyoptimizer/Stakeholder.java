package energyoptimizer;

public class Stakeholder extends LifelineElement{
	private int min, max;
	
	public Stakeholder(String name, String id, int min, int max) {
		super(id,name);
		this.min=min;
		this.max=max;
	}
	public Stakeholder(String name, String id) {
		super(id,name);
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
	public String toString(){
		return getName()+": Min="+min+" Max="+max;
	}
}
