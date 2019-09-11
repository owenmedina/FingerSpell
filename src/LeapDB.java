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
	
	public static void insertSample(String name, int[][] distances) throws Exception{
		
		int tTip_tProx = distances[0][1];
		int tTip_iTip  = distances[0][2];
		int tTip_iProx  = distances[0][3];
		int tTip_mTip = distances[0][4];
		int tTip_mProx = distances[0][5];
		int tTip_rTip = distances[0][6];
		int tTip_rProx = distances[0][7];
		int tTip_pTip = distances[0][8];
		int tTip_pProx = distances[0][9];
		int tTip_pc = distances[0][10];
		int tProx_iTip = distances[1][2];
		int tProx_iProx = distances[1][3];
		int tProx_mTip = distances[1][4];
		int tProx_mProx = distances[1][5];
		int tProx_rTip = distances[1][6];
		int tProx_rProx = distances[1][7];
		int tProx_pTip = distances[1][8];
		int tProx_pProx = distances[1][9];
		int tProx_pc = distances[1][10];
		int iTip_iProx = distances[2][3];
		int iTip_mTip = distances[2][4];
		int iTip_mProx = distances[2][5];
		int iTip_rTip = distances[2][6];
		int iTip_rProx = distances[2][7];
		int iTip_pTip = distances[2][8];
		int iTip_pProx = distances[2][9];
		int iTip_pc = distances[2][10];
		int iProx_mTip = distances[3][4];
		int iProx_mProx = distances[3][5];
		int iProx_rTip = distances[3][6];
		int iProx_rProx = distances[3][7];
		int iProx_pTip = distances[3][8];
		int iProx_pProx = distances[3][9];
		int iProx_pc = distances[3][10];
		int mTip_mProx = distances[4][5];
		int mTip_rTip = distances[4][6];
		int mTip_rProx = distances[4][7];
		int mTip_pTip = distances[4][8];
		int mTip_pProx = distances[4][9];
		int mTip_pc = distances[4][10];
		int mProx_rTip = distances[5][6];
		int mProx_rProx = distances[5][7];
		int mProx_pTip = distances[5][8];
		int mProx_pProx = distances[5][9];
		int mProx_pc = distances[5][10];
		int rTip_rProx = distances[6][7];
		int rTip_pTip = distances[6][8];
		int rTip_pProx = distances[6][9];
		int rTip_pc = distances[6][10];
		int rProx_pTip = distances[7][8];
		int rProx_pProx = distances[7][9];
		int rProx_pc = distances[7][10];
		int pTip_pProx = distances[8][9];
		int pTip_pc = distances[8][10];
		int pProx_pc = distances[9][10];
		
		
		// inserts a sample of a gesture in the gestures table
		try{
			// first inner array is for the x, y and z of the tip of the finger
			// second inner array is for the x, y and z of the proximal joint of the finger
			
			PreparedStatement newGesture = con.prepareStatement("INSERT INTO gestures VALUES('"
						+ tTip_tProx + "', '" + tTip_iTip + "', '" + tTip_iProx + "', '" + tTip_mTip + "', '" + tTip_mProx + "', '" + tTip_rTip + "', '"
						+ tTip_rProx + "', '" + tTip_pTip + "', '" + tTip_pProx + "', '" + tTip_pc + "', '" + tProx_iTip + "', '" + tProx_iProx + "', '"
						+ tProx_mTip + "', '" + tProx_mProx + "', '" + tProx_rTip + "', '" + tProx_rProx + "', '" + tProx_pTip + "', '" + tProx_pProx + "', '"
						+ tProx_pc + "', '" + iTip_iProx + "', '" + iTip_mTip + "', '" + iTip_mProx + "', '" + iTip_rTip + "', '" + iTip_rProx + "', '"
						+ iTip_pTip + "', '" + iTip_pProx + "', '" + iTip_pc + "', '" + iProx_mTip + "', '" + iProx_mProx + "', '" + iProx_rTip + "', '"
						+ iProx_rProx + "', '" + iProx_pTip + "', '" + iProx_pProx + "', '" + iProx_pc + "', '" + mTip_mProx + "', '" + mTip_rTip + "', '"
						+ mTip_rProx + "', '" + mTip_pTip + "', '" + mTip_pProx + "', '" + mTip_pc + "', '" + mProx_rTip + "', '" + mProx_rProx + "', '"
						+ mProx_pTip + "', '" + mProx_pProx + "', '" + mProx_pc + "', '" + rTip_rProx + "', '" + rTip_pTip + "', '" + rTip_pProx + "', '"
						+ rTip_pc + "', '" + rProx_pTip + "', '" + rProx_pProx + "', '" + rProx_pc + "', '" + pTip_pProx + "', '" + pTip_pc + "', '" + pProx_pc + "')"
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
