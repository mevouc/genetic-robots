Genetic Robots
==============

Project for Computer Science E - 214 at Stellenbosch University
by Meven Courouble (Student number 20586663).

Compilation and execution
-------------------------

GeneticRobots.java is the main class of the project. It is situated it the src/
folder with all the others .java files.

 - Compile: `javac GeneticRobots.java`
 - Execute: `java GeneticRobots`

If you are on Linux, a Makefile is provided.

 - Clean the sources: `make clean`
 - Compile: `make`
 - Execute: `make run`

Interface inheritance
---------------------

There are 2 interfaces in this project: IShooter.java and IGameObject.java
Find below a diagram represinting Interface inheritance:

    IShooter
    |-- Player
    |-- Robot

    IGameObject
    |-- GameObject
        |-- ALL GAME OBJECTS

### IShooter

It implements 2 methods common to Player and Robot, which are the two elements
to blast shots.

The class Shot has one instance variable of type IShooter, which is named
"shooter". It is a reference to the IShooter who fired the Shot. It is used in
the method `rewardShooter()` to inform him when he hit something.

### IGameObject

It implements 2 methods common to all GameObject, update and render, which are
called at each frame.

The main class GeneticRobots has one `Collection<IGameObject>` named "objects",
which is used in his own method update to call each method update of each
GameObject present in the game.

Class Inheritance
-----------------

There is a diagram beloaw representing call inheritance in this project:

    GameObject        (abstract)
    |-- Animation         (abstract)
        |-- Blood             (final)
        |-- Explosion         (final)
    |-- Bonus             (final)
    |-- Ground            (final)
    |-- MovingObject      (abstract)
        |-- Player            (final)
        |-- Robot             (final)
        |-- Shot              (final)

    Template          (abstract)
    |-- Background        (final)
    |-- BonusParticle     (final)
    |-- BonusParticles    (final)
    |-- Gun               (final)
    |-- PlayerBody        (final)
    |-- PlayerBullet      (final)
    |-- RobotBody         (final)
    |-- RobotBullet       (final)
    |-- RobotEye          (final)
    |-- RobotHead         (final)
    |-- ScalableTemplate  (abstract)
        |-- BloodParticles    (final)
        |-- Flames            (final)

### Polymorphism

Class inheritance is used for polymorphism in several classes in this project.

 - The abstract class GameObject does not implement the method `update(long)` the
   interface IGameObject defines. Thus, all sub-GameObject implement its own
   method `update(long)`. Therefore, in GeneticRobots, when `update()` calls
   `update(long)` on all IGameObject of the game, each of them implements it with
   its own version.
 - This also applies to the `render()` method. Which each GameObject override
   with its own version.
 - The abstract class Template defines an abstract method 
   `display(Vector, double)`, which it calls in its recursive method
   `render(Vector, double)`. Each Template then implements its own version
   explaining how it should be displayed.

Immutable Classes
----------------

There is a list of all the immutable classes of this project:

 - Collision
 - LifeBar
 - Vector (provided by in the skeleton)

Additionnal Work
----------------

Read below a list of all additionnal work done for this project:

- Recursive rendering with sub-components. Using the class Template. You can see
  examples with the gun of the player, the eyes and head of the robots, the
  particles of the bonuses.
- Infinite map with repetitive tile as background. The background is too small
  to fit in all the screen, but the map is infinite. While the player moves,
  only the 9 tiles around him are displayed.
- Player stay in center and everything is printed relatively to him.
- Player and Robots can shoot.
- When robots or bonuses are far, a triangle is displayed on the edge of the
  screen to point at its position.
- When robots die, an animation of 5 frames occurs.
- When robots or player are hit, a particles effect of colorful blood occurs.
- There is music during menu.
- There are multiple sounds during the game (fire blasts, explosions).
- It's compatible for QWERTY and AZERTY keymaps.
- The menu is not a simple splash screen.
- User can choose a pseudo.
- Game saves the leader board.
- Player and Robot have life bars.
- Genetic Algorithmics is used to create waves. At the end of each wave, the 2
  best robots (who hurted the most the player and last the longest) are used to
  determine the new default properties on which the robots of the next wave will
  be based on. Because of this, the difficulty increase slowly and every game is
  different. But because the fact its genetic and based mainly on random, it can
  last a very long time before the difficulty of the game actually increases.
