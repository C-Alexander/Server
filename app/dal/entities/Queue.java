package dal.entities;

import javax.persistence.*;

@Entity
@Table(name = "Queue")
@NamedQueries({
        @NamedQuery(name = "Queue.findAll", query = "select u from Queue as u"),
})

public class Queue {

    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
