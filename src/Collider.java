import java.awt.Color;

public class Collider implements ICollider
{
  private final double radius;
  private Vector position;
  private final Tag tag;
  private final GameObject obj;

  public Collider(double radius, Vector position, Tag tag, GameObject obj)
  {
    this.radius = radius;
    this.position = position;
    this.tag = tag;
    this.obj = obj;
  }

  public double getRadius()
  {
    return this.radius;
  }

  public Vector getPosition()
  {
    return this.position;
  }

  public void setPosition(Vector position)
  {
    this.position = position;
  }

  // return null if not Colliding, a Vector representing the force of collision
  public Collision isColliding(ICollider that)
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
  
  // is colliding to any of the others colliders
  public Collision isColliding()
  {
    Collision tmp;
    for (ICollider thatCollider : GeneticRobots.getColliders())
    {
      if (thatCollider != this && ((tmp = isColliding(thatCollider)) != null))
        return tmp;
    }
    return null;
  }

  public Tag getTag()
  {
    return this.tag;
  }

  public GameObject getObject()
  {
    return this.obj;
  }
}
