package msgs;

public class DeleteUpdate extends Message {

    public int characterId;

    public DeleteUpdate() {
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }
}
