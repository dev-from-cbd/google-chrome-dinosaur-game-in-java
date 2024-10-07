import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class ChromeDinosaur extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 750;
    int boardHeight = 250;

    // Images for game assets
    Image dinosaurImg;
    Image dinosaurDeadImg;
    Image dinosaurJumpImg;
    Image cactus1Img;
    Image cactus2Img;
    Image cactus3Img;

    // Class to represent a game object block (like a dinosaur or cactus)
    class Block {
        int x;
        int y;
        int width;
        int height;
        Image img;

        Block(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }

    // Dinosaur settings
    int dinosaurWidth = 88;
    int dinosaurHeight = 94;
    int dinosaurX = 50;
    int dinosaurY = boardHeight - dinosaurHeight;

    Block dinosaur;

    // Cactus settings
    int cactus1Width = 34;
    int cactus2Width = 69;
    int cactus3Width = 102;

    int cactusHeight = 70;
    int cactusX = 700;
    int cactusY = boardHeight - cactusHeight;
    ArrayList<Block> cactusArray; // ArrayList to hold cacti

    // Game physics settings
    int velocityX = -12; // Cacti moving left speed
    int velocityY = 0;   // Dinosaur jump speed
    int gravity = 1;

    boolean gameOver = false; // Boolean to check if game is over
    int score = 0; // Player score

    Timer gameLoop; // Timer for game loop
    Timer placeCactusTimer; // Timer to add new cacti

    public ChromeDinosaur() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.lightGray); // Set background color of the game board
        setFocusable(true);
        addKeyListener(this); // Add key listener for keyboard inputs

        // Load images for assets
        dinosaurImg = new ImageIcon(getClass().getResource("./img/dino-run.gif")).getImage();
        dinosaurDeadImg = new ImageIcon(getClass().getResource("./img/dino-dead.png")).getImage();
        dinosaurJumpImg = new ImageIcon(getClass().getResource("./img/dino-jump.png")).getImage();
        cactus1Img = new ImageIcon(getClass().getResource("./img/cactus1.png")).getImage();
        cactus2Img = new ImageIcon(getClass().getResource("./img/cactus2.png")).getImage();
        cactus3Img = new ImageIcon(getClass().getResource("./img/cactus3.png")).getImage();

        // Create the dinosaur block
        dinosaur = new Block(dinosaurX, dinosaurY, dinosaurWidth, dinosaurHeight, dinosaurImg);

        // Initialize the cactus array
        cactusArray = new ArrayList<Block>();

        // Game timer for updates (60 frames per second)
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        // Timer to place new cacti every 1.5 seconds
        placeCactusTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeCactus();
            }
        });
        placeCactusTimer.start();
    }

    // Function to place new cacti
    void placeCactus() {
        if (gameOver) {
            return; // Stop placing cacti if game is over
        }

        double placeCactusChance = Math.random(); // Random number to determine which cactus to place
        if (placeCactusChance > 0.90) { // 10% chance to place cactus3
            Block cactus = new Block(cactusX, cactusY, cactus3Width, cactusHeight, cactus3Img);
            cactusArray.add(cactus);
        } else if (placeCactusChance > 0.70) { // 20% chance to place cactus2
            Block cactus = new Block(cactusX, cactusY, cactus2Width, cactusHeight, cactus2Img);
            cactusArray.add(cactus);
        } else if (placeCactusChance > 0.50) { // 20% chance to place cactus1
            Block cactus = new Block(cactusX, cactusY, cactus1Width, cactusHeight, cactus1Img);
            cactusArray.add(cactus);
        }

        // Remove oldest cactus if there are more than 10 in the list
        if (cactusArray.size() > 10) {
            cactusArray.remove(0);
        }
    }

    // Paint component method to draw game elements
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Draw method to render game elements
    public void draw(Graphics g) {
        // Draw the dinosaur
        g.drawImage(dinosaur.img, dinosaur.x, dinosaur.y, dinosaur.width, dinosaur.height, null);

        // Draw the cacti
        for (int i = 0; i < cactusArray.size(); i++) {
            Block cactus = cactusArray.get(i);
            g.drawImage(cactus.img, cactus.x, cactus.y, cactus.width, cactus.height, null);
        }

        // Draw the score
        g.setColor(Color.black);
        g.setFont(new Font("Courier", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf(score), 10, 35);
        } else {
            g.drawString(String.valueOf(score), 10, 35);
        }
    }

    // Move method to update game physics
    public void move() {
        // Update the dinosaur's vertical position (gravity effect)
        velocityY += gravity;
        dinosaur.y += velocityY;

        // Stop the dinosaur from falling below the ground
        if (dinosaur.y > dinosaurY) {
            dinosaur.y = dinosaurY;
            velocityY = 0;
            dinosaur.img = dinosaurImg;
        }

        // Move the cacti to the left
        for (int i = 0; i < cactusArray.size(); i++) {
            Block cactus = cactusArray.get(i);
            cactus.x += velocityX;

            // Check for collision between dinosaur and cactus
            if (collision(dinosaur, cactus)) {
                gameOver = true; // End the game if there's a collision
                dinosaur.img = dinosaurDeadImg;
            }
        }

        // Increase the score as the game progresses
        score++;
    }

    // Collision detection method between two blocks
    boolean collision(Block a, Block b) {
        return a.x < b.x + b.width &&   // a's top left corner doesn't reach b's top right corner
               a.x + a.width > b.x &&   // a's top right corner passes b's top left corner
               a.y < b.y + b.height &&  // a's top left corner doesn't reach b's bottom left corner
               a.y + a.height > b.y;    // a's bottom left corner passes b's top left corner
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move(); // Update game state
        repaint(); // Repaint game elements
        if (gameOver) {
            // Stop the game timers when game over
            placeCactusTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Make the dinosaur jump when space key is pressed
            if (dinosaur.y == dinosaurY) {
                velocityY = -17;
                dinosaur.img = dinosaurJumpImg;
            }

            // Restart the game if it's over
            if (gameOver) {
                dinosaur.y = dinosaurY;
                dinosaur.img = dinosaurImg;
                velocityY = 0;
                cactusArray.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placeCactusTimer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}