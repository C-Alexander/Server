package msgs;

import works.maatwerk.gamelogic.models.Character;

public class AddCharacterUpdate extends Message {

   private Character character;
   private int y;
   private int x;

   public AddCharacterUpdate() {
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }




}
