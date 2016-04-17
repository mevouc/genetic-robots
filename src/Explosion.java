public class Explosion extends Animation
{
  private final Sound boom;
  public Explosion(Vector position)
  {
    this.position = position;
    this.appearence = new Flames();
    this.timeToAppear = 20; 
    this.timeLeft = this.timeToAppear;
    this.growingScale = true;
    // http://opengameart.org/content/rumbleexplosion
    this.boom = new Sound("snd/rumble.wav");
    this.boom.play();
  }

  protected void destroy()
  {
    GeneticRobots.rmObject(this);
    this.boom.stop();
  }
}
