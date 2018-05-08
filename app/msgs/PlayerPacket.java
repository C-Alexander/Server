package msgs;

public class PlayerPacket extends Packet {
    private int playerId;

    public PlayerPacket(Packet packet, int playerId) {
        super(packet.messageType, packet.data);
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
