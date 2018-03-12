package msgs;

public class PlayerPacket extends Packet {
    private String playerId;

    public PlayerPacket(Packet packet, String playerId) {
        super(packet.type, packet.data);
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
}
