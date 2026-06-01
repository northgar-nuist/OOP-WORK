package com.escape.world;

import com.escape.entity.*;
import com.escape.item.*;

/**
 * Factory pattern: constructs the entire game world.
 * Separates world-building from game logic.
 */
public class WorldFactory {

    public Room createWorld() {
        // === Rooms ===
        Room crewQuarters = new NormalRoom("船员舱",
                "你从昏迷中醒来，头痛欲裂。这是你在深空号上的舱室。\n" +
                "应急灯发出暗红色的光，警报声在远处隐约可闻。\n" +
                "床头柜上有一块备用电池——你最好带上它。");

        Room corridorA = new NormalRoom("走廊A",
                "一条主走廊，应急灯在头顶忽明忽暗。\n" +
                "往北是储藏室的方向，往南是你刚来的船员舱，往东则是安保控制区——\n" +
                "那边隐约传来机械运作的声音，不太妙。");

        Room storage = new NormalRoom("储藏室",
                "堆满货架的储藏室。角落的工具箱旁边有一张门禁卡和一把扳手。\n" +
                "北面的门通往医疗室。");

        Room medBay = new NormalRoom("医疗室",
                "小型医疗舱，大部分药品都被撤离的人拿走了。\n" +
                "检查台下面的抽屉里还剩下一个医疗包。");

        Room securityRoom = new BattleRoom("安保室",
                "空间站的安全控制中心。整面墙都是监控屏幕，大多已经损坏。\n" +
                "一台哨兵炮塔从天花板上垂下来，红色瞄准激光扫过房间——\n" +
                "你得先摧毁它才能继续往东走。往西可以退回走廊。",
                new SentryBot(), "东");

        Room engineRoom = new PuzzleRoom("机舱",
                "巨大的引擎核心占据了房间中央。管道和线缆从四面八方汇聚过来。\n" +
                "核心电路板有明显的烧毁痕迹——不修好它，通往东侧的通道就打不开。",
                "东");

        Room corridorB = new NormalRoom("走廊B",
                "这条走廊干净得反常，墙上装着一个门禁读卡器，红灯闪烁。\n" +
                "往东通往逃生舱——但得先刷开门禁。",
                "东", "门禁卡");

        Room escapePod = new ExitRoom("逃生舱",
                "逃生舱就在眼前！透过舷窗可以看到外面无垠的星空。\n" +
                "控制面板已经预设好了回地球的导航坐标。");

        // === Wire exits (bidirectional) ===
        // crewQuarters <-> corridorA
        crewQuarters.addExit("东", corridorA);
        corridorA.addExit("西", crewQuarters);

        // corridorA <-> storage
        corridorA.addExit("北", storage);
        storage.addExit("南", corridorA);

        // storage <-> medBay
        storage.addExit("北", medBay);
        medBay.addExit("南", storage);

        // corridorA <-> securityRoom (BattleRoom, blocks east)
        corridorA.addExit("东", securityRoom);
        securityRoom.addExit("西", corridorA);

        // securityRoom <-> engineRoom
        securityRoom.addExit("东", engineRoom);
        engineRoom.addExit("西", securityRoom);

        // engineRoom <-> corridorB
        engineRoom.addExit("东", corridorB);
        corridorB.addExit("西", engineRoom);

        // corridorB <-> escapePod
        corridorB.addExit("东", escapePod);
        escapePod.addExit("西", corridorB);

        // === Place items ===
        crewQuarters.addItem(new Battery(10));
        storage.addItem(new KeyCard("门禁卡", "一张标记'B区通行'的磁卡。", "corridorB"));
        storage.addItem(new Weapon("扳手", 20));
        medBay.addItem(new MedKit(35));

        return crewQuarters;
    }
}
