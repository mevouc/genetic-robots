/**
 * This class represents the animation of explosion produced by a robot death.
 * @see Animation
 */
public final class Explosion extends Animation
{
  // the sound of the explosion
  private static final Sound boom;

  // static initializer
  static
  {
    // only one reference to the file, to avoid enormous number of pointers on
    // this file.
    // http://opengameart.org/content/rumbleexplosion
    boom = new Sound("snd/rumble.wav");
  }

  /**
   * Create an explosion at the given position.
   * @param position the position of the explosion.
   */
  public Explosion(Vector position)
  {
    this.position = position;
    this.appearence = new Flames();
    this.timeToAppear = 20; 
    this.timeLeft = this.timeToAppear;
    this.growingScale = true;
    // play the sound
    boom.play();
  }
}
