package AlgorithmSTL;

import Objects.Conditions.Conditions;
import Tools.Utils;


public class TrafficConditions {

    private Node root;
    private Node goal;
    private double min_time; //min traffic light working time
    private double max_time; //max traffic light working time

    private static Conditions conditions;
    private static AlgorithmConditions algorithm_conditions;

    public TrafficConditions(Conditions conditions) {
        this.conditions = conditions;
        this.algorithm_conditions = new AlgorithmConditions(conditions);

        //initial state
        root = new Node("", 0, this.algorithm_conditions);

        //goal state
        AlgorithmConditions goal_conditions = Utils.createGoalAlgorithmConditions(); //zero conditions
        goal = new Node("", 0, goal_conditions);

        min_time = conditions.getFirstCrossroadInfo().getCrossroad().getTimeDistribution().getMinTime();
        max_time = conditions.getFirstCrossroadInfo().getCrossroad().getTimeDistribution().getMaxTime();
    }

    public static Node createNeighbourForNodeByAction(Node current, double time, double max_time, double min_time) {
        if (time > max_time) {
            return null;
        }

        double north_south_time = time;
        double east_west_time = (max_time + min_time) - time;

        AlgorithmConditions new_algorithm_conditions = new AlgorithmConditions(current.getConditions());

        Utils.updatePassedCars(new_algorithm_conditions, north_south_time, east_west_time);

        String new_name = Utils.createNewName(current.getName(), north_south_time, east_west_time);
        Node neighbour = new Node(new_name, current.getPrice() + 1, new_algorithm_conditions);

        return neighbour;
    }

    //check all speeds > 0
    public boolean getIsPathExist() {
        for (AlgorithmLaneInfo lane_info : algorithm_conditions.getLanesInfoFirstCrossroad()) {
//            if (lane_info.getSpeedLimit() <= 0 %% lane_info.getActualSpeed() <= 0) {
            if (lane_info.getSpeedLimit() <= 0) {
                return false;
            }
        }

        for (AlgorithmLaneInfo lane_info : algorithm_conditions.getLanesInfoSecondCrossroad()) {
//            if (lane_info.getSpeedLimit() <= 0 %% lane_info.getActualSpeed() <= 0) {
            if (lane_info.getSpeedLimit() <= 0) {
                return false;
            }
        }

        return true;
    }

    public void setBetterDistribution(String result) {
        this.conditions.setBetterDistribution(result);
    }

    public Node getRoot() {
        return root;
    }

    public Node getGoal() {
        return goal;
    }

    public double getMaxTime() {
        return max_time;
    }

    public double getMinTime() {
        return min_time;
    }
}
