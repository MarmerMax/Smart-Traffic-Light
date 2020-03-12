package Objects.CrossroadInfo;

import Objects.Crossroad.Crossroad;

public class CrossroadInfo {

    private Crossroad crossroad;
    private int [] speedLimit; //speedLimitNorth, speedLimitEast, speedLimitSouth, speedLimitWest;
    private int [] actualSpeed; //actualSpeedNorth, actualSpeedEast, actualSpeedSouth, actualSpeedWest;
    private int [] carsCount; //carsCountNorth, carsCountEast, carsCountSouth, carsCountWest;

    public CrossroadInfo(Crossroad crossroad){
        this.crossroad = crossroad;
        speedLimit = new int[4];
        actualSpeed = new int[4];
        carsCount = new int[4];
    }

    public Crossroad getCrossroad(){
        return crossroad;
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
