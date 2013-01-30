package energyoptimizer;

import java.util.List;

public class AssociationEnhanced extends ModelElement{
	private Stakeholder stakeholder;
	private FunctionalRequirement functionalRequirement;
	private String stakeholderId, functionalRequirementID;
	private double probability;

	public AssociationEnhanced(Stakeholder stakeholder, FunctionalRequirement functionalRequirement, double probability) {
		this.stakeholder=stakeholder;
		this.functionalRequirement=functionalRequirement;
		this.probability=probability;
	}

	public AssociationEnhanced(String stakeholderId, String functionalRequirementID, double probability) {
		this.stakeholderId = stakeholderId;
		this.functionalRequirementID = functionalRequirementID;
		this.probability = probability;
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
	
	public void bind(List<Stakeholder> stakeholders, List<FunctionalRequirement> functionalRequirements){
		for(Stakeholder sh:stakeholders){
			if(sh.getId().equals(stakeholderId))
				this.stakeholder=sh;
		}
		for(FunctionalRequirement fr:functionalRequirements){
			if(fr.getId().equals(functionalRequirementID)){
				this.functionalRequirement=fr;
				fr.getAssociations().add(this);
			}
		}
	}
	
	@Override
	public String toString(){
		return "From: "+stakeholder.getName()+" to: "+functionalRequirement.getName()+" with probability: "+probability;
	}
}