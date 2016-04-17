import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.awt.Color;

public class Wave
{
  private final int number;
  private final double defLife;
  private final double defMaxSpeed;
  private final double defDamage;
  private final double defFireFreq;
  private final ArrayList<Robot> robots;
  private final Collection<Bonus> bonuses;
  private static ArrayList<Color> colors;
  private static Color[] colorsArray  = { new Color(240, 110, 170),
                                          new Color(96, 92, 168),
                                          new Color(23, 147, 209),
                                          new Color(0, 166, 81),
                                          new Color(255, 242, 0),
                                          new Color(237, 28, 36),
                                          new Color(247, 148, 29) };

  // random position around origin
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

  private double randomize(double value, double factor)
  {
    double tmp = factor * value;
    return value + Math.random() * tmp - Math.random() * tmp;
  }

  private double avg(double a, double b)
  {
    return (a + b) / 2.0;
  }

  private double avoidNeg(double n)
  {
    if (n < 0)
      return 0;
    return n;
  }

  public Wave(Wave previous)
  {
    this.colors = new ArrayList<Color>(Arrays.asList(this.colorsArray));
    this.bonuses = new ArrayList<Bonus>();
    this.robots = new ArrayList<Robot>();
    if (previous == null)
    {
      this.number = 1;
      this.defLife = 1;
      this.defMaxSpeed = 0.001;
      this.defDamage = 0.5;
      this.defFireFreq = 0.001;
    }
    else
    {
      this.number = previous.number + 1;
      Collections.sort(previous.robots);
      Robot best1 = previous.robots.get(0);
      Robot best2 = previous.robots.get(1);
      this.defLife = avg(best1.getMaxLife(), best2.getMaxLife());
      this.defMaxSpeed = avg(best1.getMaxSpeed(), best2.getMaxSpeed());
      this.defDamage = avg(best1.getDamage(), best2.getDamage());
      this.defFireFreq = avg(best1.getFireFreq(), best2.getFireFreq());
    }
    double factorLife = 0.75;
    double factorMaxSpeed = 0.75;
    double factorDamage = 0.75;
    double factorFireFreq = 0.75;
    for (int i = 0; i < 7; i++)
    {
      double currLife = avoidNeg(randomize(defLife, factorLife));
      double currMaxSpeed = avoidNeg(randomize(defMaxSpeed, factorMaxSpeed));
      double currDamage = avoidNeg(randomize(defDamage, factorMaxSpeed));
      double currFireFreq = avoidNeg(randomize(defFireFreq, factorFireFreq));
      Collections.shuffle(this.colors);
      Color currColor = this.colors.get(0);
      this.colors.remove(0);
      this.robots.add(new Robot(randomPosition(GeneticRobots.getPlayer().getPosition()), 0.00025, currMaxSpeed, currDamage, currLife, currFireFreq, currColor));
    }
    for (int i = 0; i < 1 + (int)(Math.random()); i++)
    {
      this.bonuses.add(new Bonus(randomPosition(GeneticRobots.getPlayer().getPosition()).times(2), 5, 0.02, 10));
    }
  }

  public int getNumber()
  {
    return this.number;
  }

  public Collection<Robot> getRobots()
  {
    return this.robots;
  }

  public Collection<Bonus> getBonuses()
  {
    return this.bonuses;
  }
  
  public boolean noRobots()
  {
    int i = 0;
    for (; i < this.robots.size() && this.robots.get(i).isDead(); i++)
      continue;
    return (i == this.robots.size());
  }
}
