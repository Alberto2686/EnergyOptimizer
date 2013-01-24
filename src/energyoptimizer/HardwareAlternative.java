package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class HardwareAlternative {
	private String id, name;
	private List<HardwareComponent> hardwareComponents=new LinkedList<>();
	public HardwareAlternative(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<HardwareComponent> getHardwareComponents() {
		return hardwareComponents;
	}
	public void setHardwareComponents(List<HardwareComponent> hardwareComponents) {
		this.hardwareComponents = hardwareComponents;
	}
	@Override
	public String toString(){
		String string="";
		for(HardwareComponent hc:hardwareComponents)
			string+=hc.toString();
		return name+": "+string;
	}
}