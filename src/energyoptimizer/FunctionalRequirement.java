package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class FunctionalRequirement {
	private String name, id;
	private List<AssociationEnhanced> associations = new LinkedList<>();
	private List<SequenceAlternative> sequenceAlternatives = new LinkedList<>();
	
	public FunctionalRequirement(String name, List<AssociationEnhanced> associations) {
		this.name = name;
		this.associations = associations;
	}
	
	public FunctionalRequirement(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<AssociationEnhanced> getAssociations() {
		return associations;
	}
	public void setAssociations(List<AssociationEnhanced> associations) {
		this.associations = associations;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public List<SequenceAlternative> getSequenceAlternatives() {
		return sequenceAlternatives;
	}

	public void setSequenceAlternatives(List<SequenceAlternative> sequenceAlternatives) {
		this.sequenceAlternatives = sequenceAlternatives;
	}
	
	@Override
	public String toString(){
		String string=" connected to ";
		for(AssociationEnhanced as:associations)
			string+=as.getStakeholder().getName()+" p="+as.getProbability()+" - ";
		return name+string.substring(0, string.length()-3);
	}
}