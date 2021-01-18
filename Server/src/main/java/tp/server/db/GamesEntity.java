package tp.server.db;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "games", schema = "chinese_checkers_db", catalog = "")
public class GamesEntity {
    private int id;
    private Integer players;
    private Timestamp start;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "players", nullable = true)
    public Integer getPlayers() {
        return players;
    }

    public void setPlayers(Integer players) {
        this.players = players;
    }

    @Basic
    @Column(name = "start", nullable = true)
    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GamesEntity that = (GamesEntity) o;

        if (id != that.id) return false;
        if (players != null ? !players.equals(that.players) : that.players != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (players != null ? players.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        return result;
    }
}
