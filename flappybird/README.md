1. **Game Initialization:**
   ```java
   FlappyBird() {
       // Initialize game components
       setPreferredSize(new Dimension(boardWidth, boardHeight));
       setFocusable(true);
       addKeyListener(this);

       // Load images
       backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
       birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
       topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
       bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

       // Initialize bird and pipes
       this.bird = new Bird(birdImg);
       pipes = new ArrayList<Pipe>();

       // Start timers
       placePipesTimer.start();
       gameLoop.start();
   }
2. **Game Loop and Movement:**
      ```java
      public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            placePipesTimer.stop();
            gameLoop.stop();
        }
      }
    
      public void move() {
          // Update bird and pipes positions
          velocityY += gravity;
          bird.y += velocityY;
          bird.y = Math.max(bird.y, 0);
      
          for (Pipe pipe : pipes) {
              pipe.x += velocityX;
              if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                  pipe.passed = true;
                  score += 0.5;
              }
              if (collision(bird, pipe)) {
                  gameOver = true;
              }
          }
      
          if (bird.y > boardHeight) gameOver = true;
      }
  3. **Collision Detection:**
     ```java
       public boolean collision(Bird a, Pipe b) {
          return a.x < b.x + b.width &&
                 a.x + a.width > b.x &&
                 a.y < b.y + b.height &&
                 a.y + a.height > b.y;
      }
  4. **Key Press Handling:**
     ```java
       public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_SPACE) {
              velocityY = -9;
              if (gameOver) {
                  // Reset game conditions
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

