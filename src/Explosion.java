public class Explosion extends Animation
{
  private static final Sound boom;

  static
  {
    // http://opengameart.org/content/rumbleexplosion
    boom = new Sound("snd/rumble.wav");
  }

  public Explosion(Vector position)
  {
    this.position = position;
    this.appearence = new Flames();
    this.timeToAppear = 20; 
    this.timeLeft = this.timeToAppear;
    this.growingScale = true;
    boom.play();
  }

  protected void destroy()
  {
    GeneticRobots.rmObject(this);
    boom.stop();
  }
}
