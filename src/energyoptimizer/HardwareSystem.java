package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class HardwareSystem {
	private List<HardwareSetAlternative> hardwareSetAlternatives=new LinkedList<>();

	public List<HardwareSetAlternative> getHardwareSetAlternatives() {
		return hardwareSetAlternatives;
	}
	public void setHardwareSetAlternatives(List<HardwareSetAlternative> hardwareSetAlternatives) {
		this.hardwareSetAlternatives = hardwareSetAlternatives;
	}
	@Override
	public String toString(){
		String string="";
		for(HardwareSetAlternative hsaList:hardwareSetAlternatives) {
			string+=hsaList.toString()+ "";
		string+=" - ";
		}
		return string.substring(0,string.length()-3);
	}
}