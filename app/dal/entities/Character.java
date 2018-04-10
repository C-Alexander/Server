package dal.entities;

import javax.persistence.*;

/**
 *
 * @author Sam Dirkx
 */
@SuppressWarnings("WeakerAccess")
@Entity
@Table(name = "Character")
@NamedQueries({
        @NamedQuery(name = "Character.getAll", query = "select c from Character as c"),
        @NamedQuery(name = "Character.findOne", query = "select c from Character as c WHERE c.id = :id")
})
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


}