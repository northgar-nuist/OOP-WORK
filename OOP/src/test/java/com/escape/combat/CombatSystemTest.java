package com.escape.combat;

import com.escape.entity.*;
import com.escape.world.NormalRoom;
import com.escape.item.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombatSystemTest {
    private Player player;
    private Enemy enemy;

    @BeforeEach
    void setUp() {
        player = new Player(new NormalRoom("测试", ""));
        enemy = new PatrolBot();
    }

    @Test
    void testPlayerAttackDealsDamage() {
        int beforeHealth = enemy.getHealth();
        String result = CombatSystem.resolvePlayerAttack(player, enemy);
        assertNotNull(result);
        assertTrue(enemy.getHealth() < beforeHealth || !enemy.isAlive());
    }

    @Test
    void testDefendDealsReducedDamage() {
        // Defend always deals some damage
        int beforeHealth = enemy.getHealth();
        String result = CombatSystem.resolveDefend(player, enemy);
        assertNotNull(result);
        assertTrue(enemy.getHealth() <= beforeHealth);
    }

    @Test
    void testEnemyAttackDamagesPlayer() {
        int beforeHealth = player.getHealth();
        String result = CombatSystem.resolveEnemyAttack(player, enemy);
        assertNotNull(result);
        assertTrue(player.getHealth() < beforeHealth);
    }

    @Test
    void testEnemyAttackOnDefendingReducesDamage() {
        int beforeHealth = player.getHealth();
        String result = CombatSystem.resolveEnemyAttackOnDefending(player, enemy);
        assertNotNull(result);
        assertTrue(player.getHealth() < beforeHealth);
    }

    @Test
    void testWeaponIncreasesPlayerDamage() {
        Weapon wrench = new Weapon("扳手", 30);
        player.equipWeapon(wrench);
        assertEquals(45, player.getAttackPower()); // 15 base + 30 bonus
    }

    @Test
    void testEnemyCanBeKilled() {
        enemy = new PatrolBot(); // 40 HP
        // Attack repeatedly until dead
        int maxAttacks = 20;
        for (int i = 0; i < maxAttacks && enemy.isAlive(); i++) {
            CombatSystem.resolvePlayerAttack(player, enemy);
        }
        assertFalse(enemy.isAlive());
    }

    @Test
    void testPlayerCanDie() {
        while (player.isAlive()) {
            CombatSystem.resolveEnemyAttack(player, enemy);
        }
        assertFalse(player.isAlive());
        assertEquals(0, player.getHealth());
    }

    @Test
    void testBossBotHasMoreHealth() {
        Enemy boss = new BossBot();
        assertEquals(100, boss.getHealth());
        assertTrue(boss.getAttackPower() > 10);
    }

    @Test
    void testSentryBotStats() {
        SentryBot sentry = new SentryBot();
        assertEquals(100, sentry.getHealth());
        assertEquals(15, sentry.getAttackPower()); // normal mode
        assertTrue(sentry.getAttackPower() > 10);
    }

    @Test
    void testSentryBotCharge() {
        SentryBot sentry = new SentryBot();
        assertFalse(sentry.isCharging());
        sentry.endTurn(); // turn 1, not charging
        assertFalse(sentry.isCharging());
        sentry.endTurn(); // turn 2, starts charging
        assertTrue(sentry.isCharging());
        assertEquals(90, sentry.getAttackPower());
        sentry.endTurn(); // turn 3, stops charging
        assertFalse(sentry.isCharging());
        assertEquals(15, sentry.getAttackPower());
    }
}
