import java.sql.*;
import java.util.Scanner;

public class KertAirlinePhase3 {
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static final String USER_COMPANY = "airline";
    public static final String USER_PASSWD = "airline";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Success!");
        } catch (ClassNotFoundException e) {
            System.err.println("error = " + e.getMessage());
            System.exit(1);
        }
        try {
            conn = DriverManager.getConnection(URL, USER_COMPANY, USER_PASSWD);
            System.out.println("Connected.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Cannot get a connection: " + ex.getLocalizedMessage());
            System.err.println("Cannot get a connection: " + ex.getMessage());
            System.exit(1);
        }
        try {
            assert conn != null;
            stmt = conn.createStatement();
            System.out.println("[+] stmt created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert stmt != null;
        // run methods
        System.out.println("1) get reserved ticket by ticket number");
        System.out.println("2) get reserved ticket by passport number");
        System.out.println("3) get flight status");
        System.out.println("4) get tickets by conditions not reserved yet ");
        System.out.println("5) reserve ticket by ticket number customer information needed");
        System.out.println("6) get cheapest roundway trip from all tickets departs at certain airport");
        System.out.println("7) refund ticket by ticket number customer information needed");
        System.out.println("8) get name of all airports ");
        System.out.println("9) get country of all airports ");
        System.out.println("10) retrive all airport(s) in Seoul ");
        System.out.println("11) retrive all first class seat ticket(s) for flights arrive in Seoul  ");
        System.out.println("12) retrive number of tickets and minimun price of each tickets group by it's class arrive in seoul ");
        System.out.println("13) retrive all ticket(s) for flights arrive in Seoul after 2019-01-01 ");
        System.out.println("14) retrive all ticket(s) for flights arrive in Seoul that it's price are lower than 400 ");
        System.out.println("15) retrive all ticket(s) for flights depart in Seoul after 2021-05-01 ");
        System.out.println("16) retrive all ticket(s) for flights arrive in Seoul not booked yet ");
        System.out.println("17) retrive all flight(s)'s flight_number,departure_time  depart in Seoul after 2018-08-01 ");
        System.out.println("18) retrive number of employees running particular flight grouped and ordered by ones role ");
        System.out.println("19) retrive all ticket(s) for flights depart in Seoul after 2021-05-01 except all of filght's tickets are reserved ");
        System.out.println("20) retrieve airport information");
        System.out.println("21) retrieve flight information of each employees");
        System.out.println("22) retrieve number of seats and average of seats group by flight number and call for each flight");
        System.out.println("23) retrieve number of seats group by class of seat that price of seat is more or equal to NUMBER");
        System.out.println("24) retrieve employee's name and salary who departs at moscow and have less than NUMBER salary");
        System.out.println("25) retrieve employee id of employee who runs flight with employee whose id is NUMBER");
        System.out.println("26) retrieve customer's country and average price of ticket of which price is more or equal to NUMBER grouped by it's country ");
        System.out.println("27) retrieve name of employee who runs flight and arrival time of flight ordered by arrival time of flight");
        System.out.println("28) retrieve flight number and sum of employee's salary who runs flight grouped by flight number and ordered by sum");
        System.out.println("29) retrieve name, salary and gender of employee who have more or equal to NUMBER salary if he's male or who have less than NUMBER salary if she's female");
        System.out.print("select options: ");

        int options = scanner.nextInt();

        if (options == 1) {
            System.out.println("insert ticket number");
            String ticketNumber = scanner.next();
            System.out.println("insert passenger first name");
            String passengerFirstName = scanner.next();
            System.out.println("insert passenger last name");
            String passengerLastname = scanner.next();
            System.out.println("insert departure date");
            String departureDate = scanner.next();

            getReservedTicketsByTicketNumber(stmt, ticketNumber, passengerFirstName, passengerLastname, departureDate);
//            getReservedTicketsByTicketNumber(stmt, "NIMEZE77824B339", "Michael", "Jensen", "2020-02-11");
        } else if (options == 2) {
            System.out.println("insert passport number");
            String passportNumber = scanner.next();
            System.out.println("insert passenger first name");
            String passengerFirstName = scanner.next();
            System.out.println("insert passenger last name");
            String passengerLastname = scanner.next();
            System.out.println("insert passenger phone number");
            String passengerPhoneNumber = scanner.next();

            getReservedTicketsByPassportNumber(stmt, passportNumber, passengerFirstName, passengerLastname, passengerPhoneNumber);
//            getReservedTicketsByPassportNumber(stmt, "9479ONCO71778", "Michael", "Jensen", "010-7037-2155");
        } else if (options == 3) {
            System.out.println("insert start date");
            String startDate = scanner.next();
            System.out.println("insert end date");
            String endDate = scanner.next();
            String currentTime = "2020-01-12 18:00:00";
            getFlightStatus(stmt, startDate, endDate, currentTime);
//            getFlightStatus(stmt, "2020-01-03", "2020-01-26", currentTime);
        } else if (options == 4) {
            System.out.print("Enter airport you want to depart : ");
            String departureairport = scanner.nextLine();
            System.out.print("Enter airport you want to arrive : ");
            String arrivalairport = scanner.nextLine();
            System.out.print("Enter date you want to depart (YYYY-MM-DD) : ");
            String date = scanner.nextLine();
            System.out.print("Enter number of tickets you want to reserve : ");
            int passnumber = scanner.nextInt();
            System.out.print("Enter class you want to reserve : ");
            scanner.nextLine();
            String classname = scanner.nextLine();
            getNotreservedTickets(stmt, departureairport, arrivalairport, date, passnumber, classname);

        } else if (options == 5) {
            System.out.print("Enter ticket number you want to reserve : ");
            String ticketnumber = scanner.nextLine();
            System.out.print("Enter your phonenumber (with dash) : ");
            String phonenumber = scanner.nextLine();
            System.out.print("Enter your first name : ");
            String firstname = scanner.nextLine();
            System.out.print("Enter your last name: ");
            String lastname = scanner.nextLine();
            System.out.print("Enter your birth date : ");
            String birthdate = scanner.nextLine();
            System.out.print("Enter your country : ");
            String country = scanner.nextLine();
            System.out.print("Enter your gender : ");
            String gender = scanner.nextLine();
            System.out.print("Enter your passportnumber : ");
            String passportnumber = scanner.nextLine();

            reserveTicket(stmt, ticketnumber, phonenumber, firstname, lastname, birthdate, country, gender, passportnumber);


        } else if (options == 6) {
            System.out.println("Enter departing country to search cheapest roundway tickets");
            String country = scanner.nextLine();
            getCheapestRoundWayTicket(stmt, country);

        } else if (options == 7) {
            System.out.print("Enter ticket number to refund : ");
            String ticketNumber = scanner.nextLine();
            System.out.print("Enter passport number used for the ticket to refund : ");
            String passportNumber = scanner.nextLine();
            refundTicket(stmt, ticketNumber, passportNumber);

        } else if (options == 8) {
            getAirportListByCity(stmt);

        } else if (options == 9) {
            getAirportListByCode(stmt);

        } else if (options == 10) {
            retrive_airports_inseoul(stmt);

        } else if (options == 11) {
            retrive_firstclass_ticket_inseoul(stmt);

        } else if (options == 12) {
            retrive_tickets_minimum_price_groupby_class_arriveinseoul(stmt);

        } else if (options == 13) {
            retrive_ticket_arrive_inSeoul_after_20190101(stmt);

        } else if (options == 14) {
            retrive_ticket_arrive_inSeoul_price_lower_than_400(stmt);

        } else if (options == 15) {
            retrive_ticket_forflights_depart_inSeoul_after_20210501(stmt);

        } else if (options == 16) {
            retrive_ticket_arrive_inSeoul_not_booked(stmt);

        } else if (options == 17) {
            retrive_flight_number_departure_time_depart_inSeoul_after_20180801(stmt);

        } else if (options == 18) {
            retrive_number_of_employees_running_particular_flight_grouped_orderedby_role(stmt);

        } else if (options == 19) {
            retrive_ticket_depart_inSeoul_after_20210501_except_tickets_reserved(stmt);

        } else if (options == 20) {
            System.out.println("insert airport name");
            String airportName = scanner.next();
            q11(stmt, airportName);
//            q11(stmt, "Turin Airport");
        } else if (options == 21) {
            q12(stmt);
        } else if (options == 22) {
            q13(stmt);
        } else if (options == 23) {
            System.out.println("insert min price");
            String price = scanner.next();
            q14(stmt, price);
//            q14(stmt, "1000");
        } else if (options == 24) {
            System.out.println("insert and max salary departure city");
            String price = scanner.next();
            String city = scanner.next();
            q15(stmt, price, city);
//            q15(stmt, "20000", "Moscow");
        } else if (options == 25) {
            System.out.println("insert eid");
            String eid = scanner.next();
            q16(stmt, eid);
//            q16(stmt, "1558263874");
        } else if (options == 26) {
            System.out.println("insert min price");
            String price = scanner.next();
            q17(stmt, price);
//            q17(stmt, "1000");
        } else if (options == 27) {
            q18(stmt);
        } else if (options == 28) {
            q19(stmt);
        } else if (options == 29) {
            System.out.println("insert male price and female price");
            String male_price = scanner.next();
            String female_price = scanner.next();
            q20(stmt, male_price, female_price);
//            q20(stmt, "30000", "20000");
        } else {
            System.out.println("invalid option number (1 to 29)");
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


    public static void q11(Statement stmt, String airportName) {
        String sql = "SELECT a.airport_code code, a.airport_name NAME, a.city city, a.acountry country_code FROM airport a WHERE a.airport_name = '" + airportName + "'";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("code name city country");
            System.out.println("----------------------");

            while (rs.next()) {
                String code = rs.getString(1);
                String name = rs.getString(2);
                String city = rs.getString(3);
                String country = rs.getString(4);
                System.out.printf("%s %s %s %s%n", code, name, city, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void q12(Statement stmt) {
        String sql = "SELECT e.efirst_name, e.elast_name, f.departure_airport, f.arrival_airport, r.role FROM run r, employee e, flight f WHERE r.reid = e.employee_id AND r.rfid = f.flight_number";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("fname lame departure arrival role");
            System.out.println("---------------------------------");

            while (rs.next()) {
                String fname = rs.getString(1);
                String lname = rs.getString(2);
                String departure = rs.getString(3);
                String arrival = rs.getString(4);
                String role = rs.getString(5);
                System.out.printf("%s %s %s %s %s%n", fname, lname, departure, arrival, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void q13(Statement stmt) {
        String sql = "SELECT t.tfnum flight, s.class, Count(class), Round(Avg(price)) FROM (ticket t JOIN seat s ON t.tpid = s.spid AND t.tsid = s.seat_id) GROUP BY t.tfnum, s.class";
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("flight class count avgprice");
            System.out.println("---------------------------");

            while (rs.next()) {
                String flight = rs.getString(1);
                String class_ = rs.getString(2);
                int count = rs.getInt(3);
                int avgprice = rs.getInt(4);
                System.out.printf("%s %s %d %d%n", flight, class_, count, avgprice);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void q14(Statement stmt, String price) {
        String sql = "SELECT s.class, Count(*) FROM ((SELECT * FROM ticket t WHERE t.price >= "+price+") t2 JOIN seat s ON t2.tpid = s.spid AND t2.tsid = s.seat_id) GROUP BY s.class";
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("class count");
            System.out.println("-----------");

            while (rs.next()) {
                String class_ = rs.getString(1);
                int count = rs.getInt(2);
                System.out.printf("%s %d%n", class_, count);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void q15(Statement stmt, String price, String city) {
        String sql = "SELECT e.efirst_name, e.elast_name, e.salary FROM employee e WHERE e.salary < "+price+" AND EXISTS(SELECT * FROM ticket t, flight f, airport a WHERE e.employee_id = t.teid AND t.tfnum = f.flight_number AND f.departure_airport = a.airport_code AND a.city = '"+city+"')";
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("fname lname salary");
            System.out.println("------------------");

            while (rs.next()) {
                String fname = rs.getString(1);
                String lname = rs.getString(2);
                int salary = rs.getInt(3);
                System.out.printf("%s %s %d%n", fname, lname, salary);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void q16(Statement stmt, String eid) {
        String sql = "SELECT DISTINCT reid FROM run r WHERE r.rfid IN (SELECT rfid FROM run WHERE run.reid = '"+eid+"')";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("eid");
            System.out.println("---");

            while (rs.next()) {
                int eid_ = rs.getInt(1);
                System.out.printf("%d%n", eid_);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void q17(Statement stmt, String price) {
        String sql = "SELECT c2.ccountry, Round(Avg(c2.price)) FROM (SELECT c.cpassport_number, t.price, c.ccountry FROM customer c, ticket t WHERE c.cpassport_number = t.tcpassport AND t.price >= "+price+") c2 GROUP BY c2.ccountry";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("country avgprice");
            System.out.println("----------------");

            while (rs.next()) {
                String country = rs.getString(1);
                int avgprice = rs.getInt(2);
                System.out.printf("%s %d%n", country, avgprice);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void q18(Statement stmt) {
        String sql = "SELECT DISTINCT e.efirst_name, e.elast_name, f.arrival_time FROM run r, employee e, flight f WHERE r.reid = e.employee_id AND r.rfid = f.flight_number ORDER BY f.arrival_time";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("fname lname arrivaltime");
            System.out.println("-----------------------");

            while (rs.next()) {
                String fname = rs.getString(1);
                String lname = rs.getString(2);
                String arrivaltime = rs.getString(3);
                System.out.printf("%s %s %s%n", fname, lname, arrivaltime);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void q19(Statement stmt) {
        String sql = "SELECT f.flight_number, Sum(e.salary) sum_sal FROM run r, employee e, flight f WHERE r.reid = e.employee_id AND r.rfid = f.flight_number GROUP BY f.flight_number ORDER BY sum_sal DESC";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("flight_number sum_sal");
            System.out.println("---------------------");

            while (rs.next()) {
                String flight = rs.getString(1);
                int sum_sal = rs.getInt(2);
                System.out.printf("%s %d%n", flight, sum_sal);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void q20(Statement stmt, String male_price, String female_price) {
        String sql = "(SELECT e.efirst_name, e.elast_name, e.salary, e.egender FROM employee e WHERE e.egender = 'M' AND e.salary >= "+male_price+") UNION (SELECT e.efirst_name, e.elast_name, e.salary, e.egender FROM employee e WHERE e.egender = 'F' AND e.salary < "+female_price+")";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("fname lname salary gender");
            System.out.println("-------------------------");

            while(rs.next()) {
                String fname = rs.getString(1);
                String lname = rs.getString(2);
                int salary = rs.getInt(3);
                String gender = rs.getString(4);
                System.out.printf("%s %s %d %s%n", fname, lname, salary, gender);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 아직 예약이 안된 ticket을 param값에 맞춰서 반환
     *
     * @param departureairport 출발공항
     * @param arrivalairport   도착공항
     * @param departureDate    출발 날짜, 2020-02-11
     * @param passnumber       인원 수
     * @param classname        좌석 등급
     */
    public static void getNotreservedTickets(Statement stmt, String departureairport, String arrivalairport, String departureDate, int passnumber, String classname) {
        String sql = "select ticket_number, price, tfnum, tpid, tsid " +
                "from ticket " +
                "where tcpassport is NULL " +
                "and tfnum in ( " +
                "select  flight_number " +
                "from flight,ticket,seat " +
                "where tfnum=flight_number " +
                "and tcpassport is NULL " +
                "and departure_airport= '" + departureairport + "' " +
                "and arrival_airport = '" + arrivalairport + "' " +
                "and tsid=seat_id " +
                "and tpid=spid " +
                "and class = '" + classname + "' " +
                "and trunc(departure_time) = TO_DATE('" + departureDate + "', 'YYYY-MM-DD') " +
                "group by flight_number " +
                "having count(ticket_number)>=" + passnumber + ")";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("ticket_number price tfnum tpid tsid");
            System.out.println("--------------------------------");
            while (rs.next()) {
                String ticket_number = rs.getString(1);
                int price = rs.getInt(2);
                String flight_number = rs.getString(3);
                String plane_id = rs.getString(4);
                String seat_id = rs.getString(5);
                System.out.printf("%s %d %s %s %s \n", ticket_number, price, flight_number, plane_id, seat_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 고객의 정보를 customer에 추가 후 티켓의 tcpassport를 수정
     *
     * @param Ticketnumber   고객의 휴대폰 번호
     * @param phone_number   고객의 휴대폰 번호
     * @param First_name     고객의 이름
     * @param Last_name      고객의 성
     * @param Birth_date     고객의 생일
     * @param Country        고객의 국적
     * @param Gender         고객의 성별
     * @param Passportnumber 고객의 여권번호
     */
    public static void reserveTicket(Statement stmt, String Ticketnumber, String phone_number, String First_name, String Last_name, String Birth_date, String Country, String Gender, String Passportnumber) {
        String sql = "insert into customer values ('" + Passportnumber + "', '" + First_name + "', '" + Last_name + "', '" + Country + "', '" + Birth_date + "', '" + Gender + "', '" + phone_number + "') ";
        try {
            stmt.executeQuery(sql);
            System.out.printf("%s %s %s %s %s %s inserted \n", phone_number, First_name, Last_name, Birth_date, Country, Gender);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql = "update ticket set tcpassport = ' " + Passportnumber + " ' where Ticket_number=' " + Ticketnumber + " '";
        try {
            stmt.executeQuery(sql);
            stmt.executeQuery("commit");
            System.out.printf("%s ticket reserved \n", Ticketnumber);
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
     * @param startDate   검색 시작 날짜, YYYY-MM-DD
     * @param endDate     검색 끝 날짜, YYYY-MM-DD
     * @param currentTime 현재 시간, format: YYYY-MM-DD HH24:MI:SS, example: 2020-02-12 05:39:06
     */
    public static void getFlightStatus(Statement stmt, String startDate, String endDate, String currentTime) {
        String sql_beforeDeparture = "select f.FLIGHT_NUMBER , " +
                "f.DEPARTURE_TIME , " +
                "f.ARRIVAL_TIME , " +
                "f.DEPARTURE_AIRPORT , " +
                "f.ARRIVAL_AIRPORT " +
                "from flight f " +
                "where f.arrival_time between TO_DATE('" + startDate + " 00:00:00', 'YYYY-MM-DD HH24:MI:SS') " +
                "and TO_DATE('" + currentTime + "', 'YYYY-MM-DD  HH24:MI:SS') " +
                "order by f.departure_time";

        String sql_nowFlying = "select f.FLIGHT_NUMBER , " +
                "f.DEPARTURE_TIME , " +
                "f.ARRIVAL_TIME , " +
                "f.DEPARTURE_AIRPORT , " +
                "f.ARRIVAL_AIRPORT " +
                "from flight f " +
                "where TO_DATE('" + currentTime + "', 'YYYY-MM-DD HH24:MI:SS') between f.departure_time and f.arrival_time " +
                "order by f.departure_time";

        String sql_afterArrival = "select f.FLIGHT_NUMBER , " +
                "f.DEPARTURE_TIME , " +
                "f.ARRIVAL_TIME , " +
                "f.DEPARTURE_AIRPORT , " +
                "f.ARRIVAL_AIRPORT " +
                "from flight f " +
                "where f.departure_time between TO_DATE('" + currentTime + "', 'YYYY-MM-DD  HH24:MI:SS') " +
                "and TO_DATE('" + endDate + " 23:59:59', 'YYYY-MM-DD HH24:MI:SS') " +
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

    // Cheapest round-way ticket
    public static void getCheapestRoundWayTicket(Statement stmt, String country) {
        String sql = " WITH ROUNDTRIP AS "
                + "    		(SELECT F1.flight_number as flightnum1, F2.flight_number as flightnum2, T1.price + T2.price as price "
                + "    		FROM FLIGHT F1, FLIGHT F2, TICKET T1, TICKET T2, AIRPORT A "
                + "    		WHERE F1.departure_airport = F2.arrival_airport "
                + "    		AND F1.arrival_airport = F2.departure_airport "
                + "    		AND A.acountry = '" + country + "' "
                + "    		AND F1.departure_airport = A.airport_code "
                + "    		AND F1.flight_number = T1.tfnum "
                + "    	AND F2.flight_number = T2.tfnum) "
                + "		SELECT flightnum1, flightnum2, price "
                + "		from ROUNDTRIP "
                + "		where price IN (SELECT MIN(price) "
                + "                    FROM ROUNDTRIP) "
                + "		AND ROWNUM = 1";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String flightnum1 = rs.getString(1);
                String flightnum2 = rs.getString(2);
                int price = rs.getInt(3);

                System.out.println(flightnum1 + "\t" + flightnum2 + "\t" + price);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    // Refund Ticket
    public static void refundTicket(Statement stmt, String ticketNumber, String passportNumber) {
        String sql = "SELECT * " +
                "FROM TICKET " +
                "WHERE ticket_number = '" + ticketNumber + "' " +
                "AND tcpassport = '" + passportNumber + "'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.err.println("Ticket info match fails");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
        sql = "UPDATE TICKET " +
                "SET tcpassport = NULL " +
                "WHERE ticket_number = '" + ticketNumber + "'";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            rs = stmt.executeQuery("commit");
            System.out.println("ticket refunded");
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    // Search every airport
    public static void getAirportListByCode(Statement stmt) {
        String sql = "SELECT airport_code, airport_name, acountry, city " +
                "FROM AIRPORT " +
                "ORDER BY airport_name";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String airportcode = rs.getString(1);
                String airportname = rs.getString(2);
                String country = rs.getString(3);
                String city = rs.getString(4);

                System.out.println(airportcode + "\t" + airportname + "\t" + country + "\t" + city);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public static void getAirportListByCity(Statement stmt) {
        String sql = "SELECT city, airport_code, airport_name " +
                "FROM AIRPORT " +
                "ORDER BY city";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String city = rs.getString(1);
                String airportcode = rs.getString(2);
                String airportname = rs.getString(3);

                System.out.println(city + "\t" + airportcode + "\t" + airportname);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public static void retrive_airports_inseoul(Statement stmt) {
        String sql = "select Airport_name " +
                "from AIRPORT " +
                "where City = 'Seoul' ";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String airportname = rs.getString(1);

                System.out.println(airportname);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public static void retrive_firstclass_ticket_inseoul(Statement stmt) {
        String sql = "select Ticket_number, Price " +
                "from FLIGHT, AIRPORT,  TICKET, SEAT " +
                "where City = 'Seoul' and Airport_code = Arrival_airport and TFnum = Flight_number and Class='First' and TPid=Spid and TSid= Seat_id ";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String ticketnumber = rs.getString(1);
                String price = rs.getString(2);

                System.out.println(ticketnumber + "\t" + price);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public static void retrive_tickets_minimum_price_groupby_class_arriveinseoul(Statement stmt) {
        String sql = "select  Class,  count(*), min(Price) " +
                "from  FLIGHT,  SEAT, AIRPORT, TICKET " +
                "where  City = 'Seoul' and Airport_code=Arrival_airport and TFnum=Flight_number and TPid=Spid and TSid=Seat_id " +
                "group by  Class";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String classname = rs.getString(1);
                int count = rs.getInt(2);
                int minprice = rs.getInt(3);

                System.out.println(classname + "\t" + count + "\t" + minprice);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public static void retrive_ticket_arrive_inSeoul_after_20190101(Statement stmt) {
        String sql = "select  Ticket_number " +
                "from   FLIGHT, AIRPORT, TICKET  " +
                "where  City = 'Seoul' and Airport_code=Arrival_airport and TFnum=Flight_number " +
                "and Flight_number in (  " +
                "select Flight_number " +
                "from  FLIGHT  " +
                "where  Arrival_time>'2019-01-01'  ) ";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String ticketnumber = rs.getString(1);

                System.out.println(ticketnumber);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public static void retrive_ticket_arrive_inSeoul_price_lower_than_400(Statement stmt) {
        String sql = "select  Ticket_number, Price  " +
                "from  TICKET  " +
                "where  Price<400  " +
                "and exists( " +
                "select  *  " +
                "from  FLIGHT,  AIRPORT  " +
                "where  City = 'Seoul'  and Airport_code=Arrival_airport and TFnum=Flight_number )";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String ticketnumber = rs.getString(1);
                int price = rs.getInt(2);

                System.out.println(ticketnumber + "\t" + price);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public static void retrive_ticket_forflights_depart_inSeoul_after_20210501(Statement stmt) {
        String sql = "select  Ticket_number,  Departure_time,  Departure_airport  " +
                "from  TICKET, FLIGHT, AIRPORT " +
                "where  City = 'Seoul' and Airport_code=Departure_airport and TFnum=Flight_number  " +
                "and Flight_number in ( " +
                "select Flight_number " +
                "from  FLIGHT " +
                "where  Departure_time>'2021-05-01' )";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String ticketnumber = rs.getString(1);
                String departure_time = rs.getString(2);
                String departure_airport = rs.getString(3);

                System.out.println(ticketnumber + "\t" + departure_time + "\t" + departure_airport);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public static void retrive_ticket_arrive_inSeoul_not_booked(Statement stmt) {
        String sql = "with NOTBOOKEDTICKET as " +
                "(select * " +
                "from  TICKET  " +
                "where TCPassport is null  )  " +
                "select  Ticket_number  " +
                "from  FLIGHT, AIRPORT, NOTBOOKEDTICKET  " +
                "where   City = 'Seoul' and Airport_code=Arrival_airport  and TFnum=Flight_number";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String ticketnumber = rs.getString(1);

                System.out.println(ticketnumber);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public static void retrive_flight_number_departure_time_depart_inSeoul_after_20180801(Statement stmt) {
        String sql = "select  Flight_number, Departure_time " +
                "from   FLIGHT, AIRPORT " +
                "where  City = 'Seoul'  and Airport_code=Departure_airport  " +
                "and Flight_number in ( " +
                "select  Flight_number  " +
                "from  FLIGHT  " +
                "where Departure_time>'2018-08-01' ) " +
                "order by Departure_time asc";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String ticketnumber = rs.getString(1);
                String departure_time = rs.getString(2);
                System.out.println(ticketnumber + "\t" + departure_time);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public static void retrive_number_of_employees_running_particular_flight_grouped_orderedby_role(Statement stmt) {
        String sql = "select  Role, count (*) " +
                "from  FLIGHT, RUN " +
                "where  Flight_number='GMPSGN1526'  and RFid=Flight_number " +
                "group by  Role " +
                "order by  Role ";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String role = rs.getString(1);
                int count = rs.getInt(2);
                System.out.println(role + "\t" + count);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public static void retrive_ticket_depart_inSeoul_after_20210501_except_tickets_reserved(Statement stmt) {
        String sql = "select  Ticket_number, Departure_time, Departure_airport  " +
                "from  TICKET,  FLIGHT, AIRPORT " +
                "where  City = 'Seoul' and Airport_code=Departure_airport and TFnum=Flight_number " +
                "and Flight_number in ( " +
                "select Flight_number  " +
                "from FLIGHT " +
                " where Departure_time>'2021-05-01' ) " +
                "intersect " +
                "select  Ticket_number, Departure_time, Departure_airport " +
                "from  TICKET, FLIGHT, AIRPORT " +
                "where  TCPassport is null ";

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String ticketnumber = rs.getString(1);
                String departure_time = rs.getString(2);
                String departure_airport = rs.getString(3);
                System.out.println(ticketnumber + "\t" + departure_time + "\t" + departure_airport);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }
}
