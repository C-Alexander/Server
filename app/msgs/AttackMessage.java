package msgs;

public class AttackMessage extends Message {
    public int CharacterId;
    public int attackX;
    public int attackY;

    public AttackMessage() {
    }

    public AttackMessage(int characterId, int attackX, int attackY) {
        CharacterId = characterId;
        this.attackX = attackX;
        this.attackY = attackY;
    }

    public int getCharacterId() {
        return CharacterId;
    }

    public void setCharacterId(int characterId) {
        CharacterId = characterId;
    }

    public int getAttackX() {
        return attackX;
    }

    public void setAttackX(int attackX) {
        this.attackX = attackX;
    }

    public int getAttackY() {
        return attackY;
    }

    public void setAttackY(int attackY) {
        this.attackY = attackY;
    }
}
