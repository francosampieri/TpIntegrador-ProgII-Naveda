package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/food_store";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    //CONSTRUCTORES
    
    private DatabaseConnection() {}
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD); 
    }
}
