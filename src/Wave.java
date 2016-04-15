import java.util.Collection;
import java.util.ArrayList;
import java.awt.Color;

public class Wave
{
  private final double defLife;
  private final double defMaxSpeed;
  private final double defDamage;
  private final double defFireFreq;
  private ArrayList<Robot> robots;
  private Collection<Bonus> bonuses;
  private static Color[] colors = { new Color(240, 110, 170),
                                    new Color(96, 92, 168),
                                    new Color(23, 147, 209),
                                    new Color(0, 166, 81),
                                    new Color(255, 242, 0),
                                    new Color(237, 28, 36),
                                    new Color(247, 148, 29) };

  private static Vector randomPosition()
  {
    /* TODO
      * random position AROUND the player
     */
    double x = Math.random();
    double y = Math.random();
    double range = 0.1;
    Vector center = GeneticRobots.getCenter();
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

  private double randomize(double value, double factor)
  {
    double tmp = factor * value;
    return value + Math.random() * tmp - Math.random() * tmp;
  }

  public Wave(Wave previous)
  {
    bonuses = new ArrayList<Bonus>();
    robots = new ArrayList<Robot>();
    double factorLife = 0.5;
    double factorMaxSpeed = 0.2;
    double factorDamage = 0.5;
    double factorFireFreq = 0.1;
    if (previous == null)
    {
      this.defLife = 10;
      this.defMaxSpeed = 0.005;
      this.defDamage = 1;
      this.defFireFreq = 500;
    }
    else
    {
    // TODO RM
      this.defLife = 10;
      this.defMaxSpeed = 0.005;
      this.defDamage = 1;
      this.defFireFreq = 500;
    // TODO RM
      // TODO use attributes of 2 best ones to create 5 new robots
    }

    for (int i = 0; i < 5; i++)
    {
      double currLife = randomize(defLife, factorLife);
      double currMaxSpeed = randomize(defMaxSpeed, factorMaxSpeed);
      double currDamage = randomize(defDamage, factorMaxSpeed);
      double currFireFreq = randomize(defFireFreq, factorFireFreq);
      Color currColor = colors[(int)(Math.random() * colors.length)];
      robots.add(new Robot(randomPosition(), 0.00025, currMaxSpeed, currDamage, currLife, currFireFreq, currColor));
    }
    for (int i = 0; i < 1 + (int)(Math.random()); i++)
    {
      bonuses.add(new Bonus(randomPosition().times(3), 5, 0.02, 10));
    }
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
