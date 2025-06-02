# Operational tasks
## Come la variazione dei parametri influenza l'apprendimento
- epsilon: eseguendo "TryQMatrix" con un valore di epsilon pari a 0.1 si osserva che l'agente esplora meno frequentemente, portando a una convergenza più rapida verso una strategia subottimale. Un epsilon più alto, come 0.9, consente all'agente di esplorare di più, ma rallenta la convergenza.
- alpha: un valore di alpha pari a 0.1 porta a un apprendimento più lento, mentre un valore di 0.9 accelera l'apprendimento.
- gamma: un gamma pari a 0.9 implica che l'agente considera le ricompense future, mentre un gamma di 0.1 lo porta a concentrarsi maggiormente sulle ricompense immediate.
- episode length: un numero maggiore di episodi consente all'agente di esplorare più a fondo l'ambiente, migliorando le sue prestazioni.

Per mostrare la differenza nell'apprendimento in base ai parametri sono stati eseguiti alcuni esperimenti per mostrare
l'effetto della variazione di epsilon, alpha, gamma e dimensione della griglia:

1) Dimensione 5x5 della griglia, alpha 0.5, gamma 0.9, epsilon 0.3, 3000 episodi da dimensione 400 ciascuno:

```scala 3
  val rl: QMatrix.Facade = Facade(
  width = 5,
  height = 5,
  initial = (0,0),
  terminal = { case _=> false },
  reward = { case ((4,4),_) => 10; case ((3,0),_) => 5; case ((0, _), LEFT) => -1; case ((_, 0), UP) => -1; case ((9, _), RIGHT) => -1; case ((_, 9), DOWN) => -1; case _ => 0 },
  jumps = { case ((1,0),_) => (1,4); case ((3,0),_) => (3,2) },
  gamma = 0.9,
  alpha = 0.5,
  epsilon = 0.3,
  v0 = 1
)

val q0 = rl.qFunction
println(rl.show(q0.vFunction,"%2.2f"))
val q1 = rl.makeLearningInstance().learn(3000,400,q0)
println(rl.show(q1.vFunction,"%2.2f"))
println(rl.show(s => q1.bestPolicy(s).toString,"%7s"))
```

```text
-1	0	1	2	3	4
0	1,00	1,00	1,00	1,00	1,00
1	1,00	1,00	1,00	1,00	1,00
2	1,00	1,00	1,00	1,00	1,00
3	1,00	1,00	1,00	1,00	1,00
4	1,00	1,00	1,00	1,00	1,00

-1	0	1	2	3	4
0	59,05	65,61	47,91	70,61	65,61
1	53,14	59,05	59,05	65,61	72,90
2	47,83	59,05	65,61	72,90	81,00
3	59,05	65,61	72,90	81,00	90,00
4	65,61	72,90	81,00	90,00	100,00

-1	0	1	2	3	4
0	      >	      <	      >	      <	      v
1	      ^	      ^	      v	      v	      v
2	      ^	      v	      >	      >	      v
3	      >	      >	      >	      >	      v
4	      >	      >	      >	      >	      >
```

2) Dimensione 5x5 della griglia, alpha 0.9, gamma 0.9, epsilon 0.3, 3000 episodi da dimensione 400 ciascuno:

```scala 3
  val rl: QMatrix.Facade = Facade(
  width = 5,
  height = 5,
  initial = (0,0),
  terminal = { case _=> false },
  reward = { case ((4,4),_) => 10; case ((3,0),_) => 5; case ((0, _), LEFT) => -1; case ((_, 0), UP) => -1; case ((9, _), RIGHT) => -1; case ((_, 9), DOWN) => -1; case _ => 0 },
  jumps = { case ((1,0),_) => (1,4); case ((3,0),_) => (3,2) },
  gamma = 0.9,
  alpha = 0.8,
  epsilon = 0.4,
  v0 = 1
)
```

```text
-1	0	1	2	3	4
0	1,00	1,00	1,00	1,00	1,00
1	1,00	1,00	1,00	1,00	1,00
2	1,00	1,00	1,00	1,00	1,00
3	1,00	1,00	1,00	1,00	1,00
4	1,00	1,00	1,00	1,00	1,00

-1	0	1	2	3	4
0	59,05	65,61	63,55	70,61	65,61
1	53,14	59,05	59,05	65,61	72,90
2	53,14	59,05	65,61	72,90	81,00
3	59,05	65,61	72,90	81,00	90,00
4	65,61	72,90	81,00	90,00	100,00

-1	0	1	2	3	4
0	      >	      <	      >	      <	      v
1	      >	      ^	      >	      >	      v
2	      v	      >	      >	      >	      v
3	      >	      >	      >	      >	      v
4	      >	      >	      >	      >	      >
```

## Dimensione della griglia
La dimensione della griglia influisce sulla complessità del problema. Griglie più grandi richiedono più episodi per l'apprendimento, poiché l'agente deve esplorare più stati e azioni.

1) Dimensione 10x10 con 3000 episodi da dimensione 400 ciascuno:

```scala 3
  val rl: QMatrix.Facade = Facade(
  width = 10,
  height = 10,
  initial = (0,0),
  terminal = { case _=> false },
  reward = { case ((9,9),_) => 10; case ((3,0),_) => 5; case ((0, _), LEFT) => -1; case ((_, 0), UP) => -1; case ((9, _), RIGHT) => -1; case ((_, 9), DOWN) => -1; case _ => 0 },
  jumps = { case ((1,0),_) => (1,4); case ((3,0),_) => (3,2) },
  gamma = 0.9,
  alpha = 0.5,
  epsilon = 0.3,
  v0 = 1
)

val q0 = rl.qFunction
println(rl.show(q0.vFunction,"%2.2f"))
val q1 = rl.makeLearningInstance().learn(3000,400,q0)
println(rl.show(q1.vFunction,"%2.2f"))
println(rl.show(s => q1.bestPolicy(s).toString,"%7s"))
```

```text
-1	0	1	2	3	4	5	6	7	8	9
0	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
1	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
2	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
3	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
4	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
5	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
6	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
7	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
8	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
9	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00

-1	0	1	2	3	4	5	6	7	8	9
0	10,89	8,82	16,61	18,45	16,61	14,94	10,89	9,73	0,86	0,82
1	12,11	13,45	14,94	16,61	14,94	13,45	12,11	10,89	8,33	0,82
2	10,89	12,11	13,45	14,94	13,45	12,11	10,89	9,81	4,24	0,84
3	9,81	10,89	12,11	13,45	12,11	10,89	9,81	8,79	6,08	0,83
4	8,82	9,81	10,89	12,11	10,89	9,81	8,82	0,90	2,35	0,82
5	7,94	8,82	9,81	10,89	9,81	8,82	7,03	0,88	0,86	0,87
6	0,82	7,93	8,82	9,81	8,82	7,62	1,86	0,90	0,88	0,89
7	0,84	0,83	0,84	8,48	6,88	3,12	1,16	0,95	13,74	32,14
8	0,86	0,83	0,84	0,85	0,88	0,89	0,93	0,93	39,17	45,68
9	0,85	0,84	0,83	0,86	0,87	0,87	0,95	3,33	23,07	51,00

-1	0	1	2	3	4	5	6	7	8	9
0	      v	      <	      >	      <	      <	      <	      v	      <	      <	      <
1	      >	      >	      >	      ^	      <	      <	      <	      <	      <	      <
2	      >	      >	      >	      ^	      <	      <	      <	      <	      ^	      <
3	      >	      >	      >	      ^	      <	      <	      <	      <	      <	      <
4	      >	      >	      >	      ^	      <	      ^	      ^	      v	      ^	      v
5	      ^	      ^	      ^	      ^	      ^	      <	      <	      v	      >	      v
6	      v	      ^	      ^	      ^	      <	      ^	      ^	      v	      >	      ^
7	      ^	      v	      v	      ^	      ^	      <	      ^	      v	      v	      v
8	      v	      >	      ^	      >	      v	      v	      <	      ^	      >	      v
9	      ^	      <	      >	      >	      >	      >	      ^	      >	      ^	      ^
```

2) Dimensione 10x10 con 30_000 episodi da dimensione 4_000 ciascuno (netto miglioramento rispetto al precedente):

```scala 3
  val rl: QMatrix.Facade = Facade(
  width = 10,
  height = 10,
  initial = (0,0),
  terminal = { case _=> false },
  reward = { case ((9,9),_) => 10; case ((3,0),_) => 5; case ((0, _), LEFT) => -1; case ((_, 0), UP) => -1; case ((9, _), RIGHT) => -1; case ((_, 9), DOWN) => -1; case _ => 0 },
  jumps = { case ((1,0),_) => (1,4); case ((3,0),_) => (3,2) },
  gamma = 0.9,
  alpha = 0.5,
  epsilon = 0.3,
  v0 = 1
)

val q0 = rl.qFunction
println(rl.show(q0.vFunction,"%2.2f"))
val q1 = rl.makeLearningInstance().learn(30_000,4_000,q0)
println(rl.show(q1.vFunction,"%2.2f"))
println(rl.show(s => q1.bestPolicy(s).toString,"%7s"))
```

```text
-1	0	1	2	3	4	5	6	7	8	9
0	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
1	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
2	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
3	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
4	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
5	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
6	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
7	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
8	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
9	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00

-1	0	1	2	3	4	5	6	7	8	9
0	20,59	22,88	25,09	27,88	25,09	25,00	28,24	31,38	34,87	38,71
1	18,53	20,59	22,58	25,09	25,42	28,24	31,38	34,87	38,74	43,05
2	18,53	20,59	22,88	25,42	28,24	31,38	34,87	38,74	43,05	47,83
3	20,59	22,88	25,42	28,24	31,38	34,87	38,74	43,05	47,83	53,14
4	22,88	25,42	28,24	31,38	34,87	38,74	43,05	47,83	53,14	59,05
5	25,42	28,24	31,38	34,87	38,74	43,05	47,83	53,14	59,05	65,61
6	28,24	31,38	34,87	38,74	43,05	47,83	53,14	59,05	65,61	72,90
7	25,39	33,96	38,74	43,05	47,83	53,14	59,05	65,61	72,90	81,00
8	0,79	23,88	34,87	47,83	53,14	59,05	65,61	72,90	81,00	90,00
9	0,77	9,07	46,35	53,14	59,05	65,61	72,90	81,00	90,00	100,00

-1	0	1	2	3	4	5	6	7	8	9
0	      >	      <	      >	      <	      <	      >	      v	      v	      v	      v
1	      >	      ^	      >	      ^	      v	      v	      v	      v	      v	      v
2	      >	      >	      >	      >	      >	      >	      >	      >	      >	      v
3	      >	      >	      >	      >	      >	      >	      >	      >	      >	      v
4	      >	      >	      >	      >	      >	      >	      >	      >	      >	      v
5	      >	      >	      >	      >	      >	      >	      >	      >	      >	      v
6	      >	      >	      >	      >	      >	      >	      >	      >	      >	      v
7	      ^	      >	      >	      >	      >	      >	      >	      >	      >	      v
8	      ^	      ^	      ^	      >	      v	      >	      >	      >	      >	      v
9	      >	      ^	      >	      >	      >	      >	      >	      >	      >	      >
```

Si può notare come quasi tutti gli elementi nella diagonale puntino verso destra (verso l'obiettivo) mentre nel caso 
precedente non era così, e l'andamento era più casuale. Questo dimostra che l'agente ha imparato meglio a muoversi
verso l'obiettivo.

# Task DesignByQLearning
Corridoio con ostacoli e obiettivo finale. L'agente deve imparare a muoversi verso l'obiettivo evitando gli ostacoli:

```scala 3
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
````

```text
-1	0	1	2	3	4	5	6	7
0	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
1	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
2	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
3	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
4	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
5	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
6	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
7	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00

-1	0	1	2	3	4	5	6	7
0	24,09	26,77	29,74	34,87	38,74	38,74	43,05	47,83
1	25,42	28,24	31,38	34,87	38,74	43,05	47,83	53,14
2	28,24	31,38	34,87	38,74	43,05	47,83	53,14	59,05
3	31,38	34,87	31,38	33,05	47,83	53,14	49,05	65,61
4	34,87	38,74	33,05	47,83	53,14	59,05	65,61	72,90
5	38,74	43,05	47,83	53,14	59,05	65,61	72,90	81,00
6	43,05	47,83	53,14	59,05	65,61	72,90	81,00	90,00
7	38,74	36,58	51,76	57,51	63,90	71,00	90,00	100,00

-1	0	1	2	3	4	5	6	7
0	      >	      v	      >	      v	      >	      v	      >	      v
1	      >	      v	      >	      >	      >	      v	      >	      v
2	      >	      v	      >	      ^	      >	      v	      >	      v
3	      >	      v	      >	      ^	      >	      v	      >	      v
4	      >	      v	      >	      ^	      >	      v	      >	      v
5	      >	      v	      >	      ^	      >	      v	      >	      v
6	      >	      >	      >	      ^	      >	      >	      >	      v
7	      >	      ^	      >	      ^	      >	      ^	      >	      >
```

Colleziona oggetti e torna alla posizione iniziale. L'agente deve imparare a muoversi verso gli oggetti evitando i 
nemici e tornando alla posizione iniziale dopo averli raccolti:

```scala 3
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
```

```text
-1	0	1	2	3	4	5	6	7
0	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
1	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
2	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
3	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
4	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
5	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
6	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00
7	1,00	1,00	1,00	1,00	1,00	1,00	1,00	1,00

-1	0	1	2	3	4	5	6	7
0	2,88	3,20	3,56	3,95	4,39	3,95	4,39	4,88
1	3,20	2,59	3,95	4,39	4,88	4,39	4,88	5,42
2	3,56	3,95	4,39	4,88	5,42	4,88	-3,31	6,02
3	3,95	-5,61	-5,12	5,42	6,02	-3,31	7,44	6,69
4	4,39	4,88	5,42	6,02	6,69	2,59	8,26	-0,82
5	4,88	5,42	6,02	6,69	7,44	8,26	9,18	10,20
6	5,42	6,02	6,69	7,44	8,26	9,18	10,20	11,33
7	4,88	5,42	6,02	2,59	9,18	10,20	11,33	12,59

-1	0	1	2	3	4	5	6	7
0	      >	      >	      >	      >	      v	      <	      >	      v
1	      v	      <	      >	      >	      v	      <	      >	      v
2	      >	      >	      >	      >	      v	      <	      v	      v
3	      v	      v	      >	      >	      v	      >	      v	      <
4	      >	      >	      >	      >	      v	      <	      v	      v
5	      >	      >	      >	      >	      >	      >	      >	      v
6	      >	      >	      >	      >	      >	      >	      >	      v
7	      ^	      ^	      ^	      <	      >	      >	      >	      <
```