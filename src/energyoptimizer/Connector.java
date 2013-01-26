package energyoptimizer;

import java.util.List;

public class Connector {
	private String id, name, clientId, supplierId;
	private int functionPoints;
	private boolean isProvided;
	private Component component;
	private Interface toInterface;
	
	public Connector(String id, String name, String clientId,
			String supplierId, int functionPoints, boolean isProvided) {
		super();
		this.id = id;
		this.name = name;
		this.clientId = clientId;
		this.supplierId = supplierId;
		this.functionPoints = functionPoints;
		this.isProvided = isProvided;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public int getFunctionPoints() {
		return functionPoints;
	}

	public void setFunctionPoints(int functionPoints) {
		this.functionPoints = functionPoints;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public Interface getToInterface() {
		return toInterface;
	}

	public void setToInterface(Interface toInterface) {
		this.toInterface = toInterface;
	}
	
	public boolean isProvided() {
		return isProvided;
	}

	public void setProvided(boolean isProvided) {
		this.isProvided = isProvided;
	}
	
	public void bind(List<Interface> interfaces, List<Component> components){
		for(Interface in:interfaces)
			if(supplierId.equals(in.getId()))
				toInterface=in;
		for(Component comp:components)
			if(clientId.equals(comp.getId())){
				component=comp;
				comp.getConnectors().add(this);
			}
	}
	
	@Override
	public String toString(){
		return (isProvided?"Provided":"Required")+" interface "+name+" = component:"+component.getName()+" interface:"+toInterface+" fp:"+functionPoints;
	}
}