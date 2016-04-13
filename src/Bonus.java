import java.awt.Color;

public class Bonus extends FixedObject
{
  private final double angularSpeed;
  private final double radius;
  private final Collider collider;
  private double currentAngle;
  private final long lifePoints;

  public Bonus(double angularSpeed, double radius, long lifePoints)
  {
    this.position = new Vector(Math.random(), Math.random());
    this.angularSpeed = angularSpeed;
    this.radius = radius;
    this.appearence = new BonusParticles(this.radius, Color.green);
    this.collider = new Collider(0.021, this.position, Tag.BONUS, this);
    GeneticRobots.addCollider(this.collider);
    this.currentAngle = 0;
    this.lifePoints = lifePoints;
  }

  public void destroy()
  {
    GeneticRobots.rmCollider(this.collider);
    GeneticRobots.rmObject(this);
  }

  public long getLifePoints()
  {
    return this.lifePoints;
  }

  public void update(long elapsedTime)
  {
    this.currentAngle += (angularSpeed * elapsedTime);
    this.currentAngle %= 360;
  }

  public void render()
  {
    appearence.render(GeneticRobots.centerOnPlayer(this.position), this.currentAngle);
  }
}
