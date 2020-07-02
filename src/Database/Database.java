package Database;

import Objects.Conditions.Conditions;
import SystemSTL.Algorithm;
import Tools.Constants;

import java.sql.*;

public class Database {

    private static final String url = "jdbc:mysql://localhost:3306/stl";
    private static final String user = "root";
    private static final String password = "root";
    private static final String driver = "com.mysql.jdbc.Driver";


    private static volatile Database instance;

    public static Database getInstance() {
        Database localInstance = instance;
        if (localInstance == null) {
            synchronized (Database.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Database();
                }
            }
        }
        return localInstance;
    }

    public void test() {
        try {
            Class.forName(driver);
            Connection myConn = DriverManager.getConnection(url, user, password);

            Statement statement = myConn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from conditions");
            while (resultSet.next()) {
                System.out.println(
                        resultSet.getString("crossroad_info_1_id") + ", " +
                                resultSet.getString("crossroad_info_2_id"));
            }
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }

    public String getConditionsNames() {
        String result = "";

        try {
            Class.forName(driver);
            Connection myConn = DriverManager.getConnection(url, user, password);

            Statement statement = myConn.createStatement();
            ResultSet resultSet = statement.executeQuery(Constants.select_conditions_names_query);
            while (resultSet.next()) {
                result += resultSet.getString(Constants.conditions_names) + "\n";
            }
        } catch (Exception e) {
            System.out.println("ERROR");
        }

        return result;
    }

    public Conditions getFromDatabase(String id) {
        return null;
    }

    public void putToDatabase(Conditions conditions, Algorithm algorithm) {
        //break conditions into specific objects and save them to tables
        //crossroadInfo: crossroad, north, east, south, west
        //crossroadInfo -> Table conditions.
        //Conditions table comprises from id, name, crossroadInfo1, crossroadInfo2
        //crossroad -> Table crossroads
        //north, east, south, west -> Table directionInfo
    }


    private String createQuery(String... query_part) {
        String full_query = "";
        for (String query : query_part) {
            full_query += query;
        }
        return full_query;
    }
}
