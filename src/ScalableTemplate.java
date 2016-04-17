public abstract class ScalableTemplate extends Template
{
  protected double scale;
  protected final double nativeScale;

  public ScalableTemplate(double nativeScale)
  {
    super();
    this.nativeScale = nativeScale;
  }

  public ScalableTemplate(Vector relativePos, double relativeAngle,
      double nativeScale)
  {
    super(relativePos, relativeAngle);
    this.nativeScale = nativeScale;
  }

  public void setScale(double scale)
  {
    this.scale = scale;
  }
}
