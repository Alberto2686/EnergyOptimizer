package energyoptimizer;

public class DeployedComponent {
	private Component component;
	private HardwareSet hardwareSet;
	private String id;
	
	public DeployedComponent(Component component, HardwareSet hardwareSet) {
		this.setComponent(component);
		this.setHardwareSet(hardwareSet);
		id = Utils.getHash(component.getHash()+hardwareSet.getHash());
	}
	public Component getComponent() {
		return component;
	}
	public void setComponent(Component component) {
		this.component = component;
	}
	public HardwareSet getHardwareSet() {
		return hardwareSet;
	}
	public void setHardwareSet(HardwareSet hardwareSet) {
		this.hardwareSet = hardwareSet;
	}
	public String getId() {
		return id;
	}
	
	@Override
	public String toString(){
		return "("+component.getName()+","+hardwareSet.getName()+")";
	}
}