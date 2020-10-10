package Database;

import Objects.Conditions.Conditions;
import Objects.Crossroad.Crossroad;
import Objects.CrossroadInfo.CrossroadInfo;
import Objects.CrossroadInfo.DirectionInfo.DirectionInfo;
import Objects.TrafficLight.TrafficLight;
import SystemSTL.Algorithm.Algorithm;
import Tools.ConsoleColors;
import Tools.Constants;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("Duplicates")
public class Database {

    private static String url = "jdbc:mysql://localhost:3306/stl";
    private static String user = "root";
    private static String password = "root";
    private static final String driver = "com.mysql.jdbc.Driver";

    private Connection con;

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

    public Connection getConnection() {
        return con;
    }

    /**
     * This function performs a connection to the database.
     *
     * @param url
     * @param user
     * @param password
     * @return
     */
    public boolean connect(String url, String user, String password) {
        try {
            con = null;
            Class.forName(driver);
            con = DriverManager.getConnection("jdbc:" + url, user, password);
            this.url = url;
            this.user = user;
            this.password = password;
        } catch (Exception e) {
            System.err.println(Constants.connection_fail);
            return false;
        }
        System.out.println("Connection success!");
        return true;
    }

    /**
     * This method check if database exist.
     * Return true if exist and false otherwise.
     * 
     * @param url
     * @param user
     * @param password
     * @return - boolean 
     */
    public boolean checkIfDatabaseExist(String url, String user, String password) {
    	try {
    		String new_url = extractHostAndPort(url);
            connect(new_url, user, password);
            
            System.out.println("Checking if database is exist: ");

            PreparedStatement check_if_database_exist_query = con.prepareStatement(Constants.select_0_condition_query);
            check_if_database_exist_query.execute();
            System.out.println("Database exist!");
            return true;

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
    }
    
    /**
     * This function creates the entire database by executing SQL commands.
     *
     * @param url
     * @param user
     * @param password
     * @return true - if all executes success, otherwise return false.
     */
    public boolean createLocalDatabase(String url, String user, String password) {
        try {
        	boolean is_exist_database = checkIfDatabaseExist(url, user, password);

        	// If database is exist then drop it down 
        	if(is_exist_database) {
        		System.out.println("Droping database: ");
                PreparedStatement drop_database_query= con.prepareStatement(Constants.drop_database_query);
                drop_database_query.execute();
                System.out.println("droping database success!");
        	}
        	
            String new_url = extractHostAndPort(url);
            connect(new_url, user, password);

            System.out.println("Creating database and tables: ");

            PreparedStatement create_database_query = con.prepareStatement(Constants.create_database_query);
            create_database_query.execute();
            System.out.println("creating database success!");

            PreparedStatement create_conditions_table_query = con.prepareStatement(Constants.create_conditions_table_query);
            create_conditions_table_query.execute();
            System.out.println("creating conditions table success!");

            PreparedStatement create_directionsInfo_table_query = con.prepareStatement(Constants.create_directionsInfo_table_query);
            create_directionsInfo_table_query.execute();
            System.out.println("creating directionsInfo table success!");

            PreparedStatement create_traffic_lights_table_query = con.prepareStatement(Constants.create_traffic_lights_table_query);
            create_traffic_lights_table_query.execute();
            System.out.println("creating trafficLights table success!");

            PreparedStatement create_cars_table_query = con.prepareStatement(Constants.create_cars_table_query);
            create_cars_table_query.execute();
            System.out.println("creating cars table success!");

            PreparedStatement create_crossroadsInfo_table_query = con.prepareStatement(Constants.create_crossroadsInfo_table_query);
            create_crossroadsInfo_table_query.execute();
            System.out.println("creating crossroadsInfo table success!");

            PreparedStatement create_crossroads_table_query = con.prepareStatement(Constants.create_crossroads_table_query);
            create_crossroads_table_query.execute();
            System.out.println("creating crossroads table success!");

        } catch (SQLException e) {
            System.err.println(Constants.create_database_fail);
            return false;
        }
        return true;
    }

    public void test() {
        try {
//            Class.forName(driver);
//            Connection myConn = DriverManager.getConnection(url, user, password);

            Statement statement = con.createStatement();
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
            PreparedStatement pstmt = con.prepareStatement(Constants.select_conditions_dates_query);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                result += resultSet.getString(Constants.conditions_dates) + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * return DatabaseConditions object by reriveting data from database.
     *
     * @param date - date of saving file
     * @return DatabaseConditions
     */
    public DatabaseConditions getDatabaseConditions(String date) {
        try {
            PreparedStatement pstmt = con.prepareStatement(Constants.select_condition_by_date_query);
            pstmt.setString(1, date);
            ResultSet rs = pstmt.executeQuery();
            int condition_id = 0;
            double initial_time = 0;
            double better_time = 0;
            double simulation_time = 0;
            double initial_awt = 0;
            double better_awt = 0;

            String better_distribtuion = "";
            while (rs.next()) {
                condition_id = rs.getInt(1);
                initial_time = rs.getInt(4);
                better_time = rs.getInt(5);
                better_distribtuion = rs.getString(6);
                initial_awt = rs.getDouble(7);
                better_awt = rs.getDouble(8);
            }

            pstmt = con.prepareStatement(Constants.select_crossroadsInfo_ids_query);
            pstmt.setInt(1, condition_id);
            rs = pstmt.executeQuery();
            int[] crossroadsInfo_ids = new int[2];
            int index = 0;
            while (rs.next()) {
                crossroadsInfo_ids[index] = rs.getInt(1);
                index++;
            }

            pstmt = con.prepareStatement(Constants.select_crossroadsInfo_query);
            pstmt.setInt(1, crossroadsInfo_ids[0]);
            rs = pstmt.executeQuery();
            // crossroadinfo 1 - direction info ids
            int[] directions_info_ids1 = new int[4];
            int[] cars_first_crossroad = new int[4];
            int[] speed_limit_first_crossroad = new int[4];
            int[] actual_speed_first_crossroad = new int[4];
            index = 0;
            // the first row accepted from query
            rs.next();
            // while there are more direction info id column then run (max number of direction is 4)
            while (index < 4) {
                // column = 1 -> north_direction_info_id
                // column = 2 -> east_direction_info_id
                // column = 3 -> south_direction_info_id
                // column = 4 -> west_direction_info_id
                int column = index + 1;
                directions_info_ids1[index] = rs.getInt(column);
                pstmt = con.prepareStatement(Constants.select_directionInfo_query);
                pstmt.setInt(1, rs.getInt(column));
                ResultSet rs1 = pstmt.executeQuery();
                while (rs1.next()) {
                    cars_first_crossroad[index] = rs1.getInt(1);
                    speed_limit_first_crossroad[index] = rs1.getInt(3);
                    actual_speed_first_crossroad[index] = rs1.getInt(2);
                }
                index++;
            }

            pstmt = con.prepareStatement(Constants.select_crossroadsInfo_query);
            pstmt.setInt(1, crossroadsInfo_ids[1]);
            rs = pstmt.executeQuery();
            // crossroadinfo 2 - direction info ids
            int[] directions_info_ids2 = new int[4];
            int[] cars_second_crossroad = new int[4];
            int[] speed_limit_second_crossroad = new int[4];
            int[] actual_speed_second_crossroad = new int[4];
            index = 0;
            // the first row accepted from query
            rs.next();
            // while there are more direction info id column then run (max number of direction is 4)
            while (index < 4) {
                // column = 1 -> north_direction_info_id
                // column = 2 -> east_direction_info_id
                // column = 3 -> south_direction_info_id
                // column = 4 -> west_direction_info_id
                int column = index + 1;
                directions_info_ids2[index] = rs.getInt(column);
                pstmt = con.prepareStatement(Constants.select_directionInfo_query);
                pstmt.setInt(1, rs.getInt(column));
                ResultSet rs1 = pstmt.executeQuery();
                while (rs1.next()) {
                    cars_second_crossroad[index] = rs1.getInt(1);
                    speed_limit_second_crossroad[index] = rs1.getInt(3);
                    actual_speed_second_crossroad[index] = rs1.getInt(2);
                }
                index++;
            }

            DatabaseConditions dc = new DatabaseConditions(
                    cars_first_crossroad,
                    cars_second_crossroad,
                    speed_limit_first_crossroad,
                    speed_limit_second_crossroad,
                    actual_speed_first_crossroad,
                    actual_speed_second_crossroad,
                    initial_time,
                    better_time,
                    simulation_time,
                    initial_awt,
                    better_awt,
                    better_distribtuion);

//            System.out.println(dc.toString());

            return dc;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }




    /**
     * This function should take the crossroads data and store it in the database.
     *
     * @param conditions
     */
    public void save(Conditions conditions) {
        try {
            //Save conditions
            PreparedStatement pstmt = con.prepareStatement(Constants.insert_conditions_statment, Statement.RETURN_GENERATED_KEYS);
            pstmt.setNString(1, "name"); //need to replace to name of conditions
            pstmt.setDouble(2, conditions.getInitialDuration());
            pstmt.setDouble(3, conditions.getAlgorithmDuration());
            pstmt.setNString(4, conditions.getBetterDistributionString());
            pstmt.setDouble(5, conditions.getInitialAWT());
            pstmt.setDouble(6, conditions.getAlgorithmAWT());
            pstmt.setDouble(7, conditions.getPhaseTime());
            pstmt.executeUpdate();

            //Extract conditions_id from ResultSet
            int current_conditions_id = getId(pstmt, 1);

            //Save crossroadsInfo
            CrossroadInfo ci1 = conditions.getFirstCrossroadInfo();
            CrossroadInfo ci2 = conditions.getSecondCrossroadInfo();
            saveCrossroadInfo(ci1, current_conditions_id);
            saveCrossroadInfo(ci2, current_conditions_id);


//            saveResult(
//                    conditions.getInitialDuration(),
//                    conditions.getAlgorithmDuration(),
//                    conditions.getSimulationDuration(),
//                    conditions.getBetterDistributionString());


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * This function should take the algorithm results data and store them in the database.
     *
     * @param results
     */
    public static void save(Algorithm results) {

    }


    private PreparedStatement saveCrossroadInfo(CrossroadInfo ci, int current_conditions_id) {
        try {
            PreparedStatement pstmt;

            //Save directionsInfo(north)
            int id1 = 0;
            DirectionInfo north_di = ci.getNorth();
            pstmt = saveDirectionInfo(north_di, "north");
            id1 = getId(pstmt, 1);

            //Save directionsInfo(east)
            int id2 = 0;
            DirectionInfo east_di = ci.getEast();
            pstmt = saveDirectionInfo(east_di, "east");
            id2 = getId(pstmt, 1);

            //Save directionsInfo(south)
            int id3 = 0;
            DirectionInfo south_di = ci.getSouth();
            pstmt = saveDirectionInfo(south_di, "south");
            id3 = getId(pstmt, 1);

            //Save directionsInfo(west)
            int id4 = 0;
            DirectionInfo west_di = ci.getWest();
            pstmt = saveDirectionInfo(west_di, "west");
            id4 = getId(pstmt, 1);

            //Save cars of directionsInfo(north)?
            //Save cars of directionsInfo(east)?
            //Save cars of directionsInfo(south)?
            //Save cars of directionsInfo(west)?


            pstmt = con.prepareStatement(Constants.insert_crossroadsInfo_statment, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, current_conditions_id);
            pstmt.setInt(2, id1);
            pstmt.setInt(3, id2);
            pstmt.setInt(4, id3);
            pstmt.setInt(5, id4);
            pstmt.executeUpdate();

            //Save crossroad
            int current_ci_id = getId(pstmt, 1);
            Crossroad cr = ci.getCrossroad();
            saveCrossroad(cr, current_ci_id);

            return pstmt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private PreparedStatement saveDirectionInfo(DirectionInfo di, String type) {
        try {
            PreparedStatement pstmt;
            pstmt = con.prepareStatement(Constants.insert_directionsInfo_statment, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, di.getCarsCount());
            pstmt.setDouble(2, di.getActualSpeed());
            pstmt.setDouble(3, di.getSpeedLimit());
            pstmt.setString(4, type);
            pstmt.executeUpdate();
            return pstmt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private PreparedStatement saveCrossroad(Crossroad cr, int ci_id) {
        try {
            PreparedStatement pstmt;

            //Save north traffic light
            int id1 = 0;
            TrafficLight tl1 = cr.getNorthTrafficLight();
            pstmt = saveTrafficLight(tl1);
            id1 = getId(pstmt, 1);

            //Save east traffic light
            int id2 = 0;
            TrafficLight tl2 = cr.getNorthTrafficLight();
            pstmt = saveTrafficLight(tl2);
            id2 = getId(pstmt, 1);

            //Save south traffic light
            int id3 = 0;
            TrafficLight tl3 = cr.getNorthTrafficLight();
            pstmt = saveTrafficLight(tl3);
            id3 = getId(pstmt, 1);

            //Save west traffic light
            int id4 = 0;
            TrafficLight tl4 = cr.getNorthTrafficLight();
            pstmt = saveTrafficLight(tl4);
            id4 = getId(pstmt, 1);

            pstmt = con.prepareStatement(Constants.insert_crossroads_statment, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, ci_id);
            pstmt.setInt(2, id1);
            pstmt.setInt(3, id2);
            pstmt.setInt(4, id3);
            pstmt.setInt(5, id4);
            pstmt.setInt(6, cr.getActualState());
            pstmt.executeUpdate();
            return pstmt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private PreparedStatement saveTrafficLight(TrafficLight tl) {
        try {
            PreparedStatement pstmt;
            pstmt = con.prepareStatement(Constants.insert_traffic_light, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, tl.getActualStateNumber());
            pstmt.executeUpdate();
            return pstmt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    private int getId(PreparedStatement pstmt, int index) {
        //Extract id from ResultSet
        try {
            ResultSet rs = pstmt.getGeneratedKeys();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt(index);
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String extractHostAndPort(String url) {
    	String pattern = "mysql://(\\w)*:(\\d)*/?";
    	Pattern r = Pattern.compile(pattern);
    	Matcher m = r.matcher(url);
    	if (m.find()) {
    		return m.group(0);
    	}
    	return null;
    }
    
    private String createQuery(String... query_part) {
        String full_query = "";
        for (String query : query_part) {
            full_query += query;
        }
        return full_query;
    }
}
