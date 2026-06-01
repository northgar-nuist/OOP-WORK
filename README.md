# Space Escape

A command-line text adventure game built for CS1OP Object-Oriented Programming at the University of Reading.

You are an engineer stranded aboard the malfunctioning space station *Deep Space*. The oxygen system is failing. Explore eight rooms, collect a weapon, defeat a sentry robot with a lethal charge attack, solve a puzzle, and reach the escape pod.

## Quick Start

```
mvn compile
mvn exec:java
```

## Run Tests

```
mvn test
```

## Commands

| Command | Alias |描述|
|---------|-------|-------------|
| n/s/e/w| north/south/east/west | Move between rooms |
| `look` | `l` | Inspect the current room |
| `take <item>` | `get` `pick` | Pick up an item |
| `inventory` | `i` `bag` | View your backpack |
| `use <item>` | | Use an item (weapon, medkit, battery) |
| `use <code> on puzzle` | | Attempt to solve the engine-room puzzle |
| `fight` | `attack` | Attack the enemy |
| `defend` | `block` | Defensive stance (halves incoming damage) |
| `hint` | `tip` | Get a contextual hint (3 uses total) |
| `help` | `h` | List all commands |
| `quit` | `q` | Exit the game |

## Design

- **Language:** Java 17
- **Build:** Maven
- **Tests:** 46 × JUnit 5
- **Design Patterns:** Command, Factory, Singleton, Strategy, Observer

## Project Structure

```
src/
├── main/java/com/escape/
│   ├── Main.java
│   ├── game/       Game loop, state, config
│   ├── world/      Room hierarchy, WorldFactory
│   ├── entity/     Player, Enemy hierarchy
│   ├── item/       Item hierarchy
│   ├── command/    Command pattern, parser
│   ├── combat/     Strategy pattern, combat system
│   └── util/       Input validation, observer
└── test/java/com/escape/
    ├── command/    CommandParserTest
    ├── combat/     CombatSystemTest
    ├── entity/     PlayerTest
    └── world/      WorldFactoryTest
```
