package energyoptimizer;

public class OtherConsumption {
	private String name;
	private double consumption;
	public OtherConsumption(String name, double consumption) {
		super();
		this.name = name;
		this.consumption = consumption;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getConsumption() {
		return consumption;
	}
	public void setConsumption(double consumption) {
		this.consumption = consumption;
	}
	@Override
	public String toString(){
		return name+" "+consumption;
	}
}