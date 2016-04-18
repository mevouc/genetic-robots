/**
 * This class define the properties of the ground of the game.
 * @see GameObject
 */
public final class Ground extends GameObject
{
  /**
   * Initialize the fields of this object.
   */
  public Ground()
  {
    this.position = new Vector(0, 0);
    this.appearence = new Background();
  }

  /**
   * {@inheritDoc}
   */
  public void render()
  {
    this.appearence.render(GeneticRobots.centerOnPlayer(position), 0);
  }

  /**
   * The ground is completely static, this method has no need. But it must be
   * implemented.
   */
  public void update(long elapsedTime)
  {
    return;
  }
}
