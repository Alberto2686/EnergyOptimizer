package energyoptimizer;

import java.util.LinkedList;
import java.util.List;

public class SequenceAlternative {
	private List<Message> messages = new LinkedList<>();
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	@Override
	public String toString(){
		return messages.toString();
	}
}