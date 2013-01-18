package energyoptimizer;

public class Behaviours {
	private Behaviour[] behaviourAlternatives;

	public Behaviours(Behaviour[] behaviourAlternatives) {
		this.behaviourAlternatives = behaviourAlternatives;
	}

	public Behaviour[] getBehaviourAlternatives() {
		return behaviourAlternatives;
	}

	public void setBehaviourAlternatives(Behaviour[] behaviourAlternatives) {
		this.behaviourAlternatives = behaviourAlternatives;
	}
}
