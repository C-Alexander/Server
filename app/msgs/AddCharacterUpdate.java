package msgs;

public class AddCharacterUpdate extends Message {

    public AddCharacterUpdate() {
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    Character character;


}
