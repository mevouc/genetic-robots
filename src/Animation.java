public abstract class Animation extends GameObject
{
  protected long timeToAppear;
  protected long timeLeft;
  protected boolean growingScale;
  protected ScalableTemplate appearence;

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

  protected void destroy()
  {
    GeneticRobots.rmObject(this);
  }

  public void render()
  {
    this.appearence.render(GeneticRobots.centerOnPlayer(this.position), 0);
  }
}
