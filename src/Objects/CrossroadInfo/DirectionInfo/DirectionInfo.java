package Objects.CrossroadInfo.DirectionInfo;

public class DirectionInfo {

    private int speedLimit;
    private int actualSpeed;
    private int carsCount;

    public DirectionInfo(int carsCount, int speedLimit, int actualSpeed) {
        if (carsCount < 0 || speedLimit < 0 || actualSpeed < 0) {
            System.err.println("Bad input arguments on DirectionInfo constructor...");
        }
        this.carsCount = carsCount;
        this.speedLimit = speedLimit;
        this.actualSpeed = actualSpeed;
    }

    public DirectionInfo(DirectionInfo directionInfo) {
        this.carsCount = directionInfo.getCarsCount();
        this.speedLimit = directionInfo.getSpeedLimit();
        this.actualSpeed = directionInfo.getActualSpeed();
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public int getActualSpeed() {
        return actualSpeed;
    }

    public int getCarsCount() {
        return carsCount;
    }
}
