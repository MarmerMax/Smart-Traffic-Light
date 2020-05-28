package Database;

import Objects.SystemConditions.Conditions;

public class Database {

    public Conditions getFromDatabase(String id) {
        return null;
    }

    public void putToDatabase(Conditions conditions) {
        //break conditions into specific objects and save them to tables
        //crossroadInfo: crossroad, north, east, south, west
        //crossroadInfo -> Table conditions.
        //Conditions table comprises from id, name, crossroadInfo1, crossroadInfo2
        //crossroad -> Table crossroads
        //north, east, south, west -> Table directionInfo
    }
}
