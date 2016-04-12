public final class Collision
{
  private final Vector force;
  private final String tag;
  private final GameObject obj;

  public Collision(Vector force, ICollider that)
  {
    this.force = force;
    this.tag = that.getTag();
    this.obj = that.getObject();
  }

  public Vector getForce()
  {
    return this.force;
  }

  public String getTag()
  {
    return this.tag;
  }

  public GameObject getObject()
  {
    return this.obj;
  }
}
