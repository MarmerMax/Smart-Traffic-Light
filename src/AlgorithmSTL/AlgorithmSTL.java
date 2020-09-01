package AlgorithmSTL;

import Tools.Utils;

/**
 * An abstract class for all algorithms.
 * Each algorithm should implement only one function - checkIfPathExist (start, target).
 * Other functions are common to all algorithms.
 */

public abstract class AlgorithmSTL extends Thread {

    protected double price;
    protected String path;
    protected boolean is_path_exist;
    protected TrafficConditions traffic_conditions;

    public AlgorithmSTL() {
        price = 0;
        path = "no path";
        is_path_exist = false;
    }

    @Override
    public void run() {
        checkTrafficConditions();
    }

    protected abstract boolean checkIfPathExist(Node start, Node goal);

    /**
     * This function checks if there is a better solution for a given traffic conditions.
     */
    private void checkTrafficConditions() {
        if (Utils.isEqualsNodes(traffic_conditions.getRoot(), traffic_conditions.getGoal())) { //check if the start state equals to the goal state
            return;
        } else if (!traffic_conditions.getIsPathExist()) { //check if all speeds > 0
            return;
        } else {
            is_path_exist = checkIfPathExist(traffic_conditions.getRoot(), traffic_conditions.getGoal());
            if (is_path_exist) {
                traffic_conditions.setBetterDistribution(path);
                System.out.println(path);
            }
        }
    }

    public void setTrafficConditions(TrafficConditions conditions) {
        traffic_conditions = conditions;
    }

    //if current node is the target node then update path, price...
    protected boolean isGoal(Node current, Node target) {
        if (Utils.isEqualsNodes(current, target)) {
            path = current.getName().substring(2);
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

    public boolean getPathExist() {
        return is_path_exist;
    }
}