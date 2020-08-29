package AlgorithmSTL;

import Tools.Utils;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class IDA extends AlgorithmSTL {

    public IDA() {
        super();
    }

    /**
     * IDA*(Node start, Vector Goals)
     * 1. L  make_stack and H  make_hash_table
     * 2. t  h(start)
     * 3. While t ≠ ∞
     * 1. minF  ∞
     * 2. L.insert(start) and H.insert(start)
     * 3. While L is not empty
     * 1. n  L.remove_front()
     * 2. If n is marked as “out”
     * 1. H.remove(n)
     * 3. Else
     * 1. mark n as “out” and L.insert(n)
     * 2. For each allowed operator on n
     * 3. g  operator(n)
     * 1. If f(g) > t
     * 1. minF  min(minF, f(g))
     * 2. continue with the next operator
     * 2. If H contains g’=g and g’ marked “out”
     * 1. continue with the next operator
     * 3. If H contains g’=g and g’ not marked “out”
     * 1. If f(g’)>f(g)
     * 1. remove g’ from L and H
     * 2. Else
     * 1. continue with the next operator
     * 4. If goal(g) then return path(g) //all the “out” nodes in L
     * 5. L.insert(g) and H.insert(g)
     * 4. t  minF
     * 4. Return false
     */

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


                    double max_time = traffic_conditions.getMaxTime();
                    double min_time = traffic_conditions.getMinTime();

                    int actions_count = (int) (max_time - min_time) + 1;

                    for (int i = 0; i < actions_count; i++) {

                        Node neighbour = TrafficConditions.createNeighbourForNodeByAction(current, min_time + i, max_time);

                        if (neighbour != null) {
                            nodes_amount++;

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
