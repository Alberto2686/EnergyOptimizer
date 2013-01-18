package energyoptimizer;

public class AssociationEnhanced {
	private Stakeholder stakeholder;
	private double probability;

	public AssociationEnhanced(Stakeholder stakeholder , double probability) {
		this.stakeholder=stakeholder;
		this.probability=probability;
	}

	public Stakeholder getStakeholder() {
		return stakeholder;
	}

	public void setStakeholder(Stakeholder stakeholder) {
		this.stakeholder = stakeholder;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}
}
