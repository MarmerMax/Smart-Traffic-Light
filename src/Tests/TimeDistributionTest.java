package Tests;

import Objects.TrafficLight.TrafficLightTimeDistribution.TimeDistribution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeDistributionTest {

    private TimeDistribution timeDistribution;

    @BeforeEach
    void init() {
        timeDistribution = new TimeDistribution(40);
    }

    @Test
    void addTimeToEastWestRoute() {
        for (int i = 0; i < 5; i++) {
            timeDistribution.addTimeToEastWestRoute();
        }
        assertEquals(timeDistribution.getEastWest(), 25);
    }

    @Test
    void addTimeToNorthSouthRoute() {
        for (int i = 0; i < 5; i++) {
            timeDistribution.addTimeToNorthSouthRoute();
        }
        assertEquals(timeDistribution.getNorthSouth(), 25);
    }

    @Test
    void setEastWest() {
        timeDistribution.setEastWest(30);
        assertEquals(timeDistribution.getEastWest(), 30);
        assertEquals(timeDistribution.getNorthSouth(), 10);
    }

    @Test
    void setNorthSouth() {
        timeDistribution.setNorthSouth(30);
        assertEquals(timeDistribution.getNorthSouth(), 30);
        assertEquals(timeDistribution.getEastWest(), 10);
    }

    @Test
    void getNorthSouth() {
        assertEquals(timeDistribution.getNorthSouth(), 20);
    }

    @Test
    void getEastWest() {
        assertEquals(timeDistribution.getEastWest(), 20);
    }

    @Test
    void getChangingExecutionTime() {
        assertEquals(timeDistribution.getChangingExecutionTime(), 2);
    }

    @Test
    void getMinTime(){
        assertEquals(timeDistribution.getMinTime(), 10);
    }
}