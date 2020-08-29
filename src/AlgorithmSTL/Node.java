package AlgorithmSTL;

import Tools.Utils;

public class Node {
    private double east_west_time;
    private double north_south_time;

    private double price;
    private double heuristic_price;
    private boolean out;
    private String name;

    private AlgorithmConditions conditions;


    public Node(String n, double ns_time, double ew_time, AlgorithmConditions conditions) {
        this.conditions = conditions;

        //for represent path
        east_west_time = ew_time;
        north_south_time = ns_time;
        name = n + "-" + north_south_time + ":" + east_west_time; //ns:ew -> 10:10 or 9:11...

        //time distribution
        price = east_west_time + north_south_time;

        //It is necessary to create a method that will calculate how long it will take for cars
        //to pass the intersection with the actual distribution of time
        heuristic_price = Utils.heuristicFunction(this.conditions);
    }

    public int getCarsCount() {
        return conditions.getEastWestCarsCount() + conditions.getNorthSouthCarsCount();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getHeuristicPrice() {
        return heuristic_price;
    }

    public double getTotalPrice() {
        return price + heuristic_price;
    }

    public void markAsNotOut() {
        out = false;
    }

    public void markAsOut() {
        out = true;
    }

    public boolean getOut() {
        return out;
    }

    public AlgorithmConditions getConditions() {
        return conditions;
    }
}
