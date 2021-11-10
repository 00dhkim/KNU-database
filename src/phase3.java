/*
 * phase 3
 * dohyun kim
 */

import java.sql.*;
import java.util.Scanner;

public class phase3 {
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static final String USER_ID = "airline";
    public static final String USER_PW = "airline";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("[+] Driver Load Success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(URL, USER_ID, USER_PW);
            System.out.println("[+] Connected.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            assert conn != null;
            stmt = conn.createStatement();
            System.out.println("[+] stmt created");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert stmt != null;

        Scanner sc = new Scanner(System.in);
        String ticketNumber, passengerFirstName, passengerLastname,departureDate;
        String passportNumber, passengerPhoneNumber;
        String startDate, endDate;
        String currentTime = "2020-01-12 18:00:00"; // current time is hard coded
        // run methods

        System.out.println("1) get reserved ticket by ticket number");
        System.out.println("2) get reserved ticket by passport number");
        System.out.println("3) get flight status");
        System.out.print("select options: ");

        int options = sc.nextInt();

        if(options == 1) {
            System.out.println("insert ticket number");
            ticketNumber = sc.next();
            System.out.println("insert passenger first name");
            passengerFirstName = sc.next();
            System.out.println("insert passenger last name");
            passengerLastname = sc.next();
            System.out.println("insert departure date");
            departureDate = sc.next();

            getReservedTicketsByTicketNumber(stmt, ticketNumber, passengerFirstName, passengerLastname, departureDate);
//            getReservedTicketsByTicketNumber(stmt, "NIMEZE77824B339", "Michael", "Jensen", "2020-02-11");
        }
        else if (options == 2) {
            System.out.println("insert passport number");
            passportNumber = sc.next();
            System.out.println("insert passenger first name");
            passengerFirstName = sc.next();
            System.out.println("insert passenger last name");
            passengerLastname = sc.next();
            System.out.println("insert passenger phone number");
            passengerPhoneNumber = sc.next();

            getReservedTicketsByPassportNumber(stmt, passportNumber, passengerFirstName, passengerLastname, passengerPhoneNumber);
//            getReservedTicketsByPassportNumber(stmt, "9479ONCO71778", "Michael", "Jensen", "010-7037-2155");
        }
        else if (options == 3) {
            System.out.println("insert start date");
            startDate = sc.next();
            System.out.println("insert end date");
            endDate = sc.next();

            getFlightStatus(stmt, startDate, endDate, currentTime);
//            getFlightStatus(stmt, "2020-01-03", "2020-01-26", currentTime);
        }


        try {
            stmt.close();
            System.out.println("[+] stmt.close() done.");

            conn.close();
            System.out.println("[+] conn.close() done.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 항공권 예약 조회, 정보를 토대로 예약된 티켓이 있는지 조회
     *
     * @param ticketNumber       티켓번호
     * @param passengerFirstName 이름
     * @param passengerLastName  성
     * @param departureDate      출발 날짜, 2020-02-11
     */
    public static void getReservedTicketsByTicketNumber(Statement stmt, String ticketNumber, String passengerFirstName, String passengerLastName, String departureDate) {
        String sql = "select ticket_number, price, tfnum flight_number, tsid seat_id, departure_time, arrival_time, departure_airport, arrival_airport " +
                "from (ticket t join flight f on t.tfnum = f.flight_number) join customer c on t.tcpassport = c.cpassport_number " +
                "where t.ticket_number = '" + ticketNumber + "' " +
                "and c.cfirst_name = '" + passengerFirstName + "' " +
                "and c.clast_name = '" + passengerLastName + "' " +
                "and trunc(departure_time) = TO_DATE('" + departureDate + "', 'YYYY-MM-DD')";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("ticket_number price flight_number seat_id departure_time arrival_time departure_airport arrival_airport");
            System.out.println("---------------------");
            while (rs.next()) {
                String ticket_number = rs.getString(1);
                int price = rs.getInt(2);
                String flight_number = rs.getString(3);
                String seat_id = rs.getString(4);
                String departure_time = rs.getString(5);
                String arrival_time = rs.getString(6);
                String departure_airport = rs.getString(7);
                String arrival_airport = rs.getString(8);
                System.out.printf("%s %d %s %s %s %s %s %s%n", ticket_number, price, flight_number, seat_id, departure_time, arrival_time, departure_airport, arrival_airport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 항공권 예약 조회, 정보를 토대로 예약된 티켓이 있는지 조회
     *
     * @param passportNumber       여권번호
     * @param passengerFirstName   이름
     * @param passengerLastName    성
     * @param passengerPhoneNumber 전화번호
     */
    public static void getReservedTicketsByPassportNumber(Statement stmt, String passportNumber, String passengerFirstName, String passengerLastName, String passengerPhoneNumber) {
        String sql = "select ticket_number, price, tfnum flight_number, tsid seat_id, departure_time, arrival_time, departure_airport, arrival_airport " +
                "from (ticket t join flight f on t.tfnum = f.flight_number) join customer c on t.tcpassport = c.cpassport_number " +
                "where c.cpassport_number = '" + passportNumber + "' " +
                "and c.cfirst_name = '" + passengerFirstName + "' " +
                "and c.clast_name = '" + passengerLastName + "' " +
                "and c.cphone_number = '" + passengerPhoneNumber + "' " +
                "order by f.departure_time";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("ticket_number price flight_number seat_id departure_time arrival_time departure_airport arrival_airport");
            System.out.println("---------------------");
            while (rs.next()) {
                String ticket_number = rs.getString(1);
                int price = rs.getInt(2);
                String flight_number = rs.getString(3);
                String seat_id = rs.getString(4);
                String departure_time = rs.getString(5);
                String arrival_time = rs.getString(6);
                String departure_airport = rs.getString(7);
                String arrival_airport = rs.getString(8);
                System.out.printf("%s %d %s %s %s %s %s %s%n", ticket_number, price, flight_number, seat_id, departure_time, arrival_time, departure_airport, arrival_airport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 항공편 현황 조회, 현재 시간을 기준으로 모든 항공편들의 현황 표시 (출발전, 비행중, 도착완료)
     *
     * @param startDate 검색 시작 날짜, YYYY-MM-DD
     * @param endDate 검색 끝 날짜, YYYY-MM-DD
     * @param currentTime      현재 시간, format: YYYY-MM-DD HH24:MI:SS, example: 2020-02-12 05:39:06
     */
    public static void getFlightStatus(Statement stmt, String startDate, String endDate, String currentTime) {
        String sql_beforeDeparture = "select f.FLIGHT_NUMBER , " +
                "f.DEPARTURE_TIME , " +
                "f.ARRIVAL_TIME , " +
                "f.DEPARTURE_AIRPORT , " +
                "f.ARRIVAL_AIRPORT " +
                "from flight f " +
                "where f.arrival_time between TO_DATE('"+startDate+" 00:00:00', 'YYYY-MM-DD HH24:MI:SS') " +
                "and TO_DATE('"+currentTime+"', 'YYYY-MM-DD  HH24:MI:SS') " +
                "order by f.departure_time";

        String sql_nowFlying = "select f.FLIGHT_NUMBER , " +
                "f.DEPARTURE_TIME , " +
                "f.ARRIVAL_TIME , " +
                "f.DEPARTURE_AIRPORT , " +
                "f.ARRIVAL_AIRPORT " +
                "from flight f " +
                "where TO_DATE('"+currentTime+"', 'YYYY-MM-DD HH24:MI:SS') between f.departure_time and f.arrival_time " +
                "order by f.departure_time";

        String sql_afterArrival = "select f.FLIGHT_NUMBER , " +
                "f.DEPARTURE_TIME , " +
                "f.ARRIVAL_TIME , " +
                "f.DEPARTURE_AIRPORT , " +
                "f.ARRIVAL_AIRPORT " +
                "from flight f " +
                "where f.departure_time between TO_DATE('"+currentTime+"', 'YYYY-MM-DD  HH24:MI:SS') " +
                "and TO_DATE('"+endDate+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS') " +
                "order by f.departure_time";

        try {
            ResultSet rs = stmt.executeQuery(sql_beforeDeparture);
            System.out.printf("%nBefore Departure%n");
            System.out.println("flight_number departure_time arrival_time departure_airport arrival_airport");
            System.out.println("---------------------");
            while (rs.next()) {
                String flight_number = rs.getString(1);
                String departure_time = rs.getString(2);
                String arrival_time = rs.getString(3);
                String departure_airport = rs.getString(4);
                String arrival_airport = rs.getString(5);
                System.out.printf("%s %s %s %s %s%n", flight_number, departure_time, arrival_time, departure_airport, arrival_airport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = stmt.executeQuery(sql_nowFlying);
            System.out.printf("%nNow Flying%n");
            System.out.println("flight_number departure_time arrival_time departure_airport arrival_airport");
            System.out.println("---------------------");
            while (rs.next()) {
                String flight_number = rs.getString(1);
                String departure_time = rs.getString(2);
                String arrival_time = rs.getString(3);
                String departure_airport = rs.getString(4);
                String arrival_airport = rs.getString(5);
                System.out.printf("%s %s %s %s %s%n", flight_number, departure_time, arrival_time, departure_airport, arrival_airport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = stmt.executeQuery(sql_afterArrival);
            System.out.printf("%nAfter Arrival%n");
            System.out.println("flight_number departure_time arrival_time departure_airport arrival_airport");
            System.out.println("---------------------");
            while (rs.next()) {
                String flight_number = rs.getString(1);
                String departure_time = rs.getString(2);
                String arrival_time = rs.getString(3);
                String departure_airport = rs.getString(4);
                String arrival_airport = rs.getString(5);
                System.out.printf("%s %s %s %s %s%n", flight_number, departure_time, arrival_time, departure_airport, arrival_airport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
