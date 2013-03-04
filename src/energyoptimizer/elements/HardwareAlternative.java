package energyOptimizer.elements;

import java.util.LinkedList;
import java.util.List;

public class HardwareAlternative extends ModelElement implements Comparable {
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

	public int compareTo(Object o) {
		HardwareComponent cheapestThis = hardwareComponents.get(0);
		HardwareComponent cheapestOther = ((HardwareAlternative) o).getHardwareComponents().get(0);
		for (HardwareComponent hardwareComponent : hardwareComponents)
			if (hardwareComponent.getConsumptionIndicator() < cheapestThis.getConsumptionIndicator())
				cheapestThis = hardwareComponent;
		for (HardwareComponent hardwareComponent : ((HardwareAlternative) o).getHardwareComponents())
			if (hardwareComponent.getConsumptionIndicator() < cheapestOther.getConsumptionIndicator())
				cheapestOther = hardwareComponent;
		if (cheapestThis.getConsumptionIndicator() < cheapestOther.getConsumptionIndicator())
			return -1;
		if (cheapestThis.getConsumptionIndicator() > cheapestOther.getConsumptionIndicator())
			return 1;
		return 0;
	}

	public String toString() {
		return getName() + ": " + hardwareComponents;
	}
}