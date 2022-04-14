package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creates a connection between a database schema and Java.
 * No instance variables, only static fields.
 * Singleton design pattern, there is only one instance of the ConnectionFactory class in the entire application in order to maintain the singularity of the Database Connection
 *
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/warehouse";
    private static final String USER = "root";
    private static final String PASS = "rootpassword";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Private constructor with tries to initiate the database connection.
     * Its access modifier is private in order to maintain the singleton design pattern for the ConnectionFactory class
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for creating the DB connection
     * @return if successful, the obtained connection to the DB, null otherwise
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
            System.out.println("db connected!");
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Static method which calls createConnection() method on the "singleton" instance of the class.
     * @return an object of type Connection, representing the connection established
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Static method to close a DB connection
     * @param connection object of type Connection
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }

    /**
     * Static method to close a Connection Statement
     * @param statement object of type Statement
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }

    /**
     * Static method to close a ResultSet of a Connection
     * @param resultSet object of type ResultSet
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }
}
