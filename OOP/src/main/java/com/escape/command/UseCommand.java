package com.escape.command;

import com.escape.game.Game;
import com.escape.item.*;
import com.escape.world.PuzzleRoom;
import com.escape.world.Room;
import com.escape.world.BattleRoom;

public class UseCommand implements Command {
    private final String itemName;
    private final String target;

    public UseCommand(String itemName) { this(itemName, null); }

    public UseCommand(String itemName, String target) {
        this.itemName = itemName;
        this.target = target;
    }

    @Override
    public String execute(Game game) {
        if (itemName == null || itemName.isEmpty()) {
            return "你想使用什么物品？输入 inventory 查看背包。";
        }

        Room current = game.getCurrentRoom();

        // Special: using an item to solve a puzzle
        if (target != null) {
            if (current instanceof PuzzleRoom pr && target.equalsIgnoreCase("puzzle")) {
                return pr.attemptSolve(itemName);
            }
            if (current instanceof PuzzleRoom pr && target.equalsIgnoreCase("panel")) {
                return pr.attemptSolve(itemName);
            }
        }

        // Check if the player has the item
        if (!game.getPlayer().hasItem(itemName)) {
            return "你的背包里没有「" + itemName + "」。";
        }

        Item item = game.getPlayer().removeItem(itemName);

        if (item instanceof MedKit mk) {
            game.getPlayer().heal(mk.getHealAmount());
            return mk.useEffect() + "\n当前生命值：" + game.getPlayer().getHealth();
        } else if (item instanceof Battery bt) {
            game.getPlayer().addOxygen(bt.getOxygenBoost());
            return bt.useEffect() + "\n当前氧气：" + game.getPlayer().getOxygen();
        } else if (item instanceof Weapon wp) {
            game.getPlayer().equipWeapon(wp);
            return wp.useEffect() + "\n当前攻击力：" + game.getPlayer().getAttackPower();
        } else if (item instanceof KeyCard kc) {
            game.getPlayer().addItem(kc);
            return "门禁卡在使用时会自动识别——你朝门禁方向走的时候它会派上用场的。";
        } else {
            game.getPlayer().addItem(item);
            return "现在不是使用「" + itemName + "」的时候。";
        }
    }
}
