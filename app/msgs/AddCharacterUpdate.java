package msgs;

import works.maatwerk.gamelogic.models.Unit;

public class AddCharacterUpdate extends Message {

   private Unit unit;
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

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }




}
