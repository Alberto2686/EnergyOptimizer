package energyoptimizer;

public class Action {
	private Signal[] signals;

	public Action(Signal[] signals) {
		this.signals = signals;
	}

	public Signal[] getSignals() {
		return signals;
	}

	public void setSignals(Signal[] signals) {
		this.signals = signals;
	}
	
}
