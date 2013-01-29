package energyoptimizer;

public class DeployedComponent {
	private Component component;
	private HardwareSet hardwareSet;
	public DeployedComponent(Component component, HardwareSet hardwareSet) {
		this.setComponent(component);
		this.setHardwareSet(hardwareSet);
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
	@Override
	public String toString(){
		return "("+component.getName()+","+hardwareSet.getName()+")";
	}
}