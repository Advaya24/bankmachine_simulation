package bankmachine.flappyFloof;

import bankmachine.gui.InputManager;

import java.awt.event.*;

public class KeyPressHandler implements KeyListener {
    /**The Flappyfloof object that holds the instance of the current game*/
    private FlappyFloof floof;
    /** The InputManager (A Jframe) that is used as the GUI for the entire application */
    private InputManager m;
    public KeyPressHandler(FlappyFloof floof, InputManager m){
        this.floof = floof;
        this.m = m;
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     */
    @Override
    public void keyTyped(KeyEvent e){

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     */
    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_E){
            m.dispose();
            m.mainLoop();
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     */
    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            floof.jump();
        }
    }
}
