import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.awt.Color;

/**
 * This class saves all the info about the current wave of robots. It also
 * manages the genetic algorithm part of the game, with the evolution of the
 * robots between each wave.
 */
public class Wave
{
  private final int number;
  // the ref values for generating the robot properties
  private final double defLife;
  private final double defMaxSpeed;
  private final double defDamage;
  private final double defFireFreq;
  private final ArrayList<Robot> robots;
  private final Collection<Bonus> bonuses;
  private static ArrayList<Color> colors;
  // the possible colors for the robots
  private static Color[] colorsArray  = {
      new Color(240, 110, 170),
      new Color(96, 92, 168),
      new Color(23, 147, 209),
      new Color(0, 166, 81),
      new Color(255, 242, 0),
      new Color(237, 28, 36),
      new Color(247, 148, 29) };

  // generate a random position around a given origin
  private static Vector randomPosition(Vector origin)
  {
    double x = Math.random();
    double y = Math.random();
    double range = 0.1;
    double magnitude = (Math.random()+ range) * 2;
    double angle = Math.random() * 360;
    Vector absoluteVector = new Vector(Math.cos(angle), Math.sin(angle));
    absoluteVector = absoluteVector.direction().times(magnitude);
    Vector relativeVector = absoluteVector.plus(origin);
    return relativeVector;
  }

  // give a random number around a given value. The range of randomness is
  // defined by the factor parameter
  private double randomize(double value, double factor)
  {
    double tmp = factor * value;
    return value + Math.random() * tmp - Math.random() * tmp;
  }

  // return the average between 2 values
  private double avg(double a, double b)
  {
    return (a + b) / 2.0;
  }

  // make a number equals to 0 if it is negative
  private double avoidNeg(double n)
  {
    if (n < 0)
      return 0;
    return n;
  }

  /**
   * Construct a new wave of robots, depending on the previous one. It takes the
   * 2 best robots of the previous wave, and use the average between their
   * properties to generate the new wave.
   * @param previous the previous wave to use to create the new one. If equals
   * to null, it create the first wave, which is always the same.
   */
  public Wave(Wave previous)
  {
    this.colors = new ArrayList<Color>(Arrays.asList(this.colorsArray));
    this.bonuses = new ArrayList<Bonus>();
    this.robots = new ArrayList<Robot>();
    if (previous == null)
    {
      // default values for the first wave
      this.number = 1;
      this.defLife = 10;
      this.defMaxSpeed = 0.005;
      this.defDamage = 1;
      this.defFireFreq = 0.002;
    }
    else
    {
      this.number = previous.number + 1;
      // get the 2 best robots
      Collections.sort(previous.robots);
      Robot best1 = previous.robots.get(0);
      Robot best2 = previous.robots.get(1);
      // use the average of their properties to define new reference values
      this.defLife = avg(best1.getMaxLife(), best2.getMaxLife());
      this.defMaxSpeed = avg(best1.getMaxSpeed(), best2.getMaxSpeed());
      this.defDamage = avg(best1.getDamage(), best2.getDamage());
      this.defFireFreq = avg(best1.getFireFreq(), best2.getFireFreq());
    }
    // factors of randomness
    double factorLife = 0.75;
    double factorMaxSpeed = 0.75;
    double factorDamage = 0.75;
    double factorFireFreq = 0.75;
    // create the 7 robots of the new wave
    for (int i = 0; i < 7; i++)
    {
      double currLife = avoidNeg(randomize(defLife, factorLife));
      double currMaxSpeed = avoidNeg(randomize(defMaxSpeed, factorMaxSpeed));
      double currDamage = avoidNeg(randomize(defDamage, factorMaxSpeed));
      double currFireFreq = avoidNeg(randomize(defFireFreq, factorFireFreq));
      // give to the current robot a random color
      Collections.shuffle(this.colors);
      Color currColor = this.colors.get(0);
      this.colors.remove(0);
      Vector randPos = randomPosition(GeneticRobots.getPlayer().getPosition());
      this.robots.add(new Robot(randPos, 0.00025, currMaxSpeed, currDamage,
            currLife, currFireFreq, currColor));
    }
    // create 1 or 2 bonuses
    for (int i = 0; i < 1 + (int)(Math.random() + 0.1); i++)
    {
      Vector randPos = randomPosition(GeneticRobots.getPlayer().getPosition());
      this.bonuses.add(new Bonus(randPos.times(2), 5, 0.02, 10));
    }
  }

  /**
   * Get the number of this wave.
   */
  public int getNumber()
  {
    return this.number;
  }

  /**
   * Get the list of all the robots in this wave.
   */
  public Collection<Robot> getRobots()
  {
    return this.robots;
  }

  /**
   * Get the list of all the bonuses in this wave.
   */
  public Collection<Bonus> getBonuses()
  {
    return this.bonuses;
  }
  
  /**
   * Tell if there are some alive robots left or not.
   * @return true if all the robots of the current wave are dead, false
   * otherwise.
   */
  public boolean noRobots()
  {
    int i = 0;
    for (; i < this.robots.size() && this.robots.get(i).isDead(); i++)
      continue;
    return (i == this.robots.size());
  }
}
