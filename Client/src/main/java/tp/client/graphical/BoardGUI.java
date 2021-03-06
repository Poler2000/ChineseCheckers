package tp.client.graphical;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.*;

/**
 * A class representing a playing field
 * Draws all FieldGUI and PawnGUI instances in itself
 * and handles dragging
 * @author anon
 *
 */
public class BoardGUI extends JPanel{
    private FieldGUI fields[];
    private volatile PawnGUI pawns[] = new PawnGUI[0];

    private int fieldSize = 2;

    /**
     * The default constructor
     * @param fields the map to use
     * @param diam the radius in fields to display (zoom level)
     */
    public BoardGUI(FieldGUI fields[], int diam){
        super();
        this.fields = fields;
        this.fieldSize = diam;
        setupListeners();

    }

    /**
     * Update displayed pawns
     * @param state new pawns state
     */
    public void setPawns(PawnGUI[] state){
        this.pawns = state;
        repaint();
    }

    private double getScale(){
        int mindim = getWidth();
        mindim = mindim > getHeight() ? getHeight() : mindim;
        return (mindim/fieldSize)*0.4;
    }

    /**
     * The main Jcomponent drawing method
     */
    @Override
    protected void paintComponent(Graphics gra){
        super.paintComponent(gra);

        for (FieldGUI elem : fields){
            elem.render(gra,getScale(),getWidth()/2,getHeight()/2);
        }
        for (PawnGUI elem : pawns){
            elem.render(gra, getScale(),getWidth()/2,getHeight()/2);
        }

    }


    private PawnMovementHandler moveHandlere;

    /**
     * Set the object to call back on pawn movement
     * (handlePawnMovement)
     * @param handler
     */
    public void setMovementHandler(PawnMovementHandler handler){
        moveHandlere = handler;
    }

    private PawnMovementHandler getMovementHandler(){
        return moveHandlere;
    }

    private PawnGUI[] getPawns(){
        return pawns;
    }

    private PawnGUI draggedPawn;

    private PawnGUI getDraggedPiece(){
        return draggedPawn;
    }

    ///Enable picking up pawns (or lock them from the user)
    public volatile boolean pickUpDisabled = false;

    private void setupListeners(){
        addMouseListener(new MouseAdapter() {
            private double originalPawnX;
            private double originalPawnY;

            @Override
            public void mousePressed(MouseEvent ev){
                if (!pickUpDisabled){
                    for (PawnGUI target : getPawns()){
                        if(!target.untouchable && target.checkCollision(ev.getX(), ev.getY(), getScale(), getWidth()/2, getHeight()/2)){
                            draggedPawn = target;
                            originalPawnX = target.getAdjustedX(1);
                            originalPawnY = target.getAdjustedY(1);
                        }
                    }
                }
            }
            public void mouseReleased(MouseEvent ev){
                if (getDraggedPiece() != null){
                	boolean foundDest = false;
                    for (FieldGUI target : fields){
                        if (target.checkCollision(ev.getX(), ev.getY(), getScale(), getWidth()/2, getHeight()/2)){
                        	foundDest = true;
                            if (getMovementHandler() != null){
                                if (getMovementHandler().handlePawnMovement(getDraggedPiece(), target)){
                                    getDraggedPiece().setCoords(target.getAdjustedX(1), target.getAdjustedY(1));
                                }
                                else{
                                    getDraggedPiece().setCoords(originalPawnX, originalPawnY);
                                }
                                break;
                            }
                        }
                    }
                    if (!foundDest) {
                    	getDraggedPiece().setCoords(originalPawnX, originalPawnY);
                    }
                    draggedPawn = null;
                    repaint();
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent ev){
                if (getDraggedPiece() != null){
                    getDraggedPiece().setCoords(ev.getX(), ev.getY(), getScale(), getWidth()/2, getHeight()/2);
                    repaint();
                }
            }
        });
    }


}