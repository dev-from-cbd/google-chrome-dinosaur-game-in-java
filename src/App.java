import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 750; // Width of the game window
        int boardHeight = 250; // Height of the game window

        // Create a new JFrame for the game window
        JFrame frame = new JFrame("Chrome Dinosaur");
        
        // Set the size of the window
        frame.setSize(boardWidth, boardHeight);
        
        // Center the window on the screen
        frame.setLocationRelativeTo(null);
        
        // Prevent the window from being resizable
        frame.setResizable(false);
        
        // Set the default close operation to exit the application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an instance of ChromeDinosaur (the game panel)
        ChromeDinosaur chromeDinosaur = new ChromeDinosaur();
        
        // Add the ChromeDinosaur component to the frame
        frame.add(chromeDinosaur);
        
        // Adjust the frame size based on the content
        frame.pack();
        
        // Request focus for the game component
        chromeDinosaur.requestFocus();
        
        // Make the window visible
        frame.setVisible(true);
    }
}
