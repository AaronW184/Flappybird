import java.awt.*;
import java.awt.event.*;
import java.nio.channels.Pipe;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
  //JPanel is light container within a GUI

  int boardWidth = 360;
  int boardHeight = 640;

  //Images
  Image backgroundImg;
  Image birdImg;
  Image topPipeImg;
  Image bottomPipeImg;

  //Bird
  int birdX = boardWidth/8; 
  int birdY = boardHeight/2;
  int birdWidth = 34;
  int birdHeight = 24;
  //position the bird 1/8 from left-side of screen and 1/2 from the top
  //bird is size 34 * 24 pixels

  class Bird {
    int x = birdX;
    int y = birdY;
    int width = birdWidth;
    int height = birdHeight;
    Image img;

    Bird(Image img){
      this.img = img;
    }
  }
  //Pipes
  //starts from top right of screen
  int pipeX = boardWidth;
  int pipeY = 0;
  int pipeWidth = 64; //scaled by 1/6 original image size
  int pipeHeight = 512;

  class Pipe{
    int x = pipeX;
    int y = pipeY;
    int width = pipeWidth;
    int height = pipeHeight;
    Image img;
    boolean passed = false; //tracks if bird has passed the pipe or not

    Pipe(Image img){
      this.img = img;
    }
  }

  //Game Logic
  Bird bird;
  int velocityX = -4; //move pipes to the left speed (simulate bird moving right)
  int velocityY = 0;
  int gravity = 1;

  ArrayList<Pipe> pipes;
  Random random = new Random();

  Timer gameLoop;
  Timer placePipesTimer;
  //facility for threads to schedule tasks for future execution in a background thread

  boolean gameOver = false;
  double score = 0;

  FlappyBird(){
    setPreferredSize(new Dimension(boardWidth, boardHeight));
    //set the size for this container
    // setBackground(Color.blue);
    setFocusable(true);
    //enable keyboard input focus for FlappyBird component
    addKeyListener(this);
    //adding key listener for this current instance for keyboard input events

    //load images
    backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage(); 
    birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
    topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
    bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
    //get FlappyBird class -> loads resource from path -> retrieves image

    //bird
    this.bird = new Bird(birdImg);
    pipes = new ArrayList<Pipe>();

    //place pipes timer
    placePipesTimer = new Timer(1500, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e){
        placePipes();
      }
    });
    placePipesTimer.start();

    //game timer
    gameLoop = new Timer(1000/60, this); //1s/60 = 60fps, current class handling timer
    gameLoop.start();
  }

  public void placePipes() {
    //0 - 128 - (0-256) how much shift upwards
    int randomPipeY = (pipeY - pipeHeight/4 - random.nextInt(pipeHeight/2));
    int openingSpace = boardHeight / 4;

    Pipe topPipe = new Pipe(topPipeImg);
    topPipe.y = randomPipeY;
    pipes.add(topPipe);

    Pipe bottomPipe = new Pipe(bottomPipeImg);
    bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
    pipes.add(bottomPipe);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g); //calls JPanel's paintComponent method, ensures default painting behaviour 
    // is preserved
    draw(g);
  }

  public void draw(Graphics g){
    //Graphics is abstract class, pass in class that inherits from Graphics
    //background
    g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
    //drawImage is defined in Graphics to draw image onto Graphics object
    //pass in (Image objectToBeDrawn, int x-coordinate where it should be drawn, 
    //          int y-coordinate where it should be draw, ImageObserver observer to be
    //x,y (0,0) top-left                              notifield as more of img is converted)
    g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

    //pipes
    for (int i = 0; i < pipes.size(); i++){
        Pipe pipe = pipes.get(i);
        g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
    }

    //score
    g.setColor(Color.white);
    g.setFont(new Font("Arial", Font.PLAIN, 32));
    if (gameOver) g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
    else g.drawString(String.valueOf((int) score), 10, 35);
  }

  public void move(){
    //bird
    velocityY += gravity;
    bird.y += velocityY;
    bird.y = Math.max(bird.y, 0);

    //pipes
    for (int i = 0; i < pipes.size(); i++){
      Pipe pipe = pipes.get(i);
      pipe.x += velocityX;
      
      if (!pipe.passed && bird.x > pipe.x + pipe.width){
        pipe.passed = true;
        score += 0.5;
      }

      if (collision(bird, pipe)){
        gameOver = true;
      }
    }

    if (bird.y > boardHeight) gameOver = true;
  }

  public boolean collision(Bird a, Pipe b){
    return a.x < b.x + b.width && //a's top left corner doesn't reach b's top right corner
           a.x + a.width > b.x && //a's top right corner passes b's top left corner
           a.y < b.y + b.height && //a's top left corner doesn't reach b's bottom left corner
           a.y +a.height > b.y; //a's bottom left corner passes b's top left corner
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    move();
    repaint(); //repaint method from Component class, JPanel subclass hence inherits
    //repaint calls paintComponent 
    if (gameOver) {
      placePipesTimer.stop();
      gameLoop.stop();
    }
  }
  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      velocityY = -9;
      //if "space" key is pressed, bird jumps up
      if (gameOver) {
        //reset the game conditions
        bird.y = birdY;
        velocityY = 0;
        pipes.clear();
        score = 0;
        gameOver = false;
        gameLoop.start();
        placePipesTimer.start();
      }
    } 
  }
  //NOT USING keyTyped & keyReleased
  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyReleased(KeyEvent e) {}
}
