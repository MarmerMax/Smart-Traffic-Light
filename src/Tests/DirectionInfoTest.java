package Tests;

import Objects.CrossroadInfo.DirectionInfo.DirectionInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionInfoTest {

    private DirectionInfo directionInfo;

    @BeforeEach
    void init() {
        directionInfo = new DirectionInfo(5, 70, 70);
    }

    @Test
    void getSpeedLimit() {
        assertEquals(directionInfo.getSpeedLimit(), 70);
    }

    @Test
    void getActualSpeed() {
        assertEquals(directionInfo.getActualSpeed(), 70);
    }

    @Test
    void getCarsCount() {
        assertEquals(directionInfo.getCarsCount(), 5);
    }
}