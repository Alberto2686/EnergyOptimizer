package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class Cpu extends HardwareComponent {
	private int cores, productive_process;
	private double tdp;
	private List<FrequencyVoltage> frequenciesVoltages=new LinkedList<>();
	
	public Cpu(String name, String id, int cores, int productive_process, double tdp) {
		setName(name);
		setId(id);
		this.cores = cores;
		this.productive_process = productive_process;
		this.tdp = tdp;
	}
	public int getCores() {
		return cores;
	}
	public void setCores(int cores) {
		this.cores = cores;
	}
	public int getProductive_process() {
		return productive_process;
	}
	public void setProductive_process(int productive_process) {
		this.productive_process = productive_process;
	}
	public double getTdp() {
		return tdp;
	}
	public void setTdp(double tdp) {
		this.tdp = tdp;
	}
	public List<FrequencyVoltage> getFrequenciesVoltages() {
		return frequenciesVoltages;
	}
	public void setFrequenciesVoltages(List<FrequencyVoltage> frequenciesVoltages) {
		this.frequenciesVoltages = frequenciesVoltages;
	}
	@Override
	public String toString(){
		return getName()+" cores:"+cores+" nm:"+productive_process+" TDP:"+tdp+" f/v: "+frequenciesVoltages;
	}
}