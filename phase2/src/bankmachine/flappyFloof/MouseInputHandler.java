package bankmachine.flappyFloof;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("SpellCheckingInspection")
public class MouseInputHandler implements MouseListener {
    /** The Flappyfloof object that holds the instance of the current game*/
    private FlappyFloof floof;

    public MouseInputHandler(FlappyFloof floof){
        this.floof = floof;
    }
    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     */
    @Override
    public void mouseClicked(MouseEvent e){
        floof.jump();
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    @Override
    public void mousePressed(MouseEvent e){

    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    @Override
    public void mouseReleased(MouseEvent e){
    }

    /**
     * Invoked when the mouse enters a component.
     */
    @Override
    public void mouseEntered(MouseEvent e){

    }

    /**
     * Invoked when the mouse exits a component.
     */
    @Override
    public void mouseExited(MouseEvent e){

    }

}
