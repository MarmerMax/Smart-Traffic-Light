package AlgorithmSTL;

import Objects.Conditions.Conditions;
import Tools.Utils;

import java.util.ArrayList;


public class TrafficConditions {

    private Node root;
    private Node goal;
    private double min_time; //min traffic light working time
    private double max_time; //max traffic light working time

    private static AlgorithmConditions conditions;

    public TrafficConditions(Conditions conditions) {
        this.conditions = new AlgorithmConditions(conditions);

        //initial state
        root = new Node("", 0, 0, this.conditions);

        //goal state
        AlgorithmConditions goal_conditions = Utils.createGoalAlgorithmConditions(); //zero conditions
        goal = new Node("", 0, 0, goal_conditions);

        min_time = conditions.getFirstCrossroadInfo().getCrossroad().getTimeDistribution().getMinTime();
        max_time = conditions.getFirstCrossroadInfo().getCrossroad().getTimeDistribution().getMaxTime();
    }

    public static Node createNeighbourForNodeByAction(Node current, double time, double max_time) {
        if (time > max_time) {
            return null;
        }

        double north_south_time = time;
        double east_west_time = max_time - time;

        AlgorithmConditions new_conditions = new AlgorithmConditions(current.getConditions());
        Utils.updatePassedCars(new_conditions, north_south_time, east_west_time);

        Node neighbour = new Node(current.getName(), north_south_time, east_west_time, new_conditions);

        return neighbour;
    }

    //check all speeds > 0
    public boolean getIsPathExist() {
        for (AlgorithmLaneInfo lane_info : conditions.getLanesInfoFirstCrossroad()) {
//            if (lane_info.getSpeedLimit() <= 0 %% lane_info.getActualSpeed() <= 0) {
            if (lane_info.getSpeedLimit() <= 0) {
                return false;
            }
        }

        for (AlgorithmLaneInfo lane_info : conditions.getLanesInfoSecondCrossroad()) {
//            if (lane_info.getSpeedLimit() <= 0 %% lane_info.getActualSpeed() <= 0) {
            if (lane_info.getSpeedLimit() <= 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean isGoal(Node current, Node goal) {
        if (current.getCarsCount() == goal.getCarsCount()) {
            return true;
        }
        return false;
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
