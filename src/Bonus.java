import java.awt.Color;

public class Bonus extends FixedObject
{
  private final double angularSpeed;
  private final double radius;
  private final Collider collider;

  public Bonus(double angularSpeed, double radius)
  {
    this.position = new Vector(Math.random(), Math.random());
    this.angularSpeed = angularSpeed;
    this.radius = radius;
    this.appearence = new BonusParticles(this.radius, Color.green);
    this.collider = new Collider(0.021, this.position, "bonus", this);
    GeneticRobots.addCollider(this.collider);
  }

  public void update(long elapsedTime)
  {
  }

  public void render()
  {
  }
}
