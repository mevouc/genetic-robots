import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Font;

public class Menu
{
  private static int bgR, bgG, bgB;
  private static String instructions;
  private static LeaderBoard leaderBoard;

  static
  {
    bgR = 5;
    bgG = 5;
    bgB = 15;
    instructions = "Use SPACE to shoot.\n\n"
                 + "Use Arrow Keys to move.\n\n"
                 + "Use WASD to change\nyour shoot angle.\n";
    leaderBoard = new LeaderBoard("../leaderboard");
  }

  private static void base(Color background)
  {
    SteveDraw.clear(background);
    double y = GeneticRobots.canvasH * 0.95 / GeneticRobots.canvasH;
    SteveDraw.setPenColor(new Color(186, 22, 44));
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 64));
    SteveDraw.text(0.5, y, "GENETIC ROBOTS");
  }

  private static void generalMenu()
  {
    SteveDraw.setPenColor(Color.lightGray);
    double y = GeneticRobots.canvasH * 0.75 / GeneticRobots.canvasH;
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 42));
    SteveDraw.text(0.5, y, "PLAY:\nPress ENTER");
    y = GeneticRobots.canvasH * 0.50 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, "INSTRUCTIONS:\nPress SPACE");
    y = GeneticRobots.canvasH * 0.25 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, "QUIT:\nPress ESCAPE");
  }

  private static void instructions()
  {
    base(new Color(bgR, bgG, bgB));
    SteveDraw.setPenColor(Color.gray);
    double y = GeneticRobots.canvasH * 0.85 / GeneticRobots.canvasH;
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
    SteveDraw.text(0.5, y, "INSTRUCTIONS");
    SteveDraw.setPenColor(Color.lightGray);
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 42));
    y = GeneticRobots.canvasH * 0.70 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, instructions);
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 32));
    y = GeneticRobots.canvasH * 0.15 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, "RETURN:\nPress ESCAPE");
    while (!(SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE)))
      continue;
  }

  public static void deathScreen(Score newScore)
  {
    base(new Color(bgR, bgG, bgB, 220));
    SteveDraw.setPenColor(Color.gray);
    double y = GeneticRobots.canvasH * 0.85 / GeneticRobots.canvasH;
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
    SteveDraw.text(0.5, y, "GAME OVER\nLEADERBOARD:");
    SteveDraw.setPenColor(Color.lightGray);
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 42));
    y = GeneticRobots.canvasH * 0.65 / GeneticRobots.canvasH;
    SteveDraw.textRight(0.92, y, leaderBoard.addScore(newScore));
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 32));
    y = GeneticRobots.canvasH * 0.15 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, "RETURN TO MENU:\nPress ESCAPE");
    SteveDraw.show();
    while (!(SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE)))
      continue;
  }

  public static String exec()
  {
    base(new Color(bgR, bgG, bgB));
    generalMenu();
    SteveDraw.show();
    boolean waitChoice = true;
    String pseudo = null;
    while (waitChoice)
    {
      if (SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE))
        waitChoice = false;
      else if (SteveDraw.isKeyPressed(KeyEvent.VK_ENTER))
      {
        pseudo = askPseudo();
        base(new Color(bgR, bgG, bgB));
        generalMenu();
        while (SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE))
          continue;
        waitChoice = (pseudo == null);
      }
      else if (SteveDraw.isKeyPressed(KeyEvent.VK_SPACE))
      {
        instructions();
        base(new Color(bgR, bgG, bgB));
        generalMenu();
        while (SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE))
          continue;
        SteveDraw.show();
      }
    }
    SteveDraw.setFont();
    return pseudo;
  }

  public static String askPseudo()
  {
    while (SteveDraw.isKeyPressed(KeyEvent.VK_ENTER))
      continue;
    while (SteveDraw.hasNextKeyTyped())
      SteveDraw.nextKeyTyped();
    Color bg = new Color(bgR, bgG, bgB);
    base(bg);
    SteveDraw.setPenColor(Color.lightGray);
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 32));
    double y = GeneticRobots.canvasH * 0.15 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, "RETURN TO MENU:\nPress ESCAPE");
    SteveDraw.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 42));
    y = GeneticRobots.canvasH * 0.7 / GeneticRobots.canvasH;
    SteveDraw.text(0.5, y, "Enter a pseudo\n(less than 14 characters)\n");
    y = GeneticRobots.canvasH * 0.5 / GeneticRobots.canvasH;
    SteveDraw.filledRectangle(0.5, y, 0.45, 0.05);
    SteveDraw.setPenColor(bg);
    SteveDraw.textLeft(0.10, y, "|");
    String pseudo = "";
    char c = 0;
    boolean ok = false;
    while (!ok)
    {
      if (SteveDraw.isKeyPressed(KeyEvent.VK_ESCAPE))
      {
        pseudo = null;
      }
      else if (SteveDraw.hasNextKeyTyped() && ((c = SteveDraw.nextKeyTyped()) != 27))
      {
        switch (c)
        {
        case '\b':
          if (pseudo.length() > 0)
            pseudo = pseudo.substring(0, pseudo.length() - 1);
          break;
        case '\n':
        case 3:
        case 4:
          break;
        default:
          if (pseudo.length() < 14)
            pseudo += c;
        }
        SteveDraw.setPenColor(Color.lightGray);
        SteveDraw.filledRectangle(0.5, y, 0.45, 0.05);
        SteveDraw.setPenColor(bg);
        SteveDraw.textLeft(0.10, y, pseudo + '|');
        SteveDraw.show();
      }
      ok = (pseudo == null) || c == '\n' || (c == 3) || (c == 4);
    }
    return pseudo;
  }
}
