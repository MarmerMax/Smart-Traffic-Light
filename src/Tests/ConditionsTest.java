package Tests;

import Objects.Conditions.Conditions;
import Objects.Crossroad.Crossroad;
import Objects.CrossroadInfo.CrossroadInfo;
import Objects.Road.Road;
import Objects.Road.RoadCreator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConditionsTest {

    private static Conditions conditions;

    @BeforeAll
    static void setup() {
        Road[] roads1 = RoadCreator.createRoads(40, 342);
        Crossroad crossroad1 = new Crossroad(roads1);
        CrossroadInfo crossroadInfo1 = new CrossroadInfo(crossroad1);

        Road[] roads2 = RoadCreator.createRoads(53, 342);
        Crossroad crossroad2 = new Crossroad(roads2);
        CrossroadInfo crossroadInfo2 = new CrossroadInfo(crossroad2);

        conditions = new Conditions(crossroadInfo1, crossroadInfo2);
    }


    @Test
    void getCrossroadInfo1() {
        assertEquals(conditions.getFirstCrossroadInfo().getCrossroad().getNorthRoad().getId(), 40);
        assertEquals(conditions.getFirstCrossroadInfo().getCrossroad().getEastRoad().getId(), 342);
        assertEquals(conditions.getFirstCrossroadInfo().getCrossroad().getSouthRoad().getId(), 40);
        assertEquals(conditions.getFirstCrossroadInfo().getCrossroad().getWestRoad().getId(), 342);
        assertNotNull(conditions.getFirstCrossroadInfo());
    }

    @Test
    void getCrossroadInfo2() {
        assertEquals(conditions.getSecondCrossroadInfo().getCrossroad().getNorthRoad().getId(), 53);
        assertEquals(conditions.getSecondCrossroadInfo().getCrossroad().getEastRoad().getId(), 342);
        assertEquals(conditions.getSecondCrossroadInfo().getCrossroad().getSouthRoad().getId(), 53);
        assertEquals(conditions.getSecondCrossroadInfo().getCrossroad().getWestRoad().getId(), 342);
        assertNotNull(conditions.getSecondCrossroadInfo());
    }
}