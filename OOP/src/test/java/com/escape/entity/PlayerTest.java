package com.escape.entity;

import com.escape.item.*;
import com.escape.world.NormalRoom;
import com.escape.world.Room;
import com.escape.game.GameConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private Room startingRoom;

    @BeforeEach
    void setUp() {
        startingRoom = new NormalRoom("测试房间", "一个测试房间。");
        player = new Player(startingRoom);
    }

    @Test
    void testInitialState() {
        assertEquals(GameConfig.MAX_HEALTH, player.getHealth());
        assertEquals(GameConfig.MAX_OXYGEN, player.getOxygen());
        assertEquals(startingRoom, player.getCurrentRoom());
        assertTrue(player.getInventory().isEmpty());
        assertNull(player.getWeapon());
    }

    @Test
    void testTakeDamage() {
        player.takeDamage(30);
        assertEquals(70, player.getHealth());
    }

    @Test
    void testDamageCannotGoBelowZero() {
        player.takeDamage(200);
        assertEquals(0, player.getHealth());
        assertFalse(player.isAlive());
    }

    @Test
    void testHeal() {
        player.takeDamage(50);
        player.heal(20);
        assertEquals(70, player.getHealth());
    }

    @Test
    void testHealCannotExceedMax() {
        player.takeDamage(10);
        player.heal(50);
        assertEquals(GameConfig.MAX_HEALTH, player.getHealth());
    }

    @Test
    void testAddAndRemoveItem() {
        Item keyCard = new KeyCard("门禁卡", "测试卡", "testDoor");
        player.addItem(keyCard);
        assertTrue(player.hasItem("门禁卡"));
        assertEquals(1, player.getInventory().size());

        Item removed = player.removeItem("门禁卡");
        assertNotNull(removed);
        assertFalse(player.hasItem("门禁卡"));
    }

    @Test
    void testRemoveNonexistentItem() {
        assertNull(player.removeItem("不存在的东西"));
    }

    @Test
    void testEquipWeapon() {
        Weapon wrench = new Weapon("扳手", 10);
        player.addItem(wrench);
        player.equipWeapon(wrench);

        assertEquals(wrench, player.getWeapon());
        assertFalse(player.hasItem("扳手"));
        assertTrue(player.getAttackPower() > GameConfig.DEFAULT_ATTACK);
    }

    @Test
    void testOxygenTick() {
        assertEquals(30, player.getOxygen());
        player.tickOxygen();
        assertEquals(29, player.getOxygen());
    }

    @Test
    void testAddOxygen() {
        player.setOxygen(10);
        player.addOxygen(15);
        assertEquals(25, player.getOxygen());
    }

    @Test
    void testMoveBetweenRooms() {
        Room other = new NormalRoom("另一个房间", "测试");
        player.setCurrentRoom(other);
        assertEquals(other, player.getCurrentRoom());
    }

    @Test
    void testUseMedKit() {
        player.takeDamage(40);
        MedKit medKit = new MedKit(20);
        player.addItem(medKit);
        assertTrue(player.hasItem("医疗包"));

        Item taken = player.removeItem("医疗包");
        assertNotNull(taken);
        player.heal(((MedKit) taken).getHealAmount());
        assertEquals(80, player.getHealth());
    }

    @Test
    void testUseBattery() {
        player.setOxygen(5);
        Battery battery = new Battery(15);
        player.addItem(battery);
        assertTrue(player.hasItem("电池"));

        Item taken = player.removeItem("电池");
        player.addOxygen(((Battery) taken).getOxygenBoost());
        assertEquals(20, player.getOxygen());
    }
}
