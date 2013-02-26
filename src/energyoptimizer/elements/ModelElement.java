package energyOptimizer.elements;

import energyOptimizer.Utils;

public abstract class ModelElement implements NamedElement {
	private String id = "", name = "", idProfile = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(String idProfile) {
		this.idProfile = idProfile;
	}

	public String getHash() {
		return Utils.getHash(id + name + idProfile);
	}
}