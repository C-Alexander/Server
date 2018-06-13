package msgs;

public class Packet {
    public MessageType messageType;
    public Message data;

    public Packet(MessageType messageType, Message data) {
        this.messageType = messageType;
        this.data = data;
    }

    public Packet() {

    }
}
