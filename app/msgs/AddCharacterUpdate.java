package msgs;

public class AddCharacterUpdate extends Message {
    Character character;
    public AddCharacterUpdate() {
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
