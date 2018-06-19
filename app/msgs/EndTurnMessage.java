package msgs;



public class EndTurnMessage extends Message {

    private boolean endTurn = false;
    private int playerId;


    public boolean getEndTurn() {
        return endTurn;
    }

    public void setEndTurn(boolean endTurn) {
        this.endTurn = endTurn;
    }
}
