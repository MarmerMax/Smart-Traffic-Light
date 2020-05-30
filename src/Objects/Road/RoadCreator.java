package Objects.Road;

public class RoadCreator {

    public static Road[] createRoads(int num1, int num2) {
        Road r1 = new Road(num1);
        Road r2 = new Road(num2);

        Road[] roads = {r1, r2, r1, r2};
        return roads;
    }
}
