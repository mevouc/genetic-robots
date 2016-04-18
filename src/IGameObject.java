/**
 * This interface defines the common methods for all the game objects.
 */
public interface IGameObject
{
  /**
   * Update the object state since a given amount of time.
   * @param elapsedTime number of frames since the last update.
   */
  public abstract void update(long elapsedTime);

  /**
   * Render the current state of the object, relatively of the player position,
   * which must appear in the center of the screen.
   */
  public abstract void render();
}
