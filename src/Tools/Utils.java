package Tools;

import AlgorithmSTL.AlgorithmConditions;
import AlgorithmSTL.AlgorithmLaneInfo;
import Objects.Car.CarTypes;
import Objects.Conditions.Conditions;
import Objects.Crossroad.Crossroad;
import Objects.CrossroadInfo.CrossroadInfo;
import Objects.Road.RoadCreator;
import AlgorithmSTL.Node;
import javafx.scene.control.Spinner;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Utils {

    public static CarTypes createRandomCarType() {
        CarTypes type;
        double random = Math.random();

        if (random < 0.1) {
            type = CarTypes.Police;
        } else if (0.1 <= random && random < 0.5) {
            type = CarTypes.Taxi;
        } else if (0.5 <= random && random < 0.6) {
            type = CarTypes.Track;
        } else if (0.6 <= random && random < 0.7) {
            type = CarTypes.Ambulance;
        } else {
            type = CarTypes.Usual;
        }

        return type;
    }

    public static double round(double value, int digits) {
        int places = digits;
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    public static double findRatio(double small_value, double big_value) {
        return round(big_value / small_value, 0);
    }

    public static double createRandomInRange(double min, double max) {
        Random r = new Random();
        double randomValue = min + (max - min) * r.nextDouble();

        return round(randomValue, 2);
    }

    public static Conditions createStartConditions() {
        Crossroad crossroad_1 = new Crossroad(RoadCreator.createRoads(54, 1));
        Crossroad crossroad_2 = new Crossroad(RoadCreator.createRoads(433, 1));

        CrossroadInfo crossroad_info_1 = new CrossroadInfo(crossroad_1);
        CrossroadInfo crossroad_info_2 = new CrossroadInfo(crossroad_2);

        int[] cars_start = {3, 3, 3, 3};
        int[] actual_start = {70, 70, 70, 70};
        int[] limit_start = {70, 70, 70, 70};

        crossroad_info_1.setCrossroadInfo(cars_start, actual_start, limit_start);
        crossroad_info_2.setCrossroadInfo(cars_start, actual_start, limit_start);

        Conditions conditions = new Conditions(crossroad_info_1, crossroad_info_2);

        return conditions;
    }

    public static void createRandomCarsCount(ArrayList<Spinner<Integer>> spinners, int exception_road) {
        for (int i = 0; i < spinners.size(); i++) {
            int min = Constants.CARS_COUNT_MIN;
            int max;
            int cars;
            if (i == exception_road) {
                max = Constants.CARS_COUNT_SHORT_ROAD_MAX;
                cars = (int) createRandomInRange(min, max);
                spinners.get(i).getValueFactory().setValue(cars);
            } else {
                max = Constants.CARS_COUNT_LONG_ROAD_MAX;
                cars = (int) createRandomInRange(min, max);
                spinners.get(i).getValueFactory().setValue(cars);
            }
        }
    }

    public static void createRandomSpeedLimit(ArrayList<Spinner<Integer>> spinners) {
        for (int i = 0; i < 4; i++) {
            int speed_limit = (int) createRandomInRange(Constants.SPEED_LIMIT_MIN, Constants.SPEED_LIMIT_MAX);
            speed_limit = ((speed_limit + 5) / 10) * 10;
            spinners.get(i).getValueFactory().setValue(speed_limit);
        }
    }

    public static void createRandomActualSpeed(ArrayList<Spinner<Integer>> spinners) {
        for (int i = 0; i < 4; i++) {
            int actual_speed = (int) createRandomInRange(Constants.ACTUAL_LIMIT_MIN, Constants.ACTUAL_LIMIT_MAX);
            actual_speed = ((actual_speed + 5) / 10) * 10;
            spinners.get(i).getValueFactory().setValue(actual_speed);
        }
    }

    public static void resetCarsCount(ArrayList<Spinner<Integer>> spinners, int exception_road) {
        for (int i = 0; i < spinners.size(); i++) {
            if (i == exception_road) {
                spinners.get(i).getValueFactory().setValue(Constants.CARS_COUNT_SHORT_ROAD);
            } else {
                spinners.get(i).getValueFactory().setValue(Constants.CARS_COUNT_LONG_ROAD);
            }
        }
    }

    public static void resetSpeedLimit(ArrayList<Spinner<Integer>> spinners) {
        for (int i = 0; i < 4; i++) {
            spinners.get(i).getValueFactory().setValue(Constants.SPEED_LIMIT_DEFAULT);
        }
    }

    public static void resetActualSpeed(ArrayList<Spinner<Integer>> spinners) {
        for (int i = 0; i < 4; i++) {
            spinners.get(i).getValueFactory().setValue(Constants.ACTUAL_LIMIT_DEFAULT);
        }
    }


    /**
     * //////////////////////////////////////////////////////////////////////////
     * //// Next part of the utils functions used for calculate AlgorithmSTL ////
     * //////////////////////////////////////////////////////////////////////////
     **/

    //start isEqualsNodes
    public static boolean isEqualsNodes(Node first, Node second) {
        return isEqualCarsCountOnCrossroad(first.getConditions().getLanesInfoFirstCrossroad(),
                second.getConditions().getLanesInfoFirstCrossroad())
                &&
                isEqualCarsCountOnCrossroad(first.getConditions().getLanesInfoSecondCrossroad(),
                        second.getConditions().getLanesInfoSecondCrossroad());
    }

    private static boolean isEqualCarsCountOnCrossroad(ArrayList<AlgorithmLaneInfo> first, ArrayList<AlgorithmLaneInfo> second) {
        for (int i = 0; i < 4; i++) {
            if (!isEqualCarsCountOnDirection(first.get(i), second.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isEqualCarsCountOnDirection(AlgorithmLaneInfo first, AlgorithmLaneInfo second) {
        return first.getCarsCount() == second.getCarsCount();
    }
    //end isEqualsNodes


    public static boolean checkIfNodeExistsInList(Node node, Set<Node> set) {
        for (Node temp : set) {
            if (isEqualsNodes(node, temp)) {
                return true;
            }
        }
        return false;
    }

    public static Node getSameNode(Node node, Set<Node> set) {
        for (Node temp : set) {
            if (isEqualsNodes(node, temp)) {
                return temp;
            }
        }
        return null;
    }


    //Check if the queue contains the same node with the higher price then change it by cheaper node
    public static void changeBetweenNodesInQueue(Queue<Node> queue, Set<Node> open_list, Node node) {
        for (Node temp : queue) {
            if (Utils.isEqualsNodes(node, temp)) {
                if (temp.getTotalPrice() > node.getTotalPrice()) {

                    queue.remove(temp);             //remove expensive node from priority queue
                    open_list.remove(temp);         //remove expensive node from open list

                    queue.add(node);                //add cheap node to priority queue
                    open_list.add(node);            //add cheap node to open list
                }
                break;
            }
        }
    }


    //start updatePassedCars
    //a function that calculates how many cars have passed the intersections
    public static void updatePassedCars(AlgorithmConditions conditions, double ns_time, double ew_time) {
        //calculate north-south
        updateLanesInfoForCrossroad(conditions.getLanesInfoFirstCrossroad(), true, ns_time);
        updateLanesInfoForCrossroad(conditions.getLanesInfoSecondCrossroad(), true, ns_time);

        //calculate east-west
        updateLanesInfoForCrossroad(conditions.getLanesInfoFirstCrossroad(), false, ew_time);
        updateLanesInfoForCrossroad(conditions.getLanesInfoSecondCrossroad(), false, ew_time);

    }

    //a function that update how many cars have passed a given intersection in a given time and in a given direction
    private static void updateLanesInfoForCrossroad(ArrayList<AlgorithmLaneInfo> cars_info, boolean is_north_south, double time) {
        if (is_north_south) {
            updateLanesInfoByDirection(cars_info.get(Constants.NORTH_DIRECTION), time);
            updateLanesInfoByDirection(cars_info.get(Constants.SOUTH_DIRECTION), time);
        } else {
            updateLanesInfoByDirection(cars_info.get(Constants.EAST_DIRECTION), time);
            updateLanesInfoByDirection(cars_info.get(Constants.WEST_DIRECTION), time);
        }
    }

    private static void updateLanesInfoByDirection(AlgorithmLaneInfo lane_info, double time) {
        int passed_cars = calculatePassedCarsByDirection(lane_info, time);

        int new_cars_count = passed_cars >= lane_info.getCarsCount() ? 0 : lane_info.getCarsCount() - passed_cars;
        double new_distance = lane_info.getDistanceFromCrossroad() - passed_cars * lane_info.getAvgCarLength();
        if (new_distance < 0) {
            new_distance = 0;
        }

        lane_info.setCarsCount(new_cars_count);
        lane_info.setDistanceFromCrossroad(new_distance);
    }

    private static int calculatePassedCarsByDirection(AlgorithmLaneInfo lane_info, double time) {

        if (lane_info.getCarsCount() == 0) {
            return 0;
        }

        int count = 0;
        double car_length = lane_info.getAvgCarLength();
        double speed_limit = lane_info.getSpeedLimit();
        boolean next = true;

        while (next && lane_info.getCarsCount() - (count + 1) > 0) {

            //car_length * count => for calculate how many cars cans passed the crossroad is necessary to
            //calculate distance that each car has to move for passing intersection.

            //(time - (count * time_step)) =>
            //Each next car that starts moving has 0.5 seconds less than the front car
            //due to the rules according to which each car must maintain a safe distance of 3 meters.
            //Each car keeps a distance of 2 meters when the traffic light status is red,
            //and to start each car, you must wait until the previous cars have passed 1 meter.
            //Each car travels 1 meter in 0.5 seconds, since the acceleration value is 2 m / s,
            //it takes 0.5 seconds to travel 1 meter.

            double acc = 2; //pass this variable
            double time_step = (Constants.SAFETY_DISTANCE_TO_START - Constants.SAFETY_DISTANCE) / acc;

            //+4.5 - stop_acc
            if (car_length * count < calculatePassedDistance(time - (count * time_step), acc, 0, speed_limit) + 4.5) {
                count++;
            } else {
                next = false;
            }
        }

        return count + 1;
    }

    //TODO calculate passed distance more smart
    private static double calculatePassedDistance(double time, double acc, double start_velocity, double speed_limit) {

//        if (speed_limit < Formulas.calculateVelocity(time, acc, start_velocity)) {
//            double time_to_max_speed = round(Formulas.calculateTimeForMaxSpeed(acc, speed_limit), 0);
//            double rest_time = time - time_to_max_speed;
//
////            return
//        }


        return Formulas.calculateDistanceByAccelerationAndTime(time, acc, start_velocity);
    }
    //end updatePassedCars


    //start createGoalAlgorithmConditions
    public static AlgorithmConditions createGoalAlgorithmConditions() {
        ArrayList<AlgorithmLaneInfo> first = createGoalAlgorithmCrossroad();
        ArrayList<AlgorithmLaneInfo> second = createGoalAlgorithmCrossroad();
        return new AlgorithmConditions(first, second);
    }

    private static ArrayList<AlgorithmLaneInfo> createGoalAlgorithmCrossroad() {
        ArrayList<AlgorithmLaneInfo> crossroad = new ArrayList<>();
        AlgorithmLaneInfo empty_lane_info_1 = createGoalAlgorithmLaneInfo();
        AlgorithmLaneInfo empty_lane_info_2 = createGoalAlgorithmLaneInfo();
        AlgorithmLaneInfo empty_lane_info_3 = createGoalAlgorithmLaneInfo();
        AlgorithmLaneInfo empty_lane_info_4 = createGoalAlgorithmLaneInfo();

        crossroad.add(empty_lane_info_1);
        crossroad.add(empty_lane_info_2);
        crossroad.add(empty_lane_info_3);
        crossroad.add(empty_lane_info_4);
        return crossroad;
    }

    private static AlgorithmLaneInfo createGoalAlgorithmLaneInfo() {
        return new AlgorithmLaneInfo(0);
    }
    //end createGoalAlgorithmConditions


    //start heuristicFunction
    //heuristic function must be admissible and consistent
    //admissible proof: our function admissible because we used the maximum speed limit for each road,
    //despite the real speed limit then we always get the result that <= to real result.
    //TODO consistent proof
    //consistent proof: in each step the cars count decrease because the speed is more than 0,
    //so we can says then if we get finite number of cars and speed more than 0 then all cars will passed the
    //intersection in finite time(proof of finite steps). because the number of cars always decrease then
    //in each next step we will have real cars number less then in previous step and heuristic time for passing
    //crossroad will always decrease.
    public static double heuristicFunction(AlgorithmConditions conditions, double[] times) {
//        int ns_max = findMaxCarsCount(conditions, true);
//        int ew_max = findMaxCarsCount(conditions, false);

        double ns_time = times[0];
        double ew_time = times[1];

        double ns_passed_distance = Formulas.convertKMpHtoMpS(Constants.SPEED_LIMIT_MAX) * ns_time;
        double ew_passed_distance = Formulas.convertKMpHtoMpS(Constants.SPEED_LIMIT_MAX) * ew_time;

        double ns_max_distance = findMaxDistance(conditions, true);
        double ew_max_distance = findMaxDistance(conditions, false);

        double time = ns_max_distance / ns_passed_distance + ew_max_distance / ew_passed_distance;

//        double time = Math.max(ns_max_distance, ew_max_distance)
//                / Formulas.convertKMpHtoMpS(Constants.SPEED_LIMIT_MAX);
//

        return time;
    }

    private static double findMaxDistance(AlgorithmConditions conditions, boolean is_north_south) {
        double max;
        if (is_north_south) {
            double first = findMaxDistanceBetweenDirections(conditions.getLanesInfoFirstCrossroad(), Constants.NORTH_DIRECTION, Constants.SOUTH_DIRECTION);
            double second = findMaxDistanceBetweenDirections(conditions.getLanesInfoSecondCrossroad(), Constants.NORTH_DIRECTION, Constants.SOUTH_DIRECTION);
            max = Math.max(first, second);
        } else {
            double first = findMaxDistanceBetweenDirections(conditions.getLanesInfoFirstCrossroad(), Constants.EAST_DIRECTION, Constants.WEST_DIRECTION);
            double second = findMaxDistanceBetweenDirections(conditions.getLanesInfoSecondCrossroad(), Constants.EAST_DIRECTION, Constants.WEST_DIRECTION);
            max = Math.max(first, second);
        }
        return max;

    }

    private static double findMaxDistanceBetweenDirections(ArrayList<AlgorithmLaneInfo> crossroad, int first, int second) {
        return Math.max(crossroad.get(first).getDistanceFromCrossroad(), crossroad.get(second).getDistanceFromCrossroad());
    }

    private static int findMaxCarsCount(AlgorithmConditions conditions, boolean is_north_south) {
        int max;
        if (is_north_south) {
            int first = findMaxCarsCountForCrossroad(conditions.getLanesInfoFirstCrossroad(), Constants.NORTH_DIRECTION, Constants.SOUTH_DIRECTION);
            int second = findMaxCarsCountForCrossroad(conditions.getLanesInfoSecondCrossroad(), Constants.NORTH_DIRECTION, Constants.SOUTH_DIRECTION);
            max = Math.max(first, second);
        } else {
            int first = findMaxCarsCountForCrossroad(conditions.getLanesInfoFirstCrossroad(), Constants.EAST_DIRECTION, Constants.WEST_DIRECTION);
            int second = findMaxCarsCountForCrossroad(conditions.getLanesInfoSecondCrossroad(), Constants.EAST_DIRECTION, Constants.WEST_DIRECTION);
            max = Math.max(first, second);
        }
        return max;
    }

    private static int findMaxCarsCountForCrossroad(ArrayList<AlgorithmLaneInfo> crossroad, int first, int second) {
        return Math.max(crossroad.get(first).getCarsCount(), crossroad.get(second).getCarsCount());
    }
    //end heuristicFunction


    public static String createNewName(String name, double ns_time, double ew_time) {
        return name + Constants.PHASE_DELIMITER + ns_time + Constants.TIMES_DELIMITER + ew_time;
    }

    public static double[] getLastTimesFromName(String str) {
        double[] bad_result = {10, 10};

        if (str.equals("")) {
            return bad_result;
        }

        String[] times_from_name = getAllPhases(str); //["10:10", "9:11", ...]
        if (times_from_name.length == 0) {
            return bad_result;
        }

        double[] result = getPhaseTimes(times_from_name[times_from_name.length - 1]);
        return result;
    }

    public static String[] getAllPhases(String str) {
        String[] times = str.split(Constants.PHASE_DELIMITER);
        return times;
    }

    public static double[] getPhaseTimes(String str) {
        double[] result = {10, 10};

        String[] times = str.split(Constants.TIMES_DELIMITER);
        if (times.length != 2) {
            return result;
        }

        result[0] = Double.parseDouble(times[0]);
        result[1] = Double.parseDouble(times[1]);
        return result;
    }

    public static void addBetterDistributionToQueue(Queue<Double> queue, String path){

        String [] phases = getAllPhases(path);
        if(phases.length == 0){
            return;
        }

        for (String phase : phases) {
            double [] times = getPhaseTimes(phase);
            queue.add(times[0]);
        }

    }

    public static double calculateBetterDistributionDuration(Queue<Double> queue){

        double phases_work_time = queue.size() * Constants.TRAFFIC_LIGHT_PHASE_TIME;
        double changing_work_time = (queue.size() - 1) * ((Constants.TRAFFIC_LIGHT_CHANGING_TIME) * 3) * 2;
        double first_work_time = (Constants.TRAFFIC_LIGHT_CHANGING_TIME) * 2;

        double better_duration = phases_work_time + changing_work_time + first_work_time;

        return better_duration;

    }
}
