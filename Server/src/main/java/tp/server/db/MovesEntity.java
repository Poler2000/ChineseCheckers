package tp.server.db;

import javax.persistence.*;

@Entity
@Table(name = "moves", schema = "chinese_checkers_db", catalog = "")
public class MovesEntity {
    private int id;
    private Integer gameId;
    private Integer playerId;
    private Integer pawnId;
    private Integer destX;
    private Integer destY;
    private Integer destZ;
   // private GamesEntity gamesByGameId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "game_id", nullable = true)
    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
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

        MovesEntity entity = (MovesEntity) o;

        if (id != entity.id) return false;
        if (gameId != null ? !gameId.equals(entity.gameId) : entity.gameId != null) return false;
        if (playerId != null ? !playerId.equals(entity.playerId) : entity.playerId != null) return false;
        if (pawnId != null ? !pawnId.equals(entity.pawnId) : entity.pawnId != null) return false;
        if (destX != null ? !destX.equals(entity.destX) : entity.destX != null) return false;
        if (destY != null ? !destY.equals(entity.destY) : entity.destY != null) return false;
        if (destZ != null ? !destZ.equals(entity.destZ) : entity.destZ != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (gameId != null ? gameId.hashCode() : 0);
        result = 31 * result + (playerId != null ? playerId.hashCode() : 0);
        result = 31 * result + (pawnId != null ? pawnId.hashCode() : 0);
        result = 31 * result + (destX != null ? destX.hashCode() : 0);
        result = 31 * result + (destY != null ? destY.hashCode() : 0);
        result = 31 * result + (destZ != null ? destZ.hashCode() : 0);
        return result;
    }

   /* @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    public GamesEntity getGamesByGameId() {
        return gamesByGameId;
    }

    public void setGamesByGameId(GamesEntity gamesByGameId) {
        this.gamesByGameId = gamesByGameId;
    }*/
}
