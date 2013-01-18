package energyoptimizer;

public class Signal {
	private Action toAction;
	private Size size;

	public Signal(Action toAction, Size size) {
		this.toAction = toAction;
		this.size = size;
	}
	
	public Action getToAction() {
		return toAction;
	}

	public void setToAction(Action toAction) {
		this.toAction = toAction;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}
	
}
