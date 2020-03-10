package Objects.CrossroadInfo;

public class CrossroadInfo {

    private int crossroadSize;
    private int [] speedLimit; //speedLimitNorth, speedLimitSouth, speedLimitEast, speedLimitWest;
    private int [] actualSpeed; //actualSpeedNorth, actualSpeedSouth, actualSpeedEast, actualSpeedWest;
    private int [] carsCount; //carsCountNorth, carsCountSouth, carsCountEast, carsCountWest;

    public CrossroadInfo(int size){
        crossroadSize = size;
        speedLimit = new int[4];
        actualSpeed = new int[4];
        carsCount = new int[4];
    }

    public boolean setSpeedLimit(String [] data){
        for(int i = 0; i < 4; i++){
            if(isInt(data[i])){
                speedLimit[i] = Integer.parseInt(data[i]);
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean setActualSpeed(String [] data){
        for(int i = 0; i < 4; i++){
            if(isInt(data[i])){
                actualSpeed[i] = Integer.parseInt(data[i]);
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean setCarsCount(String [] data){
        for(int i = 0; i < 4; i++){
            if(isInt(data[i])){
                carsCount[i] = Integer.parseInt(data[i]);
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean isInt(String value){
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
