package Tools;

public class Constants {

    /////////////////////database queries///////////////////////
    //Creates
    public static final String create_database_query = "create database if not exists stl";
    public static final String create_conditions_table_query = "create table if not exists stl.Conditions (\n" +
            "	`condition_id` int not null auto_increment,\n" +
            "    `condition_name` varchar(20) not null,\n" +
            "    `Date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
            "    PRIMARY KEY (`condition_id`)\n" +
            ")";
    public static final String create_directionsInfo_table_query = "create table if not exists stl.DirectionsInfo (\n" +
            "	`direction_info_id` int not null auto_increment, \n" +
            "	`cars_amount` int not null,\n" +
            "	`average_speed` int not null,\n" +
            "	`limit_speed` int not null,\n" +
            "	`type` varchar(20) not null,\n" +
            "	PRIMARY KEY (`direction_info_id`)\n" +
            ")";
    public static final String create_traffic_lights_table_query = "create table if not exists stl.TrafficLights (\n" +
            "    `traffic_light_id` int not null auto_increment,\n" +
            "    `actual_state` int not null,\n" +
            "    primary key(`traffic_light_id`)\n" +
            ")";
    public static final String create_cars_table_query = "create table if not exists stl.Cars (\n" +
            "    `car_id` int not null auto_increment,\n" +
            "    `direction_info_id` int not null,\n" +
            "    `car_type` varchar(25),\n" +
            "    `length` int not null,\n" +
            "    `speed` int not null,\n" +
            "    primary key(`car_id`),\n" +
            "    foreign key(`direction_info_id`) references DirectionsInfo(`direction_info_id`)\n" +
            ")";
    public static final String create_crossroadsInfo_table_query = "create table if not exists stl.CrossroadsInfo(\n" +
            "	`crossroad_info_id` int not null auto_increment,\n" +
            "    `condition_id` int not null,\n" +
            "    `north_direction_info_id` int,\n" +
            "	`east_direction_info_id` int,\n" +
            "    `south_direction_info_id` int,\n" +
            "    `west_direction_info_id` int,\n" +
            "	PRIMARY KEY (`crossroad_info_id`),\n" +
            "    foreign key(`condition_id`) references Conditions(`condition_id`),\n" +
            "    foreign key(`north_direction_info_id`) references DirectionsInfo(`direction_info_id`),\n" +
            "    foreign key(`east_direction_info_id`) references DirectionsInfo(`direction_info_id`),\n" +
            "    foreign key(`south_direction_info_id`) references DirectionsInfo(`direction_info_id`),\n" +
            "    foreign key(`west_direction_info_id`) references DirectionsInfo(`direction_info_id`)\n" +
            ")";
    public static final String create_crossroads_table_query = "create table if not exists stl.Crossroads(\n" +
            "	`crossroad_id` int not null auto_increment,\n" +
            "    `crossroad_info_id` int not null,\n" +
            "    `north_traffic_light_id` int,\n" +
            "    `east_traffic_light_id` int,\n" +
            "    `south_traffic_light_id` int,\n" +
            "    `west_traffic_light_id` int,\n" +
            "    `actual_state` int not null,\n" +
            "    primary key(`crossroad_id`),\n" +
            "    foreign key(`crossroad_info_id`) references CrossroadsInfo(`crossroad_info_id`),\n" +
            "    foreign key(`north_traffic_light_id`) references TrafficLights(`traffic_light_id`),\n" +
            "    foreign key(`east_traffic_light_id`) references TrafficLights(`traffic_light_id`),\n" +
            "    foreign key(`south_traffic_light_id`) references TrafficLights(`traffic_light_id`),\n" +
            "    foreign key(`west_traffic_light_id`) references TrafficLights(`traffic_light_id`)\n" +
            ")";

    //Saves
    public static final String insert_conditions_statment = "insert into stl.Conditions(condition_name) values(?)";
    public static final String insert_directionsInfo_statment = "insert into "
            + "stl.DirectionsInfo(cars_amount, average_speed, limit_speed, type)"
            + " values(?,?,?,?)";
    public static final String insert_crossroadsInfo_statment = "insert into "
            + "stl.CrossroadsInfo(condition_id, north_direction_info_id, east_direction_info_id, south_direction_info_id, west_direction_info_id)"
            + " values(?,?,?,?,?)";
    public static final String insert_crossroads_statment = "insert into"
    		+ " stl.crossroads(crossroad_info_id, north_traffic_light_id, east_traffic_light_id, south_traffic_light_id, west_traffic_light_id, actual_state)"
    		+ " values(?,?,?,?,?,?)";
    public static final String insert_traffic_light = "insert into stl.trafficlights(actual_state) values(?)";

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


    //sizes
    public static final double TRAFFIC_LIGHT_HEIGHT = 40;
    public static final double TRAFFIC_LIGHT_WIDTH = 15;

    public static final double CAR_HEIGHT = 60;
    public static final double CAR_WIDTH = 40;


    //times
    public static final int TRAFFIC_LIGHT_CHANGING_TIME = 2;
    public static final int TRAFFIC_LIGHT_MIN_DISTRIBUTION = 5;
    public static final double TRAFFIC_LIGHT_PHASE_TIME = 20;


    // directions
    public static final int NORTH_DIRECTION = 0;
    public static final int EAST_DIRECTION = 1;
    public static final int SOUTH_DIRECTION = 2;
    public static final int WEST_DIRECTION = 3;

    // names
    public static final String DIRECTION_NAME_EAST_WEST = "east-west";
    public static final String DIRECTION_NAME_NORTH_SOUTH = "north-south";
    public static final String DIRECTION_NAME_EAST = "east";
    public static final String DIRECTION_NAME_WEST = "west";
    public static final String DIRECTION_NAME_NORTH = "north";
    public static final String DIRECTION_NAME_SOUTH = "south";
    public static final String CROSSROAD_NAME_FIRST = "first";
    public static final String CROSSROAD_NAME_SECOND = "second";

    //Units
    public static final double PIXEL_TO_METER = 0.12;

    public static final double METER_TO_PIXEL = 15;
    public static final double SAFETY_DISTANCE = 2;
    public static final double SAFETY_DISTANCE_TO_START = 3;

    public static final double SAFETY_DISTANCE_TO_STOP = 1;
    public static final double START_DISTANCE_BETWEEN_CARS = 2;

    public static final String PHASE_DELIMITER = "->";
    public static final String TIMES_DELIMITER = ":";


    //Start values
    public static final int CARS_COUNT_SHORT_ROAD = 3;
    public static final int CARS_COUNT_LONG_ROAD = 50;
    public static final int CARS_COUNT_LONG_ROAD_MAX = 100;
    public static final int CARS_COUNT_SHORT_ROAD_MAX = 4;
    public static final int CARS_COUNT_MIN = 1;

    public static final int SPEED_LIMIT_DEFAULT = 50;
    public static final int SPEED_LIMIT_MAX = 110;
    public static final int SPEED_LIMIT_MIN = 50;

    public static final int ACTUAL_LIMIT_DEFAULT = 50;
    public static final int ACTUAL_LIMIT_MAX = 110;
    public static final int ACTUAL_LIMIT_MIN = 50;


    //info text
    public static final String INFO_LABEL = "Main information";
    public static final String INFO_DIRECTIONS_LABEL = "Directions information";
    public static final String INFO_TRAFFIC_LIGHTS_LABEL = "Traffic lights information";
    public static final String INFO_CARS_COUNT_LABEL = "Cars count information";
    public static final String INFO_SPEED_LIMIT_LABEL = "Speed limit information";
    public static final String INFO_ACTUAL_SPEED_LABEL = "Actual speed information";

    public static final String INFO_TEXT = "Before starting the application, you must select the conditions that the algorithm will try to solve." +
            "\n\nConditions include cars and speed for two intersections." +
            "\n\nThe user can set this data manually, load it from the database by clicking on the 'Database' button, or select it by random clicking 'Random' button." +
            "\n\nTo reset all selected settings, press the 'Reset' button.";
    public static final String INFO_DIRECTIONS_TEXT = "Each intersection has four roads." +
            "\n\nFor a better understanding of the movement of the vehicle, the picture on the left shows the order with the names of the sides and it is in this order that the lanes and directions of movement will be called.";
    public static final String INFO_TRAFFIC_LIGHTS_TEXT = "Each lane is regulated by a traffic light. In the figure, the arrow from the traffic light indicates the lane that will depend on the particular traffic light." +
            "\n\nEach traffic light has 4 states: green, yellow, red, red-yellow." +
            "\n\nGreen - traffic allowed." +
            "\nYellow - stop moving." +
            "\nRed - cars are waiting for green." +
            "\nRed-yellow - mice are ready to go." +
            "\n\nIt takes 3 seconds to switch the state of traffic light colors.";
    public static final String INFO_CARS_COUNT_TEXT = "The number of cars indicates how many cars must pass the intersection from the selected side.";
    public static final String INFO_SPEED_LIMIT_TEXT = "Speed limit indicates the speed limit for the selected road.";
    public static final String INFO_ACTUAL_SPEED_TEXT = "The actual speed indicates the speed at which the movement is taking place in real time.";
}
