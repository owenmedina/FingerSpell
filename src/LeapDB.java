import java.sql.*;
import java.sql.Date;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class LeapDB {
    private static Connection con;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
            con = getConnection();
	}

	//constructor
	public LeapDB() throws Exception{
		con = getConnection();
	}
	
	//confirmed
	public static Connection getConnection() throws Exception{ //connect to mysql db
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/fingerspell_db?useLegacyDatetimeCode=false&serverTimezone=UTC"; // "//localhost OR ip add/port/dbname" //where the db is located
			//Class.forName(driver);
			
			//establish connection
			String user = "root" ;
			String pw = "" ;
			Connection conn = DriverManager.getConnection(url, user, pw);
			System.out.println("Connected successfully."); //tester
			return conn;
		}catch(Exception e) {
			System.out.println("Error in getConnection: " + e); //in case of any errors
		}
		return null;
	}
	
        
}
