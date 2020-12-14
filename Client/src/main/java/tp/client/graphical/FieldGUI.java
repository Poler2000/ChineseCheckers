package tp.client.graphical;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.Shape;

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

    public FieldGUI(double x, double y){
        setCoords(x,y);
    }

    public boolean checkCollision(double x, double y, double scale, double xoffset, double yoffset){
        return getMyShape(scale,xoffset,yoffset).contains(x, y);

    }

    public void render(Graphics gra, double scale, double xoffset, double yoffset){
        ((Graphics2D)gra).draw(getMyShape(scale,xoffset,yoffset));
    }
}