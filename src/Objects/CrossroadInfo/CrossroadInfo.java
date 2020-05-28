package Objects.CrossroadInfo;

import Objects.Crossroad.Crossroad;
import Objects.CrossroadInfo.DirectionInfo.DirectionInfo;

@SuppressWarnings("Duplicates")
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
        this.crossroad = new Crossroad();
        this.east = new DirectionInfo(crossroadInfo.getEast());
        this.west = new DirectionInfo(crossroadInfo.getWest());
        this.south = new DirectionInfo(crossroadInfo.getSouth());
        this.north = new DirectionInfo(crossroadInfo.getNorth());
    }

    public Crossroad getCrossroad() {
        return crossroad;
    }

    public void setCrossroadInfo(int [] carsCount, int [] speedLimit, int [] actualSpeed){
            north = new DirectionInfo(carsCount[0], speedLimit[0], actualSpeed[0]);
            east = new DirectionInfo(carsCount[1], speedLimit[1], actualSpeed[1]);
            south = new DirectionInfo(carsCount[2], speedLimit[2], actualSpeed[2]);
            west = new DirectionInfo(carsCount[3], speedLimit[3], actualSpeed[3]);
    }

    private boolean checkData(String[] data) {
        for (int i = 0; i < 4; i++) {
            if (!isInt(data[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean isInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
