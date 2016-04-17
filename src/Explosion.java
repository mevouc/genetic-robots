public class Explosion extends GameObject
{
  private long timeToAppear;
  private long timeLeft;

  public Explosion(Vector position)
  {
    this.position = position;
    this.appearence = new Flames();
    this.timeToAppear = 20; 
    this.timeLeft = this.timeToAppear;
  }

  public void update(long elapsedTime)
  {
    ((Flames)this.appearence).setScale(this.timeLeft / (double)this.timeToAppear);
    this.timeLeft -= elapsedTime;
    if (this.timeLeft <= 0)
      GeneticRobots.rmObject(this);
  }

  public void render()
  {
    this.appearence.render(GeneticRobots.centerOnPlayer(this.position), 0);
  }
}
