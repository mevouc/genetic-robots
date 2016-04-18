/**
 * This class is a summary of the property of a collision occured with a
 * collider.
 * @see Collider
 */
public final class Collision
{
  private final Vector force;
  private final Tag tag;
  private final GameObject obj;

  /**
   * Create a new collision of the given force with the given collider.
   * @param force the force of the collision
   * @param that the collider which collides with the one which create this
   * instance
   */
  public Collision(Vector force, Collider that)
  {
    this.force = force;
    this.tag = that.getTag();
    this.obj = that.getObject();
  }

  /**
   * Get the force of the collision.
   */
  public Vector getForce()
  {
    return this.force;
  }

  /**
   * Get the tag.
   */
  public Tag getTag()
  {
    return this.tag;
  }

  /**
   * Get the object which is colliding with the creator of the instance.
   */
  public GameObject getObject()
  {
    return this.obj;
  }
}
