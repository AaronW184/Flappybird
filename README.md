# FlappyBird Game

This project is a Java implementation of the classic Flappy Bird game. I followed along a YouTube tutorial for this project as a learning exercise to explore various concepts in Java, including GUI development, event handling, game logic, and animations.

## What I Learned

### Java Concepts

1. **Swing and AWT Libraries:**
   - **JPanel:** Utilized as the main container for the game, which allowed for custom painting and handling of graphics.
   - **JFrame:** Used to create the window for the game.
   - **Graphics:** Leveraged to draw images and text onto the JPanel, enabling the rendering of the game components.

2. **Event Handling:**
   - **ActionListener:** Implemented to handle timer events that drive the game loop and pipe placement.
   - **KeyListener:** Used to handle keyboard inputs, specifically the space bar to control the bird's movement.

3. **Timers:**
   - **javax.swing.Timer:** Used to create a game loop running at 60 frames per second and to periodically place new pipes.

4. **Object-Oriented Programming:**
   - **Classes and Objects:** Created `Bird` and `Pipe` classes to encapsulate the properties and behaviors of the game entities.
   - **Inheritance:** Extended `JPanel` to create a custom game panel that supports custom painting and event handling.

### High-Level Overview

1. **Game Loop:**
   - Implemented a game loop using a timer that updates the game state and repaints the screen at a consistent frame rate.
   - Learned how to manage game state transitions, such as starting, running, and game over states.

2. **Collision Detection:**
   - Developed collision detection logic to determine when the bird collides with pipes or the ground, which triggers the game over state.
   - Gained insights into bounding box collision detection.

3. **Graphics and Animation:**
   - Used images to represent the bird, pipes, and background, creating a visually appealing game.
   - Implemented smooth animations for the bird's movement and the scrolling pipes.

4. **Randomization:**
   - Applied randomization to vary the vertical position of pipes, making the game more challenging and dynamic.

5. **State Management:**
   - Managed game state variables such as the bird's position, velocity, score, and game over flag.
   - Learned how to reset the game state to allow for restarting the game after a game over.

### Running the Game

To run the game, download the flappybird folder, compile and execute the `FlappyBird` class.
