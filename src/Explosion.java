public class Explosion extends Animation
{
  public Explosion(Vector position)
  {
    this.position = position;
    this.appearence = new Flames();
    this.timeToAppear = 20; 
    this.timeLeft = this.timeToAppear;
    this.growingScale = true;
  }
}
