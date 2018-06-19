package game;


import akka.actor.ActorRef;
import msgs.*;
import play.Logger;
import play.libs.Json;
import works.maatwerk.gamelogic.enums.RankName;
import works.maatwerk.gamelogic.enums.Team;
import works.maatwerk.gamelogic.models.*;

import java.util.ArrayList;
import java.util.List;


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
        characterLayer = new Unit[240][120];
        Race human = new Race("Human", new Stats());
        //Eerste hero toevoegen
       Unit hero1 = new Unit(human, new Rank(RankName.HERO), WeaponClass.SPEAR);
       hero1.setId(1);
       characterLayer[15][4] = hero1;
       hero1.setX(15);
       hero1.setY(4);
       hero1.setTeam(Team.TEAMA);
       characterMap.add(hero1);


       //General toevoegen
        Unit general = new Unit(human, new Rank(RankName.GENERAL), WeaponClass.SWORD);
        general.setId(2);
        characterLayer[17][4] = general;
        general.setX(17);
        general.setY(4);
        general.setTeam(Team.TEAMA);
        characterMap.add(general);
        hero1.addMinion(general);


        //General toevoegen
        Unit general3 = new Unit(human, new Rank(RankName.GENERAL), WeaponClass.AXE);
        general3.setId(3);
        characterLayer[13][4] = general3;
        general3.setX(13);
        general3.setY(4);
        general3.setTeam(Team.TEAMA);
        characterMap.add(general3);
        hero1.addMinion(general3);
        hero1.matchStart();
        general.matchStart();
        general3.matchStart();

        List<Unit> grunts = general.getMinions();
        Unit grunt1 = grunts.get(0);
        characterLayer[16][5] = grunt1;
        grunt1.setId(7);
        grunt1.setX(16);
        grunt1.setY(5);
        grunt1.setTeam(Team.TEAMA);
        characterMap.add(grunt1);
        Unit grunt2 = grunts.get(1);
        characterLayer[18][5] = grunt2;
        grunt2.setId(8);
        grunt2.setX(18);
        grunt2.setY(5);
        grunt2.setTeam(Team.TEAMA);
        characterMap.add(grunt2);
        Unit grunt9 = grunts.get(2);
        characterLayer[17][5] = grunt9;
        grunt9.setId(15);
        grunt9.setX(17);
        grunt9.setY(5);
        grunt9.setTeam(Team.TEAMA);
        characterMap.add(grunt9);
        List<Unit> grunts2 = general3.getMinions();

        Unit grunt3 = grunts2.get(0);
        characterLayer[12][5] = grunt3;
        grunt3.setId(9);
        grunt3.setX(12);
        grunt3.setY(5);
        grunt3.setTeam(Team.TEAMA);
        characterMap.add(grunt3);
        Unit grunt4 = grunts2.get(1);
        characterLayer[14][5] = grunt4;
        grunt4.setId(10);
        grunt4.setX(14);
        grunt4.setY(5);
        grunt4.setTeam(Team.TEAMA);
        characterMap.add(grunt4);
        Unit grunt10 = grunts2.get(2);
        characterLayer[13][5] = grunt10;
        grunt10.setId(16);
        grunt10.setX(13);
        grunt10.setY(5);
        grunt10.setTeam(Team.TEAMA);
        characterMap.add(grunt10);
       //Tweede hero toevoegen
       Unit hero2 = new Unit(human, new Rank(RankName.HERO), WeaponClass.SPEAR);
       hero2.setId(4);
       characterLayer[15][27] = hero2;
       hero2.setX(15);
       hero2.setY(27);
       hero2.setTeam(Team.TEAMB);
       characterMap.add(hero2);

        //General toevoegen
        Unit general2 = new Unit(human, new Rank(RankName.GENERAL), WeaponClass.SWORD);
        general2.setId(5);
        characterLayer[17][27] = general2;
        general2.setX(17);
        general2.setY(27);
        general2.setTeam(Team.TEAMB);
        characterMap.add(general2);
        hero2.addMinion(general2);

        //General toevoegen
        Unit general4 = new Unit(human, new Rank(RankName.GENERAL), WeaponClass.AXE);
        general4.setId(6);
        characterLayer[13][27] = general4;
        general4.setX(13);
        general4.setY(27);
        general4.setTeam(Team.TEAMB);
        characterMap.add(general4);
        hero2.addMinion(general4);
        hero2.matchStart();
        general4.matchStart();
        general2.matchStart();

        List<Unit> grunts3 = general2.getMinions();
        Unit grunt5 = grunts3.get(0);
        characterLayer[16][26] = grunt5;
        grunt5.setId(11);
        grunt5.setX(16);
        grunt5.setY(26);
        grunt5.setTeam(Team.TEAMB);
        characterMap.add(grunt5);
        Unit grunt6 = grunts3.get(1);
        characterLayer[18][26] =grunt6;
        grunt6.setId(12);
        grunt6.setX(18);
        grunt6.setY(26);
        grunt6.setTeam(Team.TEAMB);
        characterMap.add(grunt6);
        Unit grunt11 = grunts3.get(2);
        characterLayer[17][26] = grunt11;
        grunt11.setId(17);
        grunt11.setX(17);
        grunt11.setY(26);
        grunt11.setTeam(Team.TEAMB);
        characterMap.add(grunt11);

        List<Unit> grunts4 = general4.getMinions();
        Unit grunt7 = grunts4.get(0);
        characterLayer[12][26] = grunt7;
        grunt7.setId(13);
        grunt7.setX(12);
        grunt7.setY(26);
        grunt7.setTeam(Team.TEAMB);
        characterMap.add(grunt7);
        Unit grunt8 = grunts4.get(1);
        characterLayer[14][26] = grunt8;
        grunt8.setId(14);
        grunt8.setX(14);
        grunt8.setY(26);
        grunt8.setTeam(Team.TEAMB);
        characterMap.add(grunt8);
        Unit grunt12 = grunts4.get(2);
        characterLayer[13][26] = grunt12;
        grunt12.setId(18);
        grunt12.setX(13);
        grunt12.setY(26);
        grunt12.setTeam(Team.TEAMB);
        characterMap.add(grunt12);

        for (int i = 0; i < characterLayer.length; i++) {
            for (int i1 = 0; i1 < characterLayer[i].length; i1++) {
                characterLayer[i][i1] = null;
            }
        }

        for (Unit unit : characterMap) {
            unit.setX(unit.getX() + 105);
            unit.setY(unit.getY() + 45);
            characterLayer[unit.getX()][unit.getY()] = unit;
        }
    }

    public void sendClientInfo(ActorRef actorRef){

        for(Unit ch: characterMap){
            if(ch.getRank().getRankName() == RankName.HERO){
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
