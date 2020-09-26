package SystemSTL.AlgorithmSTL.AlgorithmCore;

import SystemSTL.AlgorithmSTL.AlgorithmRules;
import SystemSTL.AlgorithmSTL.AlgorithmSTL;
import SystemSTL.AlgorithmSTL.Node;
import Tools.Utils;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class IDA extends AlgorithmSTL {

    public IDA() {
        super();
    }

    @Override
    protected boolean checkIfPathExist(Node start, Node goal) {
        //create stack for nodes and set for checking loop avoidance
        Stack<Node> stack = new Stack<>();
        Set<Node> loop_avoidance_list = new HashSet<>();

        double threshold = start.getHeuristicPrice();

        while (threshold != Double.MAX_VALUE) {

            double min_price = Double.MAX_VALUE;

            stack.add(start);
            loop_avoidance_list.add(start);

            while (!stack.empty()) {

                Node current = stack.pop();

                if (current.getOut()) {

                    loop_avoidance_list.remove(current);

                } else {

                    current.markAsOut();
                    stack.add(current);


                    double max_time = traffic_rules.getMaxTime();
                    double min_time = traffic_rules.getMinTime();

                    int actions_count = (int) (max_time - min_time) + 1;

                    for (int i = 0; i < actions_count; i++) {

                        Node neighbour = AlgorithmRules.createNeighbourForNodeByAction(current, min_time + i, max_time, min_time);

                        if (neighbour != null) {

                            double neighbour_total_price = neighbour.getTotalPrice();

                            if (neighbour_total_price > threshold) {

                                min_price = Math.min(min_price, neighbour_total_price);
                                continue;
                            }

                            //if not continue with the next operator and neighbour already exists in loop avoidance list
                            if (Utils.checkIfNodeExistsInList(neighbour, loop_avoidance_list)) {

                                //get same node
                                Node same_node = Utils.getSameNode(neighbour, loop_avoidance_list);

                                if (same_node.getOut()) {
                                    continue;
                                } else {

                                    double same_node_total_price = same_node.getTotalPrice();

                                    if (same_node_total_price > neighbour_total_price) {
                                        stack.remove(same_node);
                                        loop_avoidance_list.remove(same_node);
                                    } else {
                                        continue;
                                    }
                                }
                            }

                            //if this node has lower or equal price to threshold and does not exists in stack
                            //then check if is goal node
                            if (isGoal(neighbour, goal)) {
                                return true;
                            }
                            stack.add(neighbour);
                            loop_avoidance_list.add(neighbour);
                        }
                    }
                }
            }
            threshold = min_price;
            start.markAsNotOut();
        }
        return false;
    }
}
