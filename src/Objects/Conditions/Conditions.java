package Objects.Conditions;

import Objects.CrossroadInfo.CrossroadInfo;
import SystemSTL.LaneInfo;
import java.util.ArrayList;

public class Conditions {

    private CrossroadInfo crossroad_info_1;
    private CrossroadInfo crossroad_info_2;

    private ArrayList<LaneInfo> cars_crossroad_1;
    private ArrayList<LaneInfo> cars_crossroad_2;

//    private ArrayList<CarInfo> passed_cars_first_crossroad_south;
//    private ArrayList<CarInfo> passed_cars_first_crossroad_south;

    public Conditions(CrossroadInfo info1, CrossroadInfo info2) {
        crossroad_info_1 = info1;
        crossroad_info_2 = info2;
        createLanesInfo();
    }

    public ArrayList<LaneInfo> getCarsInFirstCrossroad() {
        return cars_crossroad_1;
    }

    public ArrayList<LaneInfo> getCarsInSecondCrossroad() {
        return cars_crossroad_2;
    }

    public Conditions(Conditions conditions) {
        crossroad_info_1 = new CrossroadInfo(conditions.getCrossroadInfo1());
        crossroad_info_2 = new CrossroadInfo(conditions.getCrossroadInfo2());
    }

    public void changeTrafficLightState() {
        crossroad_info_1.getCrossroad().changeTrafficLightStateOnCrossroad();
        crossroad_info_2.getCrossroad().changeTrafficLightStateOnCrossroad();
    }

    public boolean isEastWestActive() {
        return crossroad_info_1.getCrossroad().isEastWestActive() && crossroad_info_2.getCrossroad().isEastWestActive();
    }

    public boolean isNorthSouthActive() {
        return crossroad_info_1.getCrossroad().isNorthSouthActive() && crossroad_info_2.getCrossroad().isNorthSouthActive();
    }

    public double getNorthSouthTimeDistribution() {
        if (crossroad_info_1.getCrossroad().getTimeDistribution().getNorthSouth() !=
                crossroad_info_2.getCrossroad().getTimeDistribution().getNorthSouth()) {
            throw new RuntimeException("Bad time distribution...");
        }
        return crossroad_info_1.getCrossroad().getTimeDistribution().getNorthSouth();
    }

    public double getEastWestTimeDistribution() {
        if (crossroad_info_1.getCrossroad().getTimeDistribution().getEastWest() !=
                crossroad_info_2.getCrossroad().getTimeDistribution().getEastWest()) {
            throw new RuntimeException("Bad time distribution...");
        }
        return crossroad_info_1.getCrossroad().getTimeDistribution().getEastWest();
    }

    public double getChangingLightsTime() {
        return crossroad_info_1.getCrossroad().getTimeDistribution().getChangingExecutionTime();
    }

    public void addTimeToEastWestRoure() {
        crossroad_info_1.getCrossroad().getTimeDistribution().addTimeToEastWestRoute();
        crossroad_info_2.getCrossroad().getTimeDistribution().addTimeToEastWestRoute();
    }

    public void addTimeToNorthSouthRoute() {
        crossroad_info_1.getCrossroad().getTimeDistribution().addTimeToNorthSouthRoute();
        crossroad_info_2.getCrossroad().getTimeDistribution().addTimeToNorthSouthRoute();
    }

    public void setDefaultTimeDistribution() {
        crossroad_info_1.getCrossroad().getTimeDistribution().setDefaultDistribution();
        crossroad_info_2.getCrossroad().getTimeDistribution().setDefaultDistribution();
    }

    public CrossroadInfo getCrossroadInfo1() {
        return crossroad_info_1;
    }

    public CrossroadInfo getCrossroadInfo2() {
        return crossroad_info_2;
    }

    private void createLanesInfo() {
        cars_crossroad_1 = new ArrayList<>();
        createLanesPerCrossroad(cars_crossroad_1, crossroad_info_1);

        cars_crossroad_2 = new ArrayList<>();
        createLanesPerCrossroad(cars_crossroad_2, crossroad_info_2);

    }

    private void createLanesPerCrossroad(ArrayList<LaneInfo> cars, CrossroadInfo actual_crossroad) {
        cars.add(new LaneInfo(actual_crossroad.getNorth().getCarsCount(), actual_crossroad.getNorth().getSpeedLimit()));
        cars.add(new LaneInfo(actual_crossroad.getEast().getCarsCount(), actual_crossroad.getEast().getSpeedLimit()));
        cars.add(new LaneInfo(actual_crossroad.getSouth().getCarsCount(), actual_crossroad.getSouth().getSpeedLimit()));
        cars.add(new LaneInfo(actual_crossroad.getWest().getCarsCount(), actual_crossroad.getWest().getSpeedLimit()));
    }

    public LaneInfo getCarsCountNorthCrossroad_1() {
        return cars_crossroad_1.get(0);
    }

    public LaneInfo getCarsCountEastCrossroad_1() {
        return cars_crossroad_1.get(1);
    }

    public LaneInfo getCarsCountSouthCrossroad_1() {
        return cars_crossroad_1.get(2);
    }

    public LaneInfo getCarsCountWestCrossroad_1() {
        return cars_crossroad_1.get(3);
    }

    public LaneInfo getCarsCountNorthCrossroad_2() {
        return cars_crossroad_2.get(0);
    }

    public LaneInfo getCarsCountEastCrossroad_2() {
        return cars_crossroad_2.get(1);
    }

    public LaneInfo getCarsCountSouthCrossroad_2() {
        return cars_crossroad_2.get(2);
    }

    public LaneInfo getCarsCountWestCrossroad_2() {
        return cars_crossroad_2.get(3);
    }
}
