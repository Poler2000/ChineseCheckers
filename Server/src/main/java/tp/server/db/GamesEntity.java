package tp.server.db;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "games", schema = "chinese_checkers_db", catalog = "")
public class GamesEntity {
    private int id;
    private Integer players;
    private Timestamp start;
    private Collection<MovesEntity> movesById;

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

        GamesEntity entity = (GamesEntity) o;

        if (id != entity.id) return false;
        if (players != null ? !players.equals(entity.players) : entity.players != null) return false;
        if (start != null ? !start.equals(entity.start) : entity.start != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (players != null ? players.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "gamesByGameId")
    public Collection<MovesEntity> getMovesById() {
        return movesById;
    }

    public void setMovesById(Collection<MovesEntity> movesById) {
        this.movesById = movesById;
    }
}
