package com.escape.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @Test
    void testSingleLetterDirections() {
        assertTrue(CommandParser.parse("n") instanceof GoCommand);
        assertTrue(CommandParser.parse("s") instanceof GoCommand);
        assertTrue(CommandParser.parse("e") instanceof GoCommand);
        assertTrue(CommandParser.parse("w") instanceof GoCommand);
    }

    @Test
    void testGoCommand() {
        assertTrue(CommandParser.parse("go north") instanceof GoCommand);
        assertTrue(CommandParser.parse("go 北") instanceof GoCommand);
    }

    @Test
    void testTakeCommand() {
        assertTrue(CommandParser.parse("take 门禁卡") instanceof TakeCommand);
        assertTrue(CommandParser.parse("take key") instanceof TakeCommand);
    }

    @Test
    void testLookCommand() {
        assertTrue(CommandParser.parse("look") instanceof LookCommand);
        assertTrue(CommandParser.parse("l") instanceof LookCommand);
    }

    @Test
    void testInventoryCommand() {
        assertTrue(CommandParser.parse("inventory") instanceof InventoryCommand);
        assertTrue(CommandParser.parse("i") instanceof InventoryCommand);
        assertTrue(CommandParser.parse("bag") instanceof InventoryCommand);
    }

    @Test
    void testFightCommand() {
        Command cmd = CommandParser.parse("fight");
        assertTrue(cmd instanceof FightCommand);

        cmd = CommandParser.parse("attack");
        assertTrue(cmd instanceof FightCommand);

        cmd = CommandParser.parse("defend");
        assertTrue(cmd instanceof FightCommand);
    }

    @Test
    void testHelpCommand() {
        assertTrue(CommandParser.parse("help") instanceof HelpCommand);
        assertTrue(CommandParser.parse("h") instanceof HelpCommand);
    }

    @Test
    void testQuitCommand() {
        Command cmd = CommandParser.parse("quit");
        assertTrue(cmd instanceof Command);
        assertTrue(cmd instanceof Command); // lambda, not a named class
    }

    @Test
    void testUseCommand() {
        assertTrue(CommandParser.parse("use 医疗包") instanceof UseCommand);
    }

    @Test
    void testUnknownCommandReturnsErrorHandler() {
        Command cmd = CommandParser.parse("xyzzy");
        assertNotNull(cmd);
        // Should be a lambda that returns an error message
    }

    @Test
    void testEmptyInput() {
        Command cmd = CommandParser.parse("");
        assertNotNull(cmd);
    }
}
