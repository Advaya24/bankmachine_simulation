package bankmachine.flappyFloof;

import bankmachine.gui.InputManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

/**Note: While almost all of the code within this package itself has been replicated from a YouTube video,
 * The documentation for all the methods and attributes are original and has been done by the members of Group 0336.
 * Thank you!
 *
 * YouTube video: https://www.youtube.com/watch?v=I1qTZaUcFX0
 */
@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class FlappyFloof implements ActionListener{//, MouseListener, KeyListener {

    /**The floof itself*/
    public static FlappyFloof flappyFloof;
    /** Parameters for the dimensions of the JFrame.*/
    public final int WIDTH = 800, HEIGHT = 800;
    /** The Object that helps to render the Jframe itself.*/
    public  Renderer renderer;
    /** A Rectangle Object representing our floof.*/
    public Rectangle floof;
    /** An integer value that is used to see whether the floof needs to start being affected by gravity or not.*/
    public int ticks;
    /** An integer value that is used to determine how fast the floof is falling at any point of time.*/
    public int yMotion;
    /** An integer value that stores the score (how many pipes have been passed through) of the player.*/
    public int score;
    /** A boolean value that is used to determine and handle cases where the game has ended.*/
    public boolean gameOver;
    /** A boolean value that is used to determine whether the Player has started the game or not.*/
    public boolean started;
    /** An ArrayList of Rectangle Objects that stores the obstacles currently within the game.*/
    public ArrayList<Rectangle> columns;
    /** A random object that is used to procedurally generate the obstacles within the game.*/
    public Random rand;
    public InputManager m;
    /**Used to change //TODO*/
    public JFrame jframe;
    public KeyPressHandler keyPressHandler;
    public Repainter repainter;

    public FlappyFloof(InputManager m){
        this.m = m;//TODO: Why do you exist?
        repainter = new Repainter(this);
        renderer = new Renderer();
        rand = new Random();
        keyPressHandler = new KeyPressHandler(this,m);
        jFrameSetUp(m);
        floof = new Rectangle(WIDTH/2-10, HEIGHT/2-10,20,20);
        columns = new ArrayList<>();
        for(int i=0;i<=3;i++){
            addColumn(true);
        }
//        timer.start();
        startGame();
    }
    public void startGame(){
        Timer timer = new Timer(20, this);
        timer.start();
    }

    /**
     * Sets up the JFrame
     */
    public void jFrameSetUp(InputManager m){
        jframe = m;
        jframe.add(renderer);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addMouseListener(keyPressHandler);
        jframe.addKeyListener(keyPressHandler);
//        jframe.setTitle("Flappy Floof");
        jframe.setResizable(false);
        jframe.setVisible(true);
    }

    /**
     * Procedurally generates new obstacles (here referred to as columns) and adds them to the columns ArrayList.
     * @param start whether the Player has started the game or not.
     */
    public void addColumn(boolean start){
        int space = 300;
        int width = 100;
        int height = 50 + rand.nextInt(300);
        if(start)
        {
            columns.add(new Rectangle(WIDTH + width + columns.size() * 300,
                     HEIGHT - height - 120, width, height));
            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300,
                    0, width, HEIGHT - height - space));
        }
        else
        {
            columns.add(new Rectangle(columns.get(columns.size()-1).x + 600, HEIGHT-height-120, width, height));
            columns.add(new Rectangle(columns.get(columns.size()-1).x, 0, width, HEIGHT-height-space));

        }
    }
    /**
     * Whenever the game is updated (as per the ActionListener Interface), this method updates all entities: The Floof,
     * new columns, deleting passed columns, and the like, and then repaints the JFrame.
     * @param e an ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 10;
        ticks++;
        if(started) {
            for (Rectangle column: columns) {
                column.x -= speed;
            }

            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }

            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);
                if (column.x + column.width < 0) {
                    columns.remove(column);
                    if (column.y == 0) {
                        addColumn(false);
                    }
                }
            }

            floof.y += yMotion;

            for (Rectangle column : columns) {
                if(column.y == 0 &&floof.x + floof.width/2 > column.x + column.width/2-5 &&
                        floof.x + floof.width/2 < column.x+column.width/2+5){
                    score++;
                }
                if (column.intersects(floof)) {
                    gameOver = true;
                    if(floof.x <= column.x) {
                        floof.x = column.x - floof.width;
                    }
                    else{
                        if(column.y!=0){
                            floof.y = column.y - floof.height;
                        }
                        else if(floof.y < column.height){
                            floof.y = column.height;
                        }
                    }
                }
            }

            if (floof.y > HEIGHT - 120 || floof.y < 0) {
                floof.y = HEIGHT - 120;
                gameOver = true;
            }

            if(floof.y + yMotion >= HEIGHT-120){
                floof.y = HEIGHT - 120 - floof.height;
            }
        }
        renderer.repaint();
    }

    /**
     * This method is invoked if the Player attempts to jump in-game, and has three functions:
     * a) If the game is over, restarts the game
     * b) If the game hasn't started, starts the game
     * c) If the game has started and is not over, sets the Floof's yMotion attribute in order to move it upwards.
     */
    public void jump(){
        if(gameOver){
            gameOver = false;
            //Reset the game
            floof = new Rectangle(WIDTH/2-10, HEIGHT/2-10,20,20);
            columns = new ArrayList<>();
            yMotion = 0;
            score = 0;
            for(int i=0;i<=3;i++){
                addColumn(true);
            }
        }

        if(!started){
            started = true;
        }
        else if(!gameOver){
            if(yMotion > 0){
                yMotion = 0;
            }
            yMotion -= 15;
        }
    }

    public static void main(String[] args){
        flappyFloof = new FlappyFloof(new InputManager());
    }
}