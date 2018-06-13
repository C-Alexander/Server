package msgs;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = JoinGameMessage.class),
        @JsonSubTypes.Type(value = MoveMessage.class),
        @JsonSubTypes.Type(value = EndTurnMessage.class),
        @JsonSubTypes.Type(value = AttackMessage.class),
})
public abstract class Message {
}