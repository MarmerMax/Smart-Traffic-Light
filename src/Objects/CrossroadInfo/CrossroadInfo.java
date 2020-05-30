package Objects.CrossroadInfo;

import Objects.Crossroad.Crossroad;
import Objects.CrossroadInfo.DirectionInfo.DirectionInfo;
import Objects.Road.Road;

public class CrossroadInfo {

    private Crossroad crossroad;
    private DirectionInfo north;
    private DirectionInfo east;
    private DirectionInfo south;
    private DirectionInfo west;


    public CrossroadInfo(Crossroad crossroad) {
        this.crossroad = crossroad;
    }

    public CrossroadInfo(CrossroadInfo crossroadInfo) {
        Road r1 = new Road(crossroadInfo.getCrossroad().getNorthRoad().getId());
        Road r2 = new Road(crossroadInfo.getCrossroad().getEastRoad().getId());
        Road r3 = new Road(crossroadInfo.getCrossroad().getSouthRoad().getId());
        Road r4 = new Road(crossroadInfo.getCrossroad().getWestRoad().getId());
        Road[] roads = {r1, r2, r3, r4};
        crossroad = new Crossroad(roads);
        east = new DirectionInfo(crossroadInfo.getEast());
        west = new DirectionInfo(crossroadInfo.getWest());
        south = new DirectionInfo(crossroadInfo.getSouth());
        north = new DirectionInfo(crossroadInfo.getNorth());
    }

    public Crossroad getCrossroad() {
        return crossroad;
    }

    public void setCrossroadInfo(int[] carsCount, int[] speedLimit, int[] actualSpeed) {
        north = new DirectionInfo(carsCount[0], speedLimit[0], actualSpeed[0]);
        east = new DirectionInfo(carsCount[1], speedLimit[1], actualSpeed[1]);
        south = new DirectionInfo(carsCount[2], speedLimit[2], actualSpeed[2]);
        west = new DirectionInfo(carsCount[3], speedLimit[3], actualSpeed[3]);
    }

    public DirectionInfo getNorth() {
        return north;
    }

    public DirectionInfo getEast() {
        return east;
    }

    public DirectionInfo getSouth() {
        return south;
    }

    public DirectionInfo getWest() {
        return west;
    }
}
