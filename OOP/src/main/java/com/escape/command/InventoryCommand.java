package com.escape.command;

import com.escape.game.Game;
import com.escape.item.Item;

import java.util.List;

public class InventoryCommand implements Command {

    @Override
    public String execute(Game game) {
        List<Item> inventory = game.getPlayer().getInventory();
        StringBuilder sb = new StringBuilder();

        sb.append("--- 背包 ---\n");
        if (inventory.isEmpty()) {
            sb.append("空空如也。");
        } else {
            for (Item item : inventory) {
                sb.append("[").append(item.getName()).append("] ")
                  .append(item.getDescription()).append("\n");
            }
        }

        Item weapon = game.getPlayer().getWeapon();
        if (weapon != null) {
            sb.append("\n已装备武器: [").append(weapon.getName()).append("]");
        }

        sb.append("\n攻击力: ").append(game.getPlayer().getAttackPower());
        sb.append(" | 生命值: ").append(game.getPlayer().getHealth());
        sb.append(" | 氧气: ").append(game.getPlayer().getOxygen());

        return sb.toString();
    }
}
