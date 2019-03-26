package bankmachine.flappyFloof;

import bankmachine.gui.InputManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInputHandler implements MouseListener {
    /** The Flappyfloof object that holds the instance of the current game*/
    private FlappyFloof floof;
    /** The InputManager (A Jframe) that is used as the GUI for the entire application */
    private InputManager m;

    public MouseInputHandler(FlappyFloof floof, InputManager m){
        this.floof = floof;
        this.m = m;
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
//        if (e.getButton()== MouseEvent.BUTTON2_MASK){
//            m.dispose();
//            System.out.println("Hi");
//            m.mainLoop();
//        }
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
