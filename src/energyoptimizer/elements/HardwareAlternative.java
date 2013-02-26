package energyOptimizer.elements;

import java.util.LinkedList;
import java.util.List;

public class HardwareAlternative extends ModelElement {
	private List<HardwareComponent> hardwareComponents = new LinkedList<>();

	public HardwareAlternative(String id, String name) {
		setId(id);
		setName(name);
	}

	public List<HardwareComponent> getHardwareComponents() {
		return hardwareComponents;
	}

	public void setHardwareComponents(List<HardwareComponent> hardwareComponents) {
		this.hardwareComponents = hardwareComponents;
	}

	@Override
	public String toString() {
		return getName() + ": " + hardwareComponents;
	}
}