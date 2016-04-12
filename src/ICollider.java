public interface ICollider
{
  public Collision isColliding(ICollider that);
  public double getRadius();
  public Vector getPosition();
  public void setPosition(Vector position);
  public String getTag();
  public GameObject getObject();
}
