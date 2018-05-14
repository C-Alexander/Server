package game;


import actors.GameActor;
import akka.actor.ActorRef;
import akka.actor.ActorRefFactory;
import akka.actor.dsl.Creators;
import msgs.*;
import play.Logger;
import works.maatwerk.gamelogic.models.Character;
import works.maatwerk.gamelogic.models.*;
import works.maatwerk.gamelogic.enums.*;
import play.libs.Json;


import java.util.ArrayList;


public class GameServer {
    private int gameObjectId = 0;
    private ActorRef gameActor;
    private Character[][] characterLayer;
    private final ArrayList<Character> characterMap =  new ArrayList<>();

    public GameServer(ActorRef gameActor) {
        this.gameActor = gameActor;
        startGame();

    }

    public void startGame(){
        characterLayer = new Character[31][31];
       Character hero1 = new Character(new Race("Test", new Stats()), new Rank(RankName.HERO), WeaponClass.VALKYRIE);
       hero1.setId(getId());
       characterLayer[15][27] = hero1;
       characterMap.add(hero1);


       Character hero2 = new Character(new Race("Test", new Stats()), new Rank(RankName.HERO), WeaponClass.VALKYRIE);
       hero2.setId(getId());
       characterLayer[15][3] = hero2;
       characterMap.add(hero2);




    }

    public void sendClientInfo(ActorRef actorRef){

        for(Character ch: characterMap){
            Logger.debug("Broadcasting CLient Info ");
            Vector2 location = getCharacterLocation(ch.getId());
            Packet packet2 = new Packet();
            packet2.data = new AddCharacterUpdate();
            packet2.messageType = MessageType.ADDCHAR;
            ((AddCharacterUpdate) packet2.data).setCharacter(ch);
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
        Character character = getCharacterById(am.getCharacterId());
        Character target = characterLayer[am.getAttackX()][am.getAttackY()];
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
        ArrayList<Character> remove = new ArrayList<>();
        for (Character c : characterMap) {
            if (!c.isAlive()) {
              Vector2  location = getCharacterLocation(c.getId());
                characterLayer[(int) location.getX()][(int) location.getY()] = null;
                remove.add(c);
            }
        }
        for (Character c : remove) {
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
    public Character getCharacterById(int id) {
        return this.characterMap.get(id);
    }

    public boolean moveCharacter(int characterId, Vector2 location) {
        if (location.x > characterLayer.length || location.y > characterLayer[0].length || location.x < 0 || location.y < 0) {

            return false;
        }
        Character character = null;
        for (Character c : this.characterMap) {
            if (c.getId() == characterId) {
                character = c;
            }
        }
        if (character != null) {
            removeCharacter(character);
            addCharacter(character);
            return true;
        }
        return false;
    }

    public void removeCharacter(Character character) {
        Vector2 location = getCharacterLocation(character.getId());
        this.characterLayer[(int) location.getX()][(int) location.getY()] = null;
        this.characterMap.remove(character);
    }

    public void addCharacter(Character character) {
        Vector2 location = getCharacterLocation(character.getId());
        this.characterMap.add(character);
        this.characterLayer[(int) location.getX()][(int) location.getY()] = character;
    }

}
