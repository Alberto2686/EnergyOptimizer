package energyoptimizer;

public class Interface extends ModelElement{

	public Interface(String id, String name) {
		setName(name);
		setId(id);
	}

	@Override
	public String toString(){
		return getName();
	}
}