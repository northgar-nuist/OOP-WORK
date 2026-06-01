package com.escape.command;

import com.escape.game.Game;
import com.escape.game.GameState;

import java.util.Map;

/** Parses raw player input into Command objects (part of the Command pattern). */
public class CommandParser {

    private static final Map<String, String> DIRECTION_ALIASES = Map.of(
            "n", "北", "north", "北",
            "s", "南", "south", "南",
            "e", "东", "east", "东",
            "w", "西", "west", "西"
    );

    public static Command parse(String input) {
        String trimmed = input.trim().toLowerCase();

        // Single-letter directions
        if (DIRECTION_ALIASES.containsKey(trimmed)) {
            return new GoCommand(DIRECTION_ALIASES.get(trimmed));
        }

        String[] parts = trimmed.split("\\s+", 3);

        return switch (parts[0]) {
            case "go", "move", "walk" -> {
                if (parts.length < 2) {
                    yield game -> "你想往哪个方向走？可用方向：" + game.getCurrentRoom().getExitsDescription();
                }
                String dir = DIRECTION_ALIASES.getOrDefault(parts[1], parts[1]);
                yield new GoCommand(dir);
            }
            case "take", "get", "pick" -> {
                String name = parts.length > 1 ? input.substring(parts[0].length()).trim() : null;
                yield new TakeCommand(name);
            }
            case "use" -> {
                if (parts.length < 2) {
                    yield game -> "你想使用什么物品？";
                }
                // Check for "use X on Y" pattern
                if (parts.length >= 3 && parts[2].startsWith("on ")) {
                    String item = parts[1];
                    String target = parts[2].substring(3);
                    yield new UseCommand(item, target);
                }
                yield new UseCommand(input.substring(4).trim());
            }
            case "fight", "attack", "kill", "hit" -> new FightCommand("attack");
            case "defend", "block", "dodge" -> new FightCommand("defend");
            case "look", "l" -> new LookCommand();
            case "inventory", "i", "inv", "bag", "backpack" -> new InventoryCommand();
            case "hint", "tip" -> new HintCommand();
            case "help", "h", "?" -> new HelpCommand();
            case "quit", "exit", "q" -> {
                yield game -> {
                    game.setState(GameState.QUIT);
                    return "你放弃了逃生...游戏结束。";
                };
            }
            default -> game -> "不明白「" + input + "」是什么意思。输入 'help' 查看可用命令。";
        };
    }
}
