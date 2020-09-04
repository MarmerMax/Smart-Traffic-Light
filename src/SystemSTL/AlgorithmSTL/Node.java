package SystemSTL.AlgorithmSTL;

import Tools.Utils;

public class Node {

    private double price;
    private double heuristic_price;
    private boolean out;
    private String name;

    private AlgorithmConditions conditions;


    public Node(String name, double price, AlgorithmConditions conditions) {
        this.conditions = conditions;
        this.name = name;
        this.price = price;

//        print();

        double[] times = Utils.getLastTimesFromName(name);

        heuristic_price = Utils.heuristicFunction(this.conditions, times);
    }

    private void print() {
        int i1 = conditions.getLanesInfoFirstCrossroad().get(0).getCarsCount();
        int i2 = conditions.getLanesInfoFirstCrossroad().get(1).getCarsCount();
        int i3 = conditions.getLanesInfoFirstCrossroad().get(2).getCarsCount();
        int i4 = conditions.getLanesInfoFirstCrossroad().get(3).getCarsCount();

        int i11 = conditions.getLanesInfoSecondCrossroad().get(0).getCarsCount();
        int i22 = conditions.getLanesInfoSecondCrossroad().get(1).getCarsCount();
        int i33 = conditions.getLanesInfoSecondCrossroad().get(2).getCarsCount();
        int i44 = conditions.getLanesInfoSecondCrossroad().get(3).getCarsCount();

        System.out.println(i1 + "-" + i2 + "-" + i3 + "-" + i4 + ":" + i11 + "-" + i22 + "-" + i33 + "-" + i44);
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
