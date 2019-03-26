package bankmachine.flappyFloof;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("SpellCheckingInspection")
public class Renderer extends JPanel {

    private static final long serialVersionUID = 1L;
    /** The Flappyfloof object that holds the instance of the current game*/
    private FlappyFloof flappyFloof;

    public Renderer(FlappyFloof floof){
        this.flappyFloof = floof;
    }
    /**
     * Paints the JPanel, and our Floof.
     * @param g //TODO: HELP
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        flappyFloof.repainter.repaint(g);
    }
}
