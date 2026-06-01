package com.escape.world;

public class ExitRoom extends Room {

    public ExitRoom(String name, String description) {
        super(name, description);
    }

    @Override
    public String onEnter() {
        return "你冲向逃生舱的发射按钮——";
    }
}
