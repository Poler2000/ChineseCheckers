package tp.client.graphical;

import java.awt.Graphics;

/**
 * A class representing a player pawn in GUI
 * @author anon
 *
 */
public class PawnGUI extends Renderable{
    //pawn radius where the hex field has a side length of 1
    private static double pawnRadius = 0.75;
    ///If this pawn can be moved (set it to true if it belongs to other player)
    public boolean untouchable = false;

    /**
     * The default constructor
     * @param x x in normalized space (field spans -1 to 1)
     * @param y y in normalized system
     */
    public PawnGUI(double x, double y){
        setCoords(x,y);
    }

    /**
     * Check if point lies in the pawn
     * @param x x in pixel space
     * @param y y in pixel space
     * @param scale scale between normalized and screenspace systems
     * @param xoffset x pixel offset to the screens center
     * @param yoffset y pixel offset to the screens center
     * @return
     */
    public boolean checkCollision(double x, double y, double scale, double xoffset, double yoffset){
        return Math.sqrt(Math.pow((x-xoffset-getAdjustedX(scale)),2) + Math.pow((y-yoffset-getAdjustedY(scale)),2)) <= pawnRadius*scale;
    }

    /**
     * Render this pawn using given Graphics
     * and screenspace params
     */
    public void render(Graphics gra, double scale, double xoffset, double yoffset){
    	gra.setColor(this.colr);
        gra.fillOval(  (int)(getAdjustedX(scale)+xoffset-pawnRadius*scale), (int)(getAdjustedY(scale)+yoffset-pawnRadius*scale), (int)(2*pawnRadius*scale), (int)(2*pawnRadius*scale) );
    }
}