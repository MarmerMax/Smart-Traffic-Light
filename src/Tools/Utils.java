package Tools;

import Database.DatabaseConditions;
import SystemSTL.AlgorithmSTL.AlgorithmConditions;
import SystemSTL.AlgorithmSTL.AlgorithmLaneInfo;
import Objects.Car.CarTypes;
import Objects.Conditions.Conditions;
import Objects.Crossroad.Crossroad;
import Objects.CrossroadInfo.CrossroadInfo;
import Objects.Road.RoadCreator;
import SystemSTL.AlgorithmSTL.Node;
import SystemSTL.TrafficComputation.Lane.LaneInfo;
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

    //random conditions for crossroad
    public static void createRandomConditions(ArrayList<Spinner<Integer>> cars_spinners,
                                              ArrayList<Spinner<Integer>> limit_spinners,
                                              ArrayList<Spinner<Integer>> actual_spinners,
                                              int exception_road) {

        createRandomCarsCount(cars_spinners, exception_road);
        createRandomSpeedLimit(limit_spinners);

        int[] limits = new int[4];
        for (int i = 0; i < limits.length; i++) {
            limits[i] = limit_spinners.get(i).getValue();
        }

        createRandomActualSpeed(actual_spinners, limits);
    }

    private static void createRandomCarsCount(ArrayList<Spinner<Integer>> spinners, int exception_road) {
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

    private static void createRandomSpeedLimit(ArrayList<Spinner<Integer>> spinners) {
        for (int i = 0; i < 4; i++) {
            int speed_limit = (int) createRandomInRange(Constants.SPEED_LIMIT_MIN, Constants.SPEED_LIMIT_MAX);
            speed_limit = ((speed_limit + 5) / 10) * 10;
            spinners.get(i).getValueFactory().setValue(speed_limit);
        }
    }

    private static void createRandomActualSpeed(ArrayList<Spinner<Integer>> spinners, int[] limits) {
        for (int i = 0; i < 4; i++) {
            int actual_speed = (int) createRandomInRange(Constants.ACTUAL_LIMIT_MIN, limits[i]);
            actual_speed = ((actual_speed + 5) / 10) * 10;
            spinners.get(i).getValueFactory().setValue(actual_speed);
        }
    }
    //end random conditions for crossroad


    /**
     * This function resets spinners conditions to default values.
     *
     * @param cars_spinners   - cars spinners
     * @param limit_spinners  - speed limit spinners
     * @param actual_spinners - actual speed spinners
     * @param exception_road  - road with lower count of cars
     */
    //reset conditions for crossroad
    public static void resetConditions(ArrayList<Spinner<Integer>> cars_spinners,
                                       ArrayList<Spinner<Integer>> limit_spinners,
                                       ArrayList<Spinner<Integer>> actual_spinners,
                                       int exception_road) {

        resetCarsCount(cars_spinners, exception_road);
        resetSpeedLimit(limit_spinners);
        resetActualSpeed(actual_spinners);
    }

    /**
     * This function resets values for cars spinner.
     *
     * @param spinners
     * @param exception_road
     */
    private static void resetCarsCount(ArrayList<Spinner<Integer>> spinners, int exception_road) {
        for (int i = 0; i < spinners.size(); i++) {
            if (i == exception_road) {
                spinners.get(i).getValueFactory().setValue(Constants.CARS_COUNT_SHORT_ROAD);
            } else {
                spinners.get(i).getValueFactory().setValue(Constants.CARS_COUNT_LONG_ROAD);
            }
        }
    }

    /**
     * This function resets values for speed limit spinner.
     *
     * @param spinners
     */
    private static void resetSpeedLimit(ArrayList<Spinner<Integer>> spinners) {
        for (int i = 0; i < 4; i++) {
            spinners.get(i).getValueFactory().setValue(Constants.SPEED_LIMIT_DEFAULT);
        }
    }

    /**
     * This function resets values for actual speed spinner.
     *
     * @param spinners
     */
    private static void resetActualSpeed(ArrayList<Spinner<Integer>> spinners) {
        for (int i = 0; i < 4; i++) {
            spinners.get(i).getValueFactory().setValue(Constants.ACTUAL_LIMIT_DEFAULT);
        }
    }
    //end reset conditions for crossroad


    /**
     * This function creates listeners for the actual limit spinners.
     * The actual speed cannot be higher than the speed limit.
     *
     * @param actual_spinners
     * @param limit_spinners
     */
    @SuppressWarnings("Duplicates")
    public static void createActualSpeedListeners(ArrayList<Spinner<Integer>> actual_spinners, ArrayList<Spinner<Integer>> limit_spinners) {
        if (actual_spinners.size() != limit_spinners.size()) {
            throw new RuntimeException("Fail! Wrong program parameters...");
        }

        actual_spinners.get(0).valueProperty().addListener((obs, oldValue, newValue) -> {
            if (limit_spinners.get(0).getValue() < newValue) {
                newValue = limit_spinners.get(0).getValue();
            }
            actual_spinners.get(0).getValueFactory().setValue(newValue);
        });

        actual_spinners.get(1).valueProperty().addListener((obs, oldValue, newValue) -> {
            if (limit_spinners.get(1).getValue() < newValue) {
                newValue = limit_spinners.get(1).getValue();
            }
            actual_spinners.get(1).getValueFactory().setValue(newValue);
        });

        actual_spinners.get(2).valueProperty().addListener((obs, oldValue, newValue) -> {
            if (limit_spinners.get(2).getValue() < newValue) {
                newValue = limit_spinners.get(2).getValue();
            }
            actual_spinners.get(2).getValueFactory().setValue(newValue);
        });

        actual_spinners.get(3).valueProperty().addListener((obs, oldValue, newValue) -> {
            if (limit_spinners.get(3).getValue() < newValue) {
                newValue = limit_spinners.get(3).getValue();
            }
            actual_spinners.get(3).getValueFactory().setValue(newValue);
        });
    }


    /**
     * //////////////////////////////////////////////////////////////////////////
     * //// Next part of the utils functions used for calculate SystemSTL.AlgorithmSTL ////
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

        double acc = 2; //pass this variable
        double time_step = (Constants.SAFETY_DISTANCE_TO_START - Constants.SAFETY_DISTANCE) / acc;

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

            //+4.5 - stop_acc
            if (car_length * count < calculatePassedDistance(time - (count * time_step), acc, 0, speed_limit)) {
                count++;
            } else {
                next = false;
            }
        }

        return count + 1;
    }

    private static double calculatePassedDistance(double time, double acc, double start_velocity, double speed_limit) {

        double result;

        if (time * acc > speed_limit) {
            result = calculateDistanceByAccTimeMaxSpeed(time, acc, start_velocity, speed_limit);
        } else {
            result = Formulas.calculateDistanceByAccelerationAndTime(time, acc, start_velocity);
        }

        return result;
    }

    private static double calculateDistanceByAccTimeMaxSpeed(double time, double acc, double start_velocity, double max_speed) {
        double dist = 0;
        double act_speed = start_velocity;

        for (int i = 1; i < time + 1; i++) {
            if (act_speed < max_speed) {
                act_speed += acc;
            }
            dist += act_speed;
        }

        return dist;
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

        double ns_time = times[0];
        double ew_time = times[1];

        double ns_passed_distance = Formulas.convertKMpHtoMpS(Constants.SPEED_LIMIT_MAX) * ns_time;
        double ew_passed_distance = Formulas.convertKMpHtoMpS(Constants.SPEED_LIMIT_MAX) * ew_time;

        double ns_max_distance = findMaxDistance(conditions, true);
        double ew_max_distance = findMaxDistance(conditions, false);

        double time = ns_max_distance / ns_passed_distance + ew_max_distance / ew_passed_distance;
        time *= (ns_time + ew_time);

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
    //end heuristicFunction


    /**
     * This functions creates name for new Node.
     * For example for parameters ("->14:6->13:7", 12, 8) the output will be "->14:6->13:7->12:8"
     *
     * @param name    - exist name
     * @param ns_time - new north-south time
     * @param ew_time - new east-west time
     * @return full name
     */
    public static String createNewName(String name, double ns_time, double ew_time) {
        return name + Constants.PHASE_DELIMITER + ns_time + Constants.TIMES_DELIMITER + ew_time;
    }

    /**
     * This function converts last phase of distribution from string to numbers.
     * For example ->14:6->13:7 will be converted to [13.0, 7.0].
     *
     * @param str
     * @return
     */
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

    /**
     * This function splits the string of phases to array of phases.
     * For example ->14:6->13:7 will be converted to ["14:6", "13:7"].
     *
     * @param str - full string of distributions
     * @return array of time distribution
     */
    public static String[] getAllPhases(String str) {
        String[] times = str.split(Constants.PHASE_DELIMITER);
        return times;
    }

    /**
     * This function convert phase from string to numbers.
     * For example "14:6" will be converted to [14.0, 6.0].
     *
     * @param str - phase string
     * @return array of numbers that represents times
     */
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

    /**
     * This function convert string of time distribution to numbers and adds it to queue.
     *
     * @param queue - queue with better times (actual is empty queue)
     * @param path  - string of time distribution
     */
    public static void addBetterDistributionToQueue(Queue<Double> queue, String path) {

        String[] phases = getAllPhases(path);
        if (phases.length == 0) {
            return;
        }

        for (String phase : phases) {
            double[] times = getPhaseTimes(phase);
            queue.add(times[0]);
        }

    }


    public static double calculateInitialDuration(Conditions conditions) {
        double time = Constants.TRAFFIC_LIGHT_PHASE_TIME / 2;

        double first_max_phase = calculatePhasesCount(conditions.getLanesInfoFirstCrossroad(), time);
        double second_max_phase = calculatePhasesCount(conditions.getLanesInfoSecondCrossroad(), time);

        double worst_time;

        if (first_max_phase > second_max_phase) {
            worst_time = calculateDurationByPhasesCount(first_max_phase);
        } else {
            worst_time = calculateDurationByPhasesCount(second_max_phase);
        }

        return worst_time;
    }


    private static double calculatePhasesCount(ArrayList<LaneInfo> crossroad, double time) {
        double worst_phases_count = -1;

        for (int i = 0; i < 4; i++) {
            double temp_phases = calculatePhasesCountForDirection(crossroad.get(i), time);
            if (temp_phases > worst_phases_count) {
                worst_phases_count = temp_phases;
            }
        }

        return worst_phases_count;
    }


    private static double calculateDurationByPhasesCount(double phases) {
        double changing_lights_time = Constants.TRAFFIC_LIGHT_CHANGING_TIME * 3 * 2;
        double phase_work_time = Constants.TRAFFIC_LIGHT_PHASE_TIME + changing_lights_time;

        double better_duration = phase_work_time * phases - Constants.TRAFFIC_LIGHT_CHANGING_TIME;

        return better_duration;
    }


    private static double calculatePhasesCountForDirection(LaneInfo lane_info, double time) {
        AlgorithmLaneInfo algorithm_lane_info = new AlgorithmLaneInfo(lane_info);

        int cars_per_phase = calculatePassedCarsByDirection(algorithm_lane_info, time);
        double phase_count = (double) lane_info.getCarsInLane().size() / (double) cars_per_phase;
        phase_count = Math.ceil(phase_count);

        return phase_count;
    }


    /**
     * This function calculates the time duration of the better distribution.
     *
     * @param str - path with better times
     * @return better duration - amount of second that includes traffic lights working and changing time
     */
    public static double calculateDurationFromString(String str) {
        String[] phases = getAllPhases(str);

        double result = calculateDurationByPhasesCount(phases.length);
        return result;
    }


    /**
     * This function create separation string for console.
     *
     * @param str
     * @return
     */
    public static String createSeparationString(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            result += "/";
        }
        return result;
    }

    /**
     * This function calculates the AWT for a given condition state.
     * This function decides for itself whether to check against the found distribution or by default.
     *
     * @param conditions - actual conditions.
     * @return - AWT amount
     */
    @SuppressWarnings("Duplicates")
    public static double calculateAWT(Conditions conditions) {
        int phases_passed = 0;

        double changing_time = Constants.TRAFFIC_LIGHT_CHANGING_TIME * 3;
        double phase_time = Constants.TRAFFIC_LIGHT_PHASE_TIME + changing_time * 2;

        LaneInfo lane_info_first_north = new LaneInfo(conditions.getFirstCrossroadInfo().getNorth());
        LaneInfo lane_info_first_east = new LaneInfo(conditions.getFirstCrossroadInfo().getEast());
        LaneInfo lane_info_first_south = new LaneInfo(conditions.getFirstCrossroadInfo().getSouth());
        LaneInfo lane_info_first_west = new LaneInfo(conditions.getFirstCrossroadInfo().getWest());
        LaneInfo lane_info_second_north = new LaneInfo(conditions.getFirstCrossroadInfo().getNorth());
        LaneInfo lane_info_second_east = new LaneInfo(conditions.getFirstCrossroadInfo().getEast());
        LaneInfo lane_info_second_south = new LaneInfo(conditions.getFirstCrossroadInfo().getSouth());
        LaneInfo lane_info_second_west = new LaneInfo(conditions.getFirstCrossroadInfo().getWest());

        AlgorithmLaneInfo first_north = new AlgorithmLaneInfo(lane_info_first_north);
        AlgorithmLaneInfo first_east = new AlgorithmLaneInfo(lane_info_first_east);
        AlgorithmLaneInfo first_south = new AlgorithmLaneInfo(lane_info_first_south);
        AlgorithmLaneInfo first_west = new AlgorithmLaneInfo(lane_info_first_west);
        AlgorithmLaneInfo second_north = new AlgorithmLaneInfo(lane_info_second_north);
        AlgorithmLaneInfo second_east = new AlgorithmLaneInfo(lane_info_second_east);
        AlgorithmLaneInfo second_south = new AlgorithmLaneInfo(lane_info_second_south);
        AlgorithmLaneInfo second_west = new AlgorithmLaneInfo(lane_info_second_west);

        ArrayList<Double> times_arr = new ArrayList<>();

        if (conditions.getBetterDistributionString().equals("")) {
            double time = Constants.TRAFFIC_LIGHT_PHASE_TIME / 2;
            double[] times = {time, time};
            boolean finish = false;

            while (!finish) {

                calculateASWTForLaneByTime(times_arr, first_north, times[0], phase_time * phases_passed);
                calculateASWTForLaneByTime(times_arr, first_south, times[0], phase_time * phases_passed);
                calculateASWTForLaneByTime(times_arr, second_north, times[0], phase_time * phases_passed);
                calculateASWTForLaneByTime(times_arr, second_south, times[0], phase_time * phases_passed);
                calculateASWTForLaneByTime(times_arr, first_west, times[1], phase_time * phases_passed + 6);
                calculateASWTForLaneByTime(times_arr, first_east, times[1], phase_time * phases_passed + 6);
                calculateASWTForLaneByTime(times_arr, second_east, times[1], phase_time * phases_passed + 6);
                calculateASWTForLaneByTime(times_arr, second_west, times[1], phase_time * phases_passed + 6);

                boolean isFirstFinished = checkIfAllPass(first_north, first_east, first_south, first_west);
                boolean isSecondFinished = checkIfAllPass(first_north, first_east, first_south, first_west);

                if (isFirstFinished && isSecondFinished) {
                    finish = true;
                }

//                if (checkIfAllPass(first_north, first_east, first_south, first_west)) {
//                    finish = true;
//                }
//
//                if (checkIfAllPass(second_north, second_east, second_south, second_west)) {
//                    finish = true;
//                }

                phases_passed++;
            }

        } else {
            String[] phases = getAllPhases(conditions.getBetterDistributionString());

            for (String phase : phases) {
                double times[] = getPhaseTimes(phase);

                calculateASWTForLaneByTime(times_arr, first_north, times[0], phase_time * phases_passed);
                calculateASWTForLaneByTime(times_arr, first_south, times[0], phase_time * phases_passed);
                calculateASWTForLaneByTime(times_arr, second_north, times[0], phase_time * phases_passed);
                calculateASWTForLaneByTime(times_arr, second_south, times[0], phase_time * phases_passed);
                calculateASWTForLaneByTime(times_arr, first_west, times[1], phase_time * phases_passed + 6);
                calculateASWTForLaneByTime(times_arr, first_east, times[1], phase_time * phases_passed + 6);
                calculateASWTForLaneByTime(times_arr, second_east, times[1], phase_time * phases_passed + 6);
                calculateASWTForLaneByTime(times_arr, second_west, times[1], phase_time * phases_passed + 6);

                phases_passed++;
            }
        }

        double result_awt = Utils.round(Formulas.AWT(times_arr), 2);
        return result_awt;
    }

    /**
     * This function calculates the AWT (average waiting time) for the given lane.
     *
     * @param times       - array with all times
     * @param lane_info   - actual lane to calculate
     * @param time        - time of traffic light working
     * @param passed_time - already passed time
     */
    private static void calculateASWTForLaneByTime(ArrayList<Double> times, AlgorithmLaneInfo lane_info, double time, double passed_time) {

        int cars_count = lane_info.getCarsCount();
        double speed_limit = lane_info.getSpeedLimit();
        double car_length = lane_info.getAvgCarLength();
        double dist = lane_info.getDistanceFromCrossroad();

        double acc = 2;

        double time_step = (Constants.SAFETY_DISTANCE_TO_START - Constants.SAFETY_DISTANCE) / acc;
        int passed_count = 0;
        boolean next = true;

        while (next && cars_count - (passed_count) > 0) {

            if (car_length * passed_count < calculatePassedDistance(time - (passed_count * time_step), acc, 0, speed_limit)) {

                double act_time = Formulas.calculateTimeForPassingDistanceByAcc(acc, car_length * passed_count);

                passed_count++;
                times.add(act_time + passed_time);

            } else {
                next = false;
            }
        }

        cars_count = cars_count - (passed_count + 1);

        if (cars_count < 0) {
            cars_count = 0;
        }


        lane_info.setCarsCount(cars_count);
        lane_info.setDistanceFromCrossroad(dist - passed_count * car_length);

    }

    /**
     * This function checks if all cars passed the crossroad.
     *
     * @param n - north info
     * @param e - east inf0
     * @param s - south info
     * @param w - west info
     * @return - true if all size equals to zero, false otherwise
     */
    private static boolean checkIfAllPass(AlgorithmLaneInfo n, AlgorithmLaneInfo e, AlgorithmLaneInfo s, AlgorithmLaneInfo w) {

        if (n.getCarsCount() == 0 && e.getCarsCount() == 0 && s.getCarsCount() == 0 && w.getCarsCount() == 0) {
            return true;
        }

        return false;
    }

    /**
     * This function prints to console message about simulation created status.
     */
    public static void printSimulationCreated() {
        System.out.println();
        System.out.println(ConsoleColors.GREEN_BOLD + "*******************************************");
        System.out.println("\t\t\tSimulation created!");
        System.out.println("*******************************************" + ConsoleColors.RESET);
    }

    /**
     * For store the conditions in database is necessary to convert the system conditions to database conditions.
     *
     * @param conditions - info about crossroads state
     * @return conditions in database representation
     */
    //create Database Conditions from conditions
    public static DatabaseConditions createDatabaseConditions(Conditions conditions) {
        int[] cars_count_first = Utils.getCarsCount(conditions.getFirstCrossroadInfo());
        int[] cars_count_second = Utils.getCarsCount(conditions.getSecondCrossroadInfo());

        int[] speed_limit_first = Utils.getSpeedLimits(conditions.getFirstCrossroadInfo());
        int[] speed_limit_second = Utils.getSpeedLimits(conditions.getSecondCrossroadInfo());

        int[] actual_speed_first = Utils.getActualSpeeds(conditions.getFirstCrossroadInfo());
        int[] actual_speed_second = Utils.getActualSpeeds(conditions.getSecondCrossroadInfo());

        double init_time = conditions.getInitialDuration();
        double alg_time = conditions.getAlgorithmDuration();
        double sim_time = conditions.getSimulationDuration();

        double init_aws = conditions.getInitialAWT();
        double alg_aws = conditions.getAlgorithmAWT();

        String better_distribution = conditions.getBetterDistributionString();

        DatabaseConditions database_conditions = new DatabaseConditions(
                cars_count_first,
                cars_count_second,
                speed_limit_first,
                speed_limit_second,
                actual_speed_first,
                actual_speed_second,
                init_time,
                alg_time,
                sim_time,
                init_aws,
                alg_aws,
                better_distribution
        );

        return database_conditions;
    }

    /**
     * This function receives crossroad info and returns array with cars counts of this crossroad.
     *
     * @param crossroad_info - information about crossroad
     * @return - array with information about cars
     */
    private static int[] getCarsCount(CrossroadInfo crossroad_info) {
        int[] res = new int[4];

        res[Constants.NORTH_DIRECTION] = crossroad_info.getNorth().getCarsCount();
        res[Constants.EAST_DIRECTION] = crossroad_info.getEast().getCarsCount();
        res[Constants.SOUTH_DIRECTION] = crossroad_info.getSouth().getCarsCount();
        res[Constants.WEST_DIRECTION] = crossroad_info.getWest().getCarsCount();

        return res;
    }

    /**
     * This function receives crossroad info and returns array with speed limits of this crossroad.
     *
     * @param crossroad_info - information about crossroad
     * @return - array with information about speed limits
     */
    private static int[] getSpeedLimits(CrossroadInfo crossroad_info) {
        int[] res = new int[4];

        res[Constants.NORTH_DIRECTION] = (int) crossroad_info.getNorth().getSpeedLimit();
        res[Constants.EAST_DIRECTION] = (int) crossroad_info.getEast().getSpeedLimit();
        res[Constants.SOUTH_DIRECTION] = (int) crossroad_info.getSouth().getSpeedLimit();
        res[Constants.WEST_DIRECTION] = (int) crossroad_info.getWest().getSpeedLimit();

        return res;
    }

    /**
     * This function receives crossroad info and returns array with actual speeds of this crossroad.
     *
     * @param crossroad_info - information about crossroad
     * @return - array with information about actuals speed
     */
    private static int[] getActualSpeeds(CrossroadInfo crossroad_info) {
        int[] res = new int[4];

        res[Constants.NORTH_DIRECTION] = (int) crossroad_info.getNorth().getActualSpeed();
        res[Constants.EAST_DIRECTION] = (int) crossroad_info.getEast().getActualSpeed();
        res[Constants.SOUTH_DIRECTION] = (int) crossroad_info.getSouth().getActualSpeed();
        res[Constants.WEST_DIRECTION] = (int) crossroad_info.getWest().getActualSpeed();

        return res;
    }
    //end of database conditions created

    /**
     * This function checks if the car can go faster or if it needs to slow down the actual speed.
     *
     * @param speed - actual speed
     * @param dec   - deceleration of car
     * @param dist  - distance to next car
     * @return - true if can go, false - otherwise
     */
    public static boolean isCarCanGo(double speed, double dec, double dist) {

        while (speed > 0) {
            dist -= speed;
            speed -= dec;
        }

        if (dist > Constants.SAFETY_DISTANCE - 1) {
            return true;
        }

        return false;
    }
}
