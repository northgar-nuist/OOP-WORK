package com.escape.game;

import com.escape.entity.Player;
import com.escape.world.Room;
import com.escape.world.WorldFactory;
import com.escape.command.Command;
import com.escape.command.CommandParser;

import java.util.Scanner;

public class Game {
    private static Game instance;

    private final Player player;
    private final Room startingRoom;
    private GameState state;
    private final Scanner scanner;

    private Game() {
        WorldFactory factory = new WorldFactory();
        startingRoom = factory.createWorld();
        player = new Player(startingRoom);
        state = GameState.RUNNING;
        scanner = new Scanner(System.in);
    }

    public static synchronized Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void run() {
        System.out.println(GameConfig.GAME_TITLE);
        System.out.println(GameConfig.INTRO);
        System.out.println();
        look();

        while (state == GameState.RUNNING) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            Command command = CommandParser.parse(input);
            String result = command.execute(this);
            System.out.println(result);

            if (state == GameState.RUNNING) {
                player.tickOxygen();
                if (player.getOxygen() <= 0) {
                    state = GameState.LOST_OXYGEN;
                    System.out.println("\n氧气耗尽！你在空间站的走廊里失去了意识...");
                    System.out.println("【游戏结束 — 你未能逃出生天】");
                }
            }
        }

        scanner.close();
    }

    public void look() {
        Room current = player.getCurrentRoom();
        System.out.println("\n--- " + current.getName() + " ---");
        System.out.println(current.getDescription());
        System.out.println(current.getExitsDescription());
        if (!current.getItems().isEmpty()) {
            System.out.println("这里可以看到: " + current.getItemsDescription());
        }
        System.out.println("氧气剩余: " + player.getOxygen() + " | 生命值: " + player.getHealth());
    }

    // Delegated methods
    public Player getPlayer() { return player; }
    public GameState getState() { return state; }
    public void setState(GameState state) { this.state = state; }
    public Room getCurrentRoom() { return player.getCurrentRoom(); }
}
