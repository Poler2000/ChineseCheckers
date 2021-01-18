package tp.server.db;

import javax.persistence.*;

@Entity
@Table(name = "moves", schema = "chinese_checkers_db", catalog = "")
public class MovesEntity {
    private int id;
    private Integer playerId;
    private Integer pawnId;
    private Integer destX;
    private Integer destY;
    private Integer destZ;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "player_id", nullable = true)
    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    @Basic
    @Column(name = "pawn_id", nullable = true)
    public Integer getPawnId() {
        return pawnId;
    }

    public void setPawnId(Integer pawnId) {
        this.pawnId = pawnId;
    }

    @Basic
    @Column(name = "dest_x", nullable = true)
    public Integer getDestX() {
        return destX;
    }

    public void setDestX(Integer destX) {
        this.destX = destX;
    }

    @Basic
    @Column(name = "dest_y", nullable = true)
    public Integer getDestY() {
        return destY;
    }

    public void setDestY(Integer destY) {
        this.destY = destY;
    }

    @Basic
    @Column(name = "dest_z", nullable = true)
    public Integer getDestZ() {
        return destZ;
    }

    public void setDestZ(Integer destZ) {
        this.destZ = destZ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovesEntity that = (MovesEntity) o;

        if (id != that.id) return false;
        if (playerId != null ? !playerId.equals(that.playerId) : that.playerId != null) return false;
        if (pawnId != null ? !pawnId.equals(that.pawnId) : that.pawnId != null) return false;
        if (destX != null ? !destX.equals(that.destX) : that.destX != null) return false;
        if (destY != null ? !destY.equals(that.destY) : that.destY != null) return false;
        if (destZ != null ? !destZ.equals(that.destZ) : that.destZ != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (playerId != null ? playerId.hashCode() : 0);
        result = 31 * result + (pawnId != null ? pawnId.hashCode() : 0);
        result = 31 * result + (destX != null ? destX.hashCode() : 0);
        result = 31 * result + (destY != null ? destY.hashCode() : 0);
        result = 31 * result + (destZ != null ? destZ.hashCode() : 0);
        return result;
    }
}
