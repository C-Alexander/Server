package game;


import akka.actor.ActorRef;
import msgs.*;
import play.Logger;
import works.maatwerk.gamelogic.models.Unit;
import works.maatwerk.gamelogic.models.*;
import works.maatwerk.gamelogic.enums.*;
import play.libs.Json;


import java.util.ArrayList;


public class GameServer {
    private int gameObjectId = 0;
    private ActorRef gameActor;
    private Unit[][] characterLayer;
    private final ArrayList<Unit> characterMap =  new ArrayList<>();
    private Team turnSwitch = Team.TEAMA;

    public GameServer(ActorRef gameActor) {
        this.gameActor = gameActor;
        startGame();
    }


    public void startGame(){
        characterLayer = new Unit[31][31];


        //Eerste hero toevoegen
       Unit hero1 = new Unit(new Race("Test", new Stats()), new Rank(RankName.HERO), WeaponClass.SWORD);
       hero1.setId(1);
       characterLayer[15][4] = hero1;
       hero1.setX(15);
       hero1.setY(4);
       characterMap.add(hero1);

       //General toevoegen
        Unit general = new Unit(new Race("Test", new Stats()), new Rank(RankName.GENERAL), WeaponClass.SWORD);
        general.setId(3);
        characterLayer[16][4] = general;
        general.setX(16);
        general.setY(4);
        characterMap.add(general);


       //Tweede hero toevoegen
       Unit hero2 = new Unit(new Race("Test", new Stats()), new Rank(RankName.HERO), WeaponClass.SWORD);
       hero2.setId(2);
       characterLayer[15][3] = hero2;
       hero2.setX(15);
       hero2.setY(3);


       characterMap.add(hero2);
    }

    public void sendClientInfo(ActorRef actorRef){

        for(Unit ch: characterMap){
            Logger.debug("Broadcasting CLient Info ");
            Vector2 location = getCharacterLocation(ch.getId());
            Packet packet2 = new Packet();
            packet2.data = new AddCharacterUpdate();
            packet2.messageType = MessageType.ADDCHAR;
            ((AddCharacterUpdate) packet2.data).setUnit(ch);
            ((AddCharacterUpdate) packet2.data).setX(location.x);
            ((AddCharacterUpdate) packet2.data).setY(location.y);
           // gameActor.tell(packet2,ActorRef.noSender());
            actorRef.tell(Json.toJson(packet2).toString(), ActorRef.noSender());
        }
    }
    private int getId(){
        return  gameObjectId++;
    }

    public void moveCharacter(PlayerPacket moveMessage) {

        MoveMessage mm = (MoveMessage) moveMessage.data;
       if( this.moveCharacter(mm.getId(),new Vector2(mm.getX(),mm.getY()))){
           MoveUpdate mu = new MoveUpdate(mm.getX(), mm.getY(), mm.getId());
           Packet packet = new Packet();
           packet.data = mu;
           packet.messageType = MessageType.MOVE;
           gameActor.tell(packet, ActorRef.noSender());
       }
    }


    public  void attackCharacter(PlayerPacket attackMessage){
        AttackMessage am = (AttackMessage) attackMessage.data;
        Unit character = getCharacterById(am.getCharacterId());
        Unit target = characterLayer[am.getAttackX()][am.getAttackY()];
        if(target != null){
            character.attack(target);
            AttackUpdate at = new AttackUpdate();
            at.setCharacterId(am.getCharacterId());
            at.setTargetId(target.getId());
            Packet packet = new Packet();
            packet.data  = at;
            packet.messageType = MessageType.ATTACK;
            gameActor.tell(packet,ActorRef.noSender());
            update();
        }
    }

    public void update() {
        ArrayList<Unit> remove = new ArrayList<>();
        for (Unit c : characterMap) {
            if (!c.alive()) {
              Vector2  location = getCharacterLocation(c.getId());
                characterLayer[(int) location.getX()][(int) location.getY()] = null;
                remove.add(c);
            }
        }
        for (Unit c : remove) {
            characterMap.remove(c);
        }
    }

    public Vector2 getCharacterLocation(int id){
        for (int x = 0; x < this.characterLayer.length ;x++){
            for (int y = 0; y < this.characterLayer[0].length ;y++){
                if(characterLayer[x][y] != null){
                    if(characterLayer[x][y].getId() == id){
                        return new Vector2(x,y);
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param id
     * @return
     */
    public Unit getCharacterById(int id) {
        for (Unit u : characterMap){
            if(u.getId() == id){
                return u;
            }
        }
        return null;
    }

    public boolean moveCharacter(int characterId, Vector2 location) {
        if (location.x > characterLayer.length || location.y > characterLayer[0].length || location.x < 0 || location.y < 0) {

            return false;
        }
        Unit character = null;
        for (Unit c : this.characterMap) {
            if (c.getId() == characterId) {
                character = c;
            }
        }
        if (character != null) {
            removeCharacter(character);
            addCharacter(character,location);
            return true;
        }
        return false;
    }

    public void removeCharacter(Unit character) {
        Vector2 location = getCharacterLocation(character.getId());
        this.characterLayer[(int) location.getX()][(int) location.getY()] = null;
    }

    public void addCharacter(Unit character, Vector2 location) {
        character.setX(location.x);
        character.setY(location.y);
        this.characterLayer[(int) location.getX()][(int) location.getY()] = character;
    }

    public void endTurn(PlayerPacket playerPacket){
        if(this.turnSwitch == Team.TEAMA){
            this.turnSwitch = Team.TEAMB;
        }
        else if(this.turnSwitch == Team.TEAMB){
            this.turnSwitch = Team.TEAMA;
        }
        Packet packet = new Packet();
        packet.messageType = MessageType.ENDTURN;
        EndTurnMessage endTurnMessage = new EndTurnMessage();
        endTurnMessage.setEndTurn(true);
        packet.data = endTurnMessage;
        this.gameActor.tell(packet,ActorRef.noSender());
    }


}
