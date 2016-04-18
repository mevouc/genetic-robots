/**
 * This abstract class provides methods and properties to display easily
 * short animations.
 * @see GameObject
 */
public abstract class Animation extends GameObject
{
  protected long timeToAppear;
  protected long timeLeft;
  protected boolean growingScale;
  protected ScalableTemplate appearence;

  /**
   * {@inheritDoc}
   */
  public void update(long elapsedTime)
  {
    if (growingScale)
      this.appearence.setScale(this.timeLeft / (double)this.timeToAppear);
    else
      this.appearence.setScale(1 - this.timeLeft / (double)this.timeToAppear);
    this.timeLeft -= elapsedTime;
    if (this.timeLeft <= 0)
      destroy();
  }

  // destroy this object and inform the game
  private void destroy()
  {
    GeneticRobots.rmObject(this);
  }

  /**
   * Render the current state of the animation.
   */
  public void render()
  {
    this.appearence.render(GeneticRobots.centerOnPlayer(this.position), 0);
  }
}
