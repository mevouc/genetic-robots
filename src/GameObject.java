/**
 * This abstract class provides the properties and methods command to all the
 * main objects of the game.
 * @see IGameObject
 */
public abstract class GameObject implements IGameObject
{
  // all these objects have a position and an appearence
  protected Vector position;
  protected Template appearence;

  /**
   * Get the position of this object.
   */
  public Vector getPosition()
  {
    return this.position;
  }
}
