package msgs;

public class MoveMessage extends Message {

    int x;
    int y;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    public MoveMessage(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public MoveMessage() {}
}
