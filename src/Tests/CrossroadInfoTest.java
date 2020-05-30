package Tests;

import Objects.Crossroad.Crossroad;
import Objects.CrossroadInfo.CrossroadInfo;
import Objects.Road.Road;
import Objects.Road.RoadCreator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrossroadInfoTest {


    private static CrossroadInfo crossroadInfo;

    @BeforeAll
    static void setup(){
        Road[] roads1 = RoadCreator.createRoads(40, 342);
        Crossroad crossroad1 = new Crossroad(roads1);
        int [] cars = {5,5,5,5};
        int [] limit = {5,5,5,5};
        int [] actual = {5,5,5,5};

        crossroadInfo = new CrossroadInfo(crossroad1);
        crossroadInfo.setCrossroadInfo(cars, limit, actual);
    }

    @Test
    void getCrossroad() {
        assertNotNull(crossroadInfo.getCrossroad());
    }

    @Test
    void getNorth() {
        assertNotNull(crossroadInfo.getNorth());
    }

    @Test
    void getEast() {
        assertNotNull(crossroadInfo.getEast());
    }

    @Test
    void getSouth() {
        assertNotNull(crossroadInfo.getSouth());
    }

    @Test
    void getWest() {
        assertNotNull(crossroadInfo.getEast());
    }
}