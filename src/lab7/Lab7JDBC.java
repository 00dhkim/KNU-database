/**************************************************
 * Skeleton code presented by KNU DEAL Lab, prof. yksuk
 * @author: dohyun kim, 2019112920
 **************************************************/
package lab7; // package name

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*; // import JDBC package
import java.text.*;

public class Lab7JDBC {
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static final String USER_UNIVERSITY = "company";
    public static final String USER_PASSWD = "company";

    public static void main(String[] args) {
        Connection conn = null; // Connection object
        Statement stmt = null;    // Statement object

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("[+] Driver Load Success!");
        } catch (ClassNotFoundException e) {
            System.err.println("[!] error: " + e.getMessage());
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(URL, USER_UNIVERSITY, USER_PASSWD);
            System.out.println("[+] Connected.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("[!] Cannot get a connection: " + ex.getLocalizedMessage());
            System.err.println("[!] Cannot get a connection: " + ex.getMessage());
        }

        try {
            stmt = conn.createStatement();
            System.out.println("[+] stmt created.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        doTask1(conn, stmt);

        System.out.println();

//        doTask2(conn, stmt);

        // Release database resources.
        try {
            // Close the Statement object.
            if (stmt != null) {
                stmt.close();
                System.out.println("[+] stmt.close() done.");
            }
            // Close the Connection object.
            if (conn != null) {
                conn.close();
                System.out.println("[+] conn.close() done.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void drop_tables(Connection conn, Statement stmt) {
        try {
            stmt.addBatch("DROP TABLE EMPLOYEE CASCADE CONSTRAINTS");
            stmt.addBatch("DROP TABLE DEPARTMENT CASCADE CONSTRAINTS");
            stmt.addBatch("DROP TABLE DEPT_LOCATIONS CASCADE CONSTRAINTS");
            stmt.addBatch("DROP TABLE PROJECT CASCADE CONSTRAINTS");
            stmt.addBatch("DROP TABLE WORKS_ON CASCADE CONSTRAINTS");
            stmt.addBatch("DROP TABLE DEPENDENT CASCADE CONSTRAINTS");

            int[] count = stmt.executeBatch();
            System.out.println("[+] " + count.length + " tables deleted.");

        } catch (SQLException ex) {
            System.err.println("[!] sql error in DROP: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void create_tables(Connection conn, Statement stmt) {
        String sql = "";

        try {

            sql = "create table employee (" +
                    "    fname varchar(15) not null," +
                    "    minit char," +
                    "    lname varchar(15) not null," +
                    "    ssn char(9) not null," +
                    "    bdate date," +
                    "    address varchar(30)," +
                    "    sex char," +
                    "    salary decimal(10,2)," +
                    "    super_ssn char(9)," +
                    "    dno int not null," +
                    "    primary key (ssn)" +
                    ")";
            stmt.addBatch(sql);

            sql = "create table department (" +
                    "    dname varchar(15) not null," +
                    "    dnumber int not null," +
                    "    mgr_ssn char(9) not null," +
                    "    mgr_start_date date," +
                    "    primary key (dnumber)," +
                    "    unique (dname)," +
                    "    foreign key (mgr_ssn) references employee(ssn)" +
                    ")";
            stmt.addBatch(sql);

            sql = "create table dept_locations (" +
                    "    dnumber int not null," +
                    "    dlocation varchar(15) not null," +
                    "    primary key (Dnumber, Dlocation)," +
                    "    foreign key (dnumber) references department(dnumber)" +
                    ")";
            stmt.addBatch(sql);

            sql = "create table project (" +
                    "    pname varchar(15) not null," +
                    "    pnumber int not null," +
                    "    plocation varchar(15)," +
                    "    dnum int not null," +
                    "    primary key (Pnumber)," +
                    "    unique (pname)," +
                    "    foreign key (Dnum) references department(dnumber)" +
                    ")";
            stmt.addBatch(sql);

            sql = "create table works_on (" +
                    "    essn char(9) not null," +
                    "    pno int not null," +
                    "    hours decimal(3,1)," +
                    "    primary key (essn, pno)," +
                    "    foreign key (essn) references employee(ssn)," +
                    "    foreign key (pno) references project (pnumber)" +
                    ")";
            stmt.addBatch(sql);

            sql = "create table dependent (" +
                    "    essn char(9) not null," +
                    "    dependent_name varchar(15) not null," +
                    "    sex char," +
                    "    bdate date," +
                    "    relationship varchar(8)," +
                    "    primary key (essn, dependent_name)," +
                    "    foreign key (essn) references employee(ssn)" +
                    ")";
            stmt.addBatch(sql);

            int[] count = stmt.executeBatch();
            System.out.println("[+] " + count.length + " tables created.");

        } catch (SQLException ex) {
            System.err.println("[!] sql error in CREATE: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static boolean isNumeric(String str) {
        ParsePosition pos = new ParsePosition(0);
        NumberFormat.getInstance().parse(str, pos);
        return str.length() == pos.getIndex();
    }


    public static boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static void insert_tuples(Connection conn, Statement stmt) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/lab7/company.txt"));
        String line = "";
        try {
            while (true) {
                line = br.readLine();
                if (line == null) break;
                // identify table
                String table_name = line.substring(1);

                line = br.readLine();
                // get some data

                String sql = "INSERT INTO " + table_name + " VALUES (";
                for (String s : line.split("#")) {
                    if (isNumeric(s) || s.toLowerCase() == "null") {
                        sql = sql + s + ",";
                    } else if (isDateValid(s)) {
                        sql = sql + "TO_DATE('" + s + "','yyyy-mm-dd'),";
                    } else {
                        sql = sql + "'" + s + "',";
                    }
                }
                sql = sql.substring(0, sql.length() - 1) + ")";
                System.out.println(sql);

                stmt.addBatch(sql);
            }
            int[] count = stmt.executeBatch();
            System.out.println("[+] " + count.length + " tuples inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        br.close();
    }

    // recreate the tables and insert records
    public static void doTask1(Connection conn, Statement stmt) {
        /**/
        drop_tables(conn, stmt);
        create_tables(conn, stmt);

        try {
            insert_tuples(conn, stmt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void doTask2(Connection conn, Statement stmt) {
        ResultSet rs = null;
        try {
            // Q1: Complete your query.
            String sql = "";
            rs = stmt.executeQuery(sql);
            System.out.println("<< query 1 result >>");
            while (rs.next()) {
                // Fill out your code
            }
            rs.close();

            System.out.println();

            // Q2: Complete your query.
            sql = "";
            System.out.println("<< query 2 result >>");
            while (rs.next()) {
                // Fill out your code
            }
            rs.close();

            System.out.println();

            // Q3: Complete your query.
            sql = "";
            rs = stmt.executeQuery(sql);
            System.out.println("<< query 3 result >>");
            while (rs.next()) {
                // Fill out your code
            }

            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
