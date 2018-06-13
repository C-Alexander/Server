package msgs;

public class MoveUpdate extends Message {
    int x;
    int y;
    int charachterId;

    public MoveUpdate(int x, int y, int charachterId) {
        this.x = x;
        this.y = y;
        this.charachterId = charachterId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCharachterId() {
        return charachterId;
    }

    public void setCharachterId(int charachterId) {
        this.charachterId = charachterId;
    }
}
