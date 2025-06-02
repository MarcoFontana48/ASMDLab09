package scala.u09.tasks.qlearningdesign

import u09.model.QMatrix
import QMatrix.*
import Move.*

object Corridor extends App :
  val rl: QMatrix.Facade = Facade(
    width = 8,
    height = 8,
    initial = (0,0),
    terminal = { case _ => false },
    // goal is in (7,7), but there are some obstacles to avoid
    // goal has positive reward, obstacles negative rewards.
    // if the robot is close to a border and tries to go through it, it gets a small negative reward to make it learn to
    // avoid it.
    reward = {
      // create complete walls with single-cell openings
      val wall1 = (0 to 7).filter(_ != 6).map(y => (2, y)).toSet  // opening only at y=6
      val wall2 = (0 to 7).filter(_ != 1).map(y => (4, y)).toSet  // opening only at y=1
      val wall3 = (0 to 7).filter(_ != 6).map(y => (6, y)).toSet  // opening only at y=6
      val allObstacles = wall1 ++ wall2 ++ wall3

      {
        // goal
        case ((7, 7), _) => 10
        // walls
        case ((x, y), _) if allObstacles.contains((x, y)) => -10
        // borders
        case ((0, _), LEFT) => -1
        case ((_, 0), UP) => -1
        case ((7, _), RIGHT) => -1
        case ((_, 7), DOWN) => -1
        // default case
        case _ => 0
      }
    },
    jumps = Map.empty,
    gamma = 0.9,
    alpha = 0.8,
    epsilon = 0.5,
    epsilonReducer = 0.00002,
    v0 = 1
  )

  val q0 = rl.qFunction
  println(rl.show(q0.vFunction,"%2.2f"))
  val q1 = rl.makeLearningInstance().learn(10000,400,q0)
  println(rl.show(q1.vFunction,"%2.2f"))
  println(rl.show(s => q1.bestPolicy(s).toString,"%7s"))

object Collector extends App:
  val rl: QMatrix.Facade = Facade(
    width = 8,
    height = 8,
    initial = (0,0),
    terminal = { case _ => false },
    reward = {
      // goal
      case ((7, 7), _) => 10;
      // enemies to avoid
      case ((2, 3), _) => -10;
      case ((5, 3), _) => -10;
      case ((6, 2), _) => -10;
      case ((7, 4), _) => -10;
      case ((1, 3), _) => -10;
      // borders
      case ((0, _), LEFT) => -1;
      case ((_, 0), UP) => -1;
      case ((7, _), RIGHT) => -1;
      case ((_, 7), DOWN) => -1;
      // default case
      case _ => 0
    },
    jumps = {
      // if the robot reaches an item, it collects it and jumps to the initial position
      case ((3, 7), _) => (0, 0);
      case ((5, 4), _) => (0, 0);
      case ((8, 1), _) => (0, 0);
      case ((1, 1), _) => (0, 0);
      case ((7, 7), _) => (0, 0);
    },
    gamma = 0.9,
    alpha = 0.8,
    epsilon = 0.5,
    epsilonReducer = 0.00001,
    v0 = 1
  )

  val q0 = rl.qFunction
  println(rl.show(q0.vFunction,"%2.2f"))
  val q1 = rl.makeLearningInstance().learn(10000,400,q0)
  println(rl.show(q1.vFunction,"%2.2f"))
  println(rl.show(s => q1.bestPolicy(s).toString,"%7s"))