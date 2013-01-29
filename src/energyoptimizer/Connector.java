package energyoptimizer;

import java.util.List;

public class Connector extends SoftwareComponent {
	private String clientId, supplierId;
	private boolean isProvided;
	private Component component;
	private Interface toInterface;
	
	public Connector(String id, String name, String clientId,
			String supplierId, int functionPoints, boolean isProvided) {
		super(id,name,functionPoints);
		this.clientId = clientId;
		this.supplierId = supplierId;
		this.isProvided = isProvided;
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
		return (isProvided?"Provided":"Required")+" interface "+getName()+" = component:"+component.getName()+" interface:"+toInterface+" fp:"+getFunctionPoints();
	}
}