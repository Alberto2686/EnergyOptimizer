package energyoptimizer;

public class Platform extends HardwareComponent {
	private double virtualization,scheduling,framework,jvm,gc,os;
	
	public Platform(String name, String id, double virtualization, double scheduling, double framework, double jvm, double gc, double os) {
		setName(name);
		setId(id);
		this.virtualization = virtualization;
		this.scheduling = scheduling;
		this.framework = framework;
		this.jvm = jvm;
		this.gc = gc;
		this.os = os;
	}


	public double getVirtualization() {
		return virtualization;
	}


	public void setVirtualization(double virtualization) {
		this.virtualization = virtualization;
	}


	public double getScheduling() {
		return scheduling;
	}


	public void setScheduling(double scheduling) {
		this.scheduling = scheduling;
	}


	public double getFramework() {
		return framework;
	}


	public void setFramework(double framework) {
		this.framework = framework;
	}


	public double getJvm() {
		return jvm;
	}


	public void setJvm(double jvm) {
		this.jvm = jvm;
	}


	public double getGc() {
		return gc;
	}


	public void setGc(double gc) {
		this.gc = gc;
	}


	public double getOs() {
		return os;
	}


	public void setOs(double os) {
		this.os = os;
	}


	@Override
	public String toString(){
		return getName()+" virtualization:"+virtualization+" scheduling:"+scheduling+" framework:"+framework+" jvm:"+jvm+" gc:"+gc+" os:"+os;
	}
}