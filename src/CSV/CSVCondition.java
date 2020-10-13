package CSV;

/**
 * This class represents the conditions to be taken from the csv file.
 */
public class CSVCondition {

    private int[] cars_first_crossroad;
    private int[] cars_second_crossroad;

    private int[] speed_limit_first_crossroad;
    private int[] speed_limit_second_crossroad;

    private int[] actual_speed_first_crossroad;
    private int[] actual_speed_second_crossroad;

    public CSVCondition(int[] cars_first_crossroad,
                        int[] cars_second_crossroad,
                        int[] speed_limit_first_crossroad,
                        int[] speed_limit_second_crossroad,
                        int[] actual_speed_first_crossroad,
                        int[] actual_speed_second_crossroad) {

        this.cars_first_crossroad = cars_first_crossroad;
        this.cars_second_crossroad = cars_second_crossroad;
        this.speed_limit_first_crossroad = speed_limit_first_crossroad;
        this.speed_limit_second_crossroad = speed_limit_second_crossroad;
        this.actual_speed_first_crossroad = actual_speed_first_crossroad;
        this.actual_speed_second_crossroad = actual_speed_second_crossroad;
    }

    public int[][] getFirstCrossroad(){
        int[][] res = {cars_first_crossroad, speed_limit_first_crossroad, actual_speed_first_crossroad};
        return res;
    }

    public int[][] getSecondCrossroad(){
        int[][] res = {cars_second_crossroad, speed_limit_second_crossroad, actual_speed_second_crossroad};
        return res;
    }

    public int[] getCarsFirstCrossroad() {
        return cars_first_crossroad;
    }

    public int[] getCarsSecondCrossroad() {
        return cars_second_crossroad;
    }

    public int[] getSpeedLimitFirstCrossroad() {
        return speed_limit_first_crossroad;
    }

    public int[] getSpeedLimitSecondCrossroad() {
        return speed_limit_second_crossroad;
    }

    public int[] getActualSpeedFirstCrossroad() {
        return actual_speed_first_crossroad;
    }

    public int[] getActualSpeedSecondCrossroad() {
        return actual_speed_second_crossroad;
    }
}
