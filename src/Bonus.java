import java.awt.Color;

/**
 * This class defines the behaviour of the bonuses the player can get.
 * @see GameObject
 * @see Player
 */
public final class Bonus extends GameObject
{
  private final double angularSpeed;
  private final double radius;
  private final Collider collider;
  // the angle can change because it rotates
  private double currentAngle;
  private final long lifePoints;

  /**
   * Creates a bonus at a given position, with a specific angular velocity,
   * radius, giving the specified amount of life points to the player when he
   * collects it.
   * @param position the position of the item.
   * @param angularSpeed the angular velocity of the item.
   * @param radius the radius betwee the center of rotation and its elements.
   * @param lifePoints the amount of life points the player can gain when he
   * collects this bonus.
   */
  public Bonus(Vector position, double angularSpeed, double radius,
      long lifePoints)
  {
    this.position = position;
    this.angularSpeed = angularSpeed;
    this.radius = radius;
    this.appearence = new BonusParticles(this.radius, Color.green);
    this.collider = new Collider(0.021, this.position, Tag.BONUS, this);
    GeneticRobots.addCollider(this.collider);
    this.currentAngle = 0;
    this.lifePoints = lifePoints;
  }

  /**
   * Destroy this object and its collider.
   */
  public void destroy()
  {
    GeneticRobots.rmCollider(this.collider);
    GeneticRobots.rmObject(this);
  }

  /**
   * Get the amount of life points linked to this bonus.
   */
  public long getLifePoints()
  {
    return this.lifePoints;
  }

  /**
   * {@inheritDoc}
   */
  public void update(long elapsedTime)
  {
    this.currentAngle += (this.angularSpeed * elapsedTime);
    this.currentAngle %= 360;
  }

  /**
   * {@inheritDoc}
   */
  public void render()
  {
    this.appearence.render(GeneticRobots.centerOnPlayer(this.position),
        this.currentAngle);
  }
}
