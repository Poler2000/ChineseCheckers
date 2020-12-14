package tp.client.graphical;

import java.awt.Graphics;

public class PawnGUI extends Renderable{
    //pawn radius where the hex field has a side length of 1
    private static double pawnRadius = 0.75;

    public PawnGUI(double x, double y){
        setCoords(x,y);
    }

    public boolean checkCollision(double x, double y, double scale, double xoffset, double yoffset){
        return Math.sqrt(Math.pow((x-xoffset-getAdjustedX(scale)),2) + Math.pow((y-yoffset-getAdjustedY(scale)),2)) <= pawnRadius*scale;
    }

    public void render(Graphics gra, double scale, double xoffset, double yoffset){
    	gra.setColor(this.colr);
        gra.fillOval(  (int)(getAdjustedX(scale)+xoffset-pawnRadius*scale), (int)(getAdjustedY(scale)+yoffset-pawnRadius*scale), (int)(2*pawnRadius*scale), (int)(2*pawnRadius*scale) );
    }
}