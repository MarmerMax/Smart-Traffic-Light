package Database;

import Objects.Conditions.Conditions;
import Objects.CrossroadInfo.CrossroadInfo;
import Objects.CrossroadInfo.DirectionInfo.DirectionInfo;
import SystemSTL.Algorithm;
import Tools.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            con = DriverManager.getConnection(url, user, password);
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
     * This function creates the entire database by executing SQL commands.
     * 
     * @param url
     * @param user
     * @param password
     * @return true - if all executes success, otherwise return false.
     */
    public boolean createLocalDatabase(String url, String user, String password) {
    	try {
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
        	
    	} catch(SQLException e) {
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
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(Constants.select_conditions_names_query);
            while (resultSet.next()) {
                result += resultSet.getString(Constants.conditions_names) + "\n";
            }
        } catch (Exception e) {
            System.err.println("ERROR");
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

    private String extractHostAndPort(String url) {
    	String pattern = "jdbc:mysql://(\\w)*:(\\d)*/?";
    	Pattern r = Pattern.compile(pattern);
    	Matcher m = r.matcher(url);
    	if(m.find( )) {
    		return m.group(0);
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
			pstmt.executeUpdate();
			
			//Extract conditions_id from ResultSet
			int current_conditions_id = getId(pstmt, 1);
			
			//Save crossroadsInfo
	    	CrossroadInfo ci1 = conditions.getCrossroadInfo1();
	    	CrossroadInfo ci2 = conditions.getCrossroadInfo2();
	    	saveCrossroadInfo(ci1, current_conditions_id);
	    	saveCrossroadInfo(ci2, current_conditions_id);
	    	
	    	
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
    		
    		pstmt = con.prepareStatement(Constants.insert_crossroadsInfo_statment, Statement.RETURN_GENERATED_KEYS);
    		pstmt.setInt(1, current_conditions_id);
    		pstmt.setInt(2, id1);
    		pstmt.setInt(3, id2);
    		pstmt.setInt(4, id3);
    		pstmt.setInt(5, id4);
			pstmt.executeUpdate();
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
    
    private int getId(PreparedStatement pstmt, int index) {
    	//Extract id from ResultSet
		try {
			ResultSet rs = pstmt.getGeneratedKeys();
			int id = 0;
			while(rs.next()){ id = rs.getInt(index); }
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
    }
    
    private String createQuery(String... query_part) {
        String full_query = "";
        for (String query : query_part) {
            full_query += query;
        }
        return full_query;
    }
}
