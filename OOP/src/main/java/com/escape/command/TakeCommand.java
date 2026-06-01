package com.escape.command;

import com.escape.game.Game;
import com.escape.item.Item;
import com.escape.world.Room;

public class TakeCommand implements Command {
    private final String itemName;

    public TakeCommand(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String execute(Game game) {
        Room current = game.getCurrentRoom();

        if (itemName == null || itemName.isEmpty()) {
            if (current.getItems().isEmpty()) {
                return "这里没有任何可以拾取的东西。";
            }
            return "你想拿什么？这里可以看到：" + current.getItemsDescription();
        }

        Item taken = current.removeItem(itemName);
        if (taken == null) {
            return "这里没有叫「" + itemName + "」的东西。";
        }

        game.getPlayer().addItem(taken);
        return "你拿起了「" + taken.getName() + "」——" + taken.getDescription();
    }
}
