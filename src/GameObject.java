public abstract class GameObject
{
  protected Vector position;
  protected Template appearence;

  public abstract void update(long elapsedTime);
  public abstract void render();

  public Vector getPosition()
  {
    return this.position;
  }
}
