package tp.client.graphical;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.Shape;
import java.awt.Color;

/**
 * A class representing a single hexagonal tile
 * in the GUI
 * @author anon
 *
 */
public class FieldGUI extends Renderable{

    private Shape getMyShape(double scale, double xoffset, double yoffset){
        Path2D.Double path = new Path2D.Double(Path2D.Double.WIND_EVEN_ODD);
        path.moveTo(getAdjustedX(scale)+(0.000*scale)+xoffset, getAdjustedY(scale)+(1.000*scale)+yoffset);
        path.lineTo(getAdjustedX(scale)+(0.866*scale)+xoffset, getAdjustedY(scale)+(0.500*scale)+yoffset);
        path.lineTo(getAdjustedX(scale)+(0.866*scale)+xoffset, getAdjustedY(scale)-(0.500*scale)+yoffset);
        path.lineTo(getAdjustedX(scale)+(0.000*scale)+xoffset, getAdjustedY(scale)-(1.000*scale)+yoffset);
        path.lineTo(getAdjustedX(scale)-(0.866*scale)+xoffset, getAdjustedY(scale)-(0.500*scale)+yoffset);
        path.lineTo(getAdjustedX(scale)-(0.866*scale)+xoffset, getAdjustedY(scale)+(0.500*scale)+yoffset);
        path.closePath();
        return path;
    }

    /**
     * Default constructor
     * @param x x coord in the 1 normalized system
     * @param y y coord in the 1 normalized system
     */
    public FieldGUI(double x, double y){
        setCoords(x,y);
    }

    /**
     * Check if point is inside field
     * @param x x coord in the screenspace system (pixels)
     * @param y y coord in the screenspace system
     * @param scale the multiplication factor between screenspace and normalized
     * @param xoffset the screenspace x offset to normalized (0,0 is center, field spans from -1 to 1)
     * @param yoffset the screenspace y offset to normalized
     * @return if collision occurs
     */
    public boolean checkCollision(double x, double y, double scale, double xoffset, double yoffset){
        return getMyShape(scale,xoffset,yoffset).contains(x, y);

    }

    /**
     * Render this tile using given Graphics2D
     * @param gra the graphics to use
     * @param scale the system mapping scale
     * @param xoffset the pixel offset to center
     */
    public void render(Graphics gra, double scale, double xoffset, double yoffset){
    	gra.setColor(this.colr);
        ((Graphics2D)gra).draw(getMyShape(scale,xoffset,yoffset));
    }
}