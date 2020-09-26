package SystemSTL.AlgorithmSTL;

import Objects.Conditions.Conditions;
import Tools.ConsoleColors;
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
    protected volatile boolean isStopped;

    protected AlgorithmRules traffic_rules;
    private Conditions conditions;

    public AlgorithmSTL() {
        isStopped = false;
        price = 0;
        path = "no path";
        is_path_exist = false;
    }

    @Override
    public void run() {
        checkTrafficConditions();
    }


    public void stopAlgorithmSTL() {
        isStopped = true;
    }

    /**
     * Abstract functions that will be search the path of best time distribution.
     *
     * @param start - start state
     * @param goal  - goal state
     * @return true - path exist, false - otherwise
     */
    protected abstract boolean checkIfPathExist(Node start, Node goal);

    /**
     * This function checks if there is a better solution for a given traffic conditions.
     */
    private void checkTrafficConditions() {
        if (Utils.isEqualsNodes(traffic_rules.getRoot(), traffic_rules.getGoal())) { //check if the start state equals to the goal state
            return;
        } else if (!traffic_rules.getIsPathExist()) { //check if all speeds > 0
            return;
        } else {
            is_path_exist = checkIfPathExist(traffic_rules.getRoot(), traffic_rules.getGoal());

            if (is_path_exist && !traffic_rules.getBetterDistribution().equals(path)) {
                traffic_rules.setBetterDistribution(path);
            }

            if (!path.equals("no path")) {
                printFinish();
            }
        }
    }

    /**
     * This function sets the conditions for checking the existing best distribution.
     *
     * @param conditions
     */
    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
        traffic_rules = new AlgorithmRules(conditions);
    }

    /**
     * This functions checks if two nodes has a similar conditions.
     *
     * @param current - first node
     * @param target  - second node
     * @return true - if states are similar, false - otherwise
     */
    //if current node is the target node then update path, price...
    protected boolean isGoal(Node current, Node target) {
        if (Utils.isEqualsNodes(current, target)) {

            double current_awt = Utils.calculateAWTForDistributionString(conditions, current.getName().substring(2));
            current.setAWT(current_awt);

            if (current.getAWT() + 0.1 < target.getAWT()) {
//            if (current.getPrice() < target.getPrice()) {
                is_path_exist = true;
                path = current.getName().substring(2);
                price = current.getPrice();


//                if (is_path_exist) {
                traffic_rules.setBetterDistribution(path);
//                }


                printFoundPath(path);

                return true;
            }
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

    private void printFoundPath(String path) {
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + Utils.createSeparationString(path) + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN_BRIGHT + "New path was found!" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN_BRIGHT + path + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN_BRIGHT + "Path duration: " + Utils.calculateDurationFromString(path) + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + Utils.createSeparationString(path) + ConsoleColors.RESET);
        System.out.println();
    }

    private void printFinish() {
        String msg = "The STL algorithm is finished with a better path.";
        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + Utils.createSeparationString(msg) + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED_BRIGHT + msg + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + Utils.createSeparationString(msg) + ConsoleColors.RESET);
        System.out.println();
    }
}