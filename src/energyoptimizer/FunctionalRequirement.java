package energyoptimizer;

public class FunctionalRequirement {
	private String ID, description;
	private AssociationEnhanced[] associations;
	
	public FunctionalRequirement(String ID, String description, AssociationEnhanced[] associations){
		this.ID=ID;
		this.description=description;
		this.associations=associations;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public AssociationEnhanced[] getAssociations() {
		return associations;
	}
	public void setAssociations(AssociationEnhanced[] associations) {
		this.associations = associations;
	}
}
