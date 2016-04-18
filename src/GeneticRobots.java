/*******************************************************************************
 * Compilation: javac GeneticRobots.java
 * Execution:   java GeneticRobots
 *
 * If you have gmake on Linux, a Makefile is provided. There are the command
 * available, being in the root directory of the project.
 * Compilation: make
 * Execution:   make run
 *
 * TODO:
 * -----
 *  - Remove all TODO instructions and useless comments
 *
 * ****************************************************************************/

import java.awt.Color;
import java.util.Collection;
import java.util.LinkedList;
import java.util.HashSet;
import java.awt.event.KeyEvent;
import java.awt.Font;

/**
 * <i>Genetic Robots</i>. This class implements the global part of the game
 * Genetic Robots. It provides all fields necessary for many classes in the
 * game, and implements the main methods of the game.
 *
 * @author Meven Courouble (St. number 20586663)
 */
public final class GeneticRobots
{
  // different Collection on which the same methods will be called at each frame
  private static Collection<IGameObject> objects;
  private static Collection<IGameObject> objAddBuffer;
  private static Collection<IGameObject> objRmBuffer;
  private static Collection<Collider> colliders;
  // a reference to the player
  private static Player player;
  // the current wave of ennemies and bonuses
  private static Wave wave;
  // the ground is out of the IGameObject collections, because it is only
  // rendered
  private static Ground ground;
  private static final Vector center;
  private static boolean isPlaying;
  private static boolean isDead;
  // number of game states since the last frame
  private static long nbStates;
  private static final long framesFrequency;
  private static long startTime;
  private static Chrono chrono;
  /**
   * The horizontal dimension of the canvas.
   */
  public static final int canvasW;
  /**
   * The vertical dimension of the canvas.
   */
  public static final int canvasH;

  // static initializer
  static
  {
    canvasW = 720;
    canvasH = 720;
    framesFrequency = 1000 / 60;
    center = new Vector(0.5, 0.5);
  }

  // init the game for a player with the given pseudo
  private static void init(String pseudo)
  {
    SteveDraw.setFont();
    int defaultSize = SteveDraw.getFont().getSize();
    // font to print the HUD information (time, wave, kills)
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, defaultSize));
    // the game is now playing
    isPlaying = true;
    isDead = false;
    // init the timer
    chrono = new Chrono();
    // init the object collections for the IGameObjects and Colliders
    objects = new HashSet<IGameObject>();
    objAddBuffer = new LinkedList<IGameObject>();
    objRmBuffer = new LinkedList<IGameObject>();
    colliders = new HashSet<Collider>();
    // create the player with the given pseudo
    player = new Player(pseudo);
    ground = new Ground();
    objects.add(player);
    // init the first wave
    wave = null;
    initWave();
  }

  // init a wave depending on the previous one
  private static void initWave()
  {
    // the new wave depends on the previous one (see the Wave.java file for more
    // explanation about the genetic process
    wave = new Wave(wave);
    // add the robots and bonuses to the IGameObject of the game
    for (Robot robot : wave.getRobots())
      objects.add(robot);
    for (Bonus bonus : wave.getBonuses())
      objects.add(bonus);
  }

  /**
   * Get the player.
   */
  public static Player getPlayer()
  {
    return player;
  }

  /**
   * Note a IGameObject be added in the game at the next frame.
   * @param object the object to be added.
   */
  public static void addObject(IGameObject object)
  {
    objAddBuffer.add(object);
  }

  /**
   * Note a IGameObject to be removed from the game at the next frame.
   * @param object the object to be removed.
   */
  public static void rmObject(IGameObject object)
  {
    objRmBuffer.add(object);
  }

  /**
   * Get the collection containing all the active colliders of the game.
   */
  public static Collection<Collider> getColliders()
  {
    return colliders;
  }

  /**
   * Note a collider be added in the game at the next frame.
   * @param collider the collider to be added.
   */
  public static void addCollider(Collider collider)
  {
    colliders.add(collider);
  }

  /**
   * Note a collider to be removed from the game at the next frame.
   * @param collider the collider to be removed.
   */
  public static void rmCollider(Collider collider)
  {
    colliders.remove(collider);
  }

  /**
   * Get a Vector representing the center of the screen.
   */
  public static Vector getCenter()
  {
    return center;
  }

  /**
   * Transform a Vector to a new one relative to the player position. Will be
   * used for rendering functions. The rendering of the game being rendered with
   * a static player in the center.
   * @return the transformed Vector, centered on the player position.
   */
  public static Vector centerOnPlayer(Vector position)
  {
    return position.minus(player.getPosition()).plus(center);
  }
  
  // update all the game properties. This method will be called at each frame
  private static void update()
  {
    chrono.setTime(System.currentTimeMillis() - startTime);
    // update all the IGameObjects
    for (IGameObject obj : objects)
      obj.update(nbStates);
    // add to the game all the waiting objects
    for (IGameObject obj : objAddBuffer)
      objects.add(obj);
    // remove from the game all the waiting objects
    for (IGameObject obj : objRmBuffer)
      objects.remove(obj);
    // empty the two buffers
    objAddBuffer = new LinkedList<IGameObject>();
    objRmBuffer = new LinkedList<IGameObject>();
    // create a new wave when the previous one is finished
    if (wave.noRobots())
      initWave();
  }

  // display the current time played, the current wave and the number of kills
  private static void displayInfo(double x, double y)
  {
    SteveDraw.setPenColor(Color.white);
    SteveDraw.textLeft(x, y, "Time: " + chrono);
    SteveDraw.textLeft(x, y - 0.021, "Wave: " + wave.getNumber());
    SteveDraw.textLeft(x, y - 0.042, "Robots killed: "
        + player.getScore().getRobotsKilled());
  }

  // render all the game elements. This method will be called at each frame
  private static void render()
  {
    // print the background first
    ground.render();
    // render all the objects
    for (IGameObject obj : objects)
      obj.render();
    displayInfo(0.01, 0.98);
    // compute the time before the next frame
    long nextFrame = System.currentTimeMillis() - startTime;
    for(; nextFrame % framesFrequency != 0; nextFrame++)
      continue;
    SteveDraw.show((int)(nextFrame - (System.currentTimeMillis() - startTime)));
  }

  /**
   * Change the game state to end the game at the next frame.
   * @param score score of the player at this moment.
   */
  public static void lose(Score score)
  {
    isDead = true;
    score.setTimePlayed(chrono.getTime());
    isPlaying = false;
  }

  // the game loop, which start and handle the game while the player is in game.
  private static void play(String pseudo)
  {
    init(pseudo);
    nbStates = 0;
    startTime = System.currentTimeMillis();
    long lastFrameTime = startTime;
    while (isPlaying)
    {
      // the player can quit instantaneously with the ESCAPE key
      isPlaying = !(SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE));
      update();
      render();
      nbStates = (System.currentTimeMillis() - lastFrameTime) / framesFrequency;
      lastFrameTime = System.currentTimeMillis();
    }
  }

  // the program main loop, which handle the menu and the call to the game.
  private static void run()
  {
    SteveDraw.setCanvasSize(canvasW, canvasH);
    boolean running = true;
    String pseudo = null;
    while (running)
    {
      // diplay the menu and get the pseudo choosed to play, if so.
      if (running = ((pseudo = Menu.exec()) != null))
      {
        play(pseudo);
        if (isDead)
        {
          Menu.deathScreen(player.getScore());
        }
        while (SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE))
          continue;
      }
    }
  }

  /**
   * Call the run method and handle unexpected exceptions
   */
  public static void main(String[] args)
  {
    try
    {
      run();
    }
    catch (Exception e)
    {
      System.err.println("An unexpected exception ended the game:\n  " + e);
    }
    finally
    {
      // exit the program
      System.exit(0);
    }
  }
}
