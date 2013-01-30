package energyoptimizer;

public class OtherConsumption extends ModelElement {
	private double consumption;
	
	public OtherConsumption(String name, double consumption) {
		setName(name);
		this.consumption = consumption;
	}
	public double getConsumption() {
		return consumption;
	}
	public void setConsumption(double consumption) {
		this.consumption = consumption;
	}
	@Override
	public String toString(){
		return getName()+" "+consumption;
	}
}