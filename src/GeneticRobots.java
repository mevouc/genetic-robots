/*******************************************************************************
 * Compilation: javac GeneticRobots.java
 * Execution:   java GeneticRobots
 *
 * If you have gmake on Linux, a Makefile is provided. There are the command
 * available, being in the root directory of the project.
 * Compilation: make
 * Execution:   make run
 * 
 * This class implemented the global part of the game Genetic Robots. It
 * provides all fields necessary for many classes in the game, and implements
 * the main methods of the game.
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
import java.lang.Thread;

public class GeneticRobots
{
  private static Collection<GameObject> objects;
  private static Collection<GameObject> objAddBuffer;
  private static Collection<GameObject> objRmBuffer;
  private static Collection<ICollider> colliders;
  private static Player player;
  private static Wave wave;
  private static Ground ground;
  private static final Vector center;
  private static boolean isPlaying;
  private static boolean isDead;
  private static long nbStates; // number of game states since the last frame
  private static final long framesFrequency;
  private static long startTime;
  private static Chrono chrono;
  public static final int canvasW;
  public static final int canvasH;

  static
  {
    canvasW = 720;
    canvasH = 720;
    framesFrequency = 1000 / 60;
    center = new Vector(0.5, 0.5);
  }

  private static Vector randomRobotPosition()
  {
    double x = Math.random();
    double y = Math.random();
    double range = 0.1;
    if (x > center.cartesian(0) && x < center.cartesian(0) + range)
      x = center.cartesian(0) + range;
    if (x < center.cartesian(0) && x > center.cartesian(0) - range)
      x = center.cartesian(0) - range;
    if (y > center.cartesian(1) && y < center.cartesian(1) + range)
      y = center.cartesian(1) + range;
    if (y < center.cartesian(1) && y > center.cartesian(1) - range)
      y = center.cartesian(1) - range;
    return new Vector(x, y);
  }

  private static void init(String pseudo)
  {
    SteveDraw.setFont();
    int defaultSize = SteveDraw.getFont().getSize();
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, defaultSize));
    isPlaying = true;
    isDead = false;
    chrono = new Chrono();
    objects = new HashSet<GameObject>();
    objAddBuffer = new LinkedList<GameObject>();
    objRmBuffer = new LinkedList<GameObject>();
    colliders = new HashSet<ICollider>();
    player = new Player(pseudo);
    ground = new Ground();
    objects.add(player);
    wave = null;
    initWave();
  }

  private static void initWave()
  {
    wave = new Wave(wave);
    for (Robot robot : wave.getRobots())
      objects.add(robot);
    for (Bonus bonus : wave.getBonuses())
      objects.add(bonus);
  }

  public static Player getPlayer()
  {
    return player;
  }

  public static void addObject(GameObject object)
  {
    objAddBuffer.add(object);
  }

  public static void rmObject(GameObject object)
  {
    objRmBuffer.add(object);
  }

  public static Collection<ICollider> getColliders()
  {
    return colliders;
  }

  public static void addCollider(ICollider collider)
  {
    colliders.add(collider);
  }

  public static void rmCollider(ICollider collider)
  {
    colliders.remove(collider);
  }

  public static Vector getCenter()
  {
    return center;
  }

  public static Vector centerOnPlayer(Vector position)
  {
    return position.minus(player.getPosition()).plus(center);
  }
    
  private static void update()
  {
    chrono.setTime(System.currentTimeMillis() - startTime);
    for (GameObject obj : objects)
      obj.update(nbStates);
    for (GameObject obj : objAddBuffer)
      objects.add(obj);
    for (GameObject obj : objRmBuffer)
      objects.remove(obj);
    objAddBuffer = new LinkedList<GameObject>();
    objRmBuffer = new LinkedList<GameObject>();
    if (wave.noRobots())
      initWave();
  }

  private static void displayInfo(double x, double y)
  {
    SteveDraw.setPenColor(Color.white);
    SteveDraw.textLeft(x, y, "Time: " + chrono);
    SteveDraw.textLeft(x, y - 0.021, "Wave: " + wave.getNumber());
    SteveDraw.textLeft(x, y - 0.042, "Robots killed: "
        + player.getScore().getRobotsKilled());
  }

  private static void render()
  {
    SteveDraw.clear(Color.white);
    ground.render();
    for (GameObject obj : objects)
      obj.render();
    displayInfo(0.01, 0.98);
    long nextFrame = System.currentTimeMillis() - startTime;
    for(; nextFrame % framesFrequency != 0; nextFrame++)
      continue;
    SteveDraw.show((int)(nextFrame - (System.currentTimeMillis() - startTime)));
  }

  public static void lose(Score score)
  {
    isPlaying = false;
    isDead = true;
    score.setTimePlayed(chrono.getTime());
  }

  private static void play(String pseudo)
  {
    init(pseudo);
    nbStates = 0;
    startTime = System.currentTimeMillis();
    long lastFrameTime = startTime;
    while (isPlaying)
    {
      isPlaying = !(SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE));
      update();
      render();
      nbStates = (System.currentTimeMillis() - lastFrameTime) / framesFrequency;
      lastFrameTime = System.currentTimeMillis();
    }
  }

  private static void run()
  {
    SteveDraw.setCanvasSize(canvasW, canvasH);
    boolean running = true;
    String pseudo = null;
    while (running)
    {
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

  public static void main(String[] args)
  {
    // TODO reput run() in the try catch
      run();
    try
    {
    }
    catch (Exception e)
    {
      System.err.println("An unexpected exception ended the game:\n  " + e);
    }
    finally
    {
      System.exit(0);
    }
  }
}
