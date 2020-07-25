package Objects.Conditions;

import Objects.CrossroadInfo.CrossroadInfo;
import SystemSTL.LaneInfo;
import java.util.ArrayList;

/**
 * This class stores all the information about the two intersections that will be used in the simulation.
 */
public class Conditions {

    private CrossroadInfo crossroad_info_1;
    private CrossroadInfo crossroad_info_2;

    private ArrayList<LaneInfo> cars_crossroad_1;
    private ArrayList<LaneInfo> cars_crossroad_2;

//    private ArrayList<CarInfo> passed_cars_first_crossroad_south;
//    private ArrayList<CarInfo> passed_cars_first_crossroad_south;

    /**
     * Constructor.
     * The constructor receives information from the intersection and creates a suitable structure for the calculation algorithm.
     * @param info1 - crossroad 1
     * @param info2 - crossroad 2
     */
    public Conditions(CrossroadInfo info1, CrossroadInfo info2) {
        crossroad_info_1 = info1;
        crossroad_info_2 = info2;
        createLanesInfo();
    }

    /**
     * Copy constructor.
     * @param conditions
     */
    public Conditions(Conditions conditions) {
        crossroad_info_1 = new CrossroadInfo(conditions.getCrossroadInfo1());
        crossroad_info_2 = new CrossroadInfo(conditions.getCrossroadInfo2());
        createLanesInfo();
    }

    /**
     * This function change state of traffic lights. Changes appears in two crossroads.
     */
    public void changeTrafficLightState() {
        crossroad_info_1.getCrossroad().changeTrafficLightStateOnCrossroad();
        crossroad_info_2.getCrossroad().changeTrafficLightStateOnCrossroad();
    }

    /**
     * This function returns true if the east-west direction is currently active.
     * @return
     */
    public boolean isEastWestActive() {
        return crossroad_info_1.getCrossroad().isEastWestActive() && crossroad_info_2.getCrossroad().isEastWestActive();
    }

    /**
     * This function returns true if the north-south direction is currently active.
     * @return
     */
    public boolean isNorthSouthActive() {
        return crossroad_info_1.getCrossroad().isNorthSouthActive() && crossroad_info_2.getCrossroad().isNorthSouthActive();
    }

    /**
     * This function returns the duration of an north-south traffic light.
     * @return
     */
    public double getNorthSouthTimeDistribution() {
        if (crossroad_info_1.getCrossroad().getTimeDistribution().getNorthSouth() !=
                crossroad_info_2.getCrossroad().getTimeDistribution().getNorthSouth()) {
            throw new RuntimeException("Bad time distribution...");
        }
        return crossroad_info_1.getCrossroad().getTimeDistribution().getNorthSouth();
    }

    /**
     * This function returns the duration of an east-west traffic light.
     * @return
     */
    public double getEastWestTimeDistribution() {
        if (crossroad_info_1.getCrossroad().getTimeDistribution().getEastWest() !=
                crossroad_info_2.getCrossroad().getTimeDistribution().getEastWest()) {
            throw new RuntimeException("Bad time distribution...");
        }
        return crossroad_info_1.getCrossroad().getTimeDistribution().getEastWest();
    }

    /**
     * This function returns the change time of the traffic light.
     * Color change is the time when the traffic light needs to switch between the following colors.
     * @return time of changing
     */
    public double getChangingLightsTime() {
        return crossroad_info_1.getCrossroad().getTimeDistribution().getChangingExecutionTime();
    }

    /**
     * This function add working time to east-west direction.
     */
    public void addTimeToEastWestRoute() {
        crossroad_info_1.getCrossroad().getTimeDistribution().addTimeToEastWestRoute();
        crossroad_info_2.getCrossroad().getTimeDistribution().addTimeToEastWestRoute();
    }

    /**
     * This function add working time to north-south direction.
     */
    public void addTimeToNorthSouthRoute() {
        crossroad_info_1.getCrossroad().getTimeDistribution().addTimeToNorthSouthRoute();
        crossroad_info_2.getCrossroad().getTimeDistribution().addTimeToNorthSouthRoute();
    }

    /**
     * This function set default time distribution on the crossroads.
     */
    public void setDefaultTimeDistribution() {
        crossroad_info_1.getCrossroad().getTimeDistribution().setDefaultDistribution();
        crossroad_info_2.getCrossroad().getTimeDistribution().setDefaultDistribution();
    }

    /**
     * This function generate information of crossroads for algorithm calculation.
     */
    private void createLanesInfo() {
        cars_crossroad_1 = new ArrayList<>();
        createLanesPerCrossroad(cars_crossroad_1, crossroad_info_1);

        cars_crossroad_2 = new ArrayList<>();
        createLanesPerCrossroad(cars_crossroad_2, crossroad_info_2);

    }

    /**
     * This function generates information for the crossroad about its vehicles.
     * @param cars - count of cars
     * @param actual_crossroad - specific crossroad
     */
    private void createLanesPerCrossroad(ArrayList<LaneInfo> cars, CrossroadInfo actual_crossroad) {
        cars.add(new LaneInfo(actual_crossroad.getNorth().getCarsCount(), actual_crossroad.getNorth().getSpeedLimit()));
        cars.add(new LaneInfo(actual_crossroad.getEast().getCarsCount(), actual_crossroad.getEast().getSpeedLimit()));
        cars.add(new LaneInfo(actual_crossroad.getSouth().getCarsCount(), actual_crossroad.getSouth().getSpeedLimit()));
        cars.add(new LaneInfo(actual_crossroad.getWest().getCarsCount(), actual_crossroad.getWest().getSpeedLimit()));
    }

    public CrossroadInfo getCrossroadInfo1() {
        return crossroad_info_1;
    }

    public CrossroadInfo getCrossroadInfo2() {
        return crossroad_info_2;
    }

    public ArrayList<LaneInfo> getCarsInFirstCrossroad() {
        return cars_crossroad_1;
    }

    public ArrayList<LaneInfo> getCarsInSecondCrossroad() {
        return cars_crossroad_2;
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
