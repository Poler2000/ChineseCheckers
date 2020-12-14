package tp.client.graphical;

import java.awt.Graphics;
import java.awt.Color;

public abstract class Renderable {
    private double xpos;
    private double ypos;
    
    protected Color colr = Color.BLACK;

    protected void setCoords(double x, double y){
        this.xpos = x;
        this.ypos = y;
    }

    protected void setCoords(double x, double y, double scale, double xoffset, double yoffset){
        this.xpos = (x-xoffset)/scale;
        this.ypos = (y-yoffset)/scale;
    }

    protected double getAdjustedX(double scale){
        return xpos*scale;
    }

    protected double getAdjustedY(double scale){
        return ypos*scale;
    }
    
    protected void setColor(Color newcolor) {
    	this.colr = newcolor;
    }

    public abstract void render(Graphics gra, double scale, double xoffset, double yoffset);
}
