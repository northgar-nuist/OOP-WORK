package com.escape.item;

public class KeyCard extends Item {
    private final String doorId;

    public KeyCard(String name, String description, String doorId) {
        super(name, description);
        this.doorId = doorId;
    }

    public String getDoorId() { return doorId; }

    @Override
    public String useEffect() {
        return "你用" + name + "刷过了门禁——绿灯亮起，门开了。";
    }
}
