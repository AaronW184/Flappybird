import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 360;
        int boardHeight = 640;
        //dimensions for window

        JFrame frame = new JFrame("Flappy Bird");
        //creates a frame with title
        //sets dimension for jframe
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        //centers frame on screen
        frame.setResizable(false);
        //prevent resizing of Jframe window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird); //added the flappybird container to frame
        frame.pack();   //resize the frame as current frame is same dimension as container
        //to make frame exclude the title bar for that dimension
        flappyBird.requestFocus();
        //allow this object to receive keyboard input events
        frame.setVisible(true); 
        
    }
}
