package Objects.Conditions;

import Objects.CrossroadInfo.CrossroadInfo;
import Objects.CrossroadInfo.DirectionInfo.DirectionInfo;
import SystemSTL.TrafficComputation.Lane.LaneInfo;
import Tools.ConsoleColors;
import Tools.Constants;
import Tools.Utils;

import java.util.*;

/**
 * This class stores all the information about the two intersections that will be used in the simulation.
 */
public class Conditions {

    private CrossroadInfo first_crossroad_info;
    private CrossroadInfo second_crossroad_info;

    private ArrayList<LaneInfo> lanes_info_first_crossroad;
    private ArrayList<LaneInfo> lanes_info_second_crossroad;

    private double better_duration; //save to database - number
    private double initial_duration;
    private double simulation_duration;

    private double initial_awt;
    private double better_awt;

    private Queue<Double> better_distribution;
    private String better_distribution_string; //save to database - as string

    /**
     * Constructor.
     * The constructor receives information from the intersection and creates a suitable structure for the calculation algorithm.
     *
     * @param info1 - crossroad 1
     * @param info2 - crossroad 2
     */
    public Conditions(CrossroadInfo info1, CrossroadInfo info2) {
        first_crossroad_info = info1;
        second_crossroad_info = info2;
        this.better_distribution = new ArrayDeque<>();
        better_distribution_string = "";
        createLanesInfo();
    }

    /**
     * Copy constructor.
     *
     * @param conditions
     */
    public Conditions(Conditions conditions) {
        first_crossroad_info = new CrossroadInfo(conditions.getFirstCrossroadInfo());
        second_crossroad_info = new CrossroadInfo(conditions.getSecondCrossroadInfo());
        createLanesInfo();
    }

    /**
     * This function change state of traffic lights. Changes appears in two crossroads.
     */
    public void changeTrafficLightState() {
        first_crossroad_info.getCrossroad().changeTrafficLightStateOnCrossroad();
        second_crossroad_info.getCrossroad().changeTrafficLightStateOnCrossroad();
    }

    /**
     * This function checks if all vehicles have passed these intersections.
     *
     * @return true or false
     */
    public boolean isAllCarsPassed() {
        for (LaneInfo lane_info : lanes_info_first_crossroad) {
            if (lane_info.getCarsInLane().size() > 0) {
                return false;
            }
        }
        for (LaneInfo lane_info : lanes_info_second_crossroad) {
            if (lane_info.getCarsInLane().size() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * This function returns true if the east-west direction is currently active.
     *
     * @return
     */
    public boolean isEastWestActive() {
        return first_crossroad_info.getCrossroad().isEastWestActive() && second_crossroad_info.getCrossroad().isEastWestActive();
    }

    /**
     * This function returns true if the north-south direction is currently active.
     *
     * @return
     */
    public boolean isNorthSouthActive() {
        return first_crossroad_info.getCrossroad().isNorthSouthActive() && second_crossroad_info.getCrossroad().isNorthSouthActive();
    }

    /**
     * This function returns the duration of an north-south traffic light.
     *
     * @return
     */
    public double getNorthSouthTimeDistribution() {
        if (first_crossroad_info.getCrossroad().getTimeDistribution().getNorthSouth() !=
                second_crossroad_info.getCrossroad().getTimeDistribution().getNorthSouth()) {
            throw new RuntimeException("Bad time distribution...");
        }
        return first_crossroad_info.getCrossroad().getTimeDistribution().getNorthSouth();
    }

    /**
     * This function returns the duration of an east-west traffic light.
     *
     * @return
     */
    public double getEastWestTimeDistribution() {
        if (first_crossroad_info.getCrossroad().getTimeDistribution().getEastWest() !=
                second_crossroad_info.getCrossroad().getTimeDistribution().getEastWest()) {
            throw new RuntimeException("Bad time distribution...");
        }
        return first_crossroad_info.getCrossroad().getTimeDistribution().getEastWest();
    }

    /**
     * This function returns the change time of the traffic light.
     * Color change is the time when the traffic light needs to switch between the following colors.
     *
     * @return time of changing
     */
    public double getChangingLightsTime() {
        return first_crossroad_info.getCrossroad().getTimeDistribution().getChangingExecutionTime();
    }

    /**
     * This function add working time to east-west direction.
     */
    public void addTimeToEastWestRoute() {
        first_crossroad_info.getCrossroad().addTimeToEastWestRoute();
        second_crossroad_info.getCrossroad().addTimeToEastWestRoute();

        first_crossroad_info.getCrossroad().getTimeDistribution().printTimeDistributionAfterChange();
    }

    /**
     * This function add working time to north-south direction.
     */
    public void addTimeToNorthSouthRoute() {
        first_crossroad_info.getCrossroad().addTimeToNorthSouthRoute();
        second_crossroad_info.getCrossroad().addTimeToNorthSouthRoute();

        first_crossroad_info.getCrossroad().getTimeDistribution().printTimeDistributionAfterChange();
    }

    /**
     * This function set distribution time to each crossroad.
     * When the north-south time changed, the time in the opposite direction (east-west) changed automatically.
     */
    public void setTimeDistribution(double north_south_time) {
        first_crossroad_info.getCrossroad().setTimeDistribution(north_south_time);
        second_crossroad_info.getCrossroad().setTimeDistribution(north_south_time);

        first_crossroad_info.getCrossroad().getTimeDistribution().printTimeDistributionAfterChange();
    }

    /**
     * This function set default time distribution on the crossroads.
     */
    public void setDefaultTimeDistribution() {
        first_crossroad_info.getCrossroad().getTimeDistribution().setDefaultDistribution();
        second_crossroad_info.getCrossroad().getTimeDistribution().setDefaultDistribution();

        first_crossroad_info.getCrossroad().getTimeDistribution().printTimeDistributionAfterChange();
    }

    /**
     * This function generate information of crossroads for algorithm calculation.
     */
    private void createLanesInfo() {
        lanes_info_first_crossroad = new ArrayList<>();
        createLanesPerCrossroad(lanes_info_first_crossroad, first_crossroad_info);

        lanes_info_second_crossroad = new ArrayList<>();
        createLanesPerCrossroad(lanes_info_second_crossroad, second_crossroad_info);
    }

    /**
     * This function generates information for the crossroad about its vehicles.
     *
     * @param cars             - count of cars
     * @param actual_crossroad - specific crossroad
     */
    private void createLanesPerCrossroad(ArrayList<LaneInfo> cars, CrossroadInfo actual_crossroad) {
        cars.add(createLaneInfo(actual_crossroad.getNorth()));
        cars.add(createLaneInfo(actual_crossroad.getEast()));
        cars.add(createLaneInfo(actual_crossroad.getSouth()));
        cars.add(createLaneInfo(actual_crossroad.getWest()));
    }

    private LaneInfo createLaneInfo(DirectionInfo direction_info) {
        return new LaneInfo(direction_info.getCarsCount(), direction_info.getSpeedLimit(), direction_info.getActualSpeed());
    }

    public int getCarsCount() {
        int count = 0;

        for (LaneInfo info : lanes_info_first_crossroad) {
            count += info.getCarsInLane().size();
        }

        for (LaneInfo info : lanes_info_second_crossroad) {
            count += info.getCarsInLane().size();
        }

        return count;
    }

    public CrossroadInfo getFirstCrossroadInfo() {
        return first_crossroad_info;
    }

    public CrossroadInfo getSecondCrossroadInfo() {
        return second_crossroad_info;
    }

    public ArrayList<LaneInfo> getLanesInfoFirstCrossroad() {
        return lanes_info_first_crossroad;
    }

    public ArrayList<LaneInfo> getLanesInfoSecondCrossroad() {
        return lanes_info_second_crossroad;
    }

    public LaneInfo getLaneInfoNorthFirstCrossroad() {
        return lanes_info_first_crossroad.get(Constants.NORTH_DIRECTION);
    }

    public LaneInfo getLaneInfoEastFirstCrossroad() {
        return lanes_info_first_crossroad.get(Constants.EAST_DIRECTION);
    }

    public LaneInfo getLaneInfoSouthFirstCrossroad() {
        return lanes_info_first_crossroad.get(Constants.SOUTH_DIRECTION);
    }

    public LaneInfo getLaneInfoWestFirstCrossroad() {
        return lanes_info_first_crossroad.get(Constants.WEST_DIRECTION);
    }

    public LaneInfo getLaneInfoNorthSecondCrossroad() {
        return lanes_info_second_crossroad.get(Constants.NORTH_DIRECTION);
    }

    public LaneInfo getLaneInfoEastSecondCrossroad() {
        return lanes_info_second_crossroad.get(Constants.EAST_DIRECTION);
    }

    public LaneInfo getLaneInfoSouthSecondCrossroad() {
        return lanes_info_second_crossroad.get(Constants.SOUTH_DIRECTION);
    }

    public LaneInfo getLaneInfoWestSecondCrossroad() {
        return lanes_info_second_crossroad.get(Constants.WEST_DIRECTION);
    }

    public int getEastWestCarsCount() {
        return calculateCarsCountForDirections(false);
    }

    public int getNorthSouthCarsCount() {
        return calculateCarsCountForDirections(true);
    }

    private int calculateCarsCountForDirections(boolean is_north_south) {
        int count = 0;

        for (int i = 0; i < 4; i++) {
            if (is_north_south) {
                if (i == Constants.NORTH_DIRECTION || i == Constants.SOUTH_DIRECTION) {
                    count += lanes_info_first_crossroad.get(i).getCarsInLane().size();
                    count += lanes_info_second_crossroad.get(i).getCarsInLane().size();
                }
            } else {
                if (i == Constants.EAST_DIRECTION || i == Constants.WEST_DIRECTION) {
                    count += lanes_info_first_crossroad.get(i).getCarsInLane().size();
                    count += lanes_info_second_crossroad.get(i).getCarsInLane().size();
                }
            }
        }

        return count;
    }

    /**
     * This function returns better distribution queue.
     *
     * @return
     */
    public Queue<Double> getBetterDistribution() {
        return better_distribution;
    }

    /**
     * This function returns next better distribution time from queue of better distribution times.
     *
     * @return
     */
    public double getNextDistribution() {
        return better_distribution.poll();
    }

    /**
     * This function receives string of better times and convert it to double and add to queue of better distribution.
     *
     * @param path - string of better distribution times (->12:8->10:10->14:6)
     */
    public void setBetterDistribution(String path) {
        better_distribution_string = path;
        better_duration = Utils.calculateDurationFromString(path);
        this.better_distribution.clear();
        Utils.addBetterDistributionToQueue(this.better_distribution, path);
    }

    /**
     * This function returns better distribution duration.
     *
     * @return - better_distribution_duration
     */
    public double getBetterDuration() {
        return better_duration;
    }

    /**
     * This function returns initial duration.
     *
     * @return - initial duration
     */
    public double getInitialDuration() {
        return initial_duration;
    }

    /**
     * This function sets initial duration of chosen conditions.
     *
     * @param initial_duration - real initial time without smart algorithm
     */
    public void setInitialDuration(double initial_duration) {
        System.out.println(ConsoleColors.CYAN + "Initial time of passing all cars: " + initial_duration + " seconds." + ConsoleColors.RESET);
        this.initial_duration = initial_duration;
    }

    /**
     * This function returns real duration of working.
     *
     * @return algorithm duration - real time of working
     */
    public double getSimulationDuration() {
        return simulation_duration;
    }

    /**
     * This function sets algorithm duration.
     *
     * @param simulation_duration
     */
    public void setSimulationDuration(double simulation_duration) {
        System.out.println(ConsoleColors.RED_BOLD + "Simulation working time: " + simulation_duration + " seconds" + ConsoleColors.RESET);
        this.simulation_duration = simulation_duration;
    }

    public String getBetterDistributionString() {
        return better_distribution_string;
    }

    public double getInitialAWT() {
        return initial_awt;
    }

    public void setInitialAWT(double initial_awt) {
        this.initial_awt = initial_awt;
        System.out.println(ConsoleColors.CYAN + "Initial time of AWT: " + initial_awt + " seconds." + ConsoleColors.RESET);
    }

    public double getBetterAWT() {
        return better_awt;
    }

    public void setBetterAWT(double better_awt) {
        this.better_awt = better_awt;
        System.out.println(ConsoleColors.GREEN + "Algorithm time of AWT: " + better_awt + " seconds" + ConsoleColors.RESET);
    }
}
