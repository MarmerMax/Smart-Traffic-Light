package Tests;

import Objects.Road.Road;
import Objects.Road.RoadCreator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoadCreatorTest {

    @Test
    void createRoads() {
        Road[] roads = RoadCreator.createRoads(45, 67);
        assertEquals(roads[0].getId(), 45);
        assertEquals(roads[1].getId(), 67);
        assertEquals(roads[2].getId(), 45);
        assertEquals(roads[3].getId(), 67);
        assertNotNull(roads);
    }
}