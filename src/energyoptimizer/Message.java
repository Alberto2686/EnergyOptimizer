package energyoptimizer;

public class Message {
	private String id,name, sendEvent,receiveEvent,senderId,receiverId;
	private LifelineElement sender, receiver;
	
	public Message(String id, String name, String sendEvent, String receiveEvent) {
		this.id = id;
		this.name = name;
		this.sendEvent = sendEvent;
		this.receiveEvent = receiveEvent;
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
	public String getSendEvent() {
		return sendEvent;
	}
	public void setSendEvent(String sendEvent) {
		this.sendEvent = sendEvent;
	}
	public String getReceiveEvent() {
		return receiveEvent;
	}
	public void setReceiveEvent(String receiveEvent) {
		this.receiveEvent = receiveEvent;
	}
	public LifelineElement getSender() {
		return sender;
	}
	public void setSender(LifelineElement sender) {
		this.sender = sender;
	}
	public LifelineElement getReceiver() {
		return receiver;
	}
	public void setReceiver(LifelineElement receiver) {
		this.receiver = receiver;
	}
	@Override
	public String toString(){
		return name+" from: "+sender.getName()+" to: "+receiver.getName();
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
}