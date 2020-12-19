package tp.client.graphical;

import java.awt.Graphics;
import java.awt.Color;
/**
 * A general parent class for game elements
 * rendered on the board
 * @author anon
 *
 */
public abstract class Renderable {
    private double xpos;
    private double ypos;
    ///Color to draw in
    protected Color colr = Color.BLACK;
    /**
     * Set coords in normalized system
     * @param x
     * @param y
     */
    protected void setCoords(double x, double y){
        this.xpos = x;
        this.ypos = y;
    }
    /**
     * Set coords, converting them from screenspace pixel system
     * @param x
     * @param y
     * @param scale scale between systems
     * @param xoffset offset in pixels to the normalized center
     * @param yoffset offset in pixels to the normalized center
     */
    protected void setCoords(double x, double y, double scale, double xoffset, double yoffset){
        this.xpos = (x-xoffset)/scale;
        this.ypos = (y-yoffset)/scale;
    }

    /**
     * x coord * scale
     * @param scale
     * @return x coord * scale
     */
    protected double getAdjustedX(double scale){
        return xpos*scale;
    }

    /**
     * y coord * scale
     * @param scale
     * @return y coord * scale
     */
    protected double getAdjustedY(double scale){
        return ypos*scale;
    }
    
    /**
     * Set color
     * @param newcolor new color
     */
    protected void setColor(Color newcolor) {
    	this.colr = newcolor;
    }

    ///Render this element on screen using graphics
    public abstract void render(Graphics gra, double scale, double xoffset, double yoffset);
}
