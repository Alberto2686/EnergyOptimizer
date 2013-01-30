package energyoptimizer;

public class AtomicOperation extends ModelElement{
	private double cost;
	private int number;
	
	public AtomicOperation(String name, double cost, int number) {
		setName(name);
		this.cost = cost;
		this.number = number;
	}
	
	protected double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public String toString(){
		return getName()+" cost:"+cost+" number:"+number;
	}
}