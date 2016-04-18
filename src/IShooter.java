/**
 * This interface defines methods common for all the entities capable of
 * shooting.
 * @see Player
 * @see Robot
 */
public interface IShooter
{
  /**
   * Shoot a new shot in the given direction.
   * @param direction the direction where the shot will be thrown.
   */
  public void shoot(Vector direction);

  /**
   * Inform the shooter that he has hitten someone and inflicted him the given
   * damage.
   * @param damage the inflicted damage to the target.
   */
  public void reward(double damage);
}
