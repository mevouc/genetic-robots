import java.awt.Color;

/**
 * This class defines the behaviour of the shots thrown by the player and the
 * robots.
 * @see MovingObject
 * @see IShooter
 */
public final class Shot extends MovingObject
{
  private final Collider collider;
  private final double damage;
  private final IShooter shooter;
  private static final Sound player;
  private static final Sound robot;

  // static initializer
  static
  {
    // only one reference to each file, to avoid enormous number of pointers on
    // these files.
    // http://opengameart.org/content/sci-fi-laser-fire-sfx
    player = new Sound("snd/laserfire02.wav");
    robot = new Sound("snd/laserfire01.wav");
  }

  /**
   * Create a shot of the first type at the given position, with the given
   * initial speed, making the given damage, with the given tag and thrown by
   * the given shooter.
   * @param positon the position of this shot
   * @param speed the initial speed of this shot
   * @param damage the damage this shot will inflict
   * @param tag the tag
   * @param shooter the shooter
   */
  public Shot(Vector position, Vector speed, int damage, Tag tag,
      IShooter shooter)
  {
    this.speed = speed;
    this.damage = damage;
    this.position = position;
    this.collider = new Collider(0.005, position, tag, this);
    GeneticRobots.addCollider(this.collider);
    this.appearence = new PlayerBullet();
    this.shooter = shooter;
    player.play();
  }

  /**
   * Create a shot of the second type at the given position, with the given
   * initial speed, making the given damage, with the given tag and color,
   * thrown by the given shooter.
   * @param positon the position of this shot
   * @param speed the initial speed of this shot
   * @param damage the damage this shot will inflict
   * @param tag the tag
   * @param color the color
   * @param shooter the shooter
   */
  public Shot(Vector position, Vector speed, double damage, Tag tag,
      Color color, IShooter shooter)
  {
    this.speed = speed;
    this.damage = damage;
    this.position = position;
    this.collider = new Collider(0.005, position, tag, this);
    GeneticRobots.addCollider(this.collider);
    this.appearence = new RobotBullet(color);
    this.shooter = shooter;
    robot.play();
  }

  /**
   * Inform the shooter that he has inflicted some damage.
   */
  public void rewardShooter()
  {
    this.shooter.reward(this.damage);
  }

  /**
   * Destroy this shot.
   */
  public void destroy()
  {
    GeneticRobots.rmCollider(this.collider);
    GeneticRobots.rmObject(this);
  }

  /**
   * {@inheritDoc}
   */
  public void update(long elasedTime)
  {
    Vector toPlayer = position.minus(GeneticRobots.getPlayer().getPosition());
    double distance = toPlayer.magnitude();
    // if shot is really far from player, useless to let it last
    if (distance > 1)
      destroy();
    else
    {
      this.elapsedTime = elapsedTime;
      move();
      this.collider.setPosition(this.position);
    }
  }

  /**
   * Get the damage this shot can inflict.
   */
  public double getDamage()
  {
    return this.damage;
  }

  /**
   * {@inheritDoc}
   */
  public void render()
  {
    this.appearence.render(GeneticRobots.centerOnPlayer(position),
        direction.angle());
  }
}
