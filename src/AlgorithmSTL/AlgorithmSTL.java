package AlgorithmSTL;

import Tools.Utils;

/**
 * An abstract class for all algorithms.
 * Each algorithm should implement only one function - checkIfPathExist (start, target).
 * Other functions are common to all algorithms.
 */

public abstract class AlgorithmSTL extends Thread{

    protected double price;
    protected long nodes_amount;
    protected String path;
    protected boolean is_path_exist;
    protected TrafficConditions traffic_conditions;

    public AlgorithmSTL() {
        price = 0;
        nodes_amount = 1; //start node
        path = "no path";
        is_path_exist = false;
    }

    @Override
    public void run(){
//        checkTrafficConditions();
    }

    protected abstract boolean checkIfPathExist(Node start, Node goal);

    /**
     * This function checks if there is a solution for a given tile puzzle.
     *
     * @param tc - a tile puzzle that needs to be checked
     */
    public void checkTrafficConditions(TrafficConditions tc) {

        traffic_conditions = tc;

        if (TrafficConditions.isGoal(traffic_conditions.getRoot(), traffic_conditions.getGoal())) { //check if the start state equals to the goal state
            return;
        } else if (!traffic_conditions.getIsPathExist()) { //check if all speeds > 0
            return;
        } else {
            //injection point of the algorithm -> for each algorithm will be the different implementation
            is_path_exist = checkIfPathExist(traffic_conditions.getRoot(), traffic_conditions.getGoal());
            System.out.println(path);
        }
    }

    //if current node is the target node then update path, price...
    protected boolean isGoal(Node current, Node target) {
        if (Utils.isEqualsNodes(current, target)) {
//        if (current.getCarsCount() == target.getCarsCount()) {
//            is_path_exist = true;
            path = current.getName().substring(1);
            //current path split by '-'
            price = current.getPrice();
            return true;
        }
        return false;
    }

    public String getPath() {
        return path;
    }

    public double getPrice() {
        return price;
    }

    public long getNodesAmount() {
        return nodes_amount;
    }

    public boolean getPathExist() {
        return is_path_exist;
    }
}