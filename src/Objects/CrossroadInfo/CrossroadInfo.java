package Objects.CrossroadInfo;

import Objects.Crossroad.Crossroad;
import Objects.CrossroadInfo.DirectionInfo.DirectionInfo;
import Objects.Road.Road;
import Tools.Constants;

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

        int north_dir = Constants.NORTH_DIRECTION;
        int east_dir = Constants.EAST_DIRECTION;
        int south_dir = Constants.SOUTH_DIRECTION;
        int west_dir = Constants.WEST_DIRECTION;

        north = new DirectionInfo(carsCount[north_dir], speedLimit[north_dir], actualSpeed[north_dir]);
        east = new DirectionInfo(carsCount[east_dir], speedLimit[east_dir], actualSpeed[east_dir]);
        south = new DirectionInfo(carsCount[south_dir], speedLimit[south_dir], actualSpeed[south_dir]);
        west = new DirectionInfo(carsCount[west_dir], speedLimit[west_dir], actualSpeed[west_dir]);
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

    public double getPhaseTime(){
        return crossroad.getPhaseTime();
    }

}
