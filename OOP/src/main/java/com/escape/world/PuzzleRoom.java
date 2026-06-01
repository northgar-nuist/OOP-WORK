package com.escape.world;

public class PuzzleRoom extends Room {
    private final String lockedDirection;
    private boolean solved;

    public PuzzleRoom(String name, String description, String lockedDirection) {
        super(name, description);
        this.lockedDirection = lockedDirection;
        this.solved = false;
    }

    @Override
    public boolean canPass(String direction) {
        if (direction.equalsIgnoreCase(lockedDirection) && !solved) {
            return false;
        }
        return hasExit(direction);
    }

    public boolean isSolved() { return solved; }

    public String attemptSolve(String answer) {
        if (solved) {
            return "电路已经修复好了。";
        }
        if (answer.equals("42") || answer.equalsIgnoreCase("reboot") || answer.equalsIgnoreCase("重启")) {
            solved = true;
            return "你重新接通了电路！引擎重新启动，通往下一区域的通道打开了。";
        }
        return "这样做不起作用... 系统提示需要输入维修代码。再想想看？";
    }

    public String getPuzzleHint() {
        return """
                引擎控制面板上显示着一行闪烁的文字：
                "系统故障 — 核心电路断开。请输入维修代码："
                旁边贴着一张泛黄的便签：「生命、宇宙和一切的答案」
                """;
    }
}
