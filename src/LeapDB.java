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
	
	public static void insertSample(String name, double[][] thumb, double[][] index, double[][] middle, double[][] ring, double[][] pinky, double[] palm) throws Exception{
		// inserts a sample of a gesture in the gestures table
		try{
			// first inner array is for the x, y and z of the tip of the finger
			// second inner array is for the x, y and z of the proximal joint of the finger
			double thumb_tip_x = thumb[0][0];
			double thumb_tip_y = thumb[0][1];
			double thumb_tip_z = thumb[0][2];
			double thumb_prox_x = thumb[1][0];
			double thumb_prox_y = thumb[1][1];
			double thumb_prox_z = thumb[1][2];
			double index_tip_x = index[0][0];
			double index_tip_y = index[0][1];
			double index_tip_z = index[0][2];
			double index_prox_x = index[1][0];
			double index_prox_y = index[1][1];
			double index_prox_z = index[1][2];
			double middle_tip_x = middle[0][0];
			double middle_tip_y = middle[0][1];
			double middle_tip_z = middle[0][2];
			double middle_prox_x = middle[1][0];
			double middle_prox_y = middle[1][1];
			double middle_prox_z = middle[1][2];
			double ring_tip_x = ring[0][0];
			double ring_tip_y = ring[0][1];
			double ring_tip_z = ring[0][2];
			double ring_prox_x = ring[1][0];
			double ring_prox_y = ring[1][1];
			double ring_prox_z = ring[1][2];
			double pinky_tip_x = pinky[0][0];
			double pinky_tip_y = pinky[0][1];
			double pinky_tip_z = pinky[0][2];
			double pinky_prox_x = pinky[1][0];
			double pinky_prox_y = pinky[1][1];
			double pinky_prox_z = pinky[1][2];
			double palm_x = palm[0];
			double palm_y = palm[1];
			double palm_z = palm[2];
			PreparedStatement newGesture = con.prepareStatement("INSERT INTO raw_gestures VALUES('"
						+ thumb_tip_x + "', '" + thumb_tip_y + "', '" + thumb_tip_z + "', '" + thumb_prox_x + "', '" + thumb_prox_y + "', '" + thumb_prox_z + "', '"
						+ index_tip_x + "', '" + index_tip_y + "', '" + index_tip_z + "', '" + index_prox_x + "', '" + index_prox_y + "', '" + index_prox_z + "', '"
						+ middle_tip_x + "', '" + middle_tip_y + "', '" + middle_tip_z + "', '" + middle_prox_x + "', '" + middle_prox_y + "', '" + middle_prox_z + "', '"
						+ ring_tip_x + "', '" + ring_tip_y + "', '" + ring_tip_z + "', '" + ring_prox_x + "', '" + ring_prox_y + "', '" + ring_prox_z + "', '"
						+ pinky_tip_x + "', '" + pinky_tip_y + "', '" + pinky_tip_z + "', '" + pinky_prox_x + "', '" + pinky_prox_y + "', '" + pinky_prox_z + "', '"
						+ palm_x + "', '" + palm_y + "', '" + palm_z + "')"
					);
			newGesture.executeUpdate(); //execute the insert
		}catch(Exception e) {
			System.out.println("Error in insertOrders " + e); //in case of any errors;
		}
		finally{
			System.out.println("Insert to Orders completed"); //tester;
		}
	}
	
        
}
