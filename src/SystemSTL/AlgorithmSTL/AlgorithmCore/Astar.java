package SystemSTL.AlgorithmSTL.AlgorithmCore;

import SystemSTL.AlgorithmSTL.AlgorithmRules;
import SystemSTL.AlgorithmSTL.AlgorithmSTL;
import SystemSTL.AlgorithmSTL.Node;
import Tools.Utils;

import java.util.*;

public class Astar extends AlgorithmSTL {

    public Astar() {
        super();
    }

    @Override
    public boolean checkIfPathExist(Node start, Node goal) {
        Comparator<Node> comparator = new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return (int) (n1.getTotalPrice() - n2.getTotalPrice());
            }
        };
        Queue<Node> queue = new PriorityQueue<>(comparator);
        Set<Node> open_list = new HashSet<>();
        Set<Node> close_list = new HashSet<>();

        queue.add(start);
        open_list.add(start);

        while (!queue.isEmpty()) {

            Node current = queue.poll();
            open_list.remove(current);

            if (isGoal(current, goal)) {
                return true;
            }

            close_list.add(current);

            double max_time = traffic_conditions.getMaxTime();
            double min_time = traffic_conditions.getMinTime();

            int actions_count = (int) (max_time - min_time) + 1;

            for (int i = 0; i < actions_count; i++) {

                Node neighbour = AlgorithmRules.createNeighbourForNodeByAction(current, min_time + i, max_time, min_time);

                if (neighbour != null) {
                    if (!Utils.checkIfNodeExistsInList(neighbour, open_list)
                            && !Utils.checkIfNodeExistsInList(neighbour, close_list)) {

                        queue.add(neighbour);
                        open_list.add(neighbour);

                    } else if (Utils.checkIfNodeExistsInList(neighbour, open_list)) {

                        Utils.changeBetweenNodesInQueue(queue, open_list, neighbour);

                    }
                }
            }
        }
        return false;
    }
}