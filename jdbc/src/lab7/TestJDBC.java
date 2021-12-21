package lab7;

import java.sql.*;
// how to set ojdbc8.jar in IntelliJ
// Project Structure > Libraries > New Project Library(+ icon) > Java > select your file and OK to

public class TestJDBC {
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static final String USER_UNIVERSITY = "university";
    public static final String USER_PASSWD = "comp322";
    public static final String TABLE_NAME = "TEST";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        String sql = "";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Success!");
        } catch (ClassNotFoundException e) {
            System.err.println("error: " + e.getMessage());
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(URL, USER_UNIVERSITY, USER_PASSWD);
            System.out.println("Connected.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Cannot get a connection: " + ex.getLocalizedMessage());
            System.err.println("Cannot get a connection: " + ex.getMessage());
        }

    }

}
