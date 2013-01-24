package energyoptimizer;

public class Stakeholder {
	private String name, id;
	private int min, max;
	
	public Stakeholder(String name, String id, int min, int max) {
		this.name=name;
		this.id=id;
		this.min=min;
		this.max=max;
	}
	public Stakeholder(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
		return name+": Min="+min+" Max="+max;
	}
}
