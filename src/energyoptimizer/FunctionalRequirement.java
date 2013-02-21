package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class FunctionalRequirement extends ModelElement {
	private List<AssociationEnhanced> associations = new LinkedList<>();
	private List<SequenceAlternative> sequenceAlternatives = new LinkedList<>();
	private double coefficient;

	public FunctionalRequirement(String name, List<AssociationEnhanced> associations) {
		setName(name);
		this.associations = associations;
	}

	public FunctionalRequirement(String name, String id) {
		setName(name);
		setId(id);
	}

	public List<AssociationEnhanced> getAssociations() {
		return associations;
	}

	public void setAssociations(List<AssociationEnhanced> associations) {
		this.associations = associations;
	}

	public List<SequenceAlternative> getSequenceAlternatives() {
		return sequenceAlternatives;
	}

	public void setSequenceAlternatives(List<SequenceAlternative> sequenceAlternatives) {
		this.sequenceAlternatives = sequenceAlternatives;
	}

	public double getCoefficient() {
		return coefficient;
	}

	public void calculateCoefficient() {
		for (AssociationEnhanced association : associations)
			coefficient += ((association.getStakeholder().getMax() + association.getStakeholder().getMin()) / 2) * association.getProbability();
	}

	@Override
	public String toString() {
		String string = " connected to ";
		for (AssociationEnhanced as : associations)
			string += as.getStakeholder().getName() + " p=" + as.getProbability() + " - ";
		return getName() + " coeff.=" + coefficient + string.substring(0, string.length() - 3);
	}
}