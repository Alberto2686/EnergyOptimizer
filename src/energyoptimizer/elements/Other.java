package energyOptimizer.elements;

import java.util.LinkedList;
import java.util.List;

public class Other extends HardwareComponent {
	private double busses, sensors, cooling, peripheralDevices, display, ups;
	private int energyPoints;
	private List<OtherConsumption> otherConsumption = new LinkedList<>();

	public Other(String name, String id, double busses, double sensors, double cooling, double peripheralDevices, double display, double ups, int ep) {
		setName(name);
		setId(id);
		this.busses = busses;
		this.sensors = sensors;
		this.cooling = cooling;
		this.peripheralDevices = peripheralDevices;
		this.display = display;
		this.ups = ups;
		this.setEnergyPoints(ep);
	}

	public double getBusses() {
		return busses;
	}

	public void setBusses(double busses) {
		this.busses = busses;
	}

	public double getSensors() {
		return sensors;
	}

	public void setSensors(double sensors) {
		this.sensors = sensors;
	}

	public double getCooling() {
		return cooling;
	}

	public void setCooling(double cooling) {
		this.cooling = cooling;
	}

	public double getPeripheralDevices() {
		return peripheralDevices;
	}

	public void setPeripheralDevices(double peripheralDevices) {
		this.peripheralDevices = peripheralDevices;
	}

	public double getDisplay() {
		return display;
	}

	public void setDisplay(double display) {
		this.display = display;
	}

	public double getUps() {
		return ups;
	}

	public void setUps(double ups) {
		this.ups = ups;
	}

	public List<OtherConsumption> getOtherConsumption() {
		return otherConsumption;
	}

	public void setOtherConsumption(List<OtherConsumption> otherConsumption) {
		this.otherConsumption = otherConsumption;
	}

	public int getEnergyPoints() {
		return energyPoints;
	}

	public void setEnergyPoints(int energyPoints) {
		this.energyPoints = energyPoints;
	}

	public String toString() {
		return getName() + " energy points:" + energyPoints + "EP, busses:" + busses + ", sensors:" + sensors + ", cooling:" + cooling + ", peripheral devices:" + peripheralDevices + ", display:" + display + ", ups:" + ups + ", others:" + otherConsumption;
	}
}