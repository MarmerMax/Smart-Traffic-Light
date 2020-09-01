package AlgorithmSTL;

import Objects.Conditions.Conditions;
import SystemSTL.LaneInfo;
import Tools.Constants;

import java.util.ArrayList;

public class AlgorithmConditions {


    private ArrayList<AlgorithmLaneInfo> lane_info_first_crossroad;
    private ArrayList<AlgorithmLaneInfo> lane_info_second_crossroad;

    //for first create from Conditions
    public AlgorithmConditions(Conditions conditions) {
        initializeAlgorithmConditions(conditions);
    }

    //for create goal state
    public AlgorithmConditions(ArrayList<AlgorithmLaneInfo> first, ArrayList<AlgorithmLaneInfo> second) {
        lane_info_first_crossroad = first;
        lane_info_second_crossroad = second;
    }

    //for create all neighbours
    public AlgorithmConditions(AlgorithmConditions conditions) {
        lane_info_first_crossroad = copyCrossroad(conditions.getLanesInfoFirstCrossroad());
        lane_info_second_crossroad = copyCrossroad(conditions.getLanesInfoSecondCrossroad());
    }

    private void initializeAlgorithmConditions(Conditions conditions) {
        lane_info_first_crossroad = createConditionsForCrossroad(conditions.getLanesInfoFirstCrossroad());
        lane_info_second_crossroad = createConditionsForCrossroad(conditions.getLanesInfoSecondCrossroad());
    }

    private ArrayList<AlgorithmLaneInfo> createConditionsForCrossroad(ArrayList<LaneInfo> cars_crossroad) {
        ArrayList<AlgorithmLaneInfo> list = new ArrayList<>();

        for (LaneInfo lane_info : cars_crossroad) {
            list.add(new AlgorithmLaneInfo(lane_info));
        }

        return list;
    }

    private ArrayList<AlgorithmLaneInfo> copyCrossroad(ArrayList<AlgorithmLaneInfo> crossroad) {
        ArrayList<AlgorithmLaneInfo> new_crossroad = new ArrayList<>();
        for (AlgorithmLaneInfo old_lane : crossroad) {
            AlgorithmLaneInfo new_lane = new AlgorithmLaneInfo(old_lane);
            new_crossroad.add(new_lane);
        }
        return new_crossroad;
    }

    public ArrayList<AlgorithmLaneInfo> getLanesInfoFirstCrossroad() {
        return lane_info_first_crossroad;
    }

    public ArrayList<AlgorithmLaneInfo> getLanesInfoSecondCrossroad() {
        return lane_info_second_crossroad;
    }

    public int getEastWestCarsCount() {
        return calculateCarsCountForDirections(false);
    }

    public int getNorthSouthCarsCount() {
        return calculateCarsCountForDirections(true);
    }

    private int calculateCarsCountForDirections(boolean is_north_south) {
        int count = 0;

        for (int i = 0; i < 4; i++) {
            if (is_north_south) {
                if (i == Constants.NORTH_DIRECTION || i == Constants.SOUTH_DIRECTION) {
                    count += lane_info_first_crossroad.get(i).getCarsCount();
                    count += lane_info_second_crossroad.get(i).getCarsCount();
                }
            } else {
                if (i == Constants.EAST_DIRECTION || i == Constants.WEST_DIRECTION) {
                    count += lane_info_first_crossroad.get(i).getCarsCount();
                    count += lane_info_second_crossroad.get(i).getCarsCount();
                }
            }
        }

        return count;
    }
}


