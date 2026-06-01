package com.escape.game;

public final class GameConfig {
    private GameConfig() {}

    public static final int MAX_OXYGEN = 30;
    public static final int MAX_HEALTH = 100;
    public static final int DEFAULT_ATTACK = 15;
    public static final int DEFAULT_DEFENSE = 5;

    public static final String GAME_TITLE = "=== 星际逃生 (Space Escape) ===";
    public static final String INTRO = """
            你是一位在深空号空间站工作的工程师。
            空间站突发故障，所有系统崩溃，氧气系统正在失效。
            你必须在氧气耗尽之前找到逃生舱！

            输入 'help' 查看可用命令。
            """;
}
