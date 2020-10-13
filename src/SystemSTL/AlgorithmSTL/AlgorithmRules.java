package SystemSTL.AlgorithmSTL;

import Objects.Conditions.Conditions;
import Tools.Utils;

import java.util.ArrayList;

/**
 * This class represents the rules for the algorithm.
 * The class creates the initial and target states for the obtained algorithm conditions.
 * The task of the algorithm is to find the path from the beginning to the goal.
 * For this purpose, it is necessary to create the following states from the initial to the target state.
 * This class helps create neighbors for each state by calculate next states.
 */
public class AlgorithmRules {

    private Node root;
    private Node goal;
    private double min_time; //min traffic light working time
    private double max_time; //max traffic light working time

    private static Conditions conditions;
    private static AlgorithmConditions algorithm_conditions;

    public AlgorithmRules(Conditions conditions) {
        this.conditions = conditions;
        algorithm_conditions = new AlgorithmConditions(conditions);

        //initial state
        root = new Node("", 0, algorithm_conditions);

        //goal state
        AlgorithmConditions goal_conditions = Utils.createGoalAlgorithmConditions(); //zero conditions
        goal = new Node("", 0, goal_conditions);

        double awt = Utils.calculateAWT(conditions);
        goal.setAWT(awt);

        min_time = conditions.getFirstCrossroadInfo().getCrossroad().getTimeDistribution().getMinTime();
        max_time = conditions.getFirstCrossroadInfo().getCrossroad().getTimeDistribution().getMaxTime();
    }

    /**
     * This function creates all neighbours of the given node in tree.
     *
     * @param current  - node
     * @param max_time - max time
     * @param min_time - min time
     * @return list of all neighbours
     */
    public static ArrayList<Node> createNeighboursOfNode(Node current, double max_time, double min_time) {
        ArrayList<Node> neighbours = new ArrayList<>();

        int actions_count = (int) (max_time - min_time) + 1;

        for (int i = 0; i < actions_count; i++) {
            Node next = createNeighbourForNodeByAction(current, min_time + i, max_time, min_time);
            if (next != null) {
                neighbours.add(next);
            }
        }

        return neighbours;
    }

    /**
     * This function creates the next node from the current node at the given time to perform calculations.
     *
     * @param current  - current node that represent current state
     * @param time     - time to execute calculation on this state
     * @param max_time - max time of traffic light working
     * @param min_time - min time of traffic light working
     * @return node that represent next state after calculation
     */
    public static Node createNeighbourForNodeByAction(Node current, double time, double max_time, double min_time) {
        if (time > max_time) {
            return null;
        }

        ArrayList<Double> previous_times = Utils.getLastAndCurrentTimeOfNode(current.getName());

        if (previous_times != null) {
            if (previous_times.contains(time)) {
                return null;
            }
        }

        double north_south_time = time;
        double east_west_time = (max_time + min_time) - time;

        AlgorithmConditions new_algorithm_conditions = new AlgorithmConditions(current.getConditions());

        Utils.updatePassedCars(new_algorithm_conditions, north_south_time, east_west_time);

        String new_name = Utils.createNewName(current.getName(), north_south_time, east_west_time);
        Node neighbour = new Node(new_name, current.getPrice() + (max_time + min_time), new_algorithm_conditions);

        return neighbour;
    }

    /**
     * This function checks if all traffic lights have a positive speed limit.
     * If each direction has a positive speed limit, then the algorithm will be completed in a finite time.
     *
     * @return true the all times is more than 0
     */
    public boolean getIsPathExist() {
        for (AlgorithmLaneInfo lane_info : algorithm_conditions.getLanesInfoFirstCrossroad()) {
            if (lane_info.getSpeedLimit() <= 0) {
                return false;
            }
        }

        for (AlgorithmLaneInfo lane_info : algorithm_conditions.getLanesInfoSecondCrossroad()) {
            if (lane_info.getSpeedLimit() <= 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * This functions changes the time distribution of traffic lights.
     * Functions receives string of times that will passed to conditions class and converted to numbers.
     * After this all times will be added to queue of time distribution.
     *
     * @param result
     */
    public void setBetterDistribution(String result) {
        conditions.setBetterDistribution(result);
        double new_awt = Utils.calculateAWT(conditions);
        conditions.setAlgorithmAWT(new_awt);
    }

    public String getBetterDistribution() {
        return conditions.getBetterDistributionString();
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
