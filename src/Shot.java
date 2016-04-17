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
    player = new Sound("snd/laserfire02.wav");
    robot = new Sound("snd/laserfire01.wav");
  }
  public Shot(Vector position, Vector speed, int damage, Tag tag, IShooter shooter)
  {
    this.speed = speed;
    this.damage = damage;
    this.position = position;
    this.collider = new Collider(0.005, position, tag, this);
    GeneticRobots.addCollider(this.collider);
    this.appearence = new PlayerBullet(new Vector(2), 0);
    this.shooter = shooter;
    // http://opengameart.org/content/sci-fi-laser-fire-sfx
    player.play();
  }

  public Shot(Vector position, Vector speed, double damage, Tag tag, Color color, IShooter shooter)
  {
    this.speed = speed;
    this.damage = damage;
    this.position = position;
    this.collider = new Collider(0.005, position, tag, this);
    GeneticRobots.addCollider(this.collider);
    this.appearence = new RobotBullet(new Vector(2), 0, color);
    this.shooter = shooter;
    // http://opengameart.org/content/sci-fi-laser-fire-sfx
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
    double distance = position.minus(GeneticRobots.getPlayer().getPosition()).magnitude();
    if (distance > 1)
      this.destroy();
    else
    {
      this.elapsedTime = elapsedTime;
      move();
      collider.setPosition(this.position);
    }
  }

  public double getDamage()
  {
    return this.damage;
  }

  public void render()
  {
    appearence.render(GeneticRobots.centerOnPlayer(position), direction.angle());
  }
}
