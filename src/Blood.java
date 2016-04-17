import java.awt.Color;

public class Blood extends Animation
{
  private Color color;

  public Blood(Vector position, double angle, Color color)
  {
    this.position = position;
    this.timeToAppear = 20; 
    this.timeLeft = this.timeToAppear;
    this.growingScale = false;
    this.appearence = new BloodParticles(angle, color);
  }
}
