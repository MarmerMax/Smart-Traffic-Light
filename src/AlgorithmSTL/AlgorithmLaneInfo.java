package AlgorithmSTL;

import SystemSTL.LaneInfo;

public class AlgorithmLaneInfo {

    private int cars_count;
    private double distance_from_crossroad;
    private double speed_limit;
    private double avg_car_length;

    public AlgorithmLaneInfo(LaneInfo lane_info) {
        this.cars_count = lane_info.getCarsInLane().size();
        this.distance_from_crossroad = lane_info.getLastCarDistance();
        this.speed_limit = lane_info.getSpeedLimit();
        this.avg_car_length = distance_from_crossroad / cars_count;
    }

    public AlgorithmLaneInfo(double zero) {
        this.cars_count = (int) zero;
        this.distance_from_crossroad = zero;
        this.speed_limit = zero;
        this.avg_car_length = zero;
    }

    public int getCarsCount() {
        return cars_count;
    }

    public double getDistanceFromCrossroad() {
        return distance_from_crossroad;
    }

    public double getSpeedLimit() {
        return speed_limit;
    }

    public void setCarsCount(int cars_count) {
        this.cars_count = cars_count;
    }

    public double getAvgCarLength() {
        return avg_car_length;
    }
}
