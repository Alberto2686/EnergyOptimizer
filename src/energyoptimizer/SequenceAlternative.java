package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class SequenceAlternative {
	private List<Message> messages = new LinkedList<>();
	private List<Component> components = new LinkedList<>();
	private List<DeploymentAlternative> deploymentAlternatives = new LinkedList<>();

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public List<DeploymentAlternative> getDeploymentAlternatives() {
		return deploymentAlternatives;
	}

	public void setDeploymentAlternatives(List<DeploymentAlternative> deploymentAlternatives) {
		this.deploymentAlternatives = deploymentAlternatives;
	}

	public void initializeComponents() {
		for (Message m : messages) {
			addIfNotPresent(m.getReceiver());
			addIfNotPresent(m.getSender());
		}
	}

	private void addIfNotPresent(LifelineElement element) {
		for (Component component : components)
			if (component.getId().equals(element.getId()))
				return;
		try {
			components.add((Component) element);
		} catch (Exception e) {
		}
	}

	@Override
	public String toString() {
		String string = "";
		for (Component c : components)
			string += c.getName() + ", ";
		return "Components: [" + string.substring(0, string.length() - 2) + "] messages: " + messages.toString();
	}
}