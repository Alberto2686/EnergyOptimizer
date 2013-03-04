package energyOptimizer.elements;

import java.util.LinkedList;
import java.util.List;

import energyOptimizer.Utils;

public class HardwareSystem {
	private List<HardwareSetAlternative> hardwareSetAlternatives = new LinkedList<>();
	private String id;

	public List<HardwareSetAlternative> getHardwareSetAlternatives() {
		return hardwareSetAlternatives;
	}

	public void setHardwareSetAlternatives(List<HardwareSetAlternative> hardwareSetAlternatives) {
		this.hardwareSetAlternatives = hardwareSetAlternatives;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void initializeId() {
		String string = "";
		for (HardwareSetAlternative hardwareSetAlternative : hardwareSetAlternatives)
			string += hardwareSetAlternative.getId();
		setId(Utils.getHash(string));
	}

	public String toString() {
		String string = "";
		for (HardwareSetAlternative hsaList : hardwareSetAlternatives) {
			string += hsaList.toString() + "";
			string += " - ";
		}
		return string.substring(0, string.length() - 3);
	}
}