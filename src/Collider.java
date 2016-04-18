import java.awt.Color;

/**
 * This class defines the behaviour of the collider component attached to
 * several objects.
 * @see Collision
 */
public final class Collider
{
  private final double radius;
  private Vector position;
  private final Tag tag;
  private final GameObject obj;

  /**
   * Create a collider with the given radius, at the given position with the
   * given tag, attached to the given object.
   * @param radius the radius of the collidebox
   * @param position the position of the collider
   * @param tag the tag
   * @param obj the game object to which this collider will be attached
   */
  public Collider(double radius, Vector position, Tag tag, GameObject obj)
  {
    this.radius = radius;
    this.position = position;
    this.tag = tag;
    this.obj = obj;
  }

  /**
   * Get this collider radius.
   */
  public double getRadius()
  {
    return this.radius;
  }

  /**
   * Get the position of this collider
   */
  public Vector getPosition()
  {
    return this.position;
  }

  /**
   * Set the position to the given one.
   * @param position the new position
   */
  public void setPosition(Vector position)
  {
    this.position = position;
  }

  // return null if not Colliding, the collision which occurs
  private Collision isColliding(Collider that)
  {
    Vector toThat = that.getPosition().minus(this.position);
    if (toThat.isZero())
      return null;
    double distance =  toThat.magnitude();
    boolean colliding = (distance < radius + that.getRadius());
    if (colliding)
      return new Collision(toThat.times(-1).direction(), that);
    return null;
  }
  
  /**
   * Say if this collider is colliding with any of the others colliders of the
   * game.
   * @return the collision which occurs between the other object it collides
   * with if there is collision, null otherwise
   */
  public Collision isColliding()
  {
    Collision tmp;
    for (Collider thatCollider : GeneticRobots.getColliders())
    {
      if (thatCollider != this && ((tmp = isColliding(thatCollider)) != null))
        return tmp;
    }
    return null;
  }

  /**
   * Get this collider tag.
   */
  public Tag getTag()
  {
    return this.tag;
  }

  /**
   * Get the object to which this collider is attached to.
   */
  public GameObject getObject()
  {
    return this.obj;
  }
}
