package SystemSTL.AlgorithmSTL.AlgorithmCore;

import SystemSTL.AlgorithmSTL.AlgorithmRules;
import SystemSTL.AlgorithmSTL.AlgorithmSTL;
import SystemSTL.AlgorithmSTL.Node;
import Tools.Utils;

import java.util.*;

public class DFBnB extends AlgorithmSTL {

    public DFBnB() {
        super();
    }

    @Override
    protected boolean checkIfPathExist(Node start, Node goal) {

        //create stack for nodes
        Stack<Node> stack = new Stack<>();
        stack.add(start);

        //create hash set for checking loop avoidance
        Set<Node> loop_avoidance_list = new HashSet<>();
        loop_avoidance_list.add(start);

        int threshold = Integer.MAX_VALUE;
        boolean result = false;

        while (!stack.empty() && !isStopped) {

            Node current = stack.pop();

            if (current.getOut()) {

                loop_avoidance_list.remove(current);

            } else {

                current.markAsOut();
                stack.add(current);

                //create all available neighbours for the current node
                double max_time = traffic_rules.getMaxTime();
                double min_time = traffic_rules.getMinTime();

                ArrayList<Node> neighbours = AlgorithmRules.createNeighboursOfNode(current, max_time, min_time);

                //if there is more than one neighbor, sort them by price
                if (neighbours.size() > 1) {
                    //create a comparator that sorts nodes by total price
                    Comparator<Node> comparator = new Comparator<Node>() {
                        @Override
                        public int compare(Node n1, Node n2) {
                            return (int) (n1.getTotalPrice() - n2.getTotalPrice());
                        }
                    };
                    neighbours.sort(comparator);
                }

                for (int i = 0; i < neighbours.size(); i++) {

                    Node neighbour = neighbours.get(i);

                    double neighbour_total_price = neighbour.getTotalPrice();

                    //if cheapest neighbour has price higher than threshold then remove all nodes from neighbours
                    if (neighbour_total_price >= threshold) {

                        neighbours.clear();

                    } else if (Utils.checkIfNodeExistsInList(neighbour, loop_avoidance_list)) {

                        Node same_node = Utils.getSameNode(neighbour, loop_avoidance_list);

                        if (same_node.getOut()) {
                            neighbours.remove(neighbour);
                            i--;
                        } else {

                            double same_node_total_price = same_node.getTotalPrice();

                            if (same_node_total_price > neighbour_total_price) {
                                stack.remove(same_node);
                                loop_avoidance_list.remove(same_node);
                            } else {
                                neighbours.remove(neighbour);
                                i--;
                            }

                        }

                    } else if (isGoal(neighbour, goal)) {
                        result = true;
                        threshold = (int)neighbour_total_price;
                        neighbours.clear();
                    }

                }

                if (neighbours.size() > 0) {
                    Collections.reverse(neighbours);
                    for (Node node : neighbours) {
                        stack.push(node);
                        loop_avoidance_list.add(node);
                    }
                }
            }
        }
        return result;
    }
}