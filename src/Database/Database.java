package Database;

import Objects.Conditions.Conditions;
import SystemSTL.Algorithm;
import Tools.Constants;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
    
    /**
     * 
     * @param url
     * @param user
     * @param password
     * @return
     */
    public boolean connect(String url, String user, String password) {
    	try {
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
     * 
     * @param url
     * @param user
     * @param password
     * @return
     */
    public boolean createLocalDatabase(String url, String user, String password) {
    	try {
    		String new_url = extractLocalhostAndPort(url);
    		connect(new_url, user, password);
//    		String query = Constants.create_database_query;
        	PreparedStatement create_database_query = con.prepareStatement(Constants.create_database_query);
        	create_database_query.execute();
        	PreparedStatement create_conditions_table_query = con.prepareStatement(Constants.create_conditions_table_query);
        	create_conditions_table_query.execute();
    	} catch(Exception e) {
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

    private String extractLocalhostAndPort(String url) {
    	String pattern = "jdbc:mysql://(\\w)*:(\\d)*/?";
    	Pattern r = Pattern.compile(pattern);
    	Matcher m = r.matcher(url);
    	if(m.find( )) {
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
