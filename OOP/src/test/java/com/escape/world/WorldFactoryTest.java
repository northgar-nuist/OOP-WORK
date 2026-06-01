package com.escape.world;

import com.escape.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldFactoryTest {
    private WorldFactory factory;
    private Room startRoom;

    @BeforeEach
    void setUp() {
        factory = new WorldFactory();
        startRoom = factory.createWorld();
    }

    @Test
    void testWorldCreationReturnsNonNull() {
        assertNotNull(startRoom);
    }

    @Test
    void testStartRoomIsCrewQuarters() {
        assertEquals("船员舱", startRoom.getName());
        assertTrue(startRoom instanceof NormalRoom);
    }

    @Test
    void testStartRoomHasBattery() {
        boolean hasBattery = startRoom.getItems().stream()
                .anyMatch(i -> i.getName().equals("电池"));
        assertTrue(hasBattery);
    }

    @Test
    void testStartRoomHasExitEast() {
        assertTrue(startRoom.hasExit("东"));
        Room corridorA = startRoom.getExit("东");
        assertNotNull(corridorA);
        assertEquals("走廊A", corridorA.getName());
    }

    @Test
    void testStorageHasKeyCardAndWeapon() {
        Room corridorA = startRoom.getExit("东");
        Room storage = corridorA.getExit("北");
        assertEquals("储藏室", storage.getName());
        boolean hasKeyCard = storage.getItems().stream()
                .anyMatch(i -> i.getName().equals("门禁卡"));
        boolean hasWeapon = storage.getItems().stream()
                .anyMatch(i -> i.getName().equals("扳手"));
        assertTrue(hasKeyCard);
        assertTrue(hasWeapon);
    }

    @Test
    void testMedBayHasMedKit() {
        Room corridorA = startRoom.getExit("东");
        Room storage = corridorA.getExit("北");
        Room medBay = storage.getExit("北");
        assertEquals("医疗室", medBay.getName());
        boolean hasMedKit = medBay.getItems().stream()
                .anyMatch(i -> i.getName().equals("医疗包"));
        assertTrue(hasMedKit);
    }

    @Test
    void testSecurityRoomIsBattleRoomWithSentry() {
        Room corridorA = startRoom.getExit("东");
        Room security = corridorA.getExit("东");
        assertTrue(security instanceof BattleRoom);
        assertEquals("安保室", security.getName());
        assertTrue(security.hasEnemy());
        assertEquals("哨兵机器人", security.getEnemy().getName());
    }

    @Test
    void testEngineRoomIsPuzzleRoom() {
        Room corridorA = startRoom.getExit("东");
        Room security = corridorA.getExit("东");
        Room engine = security.getExit("东");
        assertTrue(engine instanceof PuzzleRoom);
        assertEquals("机舱", engine.getName());
    }

    @Test
    void testEscapePodIsExitRoom() {
        Room corridorA = startRoom.getExit("东");
        Room security = corridorA.getExit("东");
        Room engine = security.getExit("东");
        Room corridorB = engine.getExit("东");
        Room escapePod = corridorB.getExit("东");
        assertTrue(escapePod instanceof ExitRoom);
        assertEquals("逃生舱", escapePod.getName());
    }

    @Test
    void testBidirectionalExits() {
        Room corridorA = startRoom.getExit("东");
        Room backToStart = corridorA.getExit("西");
        assertEquals(startRoom, backToStart);
    }

    @Test
    void testWorldHasEightRooms() {
        java.util.Set<String> visited = new java.util.HashSet<>();
        explore(startRoom, visited);
        assertEquals(8, visited.size());
    }

    @Test
    void testBattleRoomAllowsRetreat() {
        Room corridorA = startRoom.getExit("东");
        Room security = corridorA.getExit("东");
        BattleRoom br = (BattleRoom) security;
        // East should be blocked by enemy
        assertFalse(br.canPass("东"));
        // West (retreat) should be allowed
        assertTrue(br.canPass("西"));
    }

    private void explore(Room room, java.util.Set<String> visited) {
        if (room == null || visited.contains(room.getName())) return;
        visited.add(room.getName());
        for (var entry : new java.util.LinkedHashMap<>(room.exits).entrySet()) {
            explore(entry.getValue(), visited);
        }
    }
}
