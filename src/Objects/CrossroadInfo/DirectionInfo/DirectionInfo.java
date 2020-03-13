package Objects.CrossroadInfo.DirectionInfo;

public class DirectionInfo {

    private int speedLimit;
    private int actualSpeed;
    private int carsCount;

    public DirectionInfo(String carsCount, String speedLimit, String actualSpeed){
        this.carsCount = parseToInt(carsCount);
        this.speedLimit = parseToInt(speedLimit);
        this.actualSpeed = parseToInt(actualSpeed);
    }

    private int parseToInt(String value){
        return Integer.parseInt(value);
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
