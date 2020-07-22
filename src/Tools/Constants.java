package Tools;

public class Constants {

    /////////////////////database queries///////////////////////
	//Creates
	public static final String create_database_query = "create database if not exists stl";
	public static final String create_conditions_table_query = "create table if not exists stl.Conditions (`conditions_id` int not null, `conditions_name` varchar(20) not null, `crossroad_info_1_id` int not null, `crossroad_info_2_id` int not null, `start_result` int not null, `finih_result` int not null, CONSTRAINT `PK_conditions_id` PRIMARY KEY (`conditions_id`))";
	//need to fix
	public static final String create_crossroadInfo_table_query = "create table if not exists stl.CrossroadInfo(`crossrod_info_id` int not null, `north_direction_info` int not null, `east_direction_info` int not null, `south_direction_info` int not null, `west_direction_info` int not null, CONSTRAINT `PK_crossrod_info_id` PRIMARY KEY (`crossrod_info_id`))";
	//need to fix
	public static final String create_directionInfo_table_query = "create table if not exists stl.DirectionInfo (`direction_info_id` int not null auto_increment, `cars_amount` int not null, `average_speed` int not null, `limit_speed` int not null, CONSTRAINT `PK_direction_info_id` PRIMARY KEY (`direction_info_id`))";

	//Selects
    public static final String select_conditions_names_query = "select conditions_name from Conditions";
    public static final String conditions_names = "conditions_name";
    ///////////////////////////////////////////////////////////
    
    //log messages
    public static final String connection_fail = "ERROR: connection fail!";
    public static final String create_database_fail = "ERROR: create local database fail!";

    //window labels
    public static final String traffic_conditions_window_label = "Choose traffic conditions";
    public static final String home_page_window_label = "Welcome to the Smart traffic light!";
    public static final String client_type_window_label = "Choose your occupation";
    public static final String simulation_window_label = "Simulation";
    public static final String about_us_window_label = "About us";
    public static final String database_window_label = "Database";
    public static final String database_connect_window_label = "Connect to Database";
    public static final String random_window_label = "Random";
    public static final String go_to_previous_page_window_label = "Go to previous window";
    public static final String exit_window_label = "Exit";


    //buttons labels
    public static final String yes_button_label = "Yes";
    public static final String no_button_label = "No";
    public static final String reset_button_label = "Reset";
    public static final String run_button_label = "Run";
    public static final String analyst_button_label = "Analyst";
    public static final String observer_button_label = "Observer";
    public static final String save_button_label = "Save";
    public static final String back_button_label = "Back";
    public static final String start_button_label = "Start";
    public static final String database_button_label = "Database";
    public static final String random_button_label = "Random";
    public static final String info_button_label = "Info";
    public static final String lets_start_button_label = "Let's start";
    public static final String about_us_button_label = "About us";
    public static final String close_button_label = "Close";
    public static final String cancel_button = "Cancel";
    public static final String confirm_button_database = "Choose";

    //pages labels
    public static final String about_us_text = "Smart Traffic Light\nVersion 1.0\n" +
            "Created by Netanel Davidov and Maxim Marmer";
    public static final String go_to_previous_page_from_conditions_text = "If you go back all options will reset\n" +
            "Sure you want to go back?";
    public static final String go_to_previous_page_from_simulation_text = "Current simulation will be deleted\n" +
            "Sure you want to go back?";
    public static final String crossroad_1_label = "Crossroad 1";
    public static final String crossroad_2_label = "Crossroad 2";
    public static final String other_features_label = "Other features";
    public static final String route_label = "Route";
    public static final String east_label = "East";
    public static final String west_label = "West";
    public static final String north_label = "North";
    public static final String south_label = "South";
    public static final String cars_count_label = "Cars count";
    public static final String actual_speed_label = "Actual speed";
    public static final String speed_limit_label = "Speed limit";
    public static final String window_label = "Smart Traffic Light";
    
    public static final String url_label = "url: ";
    public static final String user_label = "user: ";
    public static final String password_label = "password: ";
    public static final String connect_button = "Connect";
    public static final String create_database_button = "Create database";

    public static final String choose_file_from_database_label = "Choose conditions file";
    public static final String generate_random_data_label = "Generate random data?";
    public static final String reset_conditions_label = "Reset all values?";
    public static final String exit_text_label = "Sure you want to exit?";



    public static final double POLICE_MAX_SPEED = 70;
    public static final double AMBULANCE_MAX_SPEED = 70;
    public static final double TAXI_MAX_SPEED = 70;
    public static final double USUAL_MAX_SPEED = 70;
    public static final double TRACK_MAX_SPEED = 70;


    public static final double CROSSROAD_PHASE_TIME = 60;


}
