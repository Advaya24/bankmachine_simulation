package bankmachine.flappyFloof;

import java.awt.*;

public class Repainter {
    /**
     * Parameters for the dimensions of the JFrame.
     */
    public final int WIDTH = 800, HEIGHT = 800;
    /**
     * A Rectangle Object representing our floof.
     */
    public FlappyFloof flappyFloof;

    public Repainter(FlappyFloof floof) {
        this.flappyFloof = floof;
    }

    public void repaint(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - 120, WIDTH, 120);

        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT - 100, WIDTH, 120);

        g.setColor(Color.red);
        g.fillRect(flappyFloof.floof.x, flappyFloof.floof.y, flappyFloof.floof.width, flappyFloof.floof.height);

        for (Rectangle column : flappyFloof.columns) {
            paintColumn(g, column);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 75));

        if (!flappyFloof.gameOver && !flappyFloof.started) {
            g.drawString("Space to start!", 150, HEIGHT / 2 - 50);
        }

        if (flappyFloof.gameOver) {
            g.drawString("Game Over!", WIDTH / 2 - 150, HEIGHT / 2 - 50);
            g.drawString("'e' to exit", WIDTH / 2 - 150, HEIGHT / 2 + 50);
        }

        if (!flappyFloof.gameOver && flappyFloof.started) {
            g.drawString(String.valueOf(flappyFloof.score), WIDTH / 2 - 25, 100);
        }
    }

    /**
     * Given a Rectangle Object <column>, this method paints it the required color (a rather dark green)
     *
     * @param g      the Graphics context of this instance //TODO: Change this
     * @param column the Rectangle object to be painted
     */
    public void paintColumn(Graphics g, Rectangle column) {
        g.setColor(Color.green.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }
}
