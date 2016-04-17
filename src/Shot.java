import java.awt.Color;

public class Shot extends MovingObject
{
  private final Collider collider;
  private final double damage;
  private final IShooter shooter;
  private static final Sound player;
  private static final Sound robot;

  static
  {
    // http://opengameart.org/content/sci-fi-laser-fire-sfx
    player = new Sound("snd/laserfire02.wav");
    robot = new Sound("snd/laserfire01.wav");
  }
  public Shot(Vector position, Vector speed, int damage, Tag tag,
      IShooter shooter)
  {
    this.speed = speed;
    this.damage = damage;
    this.position = position;
    this.collider = new Collider(0.005, position, tag, this);
    GeneticRobots.addCollider(this.collider);
    this.appearence = new PlayerBullet(new Vector(2), 0);
    this.shooter = shooter;
    player.play();
  }

  public Shot(Vector position, Vector speed, double damage, Tag tag,
      Color color, IShooter shooter)
  {
    this.speed = speed;
    this.damage = damage;
    this.position = position;
    this.collider = new Collider(0.005, position, tag, this);
    GeneticRobots.addCollider(this.collider);
    this.appearence = new RobotBullet(new Vector(2), 0, color);
    this.shooter = shooter;
    robot.play();
  }

  public void rewardShooter()
  {
    this.shooter.reward(this.damage);
  }

  public void destroy()
  {
    GeneticRobots.rmCollider(this.collider);
    GeneticRobots.rmObject(this);
  }

  public void update(long elasedTime)
  {
    Vector toPlayer = position.minus(GeneticRobots.getPlayer().getPosition());
    double distance = toPlayer.magnitude();
    if (distance > 1)
      destroy();
    else
    {
      this.elapsedTime = elapsedTime;
      move();
      this.collider.setPosition(this.position);
    }
  }

  public double getDamage()
  {
    return this.damage;
  }

  public void render()
  {
    this.appearence.render(GeneticRobots.centerOnPlayer(position),
        direction.angle());
  }
}
