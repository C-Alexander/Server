package msgs;

public class Packet {
    public MessageType type;
    public Message data;

    public Packet(MessageType type, Message data) {
        this.type = type;
        this.data = data;
    }

    public Packet() {

    }
}
